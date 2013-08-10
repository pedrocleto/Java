package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.GridBagConstraints;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.BorderLayout;
import pt.inescporto.template.client.util.DataSourceRMI;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Insets;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplJTextField;
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
public class PartTypeForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");
  DataSourceRMI detail = null;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  TmplJLabel jlblPartTypeID = new TmplJLabel();
  TmplJTextField jtfldPartTypeID = new TmplJTextField();

  TmplJLabel jlblPartTypeDescription = new TmplJLabel();
  TmplJTextField jtfldPartTypeDescription = new TmplJTextField();

  public PartTypeForm() {
    super();

    // define the datasource tree
    DataSourceRMI master = new DataSourceRMI("PartType");
    master.setUrl("pt.inescporto.siasoft.proc.ejb.session.PartType");

    // register the master datasource the this form
    setDataSource(master);

    // set the id from permission control
    setAccessPermitionIdentifier("PART_TYPE");
    setPublisherEvent(SyncronizerSubjects.procPARTTYPE);

    // headers for the table headers
    allHeaders = new String[] {res.getString("partType.label.code"), res.getString("partType.label.desc")};

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
    dataSource.addDatasourceListener(jtfldPartTypeID);
    dataSource.addDatasourceListener(jtfldPartTypeDescription);

    addFWComponentListener(jtfldPartTypeID);
    addFWComponentListener(jtfldPartTypeDescription);
  }

  private void jbInit() throws Exception {
    jlblPartTypeID.setText(res.getString("partType.label.code"));
    jtfldPartTypeID.setField("partTypeID");
    jtfldPartTypeID.setHolder(jlblPartTypeID);

    jlblPartTypeDescription.setText(res.getString("partType.label.desc"));
    jtfldPartTypeDescription.setField("partTypeDescription");
    jtfldPartTypeDescription.setHolder(jlblPartTypeDescription);

    add(jPanel1, java.awt.BorderLayout.CENTER);
    jPanel2.setLayout(gridBagLayout1);
    jPanel2.setOpaque(false);
    jPanel1.setLayout(borderLayout1);
    jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);
    jPanel2.add(jlblPartTypeID, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jtfldPartTypeID, new GridBagConstraints(1, 0, 1, 1, 100.0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jlblPartTypeDescription, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel2.add(jtfldPartTypeDescription, new GridBagConstraints(1, 1, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}
