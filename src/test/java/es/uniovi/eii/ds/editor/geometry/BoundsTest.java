package es.uniovi.eii.ds.editor.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BoundsTest {

    private Bounds bounds;
    private Point start;

    private void startAt(int x, int y) {
        start = new Point(x, y);
        bounds = new Bounds(start);
    }

    private void moveTo(int x, int y) {
        bounds.resizeTo(new Point(x, y));
    }

    // Checks that the bounds always represent the region from (60, 100) to
    // (180, 200), regardless of the direction of the resize.
    private void assertBounds() {
        assertEquals(new Point(60, 100), bounds.topLeft());
        assertEquals(new Point(180, 200), bounds.bottomRight());
        assertEquals(120, bounds.width());
        assertEquals(100, bounds.height());
    }

    @Test
    void initialBounds() {
        startAt(100, 100);
        assertEquals(start, bounds.topLeft());
        assertEquals(start, bounds.bottomRight());
    }

    @Test
    void resizeDownRight() {
        startAt(60, 100);
        moveTo(180, 200);
        assertBounds();
    }

    @Test
    void resizeDownLeft() {
        startAt(180, 200);
        moveTo(60, 100);
        assertBounds();
    }

    @Test
    void resizeUpLeft() {
        startAt(180, 200);
        moveTo(60, 100);
        assertBounds();
    }

    @Test 
    void resizeUpRight() {
        startAt(60, 200);
        moveTo(180, 100);
        assertBounds();
    }   
}
