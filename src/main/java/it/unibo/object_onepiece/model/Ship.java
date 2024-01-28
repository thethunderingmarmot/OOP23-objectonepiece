package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Direction;

/**
 * An animated entity, it can be the player or an enemy
 */
public interface Ship extends Entity, Collider {
    public MoveReturnType move(Direction direction);

    public void takeDamage(int damage);

    public void setWeapon(Weapon weapon);
    public void setHealth(int health);

    public Weapon getWeapon();
    public int getHealth();
    public Direction getDirection();
}