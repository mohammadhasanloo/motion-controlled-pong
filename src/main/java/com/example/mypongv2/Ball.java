package com.example.mypongv2;

import android.graphics.RectF;

import com.example.mypongv2.geometry.Polygon;

/** The ball: position, velocity, and the gravity acting on it. */
public class Ball {

    /**
     * Earth gravity, in metres per second squared.
     *
     * <p>Positions in this game are pixels, not metres, so this value is converted
     * by {@link #PIXELS_PER_METRE} before it reaches a velocity. Applied directly
     * to pixel-space velocity it would accelerate the ball at roughly ten pixels
     * per second squared, which is imperceptible on a phone screen.
     */
    private static final float GRAVITY_METRES_PER_SECOND_SQUARED = 9.81f;

    /**
     * Pixels per metre. A gameplay scale rather than a physical constant; raise it
     * for a heavier, faster-falling ball.
     */
    private static final float PIXELS_PER_METRE = 300f;

    private static final float GRAVITY_PIXELS =
            GRAVITY_METRES_PER_SECOND_SQUARED * PIXELS_PER_METRE;

    private float x;
    private float y;
    private final float radius;
    private float velocityX;
    private float velocityY;

    public Ball(float x, float y, float radius, float velocityX, float velocityY) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    /** Advance the ball by one frame. */
    public void updatePosition(float timeDeltaSeconds) {
        x += velocityX * timeDeltaSeconds;
        y += velocityY * timeDeltaSeconds;
        velocityY += GRAVITY_PIXELS * timeDeltaSeconds;
    }

    public void reverseVelocityX() {
        velocityX = -velocityX;
    }

    public void reverseVelocityY() {
        velocityY = -velocityY;
    }

    public RectF getRectF() {
        return new RectF(x - radius, y - radius, x + radius, y + radius);
    }

    /**
     * The ball's bounding box, for collision.
     *
     * <p>Built on demand rather than cached, so it can never lag the position it
     * describes.
     */
    public Polygon getPolygon() {
        return Polygon.box(x - radius, y - radius, x + radius, y + radius);
    }
}
