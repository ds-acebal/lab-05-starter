package es.uniovi.eii.ds.editor.core;

import java.io.PrintWriter;

public interface Figure {
    void draw(PrintWriter out);
    boolean contains(int x, int y);
    void moveBy(int dx, int dy);
}
