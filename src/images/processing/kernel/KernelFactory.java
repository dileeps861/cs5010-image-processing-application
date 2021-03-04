package images.processing.kernel;

/**
 * Abstract factory to create the Kernel Objects.
 */
public class KernelFactory {
  /**
   * Factory method to build the Kernel objects.
   * 
   * @param type the type of Kernel.
   * @return the kernel Object.
   */
  public static Kernel buildKernel(KernelType type) {
    Kernel kernel = null;
    switch (type) {
    case FIVE_PIXEL_SHARPEN:
      kernel = new FivePixelSharpenKernel();
      break;
    case THREE_PIXEL_BLUR:
      kernel = new ThreePixelBlurKernel();
      break;
    default:
      break;
    }
    return kernel;
  }
}
