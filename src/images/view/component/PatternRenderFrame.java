package images.view.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Frame to render the pattern. This frame pops up a frame on top of main frame
 * and shows generated text pattern.
 * 
 * @author dileepshah
 *
 */
public class PatternRenderFrame extends JFrame {

  /**
   * Generated serial.
   */
  private static final long serialVersionUID = -8172739645466582243L;
  private final JTextArea patternTextArea;

  /**
   * Create the frame.
   */
  public PatternRenderFrame() {
    setTitle("Generated Pattern");
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    setBounds(100, 100, 650, 450);
    JPanel contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setSize(new Dimension(300, 250));
    scrollPane.setAutoscrolls(true);
    scrollPane.setBorder(new TitledBorder("Generated Pattern"));

    JPanel panel = new JPanel();
    contentPane.add(panel, BorderLayout.CENTER);
    patternTextArea = new JTextArea();
    patternTextArea.setEditable(false);
    patternTextArea.setColumns(50);
    patternTextArea.setRows(20);
    scrollPane.setViewportView(patternTextArea);

    panel.add(scrollPane);
    getContentPane().add(panel);
  }

  /**
   * Set the Pattern text.
   * 
   * @param pattern the pattern text.
   */
  public void setPatternText(String pattern) {
    if (this.patternTextArea != null && pattern != null) {
      this.patternTextArea.setText(pattern);
      this.repaint();
    }
  }

}
