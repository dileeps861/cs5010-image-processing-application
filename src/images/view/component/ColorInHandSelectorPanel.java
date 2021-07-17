package images.view.component;

import images.PatternUtilities;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.event.ListSelectionEvent;




/**
 * Panel to pick colors in hand.
 * 
 * @author dileepshah
 */
public class ColorInHandSelectorPanel extends JPanel {

  /**
   * Generated Serial.
   */
  private static final long serialVersionUID = -4846936086493550179L;

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private final JList<String> availableColorsList;
  private final JList<String> selectedColorsList;
  private final Set<String> selectedColors;

  // End of variables declaration//GEN-END:variables

  /**
   * Creates new form ColorInHandSelector.
   */
  public ColorInHandSelectorPanel() {
    selectedColors = new LinkedHashSet<>();
    selectedColorsList = new JList<>();
    availableColorsList = new JList<>();
    initComponents();
  }

  private void initComponents() {

    JScrollPane availableColorsScrollPane = new JScrollPane();
    

    // Setting available colors list
    availableColorsScrollPane.setBorder(BorderFactory.createTitledBorder("Available Colors"));
    availableColorsList.setModel(new AbstractListModel<>() {
      /**
       * Serial version.
       */
      private static final long serialVersionUID = 1L;
      final String[] items = PatternUtilities.getDMCMap().keySet().toArray(String[]::new);

      public int getSize() {
        return items.length;
      }

      public String getElementAt(int i) {
        return items[i];
      }
    });
    availableColorsList.setCellRenderer(new DMCColorListRenderer());
    availableColorsList.addListSelectionListener(this::availableColorsListValueChanged);
    availableColorsScrollPane.setViewportView(availableColorsList);

    // Setting selected colors list.
    JScrollPane selectedColorsScrollPane = new JScrollPane();
    selectedColorsScrollPane.setBorder(BorderFactory.createTitledBorder("Selected Colors"));
    selectedColorsList.setModel(new AbstractListModel<>() {
      /**
       * Serial version.
       */
      private static final long serialVersionUID = 1L;
      final String[] items = selectedColors.toArray(String[]::new);

      public int getSize() {
        return items.length;
      }

      public String getElementAt(int i) {
        return items[i];
      }
    });
    selectedColorsList.addListSelectionListener(this::selectedColorsListValueChanged);
    selectedColorsList.setCellRenderer(new DMCColorListRenderer());
    selectedColorsScrollPane.setViewportView(selectedColorsList);

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
            .createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addPreferredGap(
                LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(availableColorsScrollPane, GroupLayout.PREFERRED_SIZE, 187,
                    GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedColorsScrollPane, GroupLayout.PREFERRED_SIZE, 143,
                    GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup().addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                .addComponent(selectedColorsScrollPane).addComponent(availableColorsScrollPane))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

  }

  private void availableColorsListValueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      this.selectedColors.add(this.availableColorsList.getSelectedValue());
      selectedColorsList.setListData(selectedColors.toArray(String[]::new));
      this.repaint();
    }

  }

  private void selectedColorsListValueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      if (selectedColors.contains(this.selectedColorsList.getSelectedValue())) {

        this.selectedColors.remove(this.selectedColorsList.getSelectedValue());
        selectedColorsList.setListData(selectedColors.toArray(String[]::new));
      }

      this.repaint();
    }

  }

  /**
   * Returns the selected dmc colors code.
   * 
   * @return the dmc color code set
   */
  public Set<String> getSelectedColorsForPattern() {
    return new LinkedHashSet<>(selectedColors);
  }

  /**
   * Clears the selection of the colors.
   */
  public void clearSelection() {
    this.selectedColors.clear();
    selectedColorsList.setListData(selectedColors.toArray(String[]::new));
  }

}
