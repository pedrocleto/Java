package pt.inescporto.siasoft.proc.client.rmi.forms;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import pt.inescporto.siasoft.proc.client.rmi.editor.Graph;
import java.awt.Dimension;
import org.jgraph.graph.DefaultGraphCell;
import pt.inescporto.siasoft.proc.client.rmi.editor.ProcessEditor;

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
public class InterLevelActivityDialog  extends JDialog {
  public InterLevelActivityDialog(JFrame frame, DefaultGraphCell cell, Graph graph, ProcessEditor pEditor) {
    super(frame, true);

    setUndecorated(true);
    setLayout(new BorderLayout());
    setResizable(true);

   InterLevelActivityPane interLevelActivityPane = new InterLevelActivityPane(cell, graph, pEditor);
   add(interLevelActivityPane, BorderLayout.CENTER);

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
