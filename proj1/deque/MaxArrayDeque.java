package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{

    public Comparator<T> comparator;

    /** Constructor: creates a MaxArrayDeque with the given Comparator.
     *
     * @param c: comparator
     */
    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    /** Returns the maximum element in the deque as governed by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null.
     */
    public T max() {
        return maxHelper(comparator);
    }

    /** Returns the maximum element in the deque as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null.
     */
    public T max(Comparator<T> c) {
        return maxHelper(c);
    }

    /** Helper function to catch the max
     *
     */
    private T maxHelper(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        int maxDex = 0;
        int size = size();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (c.compare(item, get(maxDex)) > 0) {
                maxDex = i;
            }
        }
        return get(maxDex);
    }
}
