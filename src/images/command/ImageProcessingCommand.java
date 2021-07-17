package images.command;

import images.model.image.Image;

/**
 * The command interface to execute models.
 * 
 * @author dileepshah
 *
 */
public interface ImageProcessingCommand {
  /**
   * Execute the model.
   * 
   * @param image the image object
   * @return the edited image
   */
  Image execute(Image image);
}
