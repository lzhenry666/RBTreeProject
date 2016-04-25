/* Nave Tal: 208518456
 * Amit Banay: 208520528
 */

/**
 * RBTree
 * <p>
 * An implementation of a RED BLACK Tree with non-negative, distinct integer
 * keys and values
 */

public class RBTree {

    public enum Color {
        RED, BLACK
    }

    /**
     * public class RBNode
     */
    public static class RBNode {
        private int key;
        private String value;
        private Color color;
        private RBNode left;
        private RBNode right;
        private RBNode parent;

        public RBNode(int key, String value, RBNode parent) {
            this.setKey(key);
            this.setValue(value);
            this.setParent(parent);
            this.setLeft(null);
            this.setRight(null);
            this.setColor(Color.RED);
        }

        public boolean isRed() {
            return this.getColor() == Color.RED;
        }

        public Color getColor() {
            return this.color;
        }

        public RBNode getLeft() {
            return this.left;
        }

        public RBNode getRight() {
            return this.right;
        }

        public RBNode getParent() {
            return this.parent;
        }

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }
    }

    private RBNode sentinel;
    private int size;

    public RBTree() {
        this.sentinel = new RBNode(-1, null, sentinel);
        this.sentinel.setLeft(this.sentinel);
        this.size = 0;
    }

    /**
     * public RBNode getRoot()
     * <p>
     * returns the root of the red black tree
     */
    public RBNode getRoot() {
        return this.sentinel.getRight();
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() {
        return this.getRoot() == null;
    }

    /**
     * public String search(int k)
     * <p>
     * returns the value of an item with key k if it exists in the tree
     * otherwise, returns null
     */
    public String search(int k) {
        RBNode current = this.getRoot();
        while (current != null && current.getKey() != k) {
            if (current.getKey() > k)
                current = current.getLeft();
            else
                current = current.getRight();
        }
        if (current == null)
            return null;

        return current.getValue();
    }

    /**
     * public int insert(int k, String v)
     * <p>
     * inserts an item with key k and value v to the red black tree. the tree
     * must remain valid (keep its invariants). returns the number of color
     * switches, or 0 if no color switches were necessary. returns -1 if an item
     * with key k already exists in the tree.
     */
    public int insert(int k, String v) {
        // Insertion:
        RBNode nearest = this.findParentNode(k);
        if (nearest.getKey() == k)
            return -1;
        RBNode newNode = new RBNode(k, v, nearest);
        if (nearest.getKey() < k)
            nearest.setLeft(newNode);
        else
            nearest.setRight(newNode);
        size++;
        // Fixing:
        int changeCount = 0;
        RBNode currentNode = newNode;
        while (true) {
            if (currentNode.getParent().getColor() == Color.RED) {
                if (currentNode.getParent().getParent().getLeft().getColor() == currentNode.getParent().getParent().getRight().getColor()) // Case
                // 1
                {
                    currentNode.getParent().getParent().getLeft().setColor(Color.BLACK);
                    currentNode.getParent().getParent().getRight().setColor(Color.BLACK);
                    currentNode = currentNode.getParent().getParent();
                    currentNode.setColor(Color.RED);
                    changeCount += 3;
                    if (currentNode == this.getRoot()) {
                        currentNode.setColor(Color.BLACK);
                        changeCount += 1;
                    }

                } else {
                    if (this.isRightChild(currentNode.getParent())) // Cases 2+3
                    {
                        if (this.isRightChild(currentNode)) // Case 2
                        {
                            RBNode B = currentNode, A = B.getParent(), C = A.getParent();
                            C.setLeft(B);
                            B.setParent(C);
                            A.setRight(B.getLeft());
                            if (B.getLeft() != null)
                                B.getLeft().setParent(A);
                            B.setLeft(A);
                            A.setParent(B);
                        }
                        RBNode B = currentNode, C = B.getParent(), D = C.getParent();
                        // Case 3

                        boolean r = this.isRightChild(C);
                        C.setLeft(B.getRight());
                        if (B.getRight() != null)
                            B.getRight().setParent(C);
                        B.setLeft(C);
                        C.setParent(B);
                        if (r)
                            D.setRight(B);
                        else
                            D.setLeft(B);
                        B.setParent(D);
                        B.setColor(Color.BLACK);
                        C.setColor(Color.RED);
                    } else // Cases 2+3 - mirrored
                    {
                        if (!this.isRightChild(currentNode)) // Case 2 - mirror
                        {
                            RBNode B = currentNode, A = B.getParent(), C = A.getParent();
                            C.setRight(B);
                            B.setParent(C);
                            A.setLeft(B.getRight());
                            if (B.getRight() != null)
                                B.getRight().setParent(A);
                            B.setRight(A);
                            A.setParent(B);
                        }
                        // Case 3 - mirror
                        RBNode B = currentNode, C = B.getParent(), D = C.getParent();
                        boolean r = this.isRightChild(C);
                        C.setRight(B.getLeft());
                        if (B.getLeft() != null)
                            B.getLeft().setParent(C);
                        B.setRight(C);
                        C.setLeft(B);
                        if (r)
                            D.setRight(B);
                        else
                            D.setLeft(B);
                        B.setParent(D);
                        B.setColor(Color.BLACK);
                        C.setColor(Color.RED);
                    }
                    changeCount += 2;
                    return changeCount;
                }
            } else
                return changeCount;
        }
    }

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there; the tree
     * must remain valid (keep its invariants). returns the number of color
     * switches, or 0 if no color switches were needed. returns -1 if an item
     * with key k was not found in the tree.
     */
    public int delete(int k) {
        RBNode deleted = this.findParentNode(k);
        if (deleted == null) {
            return -1;
        }

        RBNode fixNode;

        Color missingColor = deleted.color; // The color that we have removed from the tree and might cause problems.
        if (deleted.getLeft() == null) {
            fixNode = deleted.getRight();
            transplant(deleted, fixNode);
        } else if (deleted.getRight() == null) {
            fixNode = deleted.getLeft();
            transplant(deleted, fixNode);
        } else {
            /**
             * If the deleted node has 2 children we replace it with it's successor:
             * First, if the successor is not the deleted node's right child we replace the successor
             * with his (the successor's) right child (which will be our fixNode).
             * Then, we replace the deleted node with the successor, and change the successor color to the deleted
             * node's color, so the missing color is the successors's color.
             */
            RBNode successor = getSuccessor(deleted);
            missingColor = successor.getColor();

            fixNode = successor.getRight();
            if (successor.getParent() != deleted) {
                transplant(successor, fixNode);
                successor.setRight(deleted.getRight());
                successor.getRight().setParent(successor);
            }
            transplant(deleted, successor);
            successor.setLeft(deleted.getLeft());
            successor.getLeft().setParent(successor);
            successor.setColor(deleted.getColor());
        }

        if (missingColor == Color.BLACK) {
            return fixDelete(fixNode);
        }

        return 0;
    }

    /**
     * public String min()
     * <p>
     * Returns the value of the item with the smallest key in the tree, or null
     * if the tree is empty
     */
    public String min() {
        if (this.empty())
            return null;
        RBNode current = this.getRoot();
        while (current.getLeft() != null)
            current = current.getLeft();
        return current.getValue();
    }

    /**
     * public String max()
     * <p>
     * Returns the value of the item with the largest key in the tree, or null
     * if the tree is empty
     */
    public String max() {
        if (this.empty())
            return null;
        RBNode current = this.getRoot();
        while (current.getRight() != null)
            current = current.getRight();
        return current.getValue();
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree, or an empty
     * array if the tree is empty.
     */
    public int[] keysToArray() {
        int[] arr = new int[this.size];
        return arr; // TODO implement this!
    }

    /**
     * public String[] valuesToArray()
     * <p>
     * Returns an array which contains all values in the tree, sorted by their
     * respective keys, or an empty array if the tree is empty.
     */
    public String[] valuesToArray() {
        String[] arr = new String[this.size];
        return arr; // TODO implement this!
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     * <p>
     * precondition: none postcondition: none
     */
    public int size() {
        return this.size;
    }

    /**
     * public int rank(int k)
     * <p>
     * Returns the number of nodes in the tree with a key smaller than k.
     * <p>
     * precondition: none postcondition: none
     */
    public int rank(int k) {
        return 42; // to be replaced by student code
    }

    /**
     * If you wish to implement classes, other than RBTree and RBNode, do it in
     * this file, not in another file.
     */

    private void transplant(RBNode oldNode, RBNode newNode) {
        if (oldNode.getParent() == sentinel) {
            sentinel.setRight(newNode);
        } else {
            if (isRightChild(oldNode)) {
                oldNode.getParent().setRight(newNode);
            } else {
                oldNode.getParent().setLeft(newNode);
            }
        }

        newNode.setParent(oldNode.getParent());
    }

    /**
     * public int findParentNode(int k)
     * <p>
     * Returns the node under which a node with key k needs to be inserted, or,
     * if a node with key k already exists, the node with key k.
     * <p>
     * precondition: none postcondition: none
     */
    private RBNode findParentNode(int k) {
        RBNode current = this.getRoot();
        if (current == null)
            return current;
        while (true) {
            if (current.getKey() == k)
                return current;
            if (current.getKey() < k) {
                if (current.getLeft() == null)
                    return current;
                else
                    current = current.getLeft();
            } else {
                if (current.getRight() == null)
                    return current;
                else
                    current = current.getRight();
            }

        }
    }

    /**
     * returns the node with the key k if it exists, else returns null.
     *
     * @param k The key of the node to be searched
     * @return The node with the key k if it exists, else null
     */
    private RBNode findNode(int k) {
        RBNode current = getRoot();

        while (current != null) {
            if (current.getKey() < k) {
                current = current.getRight();
            } else if (current.getKey() > k) {
                current = current.getLeft();
            } else {
                return current;
            }
        }

        return null;
    }

    private boolean isRightChild(RBNode node) {
        return node.getParent().getRight() == node;
    }

    /**
     * Fix the Red Black tree after a deletion so it will satisfy all the conditions it should.
     *
     * @param fixNode The node that replaced the node that his color is missing.
     * @return The number of color switches.
     */
    private int fixDelete(RBNode fixNode) {
        int colorSwitches = 0;
        while (fixNode.parent != sentinel && fixNode.color == Color.BLACK) {
            if (isRightChild(fixNode)) {
                RBNode brother = fixNode.getParent().getLeft(); // Exists
                if (brother.getColor() == Color.RED) {
                    colorSwitches++;
                    brother.setColor(Color.BLACK);
                    if (fixNode.getParent().getColor() != Color.RED) {
                        colorSwitches++;
                    }
                    fixNode.getParent().setColor(Color.RED);
                    rightRotate(fixNode.getParent());
                    brother = fixNode.getParent().getLeft();
                }
                if ((brother.getLeft() == null || brother.getLeft().getColor() == Color.BLACK)
                        && (brother.getRight() == null || brother.getRight().getColor() == Color.BLACK)) {
                    colorSwitches++;
                    brother.setColor(Color.RED);
                    fixNode = fixNode.getParent();
                } else {
                    if (brother.getLeft() == null || brother.getLeft().getColor() == Color.BLACK) {
                        colorSwitches++;
                        brother.getLeft().setColor(Color.BLACK); // A red right node exists because only left is black
                        brother.setColor(Color.RED);
                        leftRotate(brother);
                        brother = fixNode.getParent().getLeft(); // It turns out brothers can be replaced.
                    }

                    Color c = fixNode.getParent().getColor();
                    if(brother.getColor() != c){
                        colorSwitches++;
                    }
                    brother.setColor(c);
                    if(fixNode.getParent().getColor() != Color.BLACK){
                        colorSwitches++;
                    }
                    fixNode.getParent().setColor(Color.BLACK);
                    if (brother.getLeft() != null) {
                        if(brother.getLeft().getColor() != Color.BLACK){
                            colorSwitches++;
                        }
                        brother.getLeft().setColor(Color.BLACK);
                    }
                    rightRotate(fixNode.getParent());
                    fixNode = getRoot();
                }
            } else {
                RBNode brother = fixNode.getParent().getRight(); // Exists
                if (brother.getColor() == Color.RED) {
                    colorSwitches++;
                    brother.setColor(Color.BLACK);
                    if (fixNode.getParent().getColor() != Color.RED) {
                        colorSwitches++;
                    }
                    fixNode.getParent().setColor(Color.RED);
                    leftRotate(fixNode.getParent());
                    brother = fixNode.getParent().getRight();
                }
                if ((brother.getLeft() == null || brother.getLeft().getColor() == Color.BLACK)
                        && (brother.getRight() == null || brother.getRight().getColor() == Color.BLACK)) {
                    colorSwitches++;
                    brother.setColor(Color.RED);
                    fixNode = fixNode.getParent();
                } else {
                    if (brother.getRight() == null || brother.getRight().getColor() == Color.BLACK) {
                        colorSwitches++;
                        brother.getLeft().setColor(Color.BLACK); // A red left node exists because only right is black
                        if(brother.getColor() == Color.BLACK){
                            colorSwitches++;
                        }
                        brother.setColor(Color.RED);
                        rightRotate(brother);
                        brother = fixNode.getParent().getRight(); // It turns out brothers can be replaced.
                    }

                    Color c = fixNode.getParent().getColor();
                    if(brother.getColor() != c){
                        colorSwitches++;
                    }
                    brother.setColor(c);
                    if(fixNode.getParent().getColor() != Color.BLACK){
                        colorSwitches++;
                    }
                    fixNode.getParent().setColor(Color.BLACK);
                    if (brother.getRight() != null) {
                        if(brother.getRight().getColor() != Color.BLACK){
                            colorSwitches++;
                        }
                        brother.getRight().setColor(Color.BLACK);
                    }
                    leftRotate(fixNode.getParent());
                    fixNode = getRoot();
                }
            }
        }

        if(fixNode.getColor() != Color.BLACK){
            colorSwitches++;
        }
        fixNode.setColor(Color.BLACK);

        return colorSwitches;
    }

    private void rightRotate(RBNode node) {
        if (node == null) {
            throw new IllegalArgumentException("node should not be null");
        } else if (node.getLeft() == null) {
            throw new IllegalArgumentException("node's left child should not be null");
        }

        RBNode y = node.getLeft();

        // Set the replacer as a child instead of node.
        y.setParent(node.getParent());
        if (node == getRoot()) {
            sentinel.setRight(y);
        } else if (isRightChild(node)) {
            node.getParent().setRight(y);
        } else {
            node.getParent().setLeft(y);
        }

        // Make the replacer's right child be the node's left child.
        node.setLeft(y.getRight());
        node.getLeft().setParent(node);

        //Make the replacer's right child be the node
        y.setRight(node);
        node.setParent(y);
    }

    private void leftRotate(RBNode node) {
        if (node == null) {
            throw new IllegalArgumentException("node should not be null");
        } else if (node.getRight() == null) {
            throw new IllegalArgumentException("node's right child should not be null");
        }

        RBNode y = node.getRight();

        // Set the replacer as a child instead of node.
        y.setParent(node.getParent());
        if (node == getRoot()) {
            sentinel.setRight(y);
        } else if (isRightChild(node)) {
            node.getParent().setRight(y);
        } else {
            node.getParent().setLeft(y);
        }

        // Make the replacer's left child be the node's right child.
        node.setRight(y.getLeft());
        node.getRight().setParent(node);

        //Make the replacer's left child be the node
        y.setLeft(node);
        node.setParent(y);
    }


    /**
     * Returns the successor of the given node.
     *
     * @param node A node to look for his successor, may be null.
     * @return The successor of the node, or null if node is null.
     */
    private static RBNode getSuccessor(RBNode node) {
        if (node == null) {
            return null;
        }

        node = node.right;
        return getMinimum(node);
    }

    /**
     * Get the node with the minimal key in the subtree of the given node.
     *
     * @param node The node to search in his subtree, may be null.
     * @return The node with the minimal key, or null if node is null.
     */
    private static RBNode getMinimum(RBNode node) {
        if (node == null) {
            return node;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node.left;
    }
}
