package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author not attributable
 * @version 0.1
 */
public class EnvironmentalAspectDialog extends JDialog {
  public EnvironmentalAspectDialog(JFrame frame, String activityID) {
    super(frame, true);

    setUndecorated(true);
    setLayout(new BorderLayout());
    setResizable(true);

    EnvironmentalAspectPane eaPanel = new EnvironmentalAspectPane(activityID);

    add(eaPanel, BorderLayout.CENTER);

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getPreferredSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }
}
