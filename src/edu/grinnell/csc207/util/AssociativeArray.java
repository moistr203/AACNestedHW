package edu.grinnell.csc207.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A basic implementation of associative arrays, which store key-value pairs
 * and allow lookup of values by key. The associative array is implemented
 * as a dynamically expanding array of {@link KVPair} objects.
 *
 * @param <K> the type of keys maintained by this associative array
 * @param <V> the type of mapped values
 *
 * @author Moise Milenge
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> implements Iterable<KVPair<K, V>> {

    private static final int DEFAULT_CAPACITY = 16;
    private KVPair<K, V>[] pairs;
    private int size;
    private int capacity;

    /**
     * Constructs an empty associative array with the default initial capacity.
     */
    @SuppressWarnings("unchecked")
    public AssociativeArray() {
        this.pairs = (KVPair<K, V>[]) new KVPair[DEFAULT_CAPACITY];
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Sets the value associated with the specified key. If the key already exists,
     * its value is updated.
     *
     * @param key the key to be added or updated
     * @param value the value to be associated with the specified key
     * @throws NullKeyException if the specified key is null
     */
    public void set(K key, V value) throws NullKeyException {
        if (key == null) {
            throw new NullKeyException("Key cannot be null.");
        }

        int pos = this.getKeyPos(key);
        if (pos > -1) {
            this.pairs[pos].set(key, value);
            return;
        }

        if (size >= capacity) {
            resize();
        }

        this.pairs[size] = new KVPair<>(key, value);
        this.size++;
    }

    /**
     * Gets the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key
     * @throws KeyNotFoundException if the key does not exist in the associative array
     */
    public V get(K key) throws KeyNotFoundException {
        int pos = this.getKeyPos(key);
        if (pos > -1) {
            return this.pairs[pos].getValue();
        }
        throw new KeyNotFoundException("Key not found.");
    }

    /**
     * Checks if the associative array contains the specified key.
     *
     * @param key the key to check for existence
     * @return {@code true} if the key is found, {@code false} otherwise
     */
    public boolean hasKey(K key) {
        return this.getKeyPos(key) > -1;
    }

    /**
     * Removes the key-value pair associated with the specified key.
     *
     * @param key the key whose key-value pair is to be removed
     */
    public void remove(K key) {
        int pos = this.getKeyPos(key);
        if (pos == -1) {
            return; // Key not found, do nothing
        }

        this.pairs[pos] = this.pairs[size - 1];
        this.pairs[size - 1] = null;
        this.size--;
    }

    /**
     * Returns the number of key-value pairs in the associative array.
     *
     * @return the number of key-value pairs in this associative array
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns an array of all keys in the associative array.
     *
     * @return an array of keys
     */
    public K[] keys() {
        K[] keysArray = (K[]) new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            keysArray[i] = this.pairs[i].getKey();
        }
        return keysArray;
    }

    /**
     * Creates an iterator to iterate through the key-value pairs in the associative array.
     *
     * @return an iterator over the key-value pairs
     */
    @Override
    public Iterator<KVPair<K, V>> iterator() {
        return new AssociativeArrayIterator();
    }

    private class AssociativeArrayIterator implements Iterator<KVPair<K, V>> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public KVPair<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return pairs[currentIndex++];
        }
    }

    /**
     * Expands the underlying array to accommodate more key-value pairs.
     */
    private void resize() {
        KVPair<K, V>[] newArray = new KVPair[this.capacity * 2];
        System.arraycopy(this.pairs, 0, newArray, 0, this.size);
        this.pairs = newArray;
        this.capacity *= 2;
    }

    /**
     * Finds the index of the key-value pair containing the specified key.
     *
     * @param key the key to find in the associative array
     * @return the index of the key-value pair containing the key, or -1 if not found
     */
    private int getKeyPos(K key) {
        for (int i = 0; i < this.size; i++) {
            if (this.pairs[i].getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }
}