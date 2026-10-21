package es.uniovi.eii.ds.editor.tools;

import es.uniovi.eii.ds.editor.core.*;
import es.uniovi.eii.ds.editor.geometry.Point;
import es.uniovi.eii.ds.editor.figures.Triangle;

public class TriangleCreationTool implements Tool {

    private Editor editor;
    private int numberOfVertices;
    private Point[] vertices = new Point[3];

    public TriangleCreationTool(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void pressed(int x, int y) {
        vertices[numberOfVertices++] = new Point(x, y);
        if (numberOfVertices == 3) {
            Figure triangle = new Triangle(vertices[0], vertices[1], vertices[2]);
            editor.drawing().addFigure(triangle);
            editor.toolDone();
        }
    }

    @Override
    public void moved(int x, int y) {
        // It has no effect during the creation of a triangle, so we can ignore it.
    }

    @Override
    public void released(int x, int y) {
        // It has no effect during the creation of a triangle, so we can ignore it.
    }

    @Override
    public String name() {
        return "Triangle";
    }

    @Override
    public void reset() {
        numberOfVertices = 0;
        vertices = new Point[3];
    }
}