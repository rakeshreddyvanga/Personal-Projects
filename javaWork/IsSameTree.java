package AnswersCheck;



public class IsSameTree {
	
	public class TreeNode {
	      int val;
	      TreeNode left;
	      TreeNode right;
	      TreeNode(int x) { val = x; }
	  }

	public static void main(String[] args) {
		

	}

	public boolean isSameTree(TreeNode p, TreeNode q) {
		
		if(p == null)
		{
			if(q == null)
				return true;
			else
				return false;
		}
		
		if(q == null)
		{
			if(p != null)
				return false;
		}
		
		if(p.val != q.val)
			return false;
		
		if(! isSameTree(p.left,q.left))
			return false;
		
		if(! isSameTree(p.right, q.right))
			return false;
		
		return true;
        
    }
}
