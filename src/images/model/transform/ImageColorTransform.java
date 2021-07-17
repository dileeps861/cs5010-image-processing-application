package images.model.transform;

import images.model.image.Image;

/**
 * This interface represents GrayscaleImageCommand model and provides
 * features related to transforming color of an image.
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
  Image transformImage(Image image) throws IllegalArgumentException;
}
