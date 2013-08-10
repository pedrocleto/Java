package pt.inescporto.siasoft.asq.web.forms;

import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJTextArea;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.TmplJButton;
import java.awt.BorderLayout;
import pt.inescporto.siasoft.comun.client.rmi.forms.ApplyProfileInterface;
import javax.swing.tree.TreePath;
import javax.swing.tree.DefaultMutableTreeNode;
import pt.inescporto.siasoft.comun.ejb.dao.ProfileBuilderNode;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import pt.inescporto.template.dao.*;
import pt.inescporto.template.client.util.*;
import java.rmi.*;
import javax.naming.*;
import pt.inescporto.siasoft.comun.client.rmi.forms.ProfileSelectorPanel;
import java.awt.Dimension;
import pt.inescporto.siasoft.asq.ejb.dao.AsqApplyRegulatoryDao;
import pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.FW_JTable;
import javax.swing.BorderFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.FW_JToggleButtonThreeState;
import pt.inescporto.template.elements.TplBoolean;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.design.TmplJDatePicker;

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
public class RegulatoryPanel extends JPanel implements ApplyProfileInterface {
  JPanel jpnlContent = new JPanel();
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  private TmplJLabel jlblRegulatoryId = null;
  private TmplJTextField jtfldRegulatoryId = null;
  private FW_JToggleButtonThreeState jtbtnRevocate = null;
  private ColorToggleButton jtbtState = null;
  private FW_JToggleButtonThreeState jtbtnIncomplete = null;
  private TmplJLabel jlblName = null;
  private TmplJTextField jtfldName = null;
  private TmplJComboBox cboxSource = null;
  private TmplJLabel jlblResume = null;
  private JScrollPane jspResume = null;
  private TmplJTextArea jtaResume = null;
  private TmplJLabel jlblComeIntoForceDate = null;
  private TmplJDatePicker jtfldComeIntoForceDate = null;
  private TmplJLabel jlblPublishDate = null;
  private TmplJDatePicker jtfldPublishDate = null;
  private TmplJLabel jlblStartDate = null;
  private TmplJDatePicker jtfldStartDate = null;
  private TmplJLabel jlblEndDate = null;
  private TmplJDatePicker jtfldEndDate = null;
  private TmplJLabel jlblDocumentName = null;
  private TmplJTextField jtfldDocumentName = null;

  public RegulatoryPanel() {
    initialize();
  }

