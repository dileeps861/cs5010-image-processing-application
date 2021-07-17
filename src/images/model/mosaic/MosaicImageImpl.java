package images.model.mosaic;



import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

import java.util.HashSet;
import java.util.Set;

/**
 * Class to provide Mosaic Image Implementation.
 */
public class MosaicImageImpl implements MosaicImage {
  private final int numberOfSeeds;

  /**
   * Constructor to create Mosaic object.
   * 
   * @param numberOfSeeds the number of seeds
   */
  public MosaicImageImpl(int numberOfSeeds) {
    if (numberOfSeeds <= 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Number of seeds must be at least 1.");
    }
    this.numberOfSeeds = numberOfSeeds;
  }

  @Override
  public  Image mosaicImage( Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image object cannot be null.");
    }
    Set<Integer> randomPixels = generateRandomPixels(image);
    int[][][] imageArray = image.getImage();
    for (int i = 0; i < imageArray.length; i++) {
      for (int j = 0; j < imageArray[i].length; j++) {
        int[] rowCol = findClosestPixel(image, randomPixels, i, j);
        imageArray[i][j] = imageArray[rowCol[0]][rowCol[1]];
      }
    }
    return ImageFactory.buildImage(ImageType.STANDARD, image.getHeight(), image.getWidth(),
        imageArray);
  }

  /**
   * Find the closest pixel from the randomly generated pixel to current pixel.
   * 
   * @param image        the image to edit
   * @param randomPixels the randomly selected pixels
   * @param row          the row index of current pixel
   * @param col          the column index of current pixel
   * @return the closest pixel's row and column index
   */
  private int  [] findClosestPixel( Image image,  Set<Integer> randomPixels, int row, int col) {
    int[] closestPixel = new int[] { row, col };
    double closestPixelDistance = Double.MAX_VALUE;
    for (Integer pixel : randomPixels) {
      int[] pixelRowCol = pixelToRowCol(image, pixel);
      int rowDistance = Math.abs(pixelRowCol[0] - row);
      int colDistance = Math.abs(pixelRowCol[1] - col);
      double squareSum = Math.pow(rowDistance, 2) + Math.pow(colDistance, 2);
      double distance = Math.sqrt(squareSum);
      if (distance < closestPixelDistance) {
        closestPixel = pixelRowCol;
        closestPixelDistance = distance;
      }
    }
    return closestPixel;
  }

  /**
   * Selects the random pixels of size numberOfSeeds.
   * 
   * @param image the image to edit
   * @return the randomly selected pixels
   */
  private  Set<Integer> generateRandomPixels( Image image) {

    Set<Integer> pixels = new HashSet<>();
    int imagePixelCount = (image.getHeight() * image.getWidth()) - 1;
    while (pixels.size() < this.numberOfSeeds) {
      double randomValue = Math.random() * imagePixelCount;
      pixels.add((int) randomValue);
    }
    return pixels;
  }

  /**
   * Converts pixel to row and column and returns the row and column index.
   * 
   * @param image the image to edit
   * @param pixel the pixel of the image
   * @return returns the array with row and column index
   */
  private int  [] pixelToRowCol( Image image, int pixel) {
    int row = pixel / image.getWidth();
    int col = pixel % image.getWidth();
    return new int[] { row, col };
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("MosaicImageImpl{");
    sb.append("numberOfSeeds=").append(numberOfSeeds);
    sb.append('}');
    return sb.toString();
  }
}
