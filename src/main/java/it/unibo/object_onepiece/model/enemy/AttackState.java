package it.unibo.object_onepiece.model.enemy;

import it.unibo.object_onepiece.model.Utils;
import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.enemy.Enemy.States;

public class AttackState implements EnemyState {
    Boolean alligned = false;
    EnemyImpl ship;
    CardinalDirection prevDirection;

    public AttackState(EnemyImpl ship) {
        this.ship = ship;
    }

    @Override
    public Boolean perform() {
        if(alligned == false){
            prevDirection = ship.getDirection();

            switch (prevDirection) {
                case NORTH:
                    ship.move(CardinalDirection.EAST, 1);
                    break;
                case EAST:
                    ship.move(CardinalDirection.NORTH, 1);
                    break;
                case WEST:
                    ship.move(CardinalDirection.SOUTH, 1);
                    break;
                case SOUTH:
                    ship.move(CardinalDirection.WEST, 1);
                    break;
                default:
                    break;
            }
            return true;
        } else {
            ship.getWeapon().shoot(nextPos(prevDirection));
            ship.changeState(States.PATROLLING);
            return true;
        }
    }


    @Override
    public States getState() {
        return States.ATTACKING;
    }

    private Position nextPos(final CardinalDirection direction) {
        return Utils.getCardinalDirectionsTranslationMap()
                .get(direction).apply(ship.getPosition());
    }
 
}
