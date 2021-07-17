package images.utilities;


import images.ImageUtilities;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;


/**
 * Implementation to ClientUtility interface.
 * 
 * @author dileepshah
 *
 */
public final class ClientUtilityImpl implements ClientUtility {

  @Override
  public void saveImageFile( Image image, String fileName)
      throws IllegalArgumentException, IOException {
    validateImageObject(image);
    validateImageSize(image);
    ImageUtilities.writeImage(image.getImage(), image.getWidth(), image.getHeight(), fileName);
  }

  @Override
  public  Image loadImage(String fileName) throws IllegalArgumentException, IOException {
    validateString(fileName);
    int[][][] imageArray = ImageUtilities.readImage(fileName);
    int height = ImageUtilities.getHeight(fileName);
    int width = ImageUtilities.getWidth(fileName);
    return ImageFactory.buildImage(ImageType.STANDARD, height, width, imageArray);
  }

  @Override
  public void saveTextFile( String data,  String fileName,  Charset encodingType)
      throws IllegalArgumentException, IOException {
    validateString(data);
    validateString(fileName);
    validateImageObject(encodingType);
    Writer stream = new OutputStreamWriter(new FileOutputStream(fileName), encodingType);
    stream.write(data);
    stream.close();

  }

  private void validateString( String arg) throws IllegalArgumentException {
    if (arg == null || arg.isEmpty()) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": String argument cannot be null and empty");
    }

  }

  private void validateImageSize( Image image) throws IllegalArgumentException {
    if (image.getHeight() == 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image cannot be null or of size 0");
    }
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("ClientUtilityImpl{");
    sb.append('}');
    return sb.toString();
  }

  /**
   * Helper validate method to validate image object not null.
   * 
   * @param object arg object
   * @throws IllegalArgumentException the null not allowed exception
   */
  private void validateImageObject( Object object) throws IllegalArgumentException {
    if (object == null) {
      throw new IllegalArgumentException("Image Argument must not be null.");
    }
  }

  @Override
  public  BufferedImage getBufferedImage( Image image) {
    validateImageObject(image);
    return ImageUtilities.getBufferedImage(image.getImage(),image.getWidth(),image.getHeight());
  }
}
