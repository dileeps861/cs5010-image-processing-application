package images.processing.transform;

/**
 * Factory class to to create objects of ImageColorTransform objects.
 */
public class ImageColorTransformFactory {
  public static ImageColorTransform buildImageColorTransform(ImageColorTransformType type) {
    ImageColorTransform transforObject = null;
    switch (type) {
    case GRAY_SCALE:
      transforObject = new GrayscaleTransform();
      break;
    case SEPIA_TONE:
      transforObject = new SepiaToneTransform();
      break;
    default:
      break;
    }
    return transforObject;
  }
}
