# CPS-CA2-Motion-Controlled-Pong-Game

# README

## General Code Description

This repository contains code for a simple 2D game developed in Java. The game features a bouncing ball that interacts with various objects, including a paddle and walls.

## Ball Class

The `Ball` class is responsible for managing the ball's position and velocity. The ball's position and velocity in the y-direction are defined by the formula: `y = ½ * g * t^2`, where `g` is the acceleration due to gravity.

## ResetButton Class

To reset the game, a reset button is embedded in the game interface.

## Polygon Class

The `Polygon` class maintains a list of 2D points using the `ArrayList` class. It features two essential functions:

- `setAsBox`: This function initializes the list of points to form a polygon with four sides, defined by the provided coordinates.
- `getAxes`: This function returns a list of vectors that are perpendicular to each edge of the polygon.

## 2Vector Class

The `2Vector` class is used to store 2D point coordinates (x and y). It provides several essential functions:

- `subtract`: Calculates the vector resulting from subtracting another vector.
- `perpendicular`: Computes the vector that is perpendicular to the current vector by swapping the x and y components.
- `dotProduct`: Computes the dot product between two vectors.
- `rotateAround`: Rotates the vector around a specified center point by a given angle, either clockwise or counterclockwise.

## SATCollision Class

The `SATCollision` class is used to detect collisions between various shapes in a 2D space.

### `checkCollision` Function

This function is designed to detect collisions between a polygon and a rectangle (`RectF`). It first creates a polygon that corresponds to the rectangle using the `setAsBox` function and then calls the `checkCollision` function with two input polygons.

### `checkCollision` Function (Polygons)

This function checks for collisions between two polygons. It first extracts the axis vectors for both polygons and collects them in a list. Then, for each axis, it checks for collision between the two polygons using the `project` and `overlap` functions. If no collision is found, it returns `false`; otherwise, it confirms the collision and returns `true`.

### `project` Function

The `project` function calculates the points on a polygon projected onto a specified axis that have the maximum and minimum distances along that axis.

### `overlap` Function

The `overlap` function checks if two intervals on a specified axis overlap.

## PongViewGame Class

The `PongViewGame` class is the main class responsible for the game's functionality. It includes various functions for handling different aspects of the game, including the use of sensors.

### Constructor

In the constructor, the `paint` and `rect` variables are initialized, and sensor information is obtained.

### `onSizeChanged` Method

This method determines the game's size and shape and sets the initial position of the ball at the beginning of the game.

### `onDraw` Method

The `onDraw` method handles the rotation of the paddle and updates the ball's position based on its speed. To calculate the time delta, it subtracts the current time from the last time and divides by 1000. This is essential for smooth updates, especially in cases where a frame rate of 30 frames per second is used (approximately 1/30 seconds per frame).

### `onTouchEvent` Method

When the user touches the reset button, the ball returns to its initial position.

### `onSensorChanged` Method

This method updates the player's rectangle position and angle based on sensor data. It also calls the `invalidate` method to redraw the game screen when sensor changes occur.

If the sensor is an accelerometer, the method calculates the acceleration in the x-axis, multiplies it by a factor (`movementFactor`), and computes the new paddle position. The player moves the paddle to the left or right based on the device's acceleration.

If the sensor is a gyroscope, the method calculates the player's angle by considering the device's rotation around the z-axis, subtracts it from the current angle, and calculates the new player angle. If the new angle exceeds the maximum allowed angle defined in the `maxAngleRotation` variable, the angle is adjusted.

### `checkCollisions` Method

This method checks if the ball has collided with the game's walls or the paddle. It then calculates the new velocity in the x and y directions using the provided formulas.

### `getPaddlePolygon` Method

The `getPaddlePolygon` method converts the player's rectangle coordinates into a polygon shape, considering the player's angle. This ensures accurate collision detection and reflection of the ball when it hits the paddle.

### `drawBorder` Method

The `drawBorder` method is responsible for drawing the game's border.

Additionally, an optional feature has been added to move the paddle left and right and provide acceleration to the ball based on z-axis movement in the late stages of development.

For more details on how to use this code and set up the game, please refer to the provided documentation or comments within the code files.
