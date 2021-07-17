package images.model.pattern;

import images.PatternUtilities;
import images.beans.IPatternBean;
import images.beans.PatternBean;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

import java.util.LinkedHashSet;
import java.util.Map;



/**
 * The implementation of IPatternManipulate interface, handles the manipulation
 * related to pattern image pixel.
 * 
 * @author dileepshah
 *
 */
public class PatternManipulate implements IPatternManipulate {
  private final Map<String, Integer[]> dmcColorsMap;

  /**
   * Constructor to initialize variables.
   */
  public PatternManipulate() {
    this.dmcColorsMap = PatternUtilities.getDMCMap();
  }

  @Override
  public IPatternBean replaceColor(IPatternBean patternBean, String oldColor, String newColor) {
    if (patternBean == null || newColor == null || oldColor == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }
    if (newColor.isEmpty() || oldColor.isEmpty()) {
      throw new IllegalArgumentException("Old color and new color must not be empty.");
    }
    if (!dmcColorsMap.containsKey(newColor) || !dmcColorsMap.containsKey(oldColor)) {
      throw new IllegalArgumentException("New color and old colors are not valid.");
    }
    String pattern = patternBean.getPattern();

    // Dmc set
    LinkedHashSet<String> legendSet = new LinkedHashSet<>(patternBean.getLegend());

    pattern = pattern.replace("DMC-" + oldColor, "DMC-" + newColor);
    Map<String, Character> dmcSymbolMap = PatternUtilities.getCharacterMap();
    Character symbolOld = dmcSymbolMap.get(oldColor);

    legendSet.remove(symbolOld + " DMC-" + oldColor);
    Character symbolNew = dmcSymbolMap.get(newColor);
    legendSet.add(symbolNew + " DMC-" + newColor);
    pattern = pattern.replace(String.valueOf(String.valueOf(symbolNew)), String.valueOf(symbolOld));

    Integer[] oldColorArray = dmcColorsMap.get(oldColor);
    Integer[] newColorArray = dmcColorsMap.get(newColor);
    int[][][] imageArray = patternBean.getImage().getImage();
    for (int i = 0; i < imageArray.length; i++) {
      for (int j = 0; j < imageArray[i].length; j++) {
        int rgbCount = 0;
        int[] newColorLocal = new int[3];
        for (int k = 0; k < oldColorArray.length; k++) {
          if (imageArray[i][j][k] == oldColorArray[k]) {
            rgbCount++;
            newColorLocal[k] = newColorArray[k];

          }
        }
        if (rgbCount == oldColorArray.length) {
          imageArray[i][j] = newColorLocal;
        }
      }
    }

    Image image = ImageFactory.buildImage(ImageType.STANDARD, patternBean.getImage().getHeight(),
        patternBean.getImage().getWidth(), imageArray);
    return new PatternBean(image, pattern, legendSet);
  }

}
