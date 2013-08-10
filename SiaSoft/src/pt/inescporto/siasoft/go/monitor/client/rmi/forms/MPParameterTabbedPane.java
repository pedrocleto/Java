package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import javax.swing.JTabbedPane;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.FW_ComponentListener;
import java.awt.Font;
import pt.inescporto.template.client.util.*;

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
public class MPParameterTabbedPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane jtpMPParameter = new JTabbedPane(JTabbedPane.BOTTOM);
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  public MPParameterTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);
    setBorder(BorderFactory.createTitledBorder(res.getString("paramTabbedPane.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    ParametersDefinitionPane  paramdefp = new ParametersDefinitionPane (datasource, fwCListener);
    jtpMPParameter.addTab(res.getString("label.definition"), paramdefp);
    jtpMPParameter.addTab(res.getString("parametersChart.label.chart"),new ParametersChartPane(datasource, paramdefp.getMasterListener()));

    add(jtpMPParameter, BorderLayout.CENTER);
  }
}
