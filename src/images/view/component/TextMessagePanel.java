
package images.view.component;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Pane to show text message in the UI for the user help.
 * 
 * @author dileepshah
 */
public class TextMessagePanel extends JPanel {

  /**
   * Generated serialVersionUID.
   */
  private static final long serialVersionUID = -374142004505630694L;

  private JTextArea textMessageArea;

  /**
   * Creates new TextMessagePanel.
   */
  public TextMessagePanel() {
    initComponents();
  }

  private void initComponents() {

    textMessageArea = new JTextArea();
    textMessageArea.setEditable(false);
    textMessageArea.setColumns(20);
    textMessageArea.setRows(5);
    textMessageArea.setText("is this panel");

    JScrollPane textMessageScrollPane = new JScrollPane();
    textMessageScrollPane.setViewportView(textMessageArea);

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout
            .createSequentialGroup().addGap(20, 20, 20).addComponent(textMessageScrollPane,
                GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(24, Short.MAX_VALUE)));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout
            .createSequentialGroup().addGap(28, 28, 28).addComponent(textMessageScrollPane,
                GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(34, Short.MAX_VALUE)));
  }

  /**
   * Adds the message to show in the popup.
   * 
   * @param message the message to show
   */
  public void addMessage(String message) {
    if (message != null && textMessageArea != null) {
      textMessageArea.setText(message);
      textMessageArea.setBackground(Color.RED);
      textMessageArea.setForeground(Color.WHITE);
      this.updateUI();
    }
  }

}
