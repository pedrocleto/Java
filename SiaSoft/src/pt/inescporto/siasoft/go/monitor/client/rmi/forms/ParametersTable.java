package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import java.awt.BorderLayout;
import pt.inescporto.template.client.design.FW_JTable;
import pt.inescporto.template.client.design.table.TmplStringEditor;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSourceException;
import pt.inescporto.template.client.design.table.FW_ColumnManager;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JScrollPane;
import java.util.ResourceBundle;
import pt.inescporto.template.client.design.FW_NavBarDetail;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.table.TmplStringRenderer;
import pt.inescporto.template.client.design.table.FW_ColumnNode;
import pt.inescporto.template.client.design.table.TmplComboBoxEditor;
import pt.inescporto.template.client.design.table.TmplLookupRenderer;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.client.rmi.MenuSingleton;
import pt.inescporto.template.client.design.table.TmplDateRenderer;
import pt.inescporto.template.client.design.table.TmplFloatRenderer;
import pt.inescporto.template.client.design.table.TmplFloatEditor;
import pt.inescporto.siasoft.events.SyncronizerSubjects;
import pt.inescporto.template.client.design.table.TmplTimestampPickerEditor;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorParameterDao;
import pt.inescporto.template.client.rmi.FW_JPanelBasic;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.client.util.TmplFormModes;
import pt.inescporto.template.client.event.EventSynchronizer;
import pt.inescporto.template.client.design.TmplResourceSingleton;
import pt.inescporto.template.client.design.table.FW_TableModel;

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
public class ParametersTable extends JPanel{

  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  JScrollPane jScrollPane1 = new JScrollPane();
  FW_JTable jtblParameters = null;
  FW_NavBarDetail navBarDetail = new FW_NavBarDetail();

  public ParametersTable(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;

    initialize();

    //table listener registration
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
					       res.getString("paramTable.label.code"),
					       "parameterID",
                                               new TmplStringRenderer(),
                                               new TmplStringEditor()));

    colManager.addColumnNode(new FW_ColumnNode(1,
					       res.getString("paramTable.label.desc"),
					       "parameterDescription",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));
    colManager.addColumnNode(new FW_ColumnNode(2,
					       res.getString("paramTable.label.startPlan"),
					       "startPlanDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(3,
					       res.getString("paramTable.label.endPlan"),
					       "endPlanDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(4,
					       res.getString("paramTable.label.start"),
					       "startDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(5,
					       res.getString("paramTable.label.end"),
					       "endDate",
					       new TmplDateRenderer(),
					       new TmplTimestampPickerEditor()));
    colManager.addColumnNode(new FW_ColumnNode(6,
					       res.getString("paramTable.label.valueRead"),
					       "valueReaded",
					       new TmplFloatRenderer(),
					       new TmplFloatEditor()));

    TmplComboBoxEditor cbeUser = new TmplComboBoxEditor("pt.inescporto.siasoft.comun.ejb.session.User", new Integer[] {new Integer(1), new Integer(0)}, "enterpriseID = ?", binds);
    cbeUser.setWatcherSubject(SyncronizerSubjects.sysUSER);
    colManager.addColumnNode(new FW_ColumnNode(7,
					       res.getString("paramTable.label.user"),
					       "fkUserID",
					       new TmplLookupRenderer("pt.inescporto.siasoft.comun.ejb.session.User"),
					       cbeUser));

    colManager.addColumnNode(new FW_ColumnNode(8,
					       res.getString("paramTable.label.obs"),
					       "obs",
					       new TmplStringRenderer(),
					       new TmplStringEditor()));

    try {
      jtblParameters = new FW_JTable(datasource.getDataSourceByName("MonitorParameters"), null, colManager) {
	public boolean doSaveBefore(Object attrs) {
	  if (super.doSaveBefore(attrs)) {
	    MonitorParameterDao mon = (MonitorParameterDao)attrs;
	    try {
	      if (!mon.startDate.getValue().equals(null)) {
		try {
		  if (mon.valueReaded.getValue().equals(null)) {
		    JFrame frame = new JFrame();
		    JOptionPane.showMessageDialog(frame,
                                                  res.getString("label.valueRead"),
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
						res.getString("label.valueRead"),
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
    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();

    jScrollPane1.getViewport().add(jtblParameters);
    content.add(jScrollPane1, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(content, BorderLayout.CENTER);

    add(navBarDetail, BorderLayout.SOUTH);
  }
}
