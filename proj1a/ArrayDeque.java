
public class ArrayDeque<T> {
	public ArrayDeque(int nextFirst, int nextLast) {
		this.nextFirst = nextFirst;
		this.nextLast = nextLast;
		this.size = 0;
		this.length = 8;
		for(int i = 0; i < length; i++) {
			items[i] = null;
		}
	}
	
	private int size;
	private int nextFirst;
	private int nextLast;
	private int length;
	private T[] items = (T[]) new Object[8];
	
	public void addFirst(T item) {
		items[nextFirst] = item;
		if(nextFirst == 0) nextFirst = length-1;
		else nextFirst -= 1;
		size += 1;
	}
	
	public void addLast(T item) {
		items[nextLast] = item;
		nextLast = (nextLast+1)%length;
		size += 1;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void printDeque() {
		if(isEmpty() == true) return;
		
		int start, end;
		start = (nextFirst+1)%length;
		if (nextLast == 0) end = length-1;
		else end = nextLast-1;
		
		for(int i = start; i <= end; i++) {
			System.out.print(items[i]+" ");
		}
		System.out.println();
	}
	
	public T removeFirst() {
		if(isEmpty() == true) return null;
		nextFirst = (nextFirst+1)%length;
		T temp = items[nextFirst];
		items[nextFirst] = null;
		return temp;
	}
	
	public T removeLast() {
		if(isEmpty() == true) return null;
		if (nextLast == 0) nextLast = length-1;
		else nextLast = nextLast-1;
		T temp = items[nextLast];
		items[nextLast] = null;
		return temp;
	}
	
	public T get(int index) {
		return items[index];
	}
}
