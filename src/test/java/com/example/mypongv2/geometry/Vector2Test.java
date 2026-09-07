package com.example.mypongv2.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Vector2Test {

    private static final float TOLERANCE = 1e-4f;

    @Test
    void subtractGivesTheVectorBetweenTwoPoints() {
        Vector2 result = new Vector2(5f, 7f).subtract(new Vector2(2f, 3f));
        assertEquals(3f, result.getX(), TOLERANCE);
        assertEquals(4f, result.getY(), TOLERANCE);
    }

    @Test
    void perpendicularTurnsAQuarterTurnAndKeepsLength() {
        Vector2 original = new Vector2(3f, 4f);
        Vector2 turned = original.perpendicular();

        assertEquals(0f, original.dotProduct(turned), TOLERANCE);
        assertEquals(original.length(), turned.length(), TOLERANCE);
    }

    @Test
    void dotProductIsZeroForPerpendicularVectors() {
        assertEquals(0f, new Vector2(1f, 0f).dotProduct(new Vector2(0f, 1f)), TOLERANCE);
    }

    @Test
    void lengthMatchesPythagoras() {
        assertEquals(5f, new Vector2(3f, 4f).length(), TOLERANCE);
    }

    @Test
    void normalisedVectorHasUnitLength() {
        assertEquals(1f, new Vector2(3f, 4f).normalised().length(), TOLERANCE);
    }

    @Test
    void normalisingTheZeroVectorDoesNotDivideByZero() {
        Vector2 result = new Vector2(0f, 0f).normalised();
        assertEquals(0f, result.getX(), TOLERANCE);
        assertEquals(0f, result.getY(), TOLERANCE);
    }

    @Test
    void rotatingAFullTurnReturnsToTheStart() {
        Vector2 original = new Vector2(2f, 5f);
        Vector2 rotated = original.rotateAround(1f, 1f, (float) (2 * Math.PI));
        assertEquals(original.getX(), rotated.getX(), 1e-3f);
        assertEquals(original.getY(), rotated.getY(), 1e-3f);
    }

    @Test
    void rotatingAboutItselfLeavesThePointWhereItIs() {
        Vector2 rotated = new Vector2(4f, 9f).rotateAround(4f, 9f, 1.234f);
        assertEquals(4f, rotated.getX(), TOLERANCE);
        assertEquals(9f, rotated.getY(), TOLERANCE);
    }

    @Test
    void quarterTurnAboutTheOriginMovesXOntoY() {
        Vector2 rotated = new Vector2(1f, 0f).rotateAround(0f, 0f, (float) (Math.PI / 2));
        assertEquals(0f, rotated.getX(), TOLERANCE);
        assertEquals(1f, rotated.getY(), TOLERANCE);
    }

    @Test
    void equalVectorsCompareEqualAndShareAHashCode() {
        assertEquals(new Vector2(1f, 2f), new Vector2(1f, 2f));
        assertEquals(new Vector2(1f, 2f).hashCode(), new Vector2(1f, 2f).hashCode());
        assertNotEquals(new Vector2(1f, 2f), new Vector2(2f, 1f));
    }

    @Test
    void operationsReturnNewInstancesRatherThanMutating() {
        Vector2 original = new Vector2(1f, 2f);
        original.subtract(new Vector2(1f, 1f));
        original.perpendicular();
        assertTrue(original.equals(new Vector2(1f, 2f)));
    }
}
