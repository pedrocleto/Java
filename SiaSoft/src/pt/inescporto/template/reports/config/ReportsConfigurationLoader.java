package pt.inescporto.template.reports.config;

import java.io.File;
import java.io.FileReader;
import pt.inescporto.template.reports.parser.objects.ReportConfig;
import java.util.Map;
import java.util.HashMap;
import pt.inescporto.template.reports.parser.ReportConfigParser;
import pt.inescporto.template.reports.jasper.JasperReportData;
import java.io.InputStream;
import java.io.InputStreamReader;
import pt.inescporto.template.reports.ConfigDirectoryLoader;

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
public abstract class ReportsConfigurationLoader {

  private static Map parameters = new HashMap();
  private static JasperReportData jreportData;

  private static String rconfigFile = ConfigDirectoryLoader.getConfigDir() + "/report-config.xml"; //System.getProperty("user.dir") + "/config/report-config.xml";

  public static void loadConfiguration() {
    try {
      ReportConfig rConfig = new ReportConfig();

      try {
        //try to load config from external file
        File file = new File(rconfigFile);
        FileReader freader = new FileReader(file);
        rConfig = (ReportConfig)rConfig.unmarshal(freader);
      } catch (Exception ex1) {
        //if it fails load the internal one
        //Files can only be used to access files in the local file system!
        //if we need to access files inside a jar we must use streams instead!
        //java.net.URL repUrl = ReportsConfigurationLoader.class.getResource("report-config.xml");
        //File file = new File(repUrl.getPath().replaceAll("%20"," "));
        //FileReader freader = new FileReader(file);
        //rConfig = (ReportConfig)rConfig.unmarshal(freader);
        InputStream str = ReportsConfigurationLoader.class.getResourceAsStream("report-config.xml");
        InputStreamReader isr = new InputStreamReader(str);
        rConfig = (ReportConfig)rConfig.unmarshal(isr);
      }

      ReportConfigParser rParser = new ReportConfigParser();
      rParser.parseConfig(parameters,rConfig);

      jreportData = new JasperReportData(parameters);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static JasperReportData getJasperReportData() {
    return jreportData;
  }

  public static Object getReportData() {
    return getJasperReportData();
  }
}
