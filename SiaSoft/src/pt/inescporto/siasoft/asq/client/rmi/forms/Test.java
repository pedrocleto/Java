package pt.inescporto.siasoft.asq.client.rmi.forms;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.TmplBasicInternalFrame;

public class Test extends TmplBasicInternalFrame {
  private RegulatoryPanel regulatoryPanel = null;

  public Test() {
    initialize();
    setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
  }

  private void initialize() {
    try {
      setLayout(new BorderLayout());
      add(getRegulatoryPanel(), java.awt.BorderLayout.CENTER);
    }
    catch (java.lang.Throwable e) {
      //  Do Something
    }
  }

  /**
   * This method initializes regulatoryPanel
   *
   * @return pt.inescporto.siasoft.asq.client.rmi.forms.RegulatoryPanel
   */
  private RegulatoryPanel getRegulatoryPanel() {
    if (regulatoryPanel == null) {
      try {
	regulatoryPanel = new RegulatoryPanel();
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return regulatoryPanel;
  }
} //  @jve:decl-index=0:visual-constraint="10,10"
