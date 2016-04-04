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
	public static class RBNode // TODO Impliment this entire thing
	{
		private int key;
		private String value;
		private Color color; //
		private RBNode left, right, parent;

		public RBNode(int key, String value, RBNode parent)
		{
			this.key = key;
			this.value = value;
			this.parent = parent;
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

	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 *
	 */
	public RBNode getRoot()
	{
		return null; // to be replaced by student code
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty()
	{
		return false; // to be replaced by student code
	}

	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k)
	{
		return "42"; // to be replaced by student code
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
		return 42; // to be replaced by student code
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
		return 42; // to be replaced by student code
	}

	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min()
	{
		return "42"; // to be replaced by student code
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max()
	{
		return "42"; // to be replaced by student code
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray()
	{
		int[] arr = new int[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray()
	{
		String[] arr = new String[42]; // to be replaced by student code
		return arr; // to be replaced by student code
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
		return 42; // to be replaced by student code
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

}
