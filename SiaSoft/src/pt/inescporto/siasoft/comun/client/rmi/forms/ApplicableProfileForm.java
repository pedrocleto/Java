package pt.inescporto.siasoft.comun.client.rmi.forms;

import pt.inescporto.template.client.rmi.TmplBasicInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import pt.inescporto.siasoft.comun.ejb.session.UserProfiles;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.util.Collection;
import java.util.Iterator;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import javax.swing.DefaultListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.factories.ButtonBarFactory;
import pt.inescporto.siasoft.comun.client.UserData;
import pt.inescporto.siasoft.comun.ejb.session.User;
import pt.inescporto.siasoft.comun.ejb.dao.UserDao;
import java.util.ResourceBundle;
import java.awt.Font;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.event.EventSynchronizer;
import pt.inescporto.siasoft.events.SyncronizerSubjects;


public class ApplicableProfileForm extends TmplBasicInternalFrame implements ActionListener {

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  private JPanel jpnlContent = null;
  private JPanel jpnlTables = null;
  private TmplJButton jbtnAdd = null;
  private TmplJButton jbtnRemove = null;
  private JScrollPane jspProfilesList = null;
  private JScrollPane jspProfileSelected = null;
  private JList jlstProfilesList = null;
  private JList jlstProfileSelected = null;
  private UserProfileDao savedProfile = null;
  private String savedEnterpriseForUser = null;

  /**
   * This method initializes
   *
   */
  public ApplicableProfileForm() {
    super();

    setPermFormId("SEL_PROFILE");

    init();
    start();
  }

  public void init() {
    super.init();

    initialize();
  }

