package pt.inescporto.siasoft.asq.client.rmi.forms;

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
public interface TreeRefreshListener {
  public void refreshTree(String regulatoryID, String lrID, int action);
}
