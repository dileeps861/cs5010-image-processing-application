package images.model.pattern;

import images.PatternUtilities;
import images.beans.IPatternBean;
import images.beans.PatternBean;
import images.model.AbstractPixelatePatternGenerate;
import images.model.chunking.ChunkGenerator;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * Implementation of interface PatternGenerator.
 */
public class PatternGeneratorImpl extends AbstractPixelatePatternGenerate
    implements PatternGenerator {

  @Override
  public IPatternBean generatePatternedBean(Image image, ChunkGenerator chunkGenerator,
      Map<String, Integer[]> colorsMap) {
    if (image == null || chunkGenerator == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image and cunk generator objects cannot be null.");
    }

    int[][][] imageArray = image.getImage();
    int[][][] newImageArray = image.getImage();

    StringBuilder patternString = new StringBuilder(String.valueOf(image.getHeight()));
    patternString.append("X").append(image.getWidth()).append("\n");

    Map<String, Character> patternMap = PatternUtilities.getCharacterMap();
    Set<String> dmcLegend = new HashSet<>();

    int i = 0;
    while (i < imageArray.length) {
      int rowIncrement = -1;
      int j = 0;

      while (j < imageArray[i].length) {

        String dmc = findClosestColor(imageArray[i][j], colorsMap);
        patternString.append(patternMap.get(dmc));
        dmcLegend.add(dmc);

        int[] superPixel = chunkGenerator.generateChunk(image, i, j);

        if (superPixel != null && superPixel.length == 2) {
          applySuperPixel(newImageArray, image, i, j, superPixel[0], superPixel[1],
              colorsMap.get(dmc));
          j += superPixel[1] > 0 ? superPixel[1] : (j + 1);
          if (rowIncrement == -1) {
            rowIncrement = superPixel[0];
          }
        }
      }

      i += rowIncrement;
      patternString.append("\n");

    }
    dmcLegend = sortDMCLegend(dmcLegend);

    patternString.append(patternSpecification(dmcLegend));
    Image editedImage = ImageFactory.buildImage(ImageType.STANDARD, image.getHeight(),
        image.getWidth(), newImageArray);

    return new PatternBean(editedImage, patternString.toString(), dmcLegend);
  }

  private String findClosestColor(int[] rgb, Map<String, Integer[]> map) {

    double closestColorDistance = Double.MAX_VALUE;
    String closestColor = "";
    for (String key : map.keySet()) {
      double distance = euclideanDistance(rgb, map.get(key));
      if (distance < closestColorDistance) {
        closestColorDistance = distance;
        closestColor = key;
      }
    }
    return closestColor;
  }

  private double euclideanDistance(int[] rgb, Integer[] dmcRGB) {
    double rBar = ((double) rgb[0] + (double) dmcRGB[0]) / 2;
    double deltaRPow = Math.pow((rgb[0] - dmcRGB[0]), 2);
    double deltaGPow = Math.pow((rgb[1] - dmcRGB[1]), 2);
    double deltaBPow = Math.pow((rgb[2] - dmcRGB[2]), 2);

    double rCoefficient = (2 + (rBar / 256)) * deltaRPow;
    double gCoefficient = 4 * deltaGPow;
    double bCoefficient = (2 + ((255 - rBar) / 256)) * deltaBPow;

    return Math.sqrt(rCoefficient + gCoefficient + bCoefficient);
  }

  private String patternSpecification(Set<String> dmcLegend) {
    StringBuilder legendString = new StringBuilder("\nLEGEND\n");

    for (String dmcKey : dmcLegend) {
      legendString.append(dmcKey).append("\n");
    }
    return legendString.toString();
  }

  private Set<String> sortDMCLegend(Set<String> unSortedDMCLegend) {
    Set<String> sortedDMCLegend = new LinkedHashSet<>();
    Set<Integer> numericDMCSorted = new TreeSet<>();
    Set<String> stringDMCSorted = new TreeSet<>();
    Map<String, Character> patternMap = PatternUtilities.getCharacterMap();
    for (String dmc : unSortedDMCLegend) {
      if (dmc.matches("[0-9]+")) {
        numericDMCSorted.add(Integer.parseInt(dmc));
      } else {
        stringDMCSorted.add(dmc);
      }
    }
    for (Integer dmcInt : numericDMCSorted) {
      String dmcKey = String.valueOf(dmcInt);
      sortedDMCLegend.add(patternMap.get(dmcKey) + " DMC-" + dmcKey);
    }
    for (String string : stringDMCSorted) {
      sortedDMCLegend.add(patternMap.get(string) + " DMC-" + string);
    }

    return sortedDMCLegend;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("PatternGeneratorImpl{");
    sb.append('}');
    return sb.toString();
  }

}
