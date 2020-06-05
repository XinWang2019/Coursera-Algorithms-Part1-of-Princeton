/* *****************************************************************************
 *  Name: Xin
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int queuesize;

    private class Node {
        Item item = null;
        Node next = null;
        Node prev = null;    // the last item relative to this position
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        queuesize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return queuesize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("The item added should not be null!");

        Node oldfirst = first;

        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) {
            oldfirst.prev = first;
        }
        if (last == null) last = first;
        queuesize += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("The item added should not be null!");

        Node oldlast = last;

        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (oldlast != null) {
            oldlast.next = last;
        }
        if (first == null) first = last;
        queuesize += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) throw new NoSuchElementException(
                "The deque is empty! No element can be returned!");

        Node oldfirst = first;

        if (queuesize == 1) last = null;
        first = first.next;
        if (first != null) first.prev = null;
        queuesize -= 1;
        return oldfirst.item;
    }

    // remove and return the item form the back
    public Item removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException(
                "The deque is empty! No element can be returned!");

        Node oldlast = last;

        if (queuesize == 1) first = null;
        last = last.prev;
        if (last != null) last.next = null;
        queuesize -= 1;
        return oldlast.item;
    }

    // return an iterator over items in order from fornt to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException("Reach the end of the iterator!");
            Item item = current.item;
            current = current.next;
            return item;
        }

        // unsupported remove method
        public void remove() {
            throw new UnsupportedOperationException("remove() method is not supported!");
        }
    }

    public static void main(String[] args) {
        Deque<String> test1 = new Deque<String>();

        // the public methods
        StdOut.println("isEmpty(): " + test1.isEmpty());
        test1.addFirst("This ");
        test1.addLast("is ");
        StdOut.printf("iterator(): ");
        for (String s : test1) StdOut.printf("%s", s);
        StdOut.printf("\n");
        StdOut.println("removeFirst(): " + "This ".equals((test1.removeFirst())));
        StdOut.println("removeLast(): " + "is ".equals(test1.removeLast()));
        StdOut.println("size(): " + (test1.size() == 0));

        // exception test
        // IllegalArgumentException
        /* try {
            test1.addFirst(null);
        }
        catch (Exception e) {
            boolean result = (e.getClass() == IllegalArgumentException.class);
            StdOut.println("IllegalArgumentException for addFirst(): " + result);
        }

        try {
            test1.addLast(null);
        }
        catch (Exception e) {
            boolean result = (e.getClass() == IllegalArgumentException.class);
            StdOut.println("IllegalArgumentException for addLast(): " + result);
        }

        // NoSuchElementException
        try {
            test1.removeFirst();
        }
        catch (Exception e) {
            boolean result = (e.getClass() == NoSuchElementException.class);
            StdOut.println("NoSuchElementException for removeFirst: " + result);
        }

        try {
            test1.removeLast();
        }
        catch (Exception e) {
            boolean result = (e.getClass() == NoSuchElementException.class);
            StdOut.println("NoSuchElementException for removeLast: " + result);
        }

        try {
            Iterator<String> sIter = test1.iterator();
            sIter.next();
        }
        catch (Exception e) {
            boolean result = (e.getClass() == NoSuchElementException.class);
            StdOut.println("NoSuchElementException for iterator().next(): " + result);
        }

        // UnsupportedOperationException
        try {
            Iterator<String> sIter = test1.iterator();
            sIter.remove();
        }
        catch (Exception e) {
            boolean result = (e.getClass() == UnsupportedOperationException.class);
            StdOut.println("UnsupportedOperationException for iterator().remove(): " + result);
        }*/
    }
}
