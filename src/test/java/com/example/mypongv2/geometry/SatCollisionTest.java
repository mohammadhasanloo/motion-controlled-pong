package com.example.mypongv2.geometry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SatCollisionTest {

    @Test
    void overlappingBoxesCollide() {
        assertTrue(SatCollision.intersects(
                Polygon.box(0f, 0f, 10f, 10f),
                Polygon.box(5f, 5f, 15f, 15f)));
    }

    @Test
    void separatedBoxesDoNotCollide() {
        assertFalse(SatCollision.intersects(
                Polygon.box(0f, 0f, 10f, 10f),
                Polygon.box(20f, 20f, 30f, 30f)));
    }

    @Test
    void boxesSeparatedOnOneAxisOnlyDoNotCollide() {
        // Overlapping vertically, apart horizontally: one gap is enough.
        assertFalse(SatCollision.intersects(
                Polygon.box(0f, 0f, 10f, 10f),
                Polygon.box(11f, 0f, 20f, 10f)));
    }

    @Test
    void touchingEdgesCountAsContact() {
        assertTrue(SatCollision.intersects(
                Polygon.box(0f, 0f, 10f, 10f),
                Polygon.box(10f, 0f, 20f, 10f)));
    }

    @Test
    void aContainedBoxCollidesWithTheOneAroundIt() {
        assertTrue(SatCollision.intersects(
                Polygon.box(0f, 0f, 100f, 100f),
                Polygon.box(40f, 40f, 60f, 60f)));
    }

    @Test
    void collisionIsSymmetric() {
        Polygon a = Polygon.box(0f, 0f, 10f, 10f);
        Polygon b = Polygon.box(5f, 5f, 15f, 15f);
        assertTrue(SatCollision.intersects(a, b) == SatCollision.intersects(b, a));
    }

    @Test
    void rotatingThePaddleBringsItOntoABallAnAxisAlignedTestWouldMiss() {
        // The point of using SAT rather than rectangle overlap: the paddle tilts.
        // A ball up and to the right of a flat paddle is clear of it, but comes
        // into contact once that paddle is rotated 45 degrees about its centre.
        Polygon ball = Polygon.box(14f, 5f, 16f, 7f);
        Polygon flat = Polygon.box(0f, 0f, 20f, 2f);
        Polygon tilted = flat.rotateAround(10f, 1f, (float) (Math.PI / 4));

        assertFalse(SatCollision.intersects(ball, flat));
        assertTrue(SatCollision.intersects(ball, tilted));
    }

    @Test
    void rotatingAPaddleAwayFromTheBallSeparatesThem() {
        Polygon ball = Polygon.box(0f, 0f, 2f, 2f);
        Polygon paddle = Polygon.box(10f, 10f, 30f, 12f).rotateAround(20f, 11f, 0.3f);
        assertFalse(SatCollision.intersects(ball, paddle));
    }
}
