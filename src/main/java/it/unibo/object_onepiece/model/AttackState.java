package it.unibo.object_onepiece.model;

import it.unibo.object_onepiece.model.EnemyImpl.States;
import it.unibo.object_onepiece.model.Utils.Position;
/**
 * The attackState of Enemy.
 */
public final class AttackState extends EnemyState {
    private final EnemyImpl ship;
    private final NavigationSystem navigationSystem;
    private Position objective;
    /**
     * The constructor of attackState.
     * @param ship 
     * @param navigationSystem
     */
    protected AttackState(final EnemyImpl ship, final NavigationSystem navigationSystem) {
        this.ship = ship;
        this.navigationSystem = navigationSystem;
    }

    @Override
    protected Boolean perform() {
        final var player = this.ship.getSection().getPlayer();
        final Integer distanceFromPlayer = player.getPosition().distanceFrom(this.ship.getPosition());

        if (distanceFromPlayer > this.ship.getTriggerDistance()) {
            ship.changeState(States.PATROLLING);
            return false;
        }
        if (ship.shoot(player.getPosition()).hasShooted()) {
            return true;
        } else {
            if (objective == null || this.ship.getPosition().equals(objective)) {
                circularTargetPlayer();
            }

            final var suggestedDir = navigationSystem.move(objective, this.ship.getPosition());

            if (!EnemyImpl.ACTION_SUCCESS_CONDITIONS.contains(ship.move(suggestedDir, 1))) {
                ship.changeState(States.AVOIDING);
                return false;
            } 
        }
       return true;
    }

    @Override
    protected States getState() {
        return States.ATTACKING;
    }

    /**
     * This algorithm randomises a point around the
     * player for the enemy to reach.
     * 
     */
    private void circularTargetPlayer() {
        final var bound = this.ship.getSection().getBounds();
        final var tsection = this.ship.getSection();
        final int radius = 2;
        final var player = ship.getSection().getPlayer();

        do {
            final int x = -radius + Utils.getRandom().nextInt(radius * 2 + 1);
            //the pitagorean formula for one side of the triangle
            final int y = Double.valueOf(Math.sqrt(Math.pow(radius, 2) - Math.pow(x, 2))).intValue();

            objective = player.getPosition().sum(new Position(x, y * (Utils.getRandom().nextBoolean() ? 1 : -1)));
        } while (!bound.isInside(objective) || tsection.getEntityAt(objective).isPresent());
    }
}
