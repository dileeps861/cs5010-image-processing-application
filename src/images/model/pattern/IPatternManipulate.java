package images.model.pattern;

import images.beans.IPatternBean;

/**
 * Utility to manipulate the pattern color and texts, functionalities included
 * are adding character to pattern, removing color from image. This interface is
 * substitute to the PatternGenrator interface, and is included in later stage.
 * 
 * @author dileepshah
 *
 */
public interface IPatternManipulate {
  /**
   * Replaces a particular color with new replacing color from the image.
   * 
   * @param patternBean    the image object
   * @param toReplaceColor the color to be replaced
   * @param replacingRGB   the new color
   * @return the new image updated
   */
  IPatternBean replaceColor(IPatternBean patternBean, String toReplaceColor, String replacingRGB);

}
