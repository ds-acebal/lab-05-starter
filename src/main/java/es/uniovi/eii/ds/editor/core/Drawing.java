package es.uniovi.eii.ds.editor.core;

import java.io.PrintWriter;
import java.util.*;

public class Drawing  {

	private List<Figure> figures = new ArrayList<>();
	
	// Adds a figure in the front of the others (so that it will be
	// displayed first when drawing).
	public void addFigure(Figure figure) {
		figures.add(0, figure);
	}

	public void draw(PrintWriter out) {
		for (Figure figure : figures) {
			figure.draw(out);
		}
	}

	public Figure findFigureAt(int x, int y) {
		for (Figure figure : figures) {
			if (figure.contains(x, y)) {
				return figure;
			}
		}
		return null;
	}
}
