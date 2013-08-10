package pt.inescporto.siasoft.go.coe.client.rmi.forms;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import java.util.ResourceBundle;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationDao;
import pt.inescporto.template.client.util.*;

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
public class EmergencySituationScenarios extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  JTabbedPane jtpScenarios = new JTabbedPane();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.coe.client.rmi.forms.FormResources");

  public EmergencySituationScenarios(DataSource datasource, FW_ComponentListener fwCListener,JTabbedPane jtpScenarios) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.jtpScenarios=jtpScenarios;
    initialize();

  }

  private void initialize() {
   setLayout(new BorderLayout());
   setOpaque(false);
   setBorder(BorderFactory.createTitledBorder(res.getString("emergSitscen.label.emergScenarios")));
   ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));
   EmergencySituationDao dt = null;
   try {
     dt = (EmergencySituationDao)datasource.getCurrentRecord();
   }
   catch (DataSourceException ex) {
   }
   EmergSitScenariosDefPane essdp = new EmergSitScenariosDefPane(datasource, fwCListener);
   jtpScenarios.addTab(res.getString("emergSituation.label.def"), essdp);
   jtpScenarios.addTab(res.getString("emergSitscen.label.emergplan"), new EmergSitScenariosDocPane(datasource, essdp.getMasterListener()));
   jtpScenarios.addTab(res.getString("emergSitscen.label.courses"), new EmergSitScenariosCoursePane(datasource, essdp.getMasterListener()));
  try{
    if (dt.typeEmerg.getValue().equals("E")) {
      jtpScenarios.setEnabledAt(1, true);
    }
    else {
      jtpScenarios.setEnabledAt(1, false);
    }
  }
  catch(Exception ex){

  }
   add(jtpScenarios, BorderLayout.CENTER);

 }

}
