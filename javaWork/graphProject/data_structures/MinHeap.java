package graphProject.data_structures;

public class MinHeap<T extends Comparable<T>> extends Heap<T> {

	public MinHeap(int capacity) {
		super(capacity);
	}
	
	public MinHeap(T[] elements){
		super(elements);
		buildHeap();
	}
	
	public T poll(){
		if(size < 1) //TODO throw new exception
			return null; 
		T min = elements[0];
		elements[0] = elements[size-1];
		size--;
		minHeapify(0);
		return min;
		
	}
	
	private void minHeapify(int idx){
		if(size < 1)
			return;
		int smallest = idx;
		int left = left(idx);
		int right = right(idx);
		if(left < size && elements[left].compareTo(elements[idx]) == 1)
			smallest = idx;
		else
			smallest = left;
		if(right < size && elements[right].compareTo(elements[smallest]) == -1 )
			smallest = right;
		
		if(smallest != idx && smallest < size){
			T temp = elements[smallest];
			elements[smallest] = elements[idx];
			elements[idx] = temp;
			minHeapify(smallest);
		}
	}
	
	
	public T[] sort(){
		buildHeap();
		for(int i = size-1; i>0 ; i--){
			T temp = elements[0];
			elements[0] = elements[i];
			elements[i] = temp;
			size--;
			minHeapify(0);
		}
		
		return elements;
	}
	
	private void buildHeap(){
		if(size < 1)
			return;
		for(int i=size/2-1;i>=0;i--){
			minHeapify(i);
		}
	}
	/**
	 * Decreases the value at index to key's value.
	 * @param index
	 * @param key
	 */
	public void updateKey(int index, T key){
		if(index >= size)
			return; // TODO throw exception
		
		if(elements[index].compareTo(key) == -1)
			return; //TODO throw exception
		elements[index] = key;
		while(index >=0 && elements[parent(index)].compareTo(elements[index]) == 1) {
			T temp =  elements[parent(index)];
			elements[parent(index)] =  elements[index];
			elements[index] = temp;
			index = parent(index);
		}
		
	}
	
	
	public void offer(T key){
		size++;
		elements[size-1] = key;
		updateKey(size-1,key);		
	}
	

}
