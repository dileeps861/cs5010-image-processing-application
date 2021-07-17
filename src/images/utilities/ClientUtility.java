package images.utilities;

import images.model.image.Image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;



/**
 * Utility interface to do the file serialization processes. This interface can
 * write and read the files of type such as Image and text.
 * 
 * @author dileepshah
 *
 */
public interface ClientUtility {

  /**
   * Utility method to save the image to file system.
   * 
   * @param image    the image to be saved.
   * @param fileName the name of the desired file
   * @throws IOException the IO Exception
   */
  void saveImageFile(Image image, String fileName) throws IllegalArgumentException, IOException;

  /**
   * Load the image from file System.
   * 
   * @param fileName the file name to load the image file
   * @return the loaded Image
   * @throws IOException the IO Exception
   */
  Image loadImage(String fileName) throws IllegalArgumentException, IOException;

  /**
   * Save the text file into file system.
   * 
   * @param data         the data which needs to be saved
   * @param fileName     the name of the desired file to save
   * @param encodingType the file encoding type
   * @throws IOException the input out put exception
   */
  void saveTextFile(String data, String fileName, Charset encodingType)
      throws IllegalArgumentException, IOException;

  /**
   * Converts the image object to buffered image object.
   * 
   * @param image the image object
   * @return the buffered image
   */
  BufferedImage getBufferedImage(Image image);

}
