// HW 10 - Christmas Tree Ornament
// Base class for all ornaments.
// You should not need to change this class.
// William Ames Fall 2011

import java.awt.Point;
import java.awt.Graphics;

abstract public class Ornament {
    protected static final Point TOP = new Point(87, 20), // coordinates of the tree triangle
            LEFT = new Point(12, 270), RIGHT = new Point(162, 270);

    protected int x, y; // location of ornament

    public Ornament() {
        Point location = randomPointInTriangle(TOP, LEFT, RIGHT);
        x = (int) location.getX();
        y = (int) location.getY();
    }

    abstract public void draw(Graphics g);

    // Select a random point inside of a triangle
    // See http://cgafaq.info/wiki/Random_Point_In_Triangle
    private Point randomPointInTriangle(Point pA, Point pB, Point pC) {
        double a = Math.random();
        double b = Math.random();
        if (a + b > 1) {
            a = 1 - a;
            b = 1 - b;
        }
        double c = 1 - a - b;
        return new Point((int) (a * pA.getX() + b * pB.getX() + c * pC.getX()),
                (int) (a * pA.getY() + b * pB.getY() + c * pC.getY()));
    }
}