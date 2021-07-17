package images.controller;

import images.beans.IPatternBean;
import images.command.ImageProcessingCommand;
import images.constants.EditingOptions;
import images.model.IModel;
import images.model.image.Image;
import images.utilities.ClientUtility;
import images.view.IImageProcessingView;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;


/**
 * Controller for controlling the UI based image processing. 
 *
 * @author dileepshah
 */
public class UIController implements IUIController, Features {
  private final CommandController commandController;
  private final CommandGenerator commandGenerator;
  private final ClientUtility clientUtility;
  private final IModel model;
  private IImageProcessingView view;
  private String command;

  /**
   * Constructor to create the UIController object.
   *
   * @param commandController the command Controller
   * @param commandGenerator  the command generator
   * @param clientUtility     the file utility object
   * @param model             the model object
   */
  public UIController(CommandController commandController, CommandGenerator commandGenerator,
      ClientUtility clientUtility, IModel model) throws IllegalArgumentException {
    if (commandController == null || commandGenerator == null || clientUtility == null
        || model == null) {
      throw new IllegalArgumentException(
          this.getClass().getSimpleName() + ": Arguments must not be null");
    }
    this.commandController = commandController;
    this.commandGenerator = commandGenerator;
    this.clientUtility = clientUtility;
    this.model = model;
  }

