package pt.inescporto.siasoft.proc.client.rmi.forms;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.design.TmplJButton;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect;
import javax.naming.*;
import pt.inescporto.template.dao.*;
import pt.inescporto.template.dao.*;
import java.rmi.*;
import pt.inescporto.siasoft.go.aa.ejb.dao.EnvironmentAspectDao;
import java.util.Collection;
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
public class EnvAspectListPane extends JPanel implements ActionListener {
  FW_JTable fwEnvAspectList = null;

  DataSourceRMI datasource = null;
  String activityID = null;

  JToolBar jtbarNav = new JToolBar();
  TmplJButton jbtnOk = new TmplJButton("Ok");
  Collection<EnvironmentAspectDao> envAspectDao = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.proc.client.rmi.forms.FormResources");

   public EnvAspectListPane(String activityID) {
    this.activityID = activityID;

    datasource = new DataSourceRMI("EnvAspect");
    datasource.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    try {
      datasource.initialize();
      Vector binds = new Vector();
      binds.add(new TplString(activityID));
      datasource.setLinkCondition("activityID = ?", binds);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    fwEnvAspectList.start();
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createTitledBorder(res.getString("envAspectListPane.label.title")));
    ((TitledBorder)getBorder()).setTitleFont(new Font("Dialog", Font.PLAIN, 12));

    // nav bar
    jtbarNav.setFloatable(false);
    jtbarNav.setOrientation(JToolBar.VERTICAL);
    jtbarNav.add(jbtnOk, null);
    add(jtbarNav, java.awt.BorderLayout.EAST);

    jbtnOk.setActionCommand("OK");
    jbtnOk.addActionListener(this);
    jbtnOk.setBorderPainted(false);
    jbtnOk.setOpaque(false);
    jbtnOk.setFocusable(false);

    fwEnvAspectList = new FW_JTable(datasource,
                                   new String[] {"", res.getString("envAspectListPane.label.desc")},
                                   new String[] {"envAspName"});

    fwEnvAspectList.setAsMaster(true);
    fwEnvAspectList.setRowHeight(25);
    fwEnvAspectList.setFont(new Font("Dialog", Font.PLAIN, 10));
    fwEnvAspectList.setShowGrid(false);
    fwEnvAspectList.setShowHorizontalLines(false);
    fwEnvAspectList.setShowVerticalLines(false);
    fwEnvAspectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    fwEnvAspectList.getTableHeader().setReorderingAllowed(false);
    JScrollPane jsp = new JScrollPane(fwEnvAspectList);
    add(jsp, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {
    Container parent = getParent();

    while (parent != null && !(parent instanceof JDialog))
      parent = parent.getParent();
    ((JDialog)parent).dispose();
  }
}
