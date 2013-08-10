package pt.inescporto.siasoft;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import pt.inescporto.template.rmi.beans.LoggedUser;
import pt.inescporto.template.client.rmi.DialogLogin;
import javax.swing.UIManager;
import javax.swing.*;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class SIASoftClient {
  boolean packFrame = false;

  /**
   * Construct and show the application.
   */
  public SIASoftClient(LoggedUser usrInfo) {
    MenuFrame frame = new MenuFrame(usrInfo);
    // Validate frames that have preset sizes
    // Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }

    // Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setTitle("SIASoft");
    frame.setVisible(true);

    try {
      Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
//	  System.out.println("Application shutdown in progress ...");
        }
      });
    }
    catch (Exception ex) {
    }
  }

  /**
   * Application entry point.
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
    }
    catch (UnsupportedLookAndFeelException ex) {
    }

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {

        DialogLogin dlg = new DialogLogin(null, "Login", true, "RMI");
        dlg.setVisible(true);
        LoggedUser usrInfo = dlg.userInfo;
        if (usrInfo == null)
          System.exit(0);

	dlg = null;
	new SIASoftClient(usrInfo);
      }
    });
  }
}
