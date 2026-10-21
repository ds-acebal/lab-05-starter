package es.uniovi.eii.ds.editor.core;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;

import es.uniovi.eii.ds.editor.geometry.Point;
import es.uniovi.eii.ds.editor.figures.*;

class EditorTest {

    private static final String SELECTION = "selection";
    private static final String RECTANGLE = "rectangle";
    private static final String CIRCLE = "circle";
    private static final String TRIANGLE = "triangle";

    private Editor editor;
    private StringWriter output;

    @BeforeEach
    void setUp() {
        output = new StringWriter();
        editor = new Editor(new PrintWriter(output, true));
    }

    // * Helper methods

    private String drawing() {
        var out = new StringWriter();
        editor.drawing().draw(new PrintWriter(out, true));
        return out.toString().stripTrailing();
    }

    private String draw(Figure figure) {
        var out = new StringWriter();
        figure.draw(new PrintWriter(out, true));
        return out.toString().stripTrailing();
    }

    private void assertToolActive(String toolName) {
        editor.draw();
        assertTrue(output.toString().stripTrailing().endsWith("Current tool: " + toolName), 
            () -> "Actual output: [" + output + "]"
        );
        // Clear the output buffer for the next assertion
        output.getBuffer().setLength(0);
    }

    // * Tests

    @Test
    void initialState() {
        assertToolActive("Selection");
        assertEquals("", drawing());
        editor.draw();
        assertEquals("""
            ---
            Current tool: Selection
            """, output.toString());
    }

    @Test
    void createRectangle() {
        editor.toolButtonPressed(RECTANGLE);
        editor.mousePressed(100, 100);
        // No figure has been created yet
        assertEquals("", drawing());
        editor.mouseReleased(300, 200);
        // After releasing the mouse, a rectangle must have been created
        Rectangle rectangle = new Rectangle(new Point(100, 100), 200, 100);
        assertEquals(draw(rectangle), drawing());
        // Now the selection tool is active
        assertToolActive("Selection");
    }

    @Test
    void cancelRectangleCreation() {
        editor.toolButtonPressed(RECTANGLE);
        editor.mousePressed(100, 100);
        // Now the user selects another tool
        editor.toolButtonPressed(CIRCLE);
        // And comes back to the rectangle tool
        editor.toolButtonPressed(RECTANGLE);
        editor.mouseReleased(100, 100);
        // No rectangle has been created
        assertEquals("", drawing());
    }

    @Test
    void createCircle() {
        editor.toolButtonPressed(CIRCLE);
        editor.mousePressed(400, 400);
        // No figure has been created yet
        assertEquals("", drawing());
        editor.mouseReleased(600, 600);
        // After releasing the mouse, a circle must have been created
        Circle circle = new Circle(new Point(500, 500), 100);
        assertEquals(draw(circle), drawing());
        // Now the selection tool is active
        assertToolActive("Selection");
    }

    @Test
    void createCircleInRectangularArea() {
        editor.toolButtonPressed(CIRCLE);
        editor.mousePressed(400, 400);
        editor.mouseReleased(700, 600);
        // The largest circle that fits in the rectangular area, centred both horizontally and vertically
        Circle circle = new Circle(new Point(550, 500), 100);
        assertEquals(draw(circle), drawing());
    }

    @Test
    void cancelCircleCreation() {
        editor.toolButtonPressed(CIRCLE);
        editor.mousePressed(400, 400);
        // Now the user selects another tool
        editor.toolButtonPressed(RECTANGLE);
        editor.toolButtonPressed(CIRCLE);
        editor.mouseReleased(600, 600);
        // No circle has been created
        assertEquals("", drawing());
    }

    @Test
    void createTriangle() {
        editor.toolButtonPressed(TRIANGLE);
        editor.mousePressed(15, 10);
        editor.mousePressed(5, 20);
        // No figure has been created yet
        assertEquals("", drawing());
        editor.mousePressed(25, 20);
        // After the third click, a triangle must have been created
        Triangle triangle = new Triangle(new Point(15, 10), new Point(5, 20), new Point(25, 20));
        assertEquals(draw(triangle), drawing());
        // Now the selection tool is active
        assertToolActive("Selection");
    }

