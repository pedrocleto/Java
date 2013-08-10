package pt.inescporto.siasoft.tools.dbanalizer.sql;

import java.io.*;

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
public class StatementNode implements Serializable {
  public static final int DROP_TABLE = 1;
  public static final int CREATE_TABLE = 2;
  public static final int ADD_COLUMN = 3;
  public static final int DROP_COLUMN = 4;
  public static final int ADD_PRIMARY_KEY = 5;
  public static final int DROP_PRIMARY_KEY = 6;
  public static final int ADD_CONSTRAINT = 7;
  public static final int DROP_CONSTRAINT = 8;
  public static final int ADD_INDEX = 9;
  public static final int DROP_INDEX = 10;

  private Integer type;
  private String statement = null;

  public StatementNode(int type, String statement) {
    this.type = new Integer(type);
    this.statement = statement;
  }

  public void setStatement(String statement) {
    this.statement = statement;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getStatement() {
    return statement;
  }

  public Integer getType() {
    return type;
  }
}
