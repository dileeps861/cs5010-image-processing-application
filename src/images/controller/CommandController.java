
package images.controller;

import images.command.ImageProcessingCommand;
import images.model.image.Image;

/**
 * The command controller to set and get the command.
 * 
 * @author dileepshah
 *
 */
public interface CommandController {

  /**
   * Set the command to command controller.
   * 
   * @param command the command to set
   */
  void setCommand(ImageProcessingCommand command);

  /**
   * Run the command one by one.
   * 
   * @return the edited image object.
   */
  Image runCommand(Image image);

}
