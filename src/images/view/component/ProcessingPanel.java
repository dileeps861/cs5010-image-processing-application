package images.view.component;

import images.constants.ProcessingCommands;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * Panel for image processing buttons groups. This component includes buttons for
 * upload, process image, and save file.
 * 
 * @author dileepshah
 *
 */
public class ProcessingPanel extends JPanel {
  /**
   * The generated serialVersionUID.
   */
  private static final long serialVersionUID = 6251870789395655375L;

  private JButton processImageButton;
  private JButton saveFileButton;
  private JButton uploadImageButton;
  private JLabel processingValueLabel;
  private JTextField processingValueTF;
  private String value;

  private final JButton clearProcessedImageButton;

  /**
   * Creates new panel ProcessingPanel.
   */
  public ProcessingPanel() {

    
    this.clearProcessedImageButton = new JButton("Clear Processing");
    clearProcessedImageButton
        .setToolTipText("Clear the processed image, image will reset to original.");
    this.clearProcessedImageButton
        .setActionCommand(ProcessingCommands.CLEAR_PROCESSED_IMAGE.toString());
    initComponents();
  }

  private void initComponents() {
    processingValueLabel = new JLabel(
        "Provide mosaic seeds/ dither colors / pixelation or pattern no of squares value:");
    processingValueLabel.setVisible(false);
    processingValueTF = new JTextField();
    processingValueTF.setText("");
    processingValueTF.setVisible(false);
    processingValueTF.getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void removeUpdate(DocumentEvent e) {
        
        processingValueActionPerformed();
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        
        processingValueActionPerformed();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        
        processingValueActionPerformed();
      }
    });

    uploadImageButton = new JButton();
    processImageButton = new JButton();
    saveFileButton = new JButton();

    uploadImageButton.setText("Upload Image");
    uploadImageButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    uploadImageButton.setActionCommand(ProcessingCommands.UPLOAD_IMAGE.toString());

    processImageButton.setText("Process Image");
    processImageButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    processImageButton.setActionCommand(ProcessingCommands.PROCESS_IMAGE.toString());

    saveFileButton.setText("Save Generated File");
    saveFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    saveFileButton.setActionCommand(ProcessingCommands.SAVE_IMAGE.toString());

    this.setBorder(new SoftBevelBorder(BevelBorder.RAISED));

    GroupLayout processImageButtonsPanelLayout = new GroupLayout(this);
    this.setLayout(processImageButtonsPanelLayout);
    processImageButtonsPanelLayout.setHorizontalGroup(
        processImageButtonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(processImageButtonsPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(processingValueLabel).addGap(18, 18, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addComponent(processingValueTF, GroupLayout.PREFERRED_SIZE, 126,
                    GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(processImageButtonsPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(processImageButtonsPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(uploadImageButton).addGap(18, 18, Short.MAX_VALUE)
                .addComponent(processImageButton, GroupLayout.PREFERRED_SIZE, 134,
                    GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addComponent(clearProcessedImageButton, GroupLayout.PREFERRED_SIZE, 174,
                    GroupLayout.PREFERRED_SIZE)

                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)
                .addComponent(saveFileButton, GroupLayout.PREFERRED_SIZE, 176,
                    GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

    );

    processImageButtonsPanelLayout.setVerticalGroup(
        processImageButtonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(processImageButtonsPanelLayout
                .createSequentialGroup().addContainerGap()
                .addGroup(processImageButtonsPanelLayout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(processingValueLabel).addComponent(processingValueTF))
                .addGroup(processImageButtonsPanelLayout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadImageButton).addComponent(processImageButton)
                    .addComponent(clearProcessedImageButton).addComponent(saveFileButton))

                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

  }

  /**
   * Method to add action listener.
   * 
   * @param actionListener action listener
   */
  public void addActionListener(ActionListener actionListener) {
    if (actionListener != null) {
      this.clearProcessedImageButton.addActionListener(actionListener);
      this.uploadImageButton.addActionListener(actionListener);
      this.saveFileButton.addActionListener(actionListener);
      this.processImageButton.addActionListener(actionListener);

    }

  }

  /**
   * Set the visibility of fields for setting seed/ dither/ pixelate value.
   * 
   * @param visibility the visibility status
   */
  public void setProcessingValuesVisible(boolean visibility) {
    if (this.processingValueLabel != null && this.processingValueTF != null) {
      this.processingValueLabel.setVisible(visibility);
      this.processingValueTF.setVisible(visibility);
      if (!visibility) {
        this.processingValueTF.setText(null);
      }
      this.updateUI();
    }
  }

  private void processingValueActionPerformed() {

    this.value = processingValueTF.getText();

  }

  public String getUserProvideSelectionValues() {
    
    return this.value;
  }

  /**
   * Set the selected menu command.
   * 
   * @param menuCommand the menu command
   */
  public void setSelectedMenuCommand(String menuCommand) {
    if (menuCommand != null && !menuCommand.isEmpty()) {
      this.processingValueLabel
          .setText("Provide the value for \"" + menuCommand.replace("_", " ").toLowerCase() + "\"");
    } else {
      this.processingValueLabel.setVisible(false);
    }
  }

}
