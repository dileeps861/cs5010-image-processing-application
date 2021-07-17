package images.view;

import images.beans.IPatternBean;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;



/**
 * Mock of IImageProcessingView interface. Create only for the purpose of
 * testing controller.
 * 
 * @author dileepshah
 *
 */
public class IImageProcessingViewMock implements IImageProcessingView {
  private Appendable appendable;

  private String batchFile;
  private String numberOfChunksForPattern;
  private String toReplaceColor;
  private IPatternBean patternBean;
  private String replacingColor;
  private Set<String> selectedColorForPattern;
  private String fileName;
  private String uploadedImagePath;
  private String userProvideSelectionValues;

  /**
   * Set the Uploaded image path.
   * 
   * @param uploadedImagePath the uploadedImagePath to set
   */
  public void setUploadedImagePath(String uploadedImagePath) {
    this.uploadedImagePath = uploadedImagePath;
  }

  /**
   * Set the select color for pattern.
   * 
   * @param selectedColorForPattern selected color
   */
  public void setSelectedColorForPattern(Set<String> selectedColorForPattern) {
    this.selectedColorForPattern = selectedColorForPattern;
  }


  /**
   * Set the batch file.
   * 
   * @param batchFile the batch file
   */
  public void setBatchFile(String batchFile) {
    this.batchFile = batchFile;
  }

  /**
   * Set the number of chunks for pattern.
   * 
   * @param numberOfChunksForPattern the chunks count
   */
  public void setNumberOfChunksForPattern(String numberOfChunksForPattern) {
    this.numberOfChunksForPattern = numberOfChunksForPattern;
  }

  /**
   * Old color set.
   * 
   * @param toReplaceColor the old color
   */
  public void setToReplaceColor(String toReplaceColor) {
    this.toReplaceColor = toReplaceColor;
  }

  /**
   * The new color setter.
   * 
   * @param replacingColor the new color
   */
  public void setReplacingColor(String replacingColor) {
    this.replacingColor = replacingColor;
  }

  /**
   * Set the user provided value, only for testing purpose.
   * 
   * @param userProvideSelectionValues the user provided value
   */
  public void setUserProvideSelectionValues(String userProvideSelectionValues) {
    this.userProvideSelectionValues = userProvideSelectionValues;

  }

  /**
   * Constructor to create object of IImageProcessingViewMock.
   * 
   * @param out the appendable object
   */
  public IImageProcessingViewMock(Appendable out) {
    this.appendable = out;
  }

  @Override
  public void loadImage() {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".loadImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
  }

  @Override
  public void showLoadedImage(BufferedImage image) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".showLoadedImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
  }

  @Override
  public void showProcessedImage(BufferedImage image) {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".showProcessedImage() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
  }

  public void setPatternBean(IPatternBean patternBean) {
    this.patternBean = patternBean;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public String saveFile() {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".saveFile() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return this.fileName;
  }

  @Override
  public String getLoadedImagePath() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".getLoadedImagePath() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return uploadedImagePath;
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".addActionListener() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
  }

  @Override
  public void setMessage(String message) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".setMessage() " + "Invoked\n");
      this.appendable.append(message);
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
  }

  @Override
  public void setProcessingValuesVisible(boolean visibility) {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".setProcessingValuesVisible() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
  }

  @Override
  public String getUserProvideSelectionValues() {
    try {
      this.appendable.append(
          this.getClass().getSimpleName() + ".getUserProvideSelectionValues() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return userProvideSelectionValues;
  }

  @Override
  public String getBatchFileText() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".getBatchFileText() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return batchFile;
  }

  @Override
  public void setSelectedMenu(String menuCommand) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".setSelectedMenu() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("IImageProcessingViewMock{");
    sb.append('}');
    return sb.toString();
  }

  @Override
  public void toggleRemoveColorButton(boolean isEnable) {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".toggleRemoveColorButton() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

  }

  @Override
  public void toggleReplaceColorButton(boolean isEnable) {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".toggleReplaceColorButton() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

  }

  @Override
  public IPatternBean getPattern() {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".getPattern() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

    return this.patternBean;
  }

  @Override
  public String getReplacingColor() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".getReplacingColor() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return replacingColor;
  }

  @Override
  public String getToReplaceColor() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".getToReplaceColor() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return toReplaceColor;
  }

  @Override
  public void showPattern(IPatternBean patternBean) {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".showPattern() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

  }

  @Override
  public void resetPattern() {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".resetPattern() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

  }

  @Override
  public void popupSelectColorDialog() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".popupSelectColorDialog() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

  }

  @Override
  public Set<String> getSelectColorsForPattern() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".getSelectColorsForPattern() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return selectedColorForPattern;
  }

  @Override
  public String getNumberOfChunksForPattern() {
    try {
      this.appendable.append(
          this.getClass().getSimpleName() + ".getNumberOfChunksForPattern() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }
    return numberOfChunksForPattern;
  }

  @Override
  public void popupCustomColorsDialog() {
    try {
      this.appendable
          .append(this.getClass().getSimpleName() + ".popupCustomColorsDialog() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

  }

  @Override
  public void resetProcess() {
    try {
      this.appendable.append(this.getClass().getSimpleName() + ".resetProcess() " + "Invoked\n");
    } catch (IOException e) {
      throw new AssertionError("Failed to append to appendable.");
    }

  }

}
