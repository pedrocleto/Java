package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.FlowLayout;
import javax.swing.JToolBar;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Rute
 * @version 1.0
 */

public class ToolBarGraph extends JToolBar{
  public ToolBarGraph(GraphicInterface wndGraphics) {
    super();
    FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
    setLayout(fl);
    setFloatable(false);
    setVisible(true);
  }
}
