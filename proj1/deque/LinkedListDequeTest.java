package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

//<editor-fold desc="Existing test">


    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }
//</editor-fold>


    /** T for getItem method:
     * 1. get an item from empty deque.
     * 2. get an item at invalid position.
     * 3. get an item with random index.
     * 4. get an item with random index and using recursion.
     */
    @Test
    public void getItemTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<String> lld2 = new LinkedListDeque<>();

        /* initialize the deque */
        lld1.addLast(1); lld1.addLast(2); lld1.addLast(3); lld1.addLast(4); lld1.addLast(5); lld1.addLast(6); lld1.addLast(7);
        int[] expectedArray = new int[]{1,2,3,4,5,6,7};

        /* randomized test: different cases of getting items */
        int N = 10000;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 4);
            int randomIndex;
            int expected;
            int actual;

            switch (operationNumber) {
                /* get from empty deque */
                case 0:
                    assertNull("The deque is empty", lld2.get(2));
                    break;
                /* get with invalid index */
                case 1:
                    assertNull("The deque doesn't have enough items", lld1.get(10));
                    break;
                /* get with a random valid index */
                case 2:
                    randomIndex = StdRandom.uniform(0, 7);
                    expected = expectedArray[randomIndex];
                    actual = lld1.get(randomIndex);
                    assertEquals(expected, actual);
                    break;
                /* get with recursion, a random valid index */
                case 3:
                    randomIndex = StdRandom.uniform(0, 7);
                    expected = expectedArray[randomIndex];
                    actual = lld1.getRecursive(randomIndex);
                    assertEquals(expected, actual);
                    break;
            }
        }
    }

    @Test
    public void iteratorTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        /* initialize the deque */
        lld1.addLast(1); lld1.addLast(2); lld1.addLast(3); lld1.addLast(4); lld1.addLast(5); lld1.addLast(6); lld1.addLast(7);
//        lld1.printDeque();

        for (int i: lld1) {
            System.out.println(i);
        }
    }

    @Test
    public void equalTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld3 = new LinkedListDeque<>();

        /* initialize the deque */
        lld1.addLast(1); lld1.addLast(2); lld1.addLast(3); lld1.addLast(4); lld1.addLast(5); lld1.addLast(6); lld1.addLast(7);
        lld2.addLast(1); lld2.addLast(2); lld2.addLast(3); lld2.addLast(4); lld2.addLast(5); lld2.addLast(6); lld2.addLast(7);
        lld3.addLast(1); lld3.addLast(2); lld3.addLast(3);

        System.out.println(lld1.equals(lld2));
        System.out.println(lld2.equals(lld3));
        System.out.println(lld1);

    }
}
