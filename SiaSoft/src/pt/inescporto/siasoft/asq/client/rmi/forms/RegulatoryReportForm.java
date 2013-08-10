package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.rmi.*;
import pt.inescporto.template.client.util.DataSourceException;
import java.awt.BorderLayout;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DataSourceRMI;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.*;
import javax.swing.JSplitPane;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import java.awt.Dimension;
import java.util.Vector;
import java.awt.Cursor;

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
public class RegulatoryReportForm  extends FW_JPanel {
  JPanel jPanel = new JPanel();
  DataSourceRMI master = null;
  DataSourceRMI detailActiveRef = null;
  DataSourceRMI detailPassiveRef = null;
  DataSourceRMI detailLegalRequirement = null;
  DataSource datasource = null;
  JSplitPane jSplit = null;
  JSplitPane jSplit2 = null;
  JSplitPane jSplit3 = null;
  RegulatoryDao regDao=null;
  public RegulatoryReportForm(/*RegulatoryDao regDao*/) {
    this.regDao=regDao;
    master = new DataSourceRMI("Regulatory");
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

    try {
      master.addDataSourceLink(mdActiveRef);
      master.addDataSourceLink(mdPassiveRef);
      master.addDataSourceLink(mdLegalRequirement);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    //Vector binds = new Vector();
    // binds.add(regDao.regId.getValue());
    // datasource=master;

    setDataSource(master);


    /* if (regDao != null) {
	try {
	  datasource.setAttrs(regDao);
	  //datasource.setLinkCondition("regID= ?",binds);
	 // setDataSource(datasource);
	}
	catch (DataSourceException ex1) {
	}
      }*/


    fwNavBar.setVisible(false);
    hasVisualPerms=true;


    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    start();
  }

  private void jbInit() throws Exception {
    setLayout(new BorderLayout());

    RegulatoryDefinitionPanel regDef = new RegulatoryDefinitionPanel(dataSource,this);
    regDef.jbtnAddPDFtoList.setVisible(false);
    regDef.jbtnViewPDF.setVisible(false);
    ClientRegulatoryHistoryPanel clRegHist = new ClientRegulatoryHistoryPanel(dataSource, this);

    jSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, regDef, clRegHist);
    jSplit.setOneTouchExpandable(true);
    jSplit.setDividerLocation(200);

    ClientLegalRequirementPanel clLegReq = new ClientLegalRequirementPanel(dataSource, this, false, null);
    clLegReq.navBarDetail.setVisible(false);

    jSplit2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jSplit, clLegReq);
    jSplit2.setOneTouchExpandable(true);
    jSplit2.setDividerLocation(420);

    jSplit2.resetToPreferredSizes();
    add(jSplit2, BorderLayout.CENTER);

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
