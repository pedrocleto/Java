package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.JPanel;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentTypeDao;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.util.Vector;
import java.awt.BorderLayout;
import java.util.ResourceBundle;

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

public class DocumentTypeForm extends FW_InternalFrame {
  JPanel jPanel = new JPanel();
  DataSourceRMI master = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  public DocumentTypeForm() {

    master = new DataSourceRMI("DocumentType");
    master.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentType");

    if (!MenuSingleton.isSupplier()) {
      Vector binds = new Vector();
      binds.add(new TplString(MenuSingleton.getEnterprise()));
      try {
	master.setLinkCondition("fkEnterpriseID = ?", binds);
      }
      catch (DataSourceException ex1) {
      }
    }

    setDataSource(master);
    allHeaders = new String[] {res.getString("documenttypedef.label.code"), res.getString("documenttypedef.label.description"), res.getString("documenttypedef.label.father"), res.getString("document.label.enterprise")};

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    start();
  }

  private void jbInit() throws Exception {
    jPanel = new JPanel(new BorderLayout());
    add(jPanel, BorderLayout.CENTER);
    jPanel.add(new DocumentTypeDefinition(dataSource, this));
  }

  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
	DocumentTypeDao daoEC = (DocumentTypeDao)dataSource.getCurrentRecord();
	daoEC.fkEnterpriseID.setLinkKey();
	daoEC.fkEnterpriseID.setValueAsObject(MenuSingleton.getEnterprise());
	dataSource.reLinkAttrs();
      }
      super.doInsert();
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
  }
}
