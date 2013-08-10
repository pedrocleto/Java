package pt.inescporto.siasoft.proc.client.rmi.editor;

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
public interface ProcessEventInterface {
  public String getProcessID();
  public void processChanged(String processID);
}
