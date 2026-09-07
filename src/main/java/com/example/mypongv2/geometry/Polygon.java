package com.example.mypongv2.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A convex polygon, stored as its vertices in order. */
public final class Polygon {

    private final List<Vector2> points;

    public Polygon(List<Vector2> points) {
        this.points = new ArrayList<>(points);
    }

    /** An axis-aligned rectangle, wound clockwise from the top-left. */
    public static Polygon box(float left, float top, float right, float bottom) {
        List<Vector2> corners = new ArrayList<>(4);
        corners.add(new Vector2(left, top));
        corners.add(new Vector2(right, top));
        corners.add(new Vector2(right, bottom));
        corners.add(new Vector2(left, bottom));
        return new Polygon(corners);
    }

    /** The vertices, unmodifiable, so a caller cannot reshape this polygon. */
    public List<Vector2> getPoints() {
        return Collections.unmodifiableList(points);
    }

    /** This polygon with every vertex rotated about a point. */
    public Polygon rotateAround(float centreX, float centreY, float angleInRadians) {
        List<Vector2> rotated = new ArrayList<>(points.size());
        for (Vector2 point : points) {
            rotated.add(point.rotateAround(centreX, centreY, angleInRadians));
        }
        return new Polygon(rotated);
    }

    /**
     * One axis per edge, each perpendicular to it.
     *
     * <p>These are the only directions a separating axis can lie along for convex
     * shapes, which is what makes the SAT test finite.
     */
    public List<Vector2> getAxes() {
        List<Vector2> axes = new ArrayList<>(points.size());
        for (int i = 0; i < points.size(); i++) {
            Vector2 current = points.get(i);
            Vector2 next = points.get((i + 1) % points.size());
            axes.add(current.subtract(next).perpendicular());
        }
        return axes;
    }

    /** The shadow this polygon casts on an axis. */
    public Projection project(Vector2 axis) {
        if (points.isEmpty()) {
            throw new IllegalStateException("cannot project a polygon with no points");
        }
        float min = axis.dotProduct(points.get(0));
        float max = min;
        for (int i = 1; i < points.size(); i++) {
            float value = axis.dotProduct(points.get(i));
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        return new Projection(min, max);
    }

    @Override
    public String toString() {
        return "Polygon" + points;
    }
}
