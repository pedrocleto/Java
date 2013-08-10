package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.ejb.session.GenericQuery;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import java.util.Calendar;
import pt.inescporto.template.elements.TplTimestamp;
import java.sql.Timestamp;

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
public class TreeBuilderEJB implements SessionBean {
  protected transient GenericQuery gQuery = null;
  protected SessionContext ctx;

  public TreeBuilderEJB() {
    System.out.println("New TreeBuilderEJB Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get GenericQuery
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome gQueryHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      gQuery = gQueryHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Unit Home");
      re.printStackTrace();
    }
  }

  /**
   * EJB Required methods
   */

  public void ejbActivate() throws RemoteException {
    getEJBReferences();
  }

  public void ejbPassivate() throws RemoteException {}

  public void ejbRemove() throws RemoteException {}

  public void ejbCreate() {
  }

  public void setSessionContext(SessionContext context) throws RemoteException {
    ctx = context;
  }

  public void unsetSessionContext() {
    this.ctx = null;
  }

  public Vector getRegulatoryByTheme(int order) {
    try {
      switch (order) {
        case 0:
          return gQuery.doQuery(
	      "select l.scopeID, l.scopeDescription, l.legislID, l.legislDescription, l.themeID, l.description, l.theme1ID, l.dsc, l.regID, l.name, r.legalreqid, r.legalreqdescription " +
	      "from(select a.scopeID, scopeDescription, a.legislID, legislDescription, a.themeID, e.description, a.theme1ID, f.description AS dsc, a.regID, b.name " +
	      "from regulatoryhasclass a, regulatory b, scope c, legislation d, theme e, theme1 f " +
	      "where a.regID = b.regID and b.revocate = false and a.scopeID = c.scopeID and a.legislID = d.legislID and a.themeID = e.themeID and a.themeID = f.themeID and a.theme1ID = f.theme1ID " +
	      "order by 2, 4, e.orderIndex, f.orderIndex, 10)AS l LEFT JOIN(select lr.legalreqid, lr.legalreqdescription, lr.regID " +
	      "from legalrequirement lr)AS r ON(l.regID = r.regID) ", new Vector());
        case 1:
          return gQuery.doQuery(
              "select l.scopeID, l.scopeDescription, l.legislID, l.legislDescription, l.publishDate, l.regID, l.name, r.legalreqid, r.legalreqdescription "+
              "from (select a.scopeID, c.scopeDescription, a.legislID, d.legislDescription, publishDate, b.regID, b.name " +
              "from RegulatoryHasClass a, regulatory b, scope c, legislation d " +
              "where a.regid = b.regid and b.revocate = false and a.scopeId = c.scopeId and a.legislID = d.legislID AND b.publishDate IS NOT NULL " +
              ")AS l LEFT JOIN  (select lr.legalreqid, lr.legalreqdescription, lr.regID "+
              "from legalrequirement lr)AS r ON (l.regID=r.regID) group by 1, 2, 3, 4, 5, 6, 7, 8, 9 order by 2 ASC, 4 ASC, 5 DESC, 7 ASC", new Vector());
        case 3:
          return gQuery.doQuery(
              "select a.scopeid, scopeDescription " +
              "from RegulatoryHasClass a, scope b " +
              "where a.scopeId = b.scopeId " +
              "group by a.scopeid, scopeDescription order by scopeDescription", new Vector());
      }
    }
    catch (RemoteException ex) {
      return null;
    }
    return null;
  }

  public Vector getRegulatoryByThemeApplicable(String enterpriseID) {
    try {
      Vector binds = new Vector();
      binds.add(new TplString(enterpriseID));

      return gQuery.doQuery(
	  "select l.scopeID, l.scopeDescription, l.legislID, l.legislDescription, l.themeID, l.description, l.theme1ID, l.dsc, l.regID, l.name, r.legalreqid, r.legalreqdescription "+
	  "from(select a.scopeID, c.scopeDescription, a.legislID, d.legislDescription, a.themeID, e.description, f.theme1ID, f.description AS dsc, b.regID, b.name "+
          "from RegulatoryHasClass a, regulatory b, scope c, legislation d, theme e, theme1 f, AsqClientApplicability g "+
          "where a.regid = b.regid and b.revocate = false and a.scopeId = c.scopeId and a.legislID = d.legislID and "+
          "a.themeID = e.themeID and a.themeID = f.themeID and a.theme1ID = f.theme1ID and g.regID = a.regid and g.enterpriseID = ? AND "+
          "g.userID IS NULL AND g.profileID IS NULL AND g.legalRequirementID IS NULL "+
          "order by 2, 4, e.orderIndex, f.orderIndex, 10)AS l LEFT JOIN(select lr.legalreqid, lr.legalreqdescription, lr.regID "+
	  "from legalrequirement lr)AS r ON(l.regID = r.regID)", binds);
    }
    catch (RemoteException ex) {
      return null;
    }
  }

