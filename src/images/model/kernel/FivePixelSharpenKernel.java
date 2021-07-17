package images.model.kernel;


/**
 * This class implements five pixel sharpening kernel.
 *
 * @author dileepshah
 */
public final class FivePixelSharpenKernel extends AbstractKernel {
  private static final double[]  [] kernelMatrix;

  static {
    kernelMatrix = new double[][] { { 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0 } };
  }

  /**
   * The default constructor to set the 5*5 kernel to Abstract class.
   */
  FivePixelSharpenKernel() {
    super(FivePixelSharpenKernel.kernelMatrix);
  }
}
