package it.unibo.object_onepiece.model.ship;
import java.util.Map;
import java.util.Optional;

import it.unibo.object_onepiece.model.Collidable;
import it.unibo.object_onepiece.model.Collider;
import it.unibo.object_onepiece.model.Entity;
import it.unibo.object_onepiece.model.EntityImpl;
import it.unibo.object_onepiece.model.Section;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;
import it.unibo.object_onepiece.model.events.EventArgs.BiArgument;

/**
 * An abstract class to define the methods implementation of Ship.
 * It also extends EntityImpl because a Ship is an Entity.
 * 
 * Can be extended by specific types of Ship like Player and Enemy
 */
public abstract class ShipImpl extends EntityImpl implements Ship {
    private Direction currDirection;
    private Weapon weapon;
    private Sail sail;
    private Bow bow;

    private final Event<BiArgument<Direction>> onDirectionChanged = Event.get();
    private final Event<Argument<Integer>> onTookDamage = Event.get();

    /**
    * Constructor for class ShipImpl.
    * @param s is the section where the ship is located
    * @param p is the position of the entity
    * @param d is the direction of the ship
    * @param weapon is the weapon of the ship
    * @param sail is the sail of the ship
    * @param bow is the bow of the ship
    */
    protected ShipImpl(final Section s, final Position p, final Direction d, final Weapon weapon, final Sail sail, final Bow bow) {
        super(s, p);
        this.setDirection(d);
        this.setWeapon(weapon);
        this.setSail(sail);
        this.setBow(bow);
    }

    /**
     * This method define the actual ship movement by checking the next position
     * using canMove() and move the Ship based on the MoveReturnType returned.
     * 
     * @param direction is the direction where the ship should move
     * @param steps is the number of steps that the ship should do to reach the final position
     * @return a MoveDetails that contains the result of the last movement made by the Ship
     * 
     * This is a recursive method that calls himself steps times.
     * Every call the method try to move the Ship to the next position up to the final position.
     * If along the path there are immovable obstacles the Ship stops right before them.
     */
    @Override
    public MoveDetails move(final Direction direction, final int steps) {
        Position nextPosition = this.getPosition().moveTowards(direction);
        Collidable obstacle = (Collidable) this.getSection().getEntityAt(nextPosition).get();

        Map<MoveDetails, Runnable> moveCondition = Map.of(
            MoveDetails.MOVED_SUCCESSFULLY, () -> this.setPosition(nextPosition),
            MoveDetails.ROTATED, () -> rotate(direction),
            MoveDetails.STATIC_COLLISION, () -> this.collideWith(obstacle),
            MoveDetails.MOVED_BUT_COLLIDED, () -> { this.collideWith(obstacle); this.setPosition(nextPosition); }
        );

        MoveReturnType nextStep = canMove(direction);
        moveCondition.get(nextStep.details()).run();

        if (steps == 0) {
            return nextStep.details();
        } else {
            return move(direction, steps - 1);
        }
    }

    public MoveDetails move(final Direction direction) {
        return move(direction, 1);
    }

    @Override
    public MoveReturnType canMove(final Direction direction) {
        if (this.sail.getHealth() <= 0) {
            return new MoveReturnType(false, MoveDetails.SAIL_BROKEN);
        }

        if (!direction.equals(this.currDirection)) {
            return new MoveReturnType(false, MoveDetails.ROTATED);
        }

        if (!this.getSection().getBounds().isInside(this.getPosition())) {
            return new MoveReturnType(false, MoveDetails.BORDER_REACHED);
        }

        Optional<Entity> obstacle = this.getSection().getEntityAt(this.getPosition().moveTowards(direction));

        if (obstacle.isPresent() && obstacle.get() instanceof Collidable c 
        && (c.getRigidness() == Rigidness.HARD || c.getRigidness() == Rigidness.MEDIUM)) {
            return new MoveReturnType(false, MoveDetails.STATIC_COLLISION);
        }

        if (obstacle.isPresent() && obstacle.get() instanceof Collidable c && c.getRigidness() == Rigidness.SOFT) {
            return new MoveReturnType(true, MoveDetails.MOVED_BUT_COLLIDED);
        }

        return new MoveReturnType(true, MoveDetails.MOVED_SUCCESSFULLY);
    }
    
    @Override
    public void takeDamage(final int damage, final ShipComponent s) {
        onTookDamage.invoke(new Argument<>(damage));
        s.setHealth(s.getHealth() - damage);
        if(this.bow.getHealth() <= 0) {
            this.remove();
        }
    }
    
    @Override
    public void setWeapon(final Weapon weapon) {
        this.weapon = weapon;
        this.weapon.setShip(this);
    }
    
    @Override
    public void setSail(final Sail sail) {
        this.sail = sail;
        this.sail.setShip(this);
    }
    
    @Override
    public void setBow(final Bow bow) {
        this.bow = bow;
        this.bow.setShip(this);
    }
    
    @Override
    public Weapon getWeapon() {
        return this.weapon;
    }
    
    @Override
    public Sail getSail() {
        return this.sail;
    }
    
    @Override
    public Bow getBow() {
        return this.bow;
    }
    
    @Override
    public Direction getDirection() {
        return this.currDirection;
    }

    @Override
    public Event<BiArgument<Direction>> getDirectionChangedEvent() {
        return this.onDirectionChanged;
    }

    @Override
    public Event<Argument<Integer>> getTookDamageEvent() {
        return this.onTookDamage;
    }

    protected void setDirection(final Direction newDirection) {
        onDirectionChanged.invoke(new BiArgument<>(this.currDirection, newDirection));
        this.currDirection = newDirection;
    }

    private void rotate(final Direction direction) {
        setDirection(direction);
    }
    
    @Override
    public void onCollisionWith(final Collider collider) {
        if(collider.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }
    
    @Override
    public void collideWith(final Collidable collidable) {
        collidable.onCollisionWith(this);
        if(collidable.getRigidness() == Rigidness.MEDIUM) {
            this.takeDamage(this.bow.getCrashDamage(), this.bow);
        }
    }
}
