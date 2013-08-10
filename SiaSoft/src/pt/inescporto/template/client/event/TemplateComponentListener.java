package pt.inescporto.template.client.event;

import java.util.*;
import javax.swing.JComponent;

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

public interface TemplateComponentListener extends EventListener {
  public void tmplPermissions(TemplateEvent e);

  public void tmplMode(TemplateEvent e);

  public JComponent tmplGetComponent();

  public void tmplEnable(TemplateEvent e);

  public boolean tmplRequired(TemplateEvent e);

  public boolean tmplValidate(TemplateEvent e);

  public void tmplDispose(TemplateEvent e);
}
