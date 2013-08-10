package pt.inescporto.siasoft.asq.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JList;
import java.util.Vector;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;
import java.awt.Component;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.naming.NamingException;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.event.CellEditorListener;
import java.util.EventObject;
import javax.swing.event.ChangeEvent;

import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.asq.ejb.dao.LegalRequirementDao;
import pt.inescporto.template.client.design.TmplJTextArea;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.siasoft.asq.ejb.dao.AsqClientApplicabilityDao;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.client.design.thirdparty.JListMutable;
import pt.inescporto.template.client.design.thirdparty.ListCellEditor;
import pt.inescporto.template.client.design.list.FW_JList;
import pt.inescporto.template.client.design.list.FW_ListModel;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.asq.ejb.dao.AsqApplyRegulatoryDao;
import pt.inescporto.template.client.rmi.FW_JPanelBasic;
import pt.inescporto.template.client.design.FW_JToggleButtonThreeState;
import pt.inescporto.template.elements.TplBoolean;
import java.awt.Graphics2D;
import java.awt.Graphics;
import pt.inescporto.template.client.design.thirdparty.State;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pt.inescporto.template.client.design.TmplJButton;
import javax.swing.ImageIcon;
import pt.inescporto.template.client.design.TmplLookupButton;

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
public class ClientLegalRequirementPanel extends FW_JPanelBasic {
  private DataSource datasource = null;
  private FW_ComponentListener fwCListener = null;
  private boolean bEditable = false;
  private TreeRefreshListener trListener = null;

  JPanel jpnlRegDesc = new JPanel(null);
  public FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  TmplJTextField jtfldRegulatoryDescription = new TmplJTextField() {
    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  JScrollPane jspLegalRequirementList = new JScrollPane();
  FW_JList jlstLegalRequirment = null;

  public ClientLegalRequirementPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ClientLegalRequirementPanel(DataSource datasource, FW_ComponentListener fwCListener, boolean bEditable, TreeRefreshListener trListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    this.bEditable = bEditable;
    this.trListener = trListener;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    fwCListener.addFWComponentListener(this);
    fwCListener.addFWComponentListener(jtfldRegulatoryDescription);

    datasource.addDatasourceListener(jtfldRegulatoryDescription);

    setAccessPermitionIdentifier("REG_CLIENT_LR");
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());

    JPanel jpnl = new JPanel(new BorderLayout());

    jtfldRegulatoryDescription.setField("name");
    jtfldRegulatoryDescription.setBounds(0, 5, 500, 21);
    jpnlRegDesc.add(jtfldRegulatoryDescription);
    jpnlRegDesc.setMinimumSize(new Dimension(500, 30));
    jpnlRegDesc.setPreferredSize(new Dimension(500, 30));

    jlstLegalRequirment = new FW_JList(datasource.getDataSourceByName("LegalRequirement"),
				       new FW_ListModel(datasource.getDataSourceByName("LegalRequirement"))) {
      protected void doUpdate() {
	if (getSelectedIndex() != -1) {
	  if (((LegalRequirementDao)lm.getAttrsAt(getSelectedIndex())).supplierLock.getValue().booleanValue() && !MenuSingleton.isSupplier()) {
	    showErrorMessage("Não possui autorização para efectuar a operação!");
	  }
	  else
	    super.doUpdate();
	}
	else
	  super.doUpdate();
      }

      protected void doDelete() {
	if (getSelectedIndex() != -1) {
	  if (getSelectedIndex() != -1) {
	    if (((LegalRequirementDao)lm.getAttrsAt(getSelectedIndex())).supplierLock.getValue().booleanValue() && !MenuSingleton.isSupplier()) {
	      showErrorMessage("Não possui autorização para efectuar a operação!");
	    }
	    else
	      super.doDelete();
	  }
	}
	else
	  super.doDelete();
      }

      protected void doInsertAfter(Object attrs) {
        if (trListener != null) {
	  LegalRequirementDao lrDao = (LegalRequirementDao)attrs;
	  trListener.refreshTree(lrDao.regId.getValue(), lrDao.legalReqId.getValue(), TmplFormModes.MODE_INSERT);
	}
      }

      protected void doUpdateAfter(Object attrs) {
        if (trListener != null) {
	  LegalRequirementDao lrDao = (LegalRequirementDao)attrs;
	  trListener.refreshTree(lrDao.regId.getValue(), lrDao.legalReqId.getValue(), TmplFormModes.MODE_UPDATE);
	}
      }

      protected void doDeleteAfter(Object attrs) {
        if (trListener != null) {
          LegalRequirementDao lrDao = (LegalRequirementDao)attrs;
          trListener.refreshTree(lrDao.regId.getValue(), lrDao.legalReqId.getValue(), TmplFormModes.MODE_DELETE);
        }
      }
    };
    jspLegalRequirementList.getViewport().add(jlstLegalRequirment);
    jlstLegalRequirment.setCellRenderer(new LRListCellRenderer());
    jlstLegalRequirment.setListCellEditor(new LRListCellEditor());
    jspLegalRequirementList.setMinimumSize(new Dimension(500, 30));
    jspLegalRequirementList.setPreferredSize(new Dimension(500, 30));
    jlstLegalRequirment.setAsMaster(false);

    navBarDetail.setActionListener(jlstLegalRequirment);
    navBarDetail.setFW_ComponentListener(jlstLegalRequirment);
    jlstLegalRequirment.start();

    jpnl.add(jpnlRegDesc, BorderLayout.NORTH);
    jpnl.add(jspLegalRequirementList, BorderLayout.CENTER);
    add(jpnl, BorderLayout.CENTER);
    if (bEditable)
      add(navBarDetail, BorderLayout.SOUTH);
  }
}

