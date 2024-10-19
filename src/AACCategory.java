import edu.grinnell.csc207.util.AssociativeArray;
import structures.AssociativeArray; // Assuming AssociativeArray is in the 'structures' package
import java.util.NoSuchElementException;

import edu.grinnell.csc207.util.KeyNotFoundException;

/**
 * Creates a category of images and their texts
 *
 * @author Catie Baker
 * @author Moise Milenge
 * Help obtained: Evening Tutors
 */
public class AACCategory {
    // +--------+------------------------------------------------------------
    // | Fields |
    // +--------+
    private String name;
    private AssociativeArray<String, String> imageMap;

    // +--------+------------------------------------------------------------
    // | Constructors|
    // +-------------+
    /**
     * Creates a new empty category with the given name.
     *
     * @param nameInput the name of the category
     */
    public AACCategory(String nameInput) {
        this.name = nameInput;
        this.imageMap = new AssociativeArray<>();
    }

    // +--------+------------------------------------------------------------
    // | Methods|
    // +--------+

    /**
     * Adds the mapping of the imageLoc to the text to the category.
     *
     * @param imageLoc the location of the image
     * @param text     the text associated with the image
     */
    public void addItem(String imageLoc, String text) {
        try {
            this.imageMap.set(imageLoc, text);  // NullKeyException may be thrown here
        } catch (NullKeyException e) {  // Handling the custom NullKeyException
            System.err.println("Error: Invalid (null) key provided.");
            e.printStackTrace();
        }
    }

    /**
     * Returns the name of the category.
     *
     * @return the name of the category
     */
    public String getCategory() {
        return this.name;
    }

    /**
     * Returns an array of all the images in the category.
     *
     * @return an array of image locations
     */
    public String[] getImages() {
        String[] images = new String[this.imageMap.size()];
        int size = 0;
        for (KVPair<String, String> pair : this.imageMap) {
            images[size++] = pair.getKey();  // Fixed method to getKey()
        }
        return images;
    }

    /**
     * Returns the text associated with the given image location in this category.
     *
     * @param imageLoc the location of the image
     * @return the text associated with the image
     * @throws NoSuchElementException if the image location is not found
     */
    public String getText(String imageLoc) {
        return this.imageMap.get(imageLoc);  // KeyNotFoundException may be thrown here
    }

    /**
     * Determines if the provided image is stored in the category.
     *
     * @param imageLoc the location of the image
     * @return true if the image is found, false otherwise
     */
    public boolean hasImage(String imageLoc) {
        return this.imageMap.hasKey(imageLoc);
    }

    /**
     * Returns a string representation of the category.
     *
     * @return the string representation of the category
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (KVPair<String, String> pair : this.imageMap) {
            result.append(">").append(pair.getKey()).append(" ").append(pair.getValue()).append("\n");
        }
        return result.toString();
    }
}