package it.unibo.object_onepiece.model.ship;

import it.unibo.object_onepiece.model.Utils.Position;

public interface Weapon extends ShipComponent {
    enum ShootDetails {
        SHOOTED_SUCCESSFULLY,
        OUT_OF_SHOOTING_RANGE,
        WEAPON_BROKEN
    };

    record ShootReturnType(boolean hasShooted, ShootDetails details) { }; 

    ShootReturnType shoot(Position position);

    int getMaxDamage();
    int getMinDamage();
    int getRange();

    static Weapon cannon() {
        final int maxDamage = 20;
        final int minDamage = 10;
        final int range = 3;
        final int health = 100;

        return new WeaponImpl(maxDamage, minDamage, range, health);
    }

    static Weapon railgun() {
        final int maxDamage = 50;
        final int minDamage = 0;
        final int range = 5;
        final int health = 70;

        return new WeaponImpl(maxDamage, minDamage, range, health);
    }

    static Weapon heavycannon() {
        final int maxDamage = 80;
        final int minDamage = 40;
        final int range = 1;
        final int health = 120;

        return new WeaponImpl(maxDamage, minDamage, range, health);
    }
}
