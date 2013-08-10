package pt.inescporto.template.client.rmi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import pt.inescporto.template.rmi.beans.LoggedUser;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class TmplMenuSupport
    extends JFrame {
  private LoggedUser usrInfo = null;
  private int shortcutType = 2;
  private JPanel contentPane;
  private JSplitPane jSplitPane1 = new JSplitPane();
  private MenuRMI jMenu = null;
  private BorderLayout borderLayout1 = new BorderLayout();
  TmplShortcutList jlstShortcut = null;
  TmplShortcutPanel jpnlShortcut = null;
  JScrollPane jscrShortcut = new JScrollPane();

  JImagePanel jpnInfoPanel = new JImagePanel();
  JPanel jpnCenter = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();

  //Construct the frame
  public TmplMenuSupport(LoggedUser usrInfo) {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    jMenu = new MenuRMI(usrInfo);
    this.usrInfo = usrInfo;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception {
    contentPane = (JPanel)this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setTitle("EUROShoE");
    contentPane.add(jSplitPane1, BorderLayout.CENTER);

    jSplitPane1.add(jMenu, JSplitPane.RIGHT);
    jSplitPane1.add(jscrShortcut, JSplitPane.LEFT);

    jpnCenter.setLayout(borderLayout2);
    jpnInfoPanel.setPreferredSize(new Dimension(25, 600));
    jpnCenter.add(jscrShortcut, BorderLayout.CENTER);
    jSplitPane1.add(jpnCenter, JSplitPane.LEFT);
    jpnCenter.add(jpnInfoPanel, BorderLayout.WEST);

    jSplitPane1.setDividerLocation(0.3D);

    switch (shortcutType) {
      case 1:
        jlstShortcut = new TmplShortcutList(usrInfo);
        jlstShortcut.setMenu(jMenu);
        jMenu.setShortcut(jlstShortcut);
        jscrShortcut.getViewport().add(jlstShortcut, null);
        break;
      case 2:
        jpnlShortcut = new TmplShortcutPanel(usrInfo);
        jpnlShortcut.setMenu(jMenu);
        jMenu.setShortcut(jpnlShortcut);
        jscrShortcut.getViewport().add(jpnlShortcut, null);
        break;
    }
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }
}

class JImagePanel
    extends JPanel {
  public void paint(Graphics g) {
    super.paint(g);
    Image Background = getToolkit().getImage(TmplMenuSupport.class.getResource(
        "logoMenu.jpg"));
    g.drawImage(Background, 25, 600, this);
  }

  public void paintComponent(Graphics g) {
    //Now draw the image scaled.
    Rectangle r = this.getBounds();
    int scaleWidth = r.width;
    int scaleHeight = r.height;
    String imagePath = "logoMenu.jpg";
    ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
    Image image = icon.getImage();
    g.drawImage(image, 0, 0, scaleWidth, scaleHeight, this);
  }
}