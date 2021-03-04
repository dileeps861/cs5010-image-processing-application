package images.processing.filter;

import images.processing.clamping.ClampColor;
import images.processing.clamping.ClampColorFactory;
import images.processing.clamping.ClampColorType;
import images.processing.kernel.Kernel;
import images.processing.kernel.KernelFactory;
import images.processing.kernel.KernelType;

/**
 * This class extends AbstractImageFilter class and provides some
 * implementations to the features related to Blurring an image.
 * 
 * @author dileepshah
 *
 */
public final class ImageFilterBlur extends AbstractImageFilter {
  private static final Kernel kernel;
  private static final ClampColor clamper;
  /**
   * Initializes the kernel and clamper.
   */
  static {
    kernel = KernelFactory.buildKernel(KernelType.THREE_PIXEL_BLUR);
    clamper = ClampColorFactory.buildClamp(ClampColorType.STANDARD);
  }

  /**
   * Constructor to initialize the fields.
   *
   * @throws IllegalArgumentException the exception
   */
  protected ImageFilterBlur() throws IllegalArgumentException {
    super(ImageFilterBlur.kernel, clamper);
  }

}
