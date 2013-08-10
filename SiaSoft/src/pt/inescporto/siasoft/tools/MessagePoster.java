package pt.inescporto.siasoft.tools;

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
public interface MessagePoster {
  public void postMessage(String msg);

  public void postMessageNL(String msg);

  public void postSeparator();
}
