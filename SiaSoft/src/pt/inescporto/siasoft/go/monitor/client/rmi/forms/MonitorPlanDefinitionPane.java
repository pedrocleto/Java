package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import pt.inescporto.template.client.util.DataSource;
import pt.inescporto.template.client.design.FW_ComponentListener;
import com.jgoodies.forms.layout.FormLayout;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.design.TmplJLabel;
import pt.inescporto.template.client.design.TmplJTextField;
import pt.inescporto.template.client.design.TmplJComboBox;
import pt.inescporto.template.client.design.TmplJDatePicker;
import pt.inescporto.template.client.design.TmplLookupField;
import pt.inescporto.template.client.design.TmplLookupButton;
import pt.inescporto.template.client.rmi.MenuSingleton;

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
public class MonitorPlanDefinitionPane extends JPanel {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  //
  TmplJLabel jlblMonitorPlanID = new TmplJLabel();
  TmplJTextField jtfldMonitorPlanID = new TmplJTextField();

  TmplJLabel jlblMonitorPlanDescription = new TmplJLabel();
  TmplJTextField jtfldMonitorPlanDescription = new TmplJTextField();

  TmplJLabel jlblPeridiocity = new TmplJLabel();
  TmplJComboBox jcmbPeridiocity = new TmplJComboBox();

  TmplJLabel jlblNextMonitorDate = new TmplJLabel();
  TmplJDatePicker jtfldNextMonitorDate=new TmplJDatePicker();

  TmplLookupButton jlbtnEnvAspect = new TmplLookupButton();
  TmplJTextField jtfldEnvAspect = new TmplJTextField();
  TmplLookupField jlfldEnvAspect = new TmplLookupField();

  TmplLookupButton jlbtnActivity = new TmplLookupButton();
  TmplJTextField jtfldActivity = new TmplJTextField();
  TmplLookupField jlfldActivity = new TmplLookupField();

  public MonitorPlanDefinitionPane(DataSource datasource, FW_ComponentListener fwCListener) {
    this.datasource = datasource;
    this.fwCListener = fwCListener;
    initialize();

    datasource.addDatasourceListener(jtfldMonitorPlanID);
    datasource.addDatasourceListener(jtfldMonitorPlanDescription);
    datasource.addDatasourceListener(jcmbPeridiocity);
    datasource.addDatasourceListener(jtfldNextMonitorDate);
    datasource.addDatasourceListener(jlbtnEnvAspect);
    datasource.addDatasourceListener(jtfldEnvAspect);
    datasource.addDatasourceListener(jlfldEnvAspect);
    datasource.addDatasourceListener(jlbtnActivity);
    datasource.addDatasourceListener(jtfldActivity);
    datasource.addDatasourceListener(jlfldActivity);

    fwCListener.addFWComponentListener(jtfldMonitorPlanID);
    fwCListener.addFWComponentListener(jtfldMonitorPlanDescription);
    fwCListener.addFWComponentListener(jcmbPeridiocity);
    fwCListener.addFWComponentListener(jtfldNextMonitorDate);
    fwCListener.addFWComponentListener(jlbtnEnvAspect);
    fwCListener.addFWComponentListener(jtfldEnvAspect);
    fwCListener.addFWComponentListener(jlfldEnvAspect);
    fwCListener.addFWComponentListener(jlbtnActivity);
    fwCListener.addFWComponentListener(jtfldActivity);
    fwCListener.addFWComponentListener(jlfldActivity);
  }

