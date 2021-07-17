package images.model.transform;


import images.model.clamping.ClampColor;

/**
 * This class extends AbstractImageColorTransform class and provides some
 * implementations to the features related to transforming an image to
 * Grayscale.
 * 
 * @author dileepshah
 *
 */
public final class GrayscaleTransform extends AbstractImageColorTransform {
  private static final double[]  [] transformationMatrix;

  static {
    transformationMatrix = new double[][] { new double[] { 0.2126, 0.7152, 0.0722 },
        new double[] { 0.2126, 0.7152, 0.0722 }, new double[] { 0.2126, 0.7152, 0.0722 } };

  }

  /**
   * Default constructor to initialize the Grayscale transformation with 3*3
   * transformation matrix.
   * 
   * @param clamper the color clamper
   */
  GrayscaleTransform(ClampColor clamper) throws IllegalArgumentException {
    super(GrayscaleTransform.transformationMatrix, clamper);

  }

}
