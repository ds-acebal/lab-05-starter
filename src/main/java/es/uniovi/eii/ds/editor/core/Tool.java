package es.uniovi.eii.ds.editor.core;

public interface Tool {
    void pressed(int x, int y);
    void moved(int x, int y);
    void released(int x, int y);
    // The name of the tool, used to identify it in the UI
    String name(); 
    //  Restarts the tool to its initial state
    void reset();
}
