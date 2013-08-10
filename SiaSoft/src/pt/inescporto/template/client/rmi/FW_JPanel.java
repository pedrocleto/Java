package pt.inescporto.template.client.rmi;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_NavBar;

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
public class FW_JPanel extends FW_JPanelNav {
  BorderLayout borderLayout1 = new BorderLayout();
  protected FW_NavBar fwNavBar = new FW_NavBar();

  public FW_JPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    // register this template component listeners
    fwNavBar.setFW_ComponentListener(this);
  }

  public void setDataSource(DataSource ds) {
    super.setDataSource(ds);

    fwNavBar.setDatasourceListener(ds);
  }

  public void start() {
    super.start();
  }

  private void jbInit() throws Exception {
    setLayout(borderLayout1);
    add(fwNavBar, BorderLayout.NORTH);
    fwNavBar.setActionListener(this);
  }
}
