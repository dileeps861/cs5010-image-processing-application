package images.model.image;



/**
 * The Factory to build Image object.
 */
public class ImageFactory {

  /**
   * Creates and returns Image object.
   * 
   * @param imageType the image type
   * @param height    the height of the image
   * @param width     the width of the image
   * @param image     the image array
   * @return the Image object
   */
  public static  Image buildImage(ImageType imageType, int height, int width, int[][]  [] image)
      throws IllegalArgumentException {
    Image imageObject = null;
    if (imageType == null || image == null) {
      throw new IllegalArgumentException("Image type and array cannot be null.");
    }
    if (imageType == ImageType.STANDARD) {
      imageObject = new ImageObject(height, width, image);
    }
    return imageObject;
  }
}
