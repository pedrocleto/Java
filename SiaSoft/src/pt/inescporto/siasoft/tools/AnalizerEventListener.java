package pt.inescporto.siasoft.tools;

import java.util.Hashtable;
import pt.inescporto.siasoft.tools.dbanalizer.sql.TableActions;

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
public interface AnalizerEventListener {
  public void setSourceStructure(Hashtable<String, TableNode> tableStructure);

  public void setTargetStructure(Hashtable<String, TableNode> tableStructure);

  public void setTableActions(Hashtable<String, TableActions> tableActions);
}
