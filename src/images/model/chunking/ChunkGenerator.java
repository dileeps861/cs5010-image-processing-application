package images.model.chunking;

import images.model.image.Image;

/**
 * Generator to generate chuck of the given image.
 */
public interface ChunkGenerator {
  /**
   * Method to find the chunk of the image starting from given height and width
   * indexes.
   *
   * @param image the image object of which chuck is to be found
   * @param row   the height index
   * @param col   the width index
   * @return the int array of size two with chunk size [height, width]
   */
  int[] generateChunk(Image image, int row, int col);

}
