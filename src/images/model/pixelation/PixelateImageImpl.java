package images.model.pixelation;



import images.model.AbstractPixelatePatternGenerate;
import images.model.chunking.ChunkGenerator;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

/**
 * The PixelateImage interface implementation to pixelate the image.
 * 
 * @author dileepshah
 *
 */
public class PixelateImageImpl extends AbstractPixelatePatternGenerate implements PixelateImage {
  private final int numberOfSquares;

  /**
   * Constructor to create PixelateImage object.
   *
   * @param numberOfSquares the number of squares in the image.
   */
  public PixelateImageImpl(int numberOfSquares) {

    if (numberOfSquares <= 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Number of squares  must be at least 1.");
    }
    this.numberOfSquares = numberOfSquares;
  }

  @Override
  public  Image pixelateImage( Image image,  ChunkGenerator chunkGenerator)
      throws IllegalArgumentException {
    if (image == null || chunkGenerator == null) {
      throw new IllegalArgumentException(this.getClass().getSimpleName()
          + ": Image and Chunk generator objects cannot be null.");
    }
    if (image.getWidth() < numberOfSquares) {
      return image.copyImage();
    }

    int[][][] imageArray = image.getImage();
    int row = 0;

    while (row < image.getHeight()) {
      int col = 0;
      int superPixelHeight = -1;
      while (col < image.getWidth()) {
        int[] superPixel = chunkGenerator.generateChunk(image, row, col);
        if (superPixel != null && superPixel.length == 2) {
          int heightSquareSize = superPixel[0];
          int widthSquareSize = superPixel[1];
          int rowLim = getSuperPixelRow(imageArray, image, row, heightSquareSize);
          int colLim = getSuperPixelCol(imageArray, image, col, widthSquareSize);
          int centerRow = row + (heightSquareSize / 2);
          centerRow = centerRow < rowLim ? centerRow : (rowLim - 1);
          int centerCol = col + (widthSquareSize / 2);
          centerCol = centerCol < colLim ? centerCol : (colLim - 1);
          int[] centralRGB = imageArray[centerRow][centerCol];
          Integer[] rgbInteger = new Integer[] {
              centralRGB[0],  centralRGB[1],  centralRGB[2]
          };
          applySuperPixel(imageArray, image, row, col, superPixel[0], superPixel[1], rgbInteger);
        }
        assert superPixel != null;
        col += superPixel[1];
        if (superPixelHeight == -1) {
          superPixelHeight = superPixel[0];
        }
      }
      row += superPixelHeight;
    }

    return ImageFactory.buildImage(ImageType.STANDARD, image.getHeight(), image.getWidth(),
        imageArray);
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("PixelateImageImpl{");
    sb.append("numberOfSquares=").append(numberOfSquares);
    sb.append('}');
    return sb.toString();
  }
}
