package images.model;

import images.model.image.Image;

/**
 * Abstract class to share the code between Pixelate and pattern generate.
 * 
 * @author dileepshah
 *
 */
public abstract class AbstractPixelatePatternGenerate {

  /**
   * Apply a new color to the super pixel.
   * 
   * @param imageArray   the image array
   * @param image        the image object
   * @param row          the row index
   * @param col          the column index
   * @param heightSquare the height of the square
   * @param widthSquare  the width of the square
   * @param newColor     the new color to replace
   */
  protected void applySuperPixel(int[][][] imageArray, Image image, int row, int col,
      int heightSquare, int widthSquare, Integer[] newColor) throws IllegalArgumentException {
    if (imageArray == null || image == null || newColor == null || imageArray.length == 0
        || newColor.length == 0) {
      throw new IllegalArgumentException(this.getClass().getSimpleName()
          + ".applySuperPixel(): Arguments must not be null and empty.");
    }
    int superPixelRow = getSuperPixelRow(imageArray, image, row, heightSquare);
    int superPixelCol = getSuperPixelCol(imageArray, image, col, widthSquare);

    for (int i = row; i < superPixelRow; i++) {
      for (int j = col; j < superPixelCol; j++) {
        int[] rgb = new int[imageArray[i][j].length];
        for (int k = 0; k < rgb.length; k++) {
          rgb[k] = newColor[k];
        }
        imageArray[i][j] = rgb;
      }
    }
  }

  /**
   * Get the super pixel number of rows.
   * 
   * @param imageArray  the image array
   * @param image       the image object
   * @param row         the row index
   * @param col         the column index
   * @param heightSquare the height of the square
   * @return the height
   * @throws IllegalArgumentException the exception if argument not given
   */
  protected int getSuperPixelRow(int[][][] imageArray, Image image, int row, int heightSquare) {
    if (imageArray == null || image == null || imageArray.length == 0) {
      throw new IllegalArgumentException(this.getClass().getSimpleName()
          + ".getSuperPixelRow(): Arguments must not be null and empty.");
    }
    return Math.min((row + heightSquare), image.getHeight());
  }

  /**
   * Get the super pixel number of columns.
   * 
   * @param imageArray  the image array
   * @param image       the image object
   * @param row         the row index
   * @param col         the column index
   * @param widthSquare the width of the square
   * @return the width
   * @throws IllegalArgumentException the exception if arguments not given
   */
  protected int getSuperPixelCol(int[][][] imageArray, Image image, int col, int widthSquare)
      throws IllegalArgumentException {
    if (imageArray == null || image == null || imageArray.length == 0) {
      throw new IllegalArgumentException(this.getClass().getSimpleName()
          + ".getSuperPixelCol(): Arguments must not be null and empty.");
    }
    return Math.min((col + widthSquare), image.getWidth());
  }
}
