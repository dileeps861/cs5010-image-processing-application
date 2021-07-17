package images.view.component;

import images.constants.EditingOptions;
import images.constants.ProcessingCommands;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

//import JOptionPane;

/**
 * Menu bar for image processing. Includes menus such as dither, transform,
 * filter, pattern generate, and pixelate image.
 * 
 * @author dileepshah
 *
 */
public class IPMenuBar extends JMenuBar {

  private static final long serialVersionUID = 5608414478624859484L;
  private final JMenu menuOptions;

  /**
   * Constructor to create IPMenuBar.
   */
  public IPMenuBar() {
    super();
    menuOptions = new JMenu("Edit Options");
    menuOptions.setMnemonic(KeyEvent.VK_1);
    menuOptions.setActionCommand(ProcessingCommands.EDIT.toString());
    menuOptions.getAccessibleContext().setAccessibleDescription("Image edit options");
    this.add(menuOptions);

    menuOptions.setCursor(new Cursor(Cursor.HAND_CURSOR));
    JMenuItem cbMenuItem;
    cbMenuItem = new JMenuItem("Blur");
    cbMenuItem.setActionCommand(EditingOptions.BLUR.toString());
    cbMenuItem.setMnemonic(KeyEvent.VK_B);
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    cbMenuItem = new JMenuItem("Sharpen");
    cbMenuItem.setActionCommand(EditingOptions.SHARPEN.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
    cbMenuItem.setMnemonic(KeyEvent.VK_S);
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    cbMenuItem = new JMenuItem("Grayscale");
    cbMenuItem.setActionCommand(EditingOptions.GRAYSCALE.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
    cbMenuItem.setMnemonic(KeyEvent.VK_R);
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    cbMenuItem = new JMenuItem("Sepia-tone");
    cbMenuItem.setActionCommand(EditingOptions.SEPIA_TONE.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
    cbMenuItem.setMnemonic(KeyEvent.VK_T);
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    cbMenuItem = new JMenuItem("Dither");
    cbMenuItem.setActionCommand(EditingOptions.DITHER.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
    cbMenuItem.setMnemonic(KeyEvent.VK_D);
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    cbMenuItem = new JMenuItem("Mosaic");
    cbMenuItem.setActionCommand(EditingOptions.MOSAIC.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setMnemonic(KeyEvent.VK_M);
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    cbMenuItem = new JMenuItem("Pixelate");
    cbMenuItem.setActionCommand(EditingOptions.PIXELATE.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setMnemonic(KeyEvent.VK_P);
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    cbMenuItem = new JMenuItem("Generate Pattern");
    cbMenuItem.setActionCommand(EditingOptions.GENERATE_PATTERN.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setMnemonic(KeyEvent.VK_G);
    cbMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
    cbMenuItem.addActionListener(this::menuActionInvoked);
    menuOptions.add(cbMenuItem);

    JMenu menuPattern = new JMenu("Custom Pattern Options");
    menuPattern.setMnemonic(KeyEvent.VK_2);
    menuPattern.setActionCommand(ProcessingCommands.PATTERN_MENU.toString());
    menuPattern.getAccessibleContext().setAccessibleDescription("Image edit options");
    this.add(menuPattern);

    cbMenuItem = new JMenuItem("Generate Pattern with Custom colors");
    cbMenuItem.setActionCommand(EditingOptions.PATTERN_WITH_CUSTOM_COLORS.toString());
    cbMenuItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
    cbMenuItem.setMnemonic(KeyEvent.VK_C);

    menuPattern.add(cbMenuItem);

    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.setToolTipText("Exit Menu Item");
    exitItem.setActionCommand(ProcessingCommands.EXIT.toString());
    exitItem.setCursor(new Cursor(Cursor.MOVE_CURSOR));
    exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0));
    exitItem.setMnemonic(KeyEvent.VK_E);
    JMenu exitMenu = new JMenu("Quit");
    exitMenu.add(exitItem);
    this.add(exitMenu);

  }

  private void menuActionInvoked(ActionEvent event) {

    this.menuOptions.setSelected(getFocusTraversalKeysEnabled());

    for (int menuIndex = 0; menuIndex < this.getMenuCount(); menuIndex++) {
      JMenu menuSelected = this.getMenu(menuIndex);
      for (int i = 0; i < menuSelected.getItemCount(); i++) {
        JMenuItem menuItem = menuSelected.getItem(i);
        if (menuItem.getActionCommand().equals(event.getActionCommand())) {
          menuItem.setForeground(Color.WHITE);
          menuItem.setBackground(Color.BLUE);
        } else {
          menuItem.setBackground(this.menuOptions.getBackground());
          menuItem.setForeground(Color.BLACK);
        }

      }
    }

  }

  /**
   * Method to add action listener to menu items.
   * 
   * @param actionListener action listener
   */
  public void addActionListener(ActionListener actionListener) {
    for (int i = 0; i < this.getMenuCount(); i++) {

      JMenu menuTemp = this.getMenu(i);
      menuTemp.addActionListener(actionListener);
      for (int j = 0; j < menuTemp.getItemCount(); j++) {
        JMenuItem menuItem = menuTemp.getItem(j);
        menuItem.addActionListener(actionListener);
      }
    }

  }
}
