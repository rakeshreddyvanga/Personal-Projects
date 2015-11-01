package graphProject.data_structures;

public class MinHeap<T extends Comparable<T>> extends Heap<T> {

	public MinHeap(int capacity) {
		super(capacity);
	}
	
	public MinHeap(T[] elements){
		super(elements);
		buildHeap();
	}
	
	@SuppressWarnings("unchecked")
	public T poll(){
		if(size < 1) //TODO throw new exception
			return null; 
		T min = (T)elements[0];
		elements[0] = elements[size-1];
		size--;
		minHeapify(0);
		return min;
		
	}
	
	@SuppressWarnings("unchecked")
	private void minHeapify(int idx){
		if(size < 1)
			return;
		int smallest = idx;
		int left = left(idx);
		int right = right(idx);
		if(left < size && ((T) elements[left]).compareTo((T) elements[idx]) == 1)
			smallest = idx;
		else
			smallest = left;
		if(right < size && ((T)elements[right]).compareTo((T)elements[smallest]) == -1 )
			smallest = right;
		
		if(smallest != idx && smallest < size){
			T temp = (T)elements[smallest];
			elements[smallest] = elements[idx];
			elements[idx] = temp;
			minHeapify(smallest);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public T[] sort(){
		buildHeap();
		for(int i = size-1; i>0 ; i--){
			T temp = (T)elements[0];
			elements[0] = elements[i];
			elements[i] = temp;
			size--;
			minHeapify(0);
		}
		
		return (T[])elements;
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
	@SuppressWarnings("unchecked")
	public void updateKey(int index, T key){
		if(index >= size)
			return; // TODO throw exception
		
		if(((T)elements[index]).compareTo(key) == -1)
			return; //TODO throw exception
		elements[index] = key;
		while(index > 0 && ((T)elements[parent(index)]).compareTo((T)elements[index]) == 1) {
			T temp =  (T)elements[parent(index)];
			elements[parent(index)] =  elements[index];
			elements[index] = temp;
			index = parent(index);
		}
		
	}
	
	
	public void offer(T key){
		if(size+1 > capacity)
			return; //TODO throw out of capacity exception
		size++;
		elements[size-1] = key;
		updateKey(size-1,key);		
	}
	

}
