package pt.inescporto.permissions.ejb.session;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import pt.inescporto.permissions.ejb.dao.GlbFormIsInRoleDao;
import pt.inescporto.permissions.ejb.session.GlbFormIsInRoleHome;
import pt.inescporto.permissions.ejb.session.GlbFormIsInRole;
import pt.inescporto.permissions.ejb.dao.GlbFieldPermDao;
import pt.inescporto.template.elements.TplString;

public class GlbPermEJB implements SessionBean {
  protected transient GlbFormIsInRole formIsInRole = null;
  protected transient GlbFieldPerm fieldPerm = null;
  protected SessionContext ctx;

  public GlbPermEJB() {
    System.out.println("New GlbPerm Session Bean created by EJB container ...");
    getEJBReferences();
  }

  private void getEJBReferences() {
    // Get GlbFormIsInRole home
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbFormIsInRole");
      GlbFormIsInRoleHome formIsInRoleHome = (GlbFormIsInRoleHome)PortableRemoteObject.narrow(objref, GlbFormIsInRoleHome.class);
      formIsInRole = formIsInRoleHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate GlbFormIsInRole Home");
      re.printStackTrace();
    }

    // Get GlbFieldPerms home
    try {
      Context ic = new InitialContext();
      java.lang.Object objref = ic.lookup("java:comp/env/ejb/GlbFieldPerm");
      GlbFieldPermHome glbFieldPermHome = (GlbFieldPermHome)PortableRemoteObject.narrow(objref, GlbFieldPermHome.class);
      fieldPerm = glbFieldPermHome.create();
    }
    catch (Exception re) {
      System.out.println("Couldn't locate GlbFieldPerm Home");
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
  /**
   * Get form permissions based on form name identification
   * @param formId Form ID
   * @return bitwise permissions based on <code>TmplPerms</code>
   * @throws RemoteException
   */
  public FormFieldPermission getFormPerms(String roleId, String formId) {
    if (formId.equalsIgnoreCase("NOTDEF"))
      return new FormFieldPermission(formId, new Integer(15), null);
    else {
      try {
        // get permission for form
	GlbFormIsInRoleDao attrs = new GlbFormIsInRoleDao();
	attrs.roleId.setValue(roleId);
	attrs.formId.setValue(formId);
	formIsInRole.findByPrimaryKey(attrs);
	attrs = formIsInRole.getData();

        // get permission for form
        GlbFieldPermDao fpDao = new GlbFieldPermDao();
        Vector binds = new Vector();
        binds.add(new TplString(roleId));
        binds.add(new TplString(formId));
        fieldPerm.setLinkCondition(fpDao.roleId.getField() + " = ? AND " + fpDao.formId.getField() + " = ?", binds);
        Collection<GlbFieldPermDao> fpList = fieldPerm.listAll();
        Hashtable<String, Integer> fieldPermList = new Hashtable<String,Integer>();
        for (GlbFieldPermDao fp : fpList) {
          fieldPermList.put(fp.fieldId.getValue(), fp.permission.getValue());
        }

        FormFieldPermission ffp = new FormFieldPermission(formId, attrs.permission.getValue(), fieldPermList.isEmpty() ? null : fieldPermList);
	return ffp;
      }
      catch (Exception ex) {
	return new FormFieldPermission(formId, new Integer(0), null);
      }
    }
  }
}
