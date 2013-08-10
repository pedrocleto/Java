package pt.inescporto.siasoft.web;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

/**
 *
 * <p>Description: Login Form</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: INESCPorto</p>
 * @author Pedro Ribeiro
 * @version 1.0
 */
public class LoginForm extends ActionForm {
  protected String User;
  protected String Password;

  protected String action;
  // --------------------------------------------------------- Public Methods

   /**
    * sets user
    * @param User user
    */
  public void setUser(String User) {
    this.User = User;
  }

  /**
   * returns user
   */
  public String getUser() {
    return User;
  }

   /**
    * sets password
    * @param Password password
    */
  public void setPassword(String Password) {
    this.Password = Password;
  }

  /**
   * returns password
   */
  public String getPassword() {
    return Password;
  }

   /**
    * sets action
    * @param action action
    */
    public void setAction(String action) {
    this.action = action;
  }

  /**
   * returns action
   */
  public String getAction() {
    return action;
  }

  /**
   * resets user and password
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    this.User = null;
    this.Password = null;
  }

}
