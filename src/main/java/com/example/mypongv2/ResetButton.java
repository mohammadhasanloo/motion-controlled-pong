package com.example.mypongv2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class ResetButton {
    private RectF rect;
    private Paint paint;

    public ResetButton(float left, float top, float right, float bottom) {
        rect = new RectF(left, top, right, bottom);
        paint = new Paint();
        paint.setColor(Color.BLUE); // Red color
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }

    public boolean isClicked(MotionEvent event) {
        return rect.contains(event.getX(), event.getY());
    }
}