  /**
   * This method initializes this
   *
   */
  private void initialize() {
    savedProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
    savedEnterpriseForUser = MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser();
    try {
      setContentPane(getJpnlContent());
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
  private JPanel getJpnlContent() {
    if (jpnlContent == null) {
      try {
	jpnlContent = new JPanel();
	jpnlContent.setLayout(new BorderLayout());
	jpnlContent.add(getJpnlTables(), java.awt.BorderLayout.CENTER);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jpnlContent;
  }

  /**
   * This method initializes jPanel
   *
   * @return javax.swing.JPanel
   */
  private JPanel getJpnlTables() {
    if (jpnlTables == null) {
      try {
        FormLayout layout = new FormLayout(
                "fill:default:grow,3dlu, 25dlu,3dlu, fill:default:grow",
                "p,pref:grow,pref:grow,3dlu,p");
        layout.setColumnGroups(new int[][] {{1, 5}});
        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        builder.addLabel(res.getString("applProfile.label.profile"), cc.xy(1, 1));
        builder.addLabel(res.getString("applProfile.label.actProfile"), cc.xy(5, 1));

        builder.add(getJScrollPane(), cc.xywh(1, 2, 1, 2));

        builder.add(new JPanel().add(getJButton()), cc.xy(3, 2, CellConstraints.FILL, CellConstraints.CENTER));
        builder.add(new JPanel().add(getRemoveButton()), cc.xy(3, 3, CellConstraints.FILL, CellConstraints.CENTER));

        builder.add(getJspProfileSelected(), cc.xywh(5, 2, 1, 2));

        JButton jbtnOk = new JButton("OK");
        jbtnOk.setFont(new Font("Dialog", Font.PLAIN, 10));
        jbtnOk.setActionCommand("OK");
        jbtnOk.addActionListener(this);

        JButton jbtnCancel = new JButton(res.getString("label.cancel"));
        jbtnCancel.setFont(new Font("Dialog", Font.PLAIN, 10));
        jbtnCancel.setActionCommand("CANCEL");
        jbtnCancel.addActionListener(this);
        builder.add(new JPanel().add(ButtonBarFactory.buildOKCancelBar(jbtnOk, jbtnCancel)), cc.xyw(1, 5, 5,CellConstraints.CENTER, CellConstraints.CENTER));

        jpnlTables = builder.getPanel();
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jpnlTables;
  }

  /**
   * This method initializes jScrollPane
   *
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getJScrollPane() {
    if (jspProfilesList == null) {
      try {
	jspProfilesList = new JScrollPane();
	jspProfilesList.setViewportView(getProfilesList());
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jspProfilesList;
  }

  /**
   * This method initializes jButton
   *
   * @return javax.swing.JButton
   */
  private TmplJButton getJButton() {
    if (jbtnAdd == null) {
      try {
	jbtnAdd = new TmplJButton("Add");
        jbtnAdd.setActionCommand(">>");
        jbtnAdd.addActionListener(this);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jbtnAdd;
  }

  /**
   * This method initializes jScrollPane1
   *
   * @return javax.swing.JScrollPane
   */
  private JScrollPane getJspProfileSelected() {
    if (jspProfileSelected == null) {
      try {
	jspProfileSelected = new JScrollPane();
	jspProfileSelected.setViewportView(getProfileSelectedList());
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jspProfileSelected;
  }

  /**
   * This method initializes jButton1
   *
   * @return javax.swing.JButton
   */
  private TmplJButton getRemoveButton() {
    if (jbtnRemove == null) {
      try {
	jbtnRemove = new TmplJButton("Remove");
        jbtnRemove.setActionCommand("<<");
        jbtnRemove.addActionListener(this);
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jbtnRemove;
  }

  /**
   * This method initializes jList
   *
   * @return javax.swing.JList
   */
  private JList getProfilesList() {
    if (jlstProfilesList == null) {
      try {
	jlstProfilesList = new JList(new DefaultListModel());
        jlstProfilesList.setFont(new Font("Dialog", Font.PLAIN, 10));
        User users = (User)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.comun.ejb.session.User");
        UserProfiles userProfiles = (UserProfiles)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.comun.ejb.session.UserProfiles");
        userProfiles.setLinkCondition(null, null);
        Iterator it = userProfiles.listAll().iterator();
        while (it.hasNext()) {
          UserProfileDao profile = (UserProfileDao)it.next();
          if (profile.userID.getValue().equals(MenuSingleton.getName()) || ((profile.permissions.getValue().intValue() & UserProfileDao.PERM_READ) > 0)) {
	    //get enterprise for this user profile
            UserDao userDao = new UserDao(profile.userID.getValue());
            users.findByPrimaryKey(userDao);
            userDao = users.getData();

            // add node to the list
            ProfileNode pNode = new ProfileNode(userDao.enterprise.getValue(), profile);
            ((DefaultListModel)jlstProfilesList.getModel()).addElement(pNode);
          }
        }
      }
      catch (java.lang.Throwable e) {
	e.printStackTrace();
      }
    }
    return jlstProfilesList;
  }

  /**
   * This method initializes jList1
   *
   * @return javax.swing.JList
   */
  private JList getProfileSelectedList() {
    if (jlstProfileSelected == null) {
      try {
        jlstProfileSelected = new JList(new DefaultListModel());
        jlstProfileSelected.setFont(new Font("Dialog", Font.PLAIN, 10));
        UserProfileDao curProfile = MenuSingleton.getUserData().getApplicableProfileData().getProfile();
        if (curProfile != null) {
          ProfileNode pNode = new ProfileNode(MenuSingleton.getUserData().getApplicableProfileData().getEnterpriseForUser(), curProfile);
	  ((DefaultListModel)jlstProfileSelected.getModel()).addElement(pNode);
	}
      }
      catch (java.lang.Throwable e) {
	// TODO: Something
      }
    }
    return jlstProfileSelected;
  }

  public void actionPerformed(ActionEvent e) {
    UserData ud = MenuSingleton.getUserData();

    // remove selected profile
    if (e.getActionCommand().equals("<<")) {
      if (jlstProfileSelected.getSelectedIndex() == -1)
        showErrorMessage("Não existe nenhum perfil seleccionado");
      else {
	((DefaultListModel)jlstProfileSelected.getModel()).remove(jlstProfileSelected.getSelectedIndex());
        ud.getApplicableProfileData().setProfile(null, null);
      }
    }
    // set selected profile
    if (e.getActionCommand().equals(">>")) {
      if (jlstProfilesList.getSelectedIndex() == -1)
        showErrorMessage("Não existe nenhum perfil seleccionado");
      else {
        if (!((DefaultListModel)jlstProfileSelected.getModel()).contains(jlstProfilesList.getSelectedValue())) {
          ((DefaultListModel)jlstProfileSelected.getModel()).removeAllElements();
	  ((DefaultListModel)jlstProfileSelected.getModel()).addElement(jlstProfilesList.getSelectedValue());
          ud.getApplicableProfileData().setProfile(((ProfileNode)jlstProfilesList.getSelectedValue()).enterpriseForUserProfile, ((ProfileNode)jlstProfilesList.getSelectedValue()).getUserProfileDao());
        }
        else
          jlstProfileSelected.setSelectedValue(jlstProfilesList.getSelectedValue(), true);
      }
    }
    if (e.getActionCommand().equals("OK")) {
      EventSynchronizer.getInstance().triggerEvent(SyncronizerSubjects.sysChangeProfile);
      dispose();
    }
    if (e.getActionCommand().equals("CANCEL")) {
      MenuSingleton.getUserData().getApplicableProfileData().setProfile(savedEnterpriseForUser, savedProfile);
      EventSynchronizer.getInstance().triggerEvent(SyncronizerSubjects.sysChangeProfile);
      dispose();
    }
  }

}

class ProfileNode {
  String enterpriseForUserProfile = null;
  UserProfileDao userProfile = null;

  public ProfileNode(String enterpriseForUserProfile, UserProfileDao userProfile) {
    this.enterpriseForUserProfile = enterpriseForUserProfile;
    this.userProfile = userProfile;
  }

  public String getEnterpriseForUserProfile() {
    return enterpriseForUserProfile;
  }

  public UserProfileDao getUserProfileDao() {
    return this.userProfile;
  }

  public String toString() {
    return userProfile.toString() + "(" + enterpriseForUserProfile + ")";
  }
}
