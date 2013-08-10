package pt.inescporto.siasoft.go.coe.client.rmi.forms;

import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import javax.swing.JScrollPane;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.design.FW_JTable;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSourceException;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.rmi.MenuSingleton;

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

public class EmergSitScenariosDefPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;

  private JScrollPane jScrollPane1 = new JScrollPane();
  private FW_JTable jtblScenarios = null;
  private FW_NavBarDetail navBarDetail = new FW_NavBarDetail();
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.coe.client.rmi.forms.FormResources");

  public EmergSitScenariosDefPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    // table listener registration
    navBarDetail.setFW_ComponentListener(jtblScenarios);
    navBarDetail.setActionListener(jtblScenarios);

    fwCListener.addFWComponentListener(jtblScenarios);

  }

  public FW_ComponentListener getMasterListener() {
    return jtblScenarios;
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    // set linkcondition binds dinamically
    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("emergSitdef.label.code"),
					       "scenarioID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("emergSitdef.label.description"),
					       "scenarioDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    TmplComboBoxEditor cbeDocument = new TmplComboBoxEditor("pt.inescporto.siasoft.condoc.ejb.session.Document", new Integer[] {new Integer(1), new Integer(0)}, "fkEnterpriseID = ?", binds);
    cbeDocument.setWatcherSubject(SyncronizerSubjects.documentFORM);
    colManager.addColumnNode(new FW_ColumnNode(2,
                                               res.getString("emergSitScenDef.label.proc"),
                                               "fkDocumentID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.condoc.ejb.session.Document"),
                                               cbeDocument));

    try {
      jtblScenarios = new FW_JTable(datasource.getDataSourceByName("EmergencyScenario"), null, colManager);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblScenarios.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 50dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblScenarios);
    content.add(jScrollPane1, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);

    add(navBarDetail, BorderLayout.SOUTH);

  }

}
