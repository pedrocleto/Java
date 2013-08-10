package pt.inescporto.template.reports.forms.parser;

import pt.inescporto.template.reports.forms.parser.objects.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

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
public class FormReportConfigParser {
  public FormReportConfigParser() {}

  public Map parseConfig(FormReportConfig frepConfig) {
    Map map = new HashMap();

    try {
      Enumeration enumr = frepConfig.enumerateForm();
      while (enumr.hasMoreElements()) {
        Form form = (Form) enumr.nextElement();
        map.put(form.getName(),form);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return new HashMap();
    }
    return map;
  }

}
