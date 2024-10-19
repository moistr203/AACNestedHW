package edu.grinnell.csc207.util;
/**
 * Represents a key-value pair for use in associative arrays or other
 * data structures.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 */
public class KVPair<K, V> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The key associated with this key-value pair.
   */
  private K key;

  /**
   * The value associated with the key in this key-value pair.
   */
  private V val;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Creates a new empty key-value pair with both key and value as null.
   */
  public KVPair() {
    this(null, null);
  } // KVPair()

  /**
   * Creates a new key-value pair with the specified key and value.
   *
   * @param pairKey The key of the new pair.
   * @param pairValue The value of the new pair.
   */
  public KVPair(K pairKey, V pairValue) {
    this.key = pairKey;
    this.val = pairValue;
  } // KVPair(K, V)

  // +------------------+--------------------------------------------
  // | Standard methods |
  // +------------------+

  /**
   * Creates a copy of this key-value pair.
   *
   * @return A new {@code KVPair} object that is a copy of this pair.
   */
  public KVPair<K, V> clone() {
    return new KVPair<>(this.key, this.val);
  } // clone()

  /**
   * Converts the key-value pair to a string for easy viewing.
   *
   * @return A string representation of the form "key:value".
   */
  @Override
  public String toString() {
    return (this.val == null)
        ? this.key.toString() + ":<null>"
        : this.key.toString() + ":" + this.val.toString();
  } // toString()

  /**
   * Gets the key of this key-value pair.
   *
   * @return The key of this pair.
   */
  public K getKey() {
    return this.key;
  } // getKey()

  /**
   * Gets the value associated with the key of this pair.
   *
   * @return The value of this pair.
   */
  public V getValue() {
    return this.val;
  } // getValue()

  /**
   * Sets the key of this key-value pair.
   *
   * @param newKey The new key.
   */
  public void setKey(K newKey) {
    this.key = newKey;
  } // setKey(K)

  /**
   * Sets the value associated with the key of this pair.
   *
   * @param newVal The new value.
   */
  public void setValue(V newVal) {
    this.val = newVal;
  } // setValue(V)

  public void set(K key2, V value) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'set'");
  }
} // class KVPair
