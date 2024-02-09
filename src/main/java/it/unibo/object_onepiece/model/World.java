package it.unibo.object_onepiece.model;

import java.util.List;

import it.unibo.object_onepiece.model.Utils.State;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.Argument;

/**
 * Represents World of the game, with the current section and the saved ones.
 */
public interface World {
    /**
     * Grid rows.
     */
    int SECTION_ROWS = 10;
    /**
     * Grid columns.
     */
    int SECTION_COLUMNS = 10;

    /**
     * 
     * @return State last saved section.
     */
    State getSavedState();
    /**
     * Save current section.
     */
    void setSavedState();

    Player getPlayer();
    List<Enemy> getEnemies();

    Event<Argument<Section>> getSectionInstantiatedEvent();
}