class LRListCellRenderer extends JPanel implements ListCellRenderer {
  BorderLayout borderLayout1 = new BorderLayout();
  GridBagLayout gbLayout = new GridBagLayout();
  JPanel jpnlHeader = new JPanel(gbLayout);
  JPanel jpnlApplicability = new JPanel();
  JPanel jpnlObs = new JPanel();
  TmplJLabel jlblObs = new TmplJLabel("Obs:");
  TmplJTextField jtfldDescription = new TmplJTextField();
  TmplJTextArea jtareaResume = new TmplJTextArea();
  TmplJTextArea jtareaObs = new TmplJTextArea();
  TmplJCheckBox jckboxAppDir = new TmplJCheckBox();
  TmplJCheckBox jckboxAppInd = new TmplJCheckBox();
  TmplJCheckBox jckboxAppInf = new TmplJCheckBox();
  ObsButton jckboxObs = new ObsButton();
  ColorToggleButton jtbtState = new ColorToggleButton();
  TmplJButton jbtnMoreObs = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/insert.png")));
  TmplJButton jbtnLessObs = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/delete.png")));

  AsqClientApplicability aca = null;

  public LRListCellRenderer() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    jtfldDescription.setText(((LegalRequirementDao)value).legalReqDescription.getValue());
    jtareaResume.setText(((LegalRequirementDao)value).legalReqObs.getValue());

