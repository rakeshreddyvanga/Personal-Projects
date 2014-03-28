package AnswersCheck;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderTraversal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ArrayList<ArrayList<Integer>> leveOrder(TreeNode root) {
		if(root == null)
				return new ArrayList<ArrayList<Integer>>();
		
		ArrayList<ArrayList<Integer>> mret = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		int size=0;
		Queue<TreeNode> que = new LinkedList<TreeNode>();
		que.offer(root);
		size=que.size();
		while(size > 0) {
			TreeNode temp = que.poll();
			size--;
			ret.add(temp.val);
			
			if(temp.left != null)
				que.offer(temp.left);
			if(temp.right != null)
				que.offer(temp.right);
			
			if(size == 0) {
				mret.add(ret);
				ret = new ArrayList<Integer>();
				size = que.size();
			}
		}
		
		return mret;
	}
	public static void levelOrder(TreeNode root) {
		if(root == null)
				return;
		
		int size=0;
		Queue<TreeNode> que = new LinkedList<TreeNode>();
		que.offer(root);
		size=que.size();
		while(size > 0) {
			TreeNode temp = que.poll();
			size--;
			System.out.println(temp.val);
			
			if(temp.left != null)
				que.offer(temp.left);
			if(temp.right != null)
				que.offer(temp.right);
			
			if(size == 0) {
				System.out.println();
				size = que.size();
			}
		}
	}
	public static void print(TreeNode root) {
		if (root == null)
			return;

		Queue<TreeNode> q = new LinkedList<TreeNode>();
		Queue<TreeNode> q1 = new LinkedList<TreeNode>();
		q.offer(root);

		while (!q.isEmpty() && !q1.isEmpty()) {

			while (!q.isEmpty()) {
				TreeNode temp = q.poll();
				System.out.println(temp.val);
				q1.offer(temp);

			}

			while (!q1.isEmpty()) {
				TreeNode temp = q1.poll();

				if (temp.left != null)
					q.offer(temp.left);
				if (temp.right != null)
					q.offer(temp.right);
			}

			System.out.println();
		}

	}

}
