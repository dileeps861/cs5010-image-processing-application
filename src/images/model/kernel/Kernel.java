package images.model.kernel;

/**
 * This interface represents Kernel model and provides features to apply kernel
 * on the image pixel.
 * 
 * @author dileepshah
 *
 */
public interface Kernel {
  /**
   * Returns the kernel's cardinality.
   * 
   * @return the kernel cardinality
   */
  int getKernelCardinality();

  /**
   * Returns the applied kernel value.
   *
   * @param pixels the pixel matrix
   * @return the appliedKernel image pixels
   * @throws IllegalArgumentException the argument exception
   */
  int[] applyKernel(int[][][] pixels) throws IllegalArgumentException;
}