    if (((FW_JList)list).getMode() != TmplFormModes.MODE_INSERT || ((LegalRequirementDao)value).legalReqId.getValue() != null) {
      // aplicability
      UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
      AsqClientApplicabilityDao acaDao = new AsqClientApplicabilityDao();
      acaDao.enterpriseID.setValue(MenuSingleton.getEnterprise());
      acaDao.regID.setValue(((LegalRequirementDao)value).regId.getValue());
      acaDao.legalRequirmentID.setValue(((LegalRequirementDao)value).legalReqId.getValue());

      if (curProfile != null) {
	acaDao.enterpriseID.setValue(MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser());
	acaDao.userID.setValue(curProfile.userID.getValue());
	acaDao.profileID.setValue(curProfile.profileID.getValue());

      }
      try {
	Vector<AsqClientApplicabilityDao> v = aca.findExactWithNulls(acaDao);
	jckboxAppDir.setSelected(v.elementAt(0).addDir.getValue().booleanValue());
	jckboxAppInd.setSelected(v.elementAt(0).addInd.getValue().booleanValue());
	jckboxAppInf.setSelected(v.elementAt(0).addInf.getValue().booleanValue());
	try {
	  if (v.elementAt(0).state.getValue() == true) {
	    jtbtState.setState(State.SELECTED);
	  }
	  else
	    if (v.elementAt(0).state.getValue() == false) {
	      jtbtState.setState(State.NOT_SELECTED);
	    }
	}
	catch (NullPointerException ex) {
	  jtbtState.setState(State.DONT_CARE);
	}
	jtareaObs.setText(v.elementAt(0).obs.getValue());
	if (!jtareaObs.getText().equals("")) {
	  jckboxObs.setSelected(true);
	}
	else {
	  jckboxObs.setSelected(false);
	}

	if (v.size() > 1)
	  System.err.println("PANIC! returned more than one legal requirement");
      }
      catch (RowNotFoundException ex) {
	jckboxAppDir.setSelected(false);
	jckboxAppInd.setSelected(false);
	jckboxAppInf.setSelected(false);
	jtbtState.setState(State.DONT_CARE);
	jckboxObs.setSelected(false);

      }
      catch (FinderException ex) {
      }
      catch (RemoteException ex) {
      }
    }
    else {
      jckboxAppDir.setSelected(false);
      jckboxAppInd.setSelected(false);
      jckboxAppInf.setSelected(false);
      jtbtState.setState(State.DONT_CARE);
      jckboxObs.setSelected(false);
    }

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
      jpnlHeader.setBackground(list.getSelectionBackground());
      jpnlHeader.setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
      jpnlHeader.setBackground(list.getBackground());
      jpnlHeader.setForeground(list.getForeground());
    }
    setOpaque(true);

    invalidate();
    return this;
  }

  private void jbInit() throws Exception {
    try {
      aca = (AsqClientApplicability)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability");
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }

    setBorder(new LineBorder(SystemColor.activeCaptionBorder));

    jtfldDescription.setOpaque(false);
    jtfldDescription.setPreferredSize(new Dimension(400, 21));

    jckboxAppDir.setOpaque(false);
    jckboxAppInd.setOpaque(false);
    jckboxAppInf.setOpaque(false);
    jckboxObs.setOpaque(false);
    jtbtState.setOpaque(false);

    jtareaResume.setEditable(false);
    jtareaResume.setLineWrap(true);
    jtareaResume.setWrapStyleWord(true);
    jtareaResume.setOpaque(false);
    jtareaResume.setBorder(BorderFactory.createLineBorder(Color.black));

    jtareaObs.setEditable(false);
    jtareaObs.setLineWrap(true);
    jtareaObs.setWrapStyleWord(true);
    jtareaObs.setOpaque(false);
    jtareaObs.setBorder(BorderFactory.createLineBorder(Color.black));

    JPanel test = new JPanel();
    test.add(new TmplJLabel("Dir."));
    test.add(new TmplJLabel("Ind."));
    test.add(new TmplJLabel("Inf."));
    test.add(new TmplJLabel("Obs."));
    test.add(new TmplJLabel("Est."));
    test.setOpaque(false);

    jpnlHeader.setOpaque(false);
    jpnlHeader.add(jtfldDescription, new GridBagConstraints(0, 1, 1, 1, 10.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 20, 0));
    jpnlHeader.add(test, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
    jtbtState.setPreferredSize(new Dimension(13, 13));

    jpnlApplicability.setOpaque(false);
    jpnlApplicability.add(jckboxAppDir);
    jpnlApplicability.add(jckboxAppInd);
    jpnlApplicability.add(jckboxAppInf);
    jpnlApplicability.add(jckboxObs);
    jpnlApplicability.add(jtbtState);
    jpnlHeader.add(jpnlApplicability, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

    setLayout(borderLayout1);
    add(jpnlHeader, java.awt.BorderLayout.NORTH);
    JScrollPane jsp = new JScrollPane(jtareaResume);
    jsp.setMaximumSize(new Dimension(100, 50));
    jsp.setPreferredSize(new Dimension(100, 50));

    JScrollPane jsp2 = new JScrollPane(jtareaObs);
    jsp2.setMaximumSize(new Dimension(100, 50));
    jsp2.setPreferredSize(new Dimension(100, 50));

    jpnlObs.setOpaque(false);
    jpnlObs.setLayout(new BorderLayout());
    jpnlObs.add(jlblObs, java.awt.BorderLayout.NORTH);
    jpnlObs.add(jsp2, java.awt.BorderLayout.CENTER);

    add(jsp, java.awt.BorderLayout.CENTER);
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setIgnoreRepaint(false);
    validate();
  }
}

class LRListCellEditor extends JPanel implements ListCellEditor, ActionListener {
  protected transient FW_JList fwList;
  protected transient Vector listeners;
  protected LegalRequirementDao originalValue = new LegalRequirementDao();

  BorderLayout borderLayout1 = new BorderLayout();
  TmplJTextField jtfldLRID = new TmplJTextField();
  TmplJTextField jtfldDescription = new TmplJTextField();
  TmplJTextArea jtareaResume = new TmplJTextArea();
  TmplJTextArea jtareaObs = new TmplJTextArea();
  GridBagLayout gbLayout = new GridBagLayout();
  JPanel jpnlHeader = new JPanel(gbLayout);
  JPanel jpnlObs = new JPanel();
  JScrollPane jsp = new JScrollPane();
  JScrollPane jsp2 = new JScrollPane();
  TmplJLabel jlblObs = new TmplJLabel("Obs:");
  JPanel jpnlApplicability = new JPanel();
  TmplJCheckBox jckboxAppDir = new TmplJCheckBox();
  TmplJCheckBox jckboxAppInd = new TmplJCheckBox();
  TmplJCheckBox jckboxAppInf = new TmplJCheckBox();
  ObsButton jckboxObs = new ObsButton();
  ColorToggleButton jtbtState = new ColorToggleButton();
  TmplJButton jbtnMoreObs = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/insert.png")));
  TmplJButton jbtnLessObs = new TmplJButton(new ImageIcon(TmplLookupButton.class.getResource("icons/delete.png")));

  AsqClientApplicability aca = null;

  public LRListCellEditor() {
    listeners = new Vector();

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Component getListCellEditorComponent(JList list, Object value, boolean isSelected, int index) {
    fwList = (FW_JList)list;
    jbtnMoreObs.setPreferredSize(new Dimension(20, 20));
    jbtnLessObs.setPreferredSize(new Dimension(20, 20));

    LegalRequirementDao convValue = (LegalRequirementDao)value;
    jtfldLRID.setText(convValue.legalReqId.getValue());
    if (fwList.getMode() == TmplFormModes.MODE_INSERT) {
      jtfldLRID.setEnabled(true);
    }
    if (fwList.getMode() == TmplFormModes.MODE_UPDATE) {
      jtfldLRID.setEnabled(false);
    }
    jtfldDescription.setText(convValue.legalReqDescription.getValue());
    jtareaResume.setText(convValue.legalReqObs.getValue());
    originalValue = convValue;

    if (((FW_JList)list).getMode() != TmplFormModes.MODE_INSERT || (((LegalRequirementDao)value).legalReqId.getValue() != null && ((LegalRequirementDao)value).legalReqId.getValue().equals(""))) {
      // aplicability
      UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
      AsqClientApplicabilityDao acaDao = new AsqClientApplicabilityDao();
      acaDao.enterpriseID.setValue(MenuSingleton.getEnterprise());
      acaDao.regID.setValue(((LegalRequirementDao)value).regId.getValue());
      acaDao.legalRequirmentID.setValue(((LegalRequirementDao)value).legalReqId.getValue());

      if (fwList.getMode() == TmplFormModes.MODE_UPDATE) {
	if (curProfile == null) {
	  fwList.showInformationMessage("Não existe nenhum perfíl seleccionado!\nA aplicabilidade fica indisponível.");
	  jckboxAppDir.setEnabled(false);
	  jckboxAppInd.setEnabled(false);
	  jckboxAppInf.setEnabled(false);
	  jbtnMoreObs.setEnabled(false);
	  jbtnLessObs.setEnabled(false);
	  jckboxObs.setEnabled(false);
	  jtbtState.setEnabled(false);
	}
	else {
	  jckboxAppDir.setEnabled(true);
	  jckboxAppInd.setEnabled(true);
	  jckboxAppInf.setEnabled(true);
	  jbtnMoreObs.setEnabled(true);
	  jbtnLessObs.setEnabled(true);
	  jckboxObs.setEnabled(false);
	  jtbtState.setEnabled(true);

          acaDao.enterpriseID.setValue(MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser());
          acaDao.userID.setValue(curProfile.userID.getValue());
          acaDao.profileID.setValue(curProfile.profileID.getValue());
	}
      }
      try {
	Vector<AsqClientApplicabilityDao> v = aca.findExactWithNulls(acaDao);
	jckboxAppDir.setSelected(v.elementAt(0).addDir.getValue().booleanValue());
        jckboxAppInd.setEnabled(v.elementAt(0).addDir.getValue().booleanValue());
	jckboxAppInd.setSelected(v.elementAt(0).addInd.getValue().booleanValue());
        jckboxAppInf.setEnabled(v.elementAt(0).addInd.getValue().booleanValue());
	jckboxAppInf.setSelected(v.elementAt(0).addInf.getValue().booleanValue());
	try {
	  if (v.elementAt(0).state.getValue() == true) {
	    jtbtState.setState(State.SELECTED);
	  }
	  else
	    if (v.elementAt(0).state.getValue() == false) {
	      jtbtState.setState(State.NOT_SELECTED);
	    }
	}
	catch (NullPointerException ex) {
	  jtbtState.setState(State.DONT_CARE);
	}

	jtareaObs.setText(v.elementAt(0).obs.getValue());
	if (!jtareaObs.getText().equals("")) {
	  jckboxObs.setSelected(true);
	}
	else {
	  jckboxObs.setSelected(false);
	}

	if (v.size() > 1)
	  System.err.println("PANIC! returned more than one legal requirement");
      }
      catch (RowNotFoundException ex) {
	jckboxAppDir.setSelected(false);
        jckboxAppInd.setEnabled(false);
	jckboxAppInd.setSelected(false);
        jckboxAppInf.setEnabled(false);
	jckboxAppInf.setSelected(false);
	jckboxObs.setSelected(false);
	jtbtState.setState(State.DONT_CARE);
      }
      catch (FinderException ex) {
      }
      catch (RemoteException ex) {
      }
    }
    else {
      jckboxAppDir.setSelected(false);
      jckboxAppInd.setSelected(false);
      jckboxAppInf.setSelected(false);
      jckboxObs.setSelected(false);
      jtbtState.setState(State.DONT_CARE);

      jckboxAppDir.setEnabled(false);
      jckboxAppInd.setEnabled(false);
      jckboxAppInf.setEnabled(false);
      jckboxObs.setEnabled(false);
      jtbtState.setEnabled(false);
      jbtnMoreObs.setEnabled(false);
      jtareaObs.setText("");
    }
    return this;
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("DIR")) {
      if (jckboxAppDir.isSelected()) {
	jckboxAppInd.setEnabled(true);
        jckboxAppInd.setSelected(false);
        jckboxAppInf.setEnabled(false);
        jckboxAppInf.setSelected(false);
      }
      else {
	jckboxAppInd.setEnabled(false);
	jckboxAppInd.setSelected(false);
        jckboxAppInf.setEnabled(false);
        jckboxAppInf.setSelected(false);
      }
    }
    if (e.getActionCommand().equals("IND")) {
      if (jckboxAppDir.isSelected()) {
        jckboxAppInf.setEnabled(true);
        jckboxAppInf.setSelected(false);
      }
      else {
	jckboxAppInf.setEnabled(false);
        jckboxAppInf.setSelected(false);
      }
    }
    if (e.getActionCommand().equals("MORE")) {
      jpnlHeader.remove(jbtnMoreObs);
      jpnlHeader.add(jbtnLessObs, new GridBagConstraints(1, 1, 1, 1, 30.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, -6, 0, 0), 0, 0));
      add(jpnlObs, java.awt.BorderLayout.SOUTH);
      jbtnMoreObs.setVisible(false);
      jbtnLessObs.setVisible(true);
      jbtnLessObs.setEnabled(true);
    }
    if (e.getActionCommand().equals("LESS")) {
      jpnlHeader.remove(jbtnLessObs);
      jpnlHeader.add(jbtnMoreObs, new GridBagConstraints(1, 1, 1, 1, 30.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, -6, 0, 0), 0, 0));
      remove(jpnlObs);
      jbtnLessObs.setVisible(false);
      jbtnMoreObs.setVisible(true);
    }
  }

  private void jbInit() throws Exception {
    try {
      aca = (AsqClientApplicability)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability");
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }

    setBorder(new LineBorder(SystemColor.activeCaptionBorder));

    jtfldDescription.setOpaque(true);
    jtfldDescription.setPreferredSize(new Dimension(400, 21));

    jckboxAppDir.setOpaque(false);
    jckboxAppInd.setOpaque(false);
    jckboxAppInf.setOpaque(false);
    jckboxObs.setOpaque(false);
    jtbtState.setOpaque(false);

    //
    jbtnLessObs.setVisible(false);
    jbtnMoreObs.setVisible(true);
    jbtnMoreObs.setEnabled(true);
    jbtnMoreObs.setActionCommand("MORE");
    jbtnLessObs.setActionCommand("LESS");

    jbtnMoreObs.addActionListener(this);
    jbtnLessObs.addActionListener(this);

    jckboxAppDir.setActionCommand("DIR");
    jckboxAppInd.setActionCommand("IND");
    jckboxAppInf.setActionCommand("INF");
    jckboxObs.setActionCommand("OBS");
    jckboxAppDir.addActionListener(this);
    jckboxAppInd.addActionListener(this);
    jckboxAppInf.addActionListener(this);
    jckboxObs.addActionListener(this);

    jtbtState.addActionListener(this);

    jtareaResume.setEditable(true);
    jtareaResume.setLineWrap(true);
    jtareaResume.setWrapStyleWord(true);
    jtareaResume.setOpaque(false);
    jtareaResume.setBorder(BorderFactory.createLineBorder(Color.black));

    jtareaObs.setEditable(true);
    jtareaObs.setLineWrap(true);
    jtareaObs.setWrapStyleWord(true);
    jtareaObs.setOpaque(false);
    jtareaObs.setBorder(BorderFactory.createLineBorder(Color.black));

    jpnlHeader.setOpaque(false);
    jpnlHeader.add(jtfldLRID, new GridBagConstraints(0, 0, 1, 1, 70.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 70, 0));
    jpnlHeader.add(jbtnMoreObs, new GridBagConstraints(1, 1, 1, 1, 30.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, -6, 0, 0), 0, 0));
    JPanel test = new JPanel();
    test.add(new TmplJLabel("Dir."));
    test.add(new TmplJLabel("Ind."));
    test.add(new TmplJLabel("Inf."));
    test.add(new TmplJLabel("Obs."));
    test.add(new TmplJLabel("Est."));
    test.setOpaque(false);

    jpnlHeader.add(test, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
    jpnlHeader.add(jtfldDescription, new GridBagConstraints(0, 1, 1, 1, 10.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 5), 20, 0));
    jtbtState.setPreferredSize(new Dimension(13, 13));
    jpnlApplicability.setOpaque(false);
    jpnlApplicability.add(jckboxAppDir);
    jpnlApplicability.add(jckboxAppInd);
    jpnlApplicability.add(jckboxAppInf);
    jpnlApplicability.add(jckboxObs);
    jpnlApplicability.add(jtbtState);
    jpnlHeader.add(jpnlApplicability, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));

    setLayout(borderLayout1);
    add(jpnlHeader, java.awt.BorderLayout.NORTH);

    jsp = new JScrollPane(jtareaResume);
    jsp.setMaximumSize(new Dimension(100, 50));
    jsp.setPreferredSize(new Dimension(100, 50));
    jsp2 = new JScrollPane(jtareaObs);
    jsp2.setMaximumSize(new Dimension(100, 50));
    jsp2.setPreferredSize(new Dimension(100, 50));

    jpnlObs.setOpaque(false);
    jpnlObs.setLayout(new BorderLayout());
    jpnlObs.add(jlblObs, java.awt.BorderLayout.WEST);
    jpnlObs.add(jsp2, java.awt.BorderLayout.CENTER);

    add(jsp, java.awt.BorderLayout.CENTER);
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    setIgnoreRepaint(false);
    validate();
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
  }

  public Object getCellEditorValue() {
    UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
    if ((curProfile != null || fwList.getMode() == TmplFormModes.MODE_UPDATE) && !jtfldLRID.getText().equals("")) {
      try {
	AsqClientApplicability aca = (AsqClientApplicability)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability");
	Vector<AsqApplyRegulatoryDao> regAppList = new Vector<AsqApplyRegulatoryDao>();
	AsqApplyRegulatoryDao aarDao = new AsqApplyRegulatoryDao();
	aarDao.enterprise = MenuSingleton.getEnterprise();
	aarDao.user = curProfile.userID.getValue();
	aarDao.profileUser = curProfile.profileID.getValue();
	aarDao.regulatory = originalValue.regId.getValue();
	aarDao.legalRequirement = jtfldLRID.getText();
	int app = (jckboxAppDir.isSelected() ? UserProfileDao.DIRECT_APP : 0) + (jckboxAppInd.isSelected() ? UserProfileDao.INDIRECT_APP : 0) +
	    (jckboxAppInf.isSelected() ? UserProfileDao.INFORM_APP : 0);
	aarDao.app = new Integer(app);
	if (jtbtState.getState() == State.SELECTED) {
	  aarDao.state = 1;
	}
	else
	  if (jtbtState.getState() == State.NOT_SELECTED) {
	    aarDao.state = 2;
	  }
	  else
	    if (jtbtState.getState() == State.DONT_CARE) {
	      aarDao.state = 3;
	    }

	aarDao.obs = jtareaObs.getText();

	regAppList.add(aarDao);
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
      catch (NullPointerException ex) {
	JFrame frame = new JFrame();
	JOptionPane.showMessageDialog(frame,
				      "Sem Perfil Activo",
				      "Aviso",
				      JOptionPane.WARNING_MESSAGE);
      }
    }

    originalValue.legalReqId.setValue(jtfldLRID.getText());
    originalValue.legalReqDescription.setValue(jtfldDescription.getText());
    originalValue.legalReqObs.setValue(jtareaResume.getText());
    if (originalValue.supplierLock.getValue() == null)
      originalValue.supplierLock.setValue(MenuSingleton.isSupplier());
    return originalValue.legalReqId.getValue() != null ? originalValue : null;
  }

  public boolean isCellEditable(EventObject eo) {
    return true;
  }

  public boolean shouldSelectCell(EventObject eo) {
    return true;
  }

  public boolean stopCellEditing() {
    fireEditingStopped();
    return true;
  }

  public void addCellEditorListener(CellEditorListener cel) {
    listeners.addElement(cel);
  }

  public void removeCellEditorListener(CellEditorListener cel) {
    listeners.removeElement(cel);
  }

  protected void fireEditingCanceled() {
    jtfldLRID.setText(originalValue.legalReqId.getValue());
    jtfldDescription.setText(originalValue.legalReqDescription.getValue());
    jtareaResume.setText(originalValue.legalReqObs.getValue());
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size(); i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);
    }
  }

  protected void fireEditingStopped() {
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
    }
  }
}

