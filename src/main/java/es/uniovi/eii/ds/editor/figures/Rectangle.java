package es.uniovi.eii.ds.editor.figures;

import java.io.PrintWriter;

import es.uniovi.eii.ds.editor.core.Figure;
import es.uniovi.eii.ds.editor.geometry.Point;

public class Rectangle implements Figure {

    private Point corner;
    private int width, height;

    public Rectangle(Point corner, int width, int height) {
        this.corner = corner;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(PrintWriter out) {
        out.printf("Rectangle: (%d, %d), width=%d, height=%d%n", 
            corner.x(), corner.y(), width, height);
    }

    @Override
    public boolean contains(int x, int y) {
        return x >= corner.x() && x <= corner.x() + width && y >= corner.y() && y <= corner.y() + height;
    }

    @Override
    public void moveBy(int dx, int dy) {
        corner = new Point(corner.x() + dx, corner.y() + dy);
    }
}
