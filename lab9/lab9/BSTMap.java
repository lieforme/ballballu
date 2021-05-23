package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private BSTNode root = null;
    int totalSize = 0;
    HashSet<K> keyset = new HashSet<>();

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;
        int size;

        BSTNode(K k, V v) {
            this.key = k;
            this.value = v;
            this.left = null;
            this.right = null;
            this.size = 0;
        }
    }

    public void clear() {
        root = null;
        totalSize = 0;
    }

    public boolean containsKey(K key) {
        return findHelper(root, key) != null;
    }

    private BSTNode findHelper(BSTNode root, K key) {
        if(root == null) return null;
        else if(root.key.equals(key)) return root;
        else if(key.compareTo(root.key) > 0) return findHelper(root.right, key);
        else return findHelper(root.left, key);
    }

    public V get(K key) {
        BSTNode result = findHelper(root, key);
        if(result == null) return null;
        else return result.value;
    }

    public int size() {
        return totalSize;
    }

    public void put(K key, V value) {
        root = putHelper(key, value, root);
        keyset.add(key);
    }

    private BSTNode putHelper(K key, V value, BSTNode root) {
        if( root == null ) {
            totalSize += 1;
            return new BSTNode(key, value);
        }
        else if ( key.compareTo(root.key) < 0) {
            root.left = putHelper(key, value, root.left);
            root.size = root.left.size + 1;
        } else if ( key.compareTo(root.key) > 0){
            root.right = putHelper(key, value, root.right);
            root.size = root.right.size + 1;
        } else root.value = value;
        return root;
    }

    /** Only for test **/
    public void printInOrder() {
        Iterator<K> myIterator = iterator();
        while(myIterator.hasNext()) {
            System.out.println(myIterator.next());
        }
    }

    public Set<K> keySet() {
        return keyset;
    }

    V returnV;
    public V remove(K key) {
        returnV = null;
        root = removeHelper(root, key);
        return returnV;
    }

    private BSTNode removeHelper(BSTNode root, K key) {
        if(root == null) return null;
        if(key.compareTo(root.key) < 0) root.left = removeHelper(root.left, key);
        else if(key.compareTo(root.key) > 0) root.right = removeHelper(root.right, key);

            // found the key!
        else {
            totalSize -= 1;
            returnV = root.value;
            keyset.remove(key);

            if(root.left == null) return root.right;
            else if(root.right == null) return root.left;
            else root.right = swapSmallest(root.right, root);
        }
        return root;
    }

    private BSTNode swapSmallest(BSTNode T, BSTNode R) {
        if( T.left == null ) {
            R.value = T.value;
            R.key = T.key;
            return T.right;
        } else {
            T.left = swapSmallest(T.left, R);
            return T;
        }
    }

    public V remove(K key, V value) {
        returnV = null;
        root = removeHelper(root, key);
        return returnV;
    }

    public Iterator<K> iterator() {
        return new BSTiterator();
    }

    private class BSTiterator implements Iterator<K> {
        BSTNode curNode = root;
        Deque<BSTNode> stk = new LinkedList<>();

        @Override
        public boolean hasNext() {
            if(curNode == null && stk.isEmpty()) return false;
            return true;
        }

        @Override
        public K next() {
            if(hasNext() == false) return null;
            else {
                while(curNode != null) {
                    stk.push(curNode);
                    curNode = curNode.left;
                }
                curNode = stk.pop();
                K returnValue = curNode.key;
                curNode = curNode.right;
                return returnValue;
            }
        }
    }
}
