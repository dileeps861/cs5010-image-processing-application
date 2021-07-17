package images.model.dethering;



/**
 * The Factory class to create Dithering object.
 * 
 * @author dileepshah
 *
 */
public class DitherImageFactory {

  /**
   * Creates and returns the DitherImage object.
   * 
   * @param type the DitherImage type
   * @return the DitherImage object
   */
  public static  DitherImage buildDitherImage(DitherImageType type) {
    DitherImage ditherImageObject = null;
    if (type == DitherImageType.FLOYD_STEINBERG) {
      ditherImageObject = new FloydSteinbergDithering();
    }
    return ditherImageObject;
  }
}
