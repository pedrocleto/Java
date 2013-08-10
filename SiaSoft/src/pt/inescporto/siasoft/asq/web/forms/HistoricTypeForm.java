package pt.inescporto.siasoft.asq.web.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import pt.inescporto.template.client.design.TmplJButtonDelete;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.design.TmplJButtonInsert;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.FW_JTable;
import javax.swing.JInternalFrame;
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
 * @author not attributable
 * @version 0.1
 */
public class HistoricTypeForm extends JInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  DataSourceRMI datasource = null;
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  FW_JTable fwHistoricTypeTable = new FW_JTable();
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public HistoricTypeForm() {
    super();

    datasource = new DataSourceRMI("HistoricType");
    datasource.setUrl("pt.inescporto.siasoft.asq.ejb.session.HistoricType");

    init();

    fwHistoricTypeTable.setPublisherEvent(SyncronizerSubjects.historicType);
    fwHistoricTypeTable.setAccessPermitionIdentifier("HISTORICTYPE");
    fwHistoricTypeTable.start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    navBarDetail.setFW_ComponentListener(fwHistoricTypeTable);
    navBarDetail.setActionListener(fwHistoricTypeTable);
  }

  private void jbInit() throws Exception {
    fwHistoricTypeTable = new FW_JTable(datasource,
                                 new String[] {res.getString("historicType.label.code"), res.getString("historicType.label.desc")},
                                 new String[] {"historicTypeID", "historicTypeDescription"});

    setOpaque(false);
    setLayout(borderLayout1);
    add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(fwHistoricTypeTable);

    add(navBarDetail, java.awt.BorderLayout.SOUTH);
  }
}
