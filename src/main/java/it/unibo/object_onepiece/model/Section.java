package it.unibo.object_onepiece.model;

import java.util.List;
import java.util.Optional;

import it.unibo.object_onepiece.model.Entity.Position;

/**
* Interface that models a Section of the world, it's itself divided in cells
* that can contain an Entity 
*/
public interface Section {
    public <T extends Entity> Optional<T> getEntityAt(Position position);
    public <T extends Entity> List<T> getEntities();

    public Player getPlayer();
}