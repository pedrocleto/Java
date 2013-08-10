package pt.inescporto.template.client.design;

import pt.inescporto.template.client.event.TemplateComponentListener;

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
public interface FW_ComponentListener {
  public void addFWComponentListener(TemplateComponentListener l);

  public void removeFWComponentListener(TemplateComponentListener l);

  public void lockParent();

  public void unlockParent();
}
