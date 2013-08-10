package pt.inescporto.siasoft.go.coe.ejb.session;

import pt.inescporto.template.elements.TplString;
import javax.ejb.SessionContext;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.elements.TplObject;
import java.rmi.RemoteException;
import java.util.Hashtable;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.DAO;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.util.Vector;
import javax.ejb.SessionBean;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationDao;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksNode;
import pt.inescporto.siasoft.go.coe.ejb.dao.EmergencySituationHasLinksDao;

/**
 * <p>Title: SIASoft</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SIA</p>
 *
 * @author Pedro
 * @version 0.1
 */
public class EmergencySituationHasLinksEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected transient EmergencySituation emergSit;
  protected SessionContext ctx;
  protected EmergencySituationDao attrs = new EmergencySituationDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public EmergencySituationHasLinksEJB() {
    System.out.println("New EmergencySituationHasLinksEJB Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get GenericQuery Remote
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome gHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      gQuery = gHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate GenericQuery Home");
      re.printStackTrace();
    }

    // get EmergencySituation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/EmergencySituation");
      EmergencySituationHome emergencySituationHome = (EmergencySituationHome)PortableRemoteObject.narrow(objref, EmergencySituationHome.class);
      emergSit = emergencySituationHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate EmergencySituation Home");
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
   * Private methods
   */
  private Hashtable<String, Vector> getEnvironmentalAspectList(String emergSitID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> envAspList = new Hashtable<String, Vector>();


    EmergencySituationDao pattern = new EmergencySituationDao();
    pattern.emergSitID.setValue(emergSitID);
    pattern.fkEnterpriseID.setValue(enterpriseID);

    try {
      if (emergSitID == null)
        emergSit.setLinkCondition(pattern.emergSitID.getField() + " IS NULL", new Vector());

      if (emergSitID == null)
        emergSit.setLinkCondition(null, null);
    }
    catch (RemoteException ex) {
    }
          Vector binds = new Vector();
          binds.add(new TplString(enterpriseID));
          binds.add(new TplString(emergSitID));

          try {
            Vector<Vector<TplObject>> result = gQuery.doQuery(
              "select m.emergSitID, m.fkEnvAspID, e.envAspName from EmergencySituation m, EnvironmentalAspect e " +
                      "where m.fkenterpriseID=? AND m.fkEnvAspID=e.envaspid AND m.emergSitID= ?", binds);

            for (Vector<TplObject> row : result) {
              if (envAspList.containsKey(emergSitID)) {
                Vector v = envAspList.get(emergSitID);
                EmergencySituationHasLinksNode node = new EmergencySituationHasLinksNode();
                node.setEmergSitID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("AA");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
                v.add(node);
                envAspList.remove(emergSitID);
                envAspList.put(emergSitID, v);
              }
              else {
                Vector v = new Vector();
                EmergencySituationHasLinksNode node = new EmergencySituationHasLinksNode();
                node.setEmergSitID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("AA");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
                v.add(node);
                envAspList.put(emergSitID, v);
              }
            }
          }
          catch (RemoteException ex) {
            ctx.setRollbackOnly();
            throw new UserException("Erro remoto");
          }

    return envAspList;
  }


  /**
   * Public methods
   */
  public EmergencySituationHasLinksDao getLinks(String emergSitID, String enterpriseID) throws UserException {
    EmergencySituationHasLinksDao dao = new EmergencySituationHasLinksDao();
    dao.eaList = getEnvironmentalAspectList(emergSitID, enterpriseID);
    return dao;
  }
}
