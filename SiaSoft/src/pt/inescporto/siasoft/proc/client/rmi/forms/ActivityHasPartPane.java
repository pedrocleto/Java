package pt.inescporto.siasoft.proc.client.rmi.forms;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Container;
import java.util.ResourceBundle;

import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pt.inescporto.siasoft.proc.ejb.dao.ActivityHasPartDao;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJRadioButton;
import pt.inescporto.template.client.design.TmplButtonGroup;
import pt.inescporto.template.client.design.TmplJButtonCancel;
import pt.inescporto.template.client.design.TmplJButtonSave;
import pt.inescporto.template.client.rmi.FW_JPanelNav;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;

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
public class ActivityHasPartPane extends FW_JPanelNav {
  TmplLookupButton jlbtnPartClass = new TmplLookupButton();
  TmplJTextField jtfldPartClass = new TmplJTextField();
  TmplLookupField jlfldPartClass = new TmplLookupField();

  TmplLookupButton jlbtnPart = new TmplLookupButton();
  TmplJTextField jtfldPart = new TmplJTextField();
  TmplLookupField jlfldPart = new TmplLookupField();

  TmplJLabel jlblQty = new TmplJLabel();
  TmplJTextField jtfldQty = new TmplJTextField();

  JToolBar jtbarNav = new JToolBar();
  TmplJButtonCancel jbtnCancel = new TmplJButtonCancel();
  TmplJButtonSave jbtnSave = new TmplJButtonSave();

  TmplButtonGroup bgFlowType = new TmplButtonGroup();
  TmplJRadioButton jrbtnInput = new TmplJRadioButton();
  TmplJRadioButton jrbtnOutput = new TmplJRadioButton();

  DataSourceRMI master = null;

  String activityID = null;
  String partClassID = null;
  String partID = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

  public ActivityHasPartPane(String activityID, String partClassID, String partID) {
    this.activityID = activityID;
    this.partClassID = partClassID;
    this.partID = partID;

    master = new DataSourceRMI("ActivityHasPart");
    master.setUrl("pt.inescporto.siasoft.proc.ejb.session.ActivityHasPart");

    setDataSource(master);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    addFWComponentListener(jbtnSave);
    addFWComponentListener(jbtnCancel);
    addFWComponentListener(jlbtnPartClass);
    addFWComponentListener(jtfldPartClass);
    addFWComponentListener(jlfldPartClass);
    addFWComponentListener(jlbtnPart);
    addFWComponentListener(jtfldPart);
    addFWComponentListener(jlfldPart);
    addFWComponentListener(jtfldQty);
    addFWComponentListener(bgFlowType);

    dataSource.addDatasourceListener(jbtnSave);
    dataSource.addDatasourceListener(jbtnCancel);
    dataSource.addDatasourceListener(jlbtnPartClass);
    dataSource.addDatasourceListener(jtfldPartClass);
    dataSource.addDatasourceListener(jlfldPartClass);
    dataSource.addDatasourceListener(jlbtnPart);
    dataSource.addDatasourceListener(jtfldPart);
    dataSource.addDatasourceListener(jlfldPart);
    dataSource.addDatasourceListener(jtfldQty);
    dataSource.addDatasourceListener(bgFlowType);

    jbtnSave.addActionListener(this);
    jbtnCancel.addActionListener(this);

    start();

    doInsert();
  }

