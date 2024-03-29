package it.unibo.object_onepiece.controller;

import it.unibo.object_onepiece.model.Utils.Position;

import java.util.Map;
import java.util.function.Consumer;

import it.unibo.object_onepiece.model.Player;
import it.unibo.object_onepiece.model.World;

/**
 * The implementation of the Controller Interface.
 */
public final class ControllerImpl implements Controller {

    private static Map<States, InputState> states = Map.of(
        States.SHOOTING, new ShootState(),
        States.MOVING, new MoveState());

    private static Map<Buttons, Consumer<World>> buttons = Map.of(
        Buttons.FIX, (w) -> w.getPlayer().healWithExperience(),
        Buttons.RESPAWN, (w) -> w.getPlayer().respawn()

    );

    @Override
    public void action(final Position position, final World world, final States state) {
        if (states.get(state).perform(position, world.getPlayer())) {
            world.getEnemies().forEach(e -> e.goNext());
        }
    }

    @Override
    public void pressGameButton(final Buttons button, final World world) {
        buttons.get(button).accept(world);
    }

    /**
     * Abstract class defining the current InputState of the Controller.
     */ 
    protected abstract static class InputState {
        /**
         * @param  pos    the input accepted by the controller
         * @param  player the player
         * @return        whenether it was possible to perform the action or not
         */
        protected abstract Boolean perform(Position pos, Player player);
        /**
         * @return The type of state that is implemented
         */
        protected abstract States getState();
    }
}
