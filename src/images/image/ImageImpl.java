package images.image;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * This class implements Image interface and represents a single object of
 * Image. It has characteristics such as image pixels, height, and width.
 *
 * @author dileepshah
 */
public final class ImageImpl implements Image {
  private final int height;
  private final int width;
  private final int[][][] image;

  /**
   * The constructor to create one single image.
   *
   * @param height the height of the image
   * @param width  the width of the image
   * @param image  the image pixels and channel
   */
  protected ImageImpl(int height, int width, int[][][] image) throws IllegalArgumentException {
    if (image.length == 0 || height < 0 || width < 0 || image.length != height
        || image[height - 1].length != width) {
      throw new IllegalArgumentException("Invalid Image properties passed.");
    }
    this.height = height;
    this.width = width;
    this.image = image;
  }

  @Override
  public int getWidth() {

    return width;
  }

  @Override
  public int getHeight() {

    return height;
  }

  @Override
  public int[][][] getImage() {

    return image;
  }

  @Override
  public Image copyImage() {
    int[][][] transformedImage = new int[getHeight()][getWidth()][3];
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        transformedImage[i][j] = image[i][j];
      }
    }
    return new ImageImpl(this.getHeight(), this.getWidth(), transformedImage);
  }

  @Override
  public Image padImage(int paddingSize) {
    if (paddingSize < 0) {
      throw new IllegalArgumentException("Invalid Padding Size.");
    }
    int[][][] transformedImage = new int[getHeight() + (paddingSize * 2)][getWidth()
        + (paddingSize * 2)][3];
    for (int i = paddingSize; i < transformedImage.length - paddingSize; i++) {
      for (int j = paddingSize; j < transformedImage[i].length - paddingSize; j++) {
        transformedImage[i][j] = image[i - paddingSize][j - paddingSize];
      }
    }
    return new ImageImpl(transformedImage.length, transformedImage[0].length, transformedImage);
  }

  @Override
  public Image removePad(int paddingSize) throws IllegalArgumentException {
    if (paddingSize < 0 || paddingSize * 2 > this.height || paddingSize * 2 > this.width) {
      throw new IllegalArgumentException("Invalid Padding Size.");
    }
    int[][][] transformedImage = new int[getHeight() - (paddingSize * 2)][getWidth()
        - (paddingSize * 2)][3];
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        transformedImage[i][j] = image[i + paddingSize][j + paddingSize];
      }
    }
    return new ImageImpl(transformedImage.length, transformedImage[0].length, transformedImage);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ImageImpl.class.getSimpleName() + "[", "]")
        .add("height=" + height).add("width=" + width).add("image=" + Arrays.toString(image))
        .toString();

  }
}
