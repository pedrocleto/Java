package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.*;

import pt.inescporto.template.client.rmi.*;
import pt.inescporto.template.client.util.*;
import java.awt.BorderLayout;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHistoryDao;
import java.lang.reflect.Method;
import java.lang.reflect.*;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import java.util.ResourceBundle;
import pt.inescporto.siasoft.events.SyncronizerSubjects;

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

public class DocumentHistoryForm extends FW_InternalFrame {
   DataSourceRMI master = null;
   JTabbedPane jTabbedPane = new JTabbedPane();
   static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  public DocumentHistoryForm() {
    master = new DataSourceRMI("DocumentHistory") {
      public void attrsAreSet(Object attrs) {
	((DocumentHistoryDao)attrs).documentID.resetLinkKey();
	((DocumentHistoryDao)attrs).documentID.resetFkKey();

        // find and execute update( Object attrs )
	try {
	  Method execMethod = remote.getClass().getMethod("setAttrs", new Class[] {attrs.getClass()});
	  execMethod.invoke(remote, new Object[] {attrs});
	}
	catch (InvocationTargetException ex) {
          ex.printStackTrace();
	}
	catch (IllegalArgumentException ex) {
          ex.printStackTrace();
	}
	catch (IllegalAccessException ex) {
          ex.printStackTrace();
	}
	catch (SecurityException ex) {
          ex.printStackTrace();
	}
	catch (NoSuchMethodException ex) {
          ex.printStackTrace();
	}
      }
    };
    master.setUrl("pt.inescporto.siasoft.condoc.ejb.session.DocumentHistory");

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

    allHeaders = new String[] {res.getString("docHistory.label.doc"), res.getString("docHistory.label.actDate"), res.getString("documentdef.label.resp"), res.getString("docHistory.label.actType"), res.getString("docHistory.label.actUser"), res.getString("docHistory.label.obs"), res.getString("document.label.enterprise")};

    setPublisherEvent(SyncronizerSubjects.documentHIST);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    start();
  }

  private void jbInit() throws Exception {
   jTabbedPane = new JTabbedPane();
   add(jTabbedPane, BorderLayout.CENTER);
   jTabbedPane.add(res.getString("document.label.definition"), new DocumentHistoryDefinition(dataSource, this));
   jTabbedPane.add(res.getString("document.label.links"), new DocumentHistoryLink(dataSource, this));

  }
  protected void doInsert() {
    try {
      if (!MenuSingleton.isSupplier()) {
        DocumentHistoryDao daoEC = (DocumentHistoryDao)dataSource.getCurrentRecord();
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


