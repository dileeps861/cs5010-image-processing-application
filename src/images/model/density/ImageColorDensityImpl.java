package images.model.density;



import images.model.clamping.ClampColor;
import images.model.dethering.DitherImage;
import images.model.image.Image;

import java.util.StringJoiner;

/**
 * This class implements ImageColorDensity interface and implements some of the
 * abstract features of the interface.
 *
 * @author dileepshah
 */
public class ImageColorDensityImpl implements ImageColorDensity {
  private final double colorsRound;
  private final  ClampColor clamper;

  /**
   * Constructor to initialize object with sixteen most common colors.
   *
   * @param colors            the no of colors per channel
   * @param channelColorLimit the maximum number of colors in a channel
   * @param clamper           the color clamper
   */
  public ImageColorDensityImpl(int colors, int channelColorLimit,  ClampColor clamper)
      throws IllegalArgumentException {
    if (clamper == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Color clamper must be specified.");
    }
    this.colorsRound = Math.floor(channelColorLimit / (colors - 1.0));
    this.clamper = clamper;

  }

  @Override
  public Image reduceColorWithEssence( Image image,  DitherImage ditherImageObject)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image can not be null");
    }
    if (ditherImageObject == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": DitherImage object can not be null");
    }

    return ditherImageObject.ditherImage(image, clamper, colorsRound);
  }

  @Override
  public boolean equals( Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ImageColorDensityImpl that = (ImageColorDensityImpl) o;

    if (Double.compare(that.colorsRound, colorsRound) != 0) {
      return false;
    }
    return clamper.equals(that.clamper);
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = 0;
    temp = Double.doubleToLongBits(colorsRound);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + clamper.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ImageColorDensityImpl.class.getSimpleName() + "[", "]")
        .add("colorsRound=" + colorsRound).add("clamper=" + clamper).toString();
  }
}
