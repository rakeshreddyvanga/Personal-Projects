package graphProject.data_structures;

import graphProject.data_structures.exceptions.HeapException;

public class MinHeap<T extends Comparable<T>> extends Heap<T> {

	public MinHeap(int capacity) {
		super(capacity);
	}
	
	public MinHeap(T[] elements) throws HeapException{
		super(elements);
		buildHeap();
	}
	
	@SuppressWarnings("unchecked")
	public T poll() throws HeapException{
		if(size < 1) //TODO throw new exception
			throw new HeapException("Heap is empty.");
		T min = (T)elements[0];
		set.remove(min);
		elements[0] = elements[size-1];
		size--;
		minHeapify(0);
		return min;
		
	}
	
	@SuppressWarnings("unchecked")
	private void minHeapify(int idx) throws HeapException{
		if(size < 1)
			return;//throw new HeapException("Heap is empty.");
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
	public T[] sort() throws HeapException{
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
	
	public void buildHeap() throws HeapException{
		if(size < 1)
			throw new HeapException("Heap is empty.");
		for(int i=size/2-1;i>=0;i--){
			minHeapify(i);
		}
	}
	/**
	 * Decreases the value at index to key's value.
	 * @param index
	 * @param key
	 * @throws HeapException 
	 */
	@SuppressWarnings("unchecked")
	public void updateKey(int index, T key) throws HeapException{
		if(index >= size)
			throw new IndexOutOfBoundsException("Cannot access Index: "+index+" from heap of size: "+size);
		
		if(((T)elements[index]).compareTo(key) == -1)
			throw new HeapException("The "+index+"'s value is already smaller.");
		elements[index] = key;
		while(index > 0 && ((T)elements[parent(index)]).compareTo((T)elements[index]) == 1) {
			T temp =  (T)elements[parent(index)];
			elements[parent(index)] =  elements[index];
			elements[index] = temp;
			index = parent(index);
		}
		
	}
	
	
	public void offer(T key) throws HeapException{
		if(size+1 > capacity)
			throw new HeapException("Cannot insert, heap is full.");
		size++;
		elements[size-1] = key;
		set.add(key);
		updateKey(size-1,key);		
	}
	

}
