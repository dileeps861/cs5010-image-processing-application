package images.model.filter;



import images.model.clamping.ClampColor;
import images.model.kernel.Kernel;

/**
 * The Factory to build ImageFilterObject.
 */
public class ImageFilterFactory {

  /**
   * Build the ImageFilter type object.
   * 
   * @param type the type of Image Filter
   * @return the ImageFilter Object
   */
  public static  ImageFilter buildImageFilter( ImageFilterType type, Kernel kernel,
                                                       ClampColor clamper) {

    ImageFilter imageFilter = null;
    switch (type) {
      case BLUR:
        imageFilter = new ImageBlur(kernel, clamper);
        break;
      case SHARPEN:
        imageFilter = new ImageSharpen(kernel, clamper);
        break;
      default:
        break;
    }
    return imageFilter;
  }
}
