package pt.inescporto.template.client.util;

import java.util.Vector;
import pt.inescporto.template.elements.TplObject;
import java.lang.reflect.Field;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class StatementsAgregature {
  private String selectPart = "";
  private String updatePart = "";
  private String wherePart = "";
  private String insertPart = "";
  private String keysPart = "";
  private String tablePart = "";
  private String linkPart = "";

  public StatementsAgregature() {
  }

  public String getInsertPart() {
    return insertPart;
  }

  public String getKeysPart() {
    return keysPart;
  }

  public String getSelectPart() {
    return selectPart;
  }

  public String getUpdatePart() {
    return updatePart;
  }

  public String getWherePart() {
    return wherePart;
  }

  public String getTablePart() {
    return tablePart;
  }

  public String getLinkPart() {
    return linkPart;
  }

  private void addInsertPart(String insertPart) {
    this.insertPart += insertPart;
  }

  private void addKeysPart(String keysPart) {
    this.keysPart += keysPart;
  }

  private void addSelectPart(String selectPart) {
    this.selectPart += selectPart;
  }

  private void addUpdatePart(String updatePart) {
    this.updatePart += updatePart;
  }

  private void addWherePart(String wherePart) {
    this.wherePart += wherePart;
  }

  public void build(String name, Object attrs, String masterKey, String detailKey) {
    Field[] fld;
    int i, reqCount = 0;
    Vector keys = null;
    boolean isMaster = (detailKey == null);

    keys = new Vector();
    fld = attrs.getClass().getDeclaredFields();
    tablePart += name + ", ";

    // process any field
    for (i = 0; i < fld.length; i++) {
      try {
	Object test = fld[i].get(attrs);
	// process template objects
	if (test instanceof pt.inescporto.template.elements.TplObject) {
	  TplObject myObject = (TplObject)test;
          if (detailKey == null || !myObject.getField().equals(detailKey)) {
	    if (myObject.isPrimaryKey()) {
	      wherePart += myObject.getField() + " = ? AND ";
	      keysPart += myObject.getField() + ", ";
	      keys.add(test);
	    }
	    else
	      updatePart += myObject.getField() + " = ?, ";
	    selectPart += name + "." + myObject.getField() + ", ";
            if (myObject.isRequired()) {
              insertPart += myObject.getField() + ", ";
              reqCount++;
            }
	  }
	}
      }
      catch (Exception e) {
	System.out.println("DAO : " + e.toString());
      }
    }

    if (masterKey != null && detailKey != null) {
      linkPart += masterKey + " = " + name + "." + detailKey;
    }

    // adjust string length
    selectPart = selectPart.substring(0, selectPart.length() - 2);
    wherePart = wherePart.substring(0, wherePart.length() - 5);
    if (!updatePart.equals(""))
      updatePart = updatePart.substring(0, updatePart.length() - 2);
    insertPart = insertPart.substring(0, insertPart.length() - 2);
    keysPart = keysPart.substring(0, keysPart.length() - 2);
    tablePart = tablePart.substring(0, tablePart.length() - 2);
  }
}
