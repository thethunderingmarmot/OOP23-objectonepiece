package it.unibo.object_onepiece.controller.Controller_states;
import it.unibo.object_onepiece.controller.Controller.States;
import it.unibo.object_onepiece.model.Utils.Position;

/**The interface that models the statesof the input of the controller.
 */
public interface InputState {
    /**
     * @param pos the input accepted by the controller
     * 
     * @return whenether it was possible to perform the action or not
     */
    Boolean perform(Position pos);
    /**
     * @return The type of state that is implemented
     */
    States getState();
}
