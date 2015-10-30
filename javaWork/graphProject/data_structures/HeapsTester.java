package graphProject.data_structures;

public class HeapsTester {

	public static void main(String[] args) {
		Integer [] elements = {8,2,9,1,7,3,6,4,5};
		MinHeap<Integer> min = new MinHeap<>(elements);
		for(int i=0;i<9;i++)
			System.out.println(min.poll().intValue());
		
			
			

	}

}
