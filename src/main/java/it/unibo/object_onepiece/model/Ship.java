package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Direction;
import it.unibo.object_onepiece.model.Utils.MoveReturnTypes;

/**
 * An animated entity, it can be the player or an enemy
 */
public interface Ship extends Entity, Crashable {
    public MoveReturnTypes move(Direction direction);

    public void takeDamage(int damage);

    public void setHealth(int health);

    public Weapon getWeapon();
    public int getHealth();
    public Direction getDirection();
}