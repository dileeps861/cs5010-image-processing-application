package images.model.clamping;

/**
 * This interface clamp image model and provides the feature of image clamping.
 * 
 * @author dileepshah
 *
 */
public interface ClampColor {

  /**
   * This method clamps the color channel if the color channel is out of color
   * range.
   * 
   * @param colorChannel the color channel to clamp
   * @return the clamped color
   */
  int clampColorChannel(int colorChannel);

  /**
   * This method clamps the color if the color is out of color range.
   * 
   * @param color the color to clamp
   * @return the clamped color
   * @throws IllegalArgumentException the color array null or empty exception
   */
  int[] clampColor(int[] color);
}
