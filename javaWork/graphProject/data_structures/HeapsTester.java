package graphProject.data_structures;

public class HeapsTester {

	public static void main(String[] args) {
		/*double ceilValue = Math.ceil(1/2d);
		System.out.println(ceilValue);
		System.out.println( (int)(ceilValue-1)); */
		//Integer [] elements = {8,2,9,1,7,3,6,4,5};
		MaxHeap<Integer> max = new MaxHeap<>(5);
		Integer five = new Integer(5);
		max.offer(five);
		max.offer(10);
		max.offer(15);
		max.offer(0);
		Integer val = max.poll();
		System.out.println("poll value: "+ val);
		max.offer(8);
		for(int i=0;i<5;i++)
			System.out.println(max.poll().intValue());
		
			
			

	}

}
