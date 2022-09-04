import java.io.*;

class RBNode {
    int element;
    int colour;
    RBNode parent;
    RBNode left;
    RBNode right;
}

class RBTree {

    private RBNode root;
    private final RBNode T_NULL;
    int count = 0;

    public RBTree() {
        T_NULL = new RBNode();
        T_NULL.colour = 0;
        T_NULL.left = null;
        T_NULL.right = null;
        root = T_NULL;
    }

    private boolean searchTreeAssist(RBNode node, int key) {
        if (key == node.element)
            return true;

        if (node == T_NULL)
            return false;

        if (key < node.element)
            return searchTreeAssist(node.left, key);

        return searchTreeAssist(node.right, key);
    }

    public boolean searchTree(int key) {
        return searchTreeAssist(this.root, key);
    }

    private void RBShift(RBNode m, RBNode n) {
        if (m.parent == null)
            root = n;
        else if (m == m.parent.left)
            m.parent.left = n;
        else
            m.parent.right = n;

        n.parent = m.parent;
    }

    // Balancing the tree after deletion of a node
    private void assertDelete(RBNode x) {
        RBNode tempNd;
        while (x != root && x.colour == 0) {

            if (x == x.parent.left) {

                tempNd = x.parent.right;

                if (tempNd.colour == 1) {
                    tempNd.colour = 0;
                    x.parent.colour = 1;
                    LTRotate(x.parent);
                    tempNd = x.parent.right;
                }

                if (tempNd.left.colour == 0 && tempNd.right.colour == 0) {
                    tempNd.colour = 1;
                    x = x.parent;
                }
                else {
                    if (tempNd.right.colour == 0) {
                        tempNd.left.colour = 0;
                        tempNd.colour = 1;
                        RTRotate(tempNd);
                        tempNd = x.parent.right;
                    }

                    tempNd.colour = x.parent.colour;
                    x.parent.colour = 0;
                    tempNd.right.colour = 0;
                    LTRotate(x.parent);
                    x = root;
                }
            }
            else {
                tempNd = x.parent.left;
                if (tempNd.colour == 1) {
                    tempNd.colour = 0;
                    x.parent.colour = 1;
                    RTRotate(x.parent);
                    tempNd = x.parent.left;
                }

                if (tempNd.right.colour == 0) {
                    tempNd.colour = 1;
                    x = x.parent;
                }
                else {
                    if (tempNd.left.colour == 0) {
                        tempNd.right.colour = 0;
                        tempNd.colour = 1;
                        LTRotate(tempNd);
                        tempNd = x.parent.left;
                    }

                    tempNd.colour = x.parent.colour;
                    x.parent.colour = 0;
                    tempNd.left.colour = 0;
                    RTRotate(x.parent);
                    x = root;
                }
            }
        }

        x.colour = 0;
    }

    private void deleteNodeAssist(RBNode node, int key) {
        RBNode tempNd = T_NULL;
        RBNode x, y;

        while (node != T_NULL) {
            if (node.element == key)
                tempNd = node;

            if (node.element <= key)
                node = node.right;
            else
                node = node.left;
        }

        if (tempNd == T_NULL)
            return;

        y = tempNd;
        int yMainColour = y.colour;

        if (tempNd.left == T_NULL) {
            x = tempNd.right;
            RBShift(tempNd, tempNd.right);
        }
        else if (tempNd.right == T_NULL) {
            x = tempNd.left;
            RBShift(tempNd, tempNd.left);
        }
        else {
            RBNode nd = tempNd.right;
            while (nd.left != T_NULL) {
                nd = nd.left;
            }
            y = nd;

            yMainColour = y.colour;
            x = y.right;
            if (y.parent == tempNd)
                x.parent = y;
            else {
                RBShift(y, y.right);
                y.right = tempNd.right;
                y.right.parent = y;
            }

            RBShift(tempNd, y);
            y.left = tempNd.left;
            y.left.parent = y;
            y.colour = tempNd.colour;
        }

        if (yMainColour == 0)
            assertDelete(x);
    }

    public void deleteNode(int key) {
        deleteNodeAssist(this.root, key);
    }

    public void LTRotate(RBNode node) {
        RBNode tempNd = node.right;
        node.right = tempNd.left;

        if (tempNd.left != T_NULL)
            tempNd.left.parent = node;

        tempNd.parent = node.parent;

        if (node.parent == null)
            this.root = tempNd;
        else if (node == node.parent.left)
            node.parent.left = tempNd;
        else
            node.parent.right = tempNd;

        tempNd.left = node;
        node.parent = tempNd;
    }

