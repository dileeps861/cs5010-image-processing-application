package images.processing.transform;

import images.image.Image;

/**
 * This interface represents ImageColorTransform model and provides features
 * related to transforming color of an image.
 * 
 * @author dileepshah
 *
 */
public interface ImageColorTransform {

  /**
   * Takes the image as an input and then returns transformed image.
   * 
   * @param image the image to be transformed
   * @return the transformed image
   * @throws IllegalArgumentException the argument exception
   */
  public Image transformImage(Image image) throws IllegalArgumentException;
}
