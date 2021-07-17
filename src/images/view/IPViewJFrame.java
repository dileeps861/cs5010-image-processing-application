package images.view;


import images.PatternUtilities;
import images.beans.IPatternBean;
import images.view.component.BatchFileTab;
import images.view.component.FileTypeFilter;
import images.view.component.GenerateCustomColorPatternDialog;
import images.view.component.IPMenuBar;
import images.view.component.ImageProcessingTabs;
import images.view.component.OriginalImagePanel;
import images.view.component.PatternPropertiesPanel;
import images.view.component.PatternRenderFrame;
import images.view.component.ProcessedImagePanel;
import images.view.component.ProcessingPanel;
import images.view.component.ReplaceColorSelectDialog;
import images.view.component.TextMessagePanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;


/**
 * Implements IImageProcessingView interface and extends the JFrame class and
 * works as view frame, this the the main implementation of the view and
 * bootstraps the UI.
 *
 * @author dileepshah
 */
public class IPViewJFrame extends JFrame implements IImageProcessingView {
  private static final long serialVersionUID = -1191597643512370920L;
  private final IPMenuBar menuBar;

  private final OriginalImagePanel originalImagePanel;
  private final ProcessedImagePanel processedImagePanel;
  private final ProcessingPanel processingPanel;
  private final BatchFileTab batchFileTab;
  private final PatternRenderFrame patternRenderFrame;
  private final ReplaceColorSelectDialog replaceColorDialog;
  private final Map<String, Integer[]> dmcColorMap;
  private final GenerateCustomColorPatternDialog customColorPatternDialog;
  private final PatternPropertiesPanel patternPropertiesPanel;
  private IPatternBean generatedPattern;
  private String loadedImageFilePath;

