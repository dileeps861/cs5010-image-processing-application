package images.processing.clamping;

/**
 * This class implements the ClampColor interface and implements actual logics
 * for the features of ClampColor interface.
 * 
 * @author dileepshah
 *
 */
public final class ClampColorImpl implements ClampColor {
  private final int minimumPremissible;
  private final int maximumPremissible;

  /**
   * Constructor which initializes ClampColor object with minimum and maximum
   * permissible.
   * 
   * @param minimumPremissible the minimum permissible
   * @param maximumPremissible the maximum permissible
   */
  public ClampColorImpl(int minimumPremissible, int maximumPremissible) {
    this.minimumPremissible = minimumPremissible;
    this.maximumPremissible = maximumPremissible;
  }

  @Override
  public int clampColorChannel(int colorChannel) {
    return colorChannel < minimumPremissible ? minimumPremissible
        : Math.min(colorChannel, maximumPremissible);
  }

  @Override
  public int[] clampColor(int[] color) {
    int[] clampedColor = new int[color.length];
    for (int i = 0; i < color.length; i++) {
      clampedColor[i] = clampColorChannel(color[i]);
    }
    return clampedColor;
  }
}
