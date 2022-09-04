import java.util.Comparator;
import java.util.Vector;

public class BinaryHeap<KeyType>
{

    private final Vector<KeyType> arr = new Vector<>();
    Comparator<KeyType> keyComparator;

    public BinaryHeap( Comparator<KeyType> keyComparator )
    {
        super();
        this.keyComparator = keyComparator;
    }

    private int parent(int i)
    {
        if (i == 0)
            return 0;

        return (i - 1) / 2;
    }

    private int LEFT(int i) {
        return (2*i + 1);
    }


    private int RIGHT(int i) {
        return (2*i + 2);
    }

    void swap(int x, int y)
    {
        KeyType temp = arr.get(x);
        arr.setElementAt(arr.get(y), x);
        arr.setElementAt(temp, y);
    }

    private void heapify_down(int i)
    {

        int left = LEFT(i);
        int right = RIGHT(i);

        int largest = i;


        if (left < size() && keyComparator.compare( arr.get(left),  arr.get(i) ) > 0) {
            largest = left;
        }

        if (right < size() && keyComparator.compare( arr.get(right),  arr.get(largest) ) > 0) {
            largest = right;
        }

        if (largest != i)
        {
            swap(i, largest);
            heapify_down(largest);
        }
    }

    private void heapify_up(int i)
    {
        if (i > 0 && keyComparator.compare( arr.get(parent(i)),  arr.get(i) ) < 0)
        {
            swap(i, parent(i));
            heapify_up(parent(i));
        }

        if (i > 0 && keyComparator.compare( arr.get(parent(i)),  arr.get(i) ) > 0)
        {
            swap(i, parent(i));
            heapify_up(parent(i));
        }
    }

    public void add(KeyType key)
    {
        arr.addElement(key);
        int index = size() - 1;
        heapify_up(index);
    }

    public KeyType poll() throws Exception {

            if (size() == 0)
                throw new Exception("Heap underflow");

            KeyType root = arr.get(0);

            arr.setElementAt(arr.lastElement(), 0);
            arr.remove(size() - 1);

            heapify_down(0);

            return root;
    }

    public int size() {
        return arr.size();
    }

    public Boolean isEmpty() {
        return arr.isEmpty();
    }

}

