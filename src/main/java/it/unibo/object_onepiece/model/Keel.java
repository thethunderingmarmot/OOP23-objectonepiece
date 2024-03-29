package it.unibo.object_onepiece.model;

/**
 * This class represents a keel, which is a type of ShipComponent.
 * It defines a method to retrieving damage endurance of the keel
 * and checking if the keel is damaged.
 * It also defines factory methods for the available keels.
 * The keel is the lateral structure of the ship.
 * 
 * @see ShipComponent
 */
public final class Keel extends ShipComponent {
    private final int damageEndurance;

    /**
     * Constructor for class Keel.
     * 
     * @param  health          the health of the keel
     * @param  damageEndurance the damage endurance of the keel
     */
    private Keel(final int health, final int damageEndurance) {
        super(health);
        this.damageEndurance = damageEndurance;
    }

    /**
     * Constructor to copy from an existing Keel.
     * 
     * @param  origin the keel to copy from
     */
    private Keel(final Keel origin) {
        super(origin);
        this.damageEndurance = origin.getDamageEndurance();
    }

    /**
     * This method makes a copy of a Keel.
     * 
     * @return the copy of the keel.
     */
    @Override
    protected Keel copy() {
        return new Keel(this);
    }

    /**
     * This method checks if the keel is damaged.
     * To do so it checks if the current health is minor than
     * the maximum health divided by the damage endurance field.
     * 
     * @return the result of the control.
     */
    protected boolean isKeelDamaged() {
        return this.getHealth() < (this.getMaxHealth() / this.damageEndurance);
    }

    /**
     * Getter for the damage endurance of the ship keel.
     * 
     * @return keel damage endurance.
     */
    protected int getDamageEndurance() {
        return this.damageEndurance;
    }

    /**
     * Factory method for the standard keel.
     * 
     * @return keel with his standard stats.
     */
    protected static Keel standard() {
        final int health = 100;
        final int damageEndurance = 2;

        return new Keel(health, damageEndurance);
    }

    /**
     * Factory method for the heavy keel, a really strong keel
     * for damage endurance and health.
     * 
     * @return keel with his standard stats.
     */
    protected static Keel heavy() {
        final int health = 120;
        final int damageEndurance = 4;

        return new Keel(health, damageEndurance);
    }

    /**
     * Factory method for the light keel, a really weak keel
     * for damage endurance and health.
     * 
     * @return keel with his standard stats.
     */
    protected static Keel light() {
        final int health = 80;
        final int damageEndurance = 1;

        return new Keel(health, damageEndurance);
    }
}
