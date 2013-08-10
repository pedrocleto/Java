package pt.inescporto.siasoft.comun.client.rmi.forms;

import java.awt.*;
import javax.swing.*;
import pt.inescporto.template.client.rmi.FW_JPanel;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJDateField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.TmplJTextArea;
import pt.inescporto.template.client.event.TemplateEvent;
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
public class ProfileGroupForm extends JDialog {
  BorderLayout borderLayout1 = new BorderLayout();

  public ProfileGroupForm(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    try {
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      jbInit();
      pack();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getPreferredSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }

  private void jbInit() throws Exception {
    getContentPane().setLayout(borderLayout1);
    ProfileGroupPanel panel = new ProfileGroupPanel();
    add(panel, BorderLayout.CENTER);
  }
}

class ProfileGroupPanel extends FW_JPanel {
  DataSourceRMI detail = null;

  JPanel jpnlMaster = new JPanel(null);
  TmplJLabel jlblCode = new TmplJLabel();
  TmplJTextField jtfldCode = new TmplJTextField();
  TmplJLabel jlblDescription = new TmplJLabel();
  TmplJTextField jtfldDescription = new TmplJTextField();

  TmplLookupButton jlbtnProfileCreator = new TmplLookupButton();
  TmplJTextField jtfldProfileCreatorID = new TmplJTextField();
  TmplLookupField jlfldProfileCreatorDescription = new TmplLookupField();

