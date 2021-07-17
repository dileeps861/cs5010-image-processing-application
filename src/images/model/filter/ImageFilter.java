package images.model.filter;

import images.model.image.Image;

/**
 * This interface represents ImageFilter model and provides features related to
 * filtering an image.
 * 
 * @author dileepshah
 *
 */
public interface ImageFilter {

  /**
   * Filter and returns the Image.
   * 
   * @param image the image to filter
   * @return the filtered Image object
   * @throws IllegalArgumentException the arguments exception
   */
  Image filterImage(Image image) throws IllegalArgumentException;
}
