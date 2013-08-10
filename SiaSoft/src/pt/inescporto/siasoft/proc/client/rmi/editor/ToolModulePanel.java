package pt.inescporto.siasoft.proc.client.rmi.editor;

import java.awt.*;
import javax.swing.*;

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
public class ToolModulePanel extends JPanel{

  private ModuleListener mListener = null;
  private ToolBarModule toolBarModule = null;

  public ToolModulePanel(ModuleListener mListener) {
    this.mListener = mListener;
    setLayout(new BorderLayout());
    toolBarModule = new ToolBarModule(mListener);
    JScrollPane jspModule = new JScrollPane(toolBarModule);
    add(jspModule, BorderLayout.CENTER);
  }

  public ToolBarModule getToolBarModule() {
    return toolBarModule;
  }
}
