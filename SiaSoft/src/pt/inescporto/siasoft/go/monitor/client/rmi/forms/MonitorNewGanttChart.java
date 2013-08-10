package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import java.util.ResourceBundle;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import java.util.Calendar;
import com.dlsc.djt.gantt.DjtDiagram;
import com.dlsc.djt.gantt.scheduling.DjtActivityEntry;
import com.dlsc.djt.gantt.scheduling.DjtEventEntry;
import com.dlsc.djt.gantt.DjtSheet;
import com.dlsc.djt.gantt.DjtGanttChart;
import java.util.Date;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorStatedDao;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.Collection;
import pt.inescporto.template.client.util.DataSourceException;
import java.awt.Color;
import com.dlsc.djt.table.DjtTable;
import com.dlsc.djt.DjtColorManager;
import com.dlsc.djt.gantt.action.DjtColorLegendAction;
import com.dlsc.djt.gantt.DjtGanttToolBar;
import java.awt.Font;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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

public class MonitorNewGanttChart extends JPanel implements DataSourceListener {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");
  MonitorGanttDatasource monGanttDS = null;
  TmplJButtonUpdate update = new TmplJButtonUpdate();
  Vector dataVector = null;
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  public MonitorNewGanttChart(MonitorGanttDatasource monGanttDS, TmplJButtonUpdate update) {

    this.monGanttDS = monGanttDS;
    monGanttDS.addDatasourceListener(this);

    this.update = update;
    initialize();
  }

  private DjtDiagram createAllSeries() {
    DjtDiagram diagramA = new DjtDiagram(res.getString("monitorGantt.label.monitoring"));
    diagramA.setFont(new Font("Dialog", 0, 10));

    MonitorStatedDao mon = new MonitorStatedDao();

    for (int i = 0; i < dataVector.size(); i++) {
      mon = (MonitorStatedDao)dataVector.elementAt(i);

      Date endDate = new Date();
      Date startDate = new Date();
      Date startDateAct = new Date();
      Date endDateAct = new Date();

      try {
	startDate = new Date(mon.monitorDate.getValue().getTime());
	endDate = new Date(mon.monitorEndDate.getValue().getTime());
	startDateAct = new Date(mon.monitorRealStartDate.getValue().getTime());
	endDateAct = new Date(mon.monitorRealEndDate.getValue().getTime());
      }
      catch (Exception ex1) {
      }

      Calendar c1 = new GregorianCalendar();
      Calendar c2 = new GregorianCalendar();
      Calendar c3 = new GregorianCalendar();
      Calendar c4 = new GregorianCalendar();

      c1.setTime(startDate);
      c2.setTime(endDate);
      c3.setTime(startDateAct);
      c4.setTime(endDateAct);

      DjtDiagram diagramA1 = new DjtDiagram(res.getString("monitorDef.label.plan") + ": " + mon.monitorPlanID.getValue() +" "+res.getString("monitorDef.label.monitorDate")+": "+dateFormat.format(mon.monitorDate.getValue()));
      diagramA1.setFont(new Font("Dialog", 0, 10));

      if (endDate.getTime() > startDate.getTime()) {
	DjtActivityEntry activity = new DjtActivityEntry("" + (i + 1) + "", c1, c2); //task,start date,end date
	activity.setBackground(Color.blue);
	activity.showBackdrop(true);
	activity.setToolTipText(res.getString("monitorGantt.label.planned") + (i + 1) + "");
	diagramA1.addEntry(activity);
      }

      if (endDate.getTime() <= startDate.getTime()) {
	DjtEventEntry entry = new DjtEventEntry("" + (i + 1) + "", c1); //task,end date
	entry.setToolTipText(res.getString("monitorGantt.label.planned") + (i + 1) + "");
	entry.setBackground(Color.BLUE);
	entry.showBackdrop(true);
	diagramA1.addEntry(entry);
      }

      if (endDateAct.getTime() < startDateAct.getTime()) {
	DjtEventEntry entry = new DjtEventEntry("" + (i + 1) + "", c3); //task,end date
	entry.setBackground(Color.RED);
	entry.setToolTipText(res.getString("monitorGantt.label.actual") + (i + 1) + "");
	diagramA1.addEntry(entry);
      }

      if (endDateAct.getTime() > startDateAct.getTime()) {
	DjtActivityEntry activity = new DjtActivityEntry("" + (i + 1) + "", c3, c4); //task,start date,end date
	activity.setBackground(Color.red);
	activity.setToolTipText(res.getString("monitorGantt.label.actual") + (i + 1) + "");
	diagramA1.addEntry(activity);
      }
      diagramA.addChild(diagramA1);
    }
    return diagramA;
  }

  private DjtSheet createSheet() {
    dataVector = new Vector();
    DjtDiagram diagramA = null;
    DjtDiagram diagramtest = null;

    try {
      Collection all = monGanttDS.listAll();
      dataVector = new Vector(all);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    //The Sheet
    DjtSheet sheet = new DjtSheet(res.getString("monitorGantt.label.planning"));
    diagramA = createAllSeries();
    diagramtest = diagramA;
    sheet.addDiagram(diagramA);
    sheet.addAction(new DjtColorLegendAction());


    DjtTable djtTable = sheet.getTable();
    djtTable.open(diagramtest);
    djtTable.setKeyName(res.getString("label.name"));


    //Color Legend
    DjtColorManager m = new DjtColorManager();
    m.addColor(res.getString("monitorGantt.label.actual"), Color.red);
    m.addColor(res.getString("monitorGantt.label.planned"), Color.blue);
    sheet.setColorManager(m);

    try {
      if (diagramtest.getChildren().length <= 0)
	sheet.removeAllDiagrams();
    }
    catch (NullPointerException ex) {
      sheet.removeAllDiagrams();
    }

    return sheet;
  }

  private void initialize() {
    removeAll();
    setLayout(new BorderLayout());
    setOpaque(false);

    // The Gantt.
    DjtGanttChart gantt = new DjtGanttChart();
    gantt.setMultipleSheets(false);
    gantt.addSheet(createSheet());

    //Tool Bar
    DjtGanttToolBar ganttToolBar = new DjtGanttToolBar();
    ganttToolBar.setGanttChart(gantt);

    FormLayout formLayout = new FormLayout("5px,pref:grow, 5px",
					   "5px,pref,130dlu:grow, 5px");
    JPanel jpnlChart = new JPanel();
    jpnlChart.setOpaque(false);
    jpnlChart.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] { {3}
    });

    CellConstraints cc = new CellConstraints();
    jpnlChart.add(ganttToolBar, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));
    jpnlChart.add(gantt, cc.xy(2, 3, CellConstraints.FILL, CellConstraints.FILL));

    add(jpnlChart, BorderLayout.CENTER);
    add(update, BorderLayout.SOUTH);
  }

  /**
   * ***************************************************************************
   *       Implementation of the <code>DataSourceListener<\code> Interface
   * ***************************************************************************
   */

  /**
   *
   * @param e TemplateEvent
   */
  public void tmplInitialize(TemplateEvent e) {
  }

  public void tmplRefresh(TemplateEvent e) {
    //System.out.println("tablemodel tmplRefresh");
    initialize();
  }

  public void tmplSave(TemplateEvent e) {
    //System.out.println("tablemodel tmplSave");
  }

  public void tmplLink(TemplateEvent e) {
    //System.out.println("tablemodel tmplLink");
  }
}