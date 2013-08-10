package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import pt.inescporto.template.client.design.FW_JTable;
import java.awt.BorderLayout;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JScrollPane;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.TmplFloatRenderer;
import pt.inescporto.template.client.design.table.TmplFloatEditor;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import pt.inescporto.template.client.design.table.FW_LookupEditor;
import pt.inescporto.template.client.rmi.MenuSingleton;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanParameterDao;
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
public class ParametersDefinitionPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblParameters = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public ParametersDefinitionPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    navBarDetail.setFW_ComponentListener(jtblParameters);
    navBarDetail.setActionListener(jtblParameters);

    fwCListener.addFWComponentListener(jtblParameters);
  }

  public FW_ComponentListener getMasterListener() {
    return jtblParameters;
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    // set linkcondition binds dinamically
    Vector binds = new Vector();
    binds.add(new TplString(MenuSingleton.isSupplier() ? MenuSingleton.getSelectedEnterprise() : MenuSingleton.getEnterprise()));

    FW_ColumnManager colManager = new FW_ColumnManager();
    colManager.addColumnNode(new FW_ColumnNode(0,
					       res.getString("paramDef.label.code"),
					       "parameterID",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("paramDef.label.desc"),
					       "parameterDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("paramDef.label.maxValue"),
					       "maxValue",
					       new TmplFloatRenderer(),
					       new TmplFloatEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("paramDef.label.vleIs"),
					       "vleIS",
                                               new TmplLookupRenderer(new Object[] {res.getString("paramDef.label.max"), res.getString("paramDef.label.min")}, new Object[] {Boolean.TRUE, Boolean.FALSE}),
					       new TmplComboBoxEditor(new Object[] {res.getString("paramDef.label.max"), res.getString("paramDef.label.min")}, new Object[] {Boolean.TRUE, Boolean.FALSE})));

    TmplComboBoxEditor cbeUnit = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.Units", new Integer[] {new Integer(1), new Integer(0)});
    cbeUnit.setWatcherSubject(SyncronizerSubjects.sysUNIT);
    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("paramDef.label.unit"),
					       "fkUnitID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.Units"),
					       cbeUnit));
    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("paramDef.label.type"),
					       "type",
					       new TmplLookupRenderer(new Object[] {"", res.getString("paramDef.label.type.internal"), res.getString("paramDef.label.type.external"),
	res.getString("paramDef.label.type.estimated")}, new Object[] {"", "I", "E", "X"}),
					       new TmplComboBoxEditor(new Object[] {"", res.getString("paramDef.label.type.internal"), res.getString("paramDef.label.type.external"),
	res.getString("paramDef.label.type.estimated")}, new Object[] {"", "I", "E", "X"})));
    TmplComboBoxEditor cbeDocument = new TmplComboBoxEditor("pt.inescporto.siasoft.condoc.ejb.session.Document", new Integer[] {new Integer(1), new Integer(0)}, "fkEnterpriseID = ?", binds);
    cbeDocument.setWatcherSubject(SyncronizerSubjects.documentFORM);
    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("paramDef.label.procedure"),
					       "fkDocumentID",
                                               new TmplLookupRenderer("pt.inescporto.siasoft.condoc.ejb.session.Document"),
                                               cbeDocument));

    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User", new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);
    colManager.addColumnNode(new FW_ColumnNode(7,
					       res.getString("paramDef.label.user"),
					       "fkUserID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
					       cbeUser));
    colManager.addColumnNode(new FW_ColumnNode(8,
					       res.getString("paramDef.label.regulatory"),
					       "fkRegID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.asq.ejb.session.Regulatory"),
					       new FW_LookupEditor("pt.inescporto.siasoft.asq.ejb.session.Regulatory", "Lista de Diplomas", null)));

    try {
      jtblParameters = new FW_JTable(datasource.getDataSourceByName("MonitorPlanParameters"), null, colManager){
        public boolean doSaveBefore(Object attrs) {
          if (super.doSaveBefore(attrs)) {
            MonitorPlanParameterDao mon = (MonitorPlanParameterDao)attrs;
            try {
              if (!mon.maxValue.getValue().equals(null)) {
                try {
                  if (mon.vleIS.getValue().equals(null)) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                                                  res.getString("label.valueIS"),
                                                  res.getString("label.warning"),
                                                  JOptionPane.WARNING_MESSAGE);
                    return false;
                  }
                  else {
                    return true;
                  }
                }
                catch (NullPointerException ex) {
                  JFrame frame = new JFrame();
                  JOptionPane.showMessageDialog(frame,
                                                res.getString("label.valueIS"),
                                                res.getString("label.warning"),
                                                JOptionPane.WARNING_MESSAGE);
                  return false;

                }
              }
            }
            catch (NullPointerException ex) {
            }
          }
          return true;
        }

	public void doCancel() {
          super.doCancel();
	  try {
	    datasource.refresh();
	  }
	  catch (DataSourceException ex) {
	  }
	}

      };

    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    jtblParameters.setAsMaster(false);

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
                                           "5px, 90dlu:grow, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2}});
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblParameters);
    content.add(jScrollPane1, cc.xy(2,2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);

    add(navBarDetail, BorderLayout.SOUTH);
  }
}
