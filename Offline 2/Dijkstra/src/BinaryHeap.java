import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

// A class for implementing the priority queue
public class BinaryHeap<KeyType>
{
    // vector to store heap elements
    private Vector<KeyType> A = new Vector<>();
    Comparator<KeyType> keyComparator;


    // constructor: use the default initial capacity of a vector
    public BinaryHeap() {
        //A = new Vector<>();
    }

    public BinaryHeap( Comparator<KeyType> keyComparator )
    {
        super();
        this.keyComparator = keyComparator;
    }

    // return parent of `A[i]`
    private int parent(int i)
    {
        // if `i` is already a root node
        if (i == 0) {
            return 0;
        }

        return (i - 1) / 2;
    }

    // return left child of `A[i]`
    private int LEFT(int i) {
        return (2*i + 1);
    }

    // return right child of `A[i]`
    private int RIGHT(int i) {
        return (2*i + 2);
    }

    // swap values at two indexes
    void swap(int x, int y)
    {
        // swap with a child having greater value
        KeyType temp = A.get(x);
        A.setElementAt(A.get(y), x);
        A.setElementAt(temp, y);
    }

    // Recursive heapify-down procedure. Here, the node at index `i`
    // and its two direct children violate the heap property
    private void heapify_down(int i)
    {
        // get left and right child of node at index `i`
        int left = LEFT(i);
        int right = RIGHT(i);

        int largest = i;



        // compare `A[i]` with its left and right child
        // and find the largest value
        if (left < size() && keyComparator.compare( A.get(left),  A.get(i) ) > 0) {
            largest = left;
        }

        if (right < size() && keyComparator.compare( A.get(right),  A.get(largest) ) > 0) {
            largest = right;
        }

        if (largest != i)
        {
            // swap with a child having greater value
            swap(i, largest);

            // call heapify-down on the child
            heapify_down(largest);
        }
    }

    // Recursive heapify-up procedure
    private void heapify_up(int i)
    {
        // check if the node at index `i` and its parent violates
        // the heap property

        if (i > 0 && keyComparator.compare( A.get(parent(i)),  A.get(i) ) < 0)
        {
            // swap the two if heap property is violated
            swap(i, parent(i));

            // call heapify-up on the parent
            heapify_up(parent(i));
        }
        if (i > 0 && keyComparator.compare( A.get(parent(i)),  A.get(i) ) > 0)
        {
            // swap the two if heap property is violated
            swap(i, parent(i));

            // call heapify-up on the parent
            heapify_up(parent(i));
        }
    }

    // return size of the heap
    public int size() {
        return A.size();
    }

    // check if the heap is empty or not
    public Boolean isEmpty() {
        return A.isEmpty();
    }

    // insert a specified key into the heap
    public void add(KeyType key)
    {
        // insert a new element at the end of the vector
        A.addElement(key);

        // get element index and call heapify-up procedure
        int index = size() - 1;
        heapify_up(index);
    }

    // Function to remove and return an element with the highest priority
    // (present at the root). It returns null if the queue is empty
    public KeyType poll()
    {
        try {
            // if the heap is empty, throw an exception
            if (size() == 0) {
                throw new Exception("Index is out of range (Heap underflow)");
            }

            // element with the highest priority
            KeyType root = A.get(0);

            // replace the root of the heap with the last element of the vector
            A.setElementAt(A.lastElement(), 0);
            A.remove(size() - 1);

            // call heapify-down on the root node
            heapify_down(0);

            // return root element
            return root;
        }
        // catch and print the exception
        catch (Exception ex)
        {
            System.out.println(ex);
            return null;
        }
    }

    // Function to return an element with the highest priority
    // (present at the root). It returns null if the queue is empty
    public KeyType peek()
    {
        try {
            // if the heap has no elements, throw an exception
            if (size() == 0) {
                throw new Exception("Index out of range (Heap underflow)");
            }

            // otherwise, return the top (first) element
            return A.firstElement();    // or A.get(0);
        }
        // catch the exception and print it, and return null
        catch (Exception ex)
        {
            System.out.println(ex);
            return null;
        }
    }

    // Function to remove all elements from the priority queue
    public void clear()
    {
        System.out.print("Emptying queue: ");
        while (!A.isEmpty()) {
            System.out.print(poll() + " ");
        }
        System.out.println();
    }

    // Returns true if the queue contains the specified element
    public Boolean contains(Integer i) {
        return A.contains(i);
    }

    // Returns an array containing all elements in the queue
    public Integer[] toArray() {
        return A.toArray(new Integer[size()]);
    }
}

