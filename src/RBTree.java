/* Nave Tal: 208518456
 * Amit Banay: 208520528
 */

/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */

public class RBTree
{

	public enum Color
	{
		Red, Black, ExtraBlack;
	}

	/**
	 * public class RBNode
	 */
	public static class RBNode
	{
		private int key;
		private String value;
		private Color color;
		private RBNode left, right, parent;

		public RBNode(int key, String value, RBNode parent)
		{
			this.key = key;
			this.value = value;
			this.parent = parent;
			this.left = null;
			this.right = null;
			this.color = Color.Red;
		}

		public boolean isRed()
		{
			return this.color == Color.Red;
		}

		public Color getColor()
		{
			return this.color;
		}

		public RBNode getLeft()
		{
			return this.left;
		}

		public RBNode getRight()
		{
			return this.right;
		}

		public RBNode getParent()
		{
			return this.parent;
		}

		public int getKey()
		{
			return this.key;
		}

		public String getValue()
		{
			return this.value;
		}
	}

	private RBNode sentinel;
	private int size;

	public RBTree()
	{
		this.sentinel = new RBNode(-1, null, sentinel);
		this.sentinel.left = this.sentinel;
		this.size = 0;
	}

	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 *
	 */
	public RBNode getRoot()
	{
		return this.sentinel.right;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty()
	{
		return this.getRoot() == null;
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k)
	{
		RBNode current = this.getRoot();
		while (current != null && current.key != k)
		{
			if (current.key > k)
				current = current.left;
			else
				current = current.right;
		}
		if (current == null)
			return null;

		return current.value;
	}

	/**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v)
	{
		// Insertion:
		RBNode nearest = this.findParentNode(k);
		if (nearest.key == k)
			return -1;
		RBNode newNode = new RBNode(k, v, nearest);
		if (nearest.key < k)
			nearest.left = newNode;
		else
			nearest.right = newNode;
		size++;
		// Fixing:
		int changeCount = 0;
		RBNode currentNode = newNode;
		while (true)
		{
			if (currentNode.parent.color == Color.Red)
			{
				if (currentNode.parent.parent.left.color == currentNode.parent.parent.left.color) // Case
																									// 1
				{
					currentNode.parent.parent.left.color = Color.Black;
					currentNode.parent.parent.right.color = Color.Black;
					currentNode = currentNode.parent.parent;
					currentNode.color = Color.Red;
					changeCount += 3;
					if (currentNode == this.getRoot())
					{
						currentNode.color = Color.Black;
						changeCount += 1;
					}

				} else
				{
					if (this.isRightChild(currentNode.parent)) // Cases 2+3
					{
						if (this.isRightChild(currentNode)) // Case 2
						{
							RBNode B = currentNode, A = B.parent, C = A.parent;
							C.left = B;
							B.parent = C;
							A.right = B.left;
							if (B.left != null)
								B.left.parent = A;
							B.left = A;
							A.parent = B;
						}
						RBNode B = currentNode, C = B.parent, D = C.parent;
						// Case 3

						boolean r = this.isRightChild(C);
						C.left = B.right;
						if (B.right != null)
							B.right.parent = C;
						B.left = C;
						C.parent = B;
						if (r)
							D.right = B;
						else
							D.left = B;
						B.parent = D;
						B.color = Color.Black;
						C.color = Color.Red;
					} else // Cases 2+3 - mirrored
					{
						if (!this.isRightChild(currentNode)) // Case 2 - mirror
						{
							RBNode B = currentNode, A = B.parent, C = A.parent;
							C.right = B;
							B.parent = C;
							A.left = B.right;
							if (B.right != null)
								B.right.parent = A;
							B.right = A;
							A.parent = B;
						}
						// Case 3 - mirror
						RBNode B = currentNode, C = B.parent, D = C.parent;
						boolean r = this.isRightChild(C);
						C.right = B.left;
						if (B.left != null)
							B.left.parent = C;
						B.right = C;
						C.left = B;
						if (r)
							D.right = B;
						else
							D.left = B;
						B.parent = D;
						B.color = Color.Black;
						C.color = Color.Red;
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
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int delete(int k)
	{
		return 42; /*
					 * TODO implement delete (with color switches and flips)
					 * don't forget to increment the size variable!
					 */
	}

	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min()
	{
		if (this.empty())
			return null;
		RBNode current = this.getRoot();
		while (current.left != null)
			current = current.left;
		return current.value;
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max()
	{
		if (this.empty())
			return null;
		RBNode current = this.getRoot();
		while (current.right != null)
			current = current.right;
		return current.value;
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray()
	{
		int[] arr = new int[this.size];
		return arr; // TODO implement this!
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray()
	{
		String[] arr = new String[this.size];
		return arr; // TODO implement this!
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size()
	{
		return this.size;
	}

	/**
	 * public int rank(int k)
	 *
	 * Returns the number of nodes in the tree with a key smaller than k.
	 *
	 * precondition: none postcondition: none
	 */
	public int rank(int k)
	{
		return 42; // to be replaced by student code
	}

	/**
	 * If you wish to implement classes, other than RBTree and RBNode, do it in
	 * this file, not in another file.
	 */

	/**
	 * public int findNearestNode(int k)
	 *
	 * Returns the node under which a node with key k needs to be inserted, or,
	 * if a node with key k already exists, the node with key k.
	 *
	 * precondition: none postcondition: none
	 */
	private RBNode findParentNode(int k)
	{
		RBNode current = this.getRoot();
		if (current == null)
			return current;
		while (true)
		{
			if (current.key == k)
				return current;
			if (current.key < k)
			{
				if (current.left == null)
					return current;
				else
					current = current.left;
			} else
			{
				if (current.right == null)
					return current;
				else
					current = current.right;
			}

		}
	}

	private boolean isRightChild(RBNode node)
	{
		return node.parent.right == node;
	}
}
