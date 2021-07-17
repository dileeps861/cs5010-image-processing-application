package images.beans;

import images.model.image.Image;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Pattern bean implementation to contain data related to pattern.
 * 
 * @author dileepshah
 *
 */
public final class PatternBean implements IPatternBean {
  private final Image image;
  private final String pattern;
  private final Set<String> legend;

  /**
   * Initialize the PatternBean with values.
   *  @param image   the image object
   * @param pattern the pattern
   * @param legend  the legend of the pattern
   */
  public PatternBean(Image image, String pattern, Set<String> legend) {
    if (image == null || pattern == null || legend == null) {

      throw new IllegalArgumentException("Arguments cannot be null for pattern bean.");

    }

    this.image = image;
    this.pattern = pattern;
    this.legend = new LinkedHashSet<>(legend);
  }

  @Override
  public Image getImage() {

    return image.copyImage();
  }

  @Override
  public String getPattern() {
    return pattern;
  }

  @Override
  public Set<String> getLegend() {
    return new LinkedHashSet<>(legend);
  }

}
