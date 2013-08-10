/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: ReportConfig.java,v 1.1 2006/09/26 10:08:18 jap Exp $
 */

package pt.inescporto.template.reports.parser.objects;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class ReportConfig.
 *
 * @version $Revision: 1.1 $ $Date: 2006/09/26 10:08:18 $
 */
public class ReportConfig implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _jasper
     */
    private pt.inescporto.template.reports.parser.objects.Jasper _jasper;


      //----------------/
     //- Constructors -/
    //----------------/

    public ReportConfig() {
        super();
    } //-- pt.inescporto.template.reports.parser.objects.ReportConfig()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'jasper'.
     *
     * @return the value of field 'jasper'.
     */
    public pt.inescporto.template.reports.parser.objects.Jasper getJasper()
    {
        return this._jasper;
    } //-- pt.inescporto.template.reports.parser.objects.Jasper getJasper()

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid()

    /**
     * Method marshal
     *
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {

        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer)

    /**
     * Method marshal
     *
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {

        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler)

    /**
     * Sets the value of field 'jasper'.
     *
     * @param jasper the value of field 'jasper'.
     */
    public void setJasper(pt.inescporto.template.reports.parser.objects.Jasper jasper)
    {
        this._jasper = jasper;
    } //-- void setJasper(pt.inescporto.template.reports.parser.objects.Jasper)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (pt.inescporto.template.reports.parser.objects.ReportConfig) Unmarshaller.unmarshal(pt.inescporto.template.reports.parser.objects.ReportConfig.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader)

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate()

}
