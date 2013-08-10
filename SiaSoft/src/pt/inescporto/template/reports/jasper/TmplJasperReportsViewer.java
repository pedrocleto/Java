package pt.inescporto.template.reports.jasper;

import java.util.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.JFrame;
import java.io.InputStream;
import pt.inescporto.template.dao.UserException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TmplJasperReportsViewer {
  private JRDataSource repDs = null;

  String jasperFilename = null;
  InputStream stream = null;
  Map parameters;
  Collection reportData;
  String title;

  private JasperViewer jasperViewer = null;

  public TmplJasperReportsViewer(Collection reportData, String jasperFilename, Map parameters, String title) {
    this.reportData = reportData;
    this.jasperFilename = jasperFilename;
    this.parameters = parameters;
    this.title = title;
  }

  public TmplJasperReportsViewer(Collection reportData, InputStream stream, Map parameters, String title) {
    this.reportData = reportData;
    this.stream = stream;
    this.parameters = parameters;
    this.title = title;
  }

  public TmplJasperReportsViewer(Collection reportData, String jasperFilename, Map parameters, String title, JRDataSource datasource) {
    this.reportData = reportData;
    this.jasperFilename = jasperFilename;
    this.parameters = parameters;
    this.title = title;
    this.repDs = datasource;
  }

  public TmplJasperReportsViewer(Collection reportData, InputStream stream, Map parameters, String title, JRDataSource datasource) {
    this.reportData = reportData;
    this.stream = stream;
    this.parameters = parameters;
    this.title = title;
    this.repDs = datasource;
  }

  public void viewReport() throws UserException {
    try {
      Vector v = new Vector(reportData);
      if (repDs == null)
	repDs = new TmplAttrsDataSource(v);

//      parameters.put("REPORT_RESOURCE_BUNDLE",ResourceBundle.getBundle("pt.inescporto.wcontrol.common.language.ReportsResource"));
      //JRViewer.setDefaultLocale();

//      JasperPrint print = JasperFillManager.fillReport("fp_warehouse.jasper",parameters,repDs);
      JasperPrint print = null;

      if (jasperFilename != null) {
	print = JasperFillManager.fillReport(jasperFilename, parameters, repDs);
      }
      else
	if (stream != null) {
	  print = JasperFillManager.fillReport(stream, parameters, repDs);
	}

      net.sf.jasperreports.view.JRViewer jrv = null;
      net.sf.jasperreports.engine.JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
      jasperViewer = new JasperViewer(print, false);
      jasperViewer.setTitle(title);
      int state = jasperViewer.getExtendedState();
      // Set the maximized bits
      state |= JFrame.MAXIMIZED_BOTH;
      // Maximize the frame
      jasperViewer.setExtendedState(state);
      jasperViewer.setVisible(true);

      if (stream != null) {
	stream.close();
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      throw new UserException(ex.getMessage());
    }
  }

  public void setVisible(boolean value) {
    if (jasperViewer != null) {
      jasperViewer.setVisible(value);
    }
  }

}
