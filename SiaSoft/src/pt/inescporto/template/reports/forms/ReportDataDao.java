package pt.inescporto.template.reports.forms;

import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import java.util.*;

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
public class ReportDataDao {
  public TplString name        = new TplString( "name", TmplKeyTypes.NOKEY, true );
  public TplString anchor        = new TplString( "anchor", TmplKeyTypes.NOKEY, true );
  public TplString file        = new TplString( "file", TmplKeyTypes.NOKEY, true );
  public Vector    subreports  = new Vector();
  Map parameters = new HashMap();

  public ReportDataDao(String name, String anchor, String file) {
    this.name.setValue(name);
    this.anchor.setValue(anchor);
    this.file.setValue(file);
  }
}
