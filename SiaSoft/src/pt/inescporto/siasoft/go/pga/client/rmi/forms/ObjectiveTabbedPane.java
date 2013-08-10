package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import java.util.ResourceBundle;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;

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
public class ObjectiveTabbedPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane jtpObjective = new JTabbedPane(JTabbedPane.BOTTOM);
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  public ObjectiveTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("obj.label.border")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    ObjectiveDefinitionPane odp = new ObjectiveDefinitionPane(datasource, fwCListener);
    jtpObjective.addTab(res.getString("label.definition"), odp);
    jtpObjective.addTab(res.getString("label.links"), new ObjectiveLinkPane(datasource, odp.getMasterListener()));
    jtpObjective.addTab(res.getString("obj.label.actions"), new ObjectiveActionsPane(datasource, odp.getMasterListener()));

    add(jtpObjective, BorderLayout.CENTER);
  }
}
