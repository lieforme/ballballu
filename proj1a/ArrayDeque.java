
public class ArrayDeque<T> {	
	private static int initialCapacity = 8;
	private static int eFactor = 2;
	private static int mCapacity = 16;
	private static double mRatio = 0.25;
	private static int cFactor = 2;
	private int capacity;
	private T[] items;
	private int nextFirst;
	private int nextLast;
	private int size;
	
	public ArrayDeque() {
		capacity = initialCapacity;
		items = (T[]) new Object[capacity];
		nextFirst = capacity - 1;
		nextLast = 0;
		size = 0;
	}
	private int oneMinus(int index){
		if (index == 0){
			return capacity - 1;
		}else{
			return index - 1;
		}
	}

	private int onePlus(int index){
		if(index == capacity - 1){
			return 0;
		}else{
			return index + 1;
		}
	}
	public void addFirst(T item) {
		items[nextFirst] = item;
		nextFirst = oneMinus(nextFirst);
		size += 1;

		expand();
	}
	
	public void addLast(T item) {
		items[nextLast] = item;
		nextLast = onePlus(nextLast);
		size += 1;

		expand();
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void printDeque() {
		if(isEmpty() == true) return;
		
		int start;
		if(nextFirst == capacity - 1) start = 0;
		else start = nextFirst + 1;
	
		for(int i = start; i != nextLast; i = (i+1)%(capacity)) {
			System.out.print(items[i]+" ");
		}
		System.out.println();
	}
	
	public T removeFirst() {
		if(isEmpty() == true) {
			return null;
		}
		int currentFirst = onePlus(nextFirst);
		T removed = items[currentFirst];
		items[currentFirst] = null;
		nextFirst = currentFirst;
		size -= 1;

		contract();

		return removed;
	}
	
	public T removeLast() {
		if(isEmpty() == true) {
			return null;
		}
		int currentLast = oneMinus(nextLast);
		T removed = items[currentLast];
		items[currentLast] = null;
		nextLast = currentLast;
		size -= 1;

		contract();

		return removed;
	}
	
	public T get(int index) {
		if (index >= size){
			return null;
		}

		int indexFromFront = nextFirst + 1 + index;
		if(indexFromFront >= capacity){
			indexFromFront -= capacity;
		}
		return items[indexFromFront];
	}

	private void resize(int newCapacity){
		T[] newItems = (T[]) new Object[newCapacity];

		int currentFirst = onePlus(nextFirst);
		int currentLast = oneMinus(nextLast);

		if(currentFirst < currentLast){
			int length = currentLast - currentFirst + 1;
			System.arraycopy(items, currentFirst, newItems, 0, length);
			nextFirst = newCapacity - 1;
			nextLast = length;
		}else{
			int lengthFirsts = capacity - currentFirst;
			int newCurrentFirst = newCapacity - lengthFirsts;
			int lengthLasts = nextLast;
			System.arraycopy(items, 0, newItems, newCurrentFirst, lengthLasts);
			System.arraycopy(items, 0, newItems, 0, lengthLasts);
			nextFirst = newCapacity - lengthFirsts -1;
		}

		capacity = newCapacity;
		items = newItems;
	}

	private void expand(){
		if (size == capacity){
			int newCapacity = capacity * eFactor;
			resize(newCapacity);
		}
	}

	private void contract() {
		double ratio = (double) size / capacity;
		if (capacity >= mCapacity && ratio < mRatio) {
			int newCapacity = capacity / cFactor;
			resize(newCapacity);
		}
	}

}
