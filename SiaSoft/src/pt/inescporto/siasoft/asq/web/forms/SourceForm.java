package pt.inescporto.siasoft.asq.web.forms;

import java.awt.*;
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
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class SourceForm extends JInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  DataSourceRMI datasource = null;
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  FW_JTable fwSourceTable = new FW_JTable();
  FW_NavBarDetail navBar = new FW_NavBarDetail();

  public SourceForm() {
    datasource = new DataSourceRMI("Source");
    datasource.setUrl("pt.inescporto.siasoft.asq.ejb.session.Source");

    init();

    fwSourceTable.setPublisherEvent(SyncronizerSubjects.sourceFORM);
    fwSourceTable.setAccessPermitionIdentifier("SOURCE");
    fwSourceTable.start();
  }

  public void init() {

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    navBar.setFW_ComponentListener(fwSourceTable);
    navBar.setActionListener(fwSourceTable);
  }

  private void jbInit() throws Exception {
    fwSourceTable = new FW_JTable(datasource,
                                 new String[] {res.getString("source.label.code"), res.getString("source.label.desc")},
                                 new String[] {"sourceId", "sourceDescription"});

    setOpaque(false);
    setLayout(borderLayout1);
    add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(fwSourceTable);

    add(navBar, java.awt.BorderLayout.SOUTH);
  }
}
