package images.view.component;

import images.PatternUtilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * DMC color renderer.
 * 
 * @author dileepshah
 *
 */
public class DMCColorListRenderer extends DefaultListCellRenderer {

  private final Map<String, Integer[]> dmcColors;
  /**
   * Generated serial.
   */
  private static final long serialVersionUID = 3745282625634014623L;

  /**
   * Default Constructor to initialize dmc colors.
   */
  public DMCColorListRenderer() {
    this.dmcColors = PatternUtilities.getDMCMap();
  }

  @Override
  public Component getListCellRendererComponent(JList<?> list, Object value, int index,
      boolean isSelected, boolean cellHasFocus) {
    Component component = super.getListCellRendererComponent(list, value, index, isSelected,
        cellHasFocus);
    String dmcKey = (String) value;
    if (dmcKey.contains("-")) {
      dmcKey = dmcKey.split("-")[1];
    }
    if (dmcColors.containsKey(dmcKey)) {

      Integer[] background = dmcColors.get(dmcKey.trim());
      setBackground(new Color(background[0], background[1], background[2]));
      return component;
    }
    this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return component;
  }

}
