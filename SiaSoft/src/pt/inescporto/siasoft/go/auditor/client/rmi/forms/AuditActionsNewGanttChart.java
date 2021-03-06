package pt.inescporto.siasoft.go.auditor.client.rmi.forms;

import com.dlsc.djt.gantt.DjtGanttChart;
import java.awt.BorderLayout;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.design.TmplJButtonUpdate;
import java.util.Date;
import javax.swing.JPanel;
import com.dlsc.djt.gantt.scheduling.DjtEventEntry;
import java.awt.Color;
import pt.inescporto.template.client.util.DataSourceException;
import com.jgoodies.forms.layout.FormLayout;
import com.dlsc.djt.gantt.DjtDiagram;
import java.util.Collection;
import com.dlsc.djt.gantt.DjtSheet;
import com.dlsc.djt.gantt.scheduling.DjtActivityEntry;
import java.util.GregorianCalendar;
import java.util.Calendar;
import pt.inescporto.template.client.event.TemplateEvent;
import java.util.ResourceBundle;
import java.util.Vector;
import com.jgoodies.forms.layout.CellConstraints;
import pt.inescporto.siasoft.go.auditor.ejb.dao.AuditActionTypeDao;
import com.dlsc.djt.table.DjtTable;
import com.dlsc.djt.gantt.DjtGanttToolBar;
import com.dlsc.djt.DjtColorManager;
import com.dlsc.djt.gantt.action.DjtColorLegendAction;
import java.awt.Font;

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
public class AuditActionsNewGanttChart extends JPanel implements DataSourceListener {
  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.auditor.client.rmi.forms.FormResources");
  AuditActionsGanttDataSource agDS = null;
  TmplJButtonUpdate update = new TmplJButtonUpdate();
  Vector dataVector = null;

  public AuditActionsNewGanttChart(AuditActionsGanttDataSource agDS, TmplJButtonUpdate update) {

    this.agDS = agDS;
    agDS.addDatasourceListener(this);

    this.update = update;
    initialize();
  }

  private DjtDiagram createAllSeries() {
    DjtDiagram diagramA = new DjtDiagram(res.getString("label.action"));
    diagramA.setFont(new Font("Dialog", 0, 10));

    AuditActionTypeDao auditActionTypeDao = new AuditActionTypeDao();

    for (int i = 0; i < dataVector.size(); i++) {
      auditActionTypeDao = (AuditActionTypeDao)dataVector.elementAt(i);

      Date endDate = new Date();
      Date startDate = new Date();
      Date startDateAct = new Date();
      Date endDateAct = new Date();

      try {
	startDate = new Date(auditActionTypeDao.planStartDate.getValue().getTime());
	endDate = new Date(auditActionTypeDao.planEndDate.getValue().getTime());
	startDateAct = new Date(auditActionTypeDao.startDate.getValue().getTime());
	endDateAct = new Date(auditActionTypeDao.endDate.getValue().getTime());
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

      DjtDiagram diagramA1 = new DjtDiagram(auditActionTypeDao.auditActionTypeID.getValue() + " " + auditActionTypeDao.auditActionTypeDescription.getValue());
      diagramA1.setFont(new Font("Dialog", 0, 10));

      if (endDate.getTime() > startDate.getTime()) {
	DjtActivityEntry activity = new DjtActivityEntry(auditActionTypeDao.auditActionTypeID.getValue() + " " + auditActionTypeDao.auditActionTypeDescription.getValue(), c1, c2); //task,start date,end date
	activity.setBackground(Color.blue);
	activity.setToolTipText(res.getString("aaGantt.label.planned") + (i + 1) + "");
	activity.showBackdrop(true);
	diagramA1.addEntry(activity);
      }

      if (endDate.getTime() <= startDate.getTime()) {
	DjtEventEntry entry = new DjtEventEntry(auditActionTypeDao.auditActionTypeID.getValue() + " " + auditActionTypeDao.auditActionTypeDescription.getValue(), c1); //task,end date
	entry.setToolTipText(res.getString("aaGantt.label.planned") + (i + 1) + "");
	entry.setBackground(Color.BLUE);
	entry.showBackdrop(true);
	diagramA1.addEntry(entry);
      }

      if (endDateAct.getTime() < startDateAct.getTime()) {
	DjtEventEntry entry = new DjtEventEntry(auditActionTypeDao.auditActionTypeID.getValue() + " " + auditActionTypeDao.auditActionTypeDescription.getValue(), c3); //task,end date
	entry.setBackground(Color.RED);
	entry.setToolTipText(res.getString("aaGantt.label.actual") + (i + 1) + "");
	diagramA1.addEntry(entry);
      }

      if (endDateAct.getTime() > startDateAct.getTime()) {
	DjtActivityEntry activity = new DjtActivityEntry(auditActionTypeDao.auditActionTypeID.getValue() + " " + auditActionTypeDao.auditActionTypeDescription.getValue(), c3, c4); //task,start date,end date
	activity.setBackground(Color.red);
	activity.setToolTipText(res.getString("aaGantt.label.actual") + (i + 1) + "");
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
      Collection all = agDS.listAll();
      dataVector = new Vector(all);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    //The Sheet
    DjtSheet sheet = new DjtSheet(res.getString("aaGantt.label.planning"));
    diagramA = createAllSeries();
    diagramtest = diagramA;
    sheet.addDiagram(diagramA);
    sheet.addAction(new DjtColorLegendAction());

    DjtTable djtTable = sheet.getTable();
    djtTable.open(diagramtest);
    djtTable.setKeyName(res.getString("label.name"));

    //Color Legend
    DjtColorManager m = new DjtColorManager();
    m.addColor(res.getString("aaGantt.label.actual"), Color.red);
    m.addColor(res.getString("aaGantt.label.planned"), Color.blue);
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

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, pref,130dlu:grow, 5px");
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
