
package images.view.component;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * The tab to handle image processing buttons. This is the panel for UI related
 * to showing and manipulating images. This is included as UI tab in the view.
 * 
 * @author dileepshah
 */
public class ImageProcessingTabs extends JPanel {

  /**
   * Generated serialVersionUID.
   */
  private static final long serialVersionUID = 414057172814823897L;

  private JTabbedPane imageProcessingTabs;

  /**
   * Creates new ImageProcessingTabs object.
   */
  public ImageProcessingTabs() {
    initComponents();
  }

  private void initComponents() {

    imageProcessingTabs = new JTabbedPane();

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(imageProcessingTabs,
            GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(imageProcessingTabs, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE));
  }

  /**
   * Method to add the panel to the tab.
   * 
   * @param panel panel component
   * @param title the title of the panel
   * @param index index position to add panel
   */
  public void addTab(JPanel panel, String title, int index) {
    if (panel != null && title != null) {
      panel.setAutoscrolls(true);
      imageProcessingTabs.insertTab(title, null, panel, title, index);

    } else {
      throw new IllegalArgumentException("Panel cannot be added to tab.");
    }

  }

}
