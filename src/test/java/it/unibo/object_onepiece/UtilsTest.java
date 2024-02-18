/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package it.unibo.object_onepiece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.object_onepiece.model.Utils.CardinalDirection;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.Utils.Bound;

class UtilsTest {
    private static final Bound B = new Bound(15, 15);
    private static final Position P = new Position(4, 2);
    private static final Position POS1 = new Position(4, 5);
    private static final Position POS2 = new Position(6, 2);
    private static final Position POS3 = new Position(4, 4);
    private static final Position POS4 = new Position(7, 9);

    @Test
    void testPosition() throws ClassNotFoundException {
        assertEquals(4, P.moveTowards(CardinalDirection.EAST).row());
        assertEquals(3, P.moveTowards(CardinalDirection.EAST).column());
        assertEquals(3, P.moveTowards(CardinalDirection.NORTH).row());
        assertEquals(2, P.moveTowards(CardinalDirection.NORTH).column());

        assertEquals(3, P.distanceFrom(POS1));
        assertEquals(2, P.distanceFrom(POS2));
        assertNotEquals(0, P.distanceFrom(POS3));
        assertNotEquals(0, P.distanceFrom(POS4));

        assertEquals(new Position(0, 1), P.versorOf(POS1));
        assertEquals(new Position(1, 0), P.versorOf(POS2));
        assertEquals(new Position(0, 1), P.versorOf(POS3));
        assertEquals(new Position(1, 1), P.versorOf(POS4));

        assertTrue(P.isInlineWith(POS1, CardinalDirection.NORTH));
        assertFalse(P.isInlineWith(POS2, CardinalDirection.SOUTH));
        assertFalse(P.isInlineWith(POS3, CardinalDirection.EAST));
        assertFalse(P.isInlineWith(POS4, CardinalDirection.WEST));

        assertEquals(CardinalDirection.EAST, P.whereTo(POS1));
        assertEquals(CardinalDirection.SOUTH, P.whereTo(POS2));
        assertEquals(CardinalDirection.EAST, P.whereTo(POS3));
        assertEquals(CardinalDirection.SOUTH, P.whereTo(POS4));

        final int sei = 6;
        final int dodici = 12;

        assertEquals(sei, POS2.opposite(CardinalDirection.WEST, B).row());
        assertEquals(dodici, POS2.opposite(CardinalDirection.WEST, B).column());
    }

    @Test
    void testBound() throws ClassNotFoundException {
        assertTrue(B.isInside(POS1));
        assertTrue(B.isInside(POS2));
        assertTrue(B.isInside(POS3));
        assertTrue(B.isInside(POS4));
    }
}
