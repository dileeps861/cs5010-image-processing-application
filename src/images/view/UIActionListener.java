package images.view;

import images.constants.EditingOptions;
import images.constants.ProcessingCommands;
import images.controller.Features;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action listener for the view. Handles the view components actions and the
 * submits the appropriate request to the controller.
 * 
 * @author dileepshah
 *
 */
public class UIActionListener implements ActionListener {

  private final Features observer;

  /**
   * Constructor to create UIActionListener object.
   * 
   * @param observer the observer
   */
  public UIActionListener(Features observer) {
    this.observer = observer;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e == null) {
      throw new IllegalArgumentException("Action event cannot be null.");
    }
    try {

      String commandString = e.getActionCommand();
      if (ProcessingCommands.EXIT.toString().equalsIgnoreCase(commandString)) {
        this.observer.exitProgram();
      } else if (ProcessingCommands.SAVE_IMAGE.toString().equalsIgnoreCase(commandString)) {
        this.observer.saveFile();

      } else if (ProcessingCommands.PROCESS_IMAGE.toString().equalsIgnoreCase(commandString)) {

        this.observer.processImage();

      } else if (EditingOptions.PATTERN_WITH_CUSTOM_COLORS.toString()
          .equalsIgnoreCase(commandString)) {
        this.observer.patternWithCustomColorSelected();

      } else if (ProcessingCommands.OK_CUSTOM_COLORS_SELECTION.toString()
          .equalsIgnoreCase(commandString)) {
        this.observer.generatePatternWithCustomColors();

      }

      else if (ProcessingCommands.CLEAR_PROCESSED_IMAGE.toString()
          .equalsIgnoreCase(commandString)) {
        this.observer.resetProcessing();

      } else if (ProcessingCommands.BATCH_RUN.toString().equalsIgnoreCase(commandString)) {
        this.observer.runBatchFile();

      } else if (ProcessingCommands.REMOVE_ONE_COLOR.toString().equalsIgnoreCase(commandString)) {

        this.observer.removePatternColor();

      } else if (ProcessingCommands.OK_COLOR_SELECTION.toString().equalsIgnoreCase(commandString)) {
        this.observer.replacePatternColor();
      }

      else if (ProcessingCommands.REPLACE_ONE_COLOR.toString().equalsIgnoreCase(commandString)) {

        this.observer.replacePatternColorSelection();

      } else if (ProcessingCommands.UPLOAD_IMAGE.toString().equalsIgnoreCase(commandString)) {
        this.observer.loadImage();
      } else {
        this.observer.editOptionSelection(commandString);
      }
    }

    catch (IllegalArgumentException exception) {
      this.observer.showMessage(exception.getMessage());
    }
  }

}
