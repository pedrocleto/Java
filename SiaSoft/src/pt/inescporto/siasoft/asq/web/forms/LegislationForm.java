package pt.inescporto.siasoft.asq.web.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class LegislationForm extends JInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  DataSourceRMI datasource = null;
  JPanel jpnlContent = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  FW_JTable fwLegislationTable = new FW_JTable();
  FW_NavBarDetail navBar = new FW_NavBarDetail();

  public LegislationForm() {
    super();

    datasource = new DataSourceRMI("Legislation");
    datasource.setUrl("pt.inescporto.siasoft.asq.ejb.session.Legislation");

    init();

    fwLegislationTable.setAccessPermitionIdentifier("LEGISLATION");
    fwLegislationTable.setPublisherEvent(SyncronizerSubjects.asqLEGISLATION);
    fwLegislationTable.start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    navBar.setFW_ComponentListener(fwLegislationTable);
    navBar.setActionListener(fwLegislationTable);
  }

  private void jbInit() throws Exception {
    fwLegislationTable = new FW_JTable(datasource,
                                       new String[] {res.getString("legisl.label.code"), res.getString("legisl.label.desc")},
                                       new String[] {"legislId", "legislDescription"});

    jpnlContent.setOpaque(false);
    jpnlContent.setLayout(borderLayout1);
    getContentPane().add(jpnlContent, BorderLayout.CENTER);
    jpnlContent.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(fwLegislationTable);

    jpnlContent.add(navBar, java.awt.BorderLayout.SOUTH);
  }
}
