package images.processing.transform;

import images.processing.clamping.ClampColor;
import images.processing.clamping.ClampColorFactory;
import images.processing.clamping.ClampColorType;

/**
 * This class extends AbstractImageColorTransform class and provides some
 * implementations to the features related to transforming an image to Sepia
 * tone.
 * 
 * @author dileepshah
 *
 */
public final class SepiaToneTransform extends AbstractImageColorTransform {
  private static final ClampColor clamper;
  private final static double[][] transformationMatrix;

  /**
   * Creates a 3*3 static transformation matrix and Initializes the clamper.
   */
  static {
    transformationMatrix = new double[][] { new double[] { 0.393, 0.769, 0.189 },
        new double[] { 0.349, 0.686, 0.168 }, new double[] { 0.272, 0.534, 0.131 } };
    clamper = ClampColorFactory.buildClamp(ClampColorType.STANDARD);
  }

  /**
   * Default constructor to initialize the Spepia Tone transformation with 3*3
   * transformation matrix.
   */
  protected SepiaToneTransform() {
    super(SepiaToneTransform.transformationMatrix, clamper);
  }

}
