package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.Ship.Ship;
import it.unibo.object_onepiece.model.Utils.Position;

public class BarrelImpl extends EntityImpl implements Barrel {

    private final int experienceGiven;

    public BarrelImpl(Section section, Position position, int experienceGiven) {
        super(section, position);
        this.experienceGiven = experienceGiven;
    }

    @Override
    public void take(Ship ship) {
        if(ship instanceof Player player) {
            player.addExperience(experienceGiven);
        }
    }

    @Override
    public void onCollisionWith(Collider collider) {
        if(collider instanceof Player player) {
            take(player);
        }
        this.remove();
    }

    @Override
    public Rigidness getRigidness() {
        return Rigidness.SOFT;
    }
}
