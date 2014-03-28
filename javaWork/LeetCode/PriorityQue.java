package AnswersCheck;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQue {

	public static void main(String[] args) {
	 PriorityQue q = new PriorityQue();
	 int[] a = {100, 4, 200, 1, 3, 2};
	 //q.kthLargest(a,8);
	 System.out.println(q.longestConsecutive(a));

	}
	
	public int longestConsecutive(int[] num) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(num.length);
		int maxcount = 0, currcount=1;
		
		for(int i = 0;i<num.length;i++)
			pq.offer(num[i]);
		
		while(!pq.isEmpty()) {
			int temp = pq.poll();
			if(!pq.isEmpty() && temp + 1 == pq.peek())
				currcount++;
			else if (!pq.isEmpty() &&  temp == pq.peek())
					continue;
			else 
				currcount = 1;
			
			maxcount = Math.max(currcount, maxcount);
		}
		
		return maxcount;
    }
	
	public  void kthLargest(int[] a,int k) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(a.length,new CustomComparator());
		
		for(int i =0;i<a.length;i++)
			pq.offer(a[i]);
		
		
		
		while(!pq.isEmpty()) {
			k--;
			if(0 == k) {
				System.out.println(pq.poll());
				break;
			}
			pq.poll();
			
		}
		
	}
	
	
	 class CustomComparator implements Comparator<Integer> 
	{

		@Override
		public int compare(Integer o1, Integer o2) {
			
			if(o1 > o2)
				return -1;
			if(o1 < o2)
				return 1;
			
			return 0;
		}
		
	}
}
