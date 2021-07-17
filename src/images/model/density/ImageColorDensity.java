package images.model.density;

import images.model.dethering.DitherImage;
import images.model.image.Image;

/**
 * This interface represents the ImageColorDensity model and provides features
 * like converting color density.
 * 
 * @author dileepshah
 *
 */
public interface ImageColorDensity {
  
  /**
   * Reduce the color of image with essence.
   * @param image the image object
   * @param ditherImageObject the dither image object
   * @return the processed Image
   */
  Image reduceColorWithEssence(Image image, DitherImage ditherImageObject);
}
