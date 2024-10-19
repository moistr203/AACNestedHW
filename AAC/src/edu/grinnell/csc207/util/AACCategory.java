import edu.grinnell.csc207.util.AssociativeArray;
import structures.AssociativeArray; // Assuming AssociativeArray is in the 'structures' package
import java.util.NoSuchElementException;

import edu.grinnell.csc207.util.KeyNotFoundException;
/**
 * Represents the mappings for a single category of items that should
 * be displayed
 *
 * @author Catie Baker & Moise
 * Creates a category of images and their texts
 * Help obtained: Evening Tutors
 */
public class AACCategory {
    // +--------+------------------------------------------------------------
    // | Fields |
    // +--------+
    private String name;
    private AssociativeArray<String, String> imageMap;

    // +-------------+--------------------------------------------------------
    // | Constructors|
    // +-------------+
    /*
     * Creates a new empty category with the given name
     */
    public AACCategory(String nameInput) {
      this.name = nameInput;
      this.imageMap = new AssociativeArray<String, String>();
    }

    // +--------+------------------------------------------------------------
    // | Methods|
    // +--------+

    /*
     * Adds the mapping of the imageLoc to the text to the category.
     * Throws NullKeyException if imageLoc or text is null.
     */
    public void addItem(String imageLoc, String text) throws NullKeyException {
      if (imageLoc == null || text == null) {
        throw new NullKeyException("Image location and text cannot be null.");
      }
      this.imageMap.set(imageLoc, text);
    }

    /*
     * Returns the name of the category
     */
    public String getCategory() {
      return this.name;
    }

    /*
     * Returns an array of all the images in the category
     */
    public String[] getImages() {
      String[] images = new String[this.imageMap.size()];
      int size = 0;
      for (KVPair<String, String> pair : this.imageMap) {
        images[size++] = pair.getKey();
      }
      return images;
    }

    /*
     * Returns the text associated with the given image loc in this category
     * Throws KeyNotFoundException if the imageLoc is not found.
     */
    public String getText(String imageLoc) throws KeyNotFoundException {
      if (this.hasImage(imageLoc)) {
        return this.imageMap.get(imageLoc);
      }
      throw new KeyNotFoundException("Image location not found in the category.");
    }

    /*
     * Determines if the provided image is stored in the category
     */
    public boolean hasImage(String imageLoc) {
      return this.imageMap.hasKey(imageLoc);
    }

    /*
     * Returns a string representation of the category
     */
    @Override
    public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("Category: ").append(this.name).append("\n");
      for (KVPair<String, String> pair : this.imageMap) {
        result.append("Image: ").append(pair.getKey())
              .append(" -> Text: ").append(pair.getValue()).append("\n");
      }
      return result.toString();
    }
  }