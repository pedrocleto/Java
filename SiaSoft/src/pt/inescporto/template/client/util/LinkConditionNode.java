package pt.inescporto.template.client.util;

import java.io.Serializable;
import java.util.Vector;

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
public class LinkConditionNode implements Serializable {
  private String statement = null;
  private Vector binds = null;

  public LinkConditionNode(String statement, Vector binds) {
    this.statement = statement;
    this.binds = binds;
  }

  public String getStatement() {
    return statement;
  }

  public Vector getBinds() {
    return binds;
  }

  public String toString() {
    return "Statement <" + statement + "> with binds <" + binds + ">";
  }
}