    public void RTRotate(RBNode node) {
        RBNode tempNd = node.left;
        node.left = tempNd.right;

        if (tempNd.right != T_NULL)
            tempNd.right.parent = node;

        tempNd.parent = node.parent;

        if (node.parent == null)
            this.root = tempNd;
        else if (node == node.parent.right)
            node.parent.right = tempNd;
        else
            node.parent.left = tempNd;

        tempNd.right = node;
        node.parent = tempNd;
    }


    // Balancing the node after insertion
    private void assertInsert(RBNode nd) {
        RBNode u;
        while (nd.parent.colour == 1) {
            if (nd.parent == nd.parent.parent.right) {
                u = nd.parent.parent.left;
                if (u.colour == 1) {
                    u.colour = 0;
                    nd.parent.colour = 0;
                    nd.parent.parent.colour = 1;
                    nd = nd.parent.parent;
                }
                else {
                    if (nd == nd.parent.left) {
                        nd = nd.parent;
                        RTRotate(nd);
                    }
                    nd.parent.colour = 0;
                    nd.parent.parent.colour = 1;
                    LTRotate(nd.parent.parent);
                }
            }
            else {
                u = nd.parent.parent.right;

                if (u.colour == 1) {
                    u.colour = 0;
                    nd.parent.colour = 0;
                    nd.parent.parent.colour = 1;
                    nd = nd.parent.parent;
                }
                else {
                    if (nd == nd.parent.right) {
                        nd = nd.parent;
                        LTRotate(nd);
                    }

                    nd.parent.colour = 0;
                    nd.parent.parent.colour = 1;
                    RTRotate(nd.parent.parent);
                }
            }

            if (nd == root)
                break;
        }
        root.colour = 0;
    }

    public void insert(int key) {
        RBNode node = new RBNode();
        node.parent = null;
        node.element = key;
        node.left = T_NULL;
        node.right = T_NULL;
        node.colour = 1;

        RBNode temp = null;
        RBNode x = this.root;

        while (x != T_NULL) {
            temp = x;

            if (node.element < x.element)
                x = x.left;
            else
                x = x.right;
        }

        node.parent = temp;

        if (temp == null)
            root = node;
        else if (node.element < temp.element)
            temp.left = node;
        else
            temp.right = node;

        if (node.parent == null) {
            node.colour = 0;
            return;
        }

        if (node.parent.parent == null)
            return;

        assertInsert(node);
    }

    private int preOrderInside(RBNode node, int key) {
        if (node != T_NULL) {
            if (key > node.element)
                count++;

            preOrderInside(node.left, key);
            preOrderInside(node.right, key);
        }
        return count;
    }

    public int preorder(int key) {
        count = 0;
        return preOrderInside(this.root, key);
    }


}

public class offline {

    public static void main(String[] args) throws IOException {

        int e,x,r;
        String fileString;
        RBTree TEUB = new RBTree();

        String line;
        String[] tokens;

        BufferedReader br = new BufferedReader(new FileReader("input.txt"));
        line = br.readLine();
        tokens = line.split("\\s");

        int m = Integer.parseInt(tokens[0]);
        fileString = String.valueOf(m);
        fileString += "\n";

        for (int i=0; i<m; i++){

            line = br.readLine();
            tokens = line.split("\\s");

            e = Integer.parseInt(tokens[0]);
            x = Integer.parseInt(tokens[1]);

            String fileString2 = e + " " + x + " ";
            fileString = fileString.concat(fileString2);

            if(e==0){
                if(TEUB.searchTree(x)) {
                    TEUB.deleteNode(x);
                    fileString += "1\n";
                }
                else
                    fileString += "0\n";
            }
            else if(e==1){
                if(!TEUB.searchTree(x)) {
                    TEUB.insert(x);
                    fileString += "1\n";
                }
                else
                    fileString += "0\n";
            }
            else if(e==2){
                if(TEUB.searchTree(x)) {
                    fileString += "1\n";
                }
                else
                    fileString += "0\n";
            }
            else if(e==3){
                r = TEUB.preorder(x);
                fileString += r + "\n";
            }
        }

        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));
        bw.write(fileString);
        bw.close();
    }
}

