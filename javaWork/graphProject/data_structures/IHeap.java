package graphProject.data_structures;

public interface IHeap<T extends Comparable<T>> {

	boolean isEmpty();
	
	public T peek();
	
	public T poll();
	
	public void offer(T key);
	
	public void updateKey(int index, T key);
	
	public T[] sort();

}