  public RegulatoryPanel(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    fwCListener.addFWComponentListener(jtfldRegulatoryId);
    fwCListener.addFWComponentListener(jtbtnRevocate);
    fwCListener.addFWComponentListener(jtbtState);
    fwCListener.addFWComponentListener(jtbtnIncomplete);
    fwCListener.addFWComponentListener(jtfldName);
    fwCListener.addFWComponentListener(cboxSource);
    fwCListener.addFWComponentListener(jtaResume);
    fwCListener.addFWComponentListener(jtfldComeIntoForceDate);
    fwCListener.addFWComponentListener(jtfldPublishDate);
    fwCListener.addFWComponentListener(jtfldStartDate);
    fwCListener.addFWComponentListener(jtfldEndDate);
    fwCListener.addFWComponentListener(jtfldDocumentName);

    datasource.addDatasourceListener(jtfldRegulatoryId);
    datasource.addDatasourceListener(jtbtnRevocate);
    datasource.addDatasourceListener(jtbtState);
    datasource.addDatasourceListener(jtbtnIncomplete);
    datasource.addDatasourceListener(jtfldName);
    datasource.addDatasourceListener(cboxSource);
    datasource.addDatasourceListener(jtaResume);
    datasource.addDatasourceListener(jtfldComeIntoForceDate);
    datasource.addDatasourceListener(jtfldPublishDate);
    datasource.addDatasourceListener(jtfldStartDate);
    datasource.addDatasourceListener(jtfldEndDate);
    datasource.addDatasourceListener(jtfldDocumentName);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    try {
      setOpaque(false);
      jpnlContent.setOpaque(false);
      FormLayout formLayout = new FormLayout("5px,pref,4dlu, left:70dlu:grow, 4dlu, 35dlu, 4dlu, 35dlu, 4dlu, 35dlu, 4dlu, 50dlu:grow, 5px",
                                             "5px, pref, 2dlu, pref, 2dlu, 40dlu:grow(0.2), 2dlu, pref, 2dlu, pref, 2dlu, 50dlu:grow, 5px");
      jpnlContent.setLayout(formLayout);

      formLayout.setRowGroups(new int[][] {{2, 4, 8}});
      CellConstraints cc = new CellConstraints();
      jpnlContent.add(getJlblRegulatoryId(), cc.xy(2, 2));
      jpnlContent.add(getJtfldRegulatoryId(), cc.xy(4, 2, CellConstraints.FILL, CellConstraints.DEFAULT));
      jpnlContent.add(getJtbtnRevocate(), cc.xy(6, 2));
      jpnlContent.add(getjbtnIncomplete(), cc.xy(8, 2));
      jpnlContent.add(getJtbtnState(), cc.xy(10, 2));
      jpnlContent.add(getCboxSource(), cc.xy(12, 2));
      jpnlContent.add(getJlblName(), cc.xy(2, 4));
      jpnlContent.add(getJtfldName(), cc.xyw(4, 4, 9));
      jpnlContent.add(getJlblResume(), cc.xy(2, 6));
      jpnlContent.add(getJspResume(), cc.xyw(4, 6, 9, CellConstraints.FILL, CellConstraints.FILL));

      FormLayout flDate = new FormLayout("pref, 2dlu, 40dlu, 4dlu:grow, pref, 2dlu, 40dlu, 4dlu:grow,pref, 2dlu, 40dlu, 4dlu:grow, pref, 2dlu, 40dlu",
                                         "pref");
      JPanel jpnlDate = new JPanel(flDate);
      jpnlDate.add(getJlblStartDate(), cc.xy(1, 1));
      jpnlDate.add(getJtfldStartDate(), cc.xy(3, 1));
      jpnlDate.add(getJlblVigorDate(), cc.xy(5, 1));
      jpnlDate.add(getJtfldComeIntoForceDate(), cc.xy(7, 1));
      jpnlDate.add(getJlblEndVigorDate(), cc.xy(9, 1));
      jpnlDate.add(getJtfldEndVigorDate(), cc.xy(11, 1));
      jpnlDate.add(getJlblPublishDate(), cc.xy(13, 1));
      jpnlDate.add(getJtfldPublishDate(), cc.xy(15, 1));
      jpnlContent.add(jpnlDate, cc.xyw(2, 8, 11, CellConstraints.FILL, CellConstraints.FILL));

      jpnlContent.add(getJlblDocumentName(), cc.xy(2, 10));
      jpnlContent.add(getJtfldDocumentName(), cc.xyw(4, 10, 9));

      FormLayout flTabels = new FormLayout("0dlu:grow:center, 2dlu, 0dlu:grow:center",
                                           "50dlu:grow");
      JPanel jpnlTabels = new JPanel(flTabels);
      jpnlTabels.add(getClassPanel(), cc.xyw(1, 1, 3, CellConstraints.FILL, CellConstraints.FILL));
      jpnlContent.add(jpnlTabels, cc.xyw(2, 12, 11, CellConstraints.FILL, CellConstraints.FILL));

      JPanel jPanelProfile = new JPanel(new BorderLayout());
      if (MenuSingleton.isSupplier()) {
        ProfileSelectorPanel psp = new ProfileSelectorPanel(this);
        psp.setVisible(false);
        TmplJButton tt = new TmplJButton("<>");
        tt.addActionListener(new AL(psp));
        add(psp, BorderLayout.WEST);
        jPanelProfile.add(tt, BorderLayout.WEST);
      }
      jPanelProfile.add(jpnlContent, BorderLayout.CENTER);
      add(jPanelProfile, BorderLayout.CENTER);
    }
    catch (java.lang.Throwable e) {
      e.printStackTrace();
    }
  }

  public void applyProfile(TreePath[] treePath, Integer app) {
    Vector<AsqApplyRegulatoryDao> regAppList = new Vector<AsqApplyRegulatoryDao>();

    for (TreePath path : treePath) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
      ProfileBuilderNode tbn = (ProfileBuilderNode)node.getUserObject();

      Object[] userObjectPath = node.getUserObjectPath();
      AsqApplyRegulatoryDao aarDao = new AsqApplyRegulatoryDao();

      switch (tbn.getNodeType()) {
	case ProfileBuilderNode.PROFILE_GROUP:
	  aarDao.profileGroup = ((ProfileBuilderNode)userObjectPath[1]).getNodeID();
	  break;
	case ProfileBuilderNode.ENTERPRISE:
	  aarDao.enterprise = ((ProfileBuilderNode)userObjectPath[2]).getNodeID();
	  break;
	case ProfileBuilderNode.USER:
	  aarDao.enterprise = ((ProfileBuilderNode)userObjectPath[2]).getNodeID();
	  aarDao.user = ((ProfileBuilderNode)userObjectPath[3]).getNodeID();
	  break;
	case ProfileBuilderNode.PROFILE:
	  userObjectPath = node.getUserObjectPath();
	  aarDao.enterprise = ((ProfileBuilderNode)userObjectPath[2]).getNodeID();
	  aarDao.user = ((ProfileBuilderNode)userObjectPath[3]).getNodeID();
	  aarDao.profileUser = ((ProfileBuilderNode)userObjectPath[4]).getNodeID();
	  break;
      }
      try {
	aarDao.regulatory = ((RegulatoryDao)datasource.getCurrentRecord()).regId.getValue();
      }
      catch (DataSourceException ex1) {
	ex1.printStackTrace();
      }
      aarDao.app = new Integer(app.intValue());
      regAppList.add(aarDao);
    }

