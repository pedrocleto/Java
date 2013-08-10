package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.Toolkit;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.MenuSingleton;
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
public class MasqDialog extends JDialog {

  public MasqDialog(JFrame frame, String activityID) {
    super(frame, true);

    setUndecorated(true);
    setLayout(new BorderLayout());
    setResizable(true);

    MasqPanel masqPanel = new MasqPanel(activityID, MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise());

    add(masqPanel, BorderLayout.CENTER);

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
