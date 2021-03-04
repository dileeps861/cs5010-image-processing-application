package images.processing.density;

import images.processing.clamping.ClampColor;
import images.processing.clamping.ClampColorFactory;
import images.processing.clamping.ClampColorType;
import images.processing.kernel.KernelFactory;
import images.processing.kernel.KernelType;

/**
 * This class extends AbstractImageColorDensity and implements
 * SixteenColorDensity, this class provides the sixteen color density image
 * conversion feature.
 * 
 * @author dileepshah
 *
 */
public final class SixteenColorImageDensityImpl extends AbstractImageColorDensity {
  private static final ClampColor clamper;

  /**
   * Initializes the clamper.
   */
  static {

    clamper = ClampColorFactory.buildClamp(ClampColorType.STANDARD);
  }

  /**
   * Default constructor to pass the sixteen colors.
   * 
   * @param color the color count
   */
  protected SixteenColorImageDensityImpl(int color) {
    super(color, clamper);
  }
}
