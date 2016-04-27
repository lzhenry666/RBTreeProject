import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main {
	enum Mode {
		Random, IncreasingOrder, DecreasingOrder, IncreaseDecreaseOrder
	}; 

	// Choose mode here, or create your own by changing the variable 'key'
	public static Mode mode = Mode.Random;
	// Make sure the folder exists
	public static String insertPath = "InsertFolder/pic";
	// Make sure the folder exists
	public static String deletePath = "DeleteFolder/pic";
	// If on random mode - choose a different seed to get a different random
	// tree
	public static int randomSeed = 8;

	// I chose a meaningful variable names, therefore there is no need to
	// explain
	// the next ones
	public static int NUM_OF_NODES = 10;
	public static boolean saveInsertPictures = false;
	public static boolean saveDeletePictures = true;

	public static void main(String[] args) throws IOException {
		RBTree tree = new RBTree();

		Random r = new Random(randomSeed);

		System.out.println("Insertion starts");
		int xd = 1366 * 3;
		int yd = 768 * 3;
		for (int i = 0; i < NUM_OF_NODES; i++) {
			int key3 = NUM_OF_NODES - i;
			int key1 = r.nextInt(NUM_OF_NODES * 50);
			int key2 = i;
			int key4;
			if (i % 2 == 1) {
				key4 = i;
			} else {
				key4 = NUM_OF_NODES - i;
			}
			int key;
			switch (mode) {
			case Random:
				key = key1;
				break;
			case IncreasingOrder:
				key = key2;
				break;
			case DecreasingOrder:
				key = key3;
				break;
			case IncreaseDecreaseOrder:
				key = key4;
				break;
			}
			tree.insert(key1, String.valueOf(i));
			if (saveInsertPictures) {
				tree.init_draw(xd, yd);
				tree.drawtree();
				try {
					if (i < 10)
						tree.save(insertPath + "00" + i);
					else if (i < 100)
						tree.save(insertPath + "0" + i);
					else if (i < 1000)
						tree.save(insertPath + i);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}

		System.out.println("Deletion starts");
		int i = 0;
		List<Integer> lst = new ArrayList<Integer>();
		int[] keys = tree.keysToArray();
		for (int j = 0; j < keys.length; j++)
			lst.add(keys[j]);
		while (tree.size() > 0) {
			int theChosenOne = r.nextInt(tree.size());
			tree.delete(lst.get(theChosenOne));
			lst.remove(theChosenOne);
			if (saveDeletePictures) {
				tree.init_draw(xd, yd);
				tree.drawtree();
				try {
					if (i < 10)
						tree.save(deletePath + "00" + i);
					else if (i < 100)
						tree.save(deletePath + "0" + i);
					else if (i < 1000)
						tree.save(deletePath + i);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			i++;
		}

		System.out.println("All done");
	}
}