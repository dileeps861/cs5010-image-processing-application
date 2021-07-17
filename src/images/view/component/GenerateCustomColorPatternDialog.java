package images.view.component;

import images.constants.ProcessingCommands;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Dialog to pop-up custom color selector. User can select the in hand colors
 * which they want to use in the pattern.
 * 
 * @author dileepshah
 *
 */
public class GenerateCustomColorPatternDialog extends JDialog {

  /**
   * The generated serialVersionUID.
   */
  private static final long serialVersionUID = -5394520581356048298L;
  private final ColorInHandSelectorPanel selectorPanel;
  private final JButton okButton;

  private final JTextField numberOfChunksTF;

  /**
   * Create the dialog.
   */
  public GenerateCustomColorPatternDialog() {
    this.selectorPanel = new ColorInHandSelectorPanel();
    GroupLayout groupLayout = (GroupLayout) selectorPanel.getLayout();
    groupLayout.setAutoCreateGaps(true);
    groupLayout.setAutoCreateContainerGaps(true);
    okButton = new JButton("Generate Pattern");
    okButton.setActionCommand(ProcessingCommands.OK_CUSTOM_COLORS_SELECTION.toString());
    this.setVisible(false);
    setBounds(100, 100, 450, 350);
    getContentPane().setLayout(new BorderLayout());
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new FlowLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    getContentPane().add(contentPanel, BorderLayout.CENTER);
    {

      JPanel panel = new JPanel();
      panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
      FlowLayout flowLayout = (FlowLayout) panel.getLayout();
      flowLayout.setAlignment(FlowLayout.LEFT);
      panel.add(selectorPanel);

      JLabel numberOfChunksLabel = new JLabel();
      numberOfChunksLabel.setText("Enter the number of Square:");

      numberOfChunksTF = new JTextField();
      numberOfChunksTF.setText("");

      JPanel enterValuePanel = new JPanel();
      GroupLayout enterValuePanelLayout = new GroupLayout(enterValuePanel);
      enterValuePanel.setLayout(enterValuePanelLayout);
      enterValuePanelLayout.setHorizontalGroup(enterValuePanelLayout
          .createParallelGroup(GroupLayout.Alignment.CENTER)
          .addGroup(enterValuePanelLayout.createSequentialGroup().addGap(62, 62, 62)
              .addComponent(numberOfChunksLabel).addGap(18, 18, 18).addComponent(numberOfChunksTF,
                  GroupLayout.DEFAULT_SIZE, 217, GroupLayout.DEFAULT_SIZE)
              .addContainerGap(73, Short.MAX_VALUE)));
      enterValuePanelLayout.setVerticalGroup(enterValuePanelLayout
          .createParallelGroup(GroupLayout.Alignment.CENTER)
          .addGroup(enterValuePanelLayout.createSequentialGroup().addContainerGap()
              .addGroup(enterValuePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                  .addComponent(numberOfChunksLabel)
                  .addComponent(numberOfChunksTF, GroupLayout.PREFERRED_SIZE,
                      GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
              .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

      JPanel jPanel4 = new JPanel();
      GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
      jPanel4.setLayout(jPanel4Layout);
      jPanel4Layout.setHorizontalGroup(
          jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
              GroupLayout.Alignment.TRAILING,
              jPanel4Layout.createSequentialGroup().addContainerGap().addContainerGap(20, 50)
                  .addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(selectorPanel, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                      .addComponent(enterValuePanel, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addContainerGap()));
      jPanel4Layout
          .setVerticalGroup(
              jPanel4Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                  .addGroup(jPanel4Layout.createSequentialGroup()
                      .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                      .addComponent(selectorPanel, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                      .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                      .addComponent(enterValuePanel, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                      .addGap(0, 72, Short.MAX_VALUE)));

      contentPanel.add(jPanel4);

    }
    {
      JPanel buttonPane = new JPanel();
      FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
      fl_buttonPane.setAlignOnBaseline(true);
      buttonPane.setLayout(fl_buttonPane);
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {

        okButton.addActionListener(e -> closePopup());
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(e -> {
          closePopup();
          clearSelection();
        });
        buttonPane.add(cancelButton);
      }
    }
  }

  private void closePopup() {
    this.setVisible(false);
  }

  /**
   * Add action listener.
   * 
   * @param actionListener the action listener
   */
  public void addActionListener(ActionListener actionListener) {
    if (actionListener != null) {
      this.okButton.addActionListener(actionListener);
    }

  }

  /**
   * Returns the selected dmc colors code.
   * 
   * @return the dmc color code set
   */
  public Set<String> getSelectColorsForPattern() {
    return this.selectorPanel.getSelectedColorsForPattern();
  }

  /**
   * Clears the selection of the colors.
   */
  public void clearSelection() {
    this.selectorPanel.clearSelection();
  }

  /**
   * Returns the number of chunks for pattern with user specific color generation.
   * 
   * @return the number of chunks
   */
  public String getNumberOfChunksForPattern() {
    if (numberOfChunksTF != null) {
      return numberOfChunksTF.getText();
    }
    return "";
  }
}
