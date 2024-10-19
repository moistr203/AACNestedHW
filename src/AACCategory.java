import edu.grinnell.csc207.util.AssociativeArray;
import edu.grinnell.csc207.util.KVPair;
import edu.grinnell.csc207.util.KeyNotFoundException;
import edu.grinnell.csc207.util.NullKeyException;
import java.util.NoSuchElementException;

/**
 * Creates a category of images and their texts.
 *
 * @author Catie Baker
 * @author Moise Milenge
 * Help obtained: Evening Tutors
 */
public class AACCategory {
    // Fields
    private String name;
    private AssociativeArray<String, String> imageMap;

    // Constructors
    /**
     * Creates a new empty category with the given name.
     *
     * @param nameInput the name of the category
     */
    public AACCategory(String nameInput) {
        this.name = nameInput;
        this.imageMap = new AssociativeArray<>();
    }

    // Methods

    /**
     * Adds the mapping of the imageLoc to the text to the category.
     *
     * @param imageLoc the location of the image
     * @param text     the text associated with the image
     */
    public void addItem(String imageLoc, String text) {
        try {
            this.imageMap.set(imageLoc, text);  // Handle potential NullKeyException here
        } catch (NullKeyException e) {
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
            images[size++] = pair.getKey();  // Get the image location
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
        try {
            return this.imageMap.get(imageLoc);  // Handle KeyNotFoundException
        } catch (KeyNotFoundException e) {
            throw new NoSuchElementException("Image location not found: " + imageLoc);
        }
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

    // Newly added methods

    /**
     * Returns an array of image locations (paths) in the category.
     *
     * @return an array of image locations
     */
    public String[] getImageLocs() {
        return getImages();  // This method is essentially an alias for getImages()
    }

    /**
     * Returns the text associated with the given image location (selected).
     *
     * @param imageLoc the location of the image
     * @return the text associated with the image
     */
    public String select(String imageLoc) {
        return getText(imageLoc);  // This method is an alias for getText()
    }
}