package images.model.chunking;



import images.model.image.Image;

/**
 * Implementation of ChunkGenerator to generate chunk of the given image.
 */
public class ChunkGeneratorImpl implements ChunkGenerator {
  private final int numberOfChunks;

  /**
   * Constructor to create ChunkGenerator Object.
   * 
   * @param numberOfChunks the required number of chunks width wise
   */
  public ChunkGeneratorImpl(int numberOfChunks) {
    if (numberOfChunks <= 0) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Number of chunks  must be at least 1.");
    }
    this.numberOfChunks = numberOfChunks;
  }

  @Override
  public int  [] generateChunk( Image image, int row, int col) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image object cannot be null.");
    }

    int squareSize = image.getWidth() / numberOfChunks;
    if (squareSize <= 0) {
      return new int[] { 1, 1 };
    }

    int[] superPixel = new int[] { squareSize, squareSize };
    int widthLeft = image.getWidth() - (squareSize * numberOfChunks);
    int widthIndex = (numberOfChunks - 1 - widthLeft) * squareSize;
    if (col > widthIndex) {
      superPixel[1] = squareSize + 1;
    }
    int heightNoOfSquares = image.getHeight() / squareSize;
    int heightLeft = image.getHeight() - (squareSize * heightNoOfSquares);
    int heightIndex = (heightNoOfSquares - 1 - heightLeft) * squareSize;

    if (row > heightIndex) {
      superPixel[0] = squareSize + 1;
    }
    return superPixel;
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("ChunkGeneratorImpl{");
    sb.append("numberOfChunks=").append(numberOfChunks);
    sb.append('}');
    return sb.toString();
  }

}
