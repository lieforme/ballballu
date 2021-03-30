
public class LinkedListDeque<T> {
	private class StuffNode{
		public T item;
		public StuffNode next;
		public StuffNode prev;
		public StuffNode() {
			item = null;
			prev = null;
			next = null;
		}
		public StuffNode(T i, StuffNode n, StuffNode p) {
			item = i;
			next = n;
			prev = p;
		}
	}
	
	private StuffNode last;
	private StuffNode sentinel;
	private int size;
	
	public LinkedListDeque(T x){
		sentinel = new StuffNode();
		sentinel.next = new StuffNode(x,null,sentinel);
		last = sentinel.next;
		size = 1;
	}
	
	public LinkedListDeque() {
		sentinel = new StuffNode();
		size = 0;
	}
	
	public void addFirst(T item) {
		StuffNode t = new StuffNode(item,sentinel.next,last);
		sentinel.next = t;
		t.next.prev = t;
		size += 1;
	}
	
	public void addLast(T item) {
		StuffNode t = new StuffNode(item,sentinel.next,last);
		last.next = t;
		size += 1;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void printDeque() {
		StuffNode t = sentinel.next;
		while(t != null) {
			System.out.print(t.item + " ");
		}
		System.out.println();
	}
	
	public T removeFirst() {
		if(isEmpty() == true) {
			return null;
		}
		StuffNode t = sentinel.next;
		sentinel.next = t.next;
		sentinel.next.prev = last;
		size -= 1;
		return t.item;
	}
	
	public T removeLast() {
		if(isEmpty() == true) {
			return null;
		}
		StuffNode t = last;
		last.prev = last;
		last.next = null;
		last.next = sentinel.next;
		size -= 1;
		return t.item;
	}
	
	public T get(int index) {
		if (index < 0 || index >= size) {
			return null;
		}
		StuffNode t = sentinel.next;
		for(int i = 0; i < index; t = t.next) {
			i++;
		}
		return t.item;
	}
	
	public T getRecursive(int index) {
		if (index < 0) {
			return null;
		}
		return recursive(0,index,sentinel.next);
	}
	
	private T recursive(int x,int index, StuffNode A) {
		if(index == 0) {
			return A.item;
		}
		if(x >= size) {
			return null;
		}
		return recursive(x+1,index-1,A.next);
	}
}
