package pt.inescporto.siasoft.proc.ejb.session;

import javax.ejb.SessionContext;
import java.sql.SQLException;
import pt.inescporto.template.dao.DupKeyException;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Collection;
import pt.inescporto.template.dao.UserException;
import javax.ejb.FinderException;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.siasoft.proc.ejb.dao.PartDao;
import java.util.Vector;
import pt.inescporto.template.dao.RowNotFoundException;
import javax.ejb.SessionBean;
import pt.inescporto.template.ejb.session.GenericQuery;
import javax.naming.Context;
import pt.inescporto.template.ejb.session.GenericQueryHome;
import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;
import javax.swing.tree.DefaultMutableTreeNode;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.siasoft.proc.ejb.dao.PartClassDao;
import pt.inescporto.template.dao.ConstraintViolatedException;

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
public class PartEJB implements SessionBean {
  protected transient GenericQuery gQuery = null;
  protected SessionContext ctx;
  protected PartDao attrs = new PartDao();
  protected DAO dao = null;

  public PartEJB() {
    System.out.println("New Part Session Bean created by EJB container ...");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Part");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Part", linkCondition, binds);
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
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Part");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "Part", linkCondition, binds);
    }
  }

  public void setData(PartDao attrs) {
    this.attrs = attrs;
  }

  public PartDao getData() {
    return this.attrs;
  }

  public void insert(PartDao attrs) throws DupKeyException, UserException {
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

  public void update(PartDao attrs) throws UserException {
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

  public void delete(PartDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(PartDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (PartDao)dao.load(attrs);
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

  public void findNext(PartDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(PartDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection listAllOrderBy(String orderByStmt) throws RemoteException {
    try {
      return dao.listAllOrderBy(orderByStmt);
    }
    catch (Exception ex) {
      throw new RemoteException(ex.toString());
    }
  }

  public Collection find(PartDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(PartDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.partClassID.setValue((String)pkKeys[0]);
      attrs.partID.setValue((String)pkKeys[1]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.partDescription.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }

  public DefaultMutableTreeNode getPartTree() throws UserException {
    DefaultMutableTreeNode partTree = new DefaultMutableTreeNode();
    String partClassID = null;

    Vector result = null;
    try {
      result = gQuery.doQuery("SELECT a.partClassID, a.partClassDescription, b.partID, b.partDescription " +
			      "FROM PartClass a, Part b " +
			      "WHERE a.partClassID = b.partClassID " +
			      "ORDER BY a.partClassDescription, b.partDescription", new Vector());
    }
    catch (RemoteException ex) {
      throw new UserException(ex.getMessage());
    }

    DefaultMutableTreeNode partClassNode = null;
    for (int i = 0; i < result.size(); i++) {
      Vector row = (Vector)result.elementAt(i);
      if (partClassID == null || (partClassID != null && !partClassID.equals(((TplString)row.elementAt(0)).getValue()))) {
        partClassID = ((TplString)row.elementAt(0)).getValue();
        PartClassDao partClass = new PartClassDao();
        partClass.partClassID.setValue(partClassID);
        partClass.partClassDescription.setValue(((TplString)row.elementAt(1)).getValue());
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(partClass);
        partClassNode = newNode;
        partTree.add(partClassNode);
      }
      PartDao part = new PartDao();
      part.partClassID.setValue(partClassID);
      part.partID.setValue(((TplString)row.elementAt(2)).getValue());
      part.partDescription.setValue(((TplString)row.elementAt(3)).getValue());
      partClassNode.add(new DefaultMutableTreeNode(part));
    }

    return partTree;
  }
}
