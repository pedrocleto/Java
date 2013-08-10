package pt.inescporto.siasoft.asq.web.forms;

import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import pt.inescporto.template.client.rmi.FW_InternalFrame;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.*;
import pt.inescporto.template.client.design.TmplJCheckBox;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.siasoft.asq.client.rmi.forms.ClientLegalRequirementPanel;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import java.awt.Cursor;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.TmplException;
import pt.inescporto.template.utils.TmplMessages;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class RegulatoryForm extends FW_InternalFrame {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.asq.web.forms.FormResources");
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  DataSourceRMI master = null;
  DataSourceRMI detailActiveRef = null;
  DataSourceRMI detailPassiveRef = null;
  DataSourceRMI detailLegalRequirement = null;

  TmplJCheckBox jchbSupplierLock = new TmplJCheckBox();

  public RegulatoryForm() {
    master = new DataSourceRMI("Regulatory") {
      public void find() throws DataSourceException {
        // broadcast save to Template Components
        fireTemplateSave();

        ((RegulatoryDao)attrs).supplierLock.resetValue();

        try {
          doSend(TmplMessages.MSG_FIND);
        }
        catch (TmplException tmplex) {
          first();
          if (tmplex.getErrorCode() != TmplMessages.MSG_OK) {
            throw new DataSourceException(DataSourceException.NOT_FOUND);
          }
          else
            throw new DataSourceException(tmplex);
        }
      }
    };
    master.setUrl("pt.inescporto.siasoft.asq.ejb.session.Regulatory");

    detailActiveRef = new DataSourceRMI("HistActiveRef");
    detailActiveRef.setUrl("pt.inescporto.siasoft.asq.ejb.session.RegulatoryHistory");

    DSRelation mdActiveRef = new DSRelation("Regulatory2HAR");
    mdActiveRef.setMaster(master);
    mdActiveRef.setDetail(detailActiveRef);
    mdActiveRef.addKey(new RelationKey("regId", "regIdFather"));

    detailPassiveRef = new DataSourceRMI("HistPassiveRef");
    detailPassiveRef.setUrl("pt.inescporto.siasoft.asq.ejb.session.RegulatoryHistory");

    DSRelation mdPassiveRef = new DSRelation("Regulatory2HPR");
    mdPassiveRef.setMaster(master);
    mdPassiveRef.setDetail(detailPassiveRef);
    mdPassiveRef.addKey(new RelationKey("regId", "regIdSon"));

    detailLegalRequirement = new DataSourceRMI("LegalRequirement");
    detailLegalRequirement.setUrl("pt.inescporto.siasoft.asq.ejb.session.LegalRequirement");

    DSRelation mdLegalRequirement = new DSRelation("Regulatory2LegalRequirement");
    mdLegalRequirement.setMaster(master);
    mdLegalRequirement.setDetail(detailLegalRequirement);
    mdLegalRequirement.addKey(new RelationKey("regId", "regId"));

    // for RegulatoryHasScope
    DataSourceRMI detailClass = new DataSourceRMI("RegulatoryHasClass");
    detailClass.setUrl("pt.inescporto.siasoft.asq.ejb.session.RegulatoryHasClass");

    DSRelation mdRegHasClass = new DSRelation("RegulatoryRHC");
    mdRegHasClass.setMaster(master);
    mdRegHasClass.setDetail(detailClass);
    mdRegHasClass.addKey(new RelationKey("regId", "regID"));

    try {
      master.addDataSourceLink(mdActiveRef);
      master.addDataSourceLink(mdPassiveRef);
      master.addDataSourceLink(mdLegalRequirement);
      master.addDataSourceLink(mdRegHasClass);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    setDataSource(master);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    master.addDatasourceListener(jchbSupplierLock);

    start();
  }

  private void jbInit() throws Exception {
    allHeaders = new String[] {res.getString("regulatory.label.code"), res.getString("regulatory.label.desc"), res.getString("regulatory.label.resume")};

    jchbSupplierLock.setField("supplierLock");
    jchbSupplierLock.setDataValues(new Object[] {Boolean.FALSE, Boolean.TRUE});

    add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    jTabbedPane1.addTab("Regulatory", new RegulatoryPanel(dataSource, this));
    jTabbedPane1.addTab("RegulatoryHistory", new RegulatoryHistoryPanel(dataSource, this));
    if (MenuSingleton.isSupplier())
      jTabbedPane1.addTab("LegalRequirement", new LegalRequirementPanel(dataSource, this));
    else
      jTabbedPane1.addTab("LegalRequirement", new ClientLegalRequirementPanel(dataSource, this, true, null));
    jTabbedPane1.addTab("RegulatoryLinks", new RegulatoryLinkPane(dataSource, this));
  }

  protected void doUpdate() {
    if (!MenuSingleton.isSupplier() && jchbSupplierLock.isSelected()) {
      doCancel();
      showInformationMessage("Não possui permissões para efectuar a operação!");
    }
    else
      super.doUpdate();
  }

  protected void doDelete() {
    if (!MenuSingleton.isSupplier() && jchbSupplierLock.isSelected()) {
      doCancel();
      showInformationMessage("Não possui permissões para efectuar a operação!");
    }
    else
      super.doDelete();
  }

  public boolean setPrimaryKey(Object keyValue) {
    try {
      if (keyValue != null) {
	setCursor(new Cursor(Cursor.WAIT_CURSOR));
	RegulatoryDao attrs = (RegulatoryDao)dataSource.getAttrs();
	attrs.regId.setValue((String)keyValue);
	dataSource.findByPK(attrs);
      }
    }
    catch (DataSourceException ex) {
      return false;
    }
    finally {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    return true;
  }
}
