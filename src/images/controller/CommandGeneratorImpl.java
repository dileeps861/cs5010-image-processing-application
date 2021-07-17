package images.controller;

import images.command.BlurImageCommand;
import images.command.DitherImageCommand;
import images.command.GrayscaleImageCommand;
import images.command.ImageProcessingCommand;
import images.command.MosaicImageCommand;
import images.command.PixelateImageCommand;
import images.command.SepiaToneCommand;
import images.command.SharpenImageCommand;
import images.model.IModel;

/**
 * Implementation of CommandGenerator.
 * 
 * @author dileepshah
 *
 */
public class CommandGeneratorImpl implements CommandGenerator {

  @Override
  public ImageProcessingCommand createCommand(String commandString, IModel model)
      throws IllegalArgumentException {
   
    if (commandString == null || model == null) {
      throw new IllegalArgumentException("Command and Model cannot be null.");
    }
    ImageProcessingCommand imageProcessingCommand;
    String[] commandsArray = commandString.split(" ");

    switch (commandsArray[0].toLowerCase()) {
      case "dither":
  
        if (commandsArray.length <= 1) {
          throw new IllegalArgumentException(this.getClass().getSimpleName()
              + ": Dither image must be in format \"dither <noOfColors>\"");
        } else if (!commandsArray[1].matches("\\d+")
            || Integer.parseInt(commandsArray[1].trim()) < 1) {
          throw new IllegalArgumentException(this.getClass().getSimpleName()
              + ": Dither image no of colors must be a number greater than zero.");
        }
        imageProcessingCommand = new DitherImageCommand(model,
            Integer.parseInt(commandsArray[1].trim()));
        break;
      case "blur":
        imageProcessingCommand = new BlurImageCommand(model);
        break;
      case "sharpen":
        imageProcessingCommand = new SharpenImageCommand(model);
        break;
      case "grayscale":
        imageProcessingCommand = new GrayscaleImageCommand(model);
        break;
      case "sepia-tone":
      case "sepia_tone":
        imageProcessingCommand = new SepiaToneCommand(model);
        break;
      case "mosaic":
        if (commandsArray.length <= 1 || !commandsArray[1].matches("\\d+")
            || Integer.parseInt(commandsArray[1].trim()) < 1) {
          throw new IllegalArgumentException(this.getClass().getSimpleName()
              + ": Invalid mosaic command, must be in format: mosaic <seeds_values>.");
        }
        imageProcessingCommand = new MosaicImageCommand(model,
            Integer.parseInt(commandsArray[1].trim()));
        break;
      case "pixelate":
  
        if (commandsArray.length < 2 || !commandsArray[1].trim().matches("\\d+")
            || Integer.parseInt(commandsArray[1].trim()) < 1) {
          throw new IllegalArgumentException(this.getClass().getSimpleName()
              + ": Number of squares must be specified and must be more than zero.");
        }
  
        imageProcessingCommand = new PixelateImageCommand(model,
            Integer.parseInt(commandsArray[1].trim()));
        break;
  
      default:
        throw new IllegalArgumentException(
            this.getClass().getSimpleName() + ": No appropriate command found.");

    }
    return imageProcessingCommand;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CommandGeneratorImpl{");
    sb.append('}');
    return sb.toString();
  }
}