  public ActivityHasPartPane(String activityID) {
    this(activityID, null, null);
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder(res.getString("activityHasPartPane.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    // nav bar
    jtbarNav.setFloatable(false);
    jtbarNav.setOrientation(JToolBar.VERTICAL);
    jtbarNav.add(jbtnSave, null);
    jtbarNav.add(jbtnCancel, null);
    add(jtbarNav, java.awt.BorderLayout.EAST);

    // form
    jtfldPartClass.setField("partClassID");
    jtfldPartClass.setLabel(res.getString("activityHasPartPane.label.class"));
    jlbtnPartClass.setText(res.getString("activityHasPartPane.label.class"));
    jlbtnPartClass.setDefaultFill(jtfldPartClass);
    jlbtnPartClass.setTitle(res.getString("activityHasPartPane.label.class"));
    jlbtnPartClass.setUrl("pt.inescporto.siasoft.proc.ejb.session.PartClass");
    jlfldPartClass.setUrl("pt.inescporto.siasoft.proc.ejb.session.PartClass");
    jlfldPartClass.setDefaultRefField(jtfldPartClass);

    jtfldPart.setField("partID");
    jtfldPart.setLabel(res.getString("activityHasPartPane.label.item"));
    jlbtnPart.setText(res.getString("activityHasPartPane.label.item"));
    jlbtnPart.setTitle(res.getString("activityHasPartPane.label.item"));
    jlbtnPart.setFillList(new JTextField[] {jtfldPartClass, jtfldPart});
    jlbtnPart.setComponentLinkList(new JComponent[]{jtfldPartClass});
    jlbtnPart.setUrl("pt.inescporto.siasoft.proc.ejb.session.Part");
    jlfldPart.setUrl("pt.inescporto.siasoft.proc.ejb.session.Part");
    jlfldPart.setRefFieldList(new JTextField[] {jtfldPartClass, jtfldPart});

    jlblQty.setText(res.getString("activityHasPartPane.label.quantity"));
    jtfldQty.setField("qty");
    jtfldQty.setHolder(jlblQty);

    jrbtnInput.setText(res.getString("activityHasPartPane.label.entry"));
    jrbtnInput.setValue("I");
    jrbtnOutput.setText(res.getString("activityHasPartPane.label.exit"));
    jrbtnOutput.setValue("O");
    bgFlowType.setField("flowType");
    bgFlowType.setLabel(res.getString("activityHasPartPane.label.entryExit"));
    bgFlowType.add(jrbtnInput);
    bgFlowType.add(jrbtnOutput);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 150dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] {{2, 4, 6, 8}});
    CellConstraints cc = new CellConstraints();

    content.add(jlbtnPartClass, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldPartClass, cc.xy(4, 2));
    content.add(jlfldPartClass, cc.xy(6, 2));

    content.add(jlbtnPart, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldPart, cc.xy(4, 4));
    content.add(jlfldPart, cc.xy(6, 4));

    content.add(jlblQty, cc.xy(2, 6));
    content.add(jtfldQty, cc.xy(4, 6));

    content.add(jrbtnInput, cc.xy(4, 8));
    content.add(jrbtnOutput, cc.xy(6, 8));

    add(content, BorderLayout.CENTER);
  }

  protected void doInsert() {
    try {
      ActivityHasPartDao daoAhP = (ActivityHasPartDao)dataSource.getCurrentRecord();
      daoAhP.activityID.setLinkKey();
      daoAhP.activityID.setValueAsObject(activityID);
      if (partClassID != null && partID != null) {
	daoAhP.partClassID.setLinkKey();
	daoAhP.partClassID.setValueAsObject(partClassID);
	daoAhP.partID.setLinkKey();
	daoAhP.partID.setValueAsObject(partID);
	try {
	  dataSource.findByPK(daoAhP);
          dataSource.reLinkAttrs();
          super.doUpdate();
          return;
	}
	catch (DataSourceException ex) {
	}
      }
      dataSource.reLinkAttrs();
      super.doInsert();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  protected boolean doSave() {
    if (super.doSave()) {
      Container parent = getParent();
      while (parent != null && !(parent instanceof JDialog))
        parent = parent.getParent();
      ((JDialog)parent).dispose();
    }
    return true;
  }

  protected void doCancel() {
    Container parent = getParent();
    while (parent != null && !(parent instanceof JDialog))
      parent = parent.getParent();
    ((JDialog)parent).dispose();
  }
}
