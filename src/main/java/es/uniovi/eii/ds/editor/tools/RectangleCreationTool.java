package es.uniovi.eii.ds.editor.tools;

import es.uniovi.eii.ds.editor.core.*;
import es.uniovi.eii.ds.editor.geometry.Bounds;
import es.uniovi.eii.ds.editor.figures.Rectangle;

public class RectangleCreationTool extends AbstractCreationTool {

    public RectangleCreationTool(Editor editor) {
        super(editor);
    }

    @Override
    protected Figure createFigure(Bounds bounds) {
        return new Rectangle(bounds.topLeft(), bounds.width(), bounds.height());
    }

    @Override
    public String name() {
        return "Rectangle";
    }
}
