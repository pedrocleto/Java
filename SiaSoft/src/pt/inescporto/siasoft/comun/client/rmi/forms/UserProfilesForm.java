package pt.inescporto.siasoft.comun.client.rmi.forms;

import pt.inescporto.template.client.rmi.TmplInternalFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import pt.inescporto.template.client.design.TmplJLabel;
import java.awt.GridBagConstraints;
import pt.inescporto.template.client.design.TmplJTextField;
import java.awt.FlowLayout;
import pt.inescporto.template.client.design.TmplJCheckBoxBit;
import pt.inescporto.template.client.design.TmplButtonGroupBit;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import java.util.ResourceBundle;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.MenuSingleton;

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
public class UserProfilesForm extends TmplInternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  private JPanel jPanel = null;
  private TmplJTextField jtfldUserId = null;
  private TmplJLabel jlblProfileId = null;
  private TmplJTextField jtfldProfileId = null;
  private TmplJLabel jlblProfileDesc = null;
  private TmplJTextField jtfldProfileDesc = null;
  private TmplJLabel jlblUser = null;
  private TmplJLabel jlblPermissions = null;
  private JPanel jPanel2 = null;
  private TmplJCheckBoxBit jchkbRead = null;
  private TmplButtonGroupBit btngrpPerm = null;

  public UserProfilesForm() {
    super();

    url = "pt.inescporto.siasoft.comun.ejb.session.UserProfiles";
    setPermFormId("PROFILE");

    if (!MenuSingleton.isSupplier())
      staticLinkCondition = "userID = '" + MenuSingleton.getName() + "'";

    init();
    start();
  }

  public void init() {
    super.init();

    allHeaders = new String[] {res.getString("user.label.code"), res.getString("userProfile.label.code"), res.getString("userProfile.label.desc"), res.getString("userProfile.label.applicability"), res.getString("userProfile.label.perm")};

    initialize();

    addTemplateListener(jtfldUserId);
    addTemplateListener(jtfldProfileId);
    addTemplateListener(jtfldProfileDesc);
    addTemplateListener(btngrpPerm);

    addTemplateComponentListener(jtfldUserId);
    addTemplateComponentListener(jtfldProfileId);
    addTemplateComponentListener(jtfldProfileDesc);
    addTemplateComponentListener(btngrpPerm);
  }
  /**
   * This method initializes this
   *
   */
  private void initialize() {
    try {
      this.setSize(new java.awt.Dimension(474, 327));
      add(getJPanel(), BorderLayout.CENTER);
    }
    catch (java.lang.Throwable e) {
      //  Do Something
    }
  }

  /**
   * This method initializes jPanel
   *
   * @return javax.swing.JPanel
   */
  private JPanel getJPanel() {
    jtfldUserId = new TmplJTextField();
    jtfldUserId.setField("userID");

    if (jPanel == null) {
      try {
	GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
	gridBagConstraints9.gridx = 1;
	gridBagConstraints9.fill = java.awt.GridBagConstraints.HORIZONTAL;
	gridBagConstraints9.gridy = 3;
	GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
	gridBagConstraints7.gridx = 0;
	gridBagConstraints7.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints7.gridy = 3;
	jlblPermissions = new TmplJLabel();
	jlblPermissions.setText(res.getString("userProfile.label.perm"));
	GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
	gridBagConstraints6.gridx = 0;
	gridBagConstraints6.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints6.gridy = 2;
	jlblUser = new TmplJLabel();
	jlblUser.setText(res.getString("userProfile.label.user"));
	GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
	gridBagConstraints5.gridx = 1;
	gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
	gridBagConstraints5.gridy = 2;
	GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
	gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
	gridBagConstraints3.gridy = 1;
	gridBagConstraints3.weightx = 1.0D;
	gridBagConstraints3.gridwidth = 1;
	gridBagConstraints3.gridx = 1;
	GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
	gridBagConstraints2.gridx = 0;
	gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints2.gridy = 1;
	jlblProfileDesc = new TmplJLabel();
	jlblProfileDesc.setText(res.getString("userProfile.label.desc"));
	GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
	gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
	gridBagConstraints1.gridy = 0;
	gridBagConstraints1.weightx = 0.0D;
	gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints1.gridwidth = 1;
	gridBagConstraints1.gridx = 1;
	GridBagConstraints gridBagConstraints = new GridBagConstraints();
	gridBagConstraints.gridx = 0;
	gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
	gridBagConstraints.gridy = 0;
	jlblProfileId = new TmplJLabel();
	jlblProfileId.setText(res.getString("userProfile.label.code"));
	jPanel = new JPanel();
	jPanel.setLayout(new GridBagLayout());
	jPanel.add(jlblProfileId, gridBagConstraints);
	jPanel.add(jlblProfileDesc, gridBagConstraints2);
	jPanel.add(getJtfldProfileDesc(), gridBagConstraints3);
	jPanel.add(getJtfldProfileId(), gridBagConstraints1);
        if (MenuSingleton.isSupplier()) {
	  jPanel.add(jlblUser, gridBagConstraints6);
          jPanel.add(jtfldUserId, gridBagConstraints5);
	}
	jPanel.add(jlblPermissions, gridBagConstraints7);
	jPanel.add(getJPanel2(), gridBagConstraints9);

        btngrpPerm = new TmplButtonGroupBit();
        btngrpPerm.setField("permissions");
        btngrpPerm.add(jchkbRead);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jPanel;
  }

  /**
   * This method initializes tmplJTextField
   *
   * @return pt.inescporto.template.client.design.TmplJTextField
   */
  private TmplJTextField getJtfldProfileId() {
    if (jtfldProfileId == null) {
      try {
	jtfldProfileId = new TmplJTextField();
	jtfldProfileId.setField("profileID");
	jtfldProfileId.setHolder(jlblProfileId);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jtfldProfileId;
  }

  /**
   * This method initializes tmplJTextField
   *
   * @return pt.inescporto.template.client.design.TmplJTextField
   */
  private TmplJTextField getJtfldProfileDesc() {
    if (jtfldProfileDesc == null) {
      try {
	jtfldProfileDesc = new TmplJTextField();
	jtfldProfileDesc.setField("profileDesc");
	jtfldProfileDesc.setHolder(jlblProfileDesc);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jtfldProfileDesc;
  }

  /**
   * This method initializes jPanel2
   *
   * @return javax.swing.JPanel
   */
  private JPanel getJPanel2() {
    if (jPanel2 == null) {
      try {
	FlowLayout flowLayout1 = new FlowLayout();
	flowLayout1.setAlignment(java.awt.FlowLayout.LEFT);
	jPanel2 = new JPanel();
	jPanel2.setLayout(flowLayout1);
	jPanel2.add(getJchkbRead(), null);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jPanel2;
  }

  /**
   * This method initializes TmplJCheckBoxBit
   *
   * @return pt.inescporto.template.client.design.TmplJCheckBoxBit
   */
  private TmplJCheckBoxBit getJchkbRead() {
    if (jchkbRead == null) {
      try {
	jchkbRead = new TmplJCheckBoxBit();
	jchkbRead.setText(res.getString("userProfile.label.read"));
        jchkbRead.setBit(UserProfileDao.PERM_READ);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jchkbRead;
  }

  protected void doSave() {
    jtfldUserId.setText(MenuSingleton.getName());
    super.doSave();
  }
} //  @jve:decl-index=0:visual-constraint="10,10"
