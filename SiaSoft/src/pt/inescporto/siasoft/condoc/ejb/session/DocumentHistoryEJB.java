package pt.inescporto.siasoft.condoc.ejb.session;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;
import java.sql.Timestamp;
import java.sql.SQLException;

import javax.ejb.SessionContext;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.rmi.PortableRemoteObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DAO;
import pt.inescporto.template.dao.UserException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.ConstraintViolatedException;
import pt.inescporto.template.email.ejb.session.SMTPConfig;
import pt.inescporto.template.email.ejb.session.SMTPConfigHome;

import pt.inescporto.siasoft.condoc.ejb.dao.DocumentHistoryDao;
import pt.inescporto.siasoft.comun.ejb.session.WarningMessage;
import pt.inescporto.siasoft.comun.ejb.session.WarningMessageHome;
import pt.inescporto.siasoft.comun.ejb.dao.WarningMessageDao;
import pt.inescporto.siasoft.comun.ejb.session.User;
import pt.inescporto.siasoft.comun.ejb.session.UserHome;
import pt.inescporto.siasoft.comun.ejb.dao.UserDao;

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
public class DocumentHistoryEJB implements SessionBean {
  protected transient WarningMessage warningMessage = null;
  protected transient SMTPConfig smtpConfig = null;
  protected transient User user = null;
  protected SessionContext ctx;
  protected DocumentHistoryDao attrs = new DocumentHistoryDao();
  protected DAO dao = null;

  public DocumentHistoryEJB() {
    System.out.println("New DocumentHistory Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // get WarningMessage Generation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/WarningMessage");
      WarningMessageHome warningMessageHome = (WarningMessageHome)PortableRemoteObject.narrow(objref, WarningMessageHome.class);
      warningMessage = warningMessageHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate WarningMessage Home");
      re.printStackTrace();
    }

    // get SMTPConfig Generation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/SMTPConfig");
      SMTPConfigHome smtpConfigHome = (SMTPConfigHome)PortableRemoteObject.narrow(objref, SMTPConfigHome.class);
      smtpConfig = smtpConfigHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate WarningMessage Home");
      re.printStackTrace();
    }

    // get User Generation
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/User");
      UserHome userHome = (UserHome)PortableRemoteObject.narrow(objref, UserHome.class);
      user = userHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate WarningMessage Home");
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
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "DocHistory");
  }

  public void ejbCreate(String linkCondition, Vector binds) {
    dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "DocHistory", linkCondition, binds);
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

  public void setAttrs(DocumentHistoryDao attrs) {
    try {
      setData(attrs);
      dao = new DAO(attrs, "", "java:comp/env/jdbc/siasoft", "DocHistory");
    }
    catch (RemoteException ex) {
    }
  }

  public void setLinkCondition(String linkCondition, Vector binds) {
    if (linkCondition == null) {
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "DocHistory");
    }
    else {
      if (binds == null)
        binds = new Vector();
      dao = new DAO((Object)attrs, "", "java:comp/env/jdbc/siasoft", "DocHistory", linkCondition, binds);
    }
  }

  public void setData(DocumentHistoryDao attrs) throws RemoteException {
    this.attrs = attrs;
  }

  public DocumentHistoryDao getData() throws RemoteException {
    return this.attrs;
  }

  public void insert(DocumentHistoryDao attrs) throws DupKeyException, UserException {
    try {
      this.attrs = attrs;
      dao.create(this.attrs);
      dao.update(this.attrs);

      // post a warning message to the user
      WarningMessageDao wmDao = new WarningMessageDao();
      wmDao.message.setValue("&startMessageDocument& #documentID# &endMessageDocument&");
      wmDao.moduleID.setValue(wmDao.docModuleHistory);
      wmDao.fkUserID.setValue(this.attrs.fkUserID.getValue());
      wmDao.dateRef.setValue(this.attrs.actionDate.getValue());
      wmDao.keyRef.setValue(new Object[] {this.attrs.documentID, this.attrs.actionDate});
      warningMessage.insert(wmDao);

      // post a email message to the user
      UserDao userAttrs = new UserDao(this.attrs.fkUserID.getValue());
      try {
	user.findByPrimaryKey(userAttrs);
	userAttrs = user.getData();
      }
      catch (UserException ex2) {
        ex2.printStackTrace();
      }
      catch (RowNotFoundException ex2) {
        ex2.printStackTrace();
      }
      catch (RemoteException ex2) {
        ex2.printStackTrace();
      }

      if (userAttrs.eMail.getValue() != null) {
        Vector userList = new Vector();
        userList.add(userAttrs.eMail.getValue());
	try {
          // null for hostname (first will be used) and null for from (used SMTPConfig userFrom)
	  smtpConfig.postMail(null, userList,
			      "[SIASoft] O documento " + attrs.documentID.getValue() + " foi alterado.",
			      userAttrs.userName.getValue() + ",\r\n\r\nO documento " + attrs.documentID.getValue() + " requer a sua aprovação/revisão.\r\n\r\nObservações :\r\n" + (attrs.obs.getValue() == null ? "Nenhuma" : attrs.obs.getValue()),
			      null, false);
	}
	catch (UserException ex1) {
          ctx.setRollbackOnly();
          throw ex1;
	}
	catch (RemoteException ex1) {
          ctx.setRollbackOnly();
          getEJBReferences();
          throw new UserException("Erro Remoto ao enviar mensagem de email!", ex1);
	}
      }
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
    catch (RemoteException ex) {
      ctx.setRollbackOnly();
      getEJBReferences();
      throw new UserException("Erro ao criar mensagem!", ex);
    }
  }

  public void update(DocumentHistoryDao attrs) throws UserException {
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

  public void delete(DocumentHistoryDao attrs) throws ConstraintViolatedException, UserException {
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

  public void findByPrimaryKey(DocumentHistoryDao attrs) throws RowNotFoundException, UserException {
    try {
      dao.findByPrimaryKey((Object)attrs);
      this.attrs = (DocumentHistoryDao)dao.load(attrs);
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

  public void findNext(DocumentHistoryDao attrs) throws FinderException, RowNotFoundException {
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

  public void findPrev(DocumentHistoryDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection find(DocumentHistoryDao attrs) throws FinderException, RowNotFoundException {
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

  public Collection findExact(DocumentHistoryDao attrs) throws FinderException, RowNotFoundException {
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
      attrs.documentID.setValue((String)pkKeys[0]);
      attrs.actionDate.setValue((Timestamp)pkKeys[1]);
      findByPrimaryKey(attrs);

      String[] MenuDesc = {attrs.fkUserID.getValue()};
      return MenuDesc;
    }
    catch (Exception rex) {
      throw new RowNotFoundException();
    }
  }
}
