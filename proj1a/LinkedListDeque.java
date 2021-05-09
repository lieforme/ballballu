
public class LinkedListDeque<T> {
	private class StuffNode{
		T item;
		StuffNode next;
		StuffNode prev;
		
		public StuffNode(T i, StuffNode p, StuffNode n) {
			item = i;
			next = n;
			prev = p;
		}
	}

	private StuffNode sentinel;
	private int size;
	
	public LinkedListDeque() {
		sentinel = new StuffNode(null,null,null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;
	}
	
	public void addFirst(T item) {
		sentinel.next = new StuffNode(item, sentinel, sentinel.next);
		sentinel.next.next.prev = sentinel.next;
		size += 1;
	}
	
	public void addLast(T item) {
		sentinel.prev = new StuffNode(item, sentinel.prev, sentinel);
		sentinel.prev.prev.next = sentinel.prev;
		size += 1;
	}
	
	public boolean isEmpty() {
		if(sentinel.next == sentinel && sentinel.prev == sentinel && size == 0) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return size;
	}
	
	public void printDeque() {
		StuffNode current = sentinel;
		while(current.next != sentinel) {
			System.out.print(current.item + " ");
			current = current.next;
		}
		System.out.println();
	}
	
	public T removeFirst() {
		if(sentinel.next == sentinel) {
			return null;
		}
		
		StuffNode t = sentinel.next;
		sentinel.next = t.next;
		sentinel.next.prev = sentinel;
		size -= 1;
		return t.item;
	}
	
	public T removeLast() {
		if(sentinel.prev == sentinel) {
			return null;
		}
		StuffNode t = sentinel.prev;
		sentinel.prev = sentinel.prev.prev;
		sentinel.prev.next = sentinel;
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
	
	public void main(String[] args) {
		
	}
}
