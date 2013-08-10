package pt.inescporto.siasoft.comun.ejb.session;

import java.rmi.*;
import javax.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.swing.tree.DefaultMutableTreeNode;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.ejb.session.GenericQuery;
import java.util.Vector;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.comun.ejb.dao.ProfileBuilderNode;

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
public class ProfileBuilderEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected SessionContext ctx;

  public ProfileBuilderEJB() {
    System.out.println("New ProfileBuilder Session Bean created by EJB container ...");
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

  /**
   * Public methods
   */
  public Vector getProfileTree() {
    Vector treeList = null;

    try {
      treeList = gQuery.doQuery("select d.profilegroupID, d.profileDesc, a.enterpriseID, enterpriseName, b.userID, username, c.profileID, c.profileDesc " +
                                       "from enterprise a, users b, userProfiles c, profileGroup d,  profileGroupHasEnterprise e " +
                                       "where a.enterpriseId = b.enterpriseId AND b.userId = c.userId AND a.enterpriseID = e.enterpriseID AND d.profilegroupid = e.profilegroupid " +
                                       "order by 2, 4, 6, 8", new Vector());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    return treeList;
  }
}
