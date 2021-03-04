package images.processing.kernel;

/**
 * This class implements five pixel sharpening kernel.
 *
 * @author dileepshah
 */
public final class FivePixelSharpenKernel extends AbstractKernel {
  private final static double[][] kernel;

  static {
    kernel = new double[][] { { 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / -8.0 },
        { 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0, 1.0 / -8.0 } };
  }

  public FivePixelSharpenKernel() {
    super(FivePixelSharpenKernel.kernel);
  }
}
