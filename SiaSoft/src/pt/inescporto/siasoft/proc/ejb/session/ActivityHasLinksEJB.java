package pt.inescporto.siasoft.proc.ejb.session;

import pt.inescporto.siasoft.proc.ejb.dao.ActivityDao;
import javax.ejb.SessionContext;
import pt.inescporto.template.dao.DAO;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import java.util.Vector;
import java.rmi.RemoteException;
import javax.ejb.SessionBean;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import java.util.Hashtable;
import pt.inescporto.siasoft.proc.ejb.dao.ActivityHasLinksDao;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TplObject;
import pt.inescporto.template.dao.*;
import javax.ejb.*;
import java.util.Collection;
import pt.inescporto.siasoft.proc.ejb.dao.ActHasLinksNode;

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
public class ActivityHasLinksEJB implements SessionBean {
  protected transient GenericQuery gQuery;
  protected transient Activity activity;
  protected SessionContext ctx;
  protected ActivityDao attrs = new ActivityDao();
  protected DAO dao = null;
  protected String linkCondition = null;

  public ActivityHasLinksEJB() {
    System.out.println("New ActivityHasLinks Session Bean created by EJB container ...");
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

    // get Activity Remote
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/Activity");
      ActivityHome activityHome = (ActivityHome)PortableRemoteObject.narrow(objref, ActivityHome.class);
      activity = activityHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Activity Home");
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
  private Hashtable<String, Vector> getFunList(String activityFatherID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> funList = new Hashtable<String, Vector>();
    return funList;
  }

  private Hashtable<String, Vector> getEnvironmentalAspectList(String activityFatherID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> envAspList = new Hashtable<String, Vector>();

    // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;
    try {
      if (activityFatherID == null)
        activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
        activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return envAspList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

    // for every child get tree
    for (ActivityDao act : childActivityList) {
      try {
	Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
        for (ActivityDao childAct : tree ) {
	  Vector binds = new Vector();
	  binds.add(new TplString(enterpriseID));
	  binds.add(new TplString(childAct.activityID.getValue()));

	  try {
	    Vector<Vector<TplObject>> result = gQuery.doQuery(
		"select a.activityID, envAspID, envAspName from EnvironmentalAspect a, Activity b " +
		"where fkEnterpriseID = ? AND a.activityID = b.activityID AND b.activityID = ?", binds);

	    for (Vector<TplObject> row : result) {
	      String activityID = act.activityID.getValue();
	      String envAspID = (String)row.elementAt(1).getValueAsObject();
	      if (envAspList.containsKey(activityID)) {
		Vector v = envAspList.get(activityID);
                ActHasLinksNode node = new ActHasLinksNode();
                node.setActivityID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("AA");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
		v.add(node);
		envAspList.remove(activityID);
		envAspList.put(activityID, v);
	      }
	      else {
		Vector v = new Vector();
                ActHasLinksNode node = new ActHasLinksNode();
                node.setActivityID((String)row.elementAt(0).getValueAsObject());
                node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setModuleName("AA");
                node.setRefKey((String)row.elementAt(1).getValueAsObject());
                v.add(node);
		envAspList.put(activityID, v);
	      }
	    }
	  }
	  catch (RemoteException ex) {
	    ctx.setRollbackOnly();
	    throw new UserException("Erro remoto");
	  }
	}
      }
      catch (UserException ex1) {
      }
      catch (RowNotFoundException ex1) {
      }
      catch (RemoteException ex1) {
      }
    }

    return envAspList;
  }

  private Hashtable<String, Vector> getEnvironmentalProgramManagmentList(String activityFatherID, String enterpriseID) throws UserException {
   Hashtable<String, Vector> epmList = new Hashtable<String,Vector>();
   // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;

    try {
      if (activityFatherID == null)
        activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
        activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return epmList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

        // for every child get tree
       for (ActivityDao act : childActivityList) {
         try {
           Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
           for (ActivityDao childAct : tree ) {
             Vector binds = new Vector();
             binds.add(new TplString(enterpriseID));
             binds.add(new TplString(childAct.activityID.getValue()));

             try {
               Vector<Vector<TplObject>> result = gQuery.doQuery(
               "select b.fkActivityID, a.goalID, goalDescription from Goals a, Objective b " +
               "where a.fkEnterpriseID = ? AND b.goalID = a.goalID AND b.fkActivityID = ?", binds);

               for (Vector<TplObject> row : result) {
                 String activityID = act.activityID.getValue();
                 String goalID = (String)row.elementAt(1).getValueAsObject();
                 if (epmList.containsKey(activityID)) {
                   Vector v = epmList.get(activityID);
                   ActHasLinksNode node = new ActHasLinksNode();
                   node.setActivityID((String)row.elementAt(0).getValueAsObject());
                   node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                   node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                   node.setModuleName("PGA");
                   node.setRefKey((String)row.elementAt(1).getValueAsObject());
                   v.add(node);
                   epmList.remove(activityID);
                   epmList.put(activityID, v);
                 }
                 else {
                   Vector v = new Vector();
                   ActHasLinksNode node = new ActHasLinksNode();
                   node.setActivityID((String)row.elementAt(0).getValueAsObject());
                   node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                   node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                   node.setModuleName("PGA");
                   node.setRefKey((String)row.elementAt(1).getValueAsObject());
                   v.add(node);
                   epmList.put(activityID, v);
                 }
               }
             }
             catch (RemoteException ex) {
               ctx.setRollbackOnly();
               throw new UserException("Erro remoto");
             }
           }
         }
         catch (UserException ex1) {
         }
         catch (RowNotFoundException ex1) {
         }
         catch (RemoteException ex1) {
         }
       }


   return epmList;
  }

  private Hashtable<String, Vector> getMonitorList(String activityFatherID, String enterpriseID) throws UserException{
    Hashtable<String, Vector> monitorList = new Hashtable<String, Vector>();
    // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;

    try {
      if (activityFatherID == null)
	activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
	activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return monitorList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

        // for every child get tree
       for (ActivityDao act : childActivityList) {
         try {
           Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
           for (ActivityDao childAct : tree ) {
             Vector binds = new Vector();
             binds.add(new TplString(enterpriseID));
             binds.add(new TplString(childAct.activityID.getValue()));

             try {
               Vector<Vector<TplObject>> result = gQuery.doQuery(
               "select a.fkActivityID, monitorPlanID, monitorPlanDescription from MonitorPlan a, Activity b " +
               "where a.fkEnterpriseID = ? AND a.fkActivityID = b.activityID AND b.activityID = ?", binds);

               for (Vector<TplObject> row : result) {
                 String activityID = act.activityID.getValue();
                 String monitorPlanID = (String)row.elementAt(1).getValueAsObject();
                 if (monitorList.containsKey(activityID)) {
                   Vector v = monitorList.get(activityID);
                   ActHasLinksNode node = new ActHasLinksNode();
                   node.setActivityID((String)row.elementAt(0).getValueAsObject());
                   node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                   node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                   node.setModuleName("MONITOR");
                   node.setRefKey((String)row.elementAt(1).getValueAsObject());
                   v.add(node);
                   monitorList.remove(activityID);
                   monitorList.put(activityID, v);
                 }
                 else {
                   Vector v = new Vector();
                   ActHasLinksNode node = new ActHasLinksNode();
                   node.setActivityID((String)row.elementAt(0).getValueAsObject());
                   node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                   node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                   node.setModuleName("MONITOR");
                   node.setRefKey((String)row.elementAt(1).getValueAsObject());
                   v.add(node);
                   monitorList.put(activityID, v);
                 }
               }
             }
             catch (RemoteException ex) {
               ctx.setRollbackOnly();
               throw new UserException("Erro remoto");
             }
           }
         }
         catch (UserException ex1) {
         }
         catch (RowNotFoundException ex1) {
         }
         catch (RemoteException ex1) {
         }
       }

    return monitorList;
  }

  private Hashtable<String, Vector> getOperationControlEmergencyList(String activityFatherID, String enterpriseID) throws UserException{

    Hashtable<String, Vector> opControlEmergList = new Hashtable<String, Vector>();

    // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;

    try {
      if (activityFatherID == null)
        activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
        activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return opControlEmergList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

    // for every child get tree
   for (ActivityDao act : childActivityList) {
     try {
       Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
       for (ActivityDao childAct : tree ) {
         Vector binds = new Vector();
         binds.add(new TplString(enterpriseID));
         binds.add(new TplString(childAct.activityID.getValue()));

         try {
           Vector<Vector<TplObject>> result = gQuery.doQuery(
               "select a.fkActivityID, emergSitID, emergSitDescription from EmergencySituation a, Activity b " +
               "where a.fkEnterpriseID = ? AND a.fkActivityID = b.activityID AND b.activityID = ? ", binds);

           for (Vector<TplObject> row : result) {
             String activityID = act.activityID.getValue();
             String emergSitID = (String)row.elementAt(1).getValueAsObject();
             if (opControlEmergList.containsKey(activityID)) {
               Vector v = opControlEmergList.get(activityID);
               ActHasLinksNode node = new ActHasLinksNode();
               node.setActivityID((String)row.elementAt(0).getValueAsObject());
               node.setModuleKey((String)row.elementAt(1).getValueAsObject());
               node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
               node.setModuleName("CO_EMERG");
               node.setRefKey((String)row.elementAt(1).getValueAsObject());
               v.add(node);
               opControlEmergList.remove(activityID);
               opControlEmergList.put(activityID, v);
             }
             else {
               Vector v = new Vector();
               ActHasLinksNode node = new ActHasLinksNode();
               node.setActivityID((String)row.elementAt(0).getValueAsObject());
               node.setModuleKey((String)row.elementAt(1).getValueAsObject());
               node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
               node.setModuleName("CO_EMERG");
               node.setRefKey((String)row.elementAt(1).getValueAsObject());
               v.add(node);
               opControlEmergList.put(activityID, v);
             }
           }
         }
         catch (RemoteException ex) {
           ctx.setRollbackOnly();
           throw new UserException("Erro remoto");
         }
       }
     }
     catch (UserException ex1) {
     }
     catch (RowNotFoundException ex1) {
     }
     catch (RemoteException ex1) {
     }
   }

    return opControlEmergList;
  }

  private Hashtable<String, Vector> getAuditList(String activityFatherID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> auditList = new Hashtable<String,Vector>();

    // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;

    try {
      if (activityFatherID == null)
	activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
	activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return auditList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

    // for every child get tree
   for (ActivityDao act : childActivityList) {
     try {
       Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
       for (ActivityDao childAct : tree ) {
         Vector binds = new Vector();
         binds.add(new TplString(enterpriseID));
         binds.add(new TplString(childAct.activityID.getValue()));

         try {
           Vector<Vector<TplObject>> result = gQuery.doQuery(
               "select a.fkActivityID, auditPlanID, auditPlanScope from AuditPlan a, Activity b " +
               "where a.fkEnterpriseID = ? AND a.fkActivityID = b.activityID AND b.activityID = ?", binds);

            for (Vector<TplObject> row : result) {
             String activityID = act.activityID.getValue();
             String auditPlanID = (String)row.elementAt(1).getValueAsObject();
             if (auditList.containsKey(activityID)) {
               Vector v = auditList.get(activityID);
               ActHasLinksNode node = new ActHasLinksNode();
               node.setActivityID((String)row.elementAt(0).getValueAsObject());
               node.setModuleKey((String)row.elementAt(1).getValueAsObject());
               node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
               node.setModuleName("AUDITOR");
               node.setRefKey((String)row.elementAt(1).getValueAsObject());
               v.add(node);
               auditList.remove(activityID);
               auditList.put(activityID, v);
             }
             else {
               Vector v = new Vector();
               ActHasLinksNode node = new ActHasLinksNode();
               node.setActivityID((String)row.elementAt(0).getValueAsObject());
               node.setModuleKey((String)row.elementAt(1).getValueAsObject());
               node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
               node.setModuleName("AUDITOR");
               node.setRefKey((String)row.elementAt(1).getValueAsObject());
               v.add(node);
               auditList.put(activityID, v);
             }
           }
         }
         catch (RemoteException ex) {
           ctx.setRollbackOnly();
           throw new UserException("Erro remoto");
         }
       }
     }
     catch (UserException ex1) {
     }
     catch (RowNotFoundException ex1) {
     }
     catch (RemoteException ex1) {
     }
   }
    return auditList;
  }

  private Hashtable<String, Vector> getRegulatoryList(String activityFatherID, String enterpriseID) throws UserException {
    Hashtable<String, Vector> regList = new Hashtable<String, Vector>();

    // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;
    try {
      if (activityFatherID == null)
	activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
	activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return regList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

    // for every child get tree
    for (ActivityDao act : childActivityList) {
      try {
	Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
	for (ActivityDao childAct : tree) {
	  Vector binds = new Vector();
          binds.add(new TplString(enterpriseID));
          binds.add(new TplString(childAct.activityID.getValue()));
          binds.add(new TplString(enterpriseID));
          binds.add(new TplString(childAct.activityID.getValue()));
          binds.add(new TplString(childAct.activityID.getValue()));

	  try {
	    Vector<Vector<TplObject>> result =
		gQuery.doQuery("select * from (" +
			       "select b.fkActivityID, a.goalID, a.goalDescription, 'PGA', b.fkRegID from goals a, objective b where a.goalid = b.goalid AND b.fkRegID is not null AND fkenterpriseid = ? AND b.fkActivityID = ? " +
                               "union " +
                               "select a.fkActivityID, a.monitorPlanID, a.monitorPlanDescription, 'MONITOR', b.fkRegID from MonitorPlan a, MonitorPlanParameters b where a.monitorplanid = b.monitorplanid AND b.fkRegID is not null AND fkenterpriseid = ? AND a.fkActivityID = ? " +
                               "union " +
                               "select b.fkActivityID, b.fkRegID, a.resume, 'LEGISLAÇÃO', b.fkRegID from Regulatory a, ActivityRegulatory b where a.regid = b.fkregid AND b.fkActivityID = ? " +
			       ") AllRegulatory group by 1,2,3,4,5", binds);
	    for (Vector<TplObject> row : result) {
	      String activityID = act.activityID.getValue();
	      String envAspID = (String)row.elementAt(1).getValueAsObject();
	      if (regList.containsKey(activityID)) {
		Vector v = regList.get(activityID);
		ActHasLinksNode node = new ActHasLinksNode();
		node.setActivityID((String)row.elementAt(0).getValueAsObject());
		node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleName((String)row.elementAt(3).getValueAsObject());
                node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setRefKey((String)row.elementAt(4).getValueAsObject());
		v.add(node);
		regList.remove(activityID);
		regList.put(activityID, v);
	      }
	      else {
		Vector v = new Vector();
		ActHasLinksNode node = new ActHasLinksNode();
		node.setActivityID((String)row.elementAt(0).getValueAsObject());
		node.setModuleKey((String)row.elementAt(1).getValueAsObject());
                node.setModuleName((String)row.elementAt(3).getValueAsObject());
		node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
                node.setRefKey((String)row.elementAt(4).getValueAsObject());
		v.add(node);
		regList.put(activityID, v);
	      }
	    }
	  }
	  catch (RemoteException ex) {
	    ctx.setRollbackOnly();
	    throw new UserException("Erro remoto");
	  }
	}
      }
      catch (UserException ex1) {
      }
      catch (RowNotFoundException ex1) {
      }
      catch (RemoteException ex1) {
      }
    }

    return regList;
  }

  private Hashtable<String, Vector> getDocumentalList(String activityFatherID, String enterpriseID) throws UserException{
    Hashtable<String, Vector> documentalList = new Hashtable<String, Vector>();

    // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;
    try {
      if (activityFatherID == null)
	activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
	activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return documentalList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

    // for every child get tree
  for (ActivityDao act : childActivityList) {
    try {
      Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
      for (ActivityDao childAct : tree) {
        Vector binds = new Vector();
        binds.add(new TplString(enterpriseID));
        binds.add(new TplString(childAct.activityID.getValue()));

        try {
          Vector<Vector<TplObject>> result =
              gQuery.doQuery("select a.fkActivityID, d.documentID, d.documentDescription from EmergencySituation a, EmergencyScenario b, EmergencyPlanDocs c, Document d " +
                             "where a.fkEnterpriseID = ? AND a.fkActivityID = ? AND b.emergSitID = a.emergSitID AND c.emergSitID = b.emergSitID AND c.scenarioID = b.scenarioID AND c.fkDocumentID = d.documentID", binds);

          for (Vector<TplObject> row : result) {
            String activityID = act.activityID.getValue();
            String documentID = (String)row.elementAt(1).getValueAsObject();
            if (documentalList.containsKey(activityID)) {
              Vector v = documentalList.get(activityID);
              ActHasLinksNode node = new ActHasLinksNode();

              node.setActivityID((String)row.elementAt(0).getValueAsObject());
              node.setModuleKey((String)row.elementAt(1).getValueAsObject());
              node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
              node.setModuleName("DOC");
              node.setRefKey((String)row.elementAt(1).getValueAsObject());
              v.add(node);
              documentalList.remove(activityID);
              documentalList.put(activityID, v);
            }
            else {
              Vector v = new Vector();
              ActHasLinksNode node = new ActHasLinksNode();
              node.setActivityID((String)row.elementAt(0).getValueAsObject());
              node.setModuleKey((String)row.elementAt(1).getValueAsObject());
              node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
              node.setModuleName("DOC");
              node.setRefKey((String)row.elementAt(1).getValueAsObject());
              v.add(node);
              documentalList.put(activityID, v);
            }
          }
        }
        catch (RemoteException ex) {
          ctx.setRollbackOnly();
          throw new UserException("Erro remoto");
        }
      }
    }
    catch (UserException ex1) {
    }
    catch (RowNotFoundException ex1) {
    }
    catch (RemoteException ex1) {
    }
  }

    return documentalList;
  }
  private Hashtable<String, Vector> getFormList(String activityFatherID, String enterpriseID) throws UserException{
    Hashtable<String, Vector> formList = new Hashtable<String,Vector>();

    // get child activity's list
    ActivityDao pattern = new ActivityDao();
    pattern.activityFatherID.setValue(activityFatherID);
    pattern.enterpriseID.setValue(enterpriseID);
    Collection<ActivityDao> childActivityList = null;
    try {
      if (activityFatherID == null)
        activity.setLinkCondition(pattern.activityFatherID.getField() + " IS NULL", new Vector());
      childActivityList = activity.findExact(pattern);
      if (activityFatherID == null)
        activity.setLinkCondition(null, null);
    }
    catch (RowNotFoundException ex) {
      return formList;
    }
    catch (FinderException ex) {
    }
    catch (RemoteException ex) {
    }

    // for every child get tree
  for (ActivityDao act : childActivityList) {
    try {
      Collection<ActivityDao> tree = activity.getActivityTree(act.activityID.getValue());
      for (ActivityDao childAct : tree) {
        Vector binds = new Vector();
        binds.add(new TplString(enterpriseID));
        binds.add(new TplString(childAct.activityID.getValue()));

        try {
          Vector<Vector<TplObject>> result =
              gQuery.doQuery("select a.fkActivityID, a.emergSitID, a.emergSitDescription from EmergencySituation a, EmergencyScenario b, EmergencyPlanCourses c, CoursePlan d " +
                             "where a.fkEnterpriseID = ? AND a.fkActivityID = ? AND b.emergSitID = a.emergSitID AND c.emergSitID = b.emergSitID AND c.scenarioID = b.scenarioID AND c.fkCoursePlanID = d.coursePlanID  ", binds);

          for (Vector<TplObject> row : result) {
            String activityID = act.activityID.getValue();
            String emergPlanCourseID = (String)row.elementAt(1).getValueAsObject();
            if (formList.containsKey(activityID)) {
              Vector v = formList.get(activityID);
              ActHasLinksNode node = new ActHasLinksNode();

              node.setActivityID((String)row.elementAt(0).getValueAsObject());
              node.setModuleKey((String)row.elementAt(1).getValueAsObject());
              node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
              node.setModuleName("FORM");
              node.setRefKey((String)row.elementAt(1).getValueAsObject());
              v.add(node);
              formList.remove(activityID);
              formList.put(activityID, v);
            }
            else {
              Vector v = new Vector();
              ActHasLinksNode node = new ActHasLinksNode();
              node.setActivityID((String)row.elementAt(0).getValueAsObject());
              node.setModuleKey((String)row.elementAt(1).getValueAsObject());
              node.setModuleDescription((String)row.elementAt(2).getValueAsObject());
              node.setModuleName("FORM");
              node.setRefKey((String)row.elementAt(1).getValueAsObject());
              v.add(node);
              formList.put(activityID, v);
            }
          }
        }
        catch (RemoteException ex) {
          ctx.setRollbackOnly();
          throw new UserException("Erro remoto");
        }
      }
    }
    catch (UserException ex1) {
    }
    catch (RowNotFoundException ex1) {
    }
    catch (RemoteException ex1) {
    }
  }

    return formList;
  }



  /**
   * Public methods
   */
  public ActivityHasLinksDao getLinks(String activityFatherID, String enterpriseID) throws UserException {
    ActivityHasLinksDao dao = new ActivityHasLinksDao();
    dao.auditList = getAuditList(activityFatherID, enterpriseID);
    dao.docList = getDocumentalList(activityFatherID, enterpriseID);
    dao.eaList = getEnvironmentalAspectList(activityFatherID, enterpriseID);
    dao.epmList = getEnvironmentalProgramManagmentList(activityFatherID, enterpriseID);
    dao.funList = getFunList(activityFatherID, enterpriseID);
    dao.monitorList = getMonitorList(activityFatherID, enterpriseID);
    dao.ocEmergList = getOperationControlEmergencyList(activityFatherID, enterpriseID);
    dao.regulatoryList = getRegulatoryList(activityFatherID, enterpriseID);
    dao.formList = getFormList(activityFatherID, enterpriseID);

    return dao;
  }
}
