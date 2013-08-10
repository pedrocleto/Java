package pt.inescporto.template.dao;

/**
 * The class <code>UserException</code> is a form of <code>Throwable</code>
 * that indicates a condition that a reasonable
 * application might want to catch.
 *
 * @author  Jorge Pereira
 * @version 1.0
 * @see     java.lang.Error
 * @since   Template 1.0
 */

public class UserException extends Exception {
  Object userValue = null;

  public UserException() {
    super();
  }

  public UserException(String message) {
    super(message);
  }

  public UserException(String message, Object userValue) {
    super(message);
    this.userValue = userValue;
  }

  public Object getUserValue() {
    return userValue;
  }
}
