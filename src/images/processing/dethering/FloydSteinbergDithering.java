package images.processing.dethering;

import images.image.Image;
import images.image.ImageFactory;
import images.image.ImageType;
import images.processing.clamping.ClampColor;
import images.processing.clamping.ClampColorFactory;
import images.processing.clamping.ClampColorType;

/**
 * This class implements DitherImage interface and provides implementation to
 * "Floyd Steinberg" dithering algorithm.
 *
 * @author dileepshah
 */
public final class FloydSteinbergDithering implements DitherImage {

  @Override
  public Image ditherImage(Image image, int channelColors, ClampColor clamper)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null");
    }
    if (clamper == null) {
      throw new IllegalArgumentException("Color clamper must be specified.");
    }

    // for each y from top to bottom do
    for (int i = 0; i < image.getImage().length; i++) {

      // for each x from left to right do
      for (int j = 0; j < image.getImage()[i].length; j++) {
        // oldpixel := pixel[x][y]
        int[] oldPixel = image.getImage()[i][j];

        // newpixel := find_closest_palette_color(oldpixel)
        int[] newPixel = findClosestPaletteColor(oldPixel, channelColors);

        // pixel[x][y] := newpixel
        image.getImage()[i][j] = clamper.clampColor(newPixel);

        // quant_error := oldpixel - newpixel
        int[] quantError = findQuantError(oldPixel, newPixel);

        // pixel[x + 1][y ] := pixel[x + 1][y ] + quant_error × 7 / 16
        if (i + 1 < image.getImage().length) {
          double coefficient = 7.0 / 16.0;
          image.getImage()[i + 1][j] = clamper
              .clampColor(quantErrorCorrect(image.getImage()[i + 1][j], coefficient, quantError));
        }

        // pixel[x - 1][y + 1] := pixel[x - 1][y + 1] + quant_error × 3 / 16
        if (i - 1 >= 0 && (j + 1) < image.getImage()[i - 1].length) {
          double coefficient = 3.0 / 16.0;
          image.getImage()[i - 1][j + 1] = clamper.clampColor(
              quantErrorCorrect(image.getImage()[i - 1][j + 1], coefficient, quantError));
        }

        // pixel[x ][y + 1] := pixel[x ][y + 1] + quant_error × 5 / 16
        if ((j + 1) < image.getImage()[i].length) {
          double coefficient = 5.0 / 16.0;
          image.getImage()[i][j + 1] = clamper
              .clampColor(quantErrorCorrect(image.getImage()[i][j + 1], coefficient, quantError));
        }

        // pixel[x + 1][y + 1] := pixel[x + 1][y + 1] + quant_error × 1 / 16
        if (i + 1 < image.getImage().length && (j + 1) < image.getImage()[i + 1].length) {
          double coefficient = 1.0 / 16.0;
          image.getImage()[i + 1][j + 1] = clamper.clampColor(
              quantErrorCorrect(image.getImage()[i + 1][j + 1], coefficient, quantError));
        }
      }
    }
    return ImageFactory.builImage(ImageType.STANDARD, image.getImage().length,
        image.getImage()[0].length, image.getImage());
  }

  /**
   * The private helper method to find the quant error at one particular pixel.
   *
   * @param oldPixel the old pixel
   * @param newPixel the new pixel
   * @return the quant error
   */
  private int[] findQuantError(int[] oldPixel, int[] newPixel) {
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
  private int[] quantErrorCorrect(int[] oldColors, double coefficient, int[] quantError) {
    int[] newColors = new int[oldColors.length];
    for (int i = 0; i < newColors.length; i++) {
      newColors[i] = (int) (oldColors[i] + (quantError[i] * coefficient));
    }
    return newColors;
  }

  /**
   * Private helper method to to find the closest pallet color.
   *
   * @param oldColors     the old colors
   * @param channelColors the number of colors in channel
   * @return the new colors
   */
  private int[] findClosestPaletteColor(int[] oldColors, int channelColors) {
    int[] newColors = new int[oldColors.length];
    for (int i = 0; i < newColors.length; i++) {
      double channelRound = Math.floor(256f / (channelColors - 1));
      newColors[i] = (int) (Math.round(oldColors[i] / channelRound) * channelRound);
    }
    return newColors;
  }
}
