package pt.inescporto.template.client.design.thirdparty;

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
/** This is a type-safe enumerated type */
public class State {
  public static final State NOT_SELECTED = new State();
  public static final State SELECTED = new State();
  public static final State DONT_CARE = new State();

  private State() {}
}
