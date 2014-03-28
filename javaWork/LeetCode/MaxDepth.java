package AnswersCheck;

public class MaxDepth {
	
	public class TreeNode {
		      int val;
		      TreeNode left;
		      TreeNode right;
		      TreeNode(int x) { val = x; }
		  }

	public static void main(String[] args) {
		
	}
	
	 public static int maxDepth(TreeNode root) {
		 
		 if(root == null)
			 return 0;
		 int left,right;
		 left = maxDepth(root.left);
		 right = maxDepth(root.right);
		 if(left>=right)
			 return left+1;
		 else
			 return right+1;
		 
	        
	    }

}
