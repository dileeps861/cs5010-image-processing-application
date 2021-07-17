package images.model.kernel;



import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * This class implements Kernel interface and provides abstract features
 * implementation to apply kernel on the image pixel.
 * 
 * @author dileepshah
 *
 */
public abstract class AbstractKernel implements Kernel {
  private final double[]  [] kernelMatrix;

  /**
   * The constructor to initialize kernel object.
   * 
   * @param kernelMatrix the kernel object
   * @throws IllegalArgumentException the matrix null or empty exception
   */
  protected AbstractKernel(double[]  [] kernelMatrix) throws IllegalArgumentException {
    if (kernelMatrix == null || kernelMatrix.length == 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Colors cannot be null or empty.");
    }
    this.kernelMatrix = kernelMatrix;
  }

  @Override
  public int getKernelCardinality() {
    return kernelMatrix.length;
  }

  @Override
  public int  [] applyKernel(int[][]  [] pixels) throws IllegalArgumentException {
    if (pixels == null || pixels.length == 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Pixels provided cannot be null or empty.");
    }
    if (pixels.length != kernelMatrix.length
        || pixels[pixels.length - 1].length != kernelMatrix[kernelMatrix.length - 1].length) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Pixels provided must be of Kernel size.");
    }
    int rowLen = pixels.length;
    int colLen = pixels[rowLen - 1].length;
    int lengthOfPixelsRGB = pixels[rowLen - 1][colLen - 1].length;
    int[] appliedKernel = new int[lengthOfPixelsRGB];
    for (int k = 0; k < kernelMatrix.length; k++) {
      for (int l = 0; l < kernelMatrix[k].length; l++) {
        int[] rgb = pixels[k][l];
        for (int m = 0; m < rgb.length; m++) {
          appliedKernel[m] += (int) (rgb[m] * kernelMatrix[k][l]);
        }
      }
    }

    return appliedKernel;
  }

  @Override
  public boolean equals( Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AbstractKernel that = (AbstractKernel) o;

    return Arrays.deepEquals(kernelMatrix, that.kernelMatrix);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(kernelMatrix);
  }

  @Override
  public String toString() {
    List<String> kernelList = Arrays.stream(kernelMatrix)
        .map(Arrays::toString).collect(Collectors.toList());
    return new StringJoiner(", ", AbstractKernel.class.getSimpleName() + "[", "]")
        .add("kernel=" + kernelList).toString();
  }
}
