package images.model.clamping;



import java.util.StringJoiner;

/**
 * This class implements the ClampColor interface and implements actual logics
 * for the features of ClampColor interface.
 * 
 * @author dileepshah
 *
 */
public final class ClampColorImplStandard implements ClampColor {
  private final int minimumPremissible;
  private final int maximumPremissible;

  /**
   * Constructor which initializes ClampColor object with minimum and maximum
   * permissible.
   *
   */
  public ClampColorImplStandard() {
    this.minimumPremissible = 0;
    this.maximumPremissible = 255;
  }

  @Override
  public int clampColorChannel(int colorChannel) {
    return colorChannel < minimumPremissible ? minimumPremissible
        : Math.min(colorChannel, maximumPremissible);
  }

  @Override
  public int  [] clampColor(int  [] color) throws IllegalArgumentException {
    if (color == null || color.length == 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Colors cannot be null or empty.");
    }
    int[] clampedColor = new int[color.length];
    for (int i = 0; i < color.length; i++) {
      clampedColor[i] = clampColorChannel(color[i]);
    }
    return clampedColor;
  }

  @Override
  public boolean equals( Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ClampColorImplStandard that = (ClampColorImplStandard) o;

    if (minimumPremissible != that.minimumPremissible) {
      return false;
    }
    return maximumPremissible == that.maximumPremissible;
  }

  @Override
  public int hashCode() {
    int result = minimumPremissible;
    result = 31 * result + maximumPremissible;
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ClampColorImplStandard.class.getSimpleName() + "[", "]")
        .add("minimumPremissible=" + minimumPremissible)
        .add("maximumPremissible=" + maximumPremissible).toString();
  }
}
