package pt.inescporto.siasoft.comun.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.util.DataSourceRMI;
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
public class UnitsForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");
  DataSourceRMI detail = null;
  JPanel jpnlContent = new JPanel();

  TmplJLabel jlblUnitID = new TmplJLabel();
  TmplJTextField jtfldUnitID = new TmplJTextField();

  TmplJLabel jlblUnitDescription = new TmplJLabel();
  TmplJTextField jtfldUnitDescription = new TmplJTextField();

  TmplJLabel jlblMinValue = new TmplJLabel();
  TmplJTextField jtfldMinValue = new TmplJTextField();

  TmplJLabel jlblMaxValue = new TmplJLabel();
  TmplJTextField jtfldMaxValue = new TmplJTextField();

  TmplJLabel jlblPrecision = new TmplJLabel();
  TmplJTextField jtfldPrecision = new TmplJTextField();

  public UnitsForm() {
    super();

    // define the datasource tree
    DataSourceRMI master = new DataSourceRMI("Units");
    master.setUrl("pt.inescporto.siasoft.comun.ejb.session.Units");

    // register the master datasource the this form
    setDataSource(master);

    // set the id from permission control
    setAccessPermitionIdentifier("UNITS");
    setPublisherEvent(SyncronizerSubjects.sysUNIT);

    // headers for the table headers
    allHeaders = new String[] {res.getString("units.label.code"), res.getString("units.label.desc"), res.getString("units.label.minValue"), res.getString("units.label.maxValue"), res.getString("units.label.precision")};

    init();
    start();
  }

  public void init() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    dataSource.addDatasourceListener(jtfldUnitID);
    dataSource.addDatasourceListener(jtfldUnitDescription);
    dataSource.addDatasourceListener(jtfldMinValue);
    dataSource.addDatasourceListener(jtfldMaxValue);
    dataSource.addDatasourceListener(jtfldPrecision);

    addFWComponentListener(jtfldUnitID);
    addFWComponentListener(jtfldUnitDescription);
    addFWComponentListener(jtfldMinValue);
    addFWComponentListener(jtfldMaxValue);
    addFWComponentListener(jtfldPrecision);
  }

  private void jbInit() throws Exception {
    jlblUnitID.setText(res.getString("units.label.code"));
    jtfldUnitID.setField("unitID");
    jtfldUnitID.setHolder(jlblUnitID);

    jlblUnitDescription.setText(res.getString("units.label.desc"));
    jtfldUnitDescription.setField("unitDesc");
    jtfldUnitDescription.setHolder(jlblUnitDescription);

    jlblMinValue.setText(res.getString("units.label.minValue"));
    jtfldMinValue.setField("minValue");
    jtfldMinValue.setHolder(jlblMinValue);

    jlblMaxValue.setText(res.getString("units.label.maxValue"));
    jtfldMaxValue.setField("maxValue");
    jtfldMaxValue.setHolder(jlblMaxValue);

    jlblPrecision.setText(res.getString("units.label.precision"));
    jtfldPrecision.setField("precision");
    jtfldPrecision.setHolder(jlblPrecision);

    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 50dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4, 6, 8, 10}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblUnitID, cc.xy(2,2));
    jpnlContent.add(jtfldUnitID, cc.xy(4,2));

    jpnlContent.add(jlblUnitDescription, cc.xy(2,4));
    jpnlContent.add(jtfldUnitDescription, cc.xyw(4,4,2));

    jpnlContent.add(jlblMinValue, cc.xy(2,6));
    jpnlContent.add(jtfldMinValue, cc.xy(4,6));

    jpnlContent.add(jlblMaxValue, cc.xy(2,8));
    jpnlContent.add(jtfldMaxValue, cc.xy(4,8));

    jpnlContent.add(jlblPrecision, cc.xy(2,10));
    jpnlContent.add(jtfldPrecision, cc.xy(4,10));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);
  }
}
