package images.model;


import images.beans.IPatternBean;
import images.model.image.Image;

import java.util.Set;


/**
 * Central point of contact for the controller, this provides the access to
 * features such as image filtering, transformation, pixelation, color
 * reduction, and pattern generation.
 * 
 * @author dileepshah
 *
 */
public interface IModel {

  /**
   * Transforms the image to gray scale.
   * 
   * @param image the image t o transform
   * @return the transformed image
   */
  Image transformImageColorToGrayScale(Image image);

  /**
   * Transforms the image to sepia tone.
   * 
   * @param image the image to transform
   * @return the transformed image
   */
  Image transformImageColorToSepiaTone(Image image);

  /**
   * Blurs the image.
   *
   * @param image the image to blur
   * @return the transformed image
   */
  Image blurImage(Image image);

  /**
   * Sharpens the image.
   *
   * @param image the image to sharpen
   * @return the transformed image
   */
  Image sharpenImage(Image image);

  /**
   * Dither the image to specified number of colors. Uses FlOYD_STEINBERG
   * algorithm to dither the image.
   *
   * @param image          the image to reduce color
   * @param numberOfColors the number of colors required per channel
   * @return the transformed image
   */

  Image ditherImage(Image image, int numberOfColors);

  /**
   * Pixelates the given image with the number super pixel given.
   *
   * @param image           the image to reduce color
   * @param numberOfSquares the number of squares required width wise
   * @return the transformed image
   */
  Image pixelateImage(Image image, int numberOfSquares);

  /**
   * Generate Pattern from the given image with the number of chunks required.
   *
   * @param image           the image to reduce color
   * @param numberOfSquares the number of squares required width wise
   * @return the pattern bean
   */
  IPatternBean generatePattern(Image image, int numberOfSquares);

  /**
   * Generate Pattern from the given image with the number of chunks required.
   * @param image the image to reduce color
   * @param numberOfSquares the number of squares required width wise
   * @param colors the user specific colors
   * @return the pattern bean
   */
  IPatternBean generatePattern(Image image, int numberOfSquares, Set<String> colors);

  /**
   * Generate Mosaic of the the given image with the number of seeds given.
   *
   * @param image         the image to reduce color
   * @param numberOfSeeds the number of seed required in the image
   * @return the transformed image
   */
  Image mosaicImage(Image image, int numberOfSeeds);

  /**
   * Replaces a particular color with new replacing color from the image.
   * 
   * @param patternBean          the image object
   * @param oldColor the color to be replaced
   * @param newColor   the new color
   * @return the new updated pattern bean
   */
  IPatternBean replaceColor(IPatternBean patternBean, String oldColor, String newColor);

  /**
   * Get the stored image of the model for further processing.
   * @return the image object
   */
  Image getImage();



  /**
   * Get the pattern bean of the pattern for further processing.
   * @return the pattern bean object
   */
  IPatternBean getPatternBean();
  
  /**
   * Clears all the processing and user can start a fresh processing from original file.
   */
  void clearProcessing();

}
