package com.example.mypongv2;

import java.util.ArrayList;
import java.util.List;

class Polygon {
    private List<Vector2> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void setAsBox(float left, float top, float right, float bottom) {
        points.clear();
        points.add(new Vector2(left, top));
        points.add(new Vector2(right, top));
        points.add(new Vector2(right, bottom));
        points.add(new Vector2(left, bottom));
    }

    public List<Vector2> getPoints() {
        return points;
    }

    public void setPoints(List<Vector2> points) {
        this.points = points;
    }

    public List<Vector2> getAxes() {
        List<Vector2> axes = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1 == points.size() ? 0 : i + 1);

            Vector2 edge = p1.subtract(p2);
            Vector2 normal = edge.perpendicular();

            axes.add(normal);
        }

        return axes;
    }
}