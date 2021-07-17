package images.controller;

import images.view.IImageProcessingView;


/**
 * This interface represents a set of features that the image processing program
 * offers. Each feature is exposed as a function in this interface. This
 * function works as observer between view and controller. If anything is
 * changed from view then controller takes it from the model. And anything
 * returned from model, that will be rendered by view from here. View can
 * implement these features in whichever way they like to.
 */
public interface Features {

  /**
   * Exit the program.
   */
  void exitProgram();

  /**
   * Process the image except for pattern generation.
   */
  void processImage();

  /**
   * Generate the pattern of image.
   */
  void generatePatternWithCustomColors();

  /**
   * Callback method to load the image.
   */
  void loadImage();

  /**
   * Save the file to file system.
   */
  void saveFile();

  /**
   * Sets the controller to the observer.
   *
   * @param view the view
   */
  void setView(IImageProcessingView view);

  /**
   * Clears the processed Image to original image.
   * 
   */
  void resetProcessing();

  /**
   * Callback method to show pattern to view.
   */
  void runBatchFile();

  /**
   * Callback method to replace one color of the pattern.
   */
  void replacePatternColor();
  
  /**
   * Callback method to select replacing color of the pattern.
   */
  void replacePatternColorSelection();

  /**
   * Callback method to replace one color of the pattern.
   */
  void removePatternColor();

  /**
   * Show message to UI.
   * @param message the message to show
   */
  void showMessage(String message);
  
  /**
   * Feature to handle editing commands.
   * @param command the command string
   */
  void editOptionSelection(String command);
  
  /**
   * Feature to handle command of pattern with custom color menu selection.
   */
  void patternWithCustomColorSelected();
  


}
