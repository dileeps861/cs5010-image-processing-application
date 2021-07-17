package images.model.kernel;


/**
 * This class implements three pixel blurring kernel.
 *
 * @author dileepshah
 */
public final class ThreePixelBlurKernel extends AbstractKernel {
  private static final double[]  [] kernelMatrix;

  static {
    kernelMatrix = new double[][] { { 1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0 },
        { 1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0 }, { 1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0 } };
  }

  /**
   * The default constructor to set the 3*3 kernel to Abstract class.
   */
  ThreePixelBlurKernel() {
    super(ThreePixelBlurKernel.kernelMatrix);
  }
}