  private void initialize() {
    setLayout(new BorderLayout());
    setOpaque(false);

    jlblMonitorPlanID.setText(res.getString("monitorPlan.label.code"));
    jtfldMonitorPlanID.setField("monitorPlanID");
    jtfldMonitorPlanID.setHolder(jlblMonitorPlanID);

    jlblMonitorPlanDescription.setText(res.getString("monitorPlan.label.desc"));
    jtfldMonitorPlanDescription.setField("monitorPlanDescription");
    jtfldMonitorPlanDescription.setHolder(jlblMonitorPlanDescription);

    jlblPeridiocity.setText(res.getString("monitorPlan.label.periodicity"));
    jcmbPeridiocity.setField("periodicity");
    jcmbPeridiocity.setDataItems(new Object[] {"", "Diário", "Semanal", "Quinzenal", "Mensal", "Bimensal", "Trimestral", "Quadrimestral", "Semestral", "Anual"});
    jcmbPeridiocity.setDataValues(new Object[] {null, "d", "s", "q", "M", "B", "T", "Q", "S", "A"});
    jcmbPeridiocity.setHolder(jlblPeridiocity);

    jlblNextMonitorDate.setText(res.getString("monitorPlan.label.nextMonitorDate"));
    jtfldNextMonitorDate.setField("nextMonitorDate");
    jtfldNextMonitorDate.setHolder(jlblNextMonitorDate);

    jtfldEnvAspect.setLabel(res.getString("monitorPlan.label.envAspect"));
    jtfldEnvAspect.setField("fkEnvAspectID");
    jlbtnEnvAspect.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlbtnEnvAspect.setText(res.getString("monitorPlan.label.envAspect"));
    jlbtnEnvAspect.setTitle(res.getString("monitorPlan.label.envAspectlist"));
    jlbtnEnvAspect.setDefaultFill(jtfldEnvAspect);
    jlfldEnvAspect.setUrl("pt.inescporto.siasoft.go.aa.ejb.session.EnvironmentAspect");
    jlfldEnvAspect.setDefaultRefField(jtfldEnvAspect);

    jtfldActivity.setLabel(res.getString("monitorPlan.label.activity"));
    jtfldActivity.setField("fkActivityID");
    if (!MenuSingleton.isSupplier())
      jlbtnActivity.setLinkCondition("fkEnterpriseID = '" + MenuSingleton.getEnterprise() + "'");
    jlbtnActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    jlbtnActivity.setText(res.getString("monitorPlan.label.activity"));
    jlbtnActivity.setTitle("Lista de Actividades");
    jlbtnActivity.setDefaultFill(jtfldActivity);
    jlfldActivity.setUrl("pt.inescporto.siasoft.proc.ejb.session.Activity");
    jlfldActivity.setDefaultRefField(jtfldActivity);


    FormLayout formLayout = new FormLayout("5px, left:pref, 4dlu, 70dlu, 4dlu, left:pref, 4dlu, 70dlu, 100dlu:grow, 5px",
                                           "5px, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 5px");
    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(formLayout);
    formLayout.setRowGroups(new int[][] { {2, 4, 6, 8, 10}
    });
    CellConstraints cc = new CellConstraints();

    content.add(jlblMonitorPlanID, cc.xy(2, 2));
    content.add(jtfldMonitorPlanID, cc.xy(4, 2));

    content.add(jlblMonitorPlanDescription, cc.xy(2, 4));
    content.add(jtfldMonitorPlanDescription, cc.xyw(4, 4, 6));

    content.add(jlblPeridiocity, cc.xy(2, 6));
    content.add(jcmbPeridiocity, cc.xy(4, 6));

    content.add(jlblNextMonitorDate, cc.xy(6, 6));
    content.add(jtfldNextMonitorDate, cc.xy(8, 6));

    content.add(jlbtnEnvAspect, cc.xy(2, 8, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldEnvAspect, cc.xy(4, 8));
    content.add(jlfldEnvAspect, cc.xyw(6, 8, 4));

    content.add(jlbtnActivity, cc.xy(2, 10, CellConstraints.FILL, CellConstraints.DEFAULT));
    content.add(jtfldActivity, cc.xy(4, 10));
    content.add(jlfldActivity, cc.xyw(6, 10, 4));

    add(content, BorderLayout.NORTH);
    add(new MPParameterTabbedPane(datasource, fwCListener), BorderLayout.CENTER);
  }
}
