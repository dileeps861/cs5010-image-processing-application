package images.view.component;

import images.constants.ProcessingCommands;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;



/**
 * Dialog box to open and give option to select color for replacement.
 *
 * @author dileepshah
 */
public class ReplaceColorSelectDialog extends JDialog {

  /**
   * Generated serial id.
   */
  private static final long serialVersionUID = -5262906545881569071L;
  private final JButton okButton;
  private final JList<String> availableColorsList;
  private final Set<String> dmcColors;
  private String selectedColor;

  /**
   * Create the dialog.
   */
  public ReplaceColorSelectDialog() {
    setBounds(100, 100, 450, 300);
    getContentPane().setLayout(new BorderLayout());
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new FlowLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    dmcColors = new LinkedHashSet<>();
    selectedColor = "";

    getContentPane().add(contentPanel, BorderLayout.CENTER);
    {
      JLabel replacingColorLabel = new JLabel("Select a new Color to replace");
      contentPanel.add(replacingColorLabel);
    }
    {
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      contentPanel.add(scrollPane);
      {
        availableColorsList = new JList<>();
        availableColorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        availableColorsList.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        scrollPane.setViewportView(availableColorsList);
        availableColorsList.setModel(new javax.swing.AbstractListModel<>() {
          /**
           * Serial version.
           */
          private static final long serialVersionUID = 1L;
          final String[] strings = dmcColors.toArray(String[]::new);

          public int getSize() {
            return strings.length;
          }

          public String getElementAt(int i) {
            return strings[i];
          }
        });
        availableColorsList.setCellRenderer(new DMCColorListRenderer());
        availableColorsList.setVisibleRowCount(10);
        scrollPane.setViewportView(availableColorsList);
        scrollPane.setPreferredSize(new Dimension(150, 200));
      }
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        okButton = new JButton("OK");
        okButton.setActionCommand(ProcessingCommands.OK_COLOR_SELECTION.toString());
        buttonPane.add(okButton);
        okButton.addActionListener(e -> okButtonActionPerformed());
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> cancelButtonActionPerformed());
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
      }
    }
    availableColorsList.addListSelectionListener(this::listValueChanged);
    
  }

  private void cancelButtonActionPerformed() {
    this.setVisible(false);
  }

  /**
   * Populate the colors.
   *
   * @param dmcColors the dmc colors
   */
  public void populateColorsList(Set<String> dmcColors) {
    if (dmcColors == null) {
      throw new IllegalArgumentException("DMC colors set cannot be null.");
    }
    this.dmcColors.clear();

    Set<String> legend = dmcColors.stream().map(
        value -> value.split("-").length > 1 ? value.split("-")[1].trim() : value)
        .collect(Collectors.toSet());
   
    this.dmcColors.addAll(legend);

    availableColorsList.setListData(this.dmcColors.toArray(String[]::new));
    this.repaint();
  }

  private void listValueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      this.selectedColor = this.availableColorsList.getSelectedValue();

    }

  }

  private void okButtonActionPerformed() {
    this.setVisible(false);
  }

  /**
   * Returns the selected dmc color code.
   *
   * @return the dmc color code
   */
  public String getSelectedValue() {
    return this.selectedColor;
  }

  /**
   * Method to add action listener to the components of color selection panel.
   *
   * @param actionListener the action listener.
   */
  public void addActionListener(ActionListener actionListener) {
    this.okButton.addActionListener(actionListener);
  }

}
