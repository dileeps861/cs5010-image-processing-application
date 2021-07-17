package images.command;


import images.model.IModel;
import images.model.image.Image;

/**
 * Command for blur image.
 * @author dileepshah
 *
 */
public class BlurImageCommand extends AbstractImageProcessingCommand {

  /**
   * Constructor to create Blur command object.
   * 
   * @param model the model
   * @throws IllegalArgumentException the illegal argument exception
   */
  public BlurImageCommand(IModel model) throws IllegalArgumentException {
    super(model);
   
  }

  @Override
  public Image execute(Image image) {
   
    return getModel().blurImage(image);
  }

  @Override
  public  String toString() {
    return "BlurImageCommand{}";
  }

}