class ColorToggleButton extends FW_JToggleButtonThreeState implements ActionListener {
  public ColorToggleButton() {
    super();
    this.addActionListener(this);
  }

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = (Graphics2D)g;
    if (getState() == State.DONT_CARE) {
      //g2.setColor(Color.white);
      g2.fillRect(0, 0, getSize().width, getSize().height);
    }
    else
      if (getState() == State.NOT_SELECTED) {
	Line2D line = new Line2D.Double(getSize().width - 3, getSize().height - 3, 3, 3);
	g2.setColor(Color.red);
	g2.setStroke(new BasicStroke(2));
	g2.draw(line);
	Line2D line2 = new Line2D.Double(getSize().width - 3, 3, 3, getSize().height - 3);
	g2.setColor(Color.red);
	g2.setStroke(new BasicStroke(2));
	g2.draw(line2);
      }
      else
	if (getState() == State.SELECTED) {
	  Line2D line = new Line2D.Double(getSize().width - 8, getSize().height - 4, 3, 5);
	  g2.setColor(Color.green);
	  g2.setStroke(new BasicStroke(2));
	  g2.draw(line);
	  Line2D line2 = new Line2D.Double(getSize().width, 0, 5, getSize().height - 4);
	  g2.setColor(Color.green);
	  g2.setStroke(new BasicStroke(2));
	  g2.draw(line2);

	}
  }

  public void actionPerformed(ActionEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
    super.tmplRefresh(e);
  }
}

