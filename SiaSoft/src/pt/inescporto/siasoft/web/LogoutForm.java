package pt.inescporto.siasoft.web;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

/**
 *
 * <p>Description: Logout Form</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: INESCPorto</p>
 * @author Pedro Ribeiro
 * @version 1.0
 */
public class LogoutForm extends ActionForm {

  protected String action;
  // --------------------------------------------------------- Public Methods

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
   * resets action
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    this.action = null;
  }

}
