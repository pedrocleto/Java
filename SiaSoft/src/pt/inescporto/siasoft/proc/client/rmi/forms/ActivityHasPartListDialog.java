package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
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

public class ActivityHasPartListDialog extends JDialog {
  public ActivityHasPartListDialog(JFrame frame, String activityID) {
    super(frame, true);

    setUndecorated(true);
    setLayout(new BorderLayout());
    setResizable(true);

    ActivityHasPartListPane aplPanel = new ActivityHasPartListPane(activityID);

    add(aplPanel, BorderLayout.CENTER);

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
