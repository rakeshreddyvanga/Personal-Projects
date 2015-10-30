package graphProject.data_structures;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

	public MaxHeap(int capacity) {
		super(capacity);
	}
	public MaxHeap(T[] elements) {
		super(elements);
		buildHeap();
	}
	
	private void buildHeap() {
		if(size < 1)
			return;
		for(int i=size/2;i>=0;i--){
			maxHeapify(i);
		}
	}
	private void maxHeapify(int i) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public T poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void offer(T key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateKey(int index, T key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T[] sort() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
	
	

}
