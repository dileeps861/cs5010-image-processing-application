package images.processing.transform;

import images.processing.clamping.ClampColor;
import images.processing.clamping.ClampColorFactory;
import images.processing.clamping.ClampColorType;
import images.processing.kernel.Kernel;
import images.processing.kernel.KernelFactory;
import images.processing.kernel.KernelType;

/**
 * This class extends AbstractImageColorTransform class and provides some
 * implementations to the features related to transforming an image to
 * Grayscale.
 * 
 * @author dileepshah
 *
 */
public final class GrayscaleTransform extends AbstractImageColorTransform {
  private final static double[][] transformationMatrix;
  private static final ClampColor clamper;

  /**
   * Creates a 3*3 static transformation matrix and Initializes the clamper.
   */
  static {
    transformationMatrix = new double[][] { new double[] { 0.2126, 0.7152, 0.0722 },
        new double[] { 0.2126, 0.7152, 0.0722 }, new double[] { 0.2126, 0.7152, 0.0722 } };
    clamper = ClampColorFactory.buildClamp(ClampColorType.STANDARD);
  }

  /**
   * Default constructor to initialize the Grayscale transformation with 3*3
   * transformation matrix.
   */
  protected GrayscaleTransform() {
    super(GrayscaleTransform.transformationMatrix, clamper);

  }

}
