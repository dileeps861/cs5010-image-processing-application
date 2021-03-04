package images.processing.kernel;

/**
 * This interface represents Kernel model and provides features to apply kernel
 * on the image pixel.
 * 
 * @author dileepshah
 *
 */
public interface Kernel {
  /**
   * Returns the generated Kernel matrix.
   * 
   * @return the kernel matrix
   */
  public double[][] getKernel();
}
