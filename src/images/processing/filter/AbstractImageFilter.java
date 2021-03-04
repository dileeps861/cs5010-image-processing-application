package images.processing.filter;

import images.image.Image;
import images.processing.clamping.ClampColor;
import images.processing.kernel.Kernel;

/**
 * This class provides implementation to some abstract features related to filtering an image.
 *
 * @author dileepshah
 */
public abstract class AbstractImageFilter implements  ImageFilter {
  private final ClampColor clamper;
  private final Kernel kernel;
  /**
   * Constructor to initialize the fields.
   *
   * @param kernel the kernel of the filter
   * @throws IllegalArgumentException the exception
   */
  protected AbstractImageFilter(Kernel kernel, ClampColor clamper) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel must be specified.");
    }
    if (clamper == null) {
      throw new IllegalArgumentException("Color Clamper must be specified.");
    }
    this.clamper = clamper;
    this.kernel = kernel;
  }

  @Override
  public Image filterImage(Image image) throws  IllegalArgumentException{
    if (image == null) {
      throw new IllegalArgumentException("Image must not be null.");
    }
    int paddingSize = (kernel.getKernel().length - 1) / 2;
    Image paddedImage = image.padImage(paddingSize);
    for (int i = paddingSize; i < paddedImage.getHeight() - paddingSize; i++) {
      for (int j = paddingSize; j < paddedImage.getImage()[i].length - paddingSize; j++) {
        int[] rgbSum = new int[3];
        for (int k = 0; k < paddingSize; k++) {
          for (int l = 0; l < paddingSize; l++) {
            int backRowPosition = i - (paddingSize - k);
            int backColPosition = j - (paddingSize - l);
            int[] rgb = paddedImage.getImage()[backRowPosition][backColPosition];
            for (int m = 0; m < rgb.length; m++) {
              rgbSum[m] = (int) (rgbSum[m] + rgb[m] * kernel.getKernel()[k][l]);
            }
          }
        }
        for (int k = paddingSize; k < kernel.getKernel().length; k++) {
          for (int l = paddingSize; l < kernel.getKernel()[k].length; l++) {
            int frontRowPosition = i + (k - paddingSize);
            int frontColPosition = j + (l - paddingSize);
            int[] rgb = paddedImage.getImage()[frontRowPosition][frontColPosition];
            for (int m = 0; m < rgb.length; m++) {
              rgbSum[m] = (int) (rgbSum[m] + rgb[m] * kernel.getKernel()[k][l]);
            }
          }
        }
        paddedImage.getImage()[i][j] = clamper.clampColor(rgbSum);
      }
    }
    paddedImage = paddedImage.removePad(paddingSize);
    return paddedImage;
  }

}
