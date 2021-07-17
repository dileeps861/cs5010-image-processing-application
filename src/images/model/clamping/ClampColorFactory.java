package images.model.clamping;



/**
 * The Factory to build color Object.
 * 
 * @author dileepshah
 *
 */
public class ClampColorFactory {
  /**
   * Creates and return ClampColor object.
   * 
   * @param type the clamp type
   * @return the ClampColor object
   */
  public static  ClampColor buildClamp(ClampColorType type) {
    ClampColor clampColorObject = null;
    if (type == ClampColorType.STANDARD) {
      clampColorObject = new ClampColorImplStandard();
    }
    return clampColorObject;
  }
}
