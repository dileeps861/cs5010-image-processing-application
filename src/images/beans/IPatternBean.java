package images.beans;

import images.model.image.Image;

import java.util.Set;


/**
 * Bean to contain data related to pattern.
 * 
 * @author dileepshah
 *
 */
public interface IPatternBean {
  /**
   * Returns the Image with DMC floss colors.
   * 
   * @return the image
   */
  Image getImage();

  /**
   * Returns the Image with DMC floss colors.
   * 
   * @return the pattern
   */
  String getPattern();

  /**
   * Returns the legend map of the pattern.
   * 
   * @return the legend map
   */
  Set<String> getLegend();

}
