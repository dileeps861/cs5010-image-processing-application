
package images.view.component;

import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Tab to create batch file and run to generate batch image processing.
 * 
 * @author dileepshah
 */
public class BatchFileTab extends JPanel {

  /**
   * Generated serialVersionUID.
   */
  private static final long serialVersionUID = -6252644258227480904L;
  private JTextArea batchFileSpecifyTextBox;
  private JButton runBatchFileButton;

  /**
   * Creates new BatchFileTab.
   */
  public BatchFileTab() {
    initComponents();
  }

  private void initComponents() {

    runBatchFileButton = new JButton();

    batchFileSpecifyTextBox = new JTextArea();

    setBorder(BorderFactory.createTitledBorder(""));

    runBatchFileButton.setText("Run the Batch");
    runBatchFileButton.setActionCommand("BATCH_RUN");

    JScrollPane scrollPaneSampleTextFile = new JScrollPane();
    scrollPaneSampleTextFile.setBorder(BorderFactory.createTitledBorder("Sample Text File"));

    JTextArea sampleBatchTextBox = new JTextArea();
    sampleBatchTextBox.setEditable(false);
    sampleBatchTextBox.setColumns(20);
    sampleBatchTextBox.setRows(5);
    StringBuilder sampleText = new StringBuilder();
    sampleText.append("load salad.jpg\n").append("mosaic 1650\n")
        .append("save salad-mosaic-1650.jpg\n").append("load salad.jpg\n").append("dither 2\n")
        .append("save salad-dither-2.jpg\n").append("load salad.jpg\n").append("blur\n")
        .append("save salad-blur-file-1.jpg\n").append("load salad.jpg\n").append("sharpen\n")
        .append("save salad-sharpen-file-1.jpg\n").append("load salad.jpg\n").append("sepia-tone\n")
        .append("save salad-sepia-tone-file-1.jpg\n").append("load salad.jpg\n")
        .append("grayscale\n").append("save salad-grayscale-file-1.jpg\n")
        .append("load salad.jpg\n").append("pixelate 50\n").append("pattern\n")
        .append("save salad-pattern-50-file-1.txt\n").append("load salad.jpg\n")
        .append("pixelate 50\n").append("save salad-pixelate-50-file-1.jpg\n")
        .append("load flower.jpeg\n").append("mosaic 1650\n")
        .append("save flower-mosaic-1650-file-1.jpeg\n").append("load flower.jpeg\n")
        .append("dither 2\n").append("save flower-dither-2-file-1.jpeg\n")
        .append("load flower.jpeg\n").append("blur\n").append("save flower-blur-file-1.jpeg\n")
        .append("load flower.jpeg\n").append("sharpen\n")
        .append("save flower-sharpen-file-1.jpeg\n").append("load flower.jpeg\n")
        .append("sepia-tone\n").append("save flower-sepia-tone-file-1.jpeg\n")
        .append("load flower.jpeg\n").append("grayscale\n")
        .append("save flower-grayscale-file-1.jpeg\n").append("load flower.jpeg\n")
        .append("pixelate 50\n").append("pattern\n").append("save flower-pattern-50-file-1.txt\n")
        .append("load flower.jpeg\n").append("pixelate 50\n")
        .append("save flower-pixelate-50-file-1.jpeg\n");
    sampleBatchTextBox.setText(sampleText.toString());
    sampleBatchTextBox.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    scrollPaneSampleTextFile.setViewportView(sampleBatchTextBox);

    JScrollPane batchFileSpecifyScrollPane = new JScrollPane();
    batchFileSpecifyScrollPane.setBorder(BorderFactory.createTitledBorder("Specify Batch file"));

    batchFileSpecifyTextBox.setColumns(20);
    batchFileSpecifyTextBox.setRows(5);

    batchFileSpecifyScrollPane.setViewportView(batchFileSpecifyTextBox);

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
        GroupLayout.Alignment.TRAILING,
        layout.createSequentialGroup().addContainerGap()
            .addComponent(batchFileSpecifyScrollPane, GroupLayout.PREFERRED_SIZE, 460,
                GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(scrollPaneSampleTextFile, GroupLayout.PREFERRED_SIZE, 330,
                    GroupLayout.PREFERRED_SIZE)
                .addComponent(runBatchFileButton))
            .addContainerGap()));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup().addGap(17, 17, 17)
            .addComponent(scrollPaneSampleTextFile, GroupLayout.PREFERRED_SIZE, 371,
                GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18).addComponent(runBatchFileButton)
            .addContainerGap(103, Short.MAX_VALUE))
        .addGroup(layout.createSequentialGroup().addContainerGap()
            .addComponent(batchFileSpecifyScrollPane).addGap(55, 55, 55)));
  }

  /**
   * Returns the batch text specified by user.
   * 
   * @return the batch text
   */
  public String getBatchFile() {
    if (this.batchFileSpecifyTextBox != null) {
      return this.batchFileSpecifyTextBox.getText();
    } else {
      return "";
    }
  }

  /**
   * Add the action listener to components.
   * 
   * @param actionListener the action listener
   */
  public void addActionListener(ActionListener actionListener) {
    runBatchFileButton.addActionListener(actionListener);
  }

}
