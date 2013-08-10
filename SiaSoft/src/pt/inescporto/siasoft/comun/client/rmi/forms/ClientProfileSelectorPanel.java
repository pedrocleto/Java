package pt.inescporto.siasoft.comun.client.rmi.forms;

import java.util.Iterator;
import java.util.ResourceBundle;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.comun.ejb.dao.UserDao;
import pt.inescporto.siasoft.comun.ejb.session.UserProfiles;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import pt.inescporto.siasoft.comun.ejb.session.User;
import pt.inescporto.siasoft.comun.client.UserData;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class ClientProfileSelectorPanel extends JPanel implements ActionListener {
  ApplyProfileInterface api = null;
  TmplJButton jbtnNew = new TmplJButton();

  // header panel
  JPanel jpnlHeader = new JPanel();
  TmplJLabel jlblHeader = new TmplJLabel("Gestão de Perfis");

  // footer panel
  JPanel jpnlFooter = new JPanel();
  TmplJButton jbtnSelectAll = new TmplJButton();
  TmplJButton jbtnClearSelection = new TmplJButton();
  TmplJButton jbtnDoit = new TmplJButton();

  // list panel
  JPanel jpnlList = new JPanel(new BorderLayout());
  JScrollPane jspList = new JScrollPane();

  private JList jlstProfilesList = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.comun.client.rmi.forms.FormResources");

  public ClientProfileSelectorPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());

    // header panel
    jpnlHeader.add(jlblHeader);

    // footer panel
    jbtnClearSelection.setText(res.getString("cliProfSelector.label.deactivate"));
    jbtnClearSelection.setActionCommand("CLEAN");
    jbtnClearSelection.addActionListener(this);

    jbtnDoit.setText(res.getString("cliProfSelector.label.activate"));
    jbtnDoit.setActionCommand("APPLY");
    jbtnDoit.addActionListener(this);

    jpnlFooter.add(jbtnDoit);
    jpnlFooter.add(jbtnClearSelection);


    jspList.getViewport().add(getProfilesList());
    jspList.setBorder(BorderFactory.createEtchedBorder());
    jpnlList.add(jspList, BorderLayout.CENTER);
    jpnlList.add(jpnlHeader, BorderLayout.NORTH);
    jpnlList.add(jpnlFooter, BorderLayout.SOUTH);

    // add panels
    add(jpnlList, BorderLayout.CENTER);
  }

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

  public void actionPerformed(ActionEvent e) {
    UserData ud = MenuSingleton.getUserData();
    if (e.getActionCommand().equals("APPLY")) {
      if (jlstProfilesList.getSelectedIndex() == -1){
	JFrame frame = new JFrame();
	JOptionPane.showMessageDialog(frame,
				      "Não existe nenhum perfil seleccionado",
				      "Aviso",
				      JOptionPane.WARNING_MESSAGE);
      }

      else {
        ud.getApplicableProfileData().setProfile(((ProfileNode)jlstProfilesList.getSelectedValue()).enterpriseForUserProfile, ((ProfileNode)jlstProfilesList.getSelectedValue()).getUserProfileDao());
	}
    }
    if (e.getActionCommand().equals("CLEAN")) {
      ud.getApplicableProfileData().setProfile(null, null);
    }
  }
}
