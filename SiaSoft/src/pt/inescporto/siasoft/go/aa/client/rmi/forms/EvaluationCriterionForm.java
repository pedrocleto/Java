package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.awt.Color;
import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.FW_NavBarDetail;

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
public class EvaluationCriterionForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");
  JPanel jpnlMaster = new JPanel();
  DataSourceRMI detail = null;

  TmplJLabel jlblEvalCriterionID = new TmplJLabel();
  TmplJTextField jtfldEvalCriterionID = new TmplJTextField();
  TmplJLabel jlblEvalCriterionDescription = new TmplJLabel();
  TmplJTextField jtfldEvalCriterionDescription = new TmplJTextField();

  JScrollPane jspDetail = new JScrollPane();
  FW_JTable fwDetailTable = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public EvaluationCriterionForm() {
    super();

    DataSourceRMI master = new DataSourceRMI("EvaluationCriterion");
    master.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EvaluationCriterion");
    setDataSource(master);

    detail = new DataSourceRMI("ECritCategory");
    detail.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.ECritCategory");

    DSRelation mdRelation = new DSRelation("MasterDetail");
    mdRelation.setMaster(master);
    mdRelation.setDetail(detail);
    mdRelation.addKey(new RelationKey("evalCriterionID", "evalCriterionID"));

    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    allHeaders = new String[] {res.getString("evaluationCriterion.label.code"), res.getString("evaluationCriterion.description")};

    init();
    start();
  }

  public void init() {
    jlblEvalCriterionID.setText(res.getString("evaluationCriterion.label.code"));
    jtfldEvalCriterionID.setHolder(jlblEvalCriterionID);
    jtfldEvalCriterionID.setField("evalCriterionID");

    jlblEvalCriterionDescription.setText(res.getString("evaluationCriterion.description"));
    jtfldEvalCriterionDescription.setHolder(jlblEvalCriterionDescription);
    jtfldEvalCriterionDescription.setField("evalCriterionDescription");

    FW_ColumnManager colManager = new FW_ColumnManager();
    FW_ColumnNode node1 = new FW_ColumnNode("Categoria", "eCritCategoryID", new TmplStringRenderer(), new TmplStringEditor());
    FW_ColumnNode node2 = new FW_ColumnNode("Descrição", "eCritCategoryDescription", new TmplStringRenderer(), new TmplStringEditor());
    colManager.addColumnNode(node1);
    colManager.addColumnNode(node2);

    fwDetailTable = new FW_JTable(detail, null, colManager);
    fwDetailTable.setAsMaster(false);

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    dataSource.addDatasourceListener(jtfldEvalCriterionID);
    dataSource.addDatasourceListener(jtfldEvalCriterionDescription);

    addFWComponentListener(jtfldEvalCriterionID);
    addFWComponentListener(jtfldEvalCriterionDescription);
    addFWComponentListener(fwDetailTable);

    //table listener registration
    navBarDetail.setFW_ComponentListener(fwDetailTable);
    navBarDetail.setActionListener(fwDetailTable);
  }

  private void jbInit() throws Exception {
    JPanel jpnlDummy = new JPanel(new BorderLayout());

    setOpaque(false);
    jpnlMaster.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 50dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 5px");
    jpnlMaster.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4}});
    CellConstraints cc = new CellConstraints();

    jpnlMaster.add(jlblEvalCriterionID, cc.xy(2,2));
    jpnlMaster.add(jtfldEvalCriterionID, cc.xy(4,2));
    jpnlMaster.add(jlblEvalCriterionDescription, cc.xy(2,4));
    jpnlMaster.add(jtfldEvalCriterionDescription, cc.xyw(4,4,2));
    jpnlDummy.add(jpnlMaster, BorderLayout.NORTH);

    jspDetail.getViewport().add(fwDetailTable);
    jpnlDummy.add(jspDetail, BorderLayout.CENTER);

    jpnlDummy.add(navBarDetail, BorderLayout.SOUTH);

    add(jpnlDummy, BorderLayout.CENTER);
  }
}
