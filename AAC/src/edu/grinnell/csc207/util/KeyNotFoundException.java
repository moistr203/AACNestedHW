package edu.grinnell.csc207.util;
/**
 * An exception that is thrown when a key is not found in an associative
 * array, dictionary, map, or similar data structure.
 *
 */
public class KeyNotFoundException extends Exception {
  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Creates a new KeyNotFoundException with a default message.
   */
  public KeyNotFoundException() {
    super("Key not found.");
  } // KeyNotFoundException()

  /**
   * Creates a new KeyNotFoundException with a specified message.
   *
   * @param message The message that provides additional details.
   */
  public KeyNotFoundException(String message) {
    super(message);
  } // KeyNotFoundException(String)
} // class KeyNotFoundException