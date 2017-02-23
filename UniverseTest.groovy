package com.ladislav
/**
 * Created by Ladislav on 2/23/2017.
 */
class UniverseTest extends GroovyTestCase {

    void testElementExists() {
        final int n = 15;
        Universe universe = new Universe(n);

        assertTrue(universe.setElement(0));
        assertTrue(universe.setElement(5));
        assertTrue(universe.setElement(12));
        assertTrue(universe.setElement(14));

        assertTrue(universe.elementExists(0));
        assertTrue(universe.elementExists(5));
        assertTrue(universe.elementExists(12));
        assertTrue(universe.elementExists(14));

        assertFalse(universe.elementExists(1));
        assertFalse(universe.elementExists(2));
        assertFalse(universe.elementExists(13));
        assertFalse(universe.elementExists(10));

    }

    void testSetElement() {

        final int n = 15;
        int[] a = [2, 3, 4, 13];
        Universe universe = new Universe(n);

        assertTrue(universe.setElement(0));
        assertTrue(universe.setElement(5));
        assertTrue(universe.setElement(12));
        assertTrue(universe.setElement(14));
        assertEquals(4, universe.setElement(a));

        println universe.toString()
    }

    void testUnsetElement() {
        final int n = 15;
        Universe universe = new Universe(n);
        int[] a = [2, 3, 4, 13];

        universe.setElement(0);
        universe.setElement(5);
        universe.setElement(12);
        universe.setElement(14);
        universe.setElement(a);

        assertTrue universe.unsetElement(14);
        assertFalse universe.elementExists(14);
        assertEquals(4, universe.unsetElement(a));
    }

    // Groovy overloads ==, it callse equals() method
    void testEquals() {
        final int n = 8;
        Universe univ1 = new Universe(n); // univ = 92982372
        Universe univ2 = new Universe(n); // univ2 = 987373436

        assertTrue univ1 == univ1
        assertTrue univ1 == univ2
        assertTrue univ2 == univ1
        assertTrue univ2 == univ2

        univ1.setElement(2);
        univ2.setElement(3);

        assertFalse univ1 == univ2
        assertFalse univ2 == univ1

        univ1.setElement(3);
        univ2.setElement(2);

        assertTrue univ1 == univ2
        assertTrue univ2 == univ1
    }

    void testUnion() {
        final int n = 50;
        Universe univ1 = new Universe(n);
        Universe univ2 = new Universe (n);

        for(int i = 0; i < univ1.power(); i++) {
            if (i > 25) {
                univ1.setElement(i);
            } else {
                univ2.setElement(i);
            }
        }

        Universe union = univ1.union(univ2);

        for (int i = 0; i < univ1.power(); i++) {
            if (i > 25) {
                assertTrue univ1.elementExists(i)
                assertFalse univ2.elementExists(i)
            } else {
                assertFalse univ1.elementExists(i)
                assertTrue univ2.elementExists(i)
            }
            assertTrue union.elementExists(i)
        }

        union = univ1.union(univ1);

        for (int i = 0; i < n; i++) {
            if (i > 25) {
                assertTrue univ1.elementExists(i)
                assertTrue union.elementExists(i)
            } else {
                assertFalse univ1.elementExists(i)
                assertFalse union.elementExists(i)
            }
        }
    }

    void testIntersection() {

        final int n = 50;

        Universe univ1 = new Universe(n);
        Universe univ2 = new Universe (n);

        for(int i = 0; i < univ1.power(); i++) {
            if (i > 25) {
                univ1.setElement(i);
            }
            univ2.setElement(i);
        }
        Universe intersection = univ1.intersection(univ2);

        assertTrue intersection == univ1
        assertFalse intersection == univ2
    }

    void testComplement() {
        final int n = 50;

        Universe univ1 = new Universe(n);
        Universe univ2 = new Universe (n);

        for(int i = 0; i < univ1.power(); i++) {
            if (i > 25) {
                univ1.setElement(i);
            }
            univ2.setElement(i);
        }

        Universe complement = univ2.complement(univ1);

        assertTrue univ1.union(complement) == univ2
    }

    void testPower() {
        int n = 15;
        Universe universe = new Universe(n);
        assertEquals n, universe.power();
    }
}
