package images.processing;

import images.image.Image;
import images.processing.density.ImageColorDensityFactory;
import images.processing.density.ImageColorDensityType;
import images.processing.dethering.DitherImage;
import images.processing.dethering.DitherImageFactory;
import images.processing.dethering.DitherImageType;
import images.processing.filter.ImageFilterFactory;
import images.processing.filter.ImageFilterType;
import images.processing.transform.ImageColorTransformFactory;
import images.processing.transform.ImageColorTransformType;

/**
 * This class implements ImageProcessing utilities. It provides the one place point of access
 * to the features such as image color transformation, changing image density,
 * and applying image filter.
 * 
 * @author dileepshah
 *
 */
public final class ImageProcessingUtilities {


  /**
   * Transforms the image to gray scale
   * @param image the image to transform
   * @return the transformed image
   */
  public static Image transformImageColorToGrayScale(Image image)throws IllegalArgumentException{
    return ImageColorTransformFactory.buildImageColorTransform(ImageColorTransformType.GRAY_SCALE)
            .transformImage(image.copyImage());
  }

  /**
   * Transforms the image to sepia to
         @param image the image to transform
   * @return the transformed image
   */
  public static Image transformImgeColorToSepiaTone(Image image)throws IllegalArgumentException{
    return ImageColorTransformFactory.buildImageColorTransform(ImageColorTransformType.SEPIA_TONE)
            .transformImage(image.copyImage());
  }

  /**
   * Blurs the image.
   *
   * @param image the image to blur
   * @return the transformed image
   */
  public static Image blurImage(Image image)throws IllegalArgumentException {
    return ImageFilterFactory .buildImageFilter(ImageFilterType.BLUR)
            .filterImage(image.copyImage());
  }

  /**
   * Sharpens the image.
   *
   * @param image the image to transform
   * @return the transformed image
   */
  public static Image sharpenImage(Image image)throws IllegalArgumentException{
    return ImageFilterFactory.buildImageFilter(ImageFilterType.SHARPEN)
            .filterImage(image.copyImage());
  }

  /**
   * Transforms the image to eight color density
   * @param image the image to transform
   * @return the transformed image
   */
  public static Image eightColorDensity(Image image)throws IllegalArgumentException{
    return ImageColorDensityFactory.buildImageColorDensity(ImageColorDensityType.EIGHT_COLOR_DENSITY)
             .reduceColor(image).copyImage();
  }

  /**
   * Transforms the image to sixteen color density
           * @param image the image to trans

     * @return the transformed image
   */
  public static Image sixteenColorDensity(Image image) throws IllegalArgumentException{
    return ImageColorDensityFactory.buildImageColorDensity(ImageColorDensityType.SIXTEEN_COLOR_DENSITY)
            .reduceColor(image.copyImage());
  }


  /**
   * Transforms the image to eight color density image while maintaining the essence.
   *     FlOYD_STEINBERG algorithm to dither the image.
   *
   * @param image the image to transform
   * @return the transformed image
   */
 
  public static Image eightColorDensityWithEssence(Image image) throws IllegalArgumentException{
    DitherImage ditherImage = DitherImageFactory.buildDitherImage( DitherImageType.FLOYD_STEINBERG );
    return ImageColorDensityFactory.buildImageColorDensity(ImageColorDensityType.EIGHT_COLOR_DENSITY).
    reduceColorWithEssence(image.copyImage(), ditherImage);
  }

  /**
   * Transforms the image to sixteen color density image while maintaining the essence. Uses
   * FLOYD_STEINBERG algorithm to dither the image.
   *
   * @param image the image to transform
   * @return the transformed image
   */
  public static Image sixteenColorDensityWithEssence(Image image)throws IllegalArgumentException{
    DitherImage ditherImage = DitherImageFactory.buildDitherImage(DitherImageType.FLOYD_STEINBERG);
    return ImageColorDensityFactory.buildImageColorDensity(ImageColorDensityType.SIXTEEN_COLOR_DENSITY)
            .reduceColorWithEssence(image.copyImage(), ditherImage);
  }

}
