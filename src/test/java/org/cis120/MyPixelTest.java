package org.cis120;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Use this file to test your implementation of Pixel.
 * 
 * We will manually grade this file to give you feedback
 * about the completeness of your test cases.
 */

public class MyPixelTest {

    /*
     * Remember, UNIT tests should ideally have one point of failure. Below we
     * give you two examples of unit tests for the Pixel constructor, one that
     * takes in three ints as arguments and one that takes in an array. We use
     * the getRed(), getGreen(), and getBlue() methods to check that the values
     * were set correctly. These two tests do not comprehensively test all of
     * Pixel so you must add your own.
     * 
     * You might want to look into assertEquals, assertTrue, assertFalse, and
     * assertArrayEquals at the following:
     * http://junit.sourceforge.net/javadoc/org/junit/Assert.html
     *
     * Note, if you want to add global variables so that you can reuse Pixels
     * in multiple tests, feel free to do so.
     */

    @Test
    public void testConstructInBounds() {
        Pixel p = new Pixel(40, 50, 60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructRedOutOfLeftBounds() {
        Pixel p = new Pixel(-1, 50, 60);
        assertEquals(0, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructRedOutOfRightBounds() {
        Pixel p = new Pixel(260, 50, 60);
        assertEquals(255, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructGreenOutOfLeftBounds() {
        Pixel p = new Pixel(40, -50, 60);
        assertEquals(40, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructGreenOutOfRightBounds() {
        Pixel p = new Pixel(40, 280, 60);
        assertEquals(40, p.getRed());
        assertEquals(255, p.getGreen());
        assertEquals(60, p.getBlue());
    }

    @Test
    public void testConstructBlueOutOfLeftBounds() {
        Pixel p = new Pixel(40, 50, -60);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructBlueOutOfRightBounds() {
        Pixel p = new Pixel(40, 50, 360);
        assertEquals(40, p.getRed());
        assertEquals(50, p.getGreen());
        assertEquals(255, p.getBlue());
    }

    @Test
    public void testConstructArrayLongerThan3() {
        int[] arr = { 10, 20, 30, 40 };
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(30, p.getBlue());
    }

    @Test
    public void testConstructArrayShorterThan3() {
        int[] arr = { 10, 20};
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(20, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructArrayLengthOf0() {
        int[] arr = {};
        Pixel p = new Pixel(arr);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructNullArray() {
        Pixel p = new Pixel(null);
        assertEquals(0, p.getRed());
        assertEquals(0, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testConstructArrayOutOfBound() {
        int[] arr = { 10, 270, -30};
        Pixel p = new Pixel(arr);
        assertEquals(10, p.getRed());
        assertEquals(255, p.getGreen());
        assertEquals(0, p.getBlue());
    }

    /* ADD YOUR OWN TESTS BELOW */
    @Test
    public void testGetRedOutOfLeftBound() {
        Pixel p = new Pixel(-40, 0, 0);
        assertEquals(0, p.getRed());
    }

    @Test
    public void testGetRedOutOfRightBound() {
        Pixel p = new Pixel(260, 0, 0);
        assertEquals(255, p.getRed());
    }

    @Test
    public void testGetRedInBound() {
        Pixel p = new Pixel(60, 0, 0);
        assertEquals(60, p.getRed());
    }

    @Test
    public void testGetGreenOutOfLeftBound() {
        Pixel p = new Pixel(0, -10, 0);
        assertEquals(0, p.getGreen());
    }

    @Test
    public void testGetGreenOutOfRightBound() {
        Pixel p = new Pixel(0, 270, 0);
        assertEquals(255, p.getGreen());
    }

    @Test
    public void testGetGreenInBound() {
        Pixel p = new Pixel(0, 10, 0);
        assertEquals(10, p.getGreen());
    }

    @Test
    public void testGetBlueOutOfLeftBound() {
        Pixel p = new Pixel(0, 0, -40);
        assertEquals(0, p.getBlue());
    }

    @Test
    public void testGetBlueOutOfRightBound() {
        Pixel p = new Pixel(0, 0, 260);
        assertEquals(255, p.getBlue());
    }

    @Test
    public void testGetBlueInBound() {
        Pixel p = new Pixel(0, 0, 40);
        assertEquals(40, p.getBlue());
    }

    @Test
    public void testGetComponents() {
        int[] arr = {10, 20, 30};
        Pixel p = new Pixel(arr);
        assertArrayEquals(arr, p.getComponents());
    }

    @Test
    public void testDistance() {
        int[] arr1 = {10, 10, 10};
        int[] arr2 = {0, 20, 20};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        assertEquals(30, p1.distance(p2));
    }

    @Test
    public void testDistanceOfSamePixel() {
        int[] arr1 = {10, 10, 10};
        int[] arr2 = {10, 10, 10};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        assertEquals(0, p1.distance(p2));
    }

    @Test
    public void testDistanceToNull() {
        int[] arr = {10, 10, 10};
        Pixel p = new Pixel(arr);
        assertEquals(-1, p.distance(null));
    }

    @Test
    public void testToString() {
        int[] arr = {10, 10, 10};
        Pixel p = new Pixel(arr);
        assertEquals("(10, 10, 10)", p.toString());
    }

    @Test
    public void testEqualsEqualPixels() {
        int[] arr1 = {10, 10, 10};
        int[] arr2 = {10, 10, 10};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void testEqualsNonEqualPixels() {
        int[] arr1 = {10, 10, 10};
        int[] arr2 = {0, 10, 10};
        Pixel p1 = new Pixel(arr1);
        Pixel p2 = new Pixel(arr2);
        assertFalse(p1.equals(p2));
    }
}
