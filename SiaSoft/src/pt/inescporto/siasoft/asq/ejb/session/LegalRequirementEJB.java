package pt.inescporto.siasoft.asq.ejb.session;

import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;
import javax.naming.NamingException;
import javax.ejb.SessionContext;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.siasoft.asq.ejb.dao.LegalRequirementDao;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import pt.inescporto.siasoft.asq.ejb.dao.AsqClientApplicabilityDao;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import pt.inescporto.template.ejb.session.GenericQuery;
import pt.inescporto.template.elements.TplString;

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
public class LegalRequirementEJB implements SessionBean {
  protected transient GenericQuery gQuery = null;
  protected SessionContext ctx;
  protected LegalRequirementDao attrs = new LegalRequirementDao();
  protected DAO dao = null;

  public LegalRequirementEJB() {
    System.out.println("New LegalRequirement Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get Generic Query
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GenericQuery");
      GenericQueryHome gQueryHome = (GenericQueryHome)PortableRemoteObject.narrow(objref, GenericQueryHome.class);
      gQuery = gQueryHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate Key Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "LegalRequirement");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "LegalRequirement", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "LegalRequirement");
    }
    else {
      if (binds == null)
	binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "LegalRequirement", linkCondition, binds);
    }
  }

  public void setData(LegalRequirementDao attrs) {
    this.attrs = attrs;
  }

  public LegalRequirementDao getData() {
    return this.attrs;
  }

  public void insert(LegalRequirementDao attrs) throws DupKeyException, UserException {
    try {
      this.attrs = attrs;
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

  public void update(LegalRequirementDao attrs) throws UserException {
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

  public void delete(LegalRequirementDao attrs) throws ConstraintViolatedException, UserException {
    try {
      // first delete applicabillity
      Vector gBinds = new Vector();
      gBinds.add(attrs.regId);
      gBinds.add(attrs.legalReqId);
      gQuery.doExecuteQuery("DELETE FROM asqclientapplicability WHERE regID = ? AND legalRequirementID = ?", gBinds);

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
    catch (RemoteException ex) {
      getEJBReferences();
      ctx.setRollbackOnly();
     throw new UserException("Impossível Eliminar dependências!", ex);
   }

 }

  public void findByPrimaryKey(LegalRequirementDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (LegalRequirementDao)dao.load(attrs);
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

  public void findNext(LegalRequirementDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(LegalRequirementDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection listAll() throws RowNotFoundException, UserException {
    try {
      return dao.listAll();
    }
    catch (RowNotFoundException ex) {
      ctx.setRollbackOnly();
      throw ex;
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

  public Collection find(LegalRequirementDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(LegalRequirementDao attrs) throws FinderException, RowNotFoundException {
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

  public String[] lookupDesc(Object[] pkKeys) throws RowNotFoundException {
    try {
      attrs.regId.setValue((String)pkKeys[0]);
      attrs.legalReqId.setValue((String)pkKeys[1]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.legalReqDescription.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
