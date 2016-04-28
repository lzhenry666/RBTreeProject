public class RBInternalTester
{

	public static boolean isTreeValid(RBTree t)
	{
		if(checkRedRule(t.getRoot()) == false)
		{
			 System.out.println("Red rule failed");
			 return false;
		}
		if(checkBlackRule(t.getRoot()) == false)
		{
			System.out.println("Black rule failed");
			System.out.println(minBlackPaths(t.getRoot(),0)+":"+maxBlackPaths(t.getRoot(),0));
			return false;
		}
		return true;
	}
	
	private static boolean checkRedRule(RBTree.RBNode currNode)
	{
		if(currNode == null || currNode.getKey() < 0)
		{
			return true;
		}
		if(currNode.isRed())
		{
			boolean isLeftRed = currNode.getLeft() != null && currNode.getLeft().isRed();
			boolean isRightRed = currNode.getRight() != null && currNode.getRight().isRed();
			if(isLeftRed || isRightRed)
				return false;
		}
			
				
		boolean leftOk = checkRedRule(currNode.getLeft());
		boolean rightOk = checkRedRule(currNode.getRight());
		return leftOk && rightOk;
	}
	
	private static boolean checkBlackRule(RBTree.RBNode currNode)
	{
		return minBlackPaths(currNode,0) == maxBlackPaths(currNode,0);
	}
	
	private static int minBlackPaths(RBTree.RBNode currNode, int currBlackCount)
	{
		if(currNode == null || currNode.getKey() < 0)
			return currBlackCount;
		if(currNode.isRed() == false)
			currBlackCount+=1;
		return Math.min(minBlackPaths(currNode.getLeft(),currBlackCount),minBlackPaths(currNode.getRight(),currBlackCount));
		
	}
	
	private static int maxBlackPaths(RBTree.RBNode currNode, int currBlackCount)
	{
		if(currNode == null|| currNode.getKey() < 0)
			return currBlackCount;
		if(currNode.isRed() == false)
			currBlackCount+=1;
		return Math.max(maxBlackPaths(currNode.getLeft(),currBlackCount),maxBlackPaths(currNode.getRight(),currBlackCount));
	}
	
}