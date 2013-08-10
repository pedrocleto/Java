package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Dimension;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.*;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class EAEvaluationSIAMethodDialog extends JDialog {
  DataSource dataSource=null;
  public EAEvaluationSIAMethodDialog(JFrame frame, TplString envAspID) {
    super(frame, true);

    setLayout(new BorderLayout());
    setResizable(true);

    EnvAspEvaluationSIAPanel panel = new EnvAspEvaluationSIAPanel(envAspID);
    add(panel, BorderLayout.CENTER);

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
