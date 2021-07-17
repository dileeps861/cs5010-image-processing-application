package images.controller;


import images.model.IModel;
import images.model.image.Image;
import images.utilities.ClientUtility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * Implementation to ImageProcessingController.
 */
public class ImageProcessingControllerImpl implements ImageProcessingController {
  private final Readable in;
  private final Appendable out;
  private final CommandController commandController;
  private final CommandGenerator commandGenerator;
  private final ClientUtility clientUtility;
  private final IModel model;

  /**
   * Constructor to create the ImageProcessingController.
   *
   * @param in                the input Readable
   * @param out               the output Appendable
   * @param commandController the command Controller
   * @param commandGenerator  the command generator
   * @param clientUtility     the file utility object
   * @param model             the model
   */
  public ImageProcessingControllerImpl(Readable in, Appendable out,
      CommandController commandController, CommandGenerator commandGenerator,
      ClientUtility clientUtility, IModel model) throws IllegalArgumentException {

    if (in == null || out == null || commandController == null || commandGenerator == null
        || model == null || clientUtility == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Arguments must not be null.");
    }

    this.in = in;
    this.out = out;
    this.commandController = commandController;
    this.commandGenerator = commandGenerator;

    this.model = model;
    this.clientUtility = clientUtility;
  }

  @Override
  public void start() throws IOException, IllegalArgumentException {
    try (Scanner scanner = new Scanner(in)) {
      Image image = null;
      String pattern = "";
      int numberOfChunks = -1;
      while (scanner.hasNext()) {
        String inputString = scanner.nextLine();
        String[] commandsArray = inputString.split(" ");
        if (inputString.trim().isEmpty()) {
          continue;
        }
        
        if (inputString.contains("load")) {

          if (commandsArray.length <= 1) {

            throw new IllegalArgumentException(
                "Load command must be in format \"load <filename>.\"");

          }
          image = clientUtility.loadImage(commandsArray[1].trim());
          this.out
              .append(
                  String.format("Image with name %s loaded successfully.", commandsArray[1].trim()))
              .append("\n");
        } else if (inputString.contains("save")) {
          if (commandsArray.length <= 1) {

            throw new IllegalArgumentException(
                "Save command must be in format \"save <filename>.\"");

          }
          String fileName = "./generated-files/" + commandsArray[1].trim();
          if (fileName.contains(".txt")) {
            if (pattern == null || pattern.trim().equals("")) {
              this.out.append("No Pattern generated to save.\n");
            } else {
              clientUtility.saveTextFile(pattern, fileName, StandardCharsets.UTF_16LE);
            }

            this.out
                .append(String.format("Pattern with pattern file name \"%s\", saved successfully.",
                    commandsArray[1].trim()))
                .append("\n");
            pattern = "";
          } else if (image != null) {

            clientUtility.saveImageFile(image, fileName);
            this.out.append(String.format("Image with name \"%s\" saved successfully.",
                commandsArray[1].trim())).append("\n");

          }

        } else {

          if (inputString.contains("pattern")) {
            String[] patternArray = inputString.split(" ");
            if (patternArray.length > 1) {
              numberOfChunks = patternArray[1].trim().matches("\\d+")
                  ? Integer.parseInt(patternArray[1].trim())
                  : numberOfChunks;
            }
            numberOfChunks = numberOfChunks != -1 ? numberOfChunks : 1;
            if (model.generatePattern(image, numberOfChunks) != null) {
              pattern = model.generatePattern(image, numberOfChunks).getPattern();
            }

            numberOfChunks = -1;

          } else {

            if (inputString.contains("pixelate")) {

              String[] commandArray = extractCommand(inputString);
              if (commandArray.length < 2 || !commandArray[1].trim().matches("[0-9]+")) {
                throw new IllegalArgumentException(
                    "Pixelate command must be in format \"pixelate <number_of_squares>.\"");
              }
              numberOfChunks = Integer.parseInt(commandArray[1].trim());

            }

            getCommandController()
                .setCommand(getCommandGenerator().createCommand(inputString, this.model));
            image = getCommandController().runCommand(image);
          }
          this.out.append(
              String.format("Image processing command \"%s\" called successfully.", inputString))
              .append("\n");
        }
      }
    } catch (IOException e) {
      this.out.append("Error while saving reading/ writing the file: ");
      this.out.append(e.getMessage());
      this.out.append("\n");
    } catch (IllegalArgumentException e) {
      this.out.append(e.getMessage());
      this.out.append("\n");
    }
  }

  private String[] extractCommand(String commandString) {
    return commandString.split(" ");
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ImageProcessingControllerImpl{");
    sb.append("in=").append(in);
    sb.append(", out=").append(out);
    sb.append(", commandController=").append(getCommandController());
    sb.append(", commandGenerator=").append(getCommandGenerator());
    sb.append(", clientUtility=").append(clientUtility);
    sb.append(", model=").append(model);
    sb.append('}');
    return sb.toString();
  }

  CommandGenerator getCommandGenerator() {
    return commandGenerator;
  }

  CommandController getCommandController() {
    return commandController;
  }

}
