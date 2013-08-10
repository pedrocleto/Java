package pt.inescporto.siasoft.form.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;
import javax.swing.JPanel;

import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
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
public class TeachingEntityForm extends FW_InternalFrame {

  TmplJLabel jlblTeachingEntityID = new TmplJLabel();
  TmplJTextField jtfldTeachingEntityID = new TmplJTextField();

  TmplJLabel jlblTeachingEntityName = new TmplJLabel();
  TmplJTextField jtfldTeachingEntityName = new TmplJTextField();

  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.form.client.rmi.forms.FormResources");

  public TeachingEntityForm() {
    super();
     DataSourceRMI master = new DataSourceRMI("TeachingEntity");
     master.setUrl("pt.inescporto.siasoft.form.ejb.session.TeachingEntity");
     setDataSource(master);

     setPublisherEvent(SyncronizerSubjects.teachENTITY);

     init();
     start();
  }

  private void init() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    allHeaders = new String[] {res.getString("teachingEntity.label.code"), res.getString("teachingEntity.label.name")};

    dataSource.addDatasourceListener(jtfldTeachingEntityID);
    dataSource.addDatasourceListener(jtfldTeachingEntityName);

    addFWComponentListener(jtfldTeachingEntityID);
    addFWComponentListener(jtfldTeachingEntityName);
  }

  private void jbInit() throws Exception {
    jlblTeachingEntityID.setText(res.getString("teachingEntity.label.code"));
    jtfldTeachingEntityID.setField("teachingEntityID");
    jtfldTeachingEntityID.setHolder(jlblTeachingEntityID);

   jlblTeachingEntityName.setText(res.getString("teachingEntity.label.name"));
   jtfldTeachingEntityName.setField("teachingEntityName");
   jtfldTeachingEntityName.setHolder(jlblTeachingEntityName);

   add(jPanel1, java.awt.BorderLayout.CENTER);
   jPanel1.setLayout(borderLayout1);
   jPanel2.setLayout(gridBagLayout1);
   jPanel2.setOpaque(false);
   jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);
   jPanel2.add(jlblTeachingEntityID, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
   jPanel2.add(jtfldTeachingEntityID , new GridBagConstraints(1, 0, 1, 1, 100.0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
   jPanel2.add(jlblTeachingEntityName, new GridBagConstraints(0, 1, 1, 1, 70.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
   jPanel2.add(jtfldTeachingEntityName, new GridBagConstraints(1, 1, 1, 1, 300.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }
}
