package images.processing.clamping;

public class ClampColorFactory {
  public static ClampColor buildClamp(ClampColorType type) {
    ClampColor clampColorObject = null;
    switch (type) {
    case STANDARD:
      clampColorObject = new ClampColorImpl(0, 255);
      break;
    default:
      break;
    }
    return clampColorObject;
  }
}
