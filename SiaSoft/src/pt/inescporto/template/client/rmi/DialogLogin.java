package pt.inescporto.template.client.rmi;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import pt.inescporto.template.ejb.session.SessionControl;
import pt.inescporto.template.rmi.beans.LoggedUser;
import pt.inescporto.template.client.design.TmplResourceSingleton;

/**
 * <p>Title: EUROShoE</p>
 * <p>Description: Extended User Oriented Shoe Enterprise</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: INESC Porto</p>
 * @author jap
 * @version 1.0
 */

public class DialogLogin extends JDialog {
  public LoggedUser userInfo = null;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel jlblPassword = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPasswordField jtfldPassword = new JPasswordField();
  JButton jbtnCancel = new JButton();
  JLabel jlblUser = new JLabel();
  JTextField jtfldUser = new JTextField();
  JPanel pnlBase = new JPanel();
  JButton jbtnOk = new JButton();
  JLabel image = new JLabel();
  SessionControl control = null;
  String sessionType = null;

  public DialogLogin(Frame frame, String title, boolean modal, String sessionType) {
    super(frame, title, modal);

    this.sessionType = sessionType;

    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }

    try {
      control = (SessionControl)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.template.ejb.session.SessionControl");
    }
    catch( Exception ex ) {
      JOptionPane.showMessageDialog(this,
                                    TmplResourceSingleton.getString("error.msg.connection"),
                                    TmplResourceSingleton.getString("error.dialog.header"),
                                    JOptionPane.ERROR_MESSAGE);
      /**
       * @todo correct the form of disposing other then exit!!!
       */
      JOptionPane.showMessageDialog(this,
                                    ex.toString(),
                                    TmplResourceSingleton.getString("error.dialog.header"),
                                    JOptionPane.ERROR_MESSAGE);
      System.exit(-1);
    }

    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
  }

  private void jbInit() throws Exception {
    jPanel1.setLayout(borderLayout1);
    jlblPassword.setText(TmplResourceSingleton.getString("label.password"));
    jtfldPassword.setText("");
    jbtnCancel.setText(TmplResourceSingleton.getString("button.cancel"));
    jbtnCancel.addActionListener(new DialogLogin_jbtnCancel_actionAdapter(this));
    jlblUser.setText(TmplResourceSingleton.getString("label.user"));
    jtfldUser.setText("");
    pnlBase.setLayout(gridBagLayout1);
    jbtnOk.setText(TmplResourceSingleton.getString("button.ok"));
    jbtnOk.addActionListener(new DialogLogin_jbtnOk_actionAdapter(this));
    this.setDefaultCloseOperation(3);
    image.setBorder(BorderFactory.createLoweredBevelBorder());
    image.setDisplayedMnemonic('0');
    image.setIcon(new ImageIcon(MenuRMI.class.getResource("SIAlogo.png")));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(pnlBase, BorderLayout.CENTER);
    pnlBase.add(jlblUser, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    pnlBase.add(jtfldUser, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 100, 0));
    pnlBase.add(jlblPassword, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(3, 3, 3, 3), 0, 0));
    pnlBase.add(jbtnOk, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 10, 0), 0, 0));
    pnlBase.add(jbtnCancel, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    pnlBase.add(jtfldPassword, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(image,  BorderLayout.WEST);
  }

  void jbtnOk_actionPerformed(ActionEvent e) {
    try {
      userInfo = control.logginUser(sessionType, jtfldUser.getText(), new String(jtfldPassword.getPassword()));
      if( userInfo != null ) {
        this.dispose();
      }
      else {
        JOptionPane.showMessageDialog(this,
                                      TmplResourceSingleton.getString("error.msg.login"),
                                      TmplResourceSingleton.getString("error.dialog.header"),
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    catch( Exception ex ) {
      ex.printStackTrace();
    }
  }

  void jbtnCancel_actionPerformed(ActionEvent e) {
    this.dispose();
  }
}

class DialogLogin_jbtnOk_actionAdapter implements java.awt.event.ActionListener {
  DialogLogin adaptee;

  DialogLogin_jbtnOk_actionAdapter(DialogLogin adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jbtnOk_actionPerformed(e);
  }
}

class DialogLogin_jbtnCancel_actionAdapter implements java.awt.event.ActionListener {
  DialogLogin adaptee;

  DialogLogin_jbtnCancel_actionAdapter(DialogLogin adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jbtnCancel_actionPerformed(e);
  }
}
