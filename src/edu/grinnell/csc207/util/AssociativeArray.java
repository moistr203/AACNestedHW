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

    // +-----------+---------------------------------------------------
    // | Constants |
    // +-----------+

    /**
     * The default capacity of the initial array.
     */
    private static final int DEFAULT_CAPACITY = 16;

    // +--------+------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The size of the associative array (the number of key-value pairs).
     */
    private int size;

    /**
     * The array of key-value pairs.
     */
    private KVPair<K, V>[] pairs;

    /**
     * The current capacity of the associative array.
     */
    private int capacity;

    // +--------------+------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Creates a new, empty associative array with the default capacity.
     */
    @SuppressWarnings("unchecked")
    public AssociativeArray() {
        this.pairs = (KVPair<K, V>[]) new KVPair[DEFAULT_CAPACITY];
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
    }

    /**
     * Creates a copy of this associative array, including all key-value pairs.
     *
     * @param arr the array of key-value pairs
     * @param size the current size of the array
     * @param capacity the current capacity of the array
     */
    public AssociativeArray(KVPair<K, V>[] arr, int size, int capacity) {
        this.pairs = arr;
        this.size = size;
        this.capacity = capacity;
    }

    // +------------------+--------------------------------------------
    // | Public Methods |
    // +------------------+

    /**
     * Sets the value associated with the specified key.
     *
     * @param key the key to be added or updated
     * @param value the value to be associated with the specified key
     * @throws NullKeyException if the specified key is null
     */
    public void set(K key, V value) throws NullKeyException {
        if (key == null) {
            throw new NullKeyException("Key cannot be null.");
        }

        // Check if the key already exists, then update its value
        int pos = this.getKeyPos(key);
        if (pos > -1) {
            this.pairs[pos].set(key, value);
            return;
        }

        // If the key does not exist, add a new key-value pair
        this.pairs[size] = new KVPair<>(key, value);
        this.size++;
        this.resize();
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
     * @param key the key to be checked for existence
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

        // Shift elements to maintain order
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
    @SuppressWarnings("unchecked")
    public K[] keys() {
        K[] keysArray = (K[]) new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            keysArray[i] = this.pairs[i].getKey();
        }
        return keysArray;
    }

    /**
     * Creates a copy of this associative array, including all key-value pairs.
     *
     * @return a new copy of the associative array
     */
    public AssociativeArray<K, V> clone() {
        KVPair<K, V>[] copy = new KVPair[this.capacity];
        for (int i = 0; i < this.size; i++) {
            K key = this.pairs[i].getKey();
            V value = this.pairs[i].getValue();
            copy[i] = new KVPair<>(key, value);
        }
        return new AssociativeArray<>(copy, this.size, this.capacity);
    }

    /**
     * Returns a string representation of the associative array.
     *
     * @return a string representation of this associative array
     */
    @Override
    public String toString() {
        if (this.size == 0) {
            return "{}";
        }
        StringBuilder result = new StringBuilder("{ ");
        for (int i = 0; i < this.size - 1; i++) {
            result.append(this.pairs[i].toString()).append(", ");
        }
        result.append(this.pairs[this.size - 1]).append(" }");
        return result.toString();
    }

    // +-----------------+---------------------------------------------
    // | Private Methods |
    // +-----------------+

    /**
     * Expands the underlying array to accommodate more key-value pairs.
     */
    private void resize() {
        if (this.size < this.capacity) {
            return;
        }
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

    // +-----------------+---------------------------------------------
    // | Iterator Support |
    // +-----------------+

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

    // +-----------------+---------------------------------------------
    // | Test Cases |
    // +-----------------+

    public static void main(String[] args) {
        try {
            AssociativeArray<String, String> aa = new AssociativeArray<>();

            // Test 1: Adding elements
            aa.set("cat", "meow");
            aa.set("dog", "bark");
            assert aa.size() == 2 : "Test 1 failed";

            // Test 2: Retrieving elements
            assert aa.get("cat").equals("meow") : "Test 2 failed";

            // Test 3: Checking existence of keys
            assert aa.hasKey("dog") : "Test 3 failed";

            // Test 4: Removing elements
            aa.remove("cat");
            assert !aa.hasKey("cat") : "Test 4 failed";

            // Test 5: Size after removal
            assert aa.size() == 1 : "Test 5 failed";

            // Test 6: Adding an existing key to update
            aa.set("dog", "woof");
            assert aa.get("dog").equals("woof") : "Test 6 failed";

            System.out.println("All tests passed.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} // class AssociativeArray