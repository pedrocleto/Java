/*
 * This class was automatically generated with
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id: Parameter.java,v 1.1 2006/09/26 10:08:14 jap Exp $
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
import pt.inescporto.template.reports.parser.objects.types.ParameterTypeType;

/**
 * Class Parameter.
 *
 * @version $Revision: 1.1 $ $Date: 2006/09/26 10:08:14 $
 */
public class Parameter implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _name
     */
    private java.lang.String _name;

    /**
     * Field _value
     */
    private java.lang.String _value;

    /**
     * Field _type
     */
    private pt.inescporto.template.reports.parser.objects.types.ParameterTypeType _type;

    /**
     * Field _anchor
     */
    private java.lang.String _anchor;


      //----------------/
     //- Constructors -/
    //----------------/

    public Parameter() {
        super();
    } //-- pt.inescporto.template.reports.parser.objects.Parameter()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'anchor'.
     *
     * @return the value of field 'anchor'.
     */
    public java.lang.String getAnchor()
    {
        return this._anchor;
    } //-- java.lang.String getAnchor()

    /**
     * Returns the value of field 'name'.
     *
     * @return the value of field 'name'.
     */
    public java.lang.String getName()
    {
        return this._name;
    } //-- java.lang.String getName()

    /**
     * Returns the value of field 'type'.
     *
     * @return the value of field 'type'.
     */
    public pt.inescporto.template.reports.parser.objects.types.ParameterTypeType getType()
    {
        return this._type;
    } //-- pt.inescporto.template.reports.parser.objects.types.ParameterTypeType getType()

    /**
     * Returns the value of field 'value'.
     *
     * @return the value of field 'value'.
     */
    public java.lang.String getValue()
    {
        return this._value;
    } //-- java.lang.String getValue()

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
     * Sets the value of field 'anchor'.
     *
     * @param anchor the value of field 'anchor'.
     */
    public void setAnchor(java.lang.String anchor)
    {
        this._anchor = anchor;
    } //-- void setAnchor(java.lang.String)

    /**
     * Sets the value of field 'name'.
     *
     * @param name the value of field 'name'.
     */
    public void setName(java.lang.String name)
    {
        this._name = name;
    } //-- void setName(java.lang.String)

    /**
     * Sets the value of field 'type'.
     *
     * @param type the value of field 'type'.
     */
    public void setType(pt.inescporto.template.reports.parser.objects.types.ParameterTypeType type)
    {
        this._type = type;
    } //-- void setType(pt.inescporto.template.reports.parser.objects.types.ParameterTypeType)

    /**
     * Sets the value of field 'value'.
     *
     * @param value the value of field 'value'.
     */
    public void setValue(java.lang.String value)
    {
        this._value = value;
    } //-- void setValue(java.lang.String)

    /**
     * Method unmarshal
     *
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (pt.inescporto.template.reports.parser.objects.Parameter) Unmarshaller.unmarshal(pt.inescporto.template.reports.parser.objects.Parameter.class, reader);
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
