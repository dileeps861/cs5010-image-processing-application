package images.view;

import images.beans.IPatternBean;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 * View Interface for Image processing. This interface is the single point of
 * contact and template for the UI implementation.
 * 
 * @author dileepshah
 *
 */
public interface IImageProcessingView {

  /**
   * Method to load image from file explorer.
   */
  void loadImage();

  /**
   * Method to show loaded image.
   * 
   * @param image the loaded image
   */
  void showLoadedImage(BufferedImage image);

  /**
   * Method to show processed image.
   * 
   * @param image the processed image
   */
  void showProcessedImage(BufferedImage image);

  /**
   * Save the file to file system.
   */
  String saveFile();

  /**
   * Get the loaded image for processing.
   * 
   * @return the loaded buffered image
   */
  String getLoadedImagePath();

  /**
   * Action to perform.
   * 
   * @param actionListener the action listener
   */
  void addActionListener(ActionListener actionListener);

  /**
   * Set the message to view.
   *
   * @param message the action listener
   */
  void setMessage(String message);

  /**
   * Set the visibility of fields for setting seed/ dither/ pixelate value.
   * 
   * @param visibility the visibility status
   */
  void setProcessingValuesVisible(boolean visibility);

  /**
   * The user provided processing values.
   * 
   * @return the user provided value
   */
  String getUserProvideSelectionValues();

  /**
   * Returns the batch file text.
   * 
   * @return the batch file
   */
  String getBatchFileText();

  /**
   * Sets the selected menu in the view.
   * 
   * @param menuCommand the menu command
   */
  void setSelectedMenu(String menuCommand);

  /**
   * Set toggle remove color button.
   * 
   * @param isEnable is to be enabled.
   */
  void toggleRemoveColorButton(boolean isEnable);

  /**
   * Set toggle replace color button.
   * 
   * @param isEnable is to be enabled.
   */
  void toggleReplaceColorButton(boolean isEnable);

  /**
   * New color to replace the color of image.
   * 
   * @return the rbg color value
   */
  String getReplacingColor();

  /**
   * Old color to be replaced from the image.
   * 
   * @return the rbg color value
   */
  String getToReplaceColor();

  /**
   * Callback method to show pattern to view.
   * 
   * @param patternBean bean of pattern
   */
  void showPattern(IPatternBean patternBean);

  /**
   * Method to get the PatternBean from view.
   * 
   * @return patternBean bean of pattern
   */
  IPatternBean getPattern();

  /**
   * Resets the pattern generated.
   */
  void resetPattern();

  /**
   * Resets the processed image.
   */
  void resetProcess();

  /**
   * Pops up the select color panel for replacing color of pattern.
   */
  void popupSelectColorDialog();

  /**
   * Returns the selected dmc colors code.
   * 
   * @return the dmc color code set
   */
  Set<String> getSelectColorsForPattern();

  /**
   * Returns the number of chunks for pattern with user specific color generation.
   * 
   * @return the number of chunks
   */
  String getNumberOfChunksForPattern();

  /**
   * Pops up select custom colors in hand to give the user flexibility to generate
   * pattern of their choice.
   */
  void popupCustomColorsDialog();

}
