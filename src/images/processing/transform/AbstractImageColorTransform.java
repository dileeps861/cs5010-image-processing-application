package images.processing.transform;

import images.image.Image;
import images.image.ImageFactory;
import images.image.ImageType;
import images.processing.clamping.ClampColor;

/**
 * This class provides implementation to some abstract features related to
 * transforming color of an image.
 * 
 * @author dileepshah
 *
 */
public abstract class AbstractImageColorTransform implements ImageColorTransform {
  private final double[][] transformationMatrix;
  private final ClampColor clamper;

  /**
   * Constructor to pass the matrix and colors values.
   *
   * @param transformationMatrix the transformation matrix
   * @param clamper
   */
  protected AbstractImageColorTransform(double[][] transformationMatrix, ClampColor clamper)
      throws IllegalArgumentException {
    this.clamper = clamper;
    if (transformationMatrix == null || transformationMatrix.length != 3) {
      throw new IllegalArgumentException(
          "Transform matrix cannot be null and must be a 3*3 matrix.");
    }
    if (clamper == null) {
      throw new IllegalArgumentException("Color Clamper must be specified.");
    }
    this.transformationMatrix = transformationMatrix;

  }

  /**
   * Helper method to generate transposed rgb color values array and returns it;
   * 
   * @param colors the rgb colors array
   * @return the return transposed colors
   */
  private int[] transposedColors(int[] colors) {

    int[] transposedColors = new int[colors.length];

    for (int i = 0; i < transformationMatrix.length; i++) {
      double colorTransposedSum = 0;
      for (int j = 0; j < transformationMatrix[i].length; j++) {
        colorTransposedSum += transformationMatrix[i][j] * colors[j];
      }
      int transposedColor = clamper.clampColorChannel((int) colorTransposedSum);
      transposedColors[i] = transposedColor;
    }

    return transposedColors;
  }

  @Override
  public Image transformImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }
    int[][][] transformedImage = new int[image.getHeight()][image.getWidth()][3];
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        int[] imageColors = image.getImage()[i][j];
        int[] transformedImageColors = transposedColors(imageColors);
        transformedImage[i][j] = transformedImageColors;
      }
    }

    return ImageFactory.builImage(ImageType.STANDARD, transformedImage.length,
        transformedImage[0].length, transformedImage);

  }
}
