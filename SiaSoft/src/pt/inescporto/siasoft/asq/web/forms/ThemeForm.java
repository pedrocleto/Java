package pt.inescporto.siasoft.asq.web.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplIntegerRenderer;
import pt.inescporto.template.client.design.table.TmplIntegerEditor;
import java.awt.GridBagLayout;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
public class ThemeForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  DataSourceRMI detail = null;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  TmplJLabel jlblCode = new TmplJLabel();
  TmplJTextField jtfldCode = new TmplJTextField();

  TmplJLabel jlblDescription = new TmplJLabel();
  TmplJTextField jtfldDescription = new TmplJTextField();

  TmplJLabel jlblIndexOrder = new TmplJLabel();
  TmplJTextField jtfldIndexOrder = new TmplJTextField();

  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout3 = new BorderLayout();
  FW_JTable fwDetailTable = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public ThemeForm() {
    super();

    // define the datasource tree
    DataSourceRMI master = new DataSourceRMI("Theme");
    master.setUrl("pt.inescporto.siasoft.asq.ejb.session.Theme");

    detail = new DataSourceRMI("Theme1");
    detail.setUrl("pt.inescporto.siasoft.asq.ejb.session.Theme1");

    DSRelation mdRelation = new DSRelation("MasterDetail");
    mdRelation.setMaster(master);
    mdRelation.setDetail(detail);
    mdRelation.addKey(new RelationKey("themeID", "themeId"));
    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    // register the master datasource the this form
    setDataSource(master);

    // set the id from permission control
    setAccessPermitionIdentifier("THEME");

   //Syncronizer Subjects
    setPublisherEvent(SyncronizerSubjects.themeFORM);

    // headers for the table headers
    allHeaders = new String[] {res.getString("theme.label.code"), res.getString("theme.label.desc"), "Ordem"};

    init();
    start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    dataSource.addDatasourceListener(jtfldCode);
    dataSource.addDatasourceListener(jtfldDescription);
    dataSource.addDatasourceListener(jtfldIndexOrder);

    addFWComponentListener(jtfldCode);
    addFWComponentListener(jtfldDescription);
    addFWComponentListener(jtfldIndexOrder);
    addFWComponentListener(fwDetailTable);

    //table listener registration
    navBarDetail.setFW_ComponentListener(fwDetailTable);
    navBarDetail.setActionListener(fwDetailTable);
  }

  private void jbInit() throws Exception {
    jlblCode.setText(res.getString("theme.label.code"));
    jtfldCode.setField("themeId");
    jtfldCode.setHolder(jlblCode);
    jlblDescription.setText(res.getString("theme.label.desc"));
    jtfldDescription.setField("description");
    jtfldDescription.setHolder(jlblDescription);
    jlblIndexOrder.setText(res.getString("theme.label.order"));
    jtfldIndexOrder.setField("orderIndex");
    jtfldIndexOrder.setHolder(jlblIndexOrder);

    FW_ColumnManager colManager = new FW_ColumnManager();
    FW_ColumnNode node1 = new FW_ColumnNode(res.getString("theme.label.code"), "theme1Id", new TmplStringRenderer(), new TmplStringEditor());
    FW_ColumnNode node2 = new FW_ColumnNode(res.getString("theme.label.desc"), "description", new TmplStringRenderer(), new TmplStringEditor());
    FW_ColumnNode node3 = new FW_ColumnNode(res.getString("theme.label.order"), "orderIndex", new TmplIntegerRenderer(), new TmplIntegerEditor());
    colManager.addColumnNode(node1);
    colManager.addColumnNode(node2);
    colManager.addColumnNode(node3);

    fwDetailTable = new FW_JTable(detail, null, colManager);
    fwDetailTable.setAsMaster(false);
    fwDetailTable.setPublisherEvent(SyncronizerSubjects.theme1FORM);
    fwDetailTable.setAccessPermitionIdentifier("THEME1");

    jPanel3.setLayout(borderLayout3);
    add(jPanel1, java.awt.BorderLayout.CENTER);
    jPanel2.setLayout(gridBagLayout1);
    jPanel2.setOpaque(false);
    jPanel1.setLayout(borderLayout1);
    jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);
    jPanel1.add(navBarDetail, java.awt.BorderLayout.SOUTH);
    jPanel2.add(jlblCode, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
    jPanel2.add(jtfldCode, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 100, 0));
    jPanel2.add(jlblDescription, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
    jPanel2.add(jtfldDescription, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
	, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 300, 0));
    jPanel2.add(jlblIndexOrder, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
    jPanel2.add(jtfldIndexOrder, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 30, 0));

    jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(fwDetailTable);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);
  }
}
