package es.uniovi.eii.ds.editor.figures;

import java.io.PrintWriter;

import es.uniovi.eii.ds.editor.core.Figure;
import es.uniovi.eii.ds.editor.geometry.Point;

public class Triangle implements Figure {

    private Point v1, v2, v3;

    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        this.v1 = vertex1;
        this.v2 = vertex2;
        this.v3 = vertex3;
    }

    @Override
    public void draw(PrintWriter out) {
        out.printf("Triangle: (%d, %d), (%d, %d), (%d, %d)%n", 
                v1.x(), v1.y(), v2.x(), v2.y(), v3.x(), v3.y());
    }

    @Override
    public boolean contains(int x, int y) {
        Point point = new Point(x, y);
        return point.equals(v1) || point.equals(v2) || point.equals(v3);
    }

    @Override
    public void moveBy(int dx, int dy) {
        v1 = new Point(v1.x() + dx, v1.y() + dy);
        v2 = new Point(v2.x() + dx, v2.y() + dy);
        v3 = new Point(v3.x() + dx, v3.y() + dy);
    }
}
