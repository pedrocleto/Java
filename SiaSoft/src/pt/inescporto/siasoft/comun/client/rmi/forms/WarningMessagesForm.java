package pt.inescporto.siasoft.comun.client.rmi.forms;

import pt.inescporto.template.client.rmi.FW_InternalFrameBasic;
import java.awt.BorderLayout;
import java.util.ResourceBundle;

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
public class WarningMessagesForm extends FW_InternalFrameBasic {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  public boolean hasData = true;

  public WarningMessagesForm() {
    setLayout(new BorderLayout());
    WarningMessagesPanel wmPanel = new WarningMessagesPanel();
    hasData = wmPanel.hasData;
    add(new WarningMessagesPanel(), BorderLayout.CENTER);
    setTitle(res.getString("wm.form.title.label"));
  }
}
