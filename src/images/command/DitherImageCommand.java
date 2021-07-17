package images.command;


import images.model.IModel;
import images.model.image.Image;

/**
 * Command to execute ImageColorDensity object.
 */
public class DitherImageCommand extends AbstractImageProcessingCommand {

  private final int numberOfColors;

  /**
   * Constructor to create MosaicImageCommand object.
   * 
   * @param numberOfColors the number of colors per channel
   * @throws IllegalArgumentException the illegal argument exception
   */
  public DitherImageCommand(IModel model, int numberOfColors)
      throws IllegalArgumentException {
    super(model);

    this.numberOfColors = numberOfColors;

  }

  @Override
  public Image execute(Image image) {
    return getModel().ditherImage(image, numberOfColors);
  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("DitherImageCommand{");
    sb.append("colorDensity=").append(numberOfColors);

    sb.append('}');
    return sb.toString();
  }
}
