package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.Toolkit;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JFrame;
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
public class ActivityHasPartDialog extends JDialog {
  public ActivityHasPartDialog(JFrame frame, String activityID) {
    this(frame, activityID, null, null);
  }

  public ActivityHasPartDialog(JFrame frame, String activityID, String partClassID, String partID) {
    super(frame, true);

    setUndecorated(true);
    setLayout(new BorderLayout());
    setResizable(true);

    ActivityHasPartPane eaPanel = null;
    if (partClassID == null && partID == null)
      eaPanel = new ActivityHasPartPane(activityID);
    else
      eaPanel = new ActivityHasPartPane(activityID, partClassID, partID);

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
