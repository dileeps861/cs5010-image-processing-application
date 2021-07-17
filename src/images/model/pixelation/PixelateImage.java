package images.model.pixelation;

import images.model.chunking.ChunkGenerator;
import images.model.image.Image;

/**
 * Interface to Pixelate the Image.
 * 
 * @author dileepshah
 *
 */
public interface PixelateImage {

  /**
   * Method to pixelate the image.
   * 
   * @param image          the image to edit
   * @param chunkGenerator the chunk generator object
   * @return the edited image
   */
  Image pixelateImage(Image image, ChunkGenerator chunkGenerator);

}
