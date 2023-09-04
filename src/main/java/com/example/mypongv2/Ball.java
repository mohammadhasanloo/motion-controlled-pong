package com.example.mypongv2;

import android.graphics.RectF;

public class Ball {
    private float x;
    private float y;
    private float radius;
    private float velocityX;
    private float velocityY;
    private Polygon polygon;

    private static final float GRAVITATIONAL_ACCELERATION = 9.81f; // m/s^2

    public Ball(float x, float y, float radius, float velocityX, float velocityY) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        polygon = new Polygon();
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

    public void updatePosition(float timeDeltaSeconds) {
        // Update position based on current velocity
        x += velocityX * timeDeltaSeconds;
        y += velocityY * timeDeltaSeconds;

        // Update velocity due to gravity
        velocityY += GRAVITATIONAL_ACCELERATION * timeDeltaSeconds;

        // Update polygon
        updatePolygon();
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

    public Polygon getPolygon() {
        return polygon;
    }

    private void updatePolygon() {
        polygon.setAsBox(x - radius, y - radius, x + radius, y + radius);
    }
}
