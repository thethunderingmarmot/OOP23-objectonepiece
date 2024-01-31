package it.unibo.object_onepiece.model.Ship;

public interface ShipComponent {
    public void setHealth(int health);
    public void setShip(Ship ship);

    public Ship getShip();
    public int getHealth();
    public int getMaxHealth();
}
