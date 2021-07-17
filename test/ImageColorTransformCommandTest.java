import static org.junit.Assert.assertNotEquals;

import images.model.clamping.ClampColor;
import images.model.clamping.ClampColorFactory;
import images.model.clamping.ClampColorType;
import images.model.image.Image;
import images.model.image.ImageFactory;
import images.model.image.ImageType;
import images.model.kernel.Kernel;
import images.model.kernel.KernelFactory;
import images.model.kernel.KernelType;
import images.model.transform.ImageColorTransform;
import images.model.transform.ImageColorTransformFactory;
import images.model.transform.ImageColorTransformType;
import org.junit.Test;

/**
 * Test class to test Image Transformation.
 * 
 * @author dileepshah
 *
 */
public class ImageColorTransformCommandTest {

  @Test
  public void transformImage() {
    int[][][] imageArray = new int[][][] { { { 94, 41, 174 }, { 94, 41, 174 } },
        { { 94, 41, 174 }, { 94, 41, 174 } } };

    Image image = ImageFactory.buildImage(ImageType.STANDARD, imageArray.length,
        imageArray[0].length, imageArray);
    ClampColor clampColor = ClampColorFactory.buildClamp(ClampColorType.STANDARD);
    ImageColorTransform transform = ImageColorTransformFactory
        .buildImageColorTransform(ImageColorTransformType.SEPIA_TONE, clampColor);

    Image transformedImage = transform.transformImage(image);

    assertNotEquals(image.toString(), transformedImage.toString());
  }

  @Test
  public void testKernel() {
    Kernel kernelOne = KernelFactory.buildKernel(KernelType.FIVE_PIXEL_SHARPEN);
    Kernel kernelTwo = KernelFactory.buildKernel(KernelType.THREE_PIXEL_BLUR);
    assertNotEquals(kernelOne, kernelTwo);
  }
}