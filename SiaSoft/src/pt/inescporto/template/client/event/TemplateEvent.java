package pt.inescporto.template.client.event;

import java.util.Vector;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.client.util.LinkConditionNode;
import java.util.Hashtable;

public class TemplateEvent extends java.util.EventObject implements java.io.Serializable {
  protected int modeForLocking;
  protected int mode;
  protected boolean enable;
  protected Object compFailed;
  protected int permission;
  protected Hashtable<String, Integer> permFieldList = null;

  protected String linkCondition = null;
  protected Vector binds = null;
  protected TplObject link = null;
  protected Hashtable<String, LinkConditionNode> linkList = new Hashtable<String,LinkConditionNode>();

  public TemplateEvent(Object source) {
    super(source);
  }

  public TemplateEvent(Object source, int permission) {
    super(source);
    this.permission = permission;
  }

  public void setMode(int mode) {
    this.mode = mode;
  }

  public int getMode() {
    return mode;
  }

  public void setModeForLocking(int mode) {
    this.modeForLocking = mode;
  }

  public int getModeForLocking() {
    return modeForLocking;
  }

  public void setEnabled(boolean enable) {
    this.enable = enable;
  }

  public boolean getEnabled() {
    return enable;
  }

  public void setCompFailed(Object compFailed) {
    this.compFailed = compFailed;
  }

  public Object getCompFailed() {
    return compFailed;
  }

  public void setPermission(int permission) {
    this.permission = permission;
  }

  public int getPermission() {
    return permission;
  }

  public void setPermFieldList(Hashtable<String, Integer> permFieldList) {
    this.permFieldList = permFieldList;
  }

  public int getPermissionForField(String fieldName) {
    return (permFieldList != null && permFieldList.containsKey(fieldName)) ? permFieldList.get(fieldName).intValue() : -1;
  }

  public void setLinkCondition(String linkCondition) {
    this.linkCondition = linkCondition;
  }

  public String getLinkCondition() {
    return linkCondition;
  }

  public void setBinds(Vector binds) {
    this.binds = binds;
  }

  public Vector getBinds() {
    return binds;
  }

  public void setLink(TplObject link) {
    this.link = link;
  }

  public TplObject getLink() {
    return link;
  }

  public String toString() {
    StringBuffer s = new StringBuffer();

    s.append("Mode : ");
    s.append(mode + "\r\n");
    s.append("Linkcondition : ");
    s.append(linkCondition + "\r\n");
    /*        protected boolean enable;
	    protected Object compFailed;
	    protected int permission;
	    protected int type;
	    protected String linkCondition = null;
	    protected Vector binds = null;
	    protected TplObject link = null;*/

    return s.toString();
  }

  public void setLinkList(Hashtable<String, LinkConditionNode> linkList) {
    this.linkList = linkList;
  }

  public void addLinkConditionNode(String name, String stmt, Vector binds) {
    linkList.put(name, new LinkConditionNode(stmt, binds));
  }

  public LinkConditionNode getLinkConditionNode(String name) {
    return linkList.containsKey(name) ? linkList.get(name) : null;
  }
}