  @Override
  public void setView(IImageProcessingView view) {

    this.view = view;

  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  /**
   * Validates if the value in argument is a number. If it is a number true will
   * be returned.
   *
   * @param value the value to validate
   * @return boolean value
   */
  private boolean validateNumber(String value) {
    return value != null && !value.isEmpty() && value.matches("\\d+")
        && Integer.parseInt(value) != 0;
  }

  private void toggleColorChangeButtons(boolean status) {
    this.view.toggleRemoveColorButton(status);
    this.view.toggleReplaceColorButton(status);
  }

  private boolean validateImage(String image) {
    if (image == null) {
      this.view.setMessage("You must load the image before proceeding to process the image.");
      return true;
    }
    return false;
  }

  private void setCommandValidateMessage(String commandValue) {
    this.view.setMessage("Pixelation/ Mosaic/ Dither/ Pattern user provided "
        + "value must be specified and must be a non zero number. Provided: " + commandValue);
  }

  private void generatePattern(Image image, String commandValue) {
    if (commandValue == null) {

      setCommandValidateMessage(null);
      return;
    }
    IPatternBean patternBean = model.generatePattern(image, Integer.parseInt(commandValue));
    if (patternBean == null) {
      this.view.setMessage("Pattern could not be generated.");
      return;
    }
    BufferedImage processedImage = clientUtility.getBufferedImage(patternBean.getImage());

    showImage(processedImage);

    showPattern(patternBean);
    toggleColorChangeButtons(true);
  }

  private void processImageHelper(Image image, String commandValue) {
    String command = this.command;
    if (commandValue != null && !commandValue.trim().isEmpty()) {
      command += " " + commandValue.trim();
    }
    ImageProcessingCommand commandObject = commandGenerator.createCommand(command, model);
    commandController.setCommand(commandObject);
    BufferedImage processedImage = clientUtility
        .getBufferedImage(commandController.runCommand(image));
    showImage(processedImage);

  }

  private Image getImage() {
    Image image = null;
    if (model.getImage() == null && model.getPatternBean() == null) {
      String loadedImage = this.view.getLoadedImagePath();
      if (validateImage(loadedImage)) {
        return null;
      }
      try {
        image = clientUtility.loadImage(loadedImage);
      } catch (IOException e) {
        this.view.setMessage(e.getMessage());
      }

    } else if (model.getPatternBean() != null) {
      image = model.getPatternBean().getImage();
    } else {
      image = model.getImage();
    }
    return image;
  }

  @Override
  public void processImage() {

    if (command == null || command.isEmpty()) {
      this.view.setMessage("You must select editing option before processing the image.");
      return;
    }
    Image image = getImage();

    if (getImage() == null) {
      return;
    }

    String commandValue = this.view.getUserProvideSelectionValues();
    if (!assertCommand(command) && commandValue != null && (commandValue.trim().isEmpty()
        || commandValue.trim().equals("0") || !commandValue.matches("\\d+"))) {
      setCommandValidateMessage(commandValue);
      return;
    }
    try {

      toggleColorChangeButtons(false);

      if (command.contains(EditingOptions.GENERATE_PATTERN.toString())) {
        generatePattern(image, commandValue);
      } else {
        processImageHelper(image, commandValue);
      }
    } catch (IllegalArgumentException e) {
      this.view.setMessage(e.getMessage());
    }

  }

  @Override
  public void generatePatternWithCustomColors() {

    Image image = this.model.getImage();
    String uploadedImagePath = this.view.getLoadedImagePath();
    if (image == null) {

      if (validateImage(uploadedImagePath)) {
        return;
      } else {
        try {
          image = clientUtility.loadImage(uploadedImagePath);
        } catch (IllegalArgumentException | IOException e) {
          this.view.setMessage(e.getMessage());
          return;
        }

      }
    }
    Set<String> selectedColors = this.view.getSelectColorsForPattern();

    if (selectedColors == null || selectedColors.isEmpty()) {
      this.view.setMessage("Custom colors are not selected.");
      return;
    }

    String numberOfChunksForPattern = this.view.getNumberOfChunksForPattern();
    if (!validateNumber(numberOfChunksForPattern)) {
      this.view
          .setMessage("Number of chunks for pattern generation must be a number greater than zero");
      return;
    }
    try {
      int numberOfChunks = Integer.parseInt(numberOfChunksForPattern);
      IPatternBean patternBean = this.model.generatePattern(image, numberOfChunks, selectedColors);

      this.view.showProcessedImage(clientUtility.getBufferedImage(patternBean.getImage()));
      this.view.showPattern(patternBean);
      toggleColorChangeButtons(true);
    } catch (IllegalArgumentException e) {
      this.view.setMessage(e.getMessage());
    }

  }

  @Override
  public void loadImage() {
    this.view.loadImage();
    try {

      Image image = this.clientUtility.loadImage(this.view.getLoadedImagePath());
      BufferedImage bufferedImage = this.clientUtility.getBufferedImage(image);
      this.view.showLoadedImage(bufferedImage);
    } catch (IllegalArgumentException | IOException e) {
      this.view.setMessage(e.getMessage());
    }

  }

  private void showImage(BufferedImage image) {
    this.view.showProcessedImage(image);

  }

  private void showPattern(IPatternBean patternBean) {
    this.view.showPattern(patternBean);

  }

  @Override
  public void runBatchFile() {
    String batchFileText = this.view.getBatchFileText();
    if (batchFileText != null && !batchFileText.isEmpty()) {
      Readable in = new StringReader(batchFileText);
      StringBuilder out = new StringBuilder();
      ImageProcessingController controllerNew = new ImageProcessingControllerImpl(in, out,
          commandController, commandGenerator, clientUtility, model);
      try {
        controllerNew.start();
        this.view.setMessage(out.toString());
      } catch (IOException e) {
        this.view.setMessage(e.getMessage());
      }
    } else {
      this.view.setMessage("Nothing to read from the batch file.");
    }

  }

  private boolean validateString(String string) {
    return string == null || string.isEmpty();
  }

  @Override
  public void saveFile() {
    if (this.model.getPatternBean() != null) {
      this.saveTextFile();

    } else if (this.model.getImage() != null) {
      this.saveImage();
    } else {
      this.view.setMessage(
          "You must load and process the image before proceeding to save the image/ pattern.");
    }

  }

  /**
   * Helper method to save image file.
   */
  private void saveImage() {

    Image image = this.model.getImage();

    String filePath = this.view.saveFile();
    if (validateString(filePath)) {
      this.view.setMessage("Not a valid path: " + filePath);
      return;
    }

    try {
      clientUtility.saveImageFile(image, filePath);
      this.view.setMessage("Image saved to file location: " + filePath);
    } catch (IllegalArgumentException | IOException e) {
      this.view.setMessage(e.getMessage());
    }

  }

  /**
   * Helper method to save text file.
   */
  private void saveTextFile() {
    IPatternBean patternBean = this.model.getPatternBean();
    String filePath = this.view.saveFile();
    if (validateString(filePath)) {
      this.view.setMessage("Pattern and file data must be given.\n file name:" + filePath);
      return;
    }
    try {
      clientUtility.saveTextFile(patternBean.getPattern(), filePath, StandardCharsets.UTF_16LE);
      this.view.setMessage("File saved to file location: " + filePath);
    } catch (IllegalArgumentException | IOException e) {
      this.view.setMessage(e.getMessage());
    }

  }

  private void replaceColors(IPatternBean patternBean, String oldColor, String newColor) {

    if (patternBean != null) {
      try {

        patternBean = model.replaceColor(patternBean, oldColor, newColor);
        this.view.showProcessedImage(clientUtility.getBufferedImage(patternBean.getImage()));
        this.view.showPattern(patternBean);
      } catch (IllegalArgumentException e) {
        this.view.setMessage(e.getMessage());
      }

    } else {
      this.view.setMessage("Image is not processed to replace the color.");
    }

  }

  @Override
  public void replacePatternColor() {
    if (this.view.getToReplaceColor() == null) {
      this.view.setMessage("To remove the color, first select the color from image.");
      return;
    }
    if (this.view.getReplacingColor() == null) {
      this.view.setMessage("Please provide the new color to replace.");
      return;
    }

    replaceColors(this.model.getPatternBean(), this.view.getToReplaceColor(),
        this.view.getReplacingColor());
  }

  @Override
  public void removePatternColor() {
    if (this.view.getToReplaceColor() == null) {
      this.view.setMessage("To remove the color first select the color from image.");
      return;
    }

    replaceColors(this.view.getPattern(), this.view.getToReplaceColor(), "310");
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UIController{");
    sb.append("commandController=").append(commandController);
    sb.append(", commandGenerator=").append(commandGenerator);
    sb.append(", clientUtility=").append(clientUtility);
    sb.append(", view=").append(view);
    sb.append(", model=").append(model);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public void resetProcessing() {
    toggleColorChangeButtons(false);
    this.view.resetPattern();
    this.view.resetProcess();
    this.model.clearProcessing();
  }

  @Override
  public void showMessage(String message) {
    this.view.setMessage(message);

  }

  private boolean assertCommand(String command) {
    return EditingOptions.BLUR.toString().equalsIgnoreCase(command)
        || EditingOptions.SHARPEN.toString().equalsIgnoreCase(command)
        || EditingOptions.SEPIA_TONE.toString().equalsIgnoreCase(command)
        || EditingOptions.GRAYSCALE.toString().equalsIgnoreCase(command);
  }

  @Override
  public void editOptionSelection(String command) {
    if (command == null) {
      this.view.setMessage("Invalid edit option selected.");
      return;
    }

    this.command = command;

    if (assertCommand(command)) {

      this.view.resetPattern();
      toggleUserInputField(command, false);
    } else if (command.equals(EditingOptions.PIXELATE.toString())
        || command.equals(EditingOptions.DITHER.toString())
        || command.equals(EditingOptions.MOSAIC.toString())) {
      this.view.resetPattern();

      toggleUserInputField(command, true);
    } else if (command.equals(EditingOptions.GENERATE_PATTERN.toString())) {
      toggleUserInputField(command, true);
    } else {

      this.view.setMessage("Error: unknown command > " + command);
    }

  }

  private void toggleUserInputField(String command, boolean toggleValue) {
    this.view.setSelectedMenu(command);
    this.view.setProcessingValuesVisible(toggleValue);
  }

  @Override
  public void patternWithCustomColorSelected() {
    this.view.popupCustomColorsDialog();

  }

  @Override
  public void replacePatternColorSelection() {
    this.view.popupSelectColorDialog();

  }

}
