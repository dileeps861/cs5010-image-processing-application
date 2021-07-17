package images.model.mosaic;

import images.model.image.Image;

/**
 * Interface to mosaic the image.
 */
public interface MosaicImage {
  
  /**
   * Mosaic image and return it.
   * 
   * @param image the image to mosaic
   * @return mosaiced image
   */
  Image mosaicImage(Image image);
}
