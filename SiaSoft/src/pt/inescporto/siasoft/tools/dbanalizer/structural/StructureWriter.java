package pt.inescporto.siasoft.tools.dbanalizer.structural;

import java.util.Vector;
import pt.inescporto.siasoft.tools.dbanalizer.sql.StatementNode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import pt.inescporto.siasoft.tools.MessagePoster;

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
public class StructureWriter {
  private StructureWriter() {
  }

  public static void applyStructDiff(MessagePoster poster, Connection conn, Vector<StatementNode> stmtList) {
    poster.postMessage("Start of applying structural diff ...");
    for (StatementNode sNode : stmtList) {
      String stmt = sNode.getStatement();
      stmt = stmt.replace(';', ' ');
      try {
	PreparedStatement ps = conn.prepareCall(stmt);
	ps.execute();
      }
      catch (SQLException ex) {
        poster.postMessage(ex.getMessage());
      }
    }
    poster.postMessage("Structural diff done!");
  }
}
