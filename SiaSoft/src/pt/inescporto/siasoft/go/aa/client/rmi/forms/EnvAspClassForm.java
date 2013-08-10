package pt.inescporto.siasoft.go.aa.client.rmi.forms;

import java.util.ResourceBundle;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJFileChooser;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.TmplFileFilter;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Dimension;
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
public class EnvAspClassForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.aa.client.rmi.forms.FormResources");
  DataSourceRMI detail = null;
  JPanel jpnlContent = new JPanel();

  TmplJLabel jlblEnvAspClassID = new TmplJLabel();
  TmplJTextField jtfldEnvAspClassID = new TmplJTextField();

  TmplJLabel jlblEnvAspClassDescription = new TmplJLabel();
  TmplJTextField jtfldEnvAspClassDescription = new TmplJTextField();

  TmplJFileChooser jbtnFCPallet = null;
  TmplJLabel jlblPalletIcon = null;

  TmplJFileChooser jbtnFCGraph = null;
  TmplJLabel jlblGraphIcon = null;

  public EnvAspClassForm() {
    super();

    //define the datasource tree
    DataSourceRMI master = new DataSourceRMI("EnvAspClass");
    master.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvAspClass");

    //register the master datasource the this form
    setDataSource(master);

    //Environmental Aspect Event
    setPublisherEvent(SyncronizerSubjects.envAspClassFORM);

    //set the id from permission control
    //setPermFormId("ENVASPCLASS");

    //headers for the table headers
    allHeaders = new String[] {res.getString("envAspClass.label.code"), res.getString("envAspClass.label.desc"), res.getString("envAspClass.label.palletIcon"), res.getString("envAspClass.label.graphIcon")};

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
    dataSource.addDatasourceListener(jtfldEnvAspClassID);
    dataSource.addDatasourceListener(jtfldEnvAspClassDescription);
    dataSource.addDatasourceListener(jbtnFCPallet);
    dataSource.addDatasourceListener(jlblPalletIcon);
    dataSource.addDatasourceListener(jbtnFCGraph);
    dataSource.addDatasourceListener(jlblGraphIcon);

    addFWComponentListener(jtfldEnvAspClassID);
    addFWComponentListener(jtfldEnvAspClassDescription);
    addFWComponentListener(jbtnFCPallet);
    addFWComponentListener(jlblPalletIcon);
    addFWComponentListener(jbtnFCGraph);
    addFWComponentListener(jlblGraphIcon);
  }

  private void jbInit() throws Exception {
    jlblEnvAspClassID.setText(res.getString("envAspClass.label.code"));
    jtfldEnvAspClassID.setField("envAspClassID");
    jtfldEnvAspClassID.setHolder(jlblEnvAspClassID);

    jlblEnvAspClassDescription.setText(res.getString("envAspClass.label.desc"));
    jtfldEnvAspClassDescription.setField("envAspClassDescription");
    jtfldEnvAspClassDescription.setHolder(jlblEnvAspClassDescription);

    jlblPalletIcon = new TmplJLabel("", "palletIcon");
    jlblPalletIcon.setMinimumSize(new Dimension(48, 48));
    jlblPalletIcon.setPreferredSize(new Dimension(48, 48));
    jlblPalletIcon.setMaximumSize(new Dimension(48, 48));
    jbtnFCPallet = new TmplJFileChooser("Icon", jlblPalletIcon);
    TmplFileFilter filter = new TmplFileFilter();
    filter.addExtension("png");
    filter.addExtension("ico");
    filter.setDescription("PNG & ICO Images");
    jbtnFCPallet.setText( res.getString("envAspClass.label.palletIcon"));
    jbtnFCPallet.setFileFilter(filter);

    jlblGraphIcon = new TmplJLabel("", "graphIcon");
    jlblGraphIcon.setMinimumSize(new Dimension(48, 48));
    jlblGraphIcon.setPreferredSize(new Dimension(48, 48));
    jlblGraphIcon.setMaximumSize(new Dimension(48, 48));
    jbtnFCGraph = new TmplJFileChooser("Icon", jlblGraphIcon);
    jbtnFCGraph.setText(res.getString("envAspClass.label.graphIcon"));
    jbtnFCGraph.setFileFilter(filter);

    setOpaque(false);
    jpnlContent.setOpaque(false);
    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 50dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    jpnlContent.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] {{2, 4}});
    CellConstraints cc = new CellConstraints();

    jpnlContent.add(jlblEnvAspClassID, cc.xy(2,2));
    jpnlContent.add(jtfldEnvAspClassID, cc.xy(4,2));

    jpnlContent.add(jlblEnvAspClassDescription, cc.xy(2,4));
    jpnlContent.add(jtfldEnvAspClassDescription, cc.xyw(4,4,2));

    jpnlContent.add(jbtnFCPallet, cc.xy(2,6));
    jpnlContent.add(jlblPalletIcon, cc.xy(4,6));

    jpnlContent.add(jbtnFCGraph, cc.xy(2,8));
    jpnlContent.add(jlblGraphIcon, cc.xy(4,8));

    JPanel jpnlDummy = new JPanel(new BorderLayout());
    jpnlDummy.add(jpnlContent, BorderLayout.NORTH);
    add(jpnlContent, BorderLayout.CENTER);
  }
}
