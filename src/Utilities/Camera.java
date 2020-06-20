package Utilities;

import Transformation.Transformable;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class Camera implements Transformable {
    public int x;
    public int y;

    private int velX = 0;
    private int velY = 0;
    private int maxVel = 10;

    double angle = 0;
    double angVel = 0;
    double maxAngVel = 0.02;

    double scale = 0.2;
    double minScale = 0.1;
    double maxScale = 0.8;

    public Camera() {};

    public Camera(int velocity, double angularVelocity) {
        maxVel = velocity;
        maxAngVel = angularVelocity;
    }

    @Override
    public void translate(boolean left, boolean up, boolean right, boolean down) {
        if ((left && right) || (!left && !right)) {
            velX = 0;
        } else if (left) {
            velX = maxVel;
        } else {
            velX = -maxVel;
        }

        if ((up && down) || (!up && !down)) {
            velY = 0;
        } else if (up) {
            velY = maxVel;
        } else {
            velY = -maxVel;
        }
    }

    @Override
    public void rotate(boolean positive, boolean negative) {
        if ((positive && negative) || (!positive && !negative)) {
            angVel = 0;
        } else if (positive) {
            angVel = maxAngVel;
        } else {
            angVel = -maxAngVel;
        }
    }


    @Override
    public void scale(double scaleFactor) {
        scale = Math.max(Math.min(scale * Math.pow(2, -scaleFactor), maxScale), minScale);
    }

    public void move() {
        angle += angVel;

        x += (Math.cos(angle) * velX + Math.sin(angle) * velY) / scale;
        y += (-Math.sin(angle) * velX + Math.cos(angle) * velY) / scale;
    }

    public AffineTransform getTransform(int translateX, int translateY) {
        AffineTransform transform = new AffineTransform();
        transform.translate(translateX,  translateY);
        transform.rotate(angle);
        transform.scale(scale, scale);
        transform.translate(- translateX, - translateY);
        transform.translate(x, y);
        return transform;
    }

    public Point2D.Double transformPoint(Point p, int translateX, int translateY) {
        AffineTransform transform = getTransform(translateX, translateY);
        Point2D.Double pOld = new Point2D.Double(p.x, p.y);
        Point2D.Double pNew = new Point2D.Double(0, 0);
        try {
            transform.inverseTransform(pOld, pNew);
        } catch (NoninvertibleTransformException e) {
            System.out.println("Non invertible transform");
        }
        return pNew;
    }

    public Path2D.Double getClip(int width, int height) {
        Point2D.Double upperLeft = new Point2D.Double(0, 0);
        Point2D.Double upperRight = new Point2D.Double(width, 0);
        Point2D.Double lowerLeft = new Point2D.Double(0, height);
        Point2D.Double lowerRight = new Point2D.Double(width, height);

        Path2D.Double path = new Path2D.Double();
        path.moveTo(upperLeft.x, upperLeft.y);
        path.lineTo(upperRight.x, upperRight.y);
        path.lineTo(lowerRight.x, lowerRight.y);
        path.lineTo(lowerLeft.x, lowerLeft.y);
        path.closePath();

        return path;
    }

    public Path2D.Double getUserClip(int width, int height) {
        Point2D.Double upperLeft = new Point2D.Double(0, 0);
        Point2D.Double upperRight = new Point2D.Double(width, 0);
        Point2D.Double lowerLeft = new Point2D.Double(0, height);
        Point2D.Double lowerRight = new Point2D.Double(width, height);

        Point2D.Double userUpperLeft = new Point2D.Double();
        Point2D.Double userUpperRight = new Point2D.Double();
        Point2D.Double userLowerLeft = new Point2D.Double();
        Point2D.Double userLowerRight = new Point2D.Double();

        AffineTransform transform = getTransform(width / 2, height / 2);

        try {
            transform.inverseTransform(upperLeft, userUpperLeft);
            transform.inverseTransform(upperRight, userUpperRight);
            transform.inverseTransform(lowerLeft, userLowerLeft);
            transform.inverseTransform(lowerRight, userLowerRight);
        } catch (NoninvertibleTransformException e) {
            System.out.println("Transform not invertible");
        }

        Path2D.Double path = new Path2D.Double();
        path.moveTo(userUpperLeft.x, userUpperLeft.y);
        path.lineTo(userUpperRight.x, userUpperRight.y);
        path.lineTo(userLowerRight.x, userLowerRight.y);
        path.lineTo(userLowerLeft.x, userLowerLeft.y);
        path.closePath();

        return path;
    }
}
