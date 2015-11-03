package graphProject.data_structures;

import graphProject.data_structures.exceptions.HeapException;

public interface IHeap<T extends Comparable<T>>  {

	boolean isEmpty();
	
	public T peek();
	
	public T poll() throws HeapException;
	
	public void offer(T key) throws HeapException;
	
	public void updateKey(int index, T key) throws HeapException;
	
	public T[] sort() throws HeapException;
	
	public boolean contains(T key);

}