  public Vector getRegulatoryByThemeApplicable(String enterpriseID, String userID, String profileID) {
    try {
      Vector binds = new Vector();
      binds.add(new TplString(enterpriseID));
      binds.add(new TplString(userID));
      binds.add(new TplString(profileID));

      return gQuery.doQuery(
          "select l.scopeID, l.scopeDescription, l.legislID, l.legislDescription, l.themeID, l.description, l.theme1ID, l.dsc, l.regID, l.name, r.legalreqid, r.legalreqdescription "+
	  "from(select a.scopeid, c.scopeDescription, a.legislID, d.legislDescription, a.themeID, e.description, a.theme1ID, f.description AS dsc, b.regID, b.name " +
          "from RegulatoryHasClass a, regulatory b, scope c, legislation d, theme e, theme1 f, AsqClientApplicability g " +
          "where a.regid = b.regid and b.revocate = false and a.scopeId = c.scopeId and a.legislID = d.legislID and " +
          "a.themeID = e.themeID and a.themeID = f.themeID and a.theme1ID = f.theme1ID and g.regID = a.regid and g.enterpriseID = ? AND " +
          "g.userID = ? AND g.profileID = ? AND g.legalRequirementID IS NULL " +
          "order by 2, 4, e.orderIndex, f.orderIndex, 10)AS l LEFT JOIN(select lr.legalreqid, lr.legalreqdescription, lr.regID "+
	  "from legalrequirement lr)AS r ON(l.regID = r.regID)", binds);
    }
    catch (RemoteException ex) {
      return null;
    }
  }

  public Vector getRegulatoryList(String scope, String legislation, String majorTheme, String minorTheme, int order) {
    try {
      Vector binds = new Vector();
      binds.add(new TplString(scope));
      binds.add(new TplString(legislation));

      switch (order) {
        case 0 :
          binds.add(new TplString(majorTheme));
          binds.add(new TplString(minorTheme));
          return gQuery.doQuery(
              "select b.regId, b.name, b.resume " +
              "from RegulatoryHasClass a, regulatory b " +
              "where a.regid = b.regid and b.revocate = false and a.scopeID = ? and a.legislID = ? and a.themeID = ? and a.theme1ID = ?" +
              "order by 2", binds);
        case 1 :
          //calculate start and end date
          Calendar startDate = Calendar.getInstance();
          startDate.set(Integer.parseInt(majorTheme), Integer.parseInt(minorTheme)-1, 1, 0, 0);
          Calendar endDate = Calendar.getInstance();
          endDate.set(Integer.parseInt(majorTheme), Integer.parseInt(minorTheme)-1, 1, 23, 59);
          endDate.set(Calendar.DAY_OF_MONTH, endDate.getMaximum(Calendar.DAY_OF_MONTH));

          binds.add(new TplTimestamp(new Timestamp(startDate.getTime().getTime())));
          binds.add(new TplTimestamp(new Timestamp(endDate.getTime().getTime())));

          return gQuery.doQuery(
              "select b.regId, b.name, b.resume " +
              "from RegulatoryHasClass a, regulatory b " +
              "where a.regid = b.regid and b.revocate = false and a.scopeID = ? and a.legislID = ? and b.publishDate >= ? and b.publishDate <= ?" +
              "order by b.publishDate DESC, b.name ASC", binds);
      }
    }
    catch (RemoteException ex) {
      return null;
    }
    return null;
  }

  public Vector getRegulatoryListApplicable(String enterpriseID, String userID, String profileID, String scope, String legislation, String majorTheme, String minorTheme) {
    try {
      Vector binds = new Vector();
      binds.add(new TplString(scope));
      binds.add(new TplString(legislation));
      binds.add(new TplString(majorTheme));
      binds.add(new TplString(minorTheme));
      binds.add(new TplString(enterpriseID));

      String selectStmt = "select b.regId, b.name, b.resume " +
          "from RegulatoryHasClass a, regulatory b, AsqClientApplicability c " +
          "where b.regid = a.regid and b.revocate = false and a.scopeID = ? and a.legislID = ? and a.themeID = ? and " +
          "a.theme1ID = ? AND c.regID = b.regID AND c.enterpriseID = ? AND c.legalRequirementID IS NULL";
      // bind by user ?
      if (userID != null) {
        selectStmt += " AND c.userID = ?";
        binds.add(new TplString(userID));
      }
      else
        selectStmt += " AND c.userID IS NULL";
      // bind by profile ?
      if (profileID != null) {
        selectStmt += " AND c.profileID = ?";
        binds.add(new TplString(profileID));
      }
      else
        selectStmt += " AND c.profileID IS NULL";

      selectStmt += " order by 2";

      return gQuery.doQuery(selectStmt, binds);
    }
    catch (RemoteException ex) {
      return null;
    }
  }
}
