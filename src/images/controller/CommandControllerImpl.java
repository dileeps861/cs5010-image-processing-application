package images.controller;

import images.command.ImageProcessingCommand;
import images.model.image.Image;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;




/**
 * The implementation of CommandController.
 *
 * @author dileepshah
 */
public class CommandControllerImpl implements CommandController {
  private final  Queue<ImageProcessingCommand> commands;

  /**
   * Command controller constructor.
   */
  public CommandControllerImpl() {
    this.commands = new ArrayDeque<>();
  }

  @Override
  public void setCommand( ImageProcessingCommand command) throws IllegalArgumentException {
    if (command == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Command cannot be null.");
    }
    commands.add(command);

  }

  @Override
  public  Image runCommand( Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Image cannot be null.");
    }
    if (commands.size() > 0) {
      return commands.remove().execute(image);
    } else {
      return createDummyImage();
    }
  }

  private  Image createDummyImage() {
    return new Image() {
      @Override
      public int getWidth() {
        return 0;
      }

      @Override
      public int getHeight() {
        return 0;
      }

      @Override
      public int[][][] getImage() {
        return new int[0][][];
      }

      @Override
      public  Image copyImage() {
        return this;
      }

      @Override
      public  Image padImage(int paddingSize) {
        return this;
      }

      @Override
      public  Image removePad(int paddingSize) {
        return this;
      }

      @Override
      public  String toString() {
        final StringBuilder sb = new StringBuilder("Dummy image.");
        return sb.toString();
      }
    };
  }

  @Override
  public  String toString() {
    List<String> commandList = Arrays.stream(commands.toArray())
        .map(Object::toString).collect(Collectors.toList());
    final StringBuilder sb = new StringBuilder("CommandControllerImpl{");
    sb.append("commands=").append(commandList);
    sb.append('}');
    return sb.toString();
  }
}