    @Test
    void move() {
        Rectangle rectangle = new Rectangle(new Point(100, 100), 200, 100);
        editor.drawing().addFigure(rectangle);
        editor.toolButtonPressed(SELECTION);
        editor.mousePressed(125, 150);
        // No figure has been moved yet
        assertEquals(draw(rectangle), drawing());
        editor.mouseMoved(200, 200);
        // After moving the mouse (dragging the figure), the rectangle must have been moved
        Rectangle movedRectangle = new Rectangle(new Point(175, 150), 200, 100);
        assertEquals(draw(movedRectangle), drawing());
        // Now the mouse is released in the new position
        editor.mouseReleased(250, 225);
        movedRectangle = new Rectangle(new Point(225, 175), 200, 100);
        assertEquals(draw(movedRectangle), drawing());
        // After releasing the mouse, subsequent moves must not affect the rectangle
        editor.mouseMoved(300, 300);
        assertEquals(draw(movedRectangle), drawing());
    }

    @Test
    void cancelTriangleCreation() {
        editor.toolButtonPressed(TRIANGLE);
        editor.mousePressed(2, 1);
        editor.mousePressed(1, 2);
        // Now the user selects another tool
        editor.toolButtonPressed(RECTANGLE);
        editor.toolButtonPressed(TRIANGLE);
        // This is now the first click of a new triangle!
        editor.mousePressed(3, 2);
        // No triangle has been created yet
        assertEquals("", drawing());
        // Other two clicks
        editor.mousePressed(1, 2);
        editor.mousePressed(3, 2);
        // Now a triangle must have been created
        Triangle triangle = new Triangle(new Point(3, 2), new Point(1, 2), new Point(3, 2));
        assertEquals(draw(triangle), drawing());
    }

    @Test
    void overlappingFigures() {
        Rectangle rectangle = new Rectangle(new Point(100, 100), 200, 100);
        editor.drawing().addFigure(rectangle);
        assertSame(rectangle, editor.drawing().findFigureAt(160, 140));
        Circle circle = new Circle(new Point(200, 150), 80);
        editor.drawing().addFigure(circle);
        // The circle must be found at that position, because it is on top of the rectangle
        // (it was added later)
        assertSame(circle, editor.drawing().findFigureAt(160, 140));
        // To make sure, let's move the circle
        editor.toolButtonPressed(SELECTION);
        editor.mousePressed(160, 140);
        editor.mouseReleased(220, 240);
        // Now, if we click in the same point, the rectangle must be found
        assertSame(rectangle, editor.drawing().findFigureAt(160, 140));
    }

    @Test
    void releaseWithoutPress() {
        // Rectangle creation tool
        editor.toolButtonPressed(RECTANGLE);
        editor.mouseReleased(150, 150);
        // No figure has been created
        assertEquals("", drawing());
        // And the rectangle tool is still active
        assertToolActive("Rectangle");
        
        // Circle creation tool
        editor.toolButtonPressed(CIRCLE);
        editor.mouseReleased(150, 150);
        // No figure has been created
        assertEquals("", drawing());
        // And the circle tool is still active
        assertToolActive("Circle");
    }

    @Test
    void draw() {
        Rectangle rectangle = new Rectangle(new Point(100, 100), 200, 100);
        Circle circle = new Circle(new Point(500, 500), 100);
        Triangle triangle = new Triangle(
                new Point(15, 10), new Point(5, 20), new Point(25, 20));

        editor.drawing().addFigure(rectangle);
        editor.drawing().addFigure(circle);
        editor.drawing().addFigure(triangle);

        editor.draw();

        String expected = """
            Triangle: (15, 10), (5, 20), (25, 20)
            Circle: (500, 500), radius=100
            Rectangle: (100, 100), width=200, height=100
            ---
            Current tool: Selection
            """;

        assertEquals(expected, output.toString());
    }
}
