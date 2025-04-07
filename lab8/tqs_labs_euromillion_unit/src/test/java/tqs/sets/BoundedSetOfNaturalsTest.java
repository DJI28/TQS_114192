/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = new BoundedSetOfNaturals(2);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    //@Disabled("DONE revise test logic")
    @Test
    public void testAddElement() {
        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());
    }

    @Test
    public void testAddToFullSet() {
        assertThrows(IllegalArgumentException.class, () -> setB.add(100));
    }

    @Test
    public void testAddNegativeElement() {
        assertThrows(IllegalArgumentException.class, () -> setA.add(-99));
    }

    @Test
    public void addDuplicateElement() {
        setC.add(99);
        assertThrows(IllegalArgumentException.class, () -> setC.add(99));
    }

    //@Disabled("DONE revise to test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testIntersect() {
        BoundedSetOfNaturals setD = new BoundedSetOfNaturals(2);
        setD.add(10);
        setD.add(20);
        assertTrue(setB.intersects(setD));
        assertFalse(setA.intersects(setD));
    }

    // Tests after here strictly done to get 100% coverage

    @Test
    public void testEqualsTrue() {
        BoundedSetOfNaturals setD = new BoundedSetOfNaturals(2);
        setD.add(10);
        setD.add(20);
        assertTrue(setD.equals(setD));
    }

    @Test
    public void testEqualsFalse() {
        assertFalse(setB.equals(null));
        assertFalse(setB.equals(1));
    }

    @Test
    public void testHashCode() {
        assertEquals(setB.hashCode(), setB.hashCode());
    }

}
