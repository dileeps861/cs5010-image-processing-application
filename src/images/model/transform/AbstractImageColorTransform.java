package images.model.transform;



import images.model.clamping.ClampColor;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * This class provides implementation to some abstract features related to
 * transforming color of an image.
 * 
 * @author dileepshah
 *
 */
public abstract class AbstractImageColorTransform implements ImageColorTransform {
  private final double[]  [] transformationMatrix;
  private final  ClampColor clamper;

  /**
   * Constructor to pass the matrix and colors values.
   *
   * @param transformationMatrix the transformation matrix
   * @param clamper              the color clamper
   */
  protected AbstractImageColorTransform(double[]  [] transformationMatrix,  ClampColor clamper)
      throws IllegalArgumentException {

    if (transformationMatrix == null || transformationMatrix.length != 3) {
      throw new IllegalArgumentException(
          "Transform matrix cannot be null and must be a 3*3 matrix.");
    }
    if (clamper == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Color Clamper must be specified.");
    }

    this.clamper = clamper;
    assert transformationMatrix[0] != null;
    double[][] transformationMatrixLocal =
        new double[transformationMatrix.length][transformationMatrix[0].length];
    for (int i = 0; i < transformationMatrixLocal.length; i++) {
      for (int j = 0; j < transformationMatrixLocal[i].length; j++) {
        assert transformationMatrix[i] != null;
        transformationMatrixLocal[i][j] = transformationMatrix[i][j];
      }
    }
    this.transformationMatrix = transformationMatrixLocal;

  }

  /**
   * Helper method to generate transposed rgb color values array and returns it.
   * 
   * @param colors the rgb colors array
   * @return the return transposed colors
   */
  private int  [] transposedColors(int  [] colors) throws IllegalArgumentException {

    int[] transposedColors = new int[colors.length];

    for (int i = 0; i < transformationMatrix.length; i++) {
      double colorTransposedSum = 0;
      for (int j = 0; j < transformationMatrix[i].length; j++) {
        colorTransposedSum += transformationMatrix[i][j] * colors[j];
      }
      assert clamper != null;
      int transposedColor = clamper.clampColorChannel((int) colorTransposedSum);
      transposedColors[i] = transposedColor;
    }

    return transposedColors;
  }

  @Override
  public  Image transformImage( Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image cannot be null.");
    }
    int[][][] transformedImage = new int[image.getHeight()][image.getWidth()][3];
    int[][][] imageArray = image.getImage();
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        int[] imageColors = imageArray[i][j];
        int[] transformedImageColors = transposedColors(imageColors);
        transformedImage[i][j] = transformedImageColors;
      }
    }

    return ImageFactory.buildImage(ImageType.STANDARD, transformedImage.length,
        transformedImage[0].length, transformedImage);

  }

  @Override
  public boolean equals( Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AbstractImageColorTransform that = (AbstractImageColorTransform) o;

    if (!Arrays.deepEquals(transformationMatrix, that.transformationMatrix)) {
      return false;
    }
    assert clamper != null;
    return clamper.equals(that.clamper);
  }

  @Override
  public int hashCode() {
    int result = Arrays.deepHashCode(transformationMatrix);
    assert clamper != null;
    result = 31 * result + clamper.hashCode();
    return result;
  }

  @Override
  public String toString() {
    List<String> tranformMatrixList = Arrays.stream(transformationMatrix)
        .map(Arrays::toString).collect(Collectors.toList());
    return new StringJoiner(", ", AbstractImageColorTransform.class.getSimpleName() + "[", "]")
        .add("transformationMatrix=" + tranformMatrixList).add("clamper=" + clamper)
        .toString();
  }
}
