package deque;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Invariants:
 * Array has number of size items.
 * front and back is in rang [0, items.length - 1]
 * First item is at index:  (front + 1) % length.
 * Last item is at index: (back - 1 + length) % length.
 * size = (back - front + length) % length - 1.
 */
public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] items;
    private int size;
    private int front;
    private int back;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        /* insert from the middle*/
        front = items.length / 2;
        back = items.length / 2 + 1;
    }

    /**
     * Resize when:
     * 1.  reaches -1
     * 2. back reaches length
     */
    private void resize(int capacity) {
        /* create a new array */
        T[] newArray = (T[]) new Object[capacity];
        int startIndex = (capacity / 2) - (size / 2);
        int leftLen = front + 1;  // left part length: 0 to back
        int rightLen = items.length - leftLen; // right part length: front to length - 1
        /* copy right part first, then left part, to maintain the order */
        System.arraycopy(items, back, newArray, startIndex, rightLen);  // if the array is in proper order, nothing will happen here, just copy the left part
        System.arraycopy(items, 0, newArray, startIndex + rightLen, leftLen);  // the new position for the left part starting after the copied right part
        items = newArray;

        /* update front and back index */
        front = startIndex - 1;
        back = front + size + 1;
    }

    /**
     * Adds an item of type T to the front of the deque.
     * You can assume that item is never null.
     *
     * add operations must not involve any looping or recursion.
     * A single such operation must take “constant time”,
     * i.e. execution time should not depend on the size of the deque.
     *
     */
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }

        /* simply add item to the front index */
        items[front] = x;
        front = (front - 1 + items.length) % items.length;
        size += 1;
    }

    /**
     * Adds an x of type T to the back of the deque.
     * You can assume that x is never null.
     *
     * add operations must not involve any looping or recursion.
     * A single such operation must take “constant time”,
     * i.e. execution time should not depend on the size of the deque.
     *
     */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }

        /* simply add item to the back index */
        items[back] = x;
        back = (back + 1) % items.length;
        size += 1;
    }

    /**
     * Return if the deque is empty.
     */
//    @Override
//    public boolean isEmpty() {
//        return size() == 0;
//    }

    /**
     *  Returns the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        for (int i = 1; i <= size; i++) {
            int index = (front + i) % items.length;
            System.out.print(items[index]+ " ");
        }
        System.out.print("\n");
    }

    /** Removes and returns the item at the front of the deque.
     * Instead of using index tricks, we will set removed item to null.
     *
     * A single such operation must take “constant time”.
     *
     * @return:
     *      if not item: null
     *      if there is the item: T
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            int l = items.length;
            /* get the first item and set the box to null */
            T first = items[(front + 1) % l];
            items[(front + 1) % l] = null;
            /* update front index and size*/
            front = (front + 1) % l;
            size -= 1;

            return first;
        }
    }

    /** Removes and returns the item at the back of the deque.
     *
     * @return:
     *      <null>: if not such item exists
     *      <T>: the target item
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            int l = items.length;
            /* get the last item and set the box to null */
            T last = items[(back - 1 + l) % l];
            items[(back - 1 + l) % l] = null;
            /* update back index and size */
            back = (back - 1 + l) % l;
            size -= 1;
            return last;
        }
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *
     * @return:
     *      <null>: if not such item exists
     *      <T>: the target item
     */
    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        } else {
            int targetIndex = (front + index + 1) % items.length;
            return items[targetIndex];
        }
    }



    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     */
    private class ArrayDequeIterator implements Iterator<T> {
        private int currPos;

        public ArrayDequeIterator() {
            currPos = 0;
        }

        public boolean hasNext() {
            return currPos < size;
        }

        public T next() {
            T returnItem = get(currPos);
            currPos += 1;
            return returnItem;
        }

    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents (as governed by the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this. Read here for more information)
     */
//    public boolean equals(Object o){
//
//    }

    /** Override toString method to show the data
     */
    @Override
    public String toString() {
        ArrayList<String> listOfItem = new ArrayList<>();
        for (T item: this) {
            listOfItem.add(item.toString());
        }
        String returnStr = String.join(", ", listOfItem);
        return "[" + returnStr + "]";
    }

}
