package images.processing.density;

import images.image.Image;
import images.image.ImageFactory;
import images.image.ImageType;
import images.processing.clamping.ClampColor;
import images.processing.clamping.ClampColorFactory;
import images.processing.clamping.ClampColorType;
import images.processing.dethering.DitherImage;
import images.processing.dethering.DitherImageFactory;
import images.processing.dethering.DitherImageType;

/**
 * This class implements ImageColorDensity interface and implements some of the
 * abstract features of the interface.
 *
 * @author dileepshah
 */
public abstract class AbstractImageColorDensity implements ImageColorDensity {
  private final int colors;
  private final double colorsRound;
  private final ClampColor clamper;

  /**
   * Constructor to initialize object with sixteen most common colors.
   *
   * @param colors  the sixteen most common colors
   * @param clamper the color clamper
   */
  protected AbstractImageColorDensity(int colors, ClampColor clamper)
      throws IllegalArgumentException {
    if (clamper == null) {
      throw new IllegalArgumentException("Color clamper must be specified.");
    }
    this.colors = colors;
    this.colorsRound = Math.floor(256.0 / (colors - 1));
    this.clamper = clamper;
  }

  @Override
  public Image reduceColor(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null");
    }
    int[][][] transformedImage = new int[image.getHeight()][image.getWidth()][3];
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        int[] imageColor = image.getImage()[i][j];
        int[] nearestColor = findNearestColors(imageColor);
        transformedImage[i][j] = nearestColor;
      }
    }
    return ImageFactory.builImage(ImageType.STANDARD, transformedImage.length,
        transformedImage[0].length, transformedImage);
  }

  @Override
  public Image reduceColorWithEssence(Image image, DitherImage ditherImageObject)
      throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image can not be null");
    }
    if (ditherImageObject == null) {
      throw new IllegalArgumentException("Dither Image object can not be null");
    }

    return ditherImageObject.ditherImage(image, colors, clamper);
  }

  /**
   * Private Helper method to find nearest color.
   * 
   * @param pixelColors the pixel colors in RGB.
   * @return the nearest pixel colors in RGB
   */
  private int[] findNearestColors(int[] pixelColors) {
    int[] newPixelColors = new int[pixelColors.length];
    for (int i = 0; i < pixelColors.length; i++) {
      newPixelColors[i] = ClampColorFactory.buildClamp(ClampColorType.STANDARD)
          .clampColorChannel((int) (Math.round(pixelColors[i] / colorsRound) * colorsRound));
    }
    return newPixelColors;
  }

}
