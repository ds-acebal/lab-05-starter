package es.uniovi.eii.ds.editor.tools;

import es.uniovi.eii.ds.editor.core.*;
import es.uniovi.eii.ds.editor.geometry.*;

public abstract class AbstractCreationTool implements Tool {

    private Editor editor;
    private Bounds bounds;

    public AbstractCreationTool(Editor editor) {
        this.editor = editor;
    }

    @Override 
    public void reset() {
        bounds = null;
    }

    @Override
    public void pressed(int x, int y) {
        bounds = new Bounds(new Point(x, y));
    }

    @Override 
    public void moved(int x, int y) {
        if (bounds != null) {
            bounds.resizeTo(new Point(x, y));
        }
    }

    @Override
    public void released(int x, int y) {
        if (bounds == null)
            return;
        bounds.resizeTo(new Point(x, y));
        Figure newFigure = createFigure(bounds);
        editor.drawing().addFigure(newFigure);
        editor.toolDone();
    }

    protected abstract Figure createFigure(Bounds bounds);
}
