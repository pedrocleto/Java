package pt.inescporto.template.reports.forms;

import pt.inescporto.template.reports.forms.parser.objects.*;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.Vector;
import java.util.Enumeration;
import java.net.URL;
import java.io.*;
import pt.inescporto.template.reports.ConfigDirectoryLoader;
import pt.inescporto.template.reports.forms.parser.FormReportConfigParser;

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
public class FormsReportConfigurationLoader {
  private static String configFile = ConfigDirectoryLoader.getConfigDir() + "/form-rep-config.xml"; //System.getProperty("user.dir") + "/config/form-rep-config.xml";
  private static Map configuration;

  public FormsReportConfigurationLoader() {}


  public static void loadConfiguration() {
    try {
      FormReportConfig frepConfig = new FormReportConfig();
      File file = new File(configFile);
      if ( !file.exists() ) {
        InputStream str = FormsReportConfigurationLoader.class.getResourceAsStream("form-rep-config.xml");
        InputStreamReader isr = new InputStreamReader(str);
        frepConfig = (FormReportConfig)frepConfig.unmarshal(isr);
      } else {
        FileReader freader = new FileReader(file);
        frepConfig = (FormReportConfig) frepConfig.unmarshal(freader);
      }
      FormReportConfigParser frepParser = new FormReportConfigParser();
      configuration = frepParser.parseConfig(frepConfig);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Vector getReportMapping(String formID) {
    try {
      Vector vec = new Vector();

      Object obj = configuration.get(formID);
      if (obj == null) {
        return new Vector();
      } else if (obj instanceof Form) {
        Form form = (Form) obj;
        Enumeration enumr = form.enumerateReport();
        while (enumr.hasMoreElements()) {
          Report report = (Report) enumr.nextElement();
          ReportDataDao rattrs = new ReportDataDao(report.getName(),report.getAnchor(),report.getFile());
          getSubreports(report, rattrs);
          vec.add(rattrs);
        }
      }
      return vec;
    } catch (Exception ex) {
      ex.printStackTrace();
      return new Vector();
    }
  }

  public static void getSubreports(Report report, ReportDataDao reportDao) {
    Enumeration enumr = report.enumerateReport();
    while (enumr.hasMoreElements()) {
      Report subreport = (Report) enumr.nextElement();
      ReportDataDao rattrs = new ReportDataDao(subreport.getName(),subreport.getAnchor(),subreport.getFile());
      getSubreports(subreport, rattrs);
      reportDao.subreports.add(rattrs);
      try {
        java.net.URL url = Class.forName(subreport.getAnchor()).getResource(subreport.getFile());
        reportDao.parameters.put(subreport.getName(), url.openStream());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}
