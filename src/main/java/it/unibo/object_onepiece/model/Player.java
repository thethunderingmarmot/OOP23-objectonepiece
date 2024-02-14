package it.unibo.object_onepiece.model;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;

/**
 * Implementation of the Player interface.
 * @see Player
 */
public final class Player extends Ship {
    private static final int DEFAULT_EXPERIENCE_HEAL_COST = 100;

    private int experience;

    public record PlayerUpdatedArgs(List<Integer> healthList, List<Integer> maxHealthList, int experience) { }
    private Event<PlayerUpdatedArgs> onPlayerUpdated = new Event<>();

    /**
     * Constructor for PlayerImpl.
     * It's protected to only allow creating this object inside this package.
     * Actual object creation is handled in the static method inside Player interface.
     * @param section the reference to the Section containing this Player 
     * @param position the position to place this Player at
     * @param direction the starting direction of the Player's ship
     * @param experience the starting experience value of the Player
     * @param weapon the starting weapon of the Player's ship
     * @param sail the starting sail of the Player's ship
     * @param bow the starting bow of the Player's ship
     * @see Player
     */
    private Player(final Section section,
                   final Position position,
                   final CardinalDirection direction,
                   final int experience,
                   final Weapon weapon,
                   final Sail sail,
                   final Bow bow,
                   final Keel keel) {
        super(section, position, direction, weapon, sail, bow, keel);
        this.experience = experience;
    }

    protected Player(final Section spawnSection, final Position spawnPosition) {
        this(spawnSection,
             spawnPosition,
             CardinalDirection.NORTH,
             0,
             Weapon.cannon(),
             Sail.sloop(),
             Bow.standard(),
             Keel.standard());
    }

    protected Player(final Player oldPlayer, final Section customSection, final Position customPosition) {
        this(customSection,
             customPosition,
             oldPlayer.getDirection(),
             oldPlayer.getExperience(),
             oldPlayer.getWeapon(),
             oldPlayer.getSail(),
             oldPlayer.getBow(),
             oldPlayer.getKeel());
    }

    protected Player(final Player oldPlayer) {
        this(oldPlayer.getSection(),
             oldPlayer.getPosition(),
             oldPlayer.getDirection(),
             oldPlayer.getExperience(),
             oldPlayer.getWeapon(),
             oldPlayer.getSail(),
             oldPlayer.getBow(),
             oldPlayer.getKeel());
    }

    /**
     * Checks wether the Player current position is the same as the one passed as argument.
     * @param position the position to check against
     * @return a boolean that indicates wether the Player is in that position
     */
    public boolean isInSamePositionAs(final Position position) {
        return this.getPosition().equals(position);
    }

    /**
     * Moves the Player's ship towards a specified position.
     * @param destination the position to reach
     * @return a boolean that indicates wether the Player has moved
     * @see Ship
     */
    public boolean moveTo(final Position destination) {
        final CardinalDirection direction = this.getPosition().whereTo(destination);
        final int distance = this.getPosition().distanceFrom(destination);
        final MoveDetails moveResult = super.move(direction, distance);
        if(moveResult.equals(MoveDetails.BORDER_REACHED)) {
            this.getWorld().createNewSection(
                (newSection) -> new Player(this, newSection, 
                                           this.getPosition().opposite(this.getDirection(), newSection.getBounds())));
        }
        return Enemy.ACTION_SUCCESS_CONDITIONS.contains(moveResult);
    }

    /**
     * Makes the Player shoot at a target position.
     * @param target the position to shoot at
     * @return a ShootReturnType as it's defined in Weapon
     * @see Weapon
     */
    public boolean shootAt(final Position target) {
        return super.shoot(target).hasShooted();
    }

    private <T> Stream<T> getFromShipComponent(Function<ShipComponent, T> map) {
        return super.getShipComponents().stream().map(map);
    }

    protected List<Integer> getMaxHealths() {
        return getFromShipComponent((c) -> c.getMaxHealth()).toList();
    }

    protected List<Integer> getHealths() {
        return getFromShipComponent((c) -> c.getHealth()).toList();
    }

    /**
     * Getter for the experience private field.
     * @return the currently owned experience value
     */
    protected int getExperience() {
        return this.experience;
    }

    /**
     * Adds experience to the Player's owned experience.
     * @param newExperience the experience value to add
     */
    protected void addExperience(final int experienceToAdd) {
        this.experience += experienceToAdd;
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    protected void subtractExperience(final int experienceToSubtract) {
        this.experience -= experienceToSubtract;
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    public void healWithExperience() {
        if(this.experience >= DEFAULT_EXPERIENCE_HEAL_COST) {
            this.subtractExperience(DEFAULT_EXPERIENCE_HEAL_COST);
            this.heal();
        }
    }

    /**
     * Overridden version of takeDamage to allow event invoking.
     * @see Ship
     */
    @Override
    protected void takeDamage(final int damage, final ShipComponent s) {
        super.takeDamage(damage, s);
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    protected void heal() {
        this.getShipComponents().forEach((c) -> c.setHealth(c.getMaxHealth()));
        this.onPlayerUpdated.invoke(new PlayerUpdatedArgs(getHealths(), getMaxHealths(), this.experience));
    }

    @Override
    protected void die() {
        super.die();
        if(this.getWorld().getSavedState().isPresent()) {
            this.getWorld().loadSavedSection();
        } else {
            this.getWorld().createNewSection(
                (newSection) -> new Player(newSection, this.getWorld().getPlayerDefaultSpawnPoint()));
        }
    }

    public Event<PlayerUpdatedArgs> getPlayerUpdatedEvent() {
        return this.onPlayerUpdated;
    }
}
