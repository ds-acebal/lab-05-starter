package es.uniovi.eii.ds.editor.figures;

import java.io.PrintWriter;

import es.uniovi.eii.ds.editor.core.Figure;
import es.uniovi.eii.ds.editor.geometry.Point;

public class Circle implements Figure {

    private Point center;
    private int radius;

    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void draw(PrintWriter out) {
        out.printf("Circle: (%d, %d), radius=%d%n", center.x(), center.y(), radius);
    }

    @Override
    public boolean contains(int x, int y) {
        return Math.pow((double) x - center.x(), 2) + Math.pow((double) y - center.y(), 2) <= Math.pow(radius, 2);
    }

    @Override
    public void moveBy(int dx, int dy) {
        center = new Point(center.x() + dx, center.y() + dy);
    }
}
