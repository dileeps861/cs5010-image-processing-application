import org.junit.Test;

import images.image.Image;
import images.image.ImageFactory;
import images.image.ImageType;
import images.processing.transform.ImageColorTransform;
import images.processing.transform.ImageColorTransformFactory;
import images.processing.transform.ImageColorTransformType;

import static org.junit.Assert.*;

public class ImageColorTransformTest {

  @Test
  public void transformImage() {
    int[][][] imageArray = new int[][][] { { { 94, 41, 174 } } };

    Image image = ImageFactory.builImage(ImageType.STANDARD, imageArray.length,
        imageArray[0].length, imageArray);

    ImageColorTransform transform = ImageColorTransformFactory
        .buildImageColorTransform(ImageColorTransformType.SEPIA_TONE);

    Image trasnformedImage = transform.transformImage(image);
    for (int[][] img : trasnformedImage.getImage()) {
      for (int[] img1 : img) {
        for (int img2 : img1) {
          System.out.println(img2);
        }
      }
    }
    assertNotEquals(image.toString(), trasnformedImage.toString());
  }
}