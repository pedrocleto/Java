package pt.inescporto.siasoft.asq.ejb.session;

import javax.ejb.SessionContext;
import pt.inescporto.siasoft.asq.ejb.dao.AsqClientApplicabilityDao;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import pt.inescporto.template.ejb.util.Key;
import java.rmi.RemoteException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DAO;
import javax.naming.InitialContext;
import javax.naming.Context;
import java.util.Vector;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.template.ejb.util.KeyHome;
import javax.ejb.SessionBean;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.siasoft.asq.ejb.dao.AsqApplyRegulatoryDao;
import pt.inescporto.siasoft.comun.ejb.session.ProfileGroupHasEnterprise;
import pt.inescporto.siasoft.comun.ejb.session.ProfileGroupHasEnterpriseHome;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.comun.ejb.dao.ProfileGroupHasEnterpriseDao;
import pt.inescporto.siasoft.comun.ejb.dao.UserProfileDao;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.siasoft.comun.ejb.session.UserProfiles;
import pt.inescporto.siasoft.comun.ejb.session.UserProfilesHome;
import pt.inescporto.template.elements.TplBoolean;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.elements.TplInt;
import pt.inescporto.template.elements.TplObject;

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
public class AsqClientApplicabilityEJB implements SessionBean {
  protected transient Key key;
  protected transient GenericQuery gQuery;
  protected transient ProfileGroupHasEnterprise pgHasEnterprise;
  protected transient UserProfiles userProfiles;
  protected SessionContext ctx;
  protected AsqClientApplicabilityDao attrs = new AsqClientApplicabilityDao();
  protected DAO dao = null;
  String tt;

  public AsqClientApplicabilityEJB() {
    System.out.println("New AsqClientApplicability Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get Key Generation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/KeyGen");
      KeyHome keyHome = (KeyHome)PortableRemoteObject.narrow(objref, KeyHome.class);
      key = keyHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Key Home");
      re.printStackTrace();
    }

