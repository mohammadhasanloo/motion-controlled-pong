package com.example.mypongv2;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

public class SATCollision {
    public static boolean checkCollision(Polygon a, RectF b) {

        Polygon rectPolygon = new Polygon();
        rectPolygon.setAsBox(b.left, b.top, b.right, b.bottom);

        return checkCollision(a, rectPolygon);
    }

    public static boolean checkCollision(Polygon a, Polygon b) {
        List<Vector2> axes = new ArrayList<>();
        axes.addAll(a.getAxes());
        axes.addAll(b.getAxes());

        for (Vector2 axis : axes) {
            if (!overlap(project(a, axis), project(b, axis))) {
                return false;
            }
        }

        return true;
    }

    private static Vector2 project(Polygon polygon, Vector2 axis) {
        float min = axis.dotProduct(polygon.getPoints().get(0));
        float max = min;

        for (int i = 1; i < polygon.getPoints().size(); i++) {
            float p = axis.dotProduct(polygon.getPoints().get(i));
            if (p < min) {
                min = p;
            } else if (p > max) {
                max = p;
            }
        }

        return new Vector2(min, max);
    }

    private static boolean overlap(Vector2 a, Vector2 b) {
        return a.getX() <= b.getY() && a.getY() >= b.getX();
    }
}




