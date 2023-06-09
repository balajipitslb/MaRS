package its.mnr.mp5.vo.parts;

import its.mnr.mp5.baseclass.ExtendedEntityImpl;
import its.mnr.mp5.baseclass.ExtendedViewRowImpl;

import java.math.BigDecimal;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 11 10:46:04 PST 2013
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class SuppliersRowImpl extends ExtendedViewRowImpl {


    public static final int ENTITY_R5CATALOGUE = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CatCurr {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatCurr();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatCurr((String)value);
            }
        }
        ,
        CatDate {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatDate();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatDate((Timestamp)value);
            }
        }
        ,
        CatExch {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatExch();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatExch((Double)value);
            }
        }
        ,
        CatGross {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatGross();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatGross((Double)value);
            }
        }
        ,
        CatLeadtime {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatLeadtime();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatLeadtime((Double)value);
            }
        }
        ,
        CatMultiply {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatMultiply();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatMultiply((Double)value);
            }
        }
        ,
        CatPart {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatPart();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatPart((String)value);
            }
        }
        ,
        CatPartOrg {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatPartOrg();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatPartOrg((String)value);
            }
        }
        ,
        CatPurprice {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatPurprice();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatPurprice((Double)value);
            }
        }
        ,
        CatPuruom {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatPuruom();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatPuruom((String)value);
            }
        }
        ,
        CatSupplier {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatSupplier();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatSupplier((String)value);
            }
        }
        ,
        CatSupplierOrg {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatSupplierOrg();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatSupplierOrg((String)value);
            }
        }
        ,
        CatTax {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatTax();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatTax((String)value);
            }
        }
        ,
        CatDesc {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCatDesc();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setCatDesc((String)value);
            }
        }
        ,
        tSupplierName {
            public Object get(SuppliersRowImpl obj) {
                return obj.gettSupplierName();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.settSupplierName((String)value);
            }
        }
        ,
        tSupplierIsPref {
            public Object get(SuppliersRowImpl obj) {
                return obj.gettSupplierIsPref();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.settSupplierIsPref((Boolean)value);
            }
        }
        ,
        tPartDesc {
            public Object get(SuppliersRowImpl obj) {
                return obj.gettPartDesc();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.settPartDesc((String)value);
            }
        }
        ,
        Companies {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCompanies();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        PartsMaintView {
            public Object get(SuppliersRowImpl obj) {
                return obj.getPartsMaintView();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MrltPorderlinesvwView {
            public Object get(SuppliersRowImpl obj) {
                return obj.getMrltPorderlinesvwView();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        POOrderlineServices {
            public Object get(SuppliersRowImpl obj) {
                return obj.getPOOrderlineServices();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        R5uoms {
            public Object get(SuppliersRowImpl obj) {
                return obj.getR5uoms();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TaxLOV {
            public Object get(SuppliersRowImpl obj) {
                return obj.getTaxLOV();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        PartsLookup {
            public Object get(SuppliersRowImpl obj) {
                return obj.getPartsLookup();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CompaniesLookup {
            public Object get(SuppliersRowImpl obj) {
                return obj.getCompaniesLookup();
            }

            public void put(SuppliersRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(SuppliersRowImpl object);

        public abstract void put(SuppliersRowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int CATCURR = AttributesEnum.CatCurr.index();
    public static final int CATDATE = AttributesEnum.CatDate.index();
    public static final int CATEXCH = AttributesEnum.CatExch.index();
    public static final int CATGROSS = AttributesEnum.CatGross.index();
    public static final int CATLEADTIME = AttributesEnum.CatLeadtime.index();
    public static final int CATMULTIPLY = AttributesEnum.CatMultiply.index();
    public static final int CATPART = AttributesEnum.CatPart.index();
    public static final int CATPARTORG = AttributesEnum.CatPartOrg.index();
    public static final int CATPURPRICE = AttributesEnum.CatPurprice.index();
    public static final int CATPURUOM = AttributesEnum.CatPuruom.index();
    public static final int CATSUPPLIER = AttributesEnum.CatSupplier.index();
    public static final int CATSUPPLIERORG = AttributesEnum.CatSupplierOrg.index();
    public static final int CATTAX = AttributesEnum.CatTax.index();
    public static final int CATDESC = AttributesEnum.CatDesc.index();
    public static final int TSUPPLIERNAME = AttributesEnum.tSupplierName.index();
    public static final int TSUPPLIERISPREF = AttributesEnum.tSupplierIsPref.index();
    public static final int TPARTDESC = AttributesEnum.tPartDesc.index();
    public static final int COMPANIES = AttributesEnum.Companies.index();
    public static final int PARTSMAINTVIEW = AttributesEnum.PartsMaintView.index();
    public static final int MRLTPORDERLINESVWVIEW = AttributesEnum.MrltPorderlinesvwView.index();
    public static final int POORDERLINESERVICES = AttributesEnum.POOrderlineServices.index();
    public static final int R5UOMS = AttributesEnum.R5uoms.index();
    public static final int TAXLOV = AttributesEnum.TaxLOV.index();
    public static final int PARTSLOOKUP = AttributesEnum.PartsLookup.index();
    public static final int COMPANIESLOOKUP = AttributesEnum.CompaniesLookup.index();

    /**
     * This is the default constructor (do not remove).
     */
    public SuppliersRowImpl() {
    }

    /**
     * Gets R5catalogue entity object.
     * @return the R5catalogue
     */
    public ExtendedEntityImpl getR5catalogue() {
        return (ExtendedEntityImpl)getEntity(ENTITY_R5CATALOGUE);
    }

    /**
     * Gets the attribute value for CAT_CURR using the alias name CatCurr.
     * @return the CAT_CURR
     */
    public String getCatCurr() {
        return (String) getAttributeInternal(CATCURR);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_CURR using the alias name CatCurr.
     * @param value value to set the CAT_CURR
     */
    public void setCatCurr(String value) {
        setAttributeInternal(CATCURR, value);
    }

    /**
     * Gets the attribute value for CAT_DATE using the alias name CatDate.
     * @return the CAT_DATE
     */
    public Timestamp getCatDate() {
        return (Timestamp) getAttributeInternal(CATDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_DATE using the alias name CatDate.
     * @param value value to set the CAT_DATE
     */
    public void setCatDate(Timestamp value) {
        setAttributeInternal(CATDATE, value);
    }

    /**
     * Gets the attribute value for CAT_EXCH using the alias name CatExch.
     * @return the CAT_EXCH
     */
    public Double getCatExch() {
        return (Double)getAttributeInternal(CATEXCH);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_EXCH using the alias name CatExch.
     * @param value value to set the CAT_EXCH
     */
    public void setCatExch(Double value) {
        setAttributeInternal(CATEXCH, value);
    }

    /**
     * Gets the attribute value for CAT_GROSS using the alias name CatGross.
     * @return the CAT_GROSS
     */
    public Double getCatGross() {
        return (Double)getAttributeInternal(CATGROSS);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_GROSS using the alias name CatGross.
     * @param value value to set the CAT_GROSS
     */
    public void setCatGross(Double value) {
        setAttributeInternal(CATGROSS, value);
    }

    /**
     * Gets the attribute value for CAT_LEADTIME using the alias name CatLeadtime.
     * @return the CAT_LEADTIME
     */
    public Double getCatLeadtime() {
        return (Double)getAttributeInternal(CATLEADTIME);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_LEADTIME using the alias name CatLeadtime.
     * @param value value to set the CAT_LEADTIME
     */
    public void setCatLeadtime(Double value) {
        setAttributeInternal(CATLEADTIME, value);
    }

    /**
     * Gets the attribute value for CAT_MULTIPLY using the alias name CatMultiply.
     * @return the CAT_MULTIPLY
     */
    public Double getCatMultiply() {
        return (Double)getAttributeInternal(CATMULTIPLY);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_MULTIPLY using the alias name CatMultiply.
     * @param value value to set the CAT_MULTIPLY
     */
    public void setCatMultiply(Double value) {
        setAttributeInternal(CATMULTIPLY, value);
    }

    /**
     * Gets the attribute value for CAT_PART using the alias name CatPart.
     * @return the CAT_PART
     */
    public String getCatPart() {
        return (String) getAttributeInternal(CATPART);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_PART using the alias name CatPart.
     * @param value value to set the CAT_PART
     */
    public void setCatPart(String value) {
        setAttributeInternal(CATPART, value);
    }

    /**
     * Gets the attribute value for CAT_PART_ORG using the alias name CatPartOrg.
     * @return the CAT_PART_ORG
     */
    public String getCatPartOrg() {
        return (String) getAttributeInternal(CATPARTORG);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_PART_ORG using the alias name CatPartOrg.
     * @param value value to set the CAT_PART_ORG
     */
    public void setCatPartOrg(String value) {
        setAttributeInternal(CATPARTORG, value);
    }

    /**
     * Gets the attribute value for CAT_PURPRICE using the alias name CatPurprice.
     * @return the CAT_PURPRICE
     */
    public Double getCatPurprice() {
        return (Double)getAttributeInternal(CATPURPRICE);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_PURPRICE using the alias name CatPurprice.
     * @param value value to set the CAT_PURPRICE
     */
    public void setCatPurprice(Double value) {
        setAttributeInternal(CATPURPRICE, value);
    }

    /**
     * Gets the attribute value for CAT_PURUOM using the alias name CatPuruom.
     * @return the CAT_PURUOM
     */
    public String getCatPuruom() {
        return (String) getAttributeInternal(CATPURUOM);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_PURUOM using the alias name CatPuruom.
     * @param value value to set the CAT_PURUOM
     */
    public void setCatPuruom(String value) {
        setAttributeInternal(CATPURUOM, value);
    }

    /**
     * Gets the attribute value for CAT_SUPPLIER using the alias name CatSupplier.
     * @return the CAT_SUPPLIER
     */
    public String getCatSupplier() {
        return (String) getAttributeInternal(CATSUPPLIER);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_SUPPLIER using the alias name CatSupplier.
     * @param value value to set the CAT_SUPPLIER
     */
    public void setCatSupplier(String value) {
        setAttributeInternal(CATSUPPLIER, value);
    }

    /**
     * Gets the attribute value for CAT_SUPPLIER_ORG using the alias name CatSupplierOrg.
     * @return the CAT_SUPPLIER_ORG
     */
    public String getCatSupplierOrg() {
        return (String) getAttributeInternal(CATSUPPLIERORG);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_SUPPLIER_ORG using the alias name CatSupplierOrg.
     * @param value value to set the CAT_SUPPLIER_ORG
     */
    public void setCatSupplierOrg(String value) {
        setAttributeInternal(CATSUPPLIERORG, value);
    }

    /**
     * Gets the attribute value for CAT_TAX using the alias name CatTax.
     * @return the CAT_TAX
     */
    public String getCatTax() {
        return (String) getAttributeInternal(CATTAX);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_TAX using the alias name CatTax.
     * @param value value to set the CAT_TAX
     */
    public void setCatTax(String value) {
        setAttributeInternal(CATTAX, value);
    }

    /**
     * Gets the attribute value for CAT_DESC using the alias name CatDesc.
     * @return the CAT_DESC
     */
    public String getCatDesc() {
        return (String) getAttributeInternal(CATDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for CAT_DESC using the alias name CatDesc.
     * @param value value to set the CAT_DESC
     */
    public void setCatDesc(String value) {
        setAttributeInternal(CATDESC, value);
    }

    /**
     * Gets the attribute value for the calculated attribute tSupplierName.
     * @return the tSupplierName
     */
    public String gettSupplierName() {
        return (String) getAttributeInternal(TSUPPLIERNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute tSupplierName.
     * @param value value to set the  tSupplierName
     */
    public void settSupplierName(String value) {
        setAttributeInternal(TSUPPLIERNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute tSupplierIsPref.
     * @return the tSupplierIsPref
     */
    public Boolean gettSupplierIsPref() {
        return (Boolean) getAttributeInternal(TSUPPLIERISPREF);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute tSupplierIsPref.
     * @param value value to set the  tSupplierIsPref
     */
    public void settSupplierIsPref(Boolean value) {
        setAttributeInternal(TSUPPLIERISPREF, value);
    }

    /**
     * Gets the attribute value for the calculated attribute tPartDesc.
     * @return the tPartDesc
     */
    public String gettPartDesc() {
        return (String) getAttributeInternal(TPARTDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute tPartDesc.
     * @param value value to set the  tPartDesc
     */
    public void settPartDesc(String value) {
        setAttributeInternal(TPARTDESC, value);
    }

    /**
     * Gets the associated <code>Row</code> using master-detail link Companies.
     */
    public Row getCompanies() {
        return (Row)getAttributeInternal(COMPANIES);
    }

    /**
     * Sets the master-detail link Companies between this object and <code>value</code>.
     */
    public void setCompanies(Row value) {
        setAttributeInternal(COMPANIES, value);
    }

    /**
     * Gets the associated <code>Row</code> using master-detail link PartsMaintView.
     */
    public Row getPartsMaintView() {
        return (Row)getAttributeInternal(PARTSMAINTVIEW);
    }

    /**
     * Sets the master-detail link PartsMaintView between this object and <code>value</code>.
     */
    public void setPartsMaintView(Row value) {
        setAttributeInternal(PARTSMAINTVIEW, value);
    }


    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MrltPorderlinesvwView.
     */
    public RowIterator getMrltPorderlinesvwView() {
        return (RowIterator)getAttributeInternal(MRLTPORDERLINESVWVIEW);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link POOrderlineServices.
     */
    public RowIterator getPOOrderlineServices() {
        return (RowIterator)getAttributeInternal(POORDERLINESERVICES);
    }

    /**
     * Gets the view accessor <code>RowSet</code> R5uoms.
     */
    public RowSet getR5uoms() {
        return (RowSet)getAttributeInternal(R5UOMS);
    }

    /**
     * Gets the view accessor <code>RowSet</code> TaxLOV.
     */
    public RowSet getTaxLOV() {
        return (RowSet)getAttributeInternal(TAXLOV);
    }

    /**
     * Gets the view accessor <code>RowSet</code> PartsLookup.
     */
    public RowSet getPartsLookup() {
        return (RowSet)getAttributeInternal(PARTSLOOKUP);
    }

    /**
     * Gets the view accessor <code>RowSet</code> CompaniesLookup.
     */
    public RowSet getCompaniesLookup() {
        return (RowSet)getAttributeInternal(COMPANIESLOOKUP);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}
