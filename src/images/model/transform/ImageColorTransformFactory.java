package images.model.transform;



import images.model.clamping.ClampColor;

/**
 * The Factory class to create objects of GrayscaleImageCommand objects.
 */
public class ImageColorTransformFactory {

  /**
   * Creates and returns GrayscaleImageCommand object.
   * 
   * @param type    the GrayscaleImageCommand type
   * @param clamper the color clamper
   * @return the GrayscaleImageCommand object
   */
  public static  ImageColorTransform buildImageColorTransform( ImageColorTransformType type,
                                                                       ClampColor clamper) {
    ImageColorTransform transforObject = null;
    switch (type) {
      case GRAY_SCALE:
        transforObject = new GrayscaleTransform(clamper);
        break;
      case SEPIA_TONE:
        transforObject = new SepiaToneTransform(clamper);
        break;
      default:
        break;
    }
    return transforObject;
  }
}
