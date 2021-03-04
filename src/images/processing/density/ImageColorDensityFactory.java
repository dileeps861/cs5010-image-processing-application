package images.processing.density;

public class ImageColorDensityFactory {
  public static ImageColorDensity buildImageColorDensity(ImageColorDensityType type) {
    ImageColorDensity density = null;
    switch (type) {
    case EIGHT_COLOR_DENSITY:
      density = new EigthColorImageDensityImpl(2);
      break;
    case SIXTEEN_COLOR_DENSITY:
      density = new SixteenColorImageDensityImpl(4);
      break;
    default:
      break;
    }
    return density;
  }
}
