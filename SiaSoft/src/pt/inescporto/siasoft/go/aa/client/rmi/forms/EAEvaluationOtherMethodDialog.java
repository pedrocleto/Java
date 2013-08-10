package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.awt.Toolkit;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Dimension;
import pt.inescporto.template.elements.TplString;

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
public class EAEvaluationOtherMethodDialog extends JDialog {
  public EAEvaluationOtherMethodDialog(JFrame frame, TplString envAspID) {
    super(frame, true);

    setLayout(new BorderLayout());
    setResizable(true);

    EnvAspEvaluationOtherPanel panel = new EnvAspEvaluationOtherPanel(envAspID);
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