    // get Generic Query
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome gQueryHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      gQuery = gQueryHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Generic Query Home");
      re.printStackTrace();
    }

    // get ProfileGroupHasEnterprise EJB
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/ProfileGroupHasEnterprise");
      ProfileGroupHasEnterpriseHome pgHasEnterpriseHome = (ProfileGroupHasEnterpriseHome)PortableRemoteObject.narrow(objref, ProfileGroupHasEnterpriseHome.class);
      pgHasEnterprise = pgHasEnterpriseHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate ProfileGroupHasEnterprise Home");
      re.printStackTrace();
    }

    // get UserProfiles EJB
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/UserProfiles");
      UserProfilesHome userProfilesHome = (UserProfilesHome)PortableRemoteObject.narrow(objref, UserProfilesHome.class);
      userProfiles = userProfilesHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate ProfileGroupHasEnterprise Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "AsqClientApplicability");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "AsqClientApplicability", linkCondition, binds);
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

  public void setLinkCondition(String linkCondition, Vector binds) {
    if (linkCondition == null) {
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "AsqClientApplicability");
    }
    else {
      if (binds == null)
	binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "AsqClientApplicability", linkCondition, binds);
    }
  }

  public void setData(AsqClientApplicabilityDao attrs) throws RemoteException {
    this.attrs = attrs;
  }

  public AsqClientApplicabilityDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert(AsqClientApplicabilityDao attrs) throws DupKeyException, UserException {
    try {
      this.attrs = attrs;

      // generate key
      try {
	this.attrs.recordID.setValue(key.GenerateKey("KM_AsqClientApplicability", "recordID"));
      }
      catch (RemoteException ex) {
	getEJBReferences();
	ctx.setRollbackOnly();
	throw new UserException("Generating key");
      }

      dao.create(this.attrs);
      dao.update(this.attrs);
    }
    catch (DupKeyException ex) {
      ctx.setRollbackOnly();
      throw ex;
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
  }

  public void update(AsqClientApplicabilityDao attrs) throws UserException {
    try {
      dao.update(attrs);
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
    this.attrs = attrs;
  }

  public void delete(AsqClientApplicabilityDao attrs) throws ConstraintViolatedException, UserException {
    try {
      this.attrs = attrs;
      dao.remove(attrs);
    }
    catch (ConstraintViolatedException ex) {
      ctx.setRollbackOnly();
      throw ex;
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL!", ex);
    }
  }

  public void findByPrimaryKey(AsqClientApplicabilityDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (AsqClientApplicabilityDao)dao.load(attrs);
    }
    catch (NamingException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no servidor de nomes.", ex);
    }
    catch (SQLException ex) {
      ctx.setRollbackOnly();
      throw new UserException("Erro no SQL !", ex);
    }
  }

  public void findNext(AsqClientApplicabilityDao attrs) throws FinderException, RowNotFoundException {
    try {
      dao.findNextKey((Object)attrs);
      this.attrs = attrs;
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public void findPrev(AsqClientApplicabilityDao attrs) throws FinderException, RowNotFoundException {
    try {
      dao.findPrevKey((Object)attrs);
      this.attrs = attrs;
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public Collection listAll() throws RemoteException {
    try {
      return dao.listAll();
    }
    catch (Exception ex) {
      throw new RemoteException(ex.toString());
    }
  }

  public Collection find(AsqClientApplicabilityDao attrs) throws FinderException, RowNotFoundException {
    try {
      return dao.find((Object)attrs);
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public Collection findExact(AsqClientApplicabilityDao attrs) throws FinderException, RowNotFoundException {
    try {
      return dao.findExact((Object)attrs);
    }
    catch (RowNotFoundException rnfex) {
      throw rnfex;
    }
    catch (Exception ex) {
      throw new FinderException(ex.toString());
    }
  }

  public Vector<AsqClientApplicabilityDao> findExactWithNulls(AsqClientApplicabilityDao attrs) throws FinderException, RowNotFoundException {
    Vector<Vector> resultQuery = null;
    Vector<AsqClientApplicabilityDao> result = new Vector<AsqClientApplicabilityDao>();

    try {
      Vector binds = new Vector();
      String selectStmt = "SELECT recordID, enterpriseID, regID, app_dir, app_ind, app_inf, state,obs";
      String whereStmt = "enterpriseID = ? AND regID = ?";

      binds.add(attrs.enterpriseID);
      binds.add(attrs.regID);

      if (attrs.userID.getValue() != null) {
	selectStmt += ", " + attrs.userID.getField();
	whereStmt += " AND " + attrs.userID.getField() + " = ?";
	binds.add(attrs.userID);
      }
      else
	whereStmt += " AND " + attrs.userID.getField() + " IS NULL";

      if (attrs.profileID.getValue() != null) {
	selectStmt += ", " + attrs.profileID.getField();
	whereStmt += " AND " + attrs.profileID.getField() + " = ?";
	binds.add(attrs.profileID);
      }
      else
	whereStmt += " AND " + attrs.profileID.getField() + " IS NULL";

      if (attrs.legalRequirmentID.getValue() != null) {
	selectStmt += ", " + attrs.legalRequirmentID.getField();
	whereStmt += " AND " + attrs.legalRequirmentID.getField() + " = ?";
	binds.add(attrs.legalRequirmentID);
      }
      else
	whereStmt += " AND " + attrs.legalRequirmentID.getField() + " IS NULL";

      resultQuery = gQuery.doQuery(selectStmt + " FROM AsqClientApplicability WHERE " + whereStmt, binds);
    }
    catch (RemoteException ex) {
    }

    if (resultQuery.size() == 0)
      throw new RowNotFoundException();

    for (Vector row : resultQuery) {
      AsqClientApplicabilityDao acaDao = new AsqClientApplicabilityDao();
      acaDao.recordID.setValue(((TplString)row.elementAt(0)).getValue());
      acaDao.enterpriseID.setValue(((TplString)row.elementAt(1)).getValue());
      acaDao.regID.setValue(((TplString)row.elementAt(2)).getValue());
      acaDao.addDir.setValue(((TplBoolean)row.elementAt(3)).getValue());
      acaDao.addInd.setValue(((TplBoolean)row.elementAt(4)).getValue());
      acaDao.addInf.setValue(((TplBoolean)row.elementAt(5)).getValue());
      if (((TplObject)row.elementAt(6)).getValueAsObject() != null) {
	acaDao.state.setValue(((TplBoolean)row.elementAt(6)).getValue());
      }
      else {
	acaDao.state.resetValue();
      }
      acaDao.obs.setValue(((TplString)row.elementAt(7)).getValue());

      int i = 0;
      if (attrs.userID.getValue() != null)
	acaDao.userID.setValue(((TplString)row.elementAt(8 + i++)).getValue());
      if (attrs.profileID.getValue() != null)
	acaDao.profileID.setValue(((TplString)row.elementAt(8 + i++)).getValue());
      if (attrs.legalRequirmentID.getValue() != null)
	acaDao.legalRequirmentID.setValue(((TplString)row.elementAt(8 + i++)).getValue());

      result.add(acaDao);
    }

    return result;
  }

  public void applyRegulatoryList(Vector<AsqApplyRegulatoryDao> appRegList) throws UserException {
    for (AsqApplyRegulatoryDao aarDao : appRegList) {
      // set state to don't care
      if (aarDao.state == null)
        aarDao.state = new Integer(3);

      // is for a legal requirement?
      boolean forLR = aarDao.legalRequirement != null;

      // apply to all enterprises in this profile group ?
      if (aarDao.profileGroup != null) {
	Vector binds = new Vector();
	binds.add(new TplString(aarDao.profileGroup));
	Collection<ProfileGroupHasEnterpriseDao> enterprises = null;
	try {
	  pgHasEnterprise.setLinkCondition("profileGroupID = ?", binds);
	  enterprises = pgHasEnterprise.listAll();
	}
	catch (RemoteException ex1) {
	  throw new UserException(ex1.getMessage());
	}
	catch (RowNotFoundException ex1) {
	}
	for (ProfileGroupHasEnterpriseDao enterprise : enterprises) {
          forLR = aarDao.legalRequirement != null;
	  AsqClientApplicabilityDao pattern = new AsqClientApplicabilityDao();
	  pattern.enterpriseID.setValue(enterprise.enterpriseID.getValue());
	  pattern.regID.setValue(aarDao.regulatory);
	  if (aarDao.legalRequirement != null) {
	    pattern.legalRequirmentID.setValue(aarDao.legalRequirement);
	  }
	  try {
	    Collection<AsqClientApplicabilityDao> findedList = findExactWithNulls(pattern);
	    for (AsqClientApplicabilityDao acaDao : findedList) {
	      //delete
	      if (aarDao.app.intValue() == 0) {
		try {
		  delete(acaDao);
		}
		catch (UserException ex3) {
		  continue;
		}
		catch (ConstraintViolatedException ex3) {
		  continue;
		}
	      }
	      //update
	      else {
		acaDao.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
		acaDao.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
		acaDao.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
		if (aarDao.state.intValue() == 1) {
		  acaDao.state.setValue(true);
		}
		else
		  if (aarDao.state.intValue() == 2) {
		    acaDao.state.setValue(false);
		  }
		  else
		    if (aarDao.state.intValue() == 3) {
		      acaDao.state.setValue(null);
		    }
		if (aarDao.obs == null || aarDao.obs.equals("")) {
		  acaDao.obs.setValue(null);
		}
		else {
		  acaDao.obs.setValue(aarDao.obs);
		}
		update(acaDao);
                if (forLR) {
		  try {
		    updateRegulatoryApplicabilityFromLR(acaDao);
		  }
		  catch (DupKeyException ex4) {
                    ex4.printStackTrace();
		  }
		}
	      }
	    }
	  }
	  catch (RowNotFoundException ex) {
	    // if app value is 0 then there's nothing to do!
	    if (aarDao.app.intValue() != 0) {
	      pattern.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
	      pattern.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
	      pattern.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
	      if (aarDao.state.intValue() == 1) {
		pattern.state.setValue(true);
	      }
	      else {
		if (aarDao.state.intValue() == 2) {
		  pattern.state.setValue(false);
		}
		else {
		  if (aarDao.state.intValue() == 3) {
		    pattern.state.setValue(null);
		  }
		}
	      }
	      if (aarDao.obs == null || aarDao.obs.equals("")) {
		pattern.obs.setValue(null);
	      }
	      else {
		pattern.obs.setValue(aarDao.obs);
	      }

	      try {
		insert(pattern);
                if (forLR) {
                  try {
                    updateRegulatoryApplicabilityFromLR(pattern);
                  }
                  catch (DupKeyException ex4) {
                    ex4.printStackTrace();
                  }
                }
	      }
	      catch (DupKeyException dkex) {
		dkex.printStackTrace();
	      }
	    }
	  }
	  catch (FinderException ex) {
	  }
	}
      }
      // apply only for this enterprise ?
      else
	if (aarDao.enterprise != null && aarDao.user == null) {
	  AsqClientApplicabilityDao pattern = new AsqClientApplicabilityDao();
	  pattern.enterpriseID.setValue(aarDao.enterprise);
	  pattern.regID.setValue(aarDao.regulatory);
	  if (aarDao.legalRequirement != null)
	    pattern.legalRequirmentID.setValue(aarDao.legalRequirement);
	  try {
	    Collection<AsqClientApplicabilityDao> findedList = findExactWithNulls(pattern);
	    for (AsqClientApplicabilityDao acaDao : findedList) {
	      //delete
	      if (aarDao.app.intValue() == 0) {
		try {
		  delete(acaDao);
		}
		catch (UserException ex3) {
		  continue;
		}
		catch (ConstraintViolatedException ex3) {
		  continue;
		}
	      }
	      //update
	      else {
		acaDao.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
		acaDao.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
		acaDao.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
		if (aarDao.state.intValue() == 1) {
		  acaDao.state.setValue(true);
		}
		else
		  if (aarDao.state.intValue() == 2) {
		    acaDao.state.setValue(false);
		  }
		  else
		    if (aarDao.state.intValue() == 3) {
		      acaDao.state.setValue(null);
		    }
                if (aarDao.obs == null || aarDao.obs.equals("")) {
		  acaDao.obs.setValue(null);
		}
		else {
		  acaDao.obs.setValue(aarDao.obs);
		}
		update(acaDao);
                if (forLR) {
                  try {
                    updateRegulatoryApplicabilityFromLR(acaDao);
                  }
                  catch (DupKeyException ex4) {
                    ex4.printStackTrace();
                  }
                }
	      }
	    }
	  }
	  catch (RowNotFoundException ex) {
	    // if app value is 0 then there's nothing to do!
	    if (aarDao.app.intValue() != 0) {
	      pattern.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
	      pattern.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
	      pattern.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
	      if (aarDao.state.intValue() == 1) {
		pattern.state.setValue(true);
	      }
	      else
		if (aarDao.state.intValue() == 2) {
		  pattern.state.setValue(false);
		}
		else
		  if (aarDao.state.intValue() == 3) {
		    pattern.state.setValue(null);
		  }
              if (aarDao.obs == null || aarDao.obs.equals("")) {
		pattern.obs.setValue(null);
	      }
	      else {
		pattern.obs.setValue(aarDao.obs);
	      }

	      try {
		insert(pattern);
	      }
	      catch (DupKeyException dkex) {
		dkex.printStackTrace();
	      }
              if (forLR) {
                try {
                  updateRegulatoryApplicabilityFromLR(pattern);
                }
                catch (DupKeyException ex4) {
                  ex4.printStackTrace();
                }
              }
	    }
	  }
	  catch (FinderException ex) {
	  }
	}
	// apply to all profiles for this user ?
	else
	  if (aarDao.profileUser == null) {
	    UserProfileDao finder = new UserProfileDao();
	    finder.userID.setValue(aarDao.user);
	    try {
	      Collection<UserProfileDao> upList = (Collection<UserProfileDao>)userProfiles.findExact(finder);
	      for (UserProfileDao upDao : upList) {
		AsqClientApplicabilityDao pattern = new AsqClientApplicabilityDao();
		pattern.enterpriseID.setValue(aarDao.enterprise);
		pattern.regID.setValue(aarDao.regulatory);
		if (aarDao.legalRequirement != null)
		  pattern.legalRequirmentID.setValue(aarDao.legalRequirement);
		pattern.userID.setValue(aarDao.user);
		pattern.profileID.setValue(upDao.profileID.getValue());
		try {
		  Collection<AsqClientApplicabilityDao> findedList = findExactWithNulls(pattern);
		  for (AsqClientApplicabilityDao acaDao : findedList) {
		    //delete
		    if (aarDao.app.intValue() == 0) {
		      try {
			delete(acaDao);
		      }
		      catch (UserException ex3) {
			continue;
		      }
		      catch (ConstraintViolatedException ex3) {
			continue;
		      }
		    }
		    //update
		    else {
		      acaDao.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
		      acaDao.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
		      acaDao.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
		      if (aarDao.state.intValue() == 1) {
			acaDao.state.setValue(true);
		      }
		      else
			if (aarDao.state.intValue() == 2) {
			  acaDao.state.setValue(false);
			}
			else
			  if (aarDao.state.intValue() == 3) {
			    acaDao.state.setValue(null);
			  }
                      if (aarDao.obs == null || aarDao.obs.equals("")) {
			acaDao.obs.setValue(null);
		      }
		      else {
			acaDao.obs.setValue(aarDao.obs);
		      }

		      update(acaDao);
                      if (forLR) {
                        try {
                          updateRegulatoryApplicabilityFromLR(acaDao);
                        }
                        catch (DupKeyException ex4) {
                          ex4.printStackTrace();
                        }
                      }
		    }
		  }
		}
		catch (RowNotFoundException ex) {
		  // if app value is 0 then there's nothing to do!
		  if (aarDao.app.intValue() != 0) {
		    pattern.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
		    pattern.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
		    pattern.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
		    if (aarDao.state.intValue() == 1) {
		      pattern.state.setValue(true);
		    }
		    else
		      if (aarDao.state.intValue() == 2) {
			pattern.state.setValue(true);
		      }
		      else
			if (aarDao.state.intValue() == 3) {
			  pattern.state.setValue(null);
			}
                    if (aarDao.obs == null || aarDao.obs.equals("")) {
		      pattern.obs.setValue(null);
		    }
		    else {
		      pattern.obs.setValue(aarDao.obs);
		    }
		    try {
		      insert(pattern);
                      if (forLR) {
                        try {
                          updateRegulatoryApplicabilityFromLR(pattern);
                        }
                        catch (DupKeyException ex4) {
                          ex4.printStackTrace();
                        }
                      }
		    }
		    catch (DupKeyException dkex) {
		      dkex.printStackTrace();
		    }
		  }
		}
		catch (FinderException ex) {
		}
	      }
	    }
	    catch (RowNotFoundException ex2) {
	    }
	    catch (FinderException ex2) {
	    }
	    catch (RemoteException ex2) {
	    }
	  }
	  else {
	    AsqClientApplicabilityDao pattern = new AsqClientApplicabilityDao();
	    pattern.enterpriseID.setValue(aarDao.enterprise);
	    pattern.regID.setValue(aarDao.regulatory);
	    if (aarDao.legalRequirement != null)
	      pattern.legalRequirmentID.setValue(aarDao.legalRequirement);
	    pattern.userID.setValue(aarDao.user);
	    pattern.profileID.setValue(aarDao.profileUser);
	    try {
	      Collection<AsqClientApplicabilityDao> findedList = findExactWithNulls(pattern);
	      for (AsqClientApplicabilityDao acaDao : findedList) {
		//delete
		if (aarDao.app.intValue() == 0) {
		  try {
		    delete(acaDao);
		  }
		  catch (UserException ex3) {
		    continue;
		  }
		  catch (ConstraintViolatedException ex3) {
		    continue;
		  }
		}
		//update
		else {
		  acaDao.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
		  acaDao.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
		  acaDao.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
		  if (aarDao.state.intValue() == 1) {
		    acaDao.state.setValue(true);
		  }
		  else
		    if (aarDao.state.intValue() == 2) {
		      acaDao.state.setValue(false);
		    }
		    else
		      if (aarDao.state.intValue() == 3) {
			acaDao.state.setValue(null);
		      }
                  if (aarDao.obs == null || aarDao.obs.equals("")) {
		    acaDao.obs.setValue(null);
		  }
		  else {
		    acaDao.obs.setValue(aarDao.obs);
		  }

		  update(acaDao);
                  if (forLR) {
                    try {
                      updateRegulatoryApplicabilityFromLR(acaDao);
                    }
                    catch (DupKeyException ex4) {
                      ex4.printStackTrace();
                    }
                  }
		}
	      }
	    }
	    catch (RowNotFoundException ex) {
	      // if app value is 0 then there's nothing to do!
	      if (aarDao.app.intValue() != 0) {
		pattern.addDir.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.DIRECT_APP) > 0));
		pattern.addInd.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INDIRECT_APP) > 0));
		pattern.addInf.setValue(new Boolean((aarDao.app.intValue() & UserProfileDao.INFORM_APP) > 0));
		if (aarDao.state.intValue() == 1) {
		  pattern.state.setValue(true);
		}
		else
		  if (aarDao.state.intValue() == 2) {
		    pattern.state.setValue(false);
		  }
		  else
		    if (aarDao.state.intValue() == 3) {
		      pattern.state.setValue(null);
		    }
                if (aarDao.obs == null || aarDao.obs.equals("")) {
		  pattern.obs.setValue(null);
		}
		else {
		  pattern.obs.setValue(aarDao.obs);
		}

		try {
		  insert(pattern);
                  if (forLR) {
                    try {
                      updateRegulatoryApplicabilityFromLR(pattern);
                    }
                    catch (DupKeyException ex4) {
                      ex4.printStackTrace();
                    }
                  }
		}
		catch (DupKeyException dkex) {
		  dkex.printStackTrace();
		}
	      }
	    }
	    catch (FinderException ex) {
	    }
	  }
    }
  }

  private void updateRegulatoryApplicabilityFromLR(AsqClientApplicabilityDao record) throws DupKeyException, UserException {
    // if legal requirement isn't set then there's nothing to do!
    if (record.legalRequirmentID.getValue() == null)
      return;

    //is there any entry for the regulatory?
    Vector<TplObject> gBinds = new Vector<TplObject>();
    try {
      String selectStmt = "SELECT regid FROM AsqClientApplicability WHERE regID = ? AND legalRequirementid is null";
      gBinds.add(new TplString(record.regID.getValue()));
      // bind enterprise
      if (record.enterpriseID.getValue() != null) {
	selectStmt += " AND enterpriseID = ?";
	gBinds.add(new TplString(record.enterpriseID.getValue()));
      }
      // bind user
      if (record.userID.getValue() != null) {
	selectStmt += " AND userID = ?";
	gBinds.add(new TplString(record.userID.getValue()));
      }
      // bind profile
      if (record.profileID.getValue() != null) {
	selectStmt += " AND profileID = ?";
	gBinds.add(new TplString(record.profileID.getValue()));
      }
      // bind profile
      if (record.profileID.getValue() != null) {
	selectStmt += " AND profileID = ?";
	gBinds.add(new TplString(record.profileID.getValue()));
      }

      // prepare record for the regulatory
      AsqClientApplicabilityDao newDao = new AsqClientApplicabilityDao();
      newDao.regID.setValue(record.regID.getValue());
      newDao.enterpriseID.setValue(record.enterpriseID.getValue());
      newDao.userID.setValue(record.userID.getValue());
      newDao.profileID.setValue(record.profileID.getValue());

      Vector<Vector<TplObject>> result = gQuery.doQuery(selectStmt, gBinds);
      //insert
      if (result.size() == 0) {
	newDao.addDir.setValue(record.addDir.getValue());
	newDao.addInd.setValue(record.addInd.getValue());
	newDao.addInf.setValue(record.addInf.getValue());
	try {
	  insert(newDao);
	}
	catch (DupKeyException ex) {
	  ctx.setRollbackOnly();
	  throw ex;
	}
	catch (UserException ex) {
	  ctx.setRollbackOnly();
	  throw ex;
	}
      }
      else {
	gBinds = new Vector<TplObject>();
	String stmtDir = "select distinct(app_dir) from asqclientapplicability WHERE regID = ? AND legalrequirementid is not null";
	String stmtInd = "select distinct(app_ind) from asqclientapplicability WHERE regID = ? AND legalrequirementid is not null";
	String stmtInf = "select distinct(app_inf) from asqclientapplicability WHERE regID = ? AND legalrequirementid is not null";
	gBinds.add(new TplString(record.regID.getValue()));
	// bind enterprise
	if (record.enterpriseID.getValue() != null) {
	  stmtDir += " AND enterpriseID = ?";
	  stmtInd += " AND enterpriseID = ?";
	  stmtInf += " AND enterpriseID = ?";
	  gBinds.add(new TplString(record.enterpriseID.getValue()));
	}
	// bind user
	if (record.userID.getValue() != null) {
	  stmtDir += " AND userID = ?";
	  stmtInd += " AND userID = ?";
	  stmtInf += " AND userID = ?";
	  gBinds.add(new TplString(record.userID.getValue()));
	}
	// bind profile
	if (record.profileID.getValue() != null) {
	  stmtDir += " AND profileID = ?";
	  stmtInd += " AND profileID = ?";
	  stmtInf += " AND profileID = ?";
	  gBinds.add(new TplString(record.profileID.getValue()));
	}
	// bind profile
	if (record.profileID.getValue() != null) {
	  stmtDir += " AND profileID = ?";
	  stmtInd += " AND profileID = ?";
	  stmtInf += " AND profileID = ?";
	  gBinds.add(new TplString(record.profileID.getValue()));
	}

	Vector<Vector<TplObject>> rDir = gQuery.doQuery(stmtDir, gBinds);
	Vector<Vector<TplObject>> rInd = gQuery.doQuery(stmtInd, gBinds);
	Vector<Vector<TplObject>> rInf = gQuery.doQuery(stmtInf, gBinds);

	// getRecordFor Regulatory
	newDao.addDir.setValue(rDir.size() == 1 ? new Boolean((Boolean)rDir.elementAt(0).elementAt(0).getValueAsObject()) : Boolean.TRUE);
	newDao.addInd.setValue(rInd.size() == 1 ? new Boolean((Boolean)rInd.elementAt(0).elementAt(0).getValueAsObject()) : Boolean.TRUE);
	newDao.addInf.setValue(rInf.size() == 1 ? new Boolean((Boolean)rInf.elementAt(0).elementAt(0).getValueAsObject()) : Boolean.TRUE);

        update(newDao);
      }
    }
    catch (RemoteException ex) {
      ex.printStackTrace();
    }
  }
}
