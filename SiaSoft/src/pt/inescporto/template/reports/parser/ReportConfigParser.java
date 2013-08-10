package pt.inescporto.template.reports.parser;

import java.util.HashMap;
import java.util.Enumeration;
import pt.inescporto.template.reports.parser.objects.*;
import pt.inescporto.template.reports.parser.objects.types.ParameterTypeType;
import javax.swing.ImageIcon;
import java.util.Map;

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
public class ReportConfigParser {
  public ReportConfigParser() {}

  public void parseConfig(Map parameters, ReportConfig data) {
    try {
      Jasper jasper = data.getJasper();

      if (jasper != null) {
        Enumeration enumr = jasper.enumerateParameter();
        while (enumr.hasMoreElements()) {
          Parameter parm = (Parameter) enumr.nextElement();
          ParameterTypeType ptype = parm.getType();
          if (ptype.getType() == ptype.JAVA_AWT_IMAGE_TYPE) {
            ImageIcon repImage = new ImageIcon(Class.forName(parm.getAnchor()).getResource(parm.getValue()));
            parameters.put(parm.getName(), repImage.getImage());
          } else if (ptype.getType() == ptype.JAVA_LANG_STRING_TYPE) {
            parameters.put(parm.getName(), parm.getValue());
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

  }

}
