package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE

    /**Add and Remove test
     * Add selected numbers and remove, compare them from both lists
     */
    @Test
    public void testThreeAddThreeRemove() {
        /* 1. Initialize two different lists*/
        AListNoResizing<Integer> goodList = new AListNoResizing<>();
        BuggyAList<Integer> badList = new BuggyAList<>();

        goodList.addLast(4); goodList.addLast(5); goodList.addLast(6);
        badList.addLast(4); badList.addLast(5); badList.addLast(6);
        int expected, actual;

        for (int i = 0; i < 3; i++) {
            expected = goodList.removeLast();
            actual = badList.removeLast();
            assertEquals(expected, actual);
        }
    }

    /**Randomized test
     * Do randomized test by using random number
     */
    @Test
    public void randomizedTest() {
        /* 1. Initialize two different lists*/
        AListNoResizing<Integer> goodList = new AListNoResizing<>();
        BuggyAList<Integer> badList = new BuggyAList<>();

        int N = 500000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            switch (operationNumber) {
                /* addLast */
                case 0:
                    int randVal = StdRandom.uniform(0, 100);
                    goodList.addLast(randVal);
                    badList.addLast(randVal);
                    System.out.println("addLast(" + randVal + ")");
                    break;
                /* size */
                case 1:
                    int gSize = goodList.size();
                    int bSize = badList.size();
                    System.out.println("good list size: " + gSize);
                    System.out.println("bad list size: " + bSize);
                    break;
                /* getLast */
                case 2:
                    if (goodList.size() > 0) {
                        int glast = goodList.getLast();
                        System.out.println("good list last item: " + glast);
                    }
                    if (badList.size() > 0) {
                        int blast = badList.getLast();
                        System.out.println("bad list last item: " + blast);
                    }
                    break;
                /* removeLast */
                case 3:
                    if (goodList.size() > 0) {
                        int glast = goodList.removeLast();
                        System.out.println("removed good list last item: " + glast);
                    }
                    if (badList.size() > 0) {
                        int blast = badList.removeLast();
                        System.out.println("removed good list last item: " + blast);
                    }
                    break;
            }

        }
    }
}
