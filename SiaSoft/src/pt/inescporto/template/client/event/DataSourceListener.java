package pt.inescporto.template.client.event;

public interface DataSourceListener extends java.util.EventListener {
  public void tmplInitialize(TemplateEvent e);

  public void tmplRefresh(TemplateEvent e);

  public void tmplSave(TemplateEvent e);

  public void tmplLink(TemplateEvent e);
}
