package images.utilities;

import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.Charset;


/**
 * Mock class to create mock implementation of ClientUtility interface.
 * 
 * @author dileepshah
 *
 */
public class MockClientUtility implements ClientUtility {
  private final Appendable out;

  /**
   * Constructor to create ClientUtility object.
   * 
   * @param out the appendable object for logging.
   */
  public MockClientUtility(Appendable out) {
    this.out = out;
  }

  @Override
  public void saveImageFile(Image image, String fileName)
      throws IllegalArgumentException, IOException {
    out.append("Invoked ").append(this.getClass().getSimpleName()).append(".saveImageFile\n");

  }

  @Override
  public Image loadImage(String fileName) throws IllegalArgumentException, IOException {
    out.append("Invoked ").append(this.getClass().getSimpleName()).append(".loadImage\n");
    int[][][] imageArray = new int[][][] { { { 1, 1, 1 } }, { { 2, 2, 2 } } };
    return ImageFactory.buildImage(ImageType.STANDARD, imageArray.length, imageArray[0].length,
        imageArray);
  }

  @Override
  public void saveTextFile(String data, String fileName, Charset encodingType)
      throws IllegalArgumentException, IOException {
    out.append("Invoked ").append(this.getClass().getSimpleName()).append(".saveTextFile\n");
  }

  @Override
  public BufferedImage getBufferedImage(Image image) {
    try {
      out.append("Invoked ").append(this.getClass().getSimpleName()).append(".getBufferedImage\n");
    } catch (IOException e) {

      throw new AssertionError(e.getMessage());
    }
    return new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MockClientUtility{");
    sb.append('}');
    return sb.toString();
  }
}
