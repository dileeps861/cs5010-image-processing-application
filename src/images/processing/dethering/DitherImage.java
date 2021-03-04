package images.processing.dethering;

import images.image.Image;
import images.processing.clamping.ClampColor;

/**
 * This interface represents DitherImage model and provides features related to
 * dithering an image.
 * 
 * @author dileepshah
 *
 */
public interface DitherImage {

  /**
   * The method to dither a giv
   * n image. It takes di 
   * torted image         and returns dithered image.
   * @param image the distorted image
   * @param channelColors the number of colors in a channel
   * @return the dithered image
   */
  public Image ditherImage(Image image, int channelColors, ClampColor clampColor);
}
