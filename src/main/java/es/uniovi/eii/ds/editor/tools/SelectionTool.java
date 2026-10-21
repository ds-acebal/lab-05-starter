package es.uniovi.eii.ds.editor.tools;

import es.uniovi.eii.ds.editor.core.*;
import es.uniovi.eii.ds.editor.geometry.Point;

public class SelectionTool implements Tool {

    private Editor editor;
    private Figure figure;
    private Point lastPosition;

    public SelectionTool(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void pressed(int x, int y) {
        figure = editor.drawing().findFigureAt(x, y);
        lastPosition = new Point(x, y);
    }

    @Override
    public void moved(int x, int y) {
        moveTo(x, y);
    }

    @Override
    public void released(int x, int y) {
        moveTo(x, y);
        editor.toolDone();
    }

    private void moveTo(int x, int y) {
        if (figure == null)
            return;
        figure.moveBy(x - lastPosition.x(), y - lastPosition.y());
        lastPosition = new Point(x, y);
    }

    @Override
    public String name() {
        return "Selection";
    }

    @Override
    public void reset() {
        figure = null;
        lastPosition = null;
    }
}
