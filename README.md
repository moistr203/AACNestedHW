# Mini-Project 5: Augmentative and Alternative Communication Devices

## Overview
This project implements an Augmentative and Alternative Communication (AAC) system using an extended version of the `AssociativeArray` class. The goal is to allow users to navigate categories and communicate using images mapped to words.

## Project Structure
- **edu.grinnell.csc207.util Package**:
  - `AssociativeArray<K, V>`: Stores key-value pairs in a custom array-based map.
  - `KVPair<K, V>`: Represents a key-value pair used in the `AssociativeArray`.
  - **Exceptions**:
    - `NullKeyException`: Thrown if a null key is passed.
    - `KeyNotFoundException`: Thrown if a key is not found.

- **AACCategory Class**:
  - Maps image locations (strings) to corresponding words.
  - Supports retrieval of image locations, selecting images, and returning associated text.

- **AACMappings Class**:
  - Manages categories using `AACCategory` objects.
  - Maps filenames to categories and allows switching between them.

## Usage
1. **Running the Application**:
   - Run the main application. It may take a few seconds to load.

2. **Navigating Categories**:
   - Users can switch between AAC categories, where each category maps images to words.

3. **Communicating**:
   - Select an image to get the associated word.

## Notes
- This project does not use Maven.
- Stubs are provided for some methods and need to be implemented.
- Modify `AAC.java` by commenting out line 52 and uncommenting line 53 to switch to `AACMappings`.
- Test files (`TestAACCategory.java`, `TestAACMappings.java`) can be used in VSCode for testing.

## Acknowledgments
- **Moise Milenge**: Project development.
- **Samuel A. Rebelsky**: Assignment and guidance.
- **Catie Baker**: Original project idea and code contributions.
- **GeeksforGeeks**: Reference for `TextSpeech` implementation.