package images.processing.dethering;

public class DitherImageFactory {
  public static DitherImage buildDitherImage(DitherImageType type) {
    DitherImage ditherImageObject = null;
    switch (type) {
    case FLOYD_STEINBERG:
      ditherImageObject = new FloydSteinbergDithering();
      break;
    default:
      break;
    }
    return ditherImageObject;
  }
}
