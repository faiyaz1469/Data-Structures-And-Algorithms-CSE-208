import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FibonacciHeap <ElementType>
{
    public class FibHeapNode
    {
        FibHeapNode left, right, parent, child;
        boolean marked = false;
        ElementType element;
        int deg = 0;

        public FibHeapNode( ElementType element )
        {
            super();
            this.element = element;
            left = this;
            right = this;
        }

    }

    Comparator<ElementType> elementComparator;
    FibHeapNode minimum;
    int numberNodes = 0;

    public FibonacciHeap( Comparator<ElementType> elementComparator )
    {
        super();
        this.elementComparator = elementComparator;
    }


    protected void insertInRootList (FibHeapNode fibNode)
    {
        //circular doubly linked list
        fibNode.parent = null;
        fibNode.marked = false;

        if (minimum == null)
        {
            minimum = fibNode;
            minimum.right = minimum;
            minimum.left = minimum;
        }
        else
        {
            fibNode.left = minimum.left;
            fibNode.right = minimum;
            fibNode.left.right = fibNode;
            fibNode.right.left = fibNode;

            if (elementComparator.compare (fibNode.element, minimum.element) < 0)
                minimum = fibNode;

        }
    }

    public void insert (ElementType element)
    {
        FibHeapNode node = new FibHeapNode (element);
        insertInRootList (node);
        ++numberNodes;
    }

    public ElementType extractMin()
    {
        if (minimum == null)
            return null;

        FibHeapNode minNode = minimum;

        // move all children to root list
        if (minNode.child != null)
        {
            FibHeapNode child = minNode.child;
            while (minNode.equals(child.parent))
            {
                FibHeapNode nextChild = child.right;
                insertInRootList (child);
                child = nextChild;
            }
        }

        minNode.left.right = minNode.right;
        minNode.right.left = minNode.left;

        if (minNode.right.equals (minNode))
            minimum = null;
        else
        {
            minimum = minimum.right;
            assemble();
        }

        --numberNodes;
        return minNode.element;
    }


    protected void assemble()
    {
        int arrSize = numberNodes + 1;

        ArrayList <FibHeapNode> arrList = new ArrayList<>(arrSize);

        for (int i = 0; i < arrSize; ++i)
            arrList.add(null);

        List<FibHeapNode> rootNodes = new LinkedList<>();
        rootNodes.add (minimum);

        for (FibHeapNode n = minimum.right; !n.equals( minimum ); n = n.right)
               rootNodes.add(n);

        for (FibHeapNode node : rootNodes)
        {
            if (node.parent != null)
                 continue;

            int d = node.deg;
            while (arrList.get(d) != null)
            {
                FibHeapNode y = arrList.get(d);

                if (elementComparator.compare(node.element, y.element) > 0 )
                {
                    FibHeapNode temp = node;
                    node = y;
                    y = temp;
                }
                connect (y, node);
                arrList.set(d, null);
                ++d;
            }
            arrList.set(d, node);
        }

        minimum = null;

        for (FibHeapNode node : arrList)
        {
            if (node != null)
                insertInRootList (node);
        }
    }


    protected void connect (FibHeapNode y, FibHeapNode x)
    {
        y.left.right = y.right;
        y.right.left = y.left;

        if (x.child == null)
        {
            y.right = y;
            y.left = y;
        }
        else
        {
            y.left = x.child.left;
            y.right = x.child;
            y.right.left = y;
            y.left.right = y;
        }

        x.child = y;
        y.parent = x;

        x.deg++;
        y.marked = false;
    }

    public boolean isEmpty()
    {
        return minimum == null;
    }

}