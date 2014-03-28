package AnswersCheck;

public class PathSum {

	public static void main(String[] args) {

	}

	public static boolean hasPathSum(TreeNode root, int sum) {
		
		if(root == null)
			return false;
		if(root.left == null && root.right == null && sum == 0)
			return true;
		
		if (hasPathSum(root.left, sum-root.val) ||
		hasPathSum(root.right, sum-root.val) )
			return true;
		
		return false;		
		
		
	}
}
