package pt.inescporto.template.email.ejb.session;

import java.util.Collection;
import java.util.Vector;
import java.rmi.RemoteException;

import javax.mail.MessagingException;
import javax.ejb.FinderException;
import javax.ejb.EJBObject;

import pt.inescporto.template.email.ejb.dao.SMTPConfigDao;
import pt.inescporto.template.dao.RowNotFoundException;
import pt.inescporto.template.dao.DupKeyException;
import pt.inescporto.template.dao.UserException;
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
 * @author not attributable
 * @version 0.1
 */
public interface SMTPConfig extends EJBObject {
  public void setLinkCondition(String linkCondition, Vector binds) throws RemoteException;

  public void setData(SMTPConfigDao attrs) throws RemoteException;

  public SMTPConfigDao getData() throws RemoteException;

  public void insert(SMTPConfigDao attrs) throws RemoteException, DupKeyException, UserException;

  public void update(SMTPConfigDao attrs) throws RemoteException, UserException;

  public void delete(SMTPConfigDao attrs) throws RemoteException, ConstraintViolatedException, UserException;

  public void findByPrimaryKey(SMTPConfigDao attrs) throws RemoteException, RowNotFoundException, UserException;

  public void findNext(SMTPConfigDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public void findPrev(SMTPConfigDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection listAll() throws RemoteException, RowNotFoundException, UserException;

  public Collection find(SMTPConfigDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public Collection findExact(SMTPConfigDao attrs) throws RemoteException, FinderException, RowNotFoundException;

  public String[] lookupDesc(Object[] pkKeys) throws RemoteException, RowNotFoundException;

  public void postMail(String serverName, Vector toEmailsList, String subject, String message, String from, boolean html) throws RemoteException, UserException;
}
