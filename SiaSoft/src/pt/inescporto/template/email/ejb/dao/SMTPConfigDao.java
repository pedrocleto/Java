package pt.inescporto.template.email.ejb.dao;

import java.io.Serializable;
import pt.inescporto.template.elements.TplString;
import pt.inescporto.template.elements.TmplKeyTypes;
import pt.inescporto.template.elements.TplBoolean;

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
public class SMTPConfigDao implements Serializable {
  public TplString hostName = new TplString(0, "hostName", 128, TmplKeyTypes.PKKEY, true);
  public TplString userName = new TplString(1, "userName", 128, TmplKeyTypes.NOKEY, false);
  public TplString passwd = new TplString(2, "password", 64, TmplKeyTypes.NOKEY, false);
  public TplString fromEmail = new TplString(3, "fromEmail", 128, TmplKeyTypes.NOKEY, true);
  public TplString fromUser = new TplString(4, "fromUser", 128, TmplKeyTypes.NOKEY, true);
  public TplBoolean debug = new TplBoolean(5, "debug", TmplKeyTypes.NOKEY, true);

  public SMTPConfigDao() {
  }
}
