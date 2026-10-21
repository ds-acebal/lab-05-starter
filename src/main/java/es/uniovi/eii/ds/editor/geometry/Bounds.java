package es.uniovi.eii.ds.editor.geometry;

/*
    Represents a rectangular region in the drawing.
    
    It is usually created as a result of a mouse drag operation. When the mouse
    button is presed, an empty area (0 ✕ 0) is created at that position. As the
    mouse is dragged in any direction, the region is resized to include the current 
    mouse position.

    It allows to get the top-left and bottom-right points of the region (the min
    and max points in a screen coordinate system, where X increases to the right
    and Y increases downward) independently of the direction of the drag mouse
    movement.
 */
public class Bounds {

    private Point start, end;

    public Bounds(Point start) {
        this.start = start;
        this.end = start;
    }

    public void resizeTo(Point end) {
        this.end = end;
    }

    public Point topLeft() {
        return new Point(Math.min(start.x(), end.x()), Math.min(start.y(), end.y()));
    }

    public Point bottomRight() {
        return new Point(Math.max(start.x(), end.x()), Math.max(start.y(), end.y()));
    }

    public int width() {
        return bottomRight().x() - topLeft().x();
    }

    public int height() {
        return bottomRight().y() - topLeft().y();
    }

    @Override 
    public String toString() {
        return "Bounds( " + topLeft() + ", " + bottomRight() + " )";
    }
}
