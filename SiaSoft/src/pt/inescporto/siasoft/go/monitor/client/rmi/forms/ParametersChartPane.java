package pt.inescporto.siasoft.go.monitor.client.rmi.forms;

import javax.swing.JPanel;
import pt.inescporto.template.client.design.FW_ComponentListener;
import pt.inescporto.template.client.util.DataSource;
import java.util.ResourceBundle;
import org.jfree.chart.*;
import org.jfree.*;
import org.jfree.ui.*;
import java.awt.Color;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.util.Collection;
import pt.inescporto.template.client.util.*;
import java.util.Vector;
import java.util.Date;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorParameterDao;
import pt.inescporto.siasoft.go.monitor.ejb.dao.MonitorPlanParameterDao;
import pt.inescporto.template.client.event.DataSourceListener;
import pt.inescporto.template.client.event.TemplateEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
public class ParametersChartPane extends JPanel implements DataSourceListener {
  FW_ComponentListener fwCListener = null;
  DataSource datasource = null;
  DataSource datasource2 = null;
  TimeSeriesCollection dataset = new TimeSeriesCollection();

  Collection all = new Vector();
  JFreeChart chart = null;

  static ResourceBundle res = ResourceBundle.getBundle("pt.inescporto.siasoft.go.monitor.client.rmi.forms.FormResources");

  public ParametersChartPane(DataSource datasource, FW_ComponentListener fwCListener) {
    try {
      this.datasource = datasource.getDataSourceByName("ParametersChartDS");
      this.datasource.addDatasourceListener(this);

      this.datasource2 = datasource.getDataSourceByName("MonitorPlanParameters");
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }
    this.fwCListener = fwCListener;

    initialize();
  }

  private XYDataset createDataset() {
    dataset.removeAllSeries();

    TimeSeries series = createSeries();
    series.fireSeriesChanged();

    dataset.addSeries(series);

    return dataset;
  }

  private JFreeChart createChart(XYDataset data) {

    chart = ChartFactory.createTimeSeriesChart(res.getString("parametersChart.label.title"), res.getString("parametersChart.label.date"), res.getString("parametersChart.label.values"), data, true, true, false);

    XYPlot plot = chart.getXYPlot();
    chart.setBackgroundPaint(Color.white);
    chart.clearSubtitles();

    DateAxis domainAxis = (DateAxis)plot.getDomainAxis();
    domainAxis.setUpperMargin(0.50);
    plot.setDomainAxis(domainAxis);

    ValueAxis rangeAxis = plot.getRangeAxis();
    rangeAxis.setUpperMargin(0.50);
    rangeAxis.setLowerMargin(0.30);
    rangeAxis.setLowerBound(0.0);

    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
    renderer.setToolTipGenerator(
	new StandardXYToolTipGenerator("{0}: ({1}, {2})", DateFormat.getDateInstance(), NumberFormat.getNumberInstance())
	);
    renderer.setPaint(Color.blue);
    renderer.setShapesVisible(true);
    renderer.setDrawOutlines(true);
    renderer.setUseFillPaint(true);
    renderer.setFillPaint(Color.white);

    return chart;
  }

  private void createMarker() {

    MonitorPlanParameterDao mon2 = new MonitorPlanParameterDao();
    Marker target = new ValueMarker(0.0F);
    XYPlot plot = chart.getXYPlot();
    float max = 0.0F;

    if (all.size() <= 0) {
      max = 0.0F;
    }
    else {
      try {
	mon2 = (MonitorPlanParameterDao)datasource2.getCurrentRecord();
	max = (float)mon2.maxValue.getValue();
        plot.clearRangeMarkers();
        if(mon2.vleIS.getValue().equals(true)){
	  plot.clearRangeMarkers();
	  target = new ValueMarker(max);
	  target.setPaint(Color.red);
	  target.setLabel(res.getString("parametersChart.label.maxvalueMax"));
	  target.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
	  target.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
	  plot.addRangeMarker(target);
	  chart.fireChartChanged();
	}
        else if (mon2.vleIS.getValue().equals(false)) {
	    plot.clearRangeMarkers();
	    target = new ValueMarker(max);
	    target.setPaint(Color.green);
	    target.setLabel(res.getString("parametersChart.label.maxvalueMin"));
	    target.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
	    target.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
	    plot.addRangeMarker(target);
	    chart.fireChartChanged();
	  }
      }
      catch (Exception ex1) {
      }
    }
  }

  private TimeSeries createSeries() {
    TimeSeries series = new TimeSeries(res.getString("parametersChart.label.monitoring"), Day.class);
    Vector dataVector = new Vector();

    try {
      all = datasource.listAll();
      dataVector = new Vector(all);
    }
    catch (DataSourceException ex) {
      ex.printStackTrace();
    }

    MonitorParameterDao mon = new MonitorParameterDao();

    for (int i = 0; i < dataVector.size(); i++) {
      mon = (MonitorParameterDao)dataVector.elementAt(i);
      float x = 0.0F;
      try {
	x = (float)mon.valueReaded.getValue();
        series.addOrUpdate(new Day(new Date(mon.startDate.getValue().getTime())), x); //date,value read
      }
      catch (Exception ex1) {
      }

    }

    return series;
  }

  private void initialize() {
    removeAll();
    setLayout(new BorderLayout());
    setOpaque(false);

    chart = createChart(createDataset());

    FormLayout formLayout = new FormLayout("5px, pref:grow, 5px",
					   "5px, 90dlu:grow, 5px");
    JPanel jpnlChart = new JPanel();
    jpnlChart.setOpaque(false);
    jpnlChart.setLayout(formLayout);

    formLayout.setRowGroups(new int[][] { {2}
    });
    CellConstraints cc = new CellConstraints();
    ChartPanel chartPanel = new ChartPanel(chart);
    JScrollPane jScrollPane1 = new JScrollPane();

    chartPanel.setPreferredSize(new Dimension(500,250));
    chartPanel.setAutoscrolls(true);
    chartPanel.setReshowDelay(0);
    chartPanel.setInitialDelay(0);

    jScrollPane1.getViewport().add(chartPanel);
    jpnlChart.add(jScrollPane1, cc.xy(2, 2, CellConstraints.FILL, CellConstraints.FILL));

    add(jpnlChart, BorderLayout.CENTER);
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
    createDataset();
    createMarker();
  }

  public void tmplSave(TemplateEvent e) {
    //System.out.println("tablemodel tmplSave");

  }

  public void tmplLink(TemplateEvent e) {
    //System.out.println("tablemodel tmplLink");
  }
}