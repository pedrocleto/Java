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

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class ParametersTabbedPane extends JPanel {
  FW_ComponentListener fwCListener = null;
   DataSource datasource = null;
   JTabbedPane jtpParameters = new JTabbedPane(JTabbedPane.BOTTOM);
   static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

   public ParametersTabbedPane(DataSource datasource, FW_ComponentListener fwCListener) {
     this.datasource = datasource;
     this.fwCListener = fwCListener;
     initialize();

   }

   private void initialize() {
     setLayout(new BorderLayout());
     setOpaque(false);
     setBorder(BorderFactory.createTitledBorder(res.getString("paramTabbedPane.label.title")));
     ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

     ParametersTable paramdefp=new ParametersTable(datasource,fwCListener);
     jtpParameters.addTab(res.getString("label.definition"),paramdefp);
     jtpParameters.addTab(res.getString("parametersChart.label.chart"),new MonitorParametersChart(datasource, paramdefp.getMasterListener()));

     add(jtpParameters, BorderLayout.CENTER);

   }
}
