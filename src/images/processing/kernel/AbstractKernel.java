package images.processing.kernel;

/**
 * This class implements Kernel interface and provides abstract features
 * implementation to apply kernel on the image pixel.
 * 
 * @author dileepshah
 *
 */
public abstract class AbstractKernel implements Kernel {
  private final double[][] kernel;

  public AbstractKernel(double[][] kernel) {
    this.kernel = kernel;
  }

  @Override
  public double[][] getKernel() {
    return kernel;
  }
}