  /**
   * Frame to view components to the GUI.
   *
   * @param caption the caption of the frame.
   */
  public IPViewJFrame(String caption) {
    super(caption);
    this.setPreferredSize(new Dimension(1050, 900));
    this.patternRenderFrame = new PatternRenderFrame();
    patternRenderFrame.setVisible(false);

    // Menu bar
    menuBar = new IPMenuBar();
    menuBar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    this.setJMenuBar(menuBar);
    // End of menu bar

    // Create object of replace color dialog.
    replaceColorDialog = new ReplaceColorSelectDialog();
    replaceColorDialog.setVisible(false);

    // Populate all the dmc colors.
    dmcColorMap = PatternUtilities.getDMCMap();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    // Variables declaration - do not modify
    this.processingPanel = new ProcessingPanel();

    // Dialog to select custom colors
    customColorPatternDialog = new GenerateCustomColorPatternDialog();
    customColorPatternDialog.setVisible(false);

    this.originalImagePanel = new OriginalImagePanel();
    this.processedImagePanel = new ProcessedImagePanel();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // Layout Setup

    JPanel panel = new JPanel();

    GroupLayout layout = new GroupLayout(panel);
    panel.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    GroupLayout originalImagePanelLayout = new GroupLayout(
        originalImagePanel);
    originalImagePanel.setLayout(originalImagePanelLayout);
    originalImagePanelLayout.setHorizontalGroup(
        originalImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 425, Short.MAX_VALUE));
    originalImagePanelLayout.setVerticalGroup(
        originalImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE));

    GroupLayout processedImagePanelLayout = new GroupLayout(
        processedImagePanel);
    processedImagePanel.setLayout(processedImagePanelLayout);
    processedImagePanelLayout.setHorizontalGroup(
        processedImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 352, Short.MAX_VALUE));
    processedImagePanelLayout.setVerticalGroup(
        processedImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE));

    JPanel imagesPanel = new JPanel();
    GroupLayout jPanel1Layout = new GroupLayout(imagesPanel);
    imagesPanel.setLayout(jPanel1Layout);

    // PatternPropertiesPanel setup
    patternPropertiesPanel = new PatternPropertiesPanel();
    patternPropertiesPanel.setVisible(false);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
                .addComponent(originalImagePanel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

                .addComponent(processedImagePanel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup().addContainerGap()
                .addComponent(originalImagePanel, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())

            .addComponent(processedImagePanel, GroupLayout.DEFAULT_SIZE,
                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)

                .addGroup(layout.createSequentialGroup().addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(processingPanel, GroupLayout.Alignment.TRAILING,
                            GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup().addComponent(imagesPanel,
                            GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE)

                        )

                        .addComponent(patternPropertiesPanel, GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

                    )))

        ));
    layout
        .setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()

                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(processingPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGap(0, 50, Short.MAX_VALUE)
                    .addComponent(imagesPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)

                    .addGap(0, 50, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patternPropertiesPanel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

            ));

    ImageProcessingTabs tabs = new ImageProcessingTabs();
    tabs.addTab(panel, "Image processing", 0);
    this.batchFileTab = new BatchFileTab();
    tabs.addTab(batchFileTab, "Batch Processing", 1);

    this.add(tabs);

    pack();
  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    if (actionListener != null) {

      // Menu bar action listeners
      if (menuBar != null) {
        menuBar.addActionListener(actionListener);
      }

      if (processingPanel != null) {
        processingPanel.addActionListener(actionListener);
      }
      if (batchFileTab != null) {
        batchFileTab.addActionListener(actionListener);
      }
      if (this.processedImagePanel != null) {
        processedImagePanel.addActionListener(actionListener);
      }
      if (this.replaceColorDialog != null) {
        this.replaceColorDialog.addActionListener(actionListener);
      }
      if (customColorPatternDialog != null) {
        this.customColorPatternDialog.addActionListener(actionListener);
      }
      if (this.patternPropertiesPanel != null) {
        this.patternPropertiesPanel.addActionListener(actionListener);
      }
    }

  }

  @Override
  public void setMessage(String message) {
    if (message != null && message.contains("\n")) {
      TextMessagePanel messagePanel = new TextMessagePanel();

      messagePanel.addMessage(message);
      JOptionPane.showMessageDialog(this, messagePanel);

    } else {
      JTextField textField = new JTextField(message);
      textField.setEditable(false);
      textField.setBackground(Color.RED);
      textField.setForeground(Color.WHITE);
      JOptionPane.showMessageDialog(this, textField);
    }

  }

  @Override
  public void loadImage() {

    JFileChooser imageFileChooser = new JFileChooser();

    imageFileChooser.addChoosableFileFilter(new FileTypeFilter("GIF image", ".gif"));
    imageFileChooser.addChoosableFileFilter(new FileTypeFilter("png image", ".png"));
    imageFileChooser.addChoosableFileFilter(new FileTypeFilter("png image", ".png"));
    imageFileChooser.addChoosableFileFilter(new FileTypeFilter("jpg image", ".jpg"));

    int userSelection = imageFileChooser.showOpenDialog(this.getParent());

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = imageFileChooser.getSelectedFile();
      loadedImageFilePath = fileToSave.getAbsolutePath();
    }
  }

  @Override
  public void showLoadedImage(BufferedImage image) {
    this.patternRenderFrame.setVisible(false);
    if (image != null) {
      this.originalImagePanel.setImage(image);
      this.processedImagePanel.setImage(null);
      this.resetPattern();
      this.resetProcess();
      this.repaint();
    }
  }

  @Override
  public String saveFile() {

    JFileChooser fileChooser = new JFileChooser();

    int userSelection = fileChooser.showSaveDialog(this);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fileChooser.getSelectedFile();
      return fileToSave.getAbsolutePath();

    }
    return null;

  }

  @Override
  public String getLoadedImagePath() {
    return loadedImageFilePath;
  }

  @Override
  public void showProcessedImage(BufferedImage image) {
    this.patternRenderFrame.setVisible(false);
    this.processedImagePanel.setImage(image);
    this.repaint();

  }

  @Override
  public void setProcessingValuesVisible(boolean visibility) {
    this.processingPanel.setProcessingValuesVisible(visibility);

  }

  @Override
  public String getUserProvideSelectionValues() {
    return this.processingPanel.getUserProvideSelectionValues();
  }

  @Override
  public void showPattern(IPatternBean imagePattern) {
    
    this.patternRenderFrame.setVisible(false);
    if (imagePattern != null) {
      this.generatedPattern = imagePattern;
      this.patternRenderFrame.setPatternText(imagePattern.getPattern());
      this.patternRenderFrame.setVisible(true);
      this.patternPropertiesPanel.setVisible(true);
      this.patternPropertiesPanel.populatePatternLegendList(imagePattern.getLegend());
      this.replaceColorDialog.populateColorsList(imagePattern.getLegend());
    }
    else {
      this.generatedPattern = null;
    }

  }

  @Override
  public String getBatchFileText() {
    return this.batchFileTab.getBatchFile();
  }

  @Override
  public IPatternBean getPattern() {

    return this.generatedPattern;
  }

  @Override
  public void setSelectedMenu(String menuCommand) {
    this.processingPanel.setSelectedMenuCommand(menuCommand);
  }

  @Override
  public String getReplacingColor() {
    String dmcColor = this.replaceColorDialog.getSelectedValue();
    if (dmcColor != null && dmcColorMap != null && dmcColorMap.containsKey(dmcColor)) {
      return dmcColor;
    }
    return null;
  }

  @Override
  public String getToReplaceColor() {

    return this.processedImagePanel.getToReplaceColor();
  }

  @Override
  public void toggleRemoveColorButton(boolean isEnable) {
    this.patternPropertiesPanel.toggleRemoveColorButton(isEnable);

  }

  @Override
  public void toggleReplaceColorButton(boolean isEnable) {
    this.patternPropertiesPanel.toggleReplaceColorButton(isEnable);

  }

  @Override
  public void resetPattern() {
    this.generatedPattern = null;
    this.patternRenderFrame.setVisible(false);
    this.patternPropertiesPanel.setVisible(false);
  }

  @Override
  public void resetProcess() {
    this.processedImagePanel.setImage(null);
    this.patternRenderFrame.setVisible(false);
    this.generatedPattern = null;
    this.patternPropertiesPanel.setVisible(false);
  }

  @Override
  public void popupSelectColorDialog() {

    this.replaceColorDialog.setVisible(true);

  }

  @Override
  public void popupCustomColorsDialog() {
    this.customColorPatternDialog.clearSelection();
    this.customColorPatternDialog.setVisible(true);

  }

  @Override
  public Set<String> getSelectColorsForPattern() {

    return this.customColorPatternDialog.getSelectColorsForPattern();
  }

  @Override
  public String getNumberOfChunksForPattern() {

    return this.customColorPatternDialog.getNumberOfChunksForPattern();
  }

}