  TmplJLabel jlblProfileDate = new TmplJLabel();
  TmplJDateField jtfldProfileDate = new TmplJDateField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }
  };

  TmplJLabel jlblProfileRevisionDate = new TmplJLabel();
  TmplJDateField jtfldProfileRevisionDate = new TmplJDateField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }
  };

  TmplJLabel jlblProfileObs = new TmplJLabel();
  TmplJTextArea jtfldProfileObs = new TmplJTextArea();

  JScrollPane jsp = new JScrollPane();
  FW_JTable fwDetailTable = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  public ProfileGroupPanel() {
    super();

    // define the datasource tree
    DataSourceRMI master = new DataSourceRMI("ProfileGroup");
    master.setUrl("pt.inescporto.siasoft.comun.ejb.session.ProfileGroup");

    detail = new DataSourceRMI("ProfileGroupHasEnterprise");
    detail.setUrl("pt.inescporto.siasoft.comun.ejb.session.ProfileGroupHasEnterprise");

    DSRelation mdRelation = new DSRelation("MasterDetail");
    mdRelation.setMaster(master);
    mdRelation.setDetail(detail);
    mdRelation.addKey(new RelationKey("profileGroupID", "profileGroupID"));
    try {
      master.addDataSourceLink(mdRelation);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    // register the master datasource the this form
    setDataSource(master);

    // set the id from permission control
//    setPermFormId("THEME");

    setPublisherEvent(SyncronizerSubjects.sysGroupProfile);

    init();
    start();
  }

  public void init() {
    allHeaders = new String[] {res.getString("profile.label.code"), res.getString("profile.label.desc"), res.getString("profile.label.enterprise")};

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    dataSource.addDatasourceListener(jtfldCode);
    dataSource.addDatasourceListener(jtfldDescription);
    dataSource.addDatasourceListener(jlbtnProfileCreator);
    dataSource.addDatasourceListener(jtfldProfileCreatorID);
    dataSource.addDatasourceListener(jlfldProfileCreatorDescription);
    dataSource.addDatasourceListener(jtfldProfileDate);
    dataSource.addDatasourceListener(jtfldProfileRevisionDate);
    dataSource.addDatasourceListener(jtfldProfileObs);

    addFWComponentListener(jtfldCode);
    addFWComponentListener(jtfldDescription);
    addFWComponentListener(jlbtnProfileCreator);
    addFWComponentListener(jtfldProfileCreatorID);
    addFWComponentListener(jlfldProfileCreatorDescription);
    addFWComponentListener(jtfldProfileDate);
    addFWComponentListener(jtfldProfileRevisionDate);
    addFWComponentListener(jtfldProfileObs);

    addFWComponentListener(fwDetailTable);

    //table listener registration
    navBarDetail.setFW_ComponentListener(fwDetailTable);
    navBarDetail.setActionListener(fwDetailTable);
  }

  private void jbInit() throws Exception {
    JPanel dummy = new JPanel(new BorderLayout());

    FormLayout formLayout = new FormLayout("5px, pref, 4dlu, 50dlu, 4dlu, pref,4dlu,50dlu,20dlu:grow,5px",
					   "5px, pref, 2dlu, pref, 2dlu, pref,2dlu, pref,2dlu, 50dlu,pref,2dlu,5px");

    jpnlMaster.setOpaque(false);
    jpnlMaster.setLayout(formLayout);

    CellConstraints cc = new CellConstraints();

    jlblCode.setText(res.getString("profile.label.code"));
    // jlblCode.setBounds(5, 5, 70, 21);
    jtfldCode.setField("profileGroupID");
    jtfldCode.setHolder(jlblCode);
    // jtfldCode.setBounds(80, 5, 150, 21);
    jlblDescription.setText(res.getString("profile.label.desc"));
    // jlblDescription.setBounds(5, 30, 70, 21);
    jtfldDescription.setField("profileDesc");
    // jtfldDescription.setBounds(80, 30, 250, 21);
    jtfldDescription.setHolder(jlblDescription);

    jtfldProfileCreatorID.setLabel(res.getString("profile.label.profileCreator"));
    jtfldProfileCreatorID.setField("profileCreator");

    jlbtnProfileCreator.setText(res.getString("profile.label.profileCreator"));
    jlbtnProfileCreator.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier())
      jlbtnProfileCreator.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnProfileCreator.setDefaultFill(jtfldProfileCreatorID);
    jlbtnProfileCreator.setTitle(res.getString("profile.label.profileCreator"));
    jlbtnProfileCreator.setDefaultFill(jtfldProfileCreatorID);

    jlfldProfileCreatorDescription.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    jlfldProfileCreatorDescription.setDefaultRefField(jtfldProfileCreatorID);

    jlblProfileDate.setText(res.getString("profile.label.profileDate"));
    jtfldProfileDate.setField("profileDate");
    jtfldProfileDate.setHolder(jlblProfileDate);

    jlblProfileRevisionDate.setText(res.getString("profile.label.profileRevisonDate"));
    jtfldProfileRevisionDate.setField("profileRevisonDate");
    jtfldProfileRevisionDate.setHolder(jlblProfileRevisionDate);

    jlblProfileObs.setText(res.getString("profile.label.Obs"));
    jtfldProfileObs.setField("profileObs");
    jtfldProfileObs.setHolder(jlblProfileObs);
    jtfldProfileObs.setWrapStyleWord(true);
    jtfldProfileObs.setLineWrap(true);
    JScrollPane jspRes = new JScrollPane(jtfldProfileObs);

    jpnlMaster.add(jlblCode, cc.xy(2, 2));
    jpnlMaster.add(jtfldCode, cc.xyw(4, 2, 3));
    jpnlMaster.add(jlblDescription, cc.xy(2, 4));
    jpnlMaster.add(jtfldDescription, cc.xyw(4, 4, 3));

    jpnlMaster.add(jlbtnProfileCreator, cc.xy(2, 6));
    jpnlMaster.add(jtfldProfileCreatorID, cc.xy(4, 6));
    jpnlMaster.add(jlfldProfileCreatorDescription, cc.xyw(6, 6, 4));

    jpnlMaster.add(jlblProfileDate, cc.xy(2, 8));
    jpnlMaster.add(jtfldProfileDate, cc.xy(4, 8));

    jpnlMaster.add(jlblProfileRevisionDate, cc.xy(6, 8));
    jpnlMaster.add(jtfldProfileRevisionDate, cc.xy(8, 8));

    jpnlMaster.add(jlblProfileObs, cc.xy(2, 10));
    jpnlMaster.add(jspRes, cc.xywh(4, 10, 6, 3, CellConstraints.FILL, CellConstraints.FILL));

    FW_ColumnManager colManager = new FW_ColumnManager();
    FW_ColumnNode node1 = new FW_ColumnNode(res.getString("profile.label.enterprise"),
					    "enterpriseID",
					    new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.Enterprise"),
					    new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.Enterprise", new Integer[] {new Integer(1), new Integer(0)}));
    colManager.addColumnNode(node1);

    fwDetailTable = new FW_JTable(detail, null, colManager);
    fwDetailTable.setAsMaster(false);
    fwDetailTable.setPublisherEvent(SyncronizerSubjects.sysGroupProfile);
    jsp.getViewport().add(fwDetailTable);

    dummy.add(jpnlMaster, BorderLayout.NORTH);
    dummy.add(jsp, BorderLayout.CENTER);
    add(dummy, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);

    setMinimumSize(new Dimension(300, 250));
    setPreferredSize(new Dimension(600, 500));

  }
}
