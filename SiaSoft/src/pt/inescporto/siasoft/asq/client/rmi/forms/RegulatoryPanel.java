package pt.inescporto.siasoft.asq.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import pt.inescporto.template.client.rmi.FW_JPanel;
import pt.inescporto.template.client.util.DataSourceRMI;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.util.DSRelation;
import pt.inescporto.template.client.util.RelationKey;
import javax.swing.JScrollPane;

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
public class RegulatoryPanel extends FW_JPanel {
  JTabbedPane jTabbedPane = new JTabbedPane();
  DataSourceRMI master = null;
  DataSourceRMI detailActiveRef = null;
  DataSourceRMI detailPassiveRef = null;
  DataSourceRMI detailLegalRequirement = null;

  public RegulatoryPanel() {
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

    start();
  }

  private void jbInit() throws Exception {
    jTabbedPane = new JTabbedPane();
    add(jTabbedPane, java.awt.BorderLayout.CENTER);
    jTabbedPane.addTab("Diplomas", new RegulatoryDefinitionPanel(dataSource, this));
    jTabbedPane.addTab("Histórico",new JScrollPane(new ClientRegulatoryHistoryPanel(dataSource, this)));
    jTabbedPane.addTab("Requisitos Legais", new ClientLegalRequirementPanel(dataSource, this, true, null));
  }
}
