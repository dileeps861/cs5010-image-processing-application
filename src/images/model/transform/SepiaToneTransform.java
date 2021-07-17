package images.model.transform;


import images.model.clamping.ClampColor;

/**
 * This class extends AbstractImageColorTransform class and provides some
 * implementations to the features related to transforming an image to Sepia
 * tone.
 * 
 * @author dileepshah
 *
 */
public final class SepiaToneTransform extends AbstractImageColorTransform {
  private static final double[]  [] transformationMatrix;

  static {
    transformationMatrix = new double[][] { new double[] { 0.393, 0.769, 0.189 },
        new double[] { 0.349, 0.686, 0.168 }, new double[] { 0.272, 0.534, 0.131 } };
  }

  /**
   * Default constructor to initialize the Spepia Tone transformation with 3*3
   * transformation matrix.
   * 
   * @param clamper the color clamper
   */
  SepiaToneTransform(ClampColor clamper) throws IllegalArgumentException {
    super(SepiaToneTransform.transformationMatrix, clamper);
  }

}
