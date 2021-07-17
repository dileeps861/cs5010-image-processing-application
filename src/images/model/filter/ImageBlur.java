package images.model.filter;

import images.model.clamping.ClampColor;
import images.model.kernel.Kernel;

/**
 * This class extends AbstractImageFilter class and provides some
 * implementations to the features related to Blurring an image.
 * 
 * @author dileepshah
 *
 */
public class ImageBlur extends AbstractImageFilter {
  /**
   * Constructor to initialize the fields.
   * 
   * @param kernel  the kernel
   * @param clamper the color clamper
   * @throws IllegalArgumentException the exception
   */
  ImageBlur(Kernel kernel, ClampColor clamper) throws IllegalArgumentException {
    super(kernel, clamper);
  }

}
