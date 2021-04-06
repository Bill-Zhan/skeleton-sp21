package deque;

import afu.org.checkerframework.checker.oigj.qual.O;
import java.util.Iterator;
import java.util.ArrayList;

/**Invariants:
 * 1. sentinel.next is head, sentinel.prev is tail.
 * 2.
 *
 */

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private ListNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ListNode();
        /* circular sentinel */
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

        size = 0;
    }

    /**
     * ListNode nested class.
     */
    public class ListNode {
        public T item;
        public ListNode prev;
        public ListNode next;


        public ListNode() {
        }

        public ListNode(T x) {
            item = x;
        }
    }



    /**
     * Add an item when the list is empty: size == 0
     */
    private void addEmpty(ListNode newNode) {
        /* update head */
        sentinel.next = newNode;
        newNode.prev = sentinel;
        /* update tail */
        sentinel.prev = newNode;
        newNode.next = sentinel;
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
        ListNode newNode = new ListNode(x);

        if (isEmpty()) {
            addEmpty(newNode);
        } else {
            /* need to update head only if there is anything in the list */
            ListNode head = sentinel.next;  // current head
            // update sentinel
            sentinel.next = newNode;
            newNode.prev = sentinel;
            // update current head
            newNode.next = head;
            head.prev = newNode;
        }
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
        ListNode newNode = new ListNode(x);

        if (isEmpty()) {
            addEmpty(newNode);
        } else {
            /* need to update tail only if there is anything in the list */
            ListNode tail = sentinel.prev;  // current tail
            /* update sentinel */
            sentinel.prev = newNode;
            newNode.next = sentinel;
            /* update current tail */
            newNode.prev = tail;
            tail.next = newNode;
        }
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
        ListNode curr = sentinel.next;
        while (curr != sentinel) {
            System.out.print(curr.item + " ");
            curr = curr.next;
        }
        System.out.print("\n");
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            T ans = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return ans;
        }
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            T ans = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return ans;
        }
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    @Override
    public T get(int index) {
        if (index > size - 1) {
            return null;
        } else {
            ListNode curr = sentinel.next;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
            return curr.item;
        }
    }

    /**
     * Using recursion to get item
     */
    private ListNode getNode(ListNode head, int index) {
        /* base case: when index is 0, return current node*/
        if (index == 0) {
            return head;
        }
        return getNode(head.next, index-1);
    }

    public T getRecursive(int index) {
        if (index > size - 1) { return null; }
        ListNode node = getNode(sentinel.next, index);
        return node.item;
    }

    /**
     * The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator.
     */
    private class LinkedListDequeIterator implements Iterator<T> {
        private ListNode currNode;

        public LinkedListDequeIterator() {
            currNode = sentinel.next;
        }

        public boolean hasNext() {
            return currNode != sentinel;
        }

        public T next() {
            T returnItem = currNode.item;
            currNode = currNode.next;
            return returnItem;
        }

    }
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents (as governed by the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this. Read here for more information)
     */
    @Override
    public boolean equals(Object o){
        /* corner cases */
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        LinkedListDeque other = (LinkedListDeque) o;
        if (this.size() != other.size()) {
            return false;
        }

        /* normal case */
        int index = 0;
        for (T item : this) {
            if (item != other.get(index)) {
                return false;
            }
            index += 1;
        }
        return true;
    }

    /** Override toString method to show the data
     */
    @Override
    public String toString() {
        ArrayList<String> listOfItem = new ArrayList<>();
        for (T item: this) {
            listOfItem.add(item.toString());
        }
        String returnStr = String.join(" <=> ", listOfItem);
        return returnStr;
    }
}
