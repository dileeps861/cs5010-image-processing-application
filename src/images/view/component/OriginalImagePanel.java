
package images.view.component;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Image panel for Original image rendering. This class is responsible for
 * showing original image to the view.
 * 
 * @author dileepshah
 */
public class OriginalImagePanel extends JPanel {

  /**
   * Generated serialVersionUID.
   */
  private static final long serialVersionUID = -9121992337882639885L;
  private final JScrollPane originalImageSP;
  private final JLabel labelImage;

  /**
   * Creates new OriginalImagePanel.
   */
  public OriginalImagePanel() {
    labelImage = new JLabel();
    originalImageSP = new JScrollPane();
    initComponents();
  }

  private void initComponents() {

    setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    setPreferredSize(new Dimension(510, 490));

    originalImageSP.setBorder(BorderFactory.createTitledBorder("Original Image"));
    originalImageSP.setAutoscrolls(true);
    originalImageSP.setSize(new Dimension(520, 500));
    originalImageSP.setVisible(true);
    originalImageSP.setViewportView(labelImage);
    this.setVisible(false);

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    GroupLayout originalImagePanelLayout = new GroupLayout(this);
    this.setLayout(originalImagePanelLayout);
    originalImagePanelLayout.setHorizontalGroup(
        originalImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 372, Short.MAX_VALUE).addComponent(originalImageSP));
    originalImagePanelLayout.setVerticalGroup(
        originalImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE).addComponent(originalImageSP));
  }

  /**
   * Set the image to panel.
   * 
   * @param image the image
   */
  public void setImage(BufferedImage image) {
    if (image == null) {
      return;
    }
    ImageIcon icon = new ImageIcon(image);
    labelImage.setIcon(icon);

    Dimension dimension = new Dimension(Math.min(500, image.getWidth()),
        Math.min(480, image.getHeight()));
    this.originalImageSP.setSize(dimension);
    this.setOpaque(false);
    this.setVisible(true);
    this.repaint();
    this.updateUI();
  }

}
