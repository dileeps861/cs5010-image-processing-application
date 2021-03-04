package images.processing.kernel;

/**
 * This class implements three pixel blurring kernel.
 *
 * @author dileepshah
 */
public final class ThreePixelBlurKernel extends AbstractKernel {
  private final static double[][] kernel;

  static {
    kernel = new double[][] { { 1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0 },
        { 1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0 }, { 1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0 } };
  }

  public ThreePixelBlurKernel() {
    super(ThreePixelBlurKernel.kernel);
  }
}
