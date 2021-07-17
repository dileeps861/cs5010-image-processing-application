package images.model;

import images.PatternUtilities;
import images.beans.IPatternBean;
import images.model.chunking.ChunkGeneratorImpl;
import images.model.clamping.ClampColor;
import images.model.clamping.ClampColorFactory;
import images.model.clamping.ClampColorType;
import images.model.density.ImageColorDensityImpl;
import images.model.dethering.DitherImage;
import images.model.dethering.DitherImageFactory;
import images.model.dethering.DitherImageType;
import images.model.filter.ImageFilterFactory;
import images.model.filter.ImageFilterType;
import images.model.image.Image;
import images.model.kernel.KernelFactory;
import images.model.kernel.KernelType;
import images.model.mosaic.MosaicImageImpl;
import images.model.pattern.PatternGeneratorImpl;
import images.model.pattern.PatternManipulate;
import images.model.pixelation.PixelateImageImpl;
import images.model.transform.ImageColorTransformFactory;
import images.model.transform.ImageColorTransformType;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;





/**
 * Central point of contact for whole model. Implements IModel interface.
 * 
 * @author dileepshah
 *
 */
public class Model implements IModel {
  private Image image;
  private IPatternBean patternBean;
  private final ClampColor clamper;

  /**
   * Constructor to create Model object.
   */
  public Model() {
    this.clamper = ClampColorFactory.buildClamp(ClampColorType.STANDARD);
  }

  @Override
  public Image transformImageColorToGrayScale(Image image) {
    this.image = ImageColorTransformFactory
        .buildImageColorTransform(ImageColorTransformType.GRAY_SCALE, clamper)
        .transformImage(image);
    clearPatternBean();
    return this.image;
  }

  @Override
  public Image transformImageColorToSepiaTone(Image image) {

    this.image = ImageColorTransformFactory
        .buildImageColorTransform(ImageColorTransformType.SEPIA_TONE, clamper)
        .transformImage(image);
    clearPatternBean();
    return this.image;
  }

  @Override
  public Image blurImage(Image image) {
    this.image = ImageFilterFactory.buildImageFilter(ImageFilterType.BLUR,
        KernelFactory.buildKernel(KernelType.THREE_PIXEL_BLUR), clamper).filterImage(image);
    clearPatternBean();
    return this.image;
  }

  @Override
  public Image sharpenImage(Image image) {
    this.image = ImageFilterFactory.buildImageFilter(ImageFilterType.SHARPEN,
        KernelFactory.buildKernel(KernelType.FIVE_PIXEL_SHARPEN), clamper).filterImage(image);
    clearPatternBean();
    return this.image;
  }

  @Override
  public Image ditherImage(Image image, int numberOfColors) {

    DitherImage ditherImage = DitherImageFactory.buildDitherImage(DitherImageType.FLOYD_STEINBERG);
    this.image = new ImageColorDensityImpl(numberOfColors, 256, clamper)
        .reduceColorWithEssence(image, ditherImage);
    clearPatternBean();
    return this.image;
  }

  @Override
  public Image pixelateImage(Image image, int numberOfSquares) {

    this.image = new PixelateImageImpl(numberOfSquares).pixelateImage(image,
        new ChunkGeneratorImpl(numberOfSquares));
    clearPatternBean();
    return this.image;
  }

  @Override
  public IPatternBean generatePattern(Image image, int numberOfSquares) {
    Map<String, Integer[]> allDmc = PatternUtilities.getDMCMap();
    this.patternBean = generatePattern(image, numberOfSquares, allDmc);
    clearImage();
    return this.patternBean;
  }

  @Override
  public IPatternBean generatePattern(Image image, int numberOfSquares, Set<String> colors) {
    Map<String, Integer[]> colorsMap = new LinkedHashMap<>();
    Map<String, Integer[]> allDmc = PatternUtilities.getDMCMap();
    for (String key : colors) {
      if (allDmc.containsKey(key)) {
        colorsMap.put(key, allDmc.get(key));
      }
    }
    this.patternBean = generatePattern(image, numberOfSquares, colorsMap);
    clearImage();
    return this.patternBean;
  }

  private IPatternBean generatePattern(Image image, int numberOfSquares,
      Map<String, Integer[]> colors) {

    this.patternBean = new PatternGeneratorImpl().generatePatternedBean(image,
        new ChunkGeneratorImpl(numberOfSquares), colors);
    clearImage();
    return this.patternBean;

  }

  @Override
  public Image mosaicImage(Image image, int numberOfSeeds) {

    this.image = new MosaicImageImpl(numberOfSeeds).mosaicImage(image);
    clearPatternBean();
    return this.image;
  }

  @Override
  public IPatternBean replaceColor(IPatternBean patternBean, String oldColor, String newColor) {
    this.patternBean = new PatternManipulate().replaceColor(patternBean, oldColor, newColor);
    clearImage();
    return this.patternBean;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Model{");
    sb.append('}');
    return sb.toString();
  }

  private void clearImage() {
    this.image = null;
  }

  private void clearPatternBean() {
    this.patternBean = null;
  }

  @Override
  public Image getImage() {
    return image;
  }

  @Override
  public IPatternBean getPatternBean() {
    return patternBean;
  }

  @Override
  public void clearProcessing() {
    clearImage();
    clearPatternBean();
  }
}
