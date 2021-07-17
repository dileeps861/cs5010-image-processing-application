package images.model.image;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * This class implements Image interface and represents a single object of
 * Image. It has characteristics such as image pixels, height, and width.
 *
 * @author dileepshah
 */
public final class ImageObject implements Image {
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
  ImageObject(int height, int width, int[][][] image) throws IllegalArgumentException {
    if (image == null || image.length == 0 || width < 0 || image.length != height
        || image[height - 1].length != width) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Invalid Image properties passed.");
    }
    if (image[0][0].length != 3) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": image color channel must be of size 3.");
    }
    this.height = height;
    this.width = width;
    int[][][] transformedImage = new int[getHeight()][getWidth()][3];
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        System.arraycopy(image[i][j], 0, transformedImage[i][j], 0, image[i][j].length);
      }
    }
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
    int[][][] transformedImage = new int[getHeight()][getWidth()][3];
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        System.arraycopy(image[i][j], 0, transformedImage[i][j], 0, image[i][j].length);
      }
    }
    return transformedImage;
  }

  @Override
  public Image copyImage() {
    return new ImageObject(this.getHeight(), this.getWidth(), getImage());
  }

  @Override
  public Image padImage(int paddingSize) {
    if (paddingSize < 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Invalid Padding Size.");
    }
    int[][][] transformedImage = new int[getHeight() + (paddingSize * 2)][getWidth()
        + (paddingSize * 2)][image[0][0].length];
    for (int i = paddingSize; i < transformedImage.length - paddingSize; i++) {
      for (int j = paddingSize; j < transformedImage[i].length - paddingSize; j++) {
        int[] rgb = image[i - paddingSize][j - paddingSize];
        System.arraycopy(rgb, 0, transformedImage[i][j], 0, transformedImage[i][j].length);
      }
    }
    return new ImageObject(transformedImage.length, transformedImage[0].length, transformedImage);
  }

  @Override
  public Image removePad(int paddingSize) throws IllegalArgumentException {
    if (paddingSize < 0 || paddingSize * 2 > this.height || paddingSize * 2 > this.width) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Invalid Padding Size.");
    }
    int[][][] transformedImage = new int[getHeight() - (paddingSize * 2)][getWidth()
        - (paddingSize * 2)][image[0][0].length];
    for (int i = 0; i < transformedImage.length; i++) {
      for (int j = 0; j < transformedImage[i].length; j++) {
        int[] rgb = image[i + paddingSize][j + paddingSize];
        System.arraycopy(rgb, 0, transformedImage[i][j], 0, transformedImage[i][j].length);

      }
    }
    return new ImageObject(transformedImage.length, transformedImage[0].length, transformedImage);
  }

  @Override
  public String toString() {
    List<String> imageChannelList = Arrays.stream(image).map(value -> Arrays.stream(value)
        .map(Arrays::toString).collect(Collectors.toList()).toString())
        .collect(Collectors.toList());
    return new StringJoiner(", ", Image.class.getSimpleName() + "[", "]").add("height=" + height)
        .add("width=" + width).add("image=" + imageChannelList).toString();

  }
}
