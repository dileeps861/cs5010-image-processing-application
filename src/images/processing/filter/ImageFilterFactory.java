package images.processing.filter;

/**
 * Abstract factory to build ImageFilterObject.
 */
public class ImageFilterFactory {

  /**
   * Build the ImageFilter type object.
   * 
   * @param type the type of Image Filter
   * @return the ImageFilter Object
   */
  public static ImageFilter buildImageFilter(ImageFilterType type) {

    ImageFilter imageFilter = null;
    switch (type) {
    case BLUR:
      imageFilter = new ImageFilterBlur();
      break;
    case SHARPEN:
      imageFilter = new ImageFilterSharpen();
      break;
    default:
      break;
    }
    return imageFilter;
  }
}
