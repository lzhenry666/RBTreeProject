import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainClass
{
	public static void main(String[] args)
	{
		int BASE_TREE_SIZE = 10000;
		for (int t = 1; t <= 10; t++)
		{
			int tree_size = BASE_TREE_SIZE * t;
			List<Integer> keys = new ArrayList<Integer>();
			for (int i = 0; i < tree_size; i++)
				keys.add(i);
			Collections.shuffle(keys);
			RBTree tree = new RBTree();
			int totalAdd = 0, totalDel = 0;
			for (int key : keys)
			{
				totalAdd += tree.insert(key, "" + key);
			}
			for (int i = 0; i < tree_size; i++)
			{
				totalDel += tree.delete(i);
			}
			System.out.println("total: " + tree_size + ", add: " + (float) totalAdd / tree_size + ", delete: "
					+ (float) totalDel / tree_size);
		}
	}
}