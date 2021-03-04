package images.processing.density;

import images.processing.clamping.ClampColor;
import images.processing.clamping.ClampColorFactory;
import images.processing.clamping.ClampColorType;

/**
 * This class extends AbstractImageColorDensity and implements
 * EigthColorDensity, this class provides the eight color density image
 * conversion feature.
 *
 * @author dileepshah
 */
public final class EigthColorImageDensityImpl extends AbstractImageColorDensity {
  private static final ClampColor clamper;

  /**
   * Initializes the clamper.
   */
  static {

    clamper = ClampColorFactory.buildClamp(ClampColorType.STANDARD);
  }

  /**
   * Default constructor to pass the eight colors.
   * 
   * @param color the color count
   */
  protected EigthColorImageDensityImpl(int color) {
    super(color, clamper);
  }

}
