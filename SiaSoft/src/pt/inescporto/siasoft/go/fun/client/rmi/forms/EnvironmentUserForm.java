package pt.inescporto.siasoft.go.fun.client.rmi.forms;

import java.lang.reflect.Field;
import java.util.Vector;
import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.rmi.FW_InternalFrameBasic;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.elements.TplObject;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import pt.inescporto.siasoft.comun.ejb.dao.UserDao;
import pt.inescporto.siasoft.go.fun.ejb.dao.EnvironmentUserDao;
import pt.inescporto.template.client.design.TmplJLabel;
import javax.swing.BoxLayout;
import java.awt.Font;

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
public class EnvironmentUserForm extends FW_InternalFrameBasic implements ActionListener {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.fun.client.rmi.forms.FormResources");

  DataSourceRMI dsUser = null;
  DataSourceRMI dsEnvUser = null;

  JList jlstUser = new JList();
  JList jlstEnvUser = new JList();

  JButton jbtnAdd = new JButton(">");
  JButton jbtnRemove = new JButton("<") ;

  public EnvironmentUserForm() {
    super();

    // set the id from permission control
    setAccessPermitionIdentifier("ENVUSER");

    init();
    start();
  }

  public void init() {
    // define User datasource
    dsUser = new DataSourceRMI("User");
    dsUser.setUrl("pt.inescporto.siasoft.comun.ejb.session.User");
    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
        dsUser.setLinkCondition("enterpriseID = ?", binds);
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }
    }
    EU_ListModel lmU = new EU_ListModel(dsUser, "userID");
    jlstUser.setModel(lmU);
    jlstUser.setFont(new Font("dialog", Font.PLAIN, 10));
    lmU.tmplRefresh(new TemplateEvent(this));

    // define Environment User datasource
    dsEnvUser = new DataSourceRMI("EnvironmentUser");
    dsEnvUser.setUrl("pt.inescporto.siasoft.go.fun.ejb.session.EnvironmentUser");
    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
        dsEnvUser.setLinkCondition("enterpriseID = ?", binds);
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }
    }
    EU_ListModel lmEU = new EU_ListModel(dsEnvUser, "envUserID");
    jlstEnvUser.setModel(lmEU);
    jlstEnvUser.setFont(new Font("dialog", Font.PLAIN, 10));
    lmEU.tmplRefresh(new TemplateEvent(this));

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    jbtnAdd.setActionCommand("ADD");
    jbtnAdd.addActionListener(this);

    jbtnRemove.setActionCommand("REMOVE");
    jbtnRemove.addActionListener(this);

    setLayout(new BorderLayout());

    FormLayout formLayout = new FormLayout("5px, left:pref:grow, 4dlu, pref, 4dlu, left:pref:grow, 5px",
                                           "5px, pref, 2dlu, pref:grow, 2dlu, pref, 5px");

    JPanel jpnlContent = new JPanel(new BorderLayout());
    jpnlContent.setLayout(formLayout);

    formLayout.setColumnGroups(new int[][] { {2, 6}});

    CellConstraints cc = new CellConstraints();

    jpnlContent.add(new TmplJLabel(res.getString("envUser.label.users")), cc.xy(2, 2));
    jpnlContent.add(new TmplJLabel(res.getString("envUser.label.envTeam")), cc.xy(6, 2));
    jpnlContent.add(new JScrollPane(jlstUser), cc.xy(2, 4, CellConstraints.FILL, CellConstraints.FILL));
    JPanel jpnlActions = new JPanel();
    jpnlActions.setLayout(new BoxLayout(jpnlActions, BoxLayout.Y_AXIS));
    jpnlActions.add(jbtnAdd);
    jpnlActions.add(jbtnRemove);
    jpnlContent.add(jpnlActions, cc.xy(4,4));
    jpnlContent.add(new JScrollPane(jlstEnvUser), cc.xy(6, 4, CellConstraints.FILL, CellConstraints.FILL));

    add(jpnlContent, BorderLayout.CENTER);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("ADD")) {
      if (jlstUser.getSelectedIndex() == -1) {
        showInformationMessage(res.getString("envUser.label.message"));
      }
      else {
        UserDao uDao = (UserDao)((EU_ListModel)jlstUser.getModel()).getObjectAt(jlstUser.getSelectedIndex());
        EnvironmentUserDao euDao = new EnvironmentUserDao();
        euDao.enterpriseID.setValue(MenuSingleton.getEnterprise());
        euDao.envUserID.setValue(uDao.userID.getValue());
	try {
	  dsEnvUser.setAttrs(euDao);
	  dsEnvUser.insert();
	}
	catch (DataSourceException ex) {
	}
      }
    }
    else if (e.getActionCommand().equals("REMOVE")) {
      if (jlstEnvUser.getSelectedIndex() == -1) {
        showInformationMessage(res.getString("envUser.label.message1"));
      }
      else {
        EnvironmentUserDao euDao = (EnvironmentUserDao)((EU_ListModel)jlstEnvUser.getModel()).getObjectAt(jlstEnvUser.getSelectedIndex());
	try {
	  dsEnvUser.setAttrs(euDao);
	  dsEnvUser.delete();
	}
	catch (DataSourceException ex1) {
	}
      }
    }
  }
}

class EU_ListModel extends AbstractListModel implements DataSourceListener {
  protected Vector delegate = new Vector();
  protected DataSource datasource = null;
  protected String field = null;

  public EU_ListModel(DataSource datasource, String field) {
    this.datasource = datasource;
    this.field = field;

    try {
      datasource.addDatasourceListener(this);
      datasource.initialize();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public int getSize() {
    return delegate.size();
  }

  /**
   * Returns the value at the specified index.
   * @param index the requested index
   * @return the value at <code>index</code>
   */
  public Object getElementAt(int index) {
    Field[] fld = null;

    fld = delegate.elementAt(index).getClass().getFields();

    // process any field
    for (int i = 0; i < fld.length; i++) {
      Object test = null;
      try {
        test = fld[i].get(delegate.elementAt(index));
      }
      catch (IllegalAccessException ex) {
      }
      catch (IllegalArgumentException ex) {
      }
      // process template objects
      if (test instanceof pt.inescporto.template.elements.TplObject) {
        TplObject myObject = null;
        myObject = (TplObject)test;
        if (myObject.getField().equalsIgnoreCase(field)) {
          return myObject.getValueAsObject();
        }
      }
    }

    return delegate.elementAt(index);
  }

  public Object getObjectAt(int index) {
    return delegate.elementAt(index);
  }

  /**
   * ***************************************************************************
   *       Implementation of the <code>DataSourceListener<\code> Interface
   * ***************************************************************************
   */

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
    try {
      delegate = new Vector(datasource.listAll());
      fireContentsChanged(this, 0, delegate.size());
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }

  public void tmplSave(TemplateEvent e) {
  }

  public void tmplLink(TemplateEvent e) {
  }
}
