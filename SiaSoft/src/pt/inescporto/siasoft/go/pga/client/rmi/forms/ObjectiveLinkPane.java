package pt.inescporto.siasoft.go.pga.client.rmi.forms;

import java.awt.BorderLayout;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;

import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.FW_ColumnManager;

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
public class ObjectiveLinkPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.pga.client.rmi.forms.FormResources");

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblObjectiveHasEA = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public ObjectiveLinkPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    //table listener registration
    navBarDetail.setFW_ComponentListener(jtblObjectiveHasEA);
    navBarDetail.setActionListener(jtblObjectiveHasEA);

    fwCListener.addFWComponentListener(jtblObjectiveHasEA);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
                                                res.getString("objLink.label.code"),
                                                "environmentalAspectID",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
                                                res.getString("objLink.label.type"),
                                                "eaType",
                                                new TmplStringRenderer(),
                                                new TmplStringEditor()));
    try {
      jtblObjectiveHasEA = new FW_JTable(datasource.getDataSourceByName("ObjectiveHasEA"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblObjectiveHasEA.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
                                           "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblObjectiveHasEA);
    content.add(jScrollPane1, cc.xy(2,2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);
    add(navBarDetail, BorderLayout.SOUTH);
  }
}