class ObsButton extends TmplJCheckBox implements ActionListener {
  public ObsButton() {
    super();
    this.addActionListener(this);
  }

  public void paint(Graphics g) {
    super.paint(g);

    Graphics2D g2 = (Graphics2D)g;
    if (isSelected()) {
      g2.setColor(getBackground());
      g2.fillRect(6, 6, getSize().width - 12, getSize().height - 12);

      Line2D line = new Line2D.Double(getSize().width - 5, 6, 5, 6);
      g2.setColor(Color.black);
      g2.setStroke(new BasicStroke(1));
      g2.draw(line);

      Line2D line2 = new Line2D.Double(getSize().width - 5, 8, 5, 8);
      g2.setColor(Color.black);
      g2.setStroke(new BasicStroke(1));
      g2.draw(line2);

      Line2D line3 = new Line2D.Double(getSize().width - 5, 10, 5, 10);
      g2.setColor(Color.black);
      g2.setStroke(new BasicStroke(1));
      g2.draw(line3);

      Line2D line4 = new Line2D.Double(getSize().width - 5, 12, 5, 12);
      g2.setColor(Color.black);
      g2.setStroke(new BasicStroke(1));
      g2.draw(line4);

      Line2D line5 = new Line2D.Double(getSize().width - 5, 14, 5, 14);
      g2.setColor(Color.black);
      g2.setStroke(new BasicStroke(1));
      g2.draw(line5);
    }

  }

  public void actionPerformed(ActionEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
    super.tmplRefresh(e);
  }
}

class AL implements ActionListener {
  JPanel jPanel = null;
  boolean toggle = false;

  public AL(JPanel jPanel) {
    this.jPanel = jPanel;
  }

  public void actionPerformed(ActionEvent e) {
    toggle = !toggle;
    jPanel.setVisible(toggle);
  }
}
