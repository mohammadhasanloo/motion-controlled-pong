package com.example.mypongv2;

class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 perpendicular() {
        return new Vector2(-y, x);
    }

    public float dotProduct(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public Vector2 rotateAround(float centerX, float centerY, float angleInRadians) {
        float xPrime = (float) ((x - centerX) * Math.cos(angleInRadians) - (y - centerY) * Math.sin(angleInRadians) + centerX);
        float yPrime = (float) ((x - centerX) * Math.sin(angleInRadians) + (y - centerY) * Math.cos(angleInRadians) + centerY);
        return new Vector2(xPrime, yPrime);
    }
}