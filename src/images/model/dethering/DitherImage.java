package images.model.dethering;

import images.model.clamping.ClampColor;
import images.model.image.Image;

/**
 * This interface represents DitherImage model and provides features related to
 * dithering an image.
 * 
 * @author dileepshah
 *
 */
public interface DitherImage {

  /**
   * The method to dither a given image. It takes distorted image and returns
   * dithered image.
   * 
   * @param image        the distorted image
   * @param channelRound the reduced number of color coefficient.
   * @return the dithered image
   */
  Image ditherImage(Image image, ClampColor clampColor, double channelRound);
}
