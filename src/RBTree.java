
// TODO Delete imports used only for testing:

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

    public enum NodeColor {
        RED, BLACK
    }

    /**
     * public class RBNode
     * <p>
     * A class with well named functions and fields that explain themselves (*with exceptions).
     */
    public static class RBNode {
        private int key;
        private String value;
        private NodeColor color;
        private RBNode left;
        private RBNode right;
        private RBNode parent;
        private int size; // Size of subtree with this node as the root

        private static final int DUMMY_LEFT_KEY = -10; // The key that is used to idetify a left dummy node.
        private static final int DUMMY_RIGHT_KEY = -20; // The key that is used to identify a right dummy node.

        /**
         * Creates a new Red Black node with the given attributes, no children
         * and red color.
         *
         * @param key
         * @param value
         * @param parent
         */
        public RBNode(int key, String value, RBNode parent) {
            this.key = key;
            this.value = value;
            this.setParent(parent);
            this.setLeft(null);
            this.setRight(null);
            this.setNodeColor(NodeColor.RED);
            this.setSize(1);
        }

        /**
         * Creates a new Red Black node with the given parent, black color,
         * "dummy" key that represents right/left-ness, null value, and no children, 0 size.
         * Perfect for dummy nodes (usually representing null "leaves")!
         *
         * @param parent
         */
        public RBNode(RBNode parent, boolean isRight) {
            this.key = (isRight ? DUMMY_RIGHT_KEY : DUMMY_LEFT_KEY);
            this.value = null;
            this.setParent(parent);
            this.setLeft(null);
            this.setRight(null);
            this.setNodeColor(NodeColor.BLACK);
            this.setSize(0);
        }

        public boolean isRed() {
            return this.getNodeColor() == NodeColor.RED;
        }

        public NodeColor getNodeColor() {
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

        public void setNodeColor(NodeColor color) {
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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    private RBNode sentinel; // "Parent" of the root, used as explained in the
    // slides shown in class

    public RBTree() {
        this.sentinel = new RBNode(-1, null, sentinel);
        this.sentinel.setLeft(this.sentinel);
        this.sentinel.setParent(sentinel);
        this.sentinel.setNodeColor(NodeColor.BLACK);
        this.sentinel.setSize(0);
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
        if (current == null) // If the node was not found we reach null (leaf)
            return null;

        return current.getValue(); // If the node was found we can call
        // getValue().
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
        assert RBInternalTester.isTreeValid(this);

        // Insertion:
        RBNode nearest = this.findParentNode(k);
        if (nearest == null) // Only happens if the tree is empty
        {
            this.sentinel.right = new RBNode(k, v, this.sentinel);
            this.sentinel.right.setNodeColor(NodeColor.BLACK);
            // this.getRoot().size++;
            return 1;
        }
        if (nearest.getKey() == k)
            return -1;
        RBNode newNode = new RBNode(k, v, nearest);
        if (nearest.getKey() < k)
            nearest.setRight(newNode);
        else
            nearest.setLeft(newNode);
        RBNode parent = newNode.getParent();
        while (parent != this.sentinel) // Increase size of all parents
        {
            parent.setSize(parent.getSize() + 1);
            parent = parent.getParent();
        }
        // Fixing:
        int changeCount = 0;
        RBNode currentNode = newNode;
        while (true) {
            if (currentNode.getParent().getNodeColor() == NodeColor.RED) {
                if (currentNode.getParent().getParent().getLeft() != null // null
                        // is
                        // black
                        && currentNode.getParent().getParent().getRight() != null // null
                        // is
                        // black
                        && currentNode.getParent().getParent().getLeft().getNodeColor() == currentNode.getParent() // if
                        // they
                        // are
                        // not
                        // of
                        // the
                        // same
                        // color
                        // the
                        // uncle
                        // must
                        // be
                        // black
                        // (parent
                        // is
                        // red)
                        .getParent().getRight().getNodeColor()) // Case
                // 1 -
                // red
                // parent,
                // red
                // uncle
                {
                    // "Moving the problem 2 layers upwards"
                    currentNode.getParent().getParent().getLeft().setNodeColor(NodeColor.BLACK);
                    currentNode.getParent().getParent().getRight().setNodeColor(NodeColor.BLACK);
                    currentNode = currentNode.getParent().getParent();
                    currentNode.setNodeColor(NodeColor.RED);
                    changeCount += 3;
                    if (currentNode == this.getRoot()) // Root must remain black
                    {
                        currentNode.setNodeColor(NodeColor.BLACK);
                        changeCount += 1;
                    }

                } else {
                    if (!this.isRightChild(currentNode.getParent())) // Cases
                    // 2+3 -
                    // red
                    // parent,
                    // black
                    // uncle
                    {
                        boolean case2happened = false;
                        if (this.isRightChild(currentNode)) // Case 2 - rotate
                        // to turn into case
                        // 3
                        {
                            RBNode B = currentNode, A = B.getParent(), C = A.getParent();
                            C.setLeft(B);
                            B.setParent(C);
                            A.setRight(B.getLeft());
                            if (B.getLeft() != null)
                                B.getLeft().setParent(A);
                            B.setLeft(A);
                            A.setParent(B);

                            int as = A.getSize();
                            B.setSize(as);
                            A.setSize(A.getSize() - 1);
                            if (B.getRight() != null)
                                A.setSize(A.getSize() - B.getRight().getSize());

                            case2happened = true;
                        }
                        // Case 3 - rotate to fix problem
                        RBNode B = currentNode;
                        if (!case2happened)
                            B = B.getParent();
                        RBNode C = B.getParent(), D = C.getParent();
                        boolean r = this.isRightChild(C);
                        C.setLeft(B.getRight());
                        if (B.getRight() != null)
                            B.getRight().setParent(C);
                        B.setRight(C);
                        C.setParent(B);
                        if (r)
                            D.setRight(B);
                        else
                            D.setLeft(B);
                        B.setParent(D);

                        int cs = C.getSize();
                        B.setSize(cs);
                        C.setSize(C.getSize() - (1 + B.left.getSize())); // No need to check if null
                        // since B must have a
                        // left child in case 3
                        // - A

                        B.setNodeColor(NodeColor.BLACK);
                        C.setNodeColor(NodeColor.RED);
                    } else // Cases 2+3 - mirrored
                    {
                        boolean case2happened = false;
                        if (!this.isRightChild(currentNode)) // Case 2 -
                        // mirrored
                        {
                            RBNode B = currentNode, A = B.getParent(), C = A.getParent();
                            C.setRight(B);
                            B.setParent(C);
                            A.setLeft(B.getRight());
                            if (B.getRight() != null)
                                B.getRight().setParent(A);
                            B.setRight(A);
                            A.setParent(B);

                            int as = A.getSize();
                            B.setSize(as);
                            A.setSize(A.getSize() - 1);
                            if (B.getLeft() != null)
                                A.setSize(A.getSize() - B.getLeft().getSize());

                            case2happened = true;
                        }
                        // Case 3 - mirrored
                        RBNode B = currentNode;
                        if (!case2happened)
                            B = B.getParent();
                        RBNode C = B.getParent(), D = C.getParent();
                        boolean r = this.isRightChild(C);
                        C.setRight(B.getLeft());
                        if (B.getLeft() != null)
                            B.getLeft().setParent(C);
                        B.setLeft(C);
                        C.setParent(B);
                        if (r)
                            D.setRight(B);
                        else
                            D.setLeft(B);
                        B.setParent(D);

                        int cs = C.getSize();
                        B.setSize(cs);
                        C.setSize(C.getSize() - (1 + B.right.getSize())); // No need to check if
                        // null since B must
                        // have a right child in
                        // case 3 - A

                        B.setNodeColor(NodeColor.BLACK);
                        C.setNodeColor(NodeColor.RED);
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
    static int ricardo = 0; // TODO: Ricardo should not be here.

    public int delete(int k) {
        assert RBInternalTester.isTreeValid(this);
        assert assertSizes(getRoot());
        ricardo++;

        RBNode deleted = findNode(k);
        if (deleted == null) {
            return -1;
        }

        RBNode fixNode;

        NodeColor missingNodeColor = deleted.color; // The color that we have
        // removed
        // from the tree and might cause
        // problems.
        boolean isDeletedRightChild = isRightChild(deleted);
        if (deleted.getLeft() == null) {
            fixNode = deleted.getRight();
            transplant(deleted, fixNode);
            if (fixNode == null) {
                fixNode = new RBNode(deleted.getParent(), isDeletedRightChild); // Creating
                // a
                // dummy
                // node representing
                // the null "node".
            }
        } else if (deleted.getRight() == null) {
            fixNode = deleted.getLeft();
            transplant(deleted, fixNode);
            if (fixNode == null) {
                fixNode = new RBNode(deleted.getParent(), isDeletedRightChild); // Creating
                // a
                // dummy
                // node representing
                // the null "node".
            }
        } else {
            /**
             * If the deleted node has 2 children we replace it with it's
             * successor: First, if the successor is not the deleted node's
             * right child we replace the successor with his (the successor's)
             * right child (which will be our fixNode). Then, we replace the
             * deleted node with the successor, and change the successor color
             * to the deleted node's color, so the missing color is the
             * successors's color.
             */
            RBNode successor = getSuccessorInSub(deleted);
            missingNodeColor = successor.getNodeColor();

            fixNode = successor.getRight();
            if (successor.getParent() != deleted) {
                boolean isSuccessorRightChild = isRightChild(successor);
                assert !isSuccessorRightChild;
                transplant(successor, fixNode);
                if (fixNode == null) {
                    fixNode = new RBNode(successor.getParent(), isSuccessorRightChild); // Creating
                    // a
                    // dummy
                    // node
                    // representing
                    // the null
                    // "node".
                }
                successor.setRight(deleted.getRight());
                successor.getRight().setParent(successor);
            } else if (fixNode == null) {
                fixNode = new RBNode(successor, true); // Creating a dummy node
                // representing the null
                // "node".
            }
            transplant(deleted, successor);
            successor.setLeft(deleted.getLeft());
            successor.getLeft().setParent(successor);
            successor.setNodeColor(deleted.getNodeColor());
        }

        if (fixNode.getKey() >= 0) { // Not dummy.
            updateSizeUpToRoot(fixNode);
        } else if (fixNode.getParent() != sentinel) {
            updateSizeUpToRoot(fixNode.getParent());
        }
        //At this point sizes should be correct.
        assert assertSizes(getRoot());

        if (missingNodeColor == NodeColor.BLACK) {
            return fixDelete(fixNode);
        }

        assert assertSizes(getRoot());
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
        int[] arr = new int[this.size()];
        int index = 0;

        RBNode current = getRoot();
        if (current == null) {
            return arr;
        }

        // A weird (stack-less) inorder recursion:
        int state = 0; // 0 - none done, 1 - left subtree done, 2 - all done.
        // (relative to current)
        while (current != sentinel) {
            switch (state) {
                case 0:
                    if (current.getLeft() != null) {
                        current = current.getLeft();
                        state = 0;
                    } else {
                        state = 1;
                    }
                    break;
                case 1:
                    arr[index++] = current.getKey();
                    if (current.getRight() != null) {
                        current = current.getRight();
                        state = 0;
                    } else {
                        state = 2;
                    }
                    break;
                case 2:
                    if (isRightChild(current)) {
                        state = 2;
                    } else {
                        state = 1;
                    }
                    current = current.getParent();
                    break;
            }
        }

        return arr;
    }

    /**
     * public String[] valuesToArray()
     * <p>
     * Returns an array which contains all values in the tree, sorted by their
     * respective keys, or an empty array if the tree is empty.
     */
    public String[] valuesToArray() {
        String[] arr = new String[this.size()];
        int index = 0;

        RBNode current = getRoot();
        if (current == null) {
            return arr;
        }

        // A wierd (stack-less) inorder recursion:
        int state = 0; // 0 - none done, 1 - left subtree done, 2 - all done.
        // (relative to current)
        while (current != sentinel) {
            switch (state) {
                case 0:
                    if (current.getLeft() != null) {
                        current = current.getLeft();
                        state = 0;
                    } else {
                        state = 1;
                    }
                    break;
                case 1:
                    arr[index++] = current.getValue();
                    if (current.getRight() != null) {
                        current = current.getRight();
                        state = 0;
                    } else {
                        state = 2;
                    }
                    break;
                case 2:
                    if (isRightChild(current)) {
                        state = 2;
                    } else {
                        state = 1;
                    }
                    current = current.getParent();
                    break;
            }
        }

        return arr;
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     * <p>
     * precondition: none postcondition: none
     */
    public int size() {
        if (this.empty())
            return 0;
        return this.getRoot().getSize();
    }

    /**
     * public int rank(int k)
     * <p>
     * Returns the number of nodes in the tree with a key smaller than k.
     * <p>
     * precondition: none postcondition: none
     */
    public int rank(int k) {
        RBNode currentNode = this.findGEQNode(k);
        if (currentNode == null) // Either tree is empty or there is no GEQ node
            // - k is bigger or equal to every key in
            // the tree
            return this.size();
        int total = 0;
        boolean shouldAdd = true;
        while (currentNode != this.sentinel) {
            if (shouldAdd && currentNode.left != null)
                total += currentNode.left.getSize();
            shouldAdd = this.isRightChild(currentNode);
            currentNode = currentNode.parent;
        }
        return total;
    }

    /**
     * If you wish to implement classes, other than RBTree and RBNode, do it in
     * this file, not in another file.
     */

    /**
     * A non documented utiliy function.
     */
    private boolean assertSizes(RBNode node) {
        if (node == null) {
            return true;
        }

        if (!assertSizes(node.getLeft()) || !assertSizes(node.getRight())) {
            return false;
        }

        return node.getSize() == 1 +
                (node.getLeft() != null ? node.getLeft().getSize() : 0) +
                (node.getRight() != null ? node.getRight().getSize() : 0);
    }

    /**
     * Replaces the subtree of oldNode with the subtree of a different node,
     * newNode. The oldNode's parent attribute is not changed even though it is
     * not his child anymore.
     *
     * @param oldNode The node to be replaced.
     * @param newNode The replacer node, may be null.
     */
    private void transplant(RBNode oldNode, RBNode newNode) {
        assert oldNode != null;
        assert oldNode != newNode;

        if (oldNode.getParent() == sentinel) {
            sentinel.setRight(newNode);
        } else {
            if (isRightChild(oldNode)) {
                oldNode.getParent().setRight(newNode);
            } else {
                oldNode.getParent().setLeft(newNode);
            }
        }

        if (newNode != null) {
            newNode.setParent(oldNode.getParent());
        }
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
                if (current.getRight() == null)
                    return current;
                else
                    current = current.getRight();
            } else {
                if (current.getLeft() == null)
                    return current;
                else
                    current = current.getLeft();
            }

        }
    }

    /**
     * Updates the size of the given node according to it's children's sizes.
     * node-size = 1 + left-child-size + right-child-size
     * Unless you do something crazy make sure the children's size is updated before calling updateSize!
     *
     * @param node The node to update it's size.
     */
    private void updateSize(RBNode node) {
        assert node != null;

        int size = 1;
        if (node.getLeft() != null) {
            size += node.getLeft().getSize();
        }
        if (node.getRight() != null) {
            size += node.getRight().getSize();
        }

        node.setSize(size);
    }

    /**
     * Updates (using updateSize) the size of the given node and all it's ancestors,
     * starting with node and going up the ancestry path to the root.
     *
     * @param node The node to update it's size.
     */
    private void updateSizeUpToRoot(RBNode node) {
        assert node != null;

        while (node != sentinel) {
            updateSize(node);
            node = node.getParent();
        }
    }

    /**
     * Returns the node with the key k if it exists, else returns null.
     * The function uses a binary search to find the node thus it runs in O(log(n)).
     *
     * @param k The key of the node to be searched
     * @return The node with the key k if it exists, else null.
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

    /**
     * returns the node with the highest key that is greater or equal to k if it
     * exists, else returns null.
     *
     * @param k The key of the node to be searched
     * @return The node with the highest key that is greater or equal to k if it
     * exists, else null
     */
    private RBNode findGEQNode(int k) {
        RBNode parent = this.findParentNode(k);
        if (parent == null || parent.key >= k)
            return parent;
        else
            return getSuccessor(parent);
    }

    /**
     * returns weather the RBNode node is a right child or not. If the given
     * node is "dummy" (not recognized by his parent) then the procedure checks
     * if it's key is DUMMY_RIGHT_KEY.
     *
     * @param node the node that is checked, if the node is null a "dummy" node
     *             should be passed.
     * @return true if node is a right child of node.parent, false if it is a
     * left child.
     */
    private boolean isRightChild(RBNode node) {
        assert node != null;

        if (node.getParent().getRight() == node) {
            return true;
        } else if (node.getParent().getLeft() == node) {
            return false;
        } else {
            return node.getKey() == RBNode.DUMMY_RIGHT_KEY;
        }
    }

    /**
     * Fix the Red Black tree after a deletion so it will satisfy all the
     * conditions it should.
     *
     * @param fixNode The node that replaced the node that his color is missing. If
     *                the replacer node is null then a dummy node representing it
     *                should be given instead.
     * @return The number of color switches.
     */
    private int fixDelete(RBNode fixNode) {
        assert fixNode != null : "ricardo: " + ricardo;

        int colorSwitches = 0;
        while (fixNode.parent != sentinel && fixNode.color == NodeColor.BLACK) {
            if (isRightChild(fixNode)) {
                RBNode brother = fixNode.getParent().getLeft(); // Exists
                assert brother != null : "ricardo :" + ricardo;

                if (brother.getNodeColor() == NodeColor.RED) {
                    colorSwitches++;
                    brother.setNodeColor(NodeColor.BLACK);
                    if (fixNode.getParent().getNodeColor() != NodeColor.RED) {
                        colorSwitches++;
                    }
                    fixNode.getParent().setNodeColor(NodeColor.RED);
                    rightRotate(fixNode.getParent());
                    brother = fixNode.getParent().getLeft();
                }
                if ((brother.getLeft() == null || brother.getLeft().getNodeColor() == NodeColor.BLACK)
                        && (brother.getRight() == null || brother.getRight().getNodeColor() == NodeColor.BLACK)) {
                    colorSwitches++;
                    brother.setNodeColor(NodeColor.RED);
                    fixNode = fixNode.getParent();
                } else {
                    if (brother.getLeft() == null || brother.getLeft().getNodeColor() == NodeColor.BLACK) {
                        colorSwitches++;
                        brother.getRight().setNodeColor(NodeColor.BLACK); // A
                        // red
                        // right
                        // node
                        // exists
                        // because
                        // only
                        // left
                        // is
                        // black
                        brother.setNodeColor(NodeColor.RED);
                        leftRotate(brother);
                        brother = fixNode.getParent().getLeft(); // It turns out
                        // brothers
                        // can be
                        // replaced.
                    }

                    NodeColor c = fixNode.getParent().getNodeColor();
                    if (brother.getNodeColor() != c) {
                        colorSwitches++;
                    }
                    brother.setNodeColor(c);
                    if (fixNode.getParent().getNodeColor() != NodeColor.BLACK) {
                        colorSwitches++;
                    }
                    fixNode.getParent().setNodeColor(NodeColor.BLACK);
                    if (brother.getLeft() != null) {
                        if (brother.getLeft().getNodeColor() != NodeColor.BLACK) {
                            colorSwitches++;
                        }
                        brother.getLeft().setNodeColor(NodeColor.BLACK);
                    }
                    rightRotate(fixNode.getParent());
                    fixNode = getRoot();
                }
            } else {
                RBNode brother = fixNode.getParent().getRight(); // Exists
                assert brother != null : "ricardo :" + ricardo;

                if (brother.getNodeColor() == NodeColor.RED) {
                    colorSwitches++;
                    brother.setNodeColor(NodeColor.BLACK);
                    if (fixNode.getParent().getNodeColor() != NodeColor.RED) {
                        colorSwitches++;
                    }
                    fixNode.getParent().setNodeColor(NodeColor.RED);
                    leftRotate(fixNode.getParent());
                    brother = fixNode.getParent().getRight();
                }
                if ((brother.getLeft() == null || brother.getLeft().getNodeColor() == NodeColor.BLACK)
                        && (brother.getRight() == null || brother.getRight().getNodeColor() == NodeColor.BLACK)) {
                    colorSwitches++;
                    brother.setNodeColor(NodeColor.RED);
                    fixNode = fixNode.getParent();
                } else {
                    if (brother.getRight() == null || brother.getRight().getNodeColor() == NodeColor.BLACK) {
                        colorSwitches++;
                        brother.getLeft().setNodeColor(NodeColor.BLACK); // A
                        // red
                        // left
                        // node
                        // exists
                        // because
                        // only
                        // right
                        // is
                        // black.
                        if (brother.getNodeColor() == NodeColor.BLACK) {
                            colorSwitches++;
                        }
                        brother.setNodeColor(NodeColor.RED);
                        rightRotate(brother);
                        brother = fixNode.getParent().getRight(); // It turns
                        // out
                        // brothers
                        // can be
                        // replaced.
                    }

                    NodeColor c = fixNode.getParent().getNodeColor();
                    if (brother.getNodeColor() != c) {
                        colorSwitches++;
                    }
                    brother.setNodeColor(c);
                    if (fixNode.getParent().getNodeColor() != NodeColor.BLACK) {
                        colorSwitches++;
                    }
                    fixNode.getParent().setNodeColor(NodeColor.BLACK);
                    if (brother.getRight() != null) {
                        if (brother.getRight().getNodeColor() != NodeColor.BLACK) {
                            colorSwitches++;
                        }
                        brother.getRight().setNodeColor(NodeColor.BLACK);
                    }
                    leftRotate(fixNode.getParent());
                    fixNode = getRoot();
                }
            }
        }

        if (fixNode.getNodeColor() != NodeColor.BLACK) {
            colorSwitches++;
        }
        fixNode.setNodeColor(NodeColor.BLACK);

        // At this point sizes should be correct.
        assert assertSizes(getRoot());
        return colorSwitches;
    }

    /**
     * Makes a right rotate with the given node as the "pivot" and updates the sizes accordingly.
     *
     * @param node The node to rotate around, the node should have a left child.
     */
    private void rightRotate(RBNode node) {
        assert node != null;
        assert node.getLeft() != null;

        RBNode replacer = node.getLeft();

        // Set the replacer as a child instead of node.
        replacer.setParent(node.getParent());
        if (node == getRoot()) {
            sentinel.setRight(replacer);
        } else if (isRightChild(node)) {
            node.getParent().setRight(replacer);
        } else {
            node.getParent().setLeft(replacer);
        }

        // Make the replacer's right child be the node's left child.
        node.setLeft(replacer.getRight());
        if (node.getLeft() != null) {
            node.getLeft().setParent(node);
        }

        // Make the replacer's right child be the node
        replacer.setRight(node);
        node.setParent(replacer);

        // Update the sizes.
        updateSize(node);
        updateSize(replacer);
    }

    /**
     * Makes a left rotate with the given node as the "pivot" and updates the sizes accordingly.
     *
     * @param node The node to rotate around, the node should have a right child.
     */
    private void leftRotate(RBNode node) {
        assert node != null;
        assert node.getRight() != null;

        RBNode replacer = node.getRight();

        // Set the replacer as a child instead of node.
        replacer.setParent(node.getParent());
        if (node == getRoot()) {
            sentinel.setRight(replacer);
        } else if (isRightChild(node)) {
            node.getParent().setRight(replacer);
        } else {
            node.getParent().setLeft(replacer);
        }

        // Make the replacer's left child be the node's right child.
        node.setRight(replacer.getLeft());
        if (node.getRight() != null) {
            node.getRight().setParent(node);
        }

        // Make the replacer's left child be the node
        replacer.setLeft(node);
        node.setParent(replacer);

        // Update the sizes.
        updateSize(node);
        updateSize(replacer);
    }

    /**
     * Returns the successor of the given node in it's sub tree
     *
     * @param node A node to look for his successor, may be null.
     * @return The successor of the node in it's sub tree, null if node is
     * null or the node itself if it is'nt null but there is no successor.
     */
    private static RBNode getSuccessorInSub(RBNode node) {
        if (node == null)
            return null;

        node = node.right;
        return getMinimum(node);
    }

    /**
     * Returns the successor of the given node.
     *
     * @param node A node to look for his successor, may be null.
     * @return The successor of the node, or null if node is null or node has no
     * successor (is maximum).
     */
    private RBNode getSuccessor(RBNode node) {
        RBNode currentNode = getSuccessorInSub(node);
        if (currentNode != null)
            return currentNode;
        else {
            while (currentNode != this.sentinel) {
                if (this.isRightChild(currentNode))
                    return currentNode.getParent();
                currentNode = currentNode.getParent();
            }
            return null;
        }
    }

    /**
     * Get the node with the minimal key in the subtree of the given node.
     *
     * @param node The node to search in his subtree, may be null.
     * @return The node with the minimal key, or null if node is null.
     */
    private static RBNode getMinimum(RBNode node) {
        if (node == null) {
            return null;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    @Override
    public String toString() {
        return this.keysToArray().toString();
    }

    // TODO Delete this testing stuff:
    private BufferedImage paintImage;
    private int XDIM, YDIM;
    private Graphics2D display;

    public void init_draw(int x, int y) {
        XDIM = x;
        YDIM = y;
        paintImage = new BufferedImage(XDIM, YDIM, BufferedImage.TYPE_INT_RGB);
        display = paintImage.createGraphics();
    }

    public int depth(RBNode N) // find max depth of tree
    {
        if (N == null)
            return 0;
        int l = depth(N.left);
        int r = depth(N.right);
        if (l > r)
            return l + 1;
        else
            return r + 1;
    }

    private int bheight = 50; // branch height
    private int yoff = 30; // static y-offset

    // l is level, lb,rb are the bounds (position of left and right child)
    private void drawnode(RBNode N, int l, int lb, int rb) {
        if (N == null)
            return;
        if (N.isRed()) {
            display.setColor(Color.red);

        } else {
            display.setColor(Color.black);

        }
        display.fillOval(((lb + rb) / 2) - 10, yoff + (l * bheight), 30, 30);
        display.setColor(Color.BLACK);
        display.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        display.drawString(N.key + "", ((lb + rb) / 2) + 25, yoff + 15 + (l * bheight));

        display.setColor(Color.blue); // draw branches
        if (N.left != null) {
            display.drawLine((lb + rb) / 2, yoff + 10 + (l * bheight), ((3 * lb + rb) / 4),
                    yoff + (l * bheight + bheight));
            drawnode(N.left, l + 1, lb, (lb + rb) / 2);
        }
        if (N.right != null) {
            display.drawLine((lb + rb) / 2, yoff + 10 + (l * bheight), ((3 * rb + lb) / 4),
                    yoff + (l * bheight + bheight));
            drawnode(N.right, l + 1, (lb + rb) / 2, rb);
        }
    }

    public void drawtree() {
        RBNode T = this.getRoot();
        if (T == null)
            return;
        int d = depth(T);
        bheight = (YDIM / d);
        display.setColor(Color.white);
        display.fillRect(0, 0, XDIM, YDIM); // clear background
        drawnode(T, 0, 0, XDIM);

    }

    public void save(String name) throws IOException {
        display.dispose();
        ImageIO.write(paintImage, "PNG", new File(name + ".png"));
    }
}
