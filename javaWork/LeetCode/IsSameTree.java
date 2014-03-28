package AnswersCheck;

public class IsSameTree {

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public static void main(String[] args) {

	}

	public boolean isSameTree(TreeNode p, TreeNode q) {

		if (p == null) {
			if (q == null)
				return true;
			else
				return false;
		}

		if (q == null) {
			if (p != null)
				return false;
		}

		if (p.val != q.val)
			return false;

		if (!isSameTree(p.left, q.left))
			return false;

		if (!isSameTree(p.right, q.right))
			return false;

		return true;

	}

	// you can also use imports, for example:
	// import java.math.*;

	/**
	 * @author Rakesh Reddy Vanga
	 * @version 1.0.0
	 * @param Tree
	 *            : Takes a root node of a tree
	 * @return Integer : total count of the visible nodes
	 */
	public int solution(TreeNode T) {
		if (T == null)
			return 0;
		return visible(T, T.val);
	}

	/**
	 * @author Rakesh Reddy Vanga
	 * @version 1.0.0
	 * @param Tree
	 *            : Takes a root node of a tree
	 * @param Integer
	 *            : takes the max value for the current node
	 * @return Integer : total count of the visible nodes
	 */
	public static int visible(TreeNode T, int max) {

		if (T == null) // when a tree is null then the visible counts for it are
						// zero
			return 0;

		int count = 0; // no.of counts to return

		if (max > T.val) // checking to add the root into the visible nodes count
						// if the max value is greater than the roots value then
						// the node is not visible

			// so checking the count in its left and right subtrees
			count += visible(T.left, max) + visible(T.right, max);

		else { // max value is less that the root value, so the node is visible
			max = T.val; // updating the max value
			// adding the root to visible nodes count and // counting the
			// visible nodes in left and right subtrees
			count += 1 + visible(T.left, max) + visible(T.right, max);
		}

		return count; // returning the count
	}

	/**
	 * @author Rakesh Reddy Vanga
	 * @version 1.0.0
	 * @param Integer
	 *            Array : Has the elements that define the pawan jump
	 * @return no of jumps of pawn to jump out of the array
	 */
	public static int solution(int[] A) {
		if (A.length == 0) // if input array is empty
			return -1;

		int jcount = 0, id = 0; // intializing the jumps and the initial index

		while (id < A.length) {
			if (jcount > A.length) // checking for the infinite loop of the
									// pawan jumps
				return -1;

			id = id + A[id];
			jcount++; // incrementing the count
			
			if (id < 0) // if the id is less than zero then the pawn is out of
						// the array from front
				return jcount;

			
		}

		return jcount;
	}
}
