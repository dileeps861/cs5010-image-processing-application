
package images.view.component;

import images.PatternUtilities;
import images.constants.ProcessingCommands;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;



/**
 * Panel to show processed Image. This panel handles the responsibility of
 * showing processed image, and being able to click on the image for color
 * selection.
 * 
 * @author dileepshah
 */
public class ProcessedImagePanel extends JPanel {
  private BufferedImage processedImage;
  private String toReplaceColor;
  /**
   * Generated serialVersionUID.
   */
  private static final long serialVersionUID = -3250648676522328457L;

  private JScrollPane processedImageSP;
  private final JButton removeColorButton;
  private final JLabel labelImage;

  /**
   * Creates new ProcessedImagePanel.
   */
  public ProcessedImagePanel() {
    this.removeColorButton = new JButton("Remove Selected Color");
    this.removeColorButton.setEnabled(false);
    this.removeColorButton.setActionCommand(ProcessingCommands.REMOVE_ONE_COLOR.toString());
    labelImage = new JLabel();
    initComponents();
  }

  private void initComponents() {

    this.labelImage.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        processedImageSPMouseClicked(e);
      }
    });

    processedImageSP = new JScrollPane();

    setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    setPreferredSize(new Dimension(510, 470));

    processedImageSP.setBorder(BorderFactory.createTitledBorder("Processed Image"));
    processedImageSP.setAutoscrolls(true);
    processedImageSP.setSize(new Dimension(500, 450));
    processedImageSP.setVisible(true);
    processedImageSP.setViewportView(labelImage);
    this.setVisible(false);

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    GroupLayout originalImagePanelLayout = new GroupLayout(this);
    this.setLayout(originalImagePanelLayout);
    originalImagePanelLayout.setHorizontalGroup(
        originalImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 372, Short.MAX_VALUE).addComponent(processedImageSP));
    originalImagePanelLayout.setVerticalGroup(
        originalImagePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE).addComponent(processedImageSP));

  }

  /**
   * Set the image to panel.
   * 
   * @param image the image
   */
  public void setImage(BufferedImage image) {
    this.toReplaceColor = null;
    if (image != null) {
      this.processedImage = image;
      this.removeColorButton.setEnabled(false);
      ImageIcon icon = new ImageIcon(image);
      labelImage.setIcon(icon);
      Dimension dimension = new Dimension(Math.min(500, image.getWidth()),
          Math.min(490, image.getHeight()));
      this.processedImageSP.setSize(dimension);
      this.setOpaque(false);
      this.setVisible(true);
      this.repaint();
      this.updateUI();
    } else {
      this.processedImage = null;
      this.setVisible(false);
    }

  }

  private void processedImageSPMouseClicked(MouseEvent e) {
    this.toReplaceColor = null;
    if (e.getSource() == this.labelImage) {
      if (processedImage != null && processedImage.getWidth() > e.getX()
          && processedImage.getHeight() > e.getY()) {
        final int pixel = processedImage.getRGB(e.getX(), e.getY());
        Color color = new Color(pixel);
        Integer[] rgb = new Integer[] { color.getRed(), color.getGreen(), color.getBlue() };

        this.toReplaceColor = PatternUtilities.findDMCCode(rgb);
        if (toReplaceColor == null) {
          JOptionPane.showMessageDialog(this,
              "This image in not pattern generated. Generate pattern to select color.");
          return;
        }

        JPanel colorSamplePanel = new JPanel();
        colorSamplePanel.setPreferredSize(new Dimension(60, 60));
        colorSamplePanel.setBackground(color);
        colorSamplePanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
        JPanel panel = new JPanel();
        JLabel colorLabel = new JLabel("Your Selected Color is: ");
        panel.add(colorLabel);
        panel.add(colorSamplePanel);
        JOptionPane.showMessageDialog(this, panel,
            "You are about to remove this selected color. After this pop-up, please click"
                + " \"Remove Selected Color\" button to remove the color.",
            JOptionPane.INFORMATION_MESSAGE);
        this.removeColorButton.setEnabled(true);
      }
    }
  }

  /**
   * Old color to be replaced from the image.
   * 
   * @return the rbg color value
   */
  public String getToReplaceColor() {
    return this.toReplaceColor;
  }

  /**
   * The Action listener to add in this panel's components.
   * 
   * @param listener the action listener
   */
  public void addActionListener(ActionListener listener) {
    this.removeColorButton.addActionListener(listener);
  }
}
