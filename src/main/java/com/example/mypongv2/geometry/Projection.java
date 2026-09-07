package com.example.mypongv2.geometry;

/**
 * The shadow a shape casts on an axis: a minimum and a maximum.
 *
 * <p>A distinct type rather than a reused vector, so an overlap test compares a
 * minimum against a maximum instead of an x against a y.
 */
public final class Projection {

    private final float min;
    private final float max;

    public Projection(float min, float max) {
        if (min > max) {
            throw new IllegalArgumentException("min " + min + " exceeds max " + max);
        }
        this.min = min;
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    /** Whether the two intervals share any span, touching at an endpoint included. */
    public boolean overlaps(Projection other) {
        return min <= other.max && max >= other.min;
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }
}
