package pt.inescporto.siasoft.tools.dbanalizer.sql;

import java.util.HashMap;
import java.util.Map;

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
public class SqlTypes {
  private String driverName = null;
  private Map<Integer, String> sqlMapTypes = new HashMap<Integer, String>();

  public SqlTypes() {
    sqlMapTypes.put(new Integer(java.sql.Types.BIT), "BOOLEAN");
    sqlMapTypes.put(new Integer(java.sql.Types.TINYINT), "TINYINT");
    sqlMapTypes.put(new Integer(java.sql.Types.SMALLINT), "SMALLINT");
    sqlMapTypes.put(new Integer(java.sql.Types.INTEGER), "INTEGER");
    sqlMapTypes.put(new Integer(java.sql.Types.BIGINT), "BIGINT");
    sqlMapTypes.put(new Integer(java.sql.Types.FLOAT), "FLOAT");
    sqlMapTypes.put(new Integer(java.sql.Types.REAL), "REAL");
    sqlMapTypes.put(new Integer(java.sql.Types.DOUBLE), "DOUBLE PRECISION");
    sqlMapTypes.put(new Integer(java.sql.Types.NUMERIC), "INTEGER"); //NUMERIC(#p, #d)");
    sqlMapTypes.put(new Integer(java.sql.Types.DECIMAL), "DECIMAL(#p, #d)");
    sqlMapTypes.put(new Integer(java.sql.Types.CHAR), "CHAR(#p)");
    sqlMapTypes.put(new Integer(java.sql.Types.VARCHAR), "VARCHAR(#p)");
    sqlMapTypes.put(new Integer(java.sql.Types.LONGVARCHAR), "TEXT");
    sqlMapTypes.put(new Integer(java.sql.Types.DATE), "DATE");
    sqlMapTypes.put(new Integer(java.sql.Types.TIME), "TIME");
    sqlMapTypes.put(new Integer(java.sql.Types.TIMESTAMP), "TIMESTAMP");
    sqlMapTypes.put(new Integer(java.sql.Types.BINARY), "BYTEA");
    sqlMapTypes.put(new Integer(java.sql.Types.VARBINARY), "VARBINARY(#p)");
    sqlMapTypes.put(new Integer(java.sql.Types.LONGVARBINARY), "IMAGE");
    sqlMapTypes.put(new Integer(java.sql.Types.NULL), "");
    sqlMapTypes.put(new Integer(java.sql.Types.OTHER), "");
    sqlMapTypes.put(new Integer(java.sql.Types.JAVA_OBJECT), "");
    sqlMapTypes.put(new Integer(java.sql.Types.DISTINCT), "DISTINCT");
    sqlMapTypes.put(new Integer(java.sql.Types.STRUCT), "OBJECT");
    sqlMapTypes.put(new Integer(java.sql.Types.ARRAY), "VARRAY");
    sqlMapTypes.put(new Integer(java.sql.Types.BLOB), "BLOB");
    sqlMapTypes.put(new Integer(java.sql.Types.CLOB), "CLOB");
    sqlMapTypes.put(new Integer(java.sql.Types.REF), "REF");
    sqlMapTypes.put(new Integer(java.sql.Types.DATALINK), "");
    sqlMapTypes.put(new Integer(java.sql.Types.BOOLEAN), "");
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getDriverName() {
    return driverName;
  }

  public String getSQLtypeName(int sqltype) {
    return sqlMapTypes.get(new Integer(sqltype));
  }

  public String getSQLtypeName(Integer sqltype, Integer precision, Integer decimal) {
    String typeName = sqlMapTypes.get(sqltype);

    if (precision != null)
      typeName = typeName.replaceAll("#p", precision.toString());
    if (decimal != null)
      typeName = typeName.replaceAll("#d", decimal.toString());

    return typeName;
  }
}
