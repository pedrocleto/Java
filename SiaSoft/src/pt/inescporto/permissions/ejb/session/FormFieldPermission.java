package pt.inescporto.permissions.ejb.session;

import java.io.Serializable;
import java.util.Hashtable;

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
public class FormFieldPermission implements Serializable {
  private String formAccessName = null;
  private Integer formPerms = null;
  private Hashtable<String, Integer> fieldPermList = null;

  public FormFieldPermission(String formAccessName, Integer formPerms, Hashtable<String, Integer> fieldPermList) {
    this.formAccessName = formAccessName;
    this.formPerms = formPerms;
    this.fieldPermList = fieldPermList;
  }

  public int getFormPerms() {
    return formPerms.intValue();
  }

  public String getFormAccessName() {
    return formAccessName;
  }

  public Hashtable<String, Integer> getFieldPermList() {
    return fieldPermList != null ? (Hashtable<String, Integer>)fieldPermList.clone() : null;
  }
}
