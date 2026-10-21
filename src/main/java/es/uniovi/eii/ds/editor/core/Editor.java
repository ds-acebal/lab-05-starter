package es.uniovi.eii.ds.editor.core;

import java.io.PrintWriter;
import java.util.*;

import es.uniovi.eii.ds.editor.tools.*;

/*
    This class is the main entry point of the editor. It represents the application
    controller and is responsible for managing the drawing, the tools, and other
    editing functionality.

    In a real graphical application, it would receive events from the user interface,
	such as mouse movements and toolbar button presses, and delegate the corresponding
	behaviour to the appropriate objects.

    In this exercise, the graphical user interface is replaced by a simple console
	application that simulates those events.
*/
public class Editor {

	private Drawing drawing;
	private PrintWriter out;

	private Map<String, Tool> tools = new HashMap<>();
	private Tool currentTool;
	private Tool defaultTool;

	public Editor(PrintWriter out) {
		drawing = new Drawing();
		this.out = out;
		createTools();
		setDefaultTool();
		currentTool = defaultTool;
	}

	// * Tool methods

	protected void addTool(String name, Tool tool) {
		tools.put(name, tool);
	}

	protected void setDefaultTool() {
		defaultTool = tools.get("selection");
	}
	
	protected void createTools() {
		addTool("selection", new SelectionTool(this));
		// Creation tools
		addTool("rectangle", new RectangleCreationTool(this));
		addTool("circle", new CircleCreationTool(this));
		addTool("triangle", new TriangleCreationTool(this));
	}

	public void toolDone() {
		currentTool.reset();
		currentTool = defaultTool;
	}

	// * Drawing methods
	
	public void draw() {
		drawing.draw(out);
		out.println("---");
		out.println("Current tool: " + currentTool.name());		
	}

	public Drawing drawing() {
		return drawing;
	}

	// * Undo/Redo

	public void undo() {
		// ? Implement undo...
	}

	public void redo() {
		// ? Implement redo...
	}
	
    // * UI methods

	public void toolButtonPressed(String toolName) {
		currentTool.reset();
		currentTool = tools.get(toolName);
	}

	public void mousePressed(int x, int y) {
		currentTool.pressed(x, y);
	}

	public void mouseMoved(int x, int y) {
		currentTool.moved(x, y);
	}

	public void mouseReleased(int x, int y) {
		currentTool.released(x, y);
	}
}
