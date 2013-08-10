package pt.inescporto.siasoft.asq.web.forms;

import pt.inescporto.template.client.design.TmplJTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JComponent;
import java.awt.Dimension;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.TmplTextAreaRender;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.TmplTextAreaEditor;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.TmplJButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import pt.inescporto.siasoft.comun.client.rmi.forms.ApplyProfileInterface;
import javax.swing.tree.TreePath;
import pt.inescporto.siasoft.comun.client.rmi.forms.ProfileSelectorPanel;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import pt.inescporto.siasoft.asq.ejb.dao.AsqApplyRegulatoryDao;
import pt.inescporto.siasoft.asq.ejb.session.AsqClientApplicability;
import java.rmi.RemoteException;
import pt.inescporto.siasoft.comun.ejb.dao.ProfileBuilderNode;
import javax.swing.JOptionPane;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.design.table.FW_TableModel;
import pt.inescporto.siasoft.asq.ejb.dao.LegalRequirementDao;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import java.util.ResourceBundle;

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
public class LegalRequirementPanel extends JPanel implements ApplyProfileInterface {
  private DataSource datasource = null;
  private FW_ComponentListener fwCListener = null;

   static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");

  JPanel jPanel1 = new JPanel();

  TmplJTextField jtfldRegulatoryDescription = new TmplJTextField() {
    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }
    public void tmplSave(TemplateEvent e) {}
  };

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblLR = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public LegalRequirementPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public LegalRequirementPanel(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;

    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //table listener registration
    navBarDetail.setFW_ComponentListener(jtblLR);
    navBarDetail.setActionListener(jtblLR);

    fwCListener.addFWComponentListener(jtfldRegulatoryDescription);
    fwCListener.addFWComponentListener(jtblLR);

    datasource.addDatasourceListener(jtfldRegulatoryDescription);
  }

  private void jbInit() throws Exception {
    JPanel content = new JPanel(new BorderLayout());
    setLayout(new BorderLayout());
    setOpaque(false);

    // cut here
    if (MenuSingleton.isSupplier()) {
      ProfileSelectorPanel psp = new ProfileSelectorPanel(this);
      psp.setVisible(false);
      TmplJButton tt = new TmplJButton("<>");
      tt.addActionListener(new AL(psp));
      content.add(tt, BorderLayout.WEST);
      add(psp, BorderLayout.WEST);
    }
    // cut here

    jtfldRegulatoryDescription.setField("name");

    jPanel1.setOpaque(false);
    FormLayout formLayout = new FormLayout("left:pref:grow", "pref");
    jPanel1.setLayout(formLayout);
    CellConstraints cc = new CellConstraints();
    jPanel1.add(jtfldRegulatoryDescription, cc.xy(1, 1, CellConstraints.FILL, CellConstraints.DEFAULT));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
                                                res.getString("legal.label.code"),
                                                "legalReqId",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
                                                res.getString("legal.label.desc"),
                                                "legalReqDescription",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
                                                res.getString("legal.label.obs"),
                                                "legalReqObs",
                                                new TmplTextAreaRender(),
                                                new TmplTextAreaEditor()));
    jtblLR = new FW_JTable(datasource.getDataSourceByName("LegalRequirement"), null, colManager) {
      protected boolean doSave() {
	switch (mode) {
	  case TmplFormModes.MODE_INSERT:
            Vector<Integer> list = tm.getInsertingRows();
            for (Integer row : list) {
	      ((LegalRequirementDao)tm.getAttrsAt(row.intValue())).supplierLock.setValue(new Boolean(MenuSingleton.isSupplier()));
            }
            break;
	}
        return super.doSave();
      }

      protected void doUpdate() {
	if (getSelectedRow() != -1 && !MenuSingleton.isSupplier() && ((LegalRequirementDao)tm.getAttrsAt(getSelectedRow())).supplierLock.getValue()) {
          showErrorMessage(res.getString("legal.label.message"));
          return;
        }
        super.doUpdate();
      }

      protected void doDelete() {
	if (getSelectedRow() != -1 && !MenuSingleton.isSupplier() && ((LegalRequirementDao)tm.getAttrsAt(getSelectedRow())).supplierLock.getValue()) {
	  showErrorMessage(res.getString("legal.label.message"));
	  return;
	}
	super.doDelete();
      }
    };
    jtblLR.setAsMaster(false);
    jtblLR.setRowHeight(50);

    jScrollPane1.getViewport().add(jtblLR);
    jScrollPane1.setPreferredSize(new Dimension(1,121));
    JPanel jpnlTable = new JPanel(new BorderLayout());

    jpnlTable.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    jpnlTable.add(navBarDetail, java.awt.BorderLayout.SOUTH);

    content.add(jPanel1, java.awt.BorderLayout.NORTH);
    content.add(jpnlTable, java.awt.BorderLayout.CENTER);

    add(content, BorderLayout.CENTER);
  }

  public void applyProfile(TreePath[] treePath, Integer app) {
    if (jtblLR.getSelectedRow() == -1) {
      JOptionPane.showMessageDialog(
          this,
	  res.getString("legal.label.message1"), TmplResourceSingleton.getString("error.dialog.header"),
	  JOptionPane.ERROR_MESSAGE);
      return;
    }
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
        aarDao.legalRequirement = ((LegalRequirementDao)((FW_TableModel)jtblLR.getModel()).getAttrsAt(jtblLR.getSelectedRow())).legalReqId.getValue();
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
