package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.Toolkit;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.Dimension;

import org.jgraph.graph.DefaultGraphCell;
import pt.inescporto.siasoft.proc.client.rmi.editor.Graph;

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
public class ActivityNameDialog extends JDialog {
  public ActivityNameDialog(JFrame frame, DefaultGraphCell cell, Graph graph) {
    super(frame, true);

    setUndecorated(true);
    setLayout(new BorderLayout());
    setResizable(true);

    ActivityNamePane acPanel = new ActivityNamePane(cell, graph);
    add(acPanel, BorderLayout.CENTER);

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
