package pt.inescporto.siasoft.tools;

import java.io.Serializable;

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
public class IndexField implements Serializable {
  public static int NOTDEF_ORDER = 0;
  public static int ASC_ORDER = 1;
  public static int DESC_ORDER = 2;
  private String fieldName = null;
  private int ordinalPosition = 0;
  private int orderType = 0;

  public IndexField() {
  }

  public IndexField(String fieldName, int ordinalPosition, int orderType) {
    if (fieldName == null)
      throw new IllegalArgumentException("Argument <fieldName> can't be null!");
    if (ordinalPosition < 1)
      throw new IllegalArgumentException("Argument <ordinalPosition> must be > = 1!");
    if (orderType < 0 || orderType > 2)
      throw new IllegalArgumentException("Argument <orderType> must be between 0 and 2!");

    this.fieldName = fieldName;
    this.ordinalPosition = ordinalPosition;
  }

  public String getFieldName() {
    return fieldName;
  }

  public int getOrdinalPosition() {
    return ordinalPosition;
  }

  public int getOrderType() {
    return orderType;
  }

  public String toString() {
    return fieldName + " [" + ordinalPosition + "]";
  }

  public boolean equals(Object o) {
    boolean match = false;

    if (o.getClass().getName().equals(getClass().getName())) {
      IndexField myObj = (IndexField)o;

      if (myObj.fieldName != null && fieldName != null && myObj.fieldName.equals(fieldName) &&
	  myObj.ordinalPosition == ordinalPosition &&
	  myObj.orderType == orderType)
        match = true;
    }

    return match;
  }
}
