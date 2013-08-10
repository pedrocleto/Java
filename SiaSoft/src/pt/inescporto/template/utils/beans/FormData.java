package pt.inescporto.template.utils.beans;

public class FormData implements java.io.Serializable {
  String formClass = null;
  String formArchives = null;
  String pageTitle = null;
  String support = null;

  public void setFormClass( String formClass ) {
    this.formClass = formClass;
  }

  public String getFormClass() {
    return formClass;
  }

  public void setFormArchives( String formArchives ) {
    this.formArchives = formArchives;
  }

  public String getFormArchives() {
    return formArchives;
  }

  public void setPageTitle( String pageTitle ) {
    this.pageTitle = pageTitle;
  }

  public String getPageTitle() {
    return pageTitle;
  }

  public void setSupport( String support ) {
    this.support = support;
  }

  public String getSupport() {
    return support;
  }
}