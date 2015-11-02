package graphProject.data_structures;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

	public MaxHeap(int capacity) {
		super(capacity);
	}
	public MaxHeap(T[] elements) {
		super(elements);
		buildHeap();
	}
	
	public void buildHeap() {
		if(size < 1)
			return;
		for(int i=size/2-1;i>=0;i--){
			maxHeapify(i);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void maxHeapify(int idx) {
		if(size < 1)
			return;
		int largest = idx;
		int left = left(idx);
		int right = right(idx);
		if(left < size && ((T)elements[left]).compareTo((T)elements[idx]) == 1)
			largest = left;
		else
			largest = idx;
		if(right < size && ((T)elements[right]).compareTo((T)elements[largest]) == 1 )
			largest = right;
		
		if(largest != idx && largest < size){
			T temp = (T)elements[largest];
			elements[largest] = elements[idx];
			elements[idx] = temp;
			maxHeapify(largest);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T poll() {
		if(size < 1) //TODO throw new exception
			return null; 
		T maxValue = (T)elements[0];
		elements[0] = elements[size-1];
		size--;
		maxHeapify(0);
		return maxValue;
		
	}

	@Override
	public void offer(T key) {
		if(size+1 > capacity)
			return; //TODO throw out of capacity exception
		size++;
		elements[size-1] = key;
		updateKey(size-1,key);		
	}
	/**
	 * Increases index's value to the passed in key value
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateKey(int index, T key) {
		if(index >= size)
			return; // TODO throw exception
		
		if(((T)elements[index]).compareTo(key) == 1)
			return; //TODO throw exception
		elements[index] = key;
		while(index > 0 && ((T)elements[parent(index)]).compareTo((T)elements[index]) == -1) {
			T temp =  (T)elements[parent(index)];
			elements[parent(index)] =  elements[index];
			elements[index] = temp;
			index = parent(index);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] sort() {
		buildHeap();
		for(int i = size-1; i>0 ; i--){
			T temp = (T)elements[0];
			elements[0] = elements[i];
			elements[i] = temp;
			size--;
			maxHeapify(0);
		}
		
		return (T[])elements;
	}
	

	
	
	
	

}
