package images.model.filter;

import images.model.clamping.ClampColor;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;
import images.model.kernel.Kernel;

import java.util.StringJoiner;

/**
 * This class provides implementation to some abstract features related to
 * filtering an image.
 *
 * @author dileepshah
 */
public abstract class AbstractImageFilter implements ImageFilter {
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
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Kernel must be specified.");
    }
    if (clamper == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Color Clamper must be specified.");
    }
    this.clamper = clamper;
    this.kernel = kernel;
  }

  @Override
  public Image filterImage(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image must not be null.");
    }
    int paddingSize = (kernel.getKernelCardinality() - 1) / 2;
    Image paddedImage = image.padImage(paddingSize);
    int[][][] imageArray = paddedImage.getImage();
    for (int i = paddingSize; i < imageArray.length - paddingSize; i++) {
      for (int j = paddingSize; j < imageArray[i].length - paddingSize; j++) {

        int[][][] pixelArray = new int[kernel.getKernelCardinality()][kernel
            .getKernelCardinality()][imageArray[i][j].length];

        int backRowPosition = i - paddingSize;

        int row = 0;
        while (row < kernel.getKernelCardinality()) {
          int backColPosition = j - paddingSize;
          int col = 0;
          while (col < kernel.getKernelCardinality()) {
            pixelArray[row][col] = imageArray[backRowPosition][backColPosition];
            backColPosition++;
            col++;
          }
          backRowPosition++;
          row++;
        }
        int[] rgbSum = kernel.applyKernel(pixelArray);
        imageArray[i][j] = clamper.clampColor(rgbSum);
      }
    }
    paddedImage = ImageFactory.buildImage(ImageType.STANDARD, paddedImage.getHeight(),
        paddedImage.getWidth(), imageArray);
    return paddedImage.removePad(paddingSize);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AbstractImageFilter that = (AbstractImageFilter) o;

    if (!clamper.equals(that.clamper)) {
      return false;
    }

    return kernel.equals(that.kernel);
  }

  @Override
  public int hashCode() {
    int result = clamper.hashCode();
    result = 31 * result + kernel.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AbstractImageFilter.class.getSimpleName() + "[", "]")
        .add("clamper=" + clamper).add("kernel=" + kernel).toString();
  }
}
