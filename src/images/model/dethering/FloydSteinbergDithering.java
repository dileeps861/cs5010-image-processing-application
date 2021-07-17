package images.model.dethering;



import images.model.clamping.ClampColor;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

/**
 * This class implements DitherImage interface and provides implementation to
 * "Floyd Steinberg" dithering algorithm.
 *
 * @author dileepshah
 */
public final class FloydSteinbergDithering implements DitherImage {
  private final double topCoefficient;
  private final double rightCoefficient;
  private final double topRightCoefficient;
  private final double bottomRightCoefficient;

  /**
   * Default constructor to create the object.
   */
  FloydSteinbergDithering() {
    this.topCoefficient = 7.0 / 16.0;
    this.rightCoefficient = 5.0 / 16.0;
    this.topRightCoefficient = 3.0 / 16.0;
    this.bottomRightCoefficient = 1.0 / 16.0;
  }

  @Override
  public  Image ditherImage( Image image,  ClampColor clamper, double channelRound)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image can not be null");
    }
    if (clamper == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Color clamper must be specified.");
    }
    int[][][] imageArray = image.getImage();
    // for each y from top to bottom do
    for (int i = 0; i < imageArray.length; i++) {

      // for each x from left to right do
      for (int j = 0; j < imageArray[i].length; j++) {
        // oldpixel := pixel[x][y]
        int[] oldPixel = imageArray[i][j];

        // newpixel := find_closest_palette_color(oldpixel)
        int[] newPixel = findClosestPaletteColor(oldPixel, channelRound);

        // pixel[x][y] := newpixel
        imageArray[i][j] = clamper.clampColor(newPixel);

        // quant_error := oldpixel - newpixel
        int[] quantError = findQuantError(oldPixel, newPixel);

        // pixel[x + 1][y ] := pixel[x + 1][y ] + quant_error × 7 / 16
        if (i + 1 < imageArray.length) {
          // double coefficient = 7.0 / 16.0;
          imageArray[i + 1][j] = clamper
              .clampColor(quantErrorCorrect(imageArray[i + 1][j], topCoefficient, quantError));
        }

        // pixel[x - 1][y + 1] := pixel[x - 1][y + 1] + quant_error × 3 / 16
        if (i - 1 >= 0 && (j + 1) < imageArray[i - 1].length) {
          // double coefficient = 3.0 / 16.0;
          imageArray[i - 1][j + 1] = clamper.clampColor(
              quantErrorCorrect(imageArray[i - 1][j + 1], topRightCoefficient, quantError));
        }

        // pixel[x ][y + 1] := pixel[x ][y + 1] + quant_error × 5 / 16
        if ((j + 1) < imageArray[i].length) {
          // double coefficient = 5.0 / 16.0;
          imageArray[i][j + 1] = clamper
              .clampColor(quantErrorCorrect(imageArray[i][j + 1], rightCoefficient, quantError));
        }

        // pixel[x + 1][y + 1] := pixel[x + 1][y + 1] + quant_error × 1 / 16
        if (i + 1 < imageArray.length && (j + 1) < imageArray[i + 1].length) {
          // double coefficient = 1.0 / 16.0;
          imageArray[i + 1][j + 1] = clamper.clampColor(
              quantErrorCorrect(imageArray[i + 1][j + 1], bottomRightCoefficient, quantError));
        }
      }
    }
    return ImageFactory.buildImage(ImageType.STANDARD, imageArray.length, imageArray[0].length,
        imageArray);
  }

  /**
   * The private helper method to find the quant error at one particular pixel.
   *
   * @param oldPixel the old pixel
   * @param newPixel the new pixel
   * @return the quant error
   */
  private int  [] findQuantError(int  [] oldPixel, int[] newPixel) {
    int[] quantError = new int[oldPixel.length];
    for (int i = 0; i < quantError.length; i++) {

      quantError[i] = oldPixel[i] - newPixel[i];
    }
    return quantError;
  }

  /**
   * The private helper method to correct the quant error.
   *
   * @param oldColors   the old colors
   * @param coefficient the quant correction coefficient
   * @param quantError  the quant error
   * @return the corrected colors
   */
  private int  [] quantErrorCorrect(int  [] oldColors, double coefficient, int[] quantError) {
    int[] newColors = new int[oldColors.length];
    for (int i = 0; i < newColors.length; i++) {
      newColors[i] = (int) (oldColors[i] + (quantError[i] * coefficient));
    }
    return newColors;
  }

  /**
   * Private helper method to to find the closest pallet color.
   *
   * @param oldColors    the old colors
   * @param channelRound the reduced number of color coefficient.
   * @return the new colors
   */
  private int  [] findClosestPaletteColor(int  [] oldColors, double channelRound) {
    int[] newColors = new int[oldColors.length];
    for (int i = 0; i < newColors.length; i++) {
      newColors[i] = (int) (Math.round(oldColors[i] / channelRound) * channelRound);
    }
    return newColors;
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("FloydSteinbergDithering{");
    sb.append("topCoefficient=").append(topCoefficient);
    sb.append(", rightCoefficient=").append(rightCoefficient);
    sb.append(", topRightCoefficient=").append(topRightCoefficient);
    sb.append(", bottomRightCoefficient=").append(bottomRightCoefficient);
    sb.append('}');
    return sb.toString();
  }
}
