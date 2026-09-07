package com.example.mypongv2.geometry;

/**
 * An immutable 2D vector.
 *
 * <p>Immutability matters here because vectors are handed to the collision code
 * and kept in polygon point lists; a mutable one shared between two polygons
 * would let a move in one silently reshape the other.
 */
public final class Vector2 {

    private final float x;
    private final float y;

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

    public Vector2 add(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    /** The vector turned a quarter turn counter-clockwise. */
    public Vector2 perpendicular() {
        return new Vector2(-y, x);
    }

    public float dotProduct(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public float length() {
        return (float) Math.sqrt(dotProduct(this));
    }

    /** This vector scaled to unit length, or the zero vector if it has none. */
    public Vector2 normalised() {
        float length = length();
        return length == 0f ? new Vector2(0f, 0f) : new Vector2(x / length, y / length);
    }

    public Vector2 rotateAround(float centreX, float centreY, float angleInRadians) {
        double cos = Math.cos(angleInRadians);
        double sin = Math.sin(angleInRadians);
        float dx = x - centreX;
        float dy = y - centreY;
        return new Vector2(
                (float) (dx * cos - dy * sin + centreX),
                (float) (dx * sin + dy * cos + centreY));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2)) {
            return false;
        }
        Vector2 that = (Vector2) other;
        return Float.compare(x, that.x) == 0 && Float.compare(y, that.y) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Float.hashCode(x) + Float.hashCode(y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
