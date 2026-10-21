package es.uniovi.eii.ds.editor.core;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.uniovi.eii.ds.editor.figures.*;
import es.uniovi.eii.ds.editor.geometry.Point;

public class DrawingTest {

    private Drawing drawing;
    private Rectangle r1;
    private Circle c1;
    private Triangle t1;

    @BeforeEach 
    void setUp() {
        drawing = new Drawing();
        // Create some figures
        r1 = new Rectangle(new Point(10, 10), 100, 50);
        c1 = new Circle(new Point(200, 200), 30);
        t1 = new Triangle(new Point(300, 300), new Point(260, 380), new Point(340, 390));
        // Add them to the drawing
        drawing.addFigure(r1);
        drawing.addFigure(c1);
        drawing.addFigure(t1);
    }

    @Test 
    void testFindFigureAt() {
        assertSame(r1, drawing.findFigureAt(50,30));
        assertSame(c1, drawing.findFigureAt(202, 198));
        assertSame(t1, drawing.findFigureAt(260, 380));
        // Test a point that is not contained in any figure
        assertNull(drawing.findFigureAt(0, 0));
        assertNull(drawing.findFigureAt(231, 200));
        // Test overlapping figures: the last added figure should be found first
        Circle c2 = new Circle(new Point(40, 30), 20);
        drawing.addFigure(c2);
        assertSame(c2, drawing.findFigureAt(40, 30));
        c2.moveBy(60, 60);
        // Now the rectangle should be found at (40, 30)
        assertSame(r1, drawing.findFigureAt(40, 30));
    }

    @Test
    void testDraw() {
        var out = new StringWriter();
        drawing.draw(new PrintWriter(out, true));
        // The expected output should contain the drawing of all figures in the
        // reverser order they were added (the last added figure is drawn first)
        String expectedOutput = """
            Triangle: (300, 300), (260, 380), (340, 390)
            Circle: (200, 200), radius=30
            Rectangle: (10, 10), width=100, height=50
            """;
        assertEquals(expectedOutput, out.toString());
    }
}
