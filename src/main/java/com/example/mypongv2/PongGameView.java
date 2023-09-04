package com.example.mypongv2;

import static android.graphics.Paint.Style.STROKE;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;

import com.example.mypongv2.Ball;
import com.example.mypongv2.SATCollision;

import java.util.ArrayList;
import java.util.List;

public class PongGameView extends View implements SensorEventListener {
    private Paint paint;
    private RectF rect;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private float movementFactor = 12.0f;
    private float rotationFactor = 3.0f;
    private float paddleAngle = 0.0f;
    private Ball ball;
    private long lastUpdateTime;

    private float maxAngleRotation = 10.0f;

    private ResetButton resetButton;

    private Sensor linearAcceleration;
    private float zAcceleration = 0.0f;

    public PongGameView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(0xFF000000); // Set the color to black
        rect = new RectF();

        lastUpdateTime = System.currentTimeMillis();

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);

        resetButton = new ResetButton(10, 10, 110, 60); // Adjust the position and size as needed

        linearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, linearAcceleration, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float width = w * 3 / 10;
        float height = 20;
        float left = (w - width) / 2;
        float top = h - height - width; // Subtract the width from the bottom position
        rect.set(left, top, left + width, top + height);

        // Ball

        float ballX = w / 2;
        float ballY = 10;
        float ballRadius = 20;
        float ballVelocityX = 0; // Speed in pixels per second
        float ballVelocityY = 1000; // Speed in pixels per second
        ball = new Ball(ballX, ballY, ballRadius, ballVelocityX, ballVelocityY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(paddleAngle, rect.centerX(), rect.centerY());
        canvas.drawRect(rect, paint);
        canvas.restore();

        // Update ball position based on its velocity
        long currentTime = System.currentTimeMillis();
        float timeDeltaSeconds = (currentTime - lastUpdateTime) / 1000f;
        lastUpdateTime = currentTime;
        ball.updatePosition(timeDeltaSeconds);

        // Check collisions
        checkCollisions();

        // Draw the ball
        canvas.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), paint);

        //Draw Reset Button
        resetButton.draw(canvas);

        //Draw Borders
        drawBorder(canvas);
    }

    // Add this method to handle touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && resetButton.isClicked(event)) {
            resetBallPosition();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void resetBallPosition() {
        ball.setX(getWidth() / 2);
        ball.setY(10);

        ball.setVelocityX(0);
        ball.setVelocityY(1000);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float ax = event.values[0];
            float left = rect.left - ax * movementFactor;
            float right = rect.right - ax * movementFactor;

            // Prevent the rectangle from
            if (left < 0) {
                left = 0;
                right = rect.width();
            } else if (right > getWidth()) {
                right = getWidth();
                left = getWidth() - rect.width();
            }
            rect.set(left, rect.top, right, rect.bottom);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float zRotation = event.values[2];
            float newPaddleAngle = paddleAngle - zRotation * rotationFactor;
            if (Math.abs(newPaddleAngle) <= this.maxAngleRotation) {
                paddleAngle = newPaddleAngle;
            }
        } else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            zAcceleration = event.values[2];
        }
        invalidate(); // Request a redraw
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void checkCollisions() {
        Polygon ballPolygon = ball.getPolygon();
        Polygon paddlePolygon = getPaddlePolygon();

        if (SATCollision.checkCollision(ballPolygon, paddlePolygon)) {
            // Calculate the angle of reflection based on the paddle's angle
            float angleOfReflection = (float) Math.toRadians(90 - paddleAngle);
            float newVelocityX = (float) (ball.getVelocityX() * Math.cos(angleOfReflection) + ball.getVelocityY() * Math.sin(angleOfReflection));
            float newVelocityY = (float) (-ball.getVelocityX() * Math.sin(angleOfReflection) + ball.getVelocityY() * Math.cos(angleOfReflection));

//            ball.setVelocityX(newVelocityX);
//            ball.setVelocityY(newVelocityY);

            // Increase ball speed based on zAcceleration
            float speedMultiplier = 1.0f + Math.abs(zAcceleration) / 10.0f; // adjust as needed
            ball.setVelocityX(newVelocityX * speedMultiplier);
            ball.setVelocityY(newVelocityY * speedMultiplier);

            zAcceleration = 0.0f; // reset zAcceleration

        }

        // Check for wall collisions
        if (ball.getX() - ball.getRadius() < 0 || ball.getX() + ball.getRadius() > getWidth()) {
            ball.reverseVelocityX();
        }

        if (ball.getY() - ball.getRadius() < 0) {
            ball.reverseVelocityY();
        }
    }

    private Polygon getPaddlePolygon() {
        float width = rect.width();
        float height = rect.height();
        float centerX = rect.centerX();
        float centerY = rect.centerY();

        float angleInRadians = (float) Math.toRadians(paddleAngle);

        List<Vector2> points = new ArrayList<>();
        points.add(new Vector2(centerX - width / 2, centerY - height / 2));
        points.add(new Vector2(centerX + width / 2, centerY - height / 2));
        points.add(new Vector2(centerX + width / 2, centerY + height / 2));
        points.add(new Vector2(centerX - width / 2, centerY + height / 2));

        for (int i = 0; i < points.size(); i++) {
            Vector2 point = points.get(i);
            points.set(i, point.rotateAround(centerX, centerY, angleInRadians));
        }

        Polygon paddlePolygon = new Polygon();
        paddlePolygon.setPoints(points);
        return paddlePolygon;
    }

    private void drawBorder(Canvas canvas) {
        Paint borderPaint = new Paint();
        borderPaint.setColor(0xFFFF0000); // Red color
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(20); // 5px border width

        // Draw the border around the screen
        canvas.drawRect(0, 0, getWidth(), getHeight(), borderPaint);
    }
 }
