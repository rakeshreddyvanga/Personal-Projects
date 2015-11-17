package graphProject.data_structures;



import graphProject.data_structures.exceptions.HeapException;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

	public MaxHeap(int capacity) {
		super(capacity);
	}
	public MaxHeap(T[] elements) throws HeapException {
		super(elements);
		buildHeap();
	}
	
	public void buildHeap() throws HeapException {
		if(size < 1)
			throw new HeapException("Heap is empty.");
		for(int i=size/2-1;i>=0;i--){
			maxHeapify(i);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void maxHeapify(int idx) throws HeapException {
		if(size < 1)
			throw new HeapException("Heap is empty.");
		int largest = idx;
		int left = left(idx);
		int right = right(idx);
		if(left < size && ((T)elements[left]).compareTo((T)elements[idx])>0)
			largest = left;
		else
			largest = idx;
		if(right < size && ((T)elements[right]).compareTo((T)elements[largest]) > 0 )
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
	public T poll() throws HeapException {
		if(size < 1) //TODO throw new exception
			throw new HeapException("Heap is empty.");
		T maxValue = (T)elements[0];
		elements[0] = elements[size-1];
		size--;
		maxHeapify(0);
		return maxValue;
		
	}

	@Override
	public void offer(T key) throws HeapException {
		if(size+1 > capacity)
			throw new HeapException("Cannot insert, heap is full.");
		size++;
		elements[size-1] = key;
		updateKey(size-1,key);		
	}
	/**
	 * Increases index's value to the passed in key value
	 * @throws HeapException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateKey(int index, T key) throws HeapException {
		if(index >= size)
			throw new IndexOutOfBoundsException("Cannot access Index: "+index+" from heap of size: "+size);
		
		if(((T)elements[index]).compareTo(key)> 0)
			throw new HeapException("The "+index+"'s value is already greater.");
		elements[index] = key;
		while(index > 0 && ((T)elements[parent(index)]).compareTo((T)elements[index]) < 0) {
			T temp =  (T)elements[parent(index)];
			elements[parent(index)] =  elements[index];
			elements[index] = temp;
			index = parent(index);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] sort() throws HeapException {
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
