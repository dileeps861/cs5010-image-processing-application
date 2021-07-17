package images.command;


import images.model.IModel;
import images.model.image.Image;

/**
 * Command for sepia tone.
 * @author dileepshah
 *
 */
public class SepiaToneCommand extends AbstractImageProcessingCommand {
  /**
   * Constructor to create Sepia tone command object.
   * 
   * @param model the model
   * @throws IllegalArgumentException the illegal argument exception
   */
  public SepiaToneCommand(IModel model)
      throws IllegalArgumentException {
    super(model);

  }

  @Override
  public  String toString() {
    final StringBuilder sb = new StringBuilder("SepiaToneCommand{");
    sb.append('}');
    return sb.toString();
  }

  @Override
  public Image execute(Image image) {
    return getModel().transformImageColorToSepiaTone(image);
  }

}
