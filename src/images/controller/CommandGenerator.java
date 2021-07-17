package images.controller;

import images.command.ImageProcessingCommand;
import images.model.IModel;

/**
 * ImageProcessingCommand Generator to generate the command objects.
 * 
 * @author dileepshah
 *
 */
public interface CommandGenerator {

  /**
   * Create ImageProcessingCommand based the Command name given in the String.
   * 
   * @param commandString the command name
   * @param model the model object
   * @return the ImageProcessingCommand object
   */
  ImageProcessingCommand createCommand(String commandString, IModel model);
}