    try {
      AsqClientApplicability aca = (AsqClientApplicability)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability");
      aca.applyRegulatoryList(regAppList);
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
    catch (UserException ex) {
      ex.printStackTrace();
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
  }
  private TmplJLabel getJlblRegulatoryId() {
    jlblRegulatoryId = new TmplJLabel();
    jlblRegulatoryId.setText(res.getString("regulatory.label.code"));

    return jlblRegulatoryId;
  }
  /**
   * This method initializes tmplJTextField
   *
   * @return pt.inescporto.template.client.design.TmplJTextField
   */
  private TmplJTextField getJtfldRegulatoryId() {
    try {
      if (jtfldRegulatoryId == null) {
	jtfldRegulatoryId = new TmplJTextField();
	jtfldRegulatoryId.setField("regId");
      }
    }
    catch (java.lang.Throwable e) {
      // TODO: Something
    }
    return jtfldRegulatoryId;
  }

  /**
   * This method initializes tmplJToggleButton
   *
   * @return pt.inescporto.template.client.design.TmplJToggleButton
   */
  private FW_JToggleButtonThreeState getJtbtnRevocate() {
    if (jtbtnRevocate == null) {
      try {
	jtbtnRevocate = new FW_JToggleButtonThreeState() {
	  public void tmplRefresh(TemplateEvent e) {
	    try {
	      setSelected(((TplBoolean)link).getValue().booleanValue());
	      setBackground(((TplBoolean)link).getValue().booleanValue() ? Color.red : SystemColor.control);
	    }
	    catch (Exception ex) {
	      setSelected(false);
	    }
	  }
	};
	jtbtnRevocate.setField("revocate");
	jtbtnRevocate.setText("R.N.V.");
        jtbtnRevocate.setDataValues(new Boolean[] {Boolean.FALSE, Boolean.TRUE});
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jtbtnRevocate;
  }

  /**
   * This method initializes tmplJToggleButton1
   *
   * @return pt.inescporto.template.client.design.TmplJToggleButton
   */
    private ColorToggleButton getJtbtnState() {
    if (jtbtState == null) {
      try {
	jtbtState = new ColorToggleButton();
	jtbtState.setField("state");
        jtbtState.setLabels(new String[] {"OFF", "ON"});
        jtbtState.setDataValues(new Boolean[] {Boolean.FALSE, Boolean.TRUE});
	jtbtState.setColors(new Color[] {Color.black, Color.white});
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jtbtState;
  }

  /**
   * This method initializes tmplJToggleButton2
   *
   * @return pt.inescporto.template.client.design.TmplJToggleButton
   */
  private FW_JToggleButtonThreeState getjbtnIncomplete() {
    if (jtbtnIncomplete == null) {
      try {
	jtbtnIncomplete = new FW_JToggleButtonThreeState() {
	  public void tmplRefresh(TemplateEvent e) {
	    try {
	      setSelected(((TplBoolean)link).getValue().booleanValue());
	      setBackground(((TplBoolean)link).getValue().booleanValue() ? Color.yellow : SystemColor.control);
	    }
	    catch (Exception ex) {
	      setSelected(false);
	    }
	  }
	};

	jtbtnIncomplete.setField("incompleted");
	jtbtnIncomplete.setText("INC");
        jtbtnIncomplete.setDataValues(new Boolean[] {Boolean.FALSE, Boolean.TRUE});
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jtbtnIncomplete;
  }

  private TmplJLabel getJlblName() {
    jlblName = new TmplJLabel();
    jlblName.setText(res.getString("regulatory.label.desc"));

    return jlblName;
  }
  /**
   * This method initializes tmplJTextField
   *
   * @return pt.inescporto.template.client.design.TmplJTextField
   */
  private TmplJTextField getJtfldName() {
    if (jtfldName == null) {
      try {
	jtfldName = new TmplJTextField();
	jtfldName.setField("name");
	jtfldName.setLabel(res.getString("regPanel.label.desc"));
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jtfldName;
  }

  /**
   * This method initializes tmplJComboBox1
   *
   * @return pt.inescporto.template.client.design.TmplJComboBox
   */
  private TmplJComboBox getCboxSource() {
    if (cboxSource == null) {
      try {
	cboxSource = new TmplJComboBox();
	cboxSource.setField("fk_sourceId");
	cboxSource.setPreferredSize(new java.awt.Dimension(1, 22));
	cboxSource.setUrl("pt.inescporto.siasoft.asq.ejb.session.Source");
	cboxSource.setShowSave(new Integer[] {new Integer(0), new Integer(0)});
        cboxSource.setLabel(res.getString("regPanel.label.publicationSource"));
        cboxSource.setWatcherSubject(SyncronizerSubjects.sourceFORM);
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return cboxSource;
  }

  private TmplJLabel getJlblResume() {
    jlblResume = new TmplJLabel();
    jlblResume.setText(res.getString("regulatory.label.resume"));

    return jlblResume;
  }
  /**
   * This method initializes jScrollPane
   *
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getJspResume() {
    if (jspResume == null) {
      try {
	jspResume = new JScrollPane();
	jspResume.getViewport().add(getJtaResume());
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jspResume;
  }

  /**
   * This method initializes tmplJTextArea
   *
   * @return pt.inescporto.template.client.design.TmplJTextArea
   */
  private TmplJTextArea getJtaResume() {
    if (jtaResume == null) {
      try {
	jtaResume = new TmplJTextArea();
	jtaResume.setLineWrap(true);
	jtaResume.setField("resume");
	jtaResume.setLabel(res.getString("regPanel.label.resume"));
	jtaResume.setWrapStyleWord(true);
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jtaResume;
  }

  private TmplJLabel getJlblVigorDate() {
    jlblComeIntoForceDate = new TmplJLabel();
    jlblComeIntoForceDate.setText(res.getString("regPanel.label.forceDate"));

    return jlblComeIntoForceDate;
  }

  /**
   * This method initializes tmplJDateField
   *
   * @return pt.inescporto.template.client.design.TmplJDateField
   */
  private TmplJDatePicker getJtfldComeIntoForceDate() {
    if (jtfldComeIntoForceDate == null) {
      try {
	jtfldComeIntoForceDate = new TmplJDatePicker();
	jtfldComeIntoForceDate.setField("comeIntoForceDate");
//	jtfldComeIntoForceDate.setLabel(res.getString("regPanel.label.forceDate"));
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jtfldComeIntoForceDate;
  }

  private TmplJLabel getJlblPublishDate() {
    jlblPublishDate = new TmplJLabel();
    jlblPublishDate.setText(res.getString("regPanel.label.publishDate"));

    return jlblPublishDate;
  }

  /**
   * This method initializes tmplJDateField
   *
   * @return pt.inescporto.template.client.design.TmplJDateField
   */
  private TmplJDatePicker getJtfldPublishDate() {
    if (jtfldPublishDate == null) {
      try {
	jtfldPublishDate = new TmplJDatePicker();
	jtfldPublishDate.setField("publishDate");
//	jtfldPublishDate.setLabel(res.getString("regPanel.label.publishDate"));
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jtfldPublishDate;
  }

  private TmplJLabel getJlblStartDate() {
    jlblStartDate = new TmplJLabel();
    jlblStartDate.setText(res.getString("regPanel.label.startDate"));

    return jlblStartDate;
  }

  /**
   * This method initializes tmplJDateField
   *
   * @return pt.inescporto.template.client.design.TmplJDateField
   */
  private TmplJDatePicker getJtfldStartDate() {
    if (jtfldStartDate == null) {
      try {
	jtfldStartDate = new TmplJDatePicker();
	jtfldStartDate.setField("startDate");
        jtfldStartDate.setHolder(getJlblStartDate());
//	jtfldStartDate.setLabel(res.getString("regPanel.label.startDate"));
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jtfldStartDate;
  }

  private TmplJLabel getJlblEndVigorDate() {
    jlblEndDate = new TmplJLabel();
    jlblEndDate.setText(res.getString("regPanel.label.endForceDate"));

    return jlblEndDate;
  }

  /**
   * This method initializes tmplJDateField
   *
   * @return pt.inescporto.template.client.design.TmplJDateField
   */
  private TmplJDatePicker getJtfldEndVigorDate() {
    if (jtfldEndDate == null) {
      try {
        jtfldEndDate = new TmplJDatePicker();
        jtfldEndDate.setField("endDate");
      }
      catch (java.lang.Throwable e) {
        e.printStackTrace();
      }
    }
    return jtfldEndDate;
  }

  private TmplJLabel getJlblDocumentName() {
    jlblDocumentName = new TmplJLabel();
    jlblDocumentName.setText(res.getString("regulatory.label.documentName"));

    return jlblDocumentName;
  }

  private TmplJTextField getJtfldDocumentName() {
    try {
      if (jtfldDocumentName == null) {
        jtfldDocumentName = new TmplJTextField();
        jtfldDocumentName.setField("documentName");
      }
    }
    catch (java.lang.Throwable e) {
      // TODO: Something
    }
    return jtfldDocumentName;
  }

  private JPanel getClassPanel() {
    JPanel jpnlClass = new JPanel(new BorderLayout());
    JScrollPane jsp = new JScrollPane();
    FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

    DataSource regulatoryHasClass = null;
    try {
      regulatoryHasClass = datasource.getDataSourceByName("RegulatoryHasClass");
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    FW_ColumnManager colManager = new FW_ColumnManager();
    TmplComboBoxEditor cbScope =new TmplComboBoxEditor("pt.inescporto.siasoft.asq.ejb.session.Scope", new Integer[] {new Integer(1), new Integer(0)});
    cbScope.setWatcherSubject(SyncronizerSubjects.scopeFORM);
    colManager.addColumnNode(new FW_ColumnNode(res.getString("regPanel.label.scope"),
                                               "scopeID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.Scope"),
                                               cbScope));
    TmplComboBoxEditor cbLegislation =new TmplComboBoxEditor("pt.inescporto.siasoft.asq.ejb.session.Legislation", new Integer[] {new Integer(1), new Integer(0)});
    cbLegislation.setWatcherSubject(SyncronizerSubjects.asqLEGISLATION);
    colManager.addColumnNode(new FW_ColumnNode(res.getString("regPanel.label.legislation"),
                                               "legislID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.Legislation"),
                                               cbLegislation));
    TmplComboBoxEditor cbTheme = new TmplComboBoxEditor("pt.inescporto.siasoft.asq.ejb.session.Theme", new Integer[] {new Integer(1), new Integer(0)});
    cbTheme.setWatcherSubject(SyncronizerSubjects.asqTHEME);
    colManager.addColumnNode(new FW_ColumnNode(res.getString("regPanel.label.theme"),
                                               "themeID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.Theme"),
                                               cbTheme));
    TmplComboBoxEditor cbTheme1 = new TmplComboBoxEditor("pt.inescporto.siasoft.asq.ejb.session.Theme1", new Integer[] {new Integer(2), new Integer(1)}, "themeID = ?", null);
    cbTheme1.setWatcherSubject(SyncronizerSubjects.asqTHEME);
    colManager.addColumnNode(new FW_ColumnNode(res.getString("regPanel.label.theme1"),
                                               "theme1ID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.Theme1", new Integer[] {new Integer(2), new Integer(3)}),
                                               cbTheme1));
    cbTheme.addCellEditorListener(cbTheme1);

    FW_JTable fwClassTable = new FW_JTable(regulatoryHasClass, null, colManager);
    navBarDetail.setFW_ComponentListener(fwClassTable);
    fwClassTable.setAsMaster(false);

    jsp.getViewport().add(fwClassTable);

    jpnlClass.add(jsp, BorderLayout.CENTER);
    jpnlClass.setBorder(BorderFactory.createLoweredBevelBorder());

    // this buttons will control the table
    navBarDetail.setActionListener(fwClassTable);

    fwCListener.addFWComponentListener(fwClassTable);

    jpnlClass.add(navBarDetail, BorderLayout.SOUTH);

    return jpnlClass;
  }
}

class ColorToggleButton extends FW_JToggleButtonThreeState implements ActionListener {
  private String[] labels = null;
  private Color[] colors = null;

  public ColorToggleButton() {
    super();
    this.addActionListener(this);
  }

  public void setLabels(String[] labels) {
    this.labels = labels;
  }

  public void setColors(Color[] colors) {
    this.colors = colors;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this) {
      if (isSelected()) {
	setText(labels[1]);
	setBackground(colors[1]);
      }
      else {
	setText(labels[0]);
	setBackground(colors[0]);
      }
    }
  }

  public void tmplRefresh(TemplateEvent e) {
    super.tmplRefresh(e);

    if (link != null && ((TplBoolean)link).getValue() != null) {
      setSelected(((TplBoolean)link).getValue().booleanValue());
      setBackground(((TplBoolean)link).getValue().booleanValue() ? colors[1] : colors[0]);
      setText(((TplBoolean)link).getValue().booleanValue() ? labels[1] : labels[0]);
    }
  }
}
