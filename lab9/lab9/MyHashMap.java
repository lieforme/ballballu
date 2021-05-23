package lab9;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private int InitialSize = 16;
    private double InitialLoadFactor = 0.75;
    private int size;
    private double loadFactor;
    private int resizeFactor = 2;
    private int numOfNodes;
    private Set<K> keyset = new HashSet<>();
    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /**
     * Constructors
     */
    public MyHashMap() {
        size = InitialSize;
        loadFactor = InitialLoadFactor;
        numOfNodes = 0;
        buckets = createTable(size);
    }

    public MyHashMap(int initialSize) {
        InitialSize = initialSize;
        size = initialSize;
        loadFactor = InitialLoadFactor;
        numOfNodes = 0;
        buckets = createTable(size);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        InitialSize = initialSize;
        InitialLoadFactor = maxLoad;
        size = initialSize;
        loadFactor = maxLoad;
        numOfNodes = 0;
        buckets = createTable(size);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     * <p>
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    @Override
    public void clear() {
        size = InitialSize;
        loadFactor = InitialLoadFactor;
        buckets = createTable(size);
        numOfNodes = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), size);
        if (buckets[index] == null) {
            return false;
        } else {
            Iterator<Node> thisBucket = buckets[index].iterator();
            K curKey;
            while (thisBucket.hasNext()) {
                curKey = thisBucket.next().key;
                if (curKey.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), size);
        if (buckets[index] == null) {
            return null;
        } else {
            Iterator<Node> thisBucket = buckets[index].iterator();
            Node curNode;
            while (thisBucket.hasNext()) {
                curNode = thisBucket.next();
                if (curNode.key.equals(key)) {
                    return curNode.value;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return numOfNodes;
    }

    @Override
    public void put(K key, V value) {
        int index = Math.floorMod(key.hashCode(), size);
        if (buckets[index] == null) {
            buckets[index] = createBucket();
            buckets[index].add(createNode(key, value));
            keyset.add(key);
            numOfNodes += 1;
        } else {
            Iterator<Node> thisBucket = buckets[index].iterator();
            Node curNode;
            while (thisBucket.hasNext()) {
                curNode = thisBucket.next();
                if (curNode.key.equals(key)) {
                    curNode.value = value;
                    return;
                }
            }
            buckets[index].add(createNode(key, value));
            keyset.add(key);
            numOfNodes += 1;
        }
        if (numOfNodes/size >= loadFactor) {
            resize(size);
        }
    }

    @Override
    public Set<K> keySet() {
        return keyset;
    }

    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), size);
        if (buckets[index] == null) {
            return null;
        } else {
            Iterator<Node> thisBucket = buckets[index].iterator();
            Node curNode;
            int i = 0;
            while (thisBucket.hasNext()) {
                curNode = thisBucket.next();
                if (curNode.key.equals(key)) {
                    V returnValue = curNode.value;
                    buckets[index].remove(curNode);
                    return returnValue;
                }
                i += 1;
            }
            return null;
        }
    }

    @Override
    public V remove(K key, V value) {
        int index = Math.floorMod(key.hashCode(), size);
        if (buckets[index] == null) {
            return null;
        } else {
            Iterator<Node> thisBucket = buckets[index].iterator();
            Node curNode;
            int i = 0;
            while (thisBucket.hasNext()) {
                curNode = thisBucket.next();
                if (curNode.key.equals(key)) {
                    if (!curNode.value.equals(value)) {
                        return null;
                    }
                    V returnValue = curNode.value;
                    buckets[index].remove(curNode);
                    return returnValue;
                }
                i += 1;
            }
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    private class KeyIterator implements Iterator<K> {
        private int curIndex;
        private Iterator<Node> curIterator;

        private KeyIterator() {
            int i = 0;
            while( buckets[i] == null ) {
                i += 1;
            }
            curIndex = i;
            curIterator = buckets[curIndex].iterator();
        }

        @Override
        public boolean hasNext() {
            if (curIterator.hasNext()) {
                return true;
            } else {
                int i = curIndex + 1;
                while ( i < size && buckets[i] == null ) {
                    i += 1;
                }
                if (i == size) {
                    return false;
                }
                curIterator = buckets[i].iterator();
                return true;
            }
        }

        @Override
        public K next() {
            if( hasNext() ) {
                return curIterator.next().key;
            }
            else {
                return null;
            }
        }
    }

    private void resize(int curSize) {
        int newSize = curSize * resizeFactor;
        Collection<Node>[] newBuckets = createTable(newSize);
        for (int i = 0; i < curSize; i++) {
            if (buckets[i] == null) {
                continue;
            } else {
                int index;
                Iterator<Node> thisBucket = buckets[i].iterator();
                Node curNode;
                while (thisBucket.hasNext()) {
                    curNode = thisBucket.next();
                    index = Math.floorMod(curNode.key.hashCode(), newSize);
                    if( newBuckets[index] == null ) {
                        newBuckets[index] = createBucket();
                    }
                    newBuckets[index].add(createNode(curNode.key, curNode.value));
                }
            }
        }
        buckets = newBuckets;
        size = newSize;
    }
}
