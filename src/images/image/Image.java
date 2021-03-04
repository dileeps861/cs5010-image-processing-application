package images.image;

/**
 * This Interface represents a single object of Image.
 * 
 * @author dileepshah
 *
 */
public interface Image {

  /**
   * Returns the width of the image
   * 
   * @return the width of the image
   */
  public int getWidth();

  /**
   * Returns the height of the image
   * 
   * @return the height of the image
   */
  public int getHeight();

  /**
   * Returns the image array.
   * 
   * @return the image array
   */
  public int[][][] getImage();

  /**
   * Clones the image and returns new instance.
   * 
   * @return the Image cloned image
   */
  public Image copyImage();

  /**
   * Returns a new padded image. Padding size is the padding of each side.
   * 
   * @param paddingSize the size of required padding
   * @return the padded Image
   */
  public Image padImage(int paddingSize);

  /**
   * Returns a new removed pad image. Padding size is the padding of each side.
   * 
   * @param paddingSize the size of padding to remove
   * @return the removed pad Image
   */
  public Image removePad(int paddingSize);

}
