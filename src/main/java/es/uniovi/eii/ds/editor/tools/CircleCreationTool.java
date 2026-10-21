package es.uniovi.eii.ds.editor.tools;

import es.uniovi.eii.ds.editor.core.*;
import es.uniovi.eii.ds.editor.geometry.*;
import es.uniovi.eii.ds.editor.figures.Circle;

public class CircleCreationTool extends AbstractCreationTool {

    public CircleCreationTool(Editor editor) {
        super(editor);
    }

    @Override
    protected Figure createFigure(Bounds bounds) {
        Point center = new Point(bounds.topLeft().x() + bounds.width() / 2, 
                                 bounds.topLeft().y() + bounds.height() / 2);
        int radius = Math.min(bounds.width(), bounds.height()) / 2;
        return new Circle(center, radius);
    }

    @Override
    public String name() {
        return "Circle";
    }
}
