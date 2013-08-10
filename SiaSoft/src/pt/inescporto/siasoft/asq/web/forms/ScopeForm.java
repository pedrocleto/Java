package pt.inescporto.siasoft.asq.web.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;

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
public class ScopeForm extends JInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  DataSourceRMI datasource = null;
  JPanel jpnlTools = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  FW_JTable fwScopeTable = new FW_JTable();
  FW_NavBarDetail navBar = new FW_NavBarDetail();

  public ScopeForm() {
    super();

    datasource = new DataSourceRMI("Scope");
    datasource.setUrl("pt.inescporto.siasoft.asq.ejb.session.Scope");
//    setPermFormId("SCOPE");

    init();

    fwScopeTable.setAccessPermitionIdentifier("SCOPE");
    fwScopeTable.setPublisherEvent(SyncronizerSubjects.scopeFORM);
    fwScopeTable.start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    navBar.setFW_ComponentListener(fwScopeTable);
    navBar.setActionListener(fwScopeTable);
  }

  private void jbInit() throws Exception {
    fwScopeTable = new FW_JTable(datasource,
                                 new String[] {res.getString("scope.label.code"), res.getString("scope.label.desc")},
                                 new String[] {"scopeId", "scopeDescription"});

    setOpaque(false);
    setLayout(borderLayout1);
    add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(fwScopeTable);

    add(navBar, java.awt.BorderLayout.SOUTH);
  }
}
