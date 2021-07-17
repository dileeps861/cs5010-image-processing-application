package images.command;


import images.model.IModel;
import images.model.image.Image;

/**
 * Command for filter image.
 */
public class SharpenImageCommand extends AbstractImageProcessingCommand {
  /**
   * Constructor to create Sharpen command object.
   * 
   * @param model the model
   * @throws IllegalArgumentException the illegal argument exception
   */
  public SharpenImageCommand(IModel model) throws IllegalArgumentException {
    super(model);
   
  }

  @Override
  public Image execute(Image image) {
   
    return getModel().sharpenImage(image);
  }

  @Override
  public  String toString() {
    return "SharpenImageCommand{}";
  }
}
