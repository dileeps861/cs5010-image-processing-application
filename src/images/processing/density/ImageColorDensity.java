package images.processing.density;

import images.image.Image;
import images.processing.dethering.DitherImage;

/**
 * This interface represents the ImageColorDensity model and provides features
 * like converting color density.
 * 
 * @author dileepshah
 *
 */
public interface ImageColorDensity {

  /**
   * Reduce the colors of image and returns new image
   * 
   * @param image image to reduce the color
   * @return the image reduced color image
   */
  public Image reduceColor(Image image) throws IllegalArgumentException;

  /**
   * Reduce the colors of image while maintaining the essence and returns new
   * image
   * 
   * @param image             image to reduce the color
   * @param ditherImageObject the Dithering object to dither the image
   * @return the image reduced color image
   */
  public Image reduceColorWithEssence(Image image, DitherImage ditherImageObject)
      throws IllegalArgumentException;
}
