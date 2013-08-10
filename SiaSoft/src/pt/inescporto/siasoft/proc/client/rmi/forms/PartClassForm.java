package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.MenuFrame;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
public class PartClassForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  DataSourceRMI detail = null;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  TmplJLabel jlblCode = new TmplJLabel();
  TmplJTextField jtfldCode = new TmplJTextField();

  TmplJLabel jlblDescription = new TmplJLabel();
  TmplJTextField jtfldDescription = new TmplJTextField();

  TmplJTextField jtfldEnterprise = new TmplJTextField();

  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout3 = new BorderLayout();
  FW_JTable fwDetailTable = null;

  public PartClassForm() {
    super();

    if (MenuSingleton.isSupplier() && MenuSingleton.getSelectedEnterprise() == null) {
      JFrame frame = new JFrame();
      JOptionPane.showMessageDialog(frame,
                                    res.getString("label.chooseEnterprise"),
                                    res.getString("label.warning"),
                                    JOptionPane.WARNING_MESSAGE);
      dispose();
      return;
    }

    // define the datasource tree
    DataSourceRMI master = new DataSourceRMI("PartClass");
    master.setUrl("pt.inescporto.siasoft.proc.ejb.session.PartClass");

    detail = new DataSourceRMI("Part");
    detail.setUrl("pt.inescporto.siasoft.proc.ejb.session.Part");

    DSRelation mdRelation = new DSRelation("MasterDetail");
    mdRelation.setMaster(master);
    mdRelation.setDetail(detail);
    mdRelation.addKey(new RelationKey("partClassID", "partClassID"));
    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    // register the master datasource the this form
    setDataSource(master);

    //Activity Event
    setPublisherEvent(SyncronizerSubjects.partIdFORM);

    // set the id from permission control
    setAccessPermitionIdentifier("PART_CLASS");

    // headers for the table headers
    allHeaders = new String[] {res.getString("partClass.label.code"), res.getString("partClass.label.desc"), res.getString("partClass.label.enterprise")};

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
    dataSource.addDatasourceListener(jtfldEnterprise);

    addFWComponentListener(jtfldCode);
    addFWComponentListener(jtfldDescription);
    addFWComponentListener(fwDetailTable);

    //table listener registration
    navBarDetail.setFW_ComponentListener(fwDetailTable);
    navBarDetail.setActionListener(fwDetailTable);
  }

  private void jbInit() throws Exception {
    jlblCode.setText(res.getString("partClass.label.code"));
    jtfldCode.setField("partClassID");
    jtfldCode.setHolder(jlblCode);
    jlblDescription.setText(res.getString("partClass.label.desc"));
    jtfldDescription.setField("partClassDescription");
    jtfldDescription.setHolder(jlblDescription);
    jtfldEnterprise.setField("fkEnterpriseID");

    FW_ColumnManager colManager = new FW_ColumnManager();
    FW_ColumnNode node1 = new FW_ColumnNode(0, res.getString("partClass.label.code"), "partID", new TmplStringRenderer(), new TmplStringEditor());
    FW_ColumnNode node2 = new FW_ColumnNode(1, res.getString("partClass.label.desc"), "partDescription", new TmplStringRenderer(), new TmplStringEditor());
    TmplComboBoxEditor cbePartType = new TmplComboBoxEditor("pt.inescporto.siasoft.proc.ejb.session.PartType", new Integer[] {new Integer(1), new Integer(0)});
    cbePartType.setWatcherSubject(SyncronizerSubjects.procPARTTYPE);
    FW_ColumnNode node3 = new FW_ColumnNode(2, res.getString("partClass.label.type"), "partTypeID",
					    new TmplLookupRenderer("pt.inescporto.siasoft.proc.ejb.session.PartType"),
					    cbePartType);
    TmplComboBoxEditor cbeUnit = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.Units", new Integer[] {new Integer(1), new Integer(0)});
    cbeUnit.setWatcherSubject(SyncronizerSubjects.sysUNIT);
    FW_ColumnNode node4 = new FW_ColumnNode(3, res.getString("partClass.label.unit"), "unitID",
					    new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.Units"),
					    cbeUnit);
    colManager.addColumnNode(node1);
    colManager.addColumnNode(node2);
    colManager.addColumnNode(node3);
    colManager.addColumnNode(node4);

    fwDetailTable = new FW_JTable(detail, null, colManager);
    fwDetailTable.setAsMaster(false);
    fwDetailTable.setPublisherEvent(SyncronizerSubjects.partIdDetailFORM);

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
    jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
    jScrollPane1.getViewport().add(fwDetailTable);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);
  }

  protected void doInsert() {
    super.doInsert();
    jtfldEnterprise.setText(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise());
  }
}
