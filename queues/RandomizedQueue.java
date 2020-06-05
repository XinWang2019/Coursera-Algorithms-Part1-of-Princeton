import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int queuesize;
    private Item[] queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        queuesize = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return queuesize == 0;
    }

    // return the number of items oon the randomized queue
    public int size() {
        return queuesize;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("The item enqueued can not be null!");

        if (queue.length == queuesize) resize(2 * queue.length);
        queue[queuesize] = item;
        queuesize += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("This is an empty queue, no element can be dequeued!");

        int i = StdRandom.uniform(queuesize);
        Item removed = queue[i];
        queue[i] = queue[queuesize - 1];
        queue[queuesize - 1] = null;
        queuesize -= 1;
        if (queuesize < queue.length / 4) resize(queue.length / 2);
        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("This is an empty queue, no element can be sampled!");

        int i = StdRandom.uniform(queuesize);

        return queue[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // Iterator class
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] iteratingqueue;
        private int remainsize;

        RandomizedQueueIterator() {
            iteratingqueue = new int[queuesize];

            for (int i = 0; i < queuesize; i++) {
                iteratingqueue[i] = i;
            }
            remainsize = queuesize;
        }

        public boolean hasNext() {
            return remainsize != 0;
        }

        public Item next() {
            if (remainsize == 0)
                throw new NoSuchElementException("This object is empty or has finished iteration!");

            int i = StdRandom.uniform(remainsize);
            int nextindex = iteratingqueue[i];
            Item nextitem = queue[nextindex];
            if (i != remainsize - 1) iteratingqueue[i] = iteratingqueue[remainsize - 1];
            remainsize -= 1;
            return nextitem;
        }

        // unsupported remove method
        public void remove() {
            throw new UnsupportedOperationException("remove() method is not supported!");
        }
    }

    // resize the array
    private void resize(int capacity) {
        Item[] newqueue = (Item[]) new Object[capacity];
        for (int i = 0; i < queuesize; i++) {
            newqueue[i] = queue[i];
        }
        queue = newqueue;
    }


    public static void main(String[] args) {
        RandomizedQueue<String> test1 = new RandomizedQueue<>();

        // the public methods
        StdOut.println("isEmpty(): " + test1.isEmpty());
        test1.enqueue("This ");
        test1.enqueue("is ");
        test1.enqueue("a ");
        test1.enqueue("test!");
        StdOut.printf("iterator(): ");
        for (String s : test1) StdOut.printf("%s", s);
        StdOut.printf("\n");
        StdOut.println("dequeue(): " + test1.dequeue());
        StdOut.println("dequeue(): " + test1.dequeue());
        StdOut.println("dequeue(): " + test1.dequeue());
        StdOut.println("dequeue(): " + test1.dequeue());
        StdOut.println("size(): " + (test1.size() == 0));

        // exception test
        // IllegalArgumentException
        /* try {
            test1.enqueue(null);
        }
        catch (Exception e) {
            boolean result = (e.getClass() == IllegalArgumentException.class);
            StdOut.println("IllegalArgumentException for enqueue(): " + result);
        }

        // NoSuchElementException
        try {
            test1.dequeue();
        }
        catch (Exception e) {
            boolean result = (e.getClass() == NoSuchElementException.class);
            StdOut.println("NoSuchElementException for dequeue(): " + result);
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
