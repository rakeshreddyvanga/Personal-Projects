package AnswersCheck;

public class BalancedTree {

	public static void main(String[] args) {
		
		TreeNode root = new TreeNode(2);
		insert(root,new TreeNode(1));
		insert(root,new TreeNode(3));
		insert(root,new TreeNode(4));
		insert(root,new TreeNode(5));
		insert(root,new TreeNode(6));
		insert(root,new TreeNode(7));
		inOrderTraverse(root);
		System.out.print(isBalanced(root));
	}
	
	public static boolean isBalanced(TreeNode root) {
		if(root == null)
			return true;
		int leftHeight = height(root.left);
		int rightHeight = height(root.right);
		if((leftHeight - rightHeight <= 1 && leftHeight - rightHeight >= 0) || (rightHeight-leftHeight <= 1 && rightHeight-leftHeight >= 0))
			if(isBalanced(root.left) && isBalanced(root.right))
				return true;
		
		return false;
		
	}
	
	public static int max(int a,int b)
	{
		if(a > b)
			return a;
		else
			return b;
	}
	
	public static int height(TreeNode root){
		if(root == null)
			return 0;
		
		return 1 + max(height(root.left), height(root.right));
	}
	
	public static void insert(TreeNode root, TreeNode n){
        if(root == null || n == null) return;
        
        if(root.val  > n.val ){
        	
            if(root.left  == null)
                root.left =n;
                   
            else
                insert(root.left ,n);
            

        }else if(root.val  < n.val ){
        	
            if(root.right  == null)
                root.right = n;
                  
            else
                insert(root.right ,n);
            
            
        }
    }
    //in-order Traversal
    public static void inOrderTraverse(TreeNode root){
        if(root != null){
            inOrderTraverse(root.left );
            System.out.print("  "+root.val );
            inOrderTraverse(root.right );
        }
    }
}

