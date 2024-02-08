package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Ship;
import it.unibo.object_onepiece.model.Utils.Position;

/**
 * The implementation of the Moving state of the controller.
 * The input is interpreted as a direction to move the player to.
 */
public final class MoveState extends InputState {
    /**
     * The constructor of MoveState.
     */
    public MoveState() {}
    
    @Override
    public Boolean perform(final Position pos, final Ship ship) {
        /* var moveS = ship.canMove(ship.getPosition().whereTo(pos));
        if (moveS.canStep()) {
            ship.move(ship.getPosition().whereTo(pos), ship.getPosition().distanceFrom(pos));
            return true;
        }
        return false; */
        return true;
    }

    @Override
    public States getState() {
        return States.MOVING;
    }

}