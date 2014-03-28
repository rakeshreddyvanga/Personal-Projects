package AnswersCheck;

public class Array_To_BST {

	public static void main(String[] args) {
		
		int[] num = {-97,-82,-69,-66,-37,-25,-20,-13,-12,-11,-7,-3,19,33,43,55,77};
		inOrderTraverse(sortedArrayToBST(num));
	}
	
	public static TreeNode sortedArrayToBST(int[] num) {
		
		if(num == null)
			return null;
		
		if(num.length == 0)
			return null;
		
		if(num.length == 1){
			TreeNode root = new TreeNode(num[0]);
			root.left = null;
			root.right = null;
			return root;
		}
		
		int size = num.length;
		int rootIndex = (size-1)/2;
		int left = rootIndex-1, right = rootIndex+1;
		
		TreeNode root = new TreeNode(num[rootIndex]);
		int[] numLeft = new int[left+1], numRight = new int [size-right];
		for(int i=0;i<=left;i++)
			numLeft[i] = num[i];
		int j = right;
		for(int i= 0; i < size-right;i++){
			numRight[i] = num[j];
			j++;
		}
		
		root.left = sortedArrayToBST(numLeft);
		root.right = sortedArrayToBST(numRight);
		
		return root;
			
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
