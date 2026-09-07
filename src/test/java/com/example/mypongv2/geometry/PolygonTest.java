package com.example.mypongv2.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PolygonTest {

    private static final float TOLERANCE = 1e-4f;

    @Test
    void boxHasFourCornersInOrder() {
        Polygon box = Polygon.box(0f, 0f, 10f, 4f);
        assertEquals(4, box.getPoints().size());
        assertEquals(new Vector2(0f, 0f), box.getPoints().get(0));
        assertEquals(new Vector2(10f, 4f), box.getPoints().get(2));
    }

    @Test
    void aBoxHasOneAxisPerEdge() {
        assertEquals(4, Polygon.box(0f, 0f, 2f, 2f).getAxes().size());
    }

    @Test
    void projectionOfABoxOntoTheXAxisSpansItsWidth() {
        Projection projection = Polygon.box(3f, 1f, 9f, 5f).project(new Vector2(1f, 0f));
        assertEquals(3f, projection.getMin(), TOLERANCE);
        assertEquals(9f, projection.getMax(), TOLERANCE);
    }

    @Test
    void projectionOfABoxOntoTheYAxisSpansItsHeight() {
        Projection projection = Polygon.box(3f, 1f, 9f, 5f).project(new Vector2(0f, 1f));
        assertEquals(1f, projection.getMin(), TOLERANCE);
        assertEquals(5f, projection.getMax(), TOLERANCE);
    }

    @Test
    void pointsCannotBeMutatedThroughTheAccessor() {
        Polygon box = Polygon.box(0f, 0f, 1f, 1f);
        assertThrows(UnsupportedOperationException.class,
                () -> box.getPoints().add(new Vector2(9f, 9f)));
    }

    @Test
    void constructorCopiesTheListItIsGiven() {
        List<Vector2> corners = new ArrayList<>();
        corners.add(new Vector2(0f, 0f));
        corners.add(new Vector2(1f, 0f));
        corners.add(new Vector2(1f, 1f));

        Polygon polygon = new Polygon(corners);
        corners.clear();

        assertEquals(3, polygon.getPoints().size());
    }

    @Test
    void rotatingAFullTurnLeavesTheShapeWhereItWas() {
        Polygon box = Polygon.box(0f, 0f, 2f, 2f);
        Polygon rotated = box.rotateAround(1f, 1f, (float) (2 * Math.PI));
        for (int i = 0; i < box.getPoints().size(); i++) {
            assertEquals(box.getPoints().get(i).getX(), rotated.getPoints().get(i).getX(), 1e-3f);
            assertEquals(box.getPoints().get(i).getY(), rotated.getPoints().get(i).getY(), 1e-3f);
        }
    }

    @Test
    void projectingAnEmptyPolygonIsRejected() {
        Polygon empty = new Polygon(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> empty.project(new Vector2(1f, 0f)));
    }
}
