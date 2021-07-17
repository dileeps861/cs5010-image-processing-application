package images.model.pattern;

import images.beans.IPatternBean;
import images.model.chunking.ChunkGenerator;
import images.model.image.Image;

import java.util.Map;


/**
 * Interface to Generate Pattern.
 */
public interface PatternGenerator {
  
  /**
   * Generates the patterned image and returns.
   *
   * @param image          the image object
   * @param chunkGenerator the chunk generator object
   * @param colors         colors map
   * @return the pattern bean object
   * @throws IllegalArgumentException the arguments invalid exception
   */
  IPatternBean generatePatternedBean(Image image, ChunkGenerator chunkGenerator,
      Map<String, Integer[]> colors);
}