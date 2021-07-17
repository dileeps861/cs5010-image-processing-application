
package images.view.component;

import images.constants.ProcessingCommands;

import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;



/**
 * The Panel to contain coloring features. This class handles the responsibility
 * of showing pattern legend, color remove and replace from the pattern.
 * 
 * @author dileepshah
 */
public class PatternPropertiesPanel extends JPanel {
  /**
   * Generated serialVersionUID.
   */
  private static final long serialVersionUID = 1233690660352555702L;

  private final JButton removeColorButton;
  private final JButton replaceColorButton;

  private final JList<String> legendList;
  private final Set<String> legendSet;
  private final JScrollPane legendListScrollPane;

  /**
   * Creates new PatternPropertiesPanel object.
   */
  public PatternPropertiesPanel() {
    this.legendSet = new LinkedHashSet<>();
    this.legendList = new JList<>();

    legendListScrollPane = new JScrollPane();

    this.setBorder(BorderFactory.createTitledBorder("Pattern Properties and Manipulation"));
    
    // Setting legend list
    legendListScrollPane.setBorder(BorderFactory.createTitledBorder("Pattern legend"));
    legendList.setModel(new AbstractListModel<>() {
      /**
       * Serial version.
       */
      private static final long serialVersionUID = 1L;
      final String[] items = getLegendAsStringArray();

      public int getSize() {
        return items.length;
      }

      public String getElementAt(int i) {
        return items[i];
      }
    });
    legendList.setCellRenderer(new DMCColorListRenderer());
    legendListScrollPane.setViewportView(legendList);

    this.removeColorButton = new JButton("Remove Selected color");
    this.removeColorButton.setToolTipText("Remove the selected color from the image.");
    this.removeColorButton.setActionCommand(ProcessingCommands.REMOVE_ONE_COLOR.toString());
    this.removeColorButton.setEnabled(false);

    this.replaceColorButton = new JButton("Replace Selected color");
    this.replaceColorButton
        .setToolTipText("Replace the selected color with new color from the image.");
    this.replaceColorButton.setActionCommand(ProcessingCommands.REPLACE_ONE_COLOR.toString());
    this.replaceColorButton.setEnabled(false);
    initComponents();
  }

  private void initComponents() {


    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup().addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)

                .addGroup(layout.createSequentialGroup()
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)

                    .addComponent(removeColorButton, GroupLayout.PREFERRED_SIZE, 200,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(replaceColorButton, GroupLayout.PREFERRED_SIZE, 200,
                        GroupLayout.PREFERRED_SIZE)
                    .addComponent(legendListScrollPane, GroupLayout.DEFAULT_SIZE, 300,
                        GroupLayout.PREFERRED_SIZE)

                ))));
    layout
        .setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)

                        .addComponent(removeColorButton).addComponent(replaceColorButton)
                        .addComponent(legendListScrollPane))
                    .addContainerGap(13, Short.MAX_VALUE)));
  }


  /**
   * Method to add action listener.
   * 
   * @param actionListener the actionListener
   */
  public void addActionListener(ActionListener actionListener) {
    if (actionListener != null) {
      this.removeColorButton.addActionListener(actionListener);
      this.replaceColorButton.addActionListener(actionListener);
    }
  }

  /**
   * Set toggle remove color button.
   * 
   * @param isEnable is to be enabled.
   */
  public void toggleRemoveColorButton(boolean isEnable) {
    this.removeColorButton.setEnabled(isEnable);
  }

  /**
   * Set toggle replace color button.
   * 
   * @param isEnable is to be enabled.
   */
  public void toggleReplaceColorButton(boolean isEnable) {
    this.replaceColorButton.setEnabled(isEnable);
  }

  /**
   * Populate the colors.
   *
   * @param legendMap the legendMap
   */
  public void populatePatternLegendList(Set<String> legendMap) {
    if (legendMap == null) {
      throw new IllegalArgumentException("Legend map cannot be null.");
    }
    this.legendSet.clear();

    this.legendSet.addAll(legendMap);
    legendList.setListData(getLegendAsStringArray());
    this.repaint();
  }

  private String[] getLegendAsStringArray() {

    return legendSet.toArray(String[]::new);
  }

}
