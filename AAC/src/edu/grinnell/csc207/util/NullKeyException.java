package edu.grinnell.csc207.util;
/**
 * An exception that is thrown when a null key is used in an associative
 * array, dictionary, map, or similar data structure.
 * <p>
 * This exception should be thrown to indicate that null keys are not
 * permitted in the data structure.
 * </p>
 *
 */
public class NullKeyException extends Exception {
  // +--------------+------------------------------------------------

  /**
   * Creates a new NullKeyException with a default message.
   */
  public NullKeyException() {
    super("Null key provided.");
  } // NullKeyException()

  /**
   * Creates a new NullKeyException with a specified message.
   *
   * @param message The message that provides additional details.
   */
  public NullKeyException(String message) {
    super(message);
  } // NullKeyException(String)
} // class NullKeyException