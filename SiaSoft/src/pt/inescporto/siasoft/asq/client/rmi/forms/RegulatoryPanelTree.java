package pt.inescporto.siasoft.asq.client.rmi.forms;

import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.RelationKey;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.DataSourceRMI;
import javax.swing.JTabbedPane;
import pt.inescporto.template.client.rmi.FW_JPanelNav;
import java.awt.BorderLayout;
import pt.inescporto.siasoft.asq.ejb.dao.RegulatoryDao;
import javax.swing.JSplitPane;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import pt.inescporto.siasoft.asq.web.forms.RegulatoryLinkPane;

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
public class RegulatoryPanelTree extends FW_JPanelNav implements RegulatoryFinderInterface {
  private JSplitPane jSplitRegulatory = null;
  private TreeRefreshListener trListener = null;

  JTabbedPane jTabbedPane = new JTabbedPane();
  DataSourceRMI master = null;
  DataSourceRMI detailActiveRef = null;
  DataSourceRMI detailPassiveRef = null;
  DataSourceRMI detailLegalRequirement = null;

  RegulatoryDefinitionPanel rdp = null;
  ClientRegulatoryHistoryPanel crhp = null;
  JScrollPane jscrollpane= new JScrollPane();
  ClientLegalRequirementPanel clrp = null;
  RegulatoryLinkPane rlp = null;

  public RegulatoryPanelTree(TreeRefreshListener trListener) {
    this.trListener = trListener;

    setLayout(new BorderLayout());
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

    setDataSource(master);

    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    // set permition access ID
    setAccessPermitionIdentifier("REGULATORY_PANELTREE");

    start();

    // determine visibility of tab's
    if (crhp.hasVisualPerms() || clrp.hasVisualPerms()) {
      jTabbedPane = new JTabbedPane();
      jTabbedPane.addTab("Diplomas", rdp);
      if (crhp.hasVisualPerms())
	jTabbedPane.addTab("Histórico", crhp);
      if (clrp.hasVisualPerms())
	jTabbedPane.addTab("Requisitos Legais", clrp);
      rlp.start();
      if (rlp.hasVisualPerms())
        jTabbedPane.addTab("Ligações", rlp);
      add(jTabbedPane, java.awt.BorderLayout.CENTER);
    }
    else {
      add(rdp, java.awt.BorderLayout.CENTER);
    }

    // start invisible
    setVisible(false);
    setMinimumSize(new Dimension(430, 250));
  }

  private void jbInit() throws Exception {
    rdp = new RegulatoryDefinitionPanel(dataSource, this);
    crhp = new ClientRegulatoryHistoryPanel(dataSource, this);
    clrp = new ClientLegalRequirementPanel(dataSource, this, true, trListener);
    rlp = new RegulatoryLinkPane(dataSource, this);
  }

  public void setRegulatoryID(String regulatoryID,boolean isLegReq) {
    if (regulatoryID != null) {
      try {
	RegulatoryDao attrs = (RegulatoryDao)dataSource.getAttrs();
	attrs.regId.setValue(regulatoryID);
	dataSource.findByPK(attrs);
      }
      catch (DataSourceException ex) {
        ex.printStackTrace();
      }

      if(isLegReq){
        clrp.jlstLegalRequirment.clearSelection();
        jTabbedPane.setSelectedIndex(2);
      }

      if (!isVisible()) {
	setVisible(true);
	revalidate();
	jSplitRegulatory.revalidate();
        jSplitRegulatory.setDividerLocation(((double)jSplitRegulatory.getHeight() - (double)getMinimumSize().getHeight()) / (double)jSplitRegulatory.getHeight());
      }
    }
    else {
      setVisible(false);
      revalidate();
    }
  }

  public void setSplitPane(JSplitPane jSplitRegulatory) {
    this.jSplitRegulatory = jSplitRegulatory;
  }
}
