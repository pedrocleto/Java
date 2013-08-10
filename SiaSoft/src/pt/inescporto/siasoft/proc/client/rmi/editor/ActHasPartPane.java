package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
class ActHasPartPane extends JPanel {
  private ActivityHasPartTree tree = null;

  public ActHasPartPane(){
  }

  public ActHasPartPane(ActivityHasPartTree tree) {
    this.tree = tree;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    JScrollPane jsp = new JScrollPane(tree);
    add(jsp, BorderLayout.CENTER);
  }

  public ActivityHasPartTree getTree() {
    return tree;
  }

  public void setTree(ActivityHasPartTree tree) {
    this.tree = tree;
  }
}
