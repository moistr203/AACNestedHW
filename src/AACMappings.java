import edu.grinnell.csc207.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Creates a set of mappings of an AAC that has two levels, one for categories
 * and then within each category, it has images that have associated text to be spoken.
 * This class provides the methods for interacting with the categories and updating
 * the set of images that would be shown and handling interactions.
 *
 * @author Catie Baker & Moise Milenge
 */
public abstract class AACMappings implements AACPage {

    private AssociativeArray<String, AACCategory> categories; // Maps category names to AACCategory instances
    private String currentCategory; // The current category being viewed/displayed

    /**
     * Creates a set of mappings for the AAC based on the provided
     * file. The file is read in to create categories and fill each
     * of the categories with initial items.
     *
     * @param filename the name of the file that stores the mapping information
     */
    public AACMappings(String filename) {
        categories = new AssociativeArray<>();
        currentCategory = "";
        loadMappings(filename); // Load the mappings from the provided file
    }

    /**
     * Loads the category and item mappings from a file.
     *
     * @param filename the name of the file to read
     */
    private void loadMappings(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            AACCategory category = null;  // Abstract class reference
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(">")) {
                    // This line defines a category
                    String[] parts = line.split(" ", 2);
                    String categoryImage = parts[0].trim();
                    String categoryName = parts[1].trim();

                    // Use the concrete implementation of AACCategory, e.g., AACCategoryImpl
                    category = new AACCategory(categoryName);  // Instantiate concrete class
                    categories.set(categoryImage, category);
                } else {
                    // This line defines an item in the current category
                    String[] parts = line.substring(1).split(" ", 2);
                    String imageLoc = parts[0].trim();
                    String text = parts[1].trim();
                    if (category != null) {
                        category.addItem(imageLoc, text);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NullKeyException e) {
            System.err.println("Error using associative array: " + e.getMessage());
        }
    }

    /**
     * Given the image location selected, it determines the action to be
     * taken. This can be updating the information that should be displayed
     * or returning text to be spoken.
     *
     * @param imageLoc the location where the image is stored
     * @return if there is text to be spoken, it returns that information, otherwise
     *         it returns the empty string
     * @throws NoSuchElementException if the image provided is not in the current
     *                                category
     */
    @Override
    public String select(String imageLoc) {
        try {
            if (categories.hasKey(imageLoc)) {
                // Change the category if the image represents a category
                currentCategory = categories.get(imageLoc).getCategory();
                return "";
            } else if (!currentCategory.equals("") && categories.get(currentCategory).hasImage(imageLoc)) {
                // If we're in a category, return the text for the selected image
                return categories.get(currentCategory).select(imageLoc);
            } else {
                System.out.println("No image selected: " + imageLoc);
                return " ";
            }
        } catch (KeyNotFoundException e) {
            System.out.println("No image selected: " + imageLoc);
            return " ";
        }
    }

    /**
     * Provides an array of all the images in the current category
     *
     * @return the array of images in the current category; if there are no images,
     *         it should return an empty array
     */
    @Override
    public String[] getImageLocs() {
        try {
            if (!currentCategory.equals("") && categories.hasKey(currentCategory)) {
                return categories.get(currentCategory).getImageLocs();
            }
        } catch (KeyNotFoundException e) {
            // Handle the exception, e.g., log it or provide feedback
            System.out.println("Category not found: " + currentCategory);
        }
        return new String[0]; // Return an empty array if no current category or exception occurs
    }

    /**
     * Resets the current category of the AAC back to the default
     * category (empty string).
     */
    public void reset() {
        currentCategory = ""; // Reset to default category (empty)
    }

    /**
     * Writes the AAC mappings stored to a file.
     *
     * @param filename the name of the file to write the AAC mapping to
     */
    public void writeToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Get the keys as an Object[] and cast each to a String
            Object[] categoryKeys = categories.keys(); // returns Object[]

            for (Object categoryKeyObj : categoryKeys) {
                String categoryKey = (String) categoryKeyObj; // Cast each key to String
                AACCategory category = categories.get(categoryKey);
                writer.write(categoryKey + " " + category.getCategory() + "\n");
                for (String imageLoc : category.getImageLocs()) {
                    writer.write(">" + imageLoc + " " + category.select(imageLoc) + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } catch (KeyNotFoundException e) {
            System.err.println("Error: Category not found.");
        }
    }

    /**
     * Adds the mapping to the current category (or the default category if
     * that is the current category)
     *
     * @param imageLoc the location of the image
     * @param text     the text associated with the image
     */
    public void addItem(String imageLoc, String text) {
        if (currentCategory.equals("")) {
            System.err.println("No current category set. Cannot add item.");
        } else {
            try {
                categories.get(currentCategory).addItem(imageLoc, text);
            } catch (KeyNotFoundException e) {
                System.err.println("Category not found: " + currentCategory);
            }
        }
    }

    /**
     * Gets the name of the current category
     *
     * @return returns the current category or the empty string if on the default
     *         category
     */
    @Override
    public String getCategory() {
        return currentCategory;
    }

    /**
     * Determines if the provided image is in the set of images that can be displayed
     * and false otherwise
     *
     * @param imageLoc the location of the image
     * @return true if it is in the set of images that can be displayed, false otherwise
     */
    @Override
    public boolean hasImage(String imageLoc) {
        try {
            if (!currentCategory.equals("") && categories.hasKey(currentCategory)) {
                return categories.get(currentCategory).hasImage(imageLoc);
            }
        } catch (KeyNotFoundException e) {
            // Handle the case where the key is not found
            System.out.println("Category not found: " + currentCategory);
        }
        return false;
    }
}
