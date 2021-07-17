package images.command;


import images.model.IModel;
import images.model.image.Image;

/**
 * Command for transform the image.
 */
public class GrayscaleImageCommand extends AbstractImageProcessingCommand {

  /**
   * Constructor to create MosaicImageCommand object.
   * 
   * @param model the model
   * @throws IllegalArgumentException the illegal argument exception
   */
  public GrayscaleImageCommand(IModel model)
      throws IllegalArgumentException {
    super(model);

  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("GrayscaleImageCommand{");
    sb.append('}');
    return sb.toString();
  }

  @Override
  public Image execute(Image image) {
    return getModel().transformImageColorToGrayScale(image);
  }
}
