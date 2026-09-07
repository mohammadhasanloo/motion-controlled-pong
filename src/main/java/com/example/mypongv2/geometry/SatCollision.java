package com.example.mypongv2.geometry;

/**
 * Separating Axis Theorem overlap test for convex polygons.
 *
 * <p>Two convex shapes are apart if and only if some axis exists on which their
 * projections do not overlap, and only the edge normals of the two shapes need
 * checking. So finding one gap proves separation and lets the test return early;
 * finding none across every axis proves contact.
 *
 * <p>Free of Android imports, so the geometry can be exercised on a plain JVM
 * without a device or emulator.
 */
public final class SatCollision {

    private SatCollision() {
    }

    public static boolean intersects(Polygon a, Polygon b) {
        return noGapAlongAxesOf(a, a, b) && noGapAlongAxesOf(b, a, b);
    }

    private static boolean noGapAlongAxesOf(Polygon source, Polygon a, Polygon b) {
        for (Vector2 axis : source.getAxes()) {
            if (!a.project(axis).overlaps(b.project(axis))) {
                return false;
            }
        }
        return true;
    }
}
