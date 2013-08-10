package pt.inescporto.siasoft.condoc.client.rmi.forms;

import javax.swing.*;

import pt.inescporto.template.client.design.*;
import pt.inescporto.template.client.util.*;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.ResourceBundle;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.rmi.TmplEJBLocater;
import pt.inescporto.template.dao.UserException;
import javax.naming.NamingException;
import java.util.Vector;
import java.rmi.RemoteException;
import java.awt.Dimension;
import java.util.Hashtable;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentDao;
import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHasLinksDao;
import pt.inescporto.siasoft.condoc.ejb.session.DocumentHasLinks;

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
public class DocumentLink extends JPanel {
  DataSource datasource=null;
  FW_ComponentListener fwCListener = null;

  TmplLookupButton jlbtnEnterprise = new TmplLookupButton();
  TmplJTextField jtfldEnterprise = new TmplJTextField();
  TmplLookupField jlfldEnterprise = new TmplLookupField();

  JTabbedPane doctab = new JTabbedPane();
  private Hashtable<String, Vector> coeList = null;
  private Hashtable<String, Vector> auditList = null;


  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.condoc.client.rmi.forms.FormResources");

  TmplJTextField jtfldDocumentDescription = new TmplJTextField() {
    public void tmplMode(TemplateEvent e) {
      super.tmplMode(e);
      setEnabled(false);
    }

    public boolean tmplRequired(TemplateEvent e) {
      return true;
    }

    public void tmplSave(TemplateEvent e) {}
  };

  public DocumentLink(DataSource datasource, FW_ComponentListener fwCListener,JTabbedPane doctab) {
    this.datasource=datasource;
    this.fwCListener=fwCListener;
    this.doctab=doctab;
    initialize();

    datasource.addDatasourceListener(jtfldDocumentDescription);
    datasource.addDatasourceListener(jlbtnEnterprise);
    datasource.addDatasourceListener(jtfldEnterprise);
    datasource.addDatasourceListener(jlfldEnterprise);

    fwCListener.addFWComponentListener(jtfldDocumentDescription);
    fwCListener.addFWComponentListener(jlbtnEnterprise);
    fwCListener.addFWComponentListener(jtfldEnterprise);
    fwCListener.addFWComponentListener(jlfldEnterprise);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jtfldDocumentDescription.setField("documentDescription");

    jtfldEnterprise.setLabel(res.getString("document.label.enterprise"));
    jtfldEnterprise.setField("fkEnterpriseID");
    if (!MenuSingleton.isSupplier())
       jlbtnEnterprise.setLinkCondition("enterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlbtnEnterprise.setText(res.getString("document.label.enterprise"));
    jlbtnEnterprise.setTitle(res.getString("document.label.enterpriselist"));
    jlbtnEnterprise.setDefaultFill(jtfldEnterprise);
    jlfldEnterprise.setUrl("pt.inescporto.siasoft.comun.ejb.session.Enterprise");
    jlfldEnterprise.setDefaultRefField(jtfldEnterprise);

    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, 100dlu:grow, 5px",
					   "5px, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jtfldDocumentDescription, cc.xyw(2, 2, 5));

    content.add(jlbtnEnterprise, cc.xy(2, 4, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnterprise, cc.xy(4, 4));
    content.add(jlfldEnterprise, cc.xy(6, 4));

    add(content, BorderLayout.NORTH);

    DocumentHasLinksPane mon = new DocumentHasLinksPane();
    DocumentHasLinksTab jDocLinksPane = new DocumentHasLinksTab(mon);

    DocumentDao dt = null;
    String documentID = null;
    String enterpriseID = null;
    try {
      dt = (DocumentDao)datasource.getCurrentRecord();
      documentID = dt.documentID.getValue();
      enterpriseID = dt.fkEnterpriseID.getValue();
    }
    catch (DataSourceException ex) {

    }
    DocumentHasLinksDao documentHasLinksDao = null;
    try {
      DocumentHasLinks documentLinks = (DocumentHasLinks)TmplEJBLocater.getInstance().getEJBRemote("pt.inescporto.siasoft.condoc.ejb.session.DocumentHasLinks");
      try {
        documentHasLinksDao = documentLinks.getLinks(documentID, enterpriseID);
      }
      catch (UserException ex) {
        ex.printStackTrace();
      }
      catch (RemoteException ex) {
        ex.printStackTrace();
      }
    }
    catch (NamingException ex) {
      ex.printStackTrace();
    }
    coeList = (Hashtable<String, Vector>)documentHasLinksDao.coeList;
    auditList = (Hashtable<String, Vector>)documentHasLinksDao.auditList;
    Vector<Vector> result = new Vector<Vector>(2);
    try {
      if (coeList.containsKey(documentID) && auditList.containsKey(documentID)) {
	result.add(0, documentHasLinksDao.coeList.get(documentID));
	result.add(1, documentHasLinksDao.auditList.get(documentID));
	jDocLinksPane.refresh(result);

      }
      else {
	if (coeList.containsKey(documentID)) {
	  Vector v = documentHasLinksDao.coeList.get(documentID);
	  jDocLinksPane.refreshOne(v);
	}

	if (auditList.containsKey(documentID)) {
	  Vector v = documentHasLinksDao.auditList.get(documentID);
	  jDocLinksPane.refreshOne(v);
	}
      }
    }
    catch (Exception ex) {}


      doctab.removeAll();
      doctab.add(jDocLinksPane.getDocumentHasLinksPane(), 0);
      doctab.setTitleAt(0, res.getString("label.activelinks"));
      doctab.setPreferredSize(new Dimension(500, 250));
      add(doctab, BorderLayout.SOUTH);


  }
}
