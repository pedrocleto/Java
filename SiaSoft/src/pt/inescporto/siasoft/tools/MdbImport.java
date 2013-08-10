package pt.inescporto.siasoft.tools;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: Gestão do Ambiente</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: INESC Porto</p>
 *
 * @author jap
 * @version 0.1
 */
public class MdbImport {
  public MdbImport() {
    try {
      mdbImport();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Gets a new connection to the source database (MDB)
   * @param jdbcDriverSource String
   * @param databaseUrlSource String
   * @return Connection
   * @throws SQLException
   */
  private Connection getConnection2Source(String jdbcDriverSource, String databaseUrlSource) throws SQLException {
    try {
      Class.forName(jdbcDriverSource);
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }

    return DriverManager.getConnection(databaseUrlSource);
  }

  private Connection getConnection2Dest(String jdbcDriverDest, String databaseUrlDest, String userName, String userPasswd) throws SQLException {
    try {
      Class.forName(jdbcDriverDest);
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }

    return DriverManager.getConnection(databaseUrlDest, userName, userPasswd);
  }

  private void mdbImport() throws SQLException {
    Connection conSource = null;
    Connection conDest = null;
    PreparedStatement psSource = null;
    PreparedStatement psDest = null;
    ResultSet rs = null;

    conSource = getConnection2Source("jstels.jdbc.csv.CsvDriver", "jdbc:jstels:csv:data/DB");
    conDest = getConnection2Dest("org.postgresql.Driver", "jdbc:postgresql://luxuria.inescn.pt/SIASoft", "SIASoft", "canela");

    // import Theme table
/*    psSource = conSource.prepareStatement("SELECT * FROM Tema");
    psDest = conDest.prepareStatement("INSERT INTO theme (themeid, description, orderIndex) VALUES (?, ?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("Tema"));
      psDest.setString(2, rs.getString("DescTema"));
      psDest.setInt(3, rs.getInt("Ordem"));
      psDest.execute();
    }

    //import Theme1 table
    psSource = conSource.prepareStatement("SELECT * FROM SubTema");
    psDest = conDest.prepareStatement("INSERT INTO theme1 (themeID, theme1ID, description, orderIndex) VALUES (?, ?, ?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("Tema"));
      psDest.setString(2, rs.getString("SubTema"));
      psDest.setString(3, rs.getString("DesSubTema"));
      psDest.setInt(4, rs.getInt("Ordem"));
      psDest.execute();
    }

    //import Regulatory table
    psSource = conSource.prepareStatement("SELECT CodigoSIA, Nome, Sumario, DataPublicacao, DataAssinatura, DocumentoPDF, Obs, FontePublicacao, TipoDiploma, Revogado, Incompleto, Activo FROM Diploma");
    psDest = conDest.prepareStatement("INSERT INTO regulatory (regID, name, resume, publishDate, startDate, comeIntoForceDate, fk_sourceid, revocate, incompleted, state, documentName, notes, supplierLock) " +
				      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("CodigoSIA"));
      psDest.setString(2, rs.getString("Nome"));
      psDest.setString(3, rs.getString("Sumario"));
      psDest.setDate(4, rs.getDate("DataPublicacao"));
      psDest.setDate(5, rs.getDate("DataAssinatura"));
      psDest.setNull(6, java.sql.Types.DATE);
      String source = rs.getString("FontePublicacao");
      if (source.equals("Diário da República - I série"))
        psDest.setString(7, "DR-I");
      if (source.equals("Diário da República - II série"))
        psDest.setString(7, "DR-II");
      if (source.equals("Diário da República - III série"))
        psDest.setString(7, "DR-III");
      if (source.equals("Diário do Governo"))
        psDest.setString(7, "DG");
      if (source.equals("Jornal Oficial das Comunidades Europeias"))
        psDest.setString(7, "JOCE");
      psDest.setBoolean(8, rs.getInt("Revogado") == 1 ? true : false);
      psDest.setBoolean(9, rs.getInt("Incompleto") == 1 ? true : false);
      psDest.setBoolean(10, rs.getInt("Activo") == 1 ? true : false);
      psDest.setString(11, rs.getString("DocumentoPDF").replaceAll(".pdf", ""));
      psDest.setString(12, rs.getString("Obs"));
      psDest.setBoolean(13, true);


      psDest.execute();
    }

    conDest.prepareStatement("create table Diploma (CodigoSIA VARCHAR(30) NOT NULL, TipoDiploma VARCHAR(20) NOT NULL)").execute();
    psSource = conSource.prepareStatement("SELECT CodigoSIA, TipoDiploma FROM Diploma");
    psDest = conDest.prepareStatement("INSERT INTO Diploma (CodigoSIA, TipoDiploma) " +
                                      "VALUES (?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("CodigoSIA"));
      psDest.setString(2, rs.getString("TipoDiploma").equals("Nacional") ? "NAC" : "COM");
      try {
        psDest.execute();
      }
      catch (SQLException ex) {
      }
    }

    //import RegulatoryHasClass table
    conDest.prepareStatement("create table DiplomaSubtema (CodigoSIA varchar(20) NOT NULL, Tema varchar(20) NOT NULL, SubTema varchar(20) NOT NULL, TipoAplicacao varchar(20) NOT NULL )").execute();
    psSource = conSource.prepareStatement("SELECT CodigoSIA, Tema, SubTema, TipoAplicacao FROM DiplomaSubtema");
    psDest = conDest.prepareStatement("INSERT INTO DiplomaSubtema (CodigoSIA, Tema, SubTema, TipoAplicacao) " +
                                      "VALUES (?, ?, ?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("CodigoSIA"));
      psDest.setString(2, rs.getString("Tema"));
      psDest.setString(3, rs.getString("SubTema"));
      psDest.setString(4, rs.getString("TipoAplicacao"));
      try {
        psDest.execute();
      }
      catch (SQLException ex) {
      }
    }

    psSource = conDest.prepareStatement("select a.CodigoSIA, b.TipoAplicacao, a.TipoDiploma, b.Tema, b.Subtema from Diploma a, DiplomaSubtema b where a.CodigoSIA = b.CodigoSIA");
    psDest = conDest.prepareStatement("INSERT INTO RegulatoryHasClass (regID, scopeID, legislID, themeID, theme1ID) VALUES (?, ?, ?, ?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("CodigoSIA"));
      psDest.setString(2, rs.getString("TipoAplicacao"));
      psDest.setString(3, rs.getString("TipoDiploma"));
      psDest.setString(4, rs.getString("Tema"));
      psDest.setString(5, rs.getString("SubTema"));
      try {
        psDest.execute();
      }
      catch (SQLException ex) {
        ex.printStackTrace();
      }
    }

/*    //import RegulatoryAsTheme table
    psSource = conSource.prepareStatement("SELECT CodigoSIA, Tema, SubTema FROM DiplomaSubtema");
    psDest = conDest.prepareStatement("INSERT INTO regulatoryAsTheme (regID, themeid, theme1id) " +
				      "VALUES (?, ?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("CodigoSIA"));
      psDest.setString(2, rs.getString("Tema"));
      psDest.setString(3, rs.getString("SubTema"));
      try {
	psDest.execute();
      }
      catch (SQLException ex) {
      }
    }

    //import RegulatoryAsScope table
    psSource = conSource.prepareStatement("SELECT CodigoSIA, TipoAplicacao FROM DiplomaSubtema");
    psDest = conDest.prepareStatement("INSERT INTO regulatoryAsScope (regID, scopeid, version) " +
				      "VALUES (?, ?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("CodigoSIA"));
      psDest.setString(2, rs.getString("TipoAplicacao"));
      psDest.setInt(3, 1);
      try {
	psDest.execute();
      }
      catch (SQLException ex) {
      }
    }

    //import RegulatoryAsLegislation table
/*    psSource = conSource.prepareStatement("SELECT CodigoSIA, TipoDiploma FROM Diploma");
    psDest = conDest.prepareStatement("INSERT INTO regulatoryAsLegislation (regID, legislid) " +
				      "VALUES (?, ?)");
    rs = psSource.executeQuery();
    while (rs.next()) {
      psDest.setString(1, rs.getString("CodigoSIA"));
      psDest.setString(2, rs.getString("TipoDiploma").equals("Nacional") ? "NAC" : "COM");
      try {
	psDest.execute();
      }
      catch (SQLException ex) {
      }
    }*/

    //import HistoricTable table
    psSource = conSource.prepareStatement("SELECT CodigoSiaPai, CodigoSiaFilho, CodTipoHistorico, Obs, Aviso FROM HistoricoDiploma");
    psDest = conDest.prepareStatement("INSERT INTO regulatoryHistory (regHistID, regIDfather, regIDson, actionType, obs, histDate) VALUES (?, ?, ?, ?, ?, ?)");
    rs = psSource.executeQuery();
    String code = "20060000000000000000";
    while (rs.next()) {
      code = getNext(code);
      psDest.setString(1, code);
      psDest.setString(2, rs.getString(((String)"CodigoSiaPai").trim()));
      psDest.setString(3, rs.getString(((String)"CodigoSiaFilho").trim()));
      psDest.setString(4, rs.getString("CodTipoHistorico"));
      psDest.setString(5, rs.getString("Obs"));
      psDest.setDate(6, rs.getDate("Aviso"));

      try {
	psDest.execute();
      }
      catch (SQLException ex) {
      }
    }

    conSource.close();
    conDest.close();
  }

  private String getNext(String keyValue) {
    String sYear = keyValue.substring(0, 4);
    String sValue = keyValue.substring(4);
    int iYear = Integer.parseInt(sYear);
    long lValue = Long.parseLong(sValue);
    String nextValue = "";
    Calendar rightNow = Calendar.getInstance();

    int curYear = rightNow.get(Calendar.YEAR);

    if (curYear != iYear) {
      iYear = curYear;
      lValue = 1;
    }
    else
      lValue++;

    sValue = Long.toString(lValue, 10);
    sYear = Integer.toString(iYear);

    nextValue = sYear;

    while (nextValue.length() + sValue.length() < keyValue.length())
      nextValue = nextValue + "0";

    nextValue = nextValue + sValue;

    return (nextValue);
  }

  public static void main(String[] args) {
    MdbImport mdbimport = new MdbImport();
  }
}
