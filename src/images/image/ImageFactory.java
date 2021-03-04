package images.image;

/**
 * Abstract factory for Image.
 */
public class ImageFactory {

  /**
   * Creates and returns Image object.
   * 
   * @param imageType
   * @param height
   * @param width
   * @param image
   * @return
   */
  public static Image builImage(ImageType imageType, int height, int width, int[][][] image) {
    Image imageObject = null;
    switch (imageType) {
    case STANDARD:
      imageObject = new ImageImpl(height, width, image);
      break;
    default:
      break;
    }

    return imageObject;
  }
}
