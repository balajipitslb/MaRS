package its.mnr.mp5.vo.cyclecount;

import its.mnr.mp5.baseclass.ExtendedViewRowImpl;

import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jul 03 15:19:09 PDT 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltCyclecountpartsMainStoreRowImpl extends ExtendedViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        StlTrans {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlTrans();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlTrans((String)value);
            }
        }
        ,
        StlPart {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlPart();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlPart((String)value);
            }
        }
        ,
        StlBin {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlBin();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlBin((String)value);
            }
        }
        ,
        StlLot {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlLot();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlLot((String)value);
            }
        }
        ,
        StlStore {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlStore();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlStore((String)value);
            }
        }
        ,
        StlExpqty {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlExpqty();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlExpqty((Number)value);
            }
        }
        ,
        StlType {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlType();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlType((String)value);
            }
        }
        ,
        StlPartOrg {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlPartOrg();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlPartOrg((String)value);
            }
        }
        ,
        StlPartclass {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlPartclass();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlPartclass((String)value);
            }
        }
        ,
        StlListid {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getStlListid();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setStlListid((Integer)value);
            }
        }
        ,
        Displayed {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getDisplayed();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setDisplayed((String)value);
            }
        }
        ,
        Include {
            public Object get(MrltCyclecountpartsMainStoreRowImpl obj) {
                return obj.getInclude();
            }

            public void put(MrltCyclecountpartsMainStoreRowImpl obj, Object value) {
                obj.setInclude((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MrltCyclecountpartsMainStoreRowImpl object);

        public abstract void put(MrltCyclecountpartsMainStoreRowImpl object, Object value);

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
    public static final int STLTRANS = AttributesEnum.StlTrans.index();
    public static final int STLPART = AttributesEnum.StlPart.index();
    public static final int STLBIN = AttributesEnum.StlBin.index();
    public static final int STLLOT = AttributesEnum.StlLot.index();
    public static final int STLSTORE = AttributesEnum.StlStore.index();
    public static final int STLEXPQTY = AttributesEnum.StlExpqty.index();
    public static final int STLTYPE = AttributesEnum.StlType.index();
    public static final int STLPARTORG = AttributesEnum.StlPartOrg.index();
    public static final int STLPARTCLASS = AttributesEnum.StlPartclass.index();
    public static final int STLLISTID = AttributesEnum.StlListid.index();
    public static final int DISPLAYED = AttributesEnum.Displayed.index();
    public static final int INCLUDE = AttributesEnum.Include.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MrltCyclecountpartsMainStoreRowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute StlTrans.
     * @return the StlTrans
     */
    public String getStlTrans() {
        return (String) getAttributeInternal(STLTRANS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlTrans.
     * @param value value to set the  StlTrans
     */
    public void setStlTrans(String value) {
        setAttributeInternal(STLTRANS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlPart.
     * @return the StlPart
     */
    public String getStlPart() {
        return (String) getAttributeInternal(STLPART);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlPart.
     * @param value value to set the  StlPart
     */
    public void setStlPart(String value) {
        setAttributeInternal(STLPART, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlBin.
     * @return the StlBin
     */
    public String getStlBin() {
        return (String) getAttributeInternal(STLBIN);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlBin.
     * @param value value to set the  StlBin
     */
    public void setStlBin(String value) {
        setAttributeInternal(STLBIN, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlLot.
     * @return the StlLot
     */
    public String getStlLot() {
        return (String) getAttributeInternal(STLLOT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlLot.
     * @param value value to set the  StlLot
     */
    public void setStlLot(String value) {
        setAttributeInternal(STLLOT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlStore.
     * @return the StlStore
     */
    public String getStlStore() {
        return (String) getAttributeInternal(STLSTORE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlStore.
     * @param value value to set the  StlStore
     */
    public void setStlStore(String value) {
        setAttributeInternal(STLSTORE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlExpqty.
     * @return the StlExpqty
     */
    public Number getStlExpqty() {
        return (Number) getAttributeInternal(STLEXPQTY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlExpqty.
     * @param value value to set the  StlExpqty
     */
    public void setStlExpqty(Number value) {
        setAttributeInternal(STLEXPQTY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlType.
     * @return the StlType
     */
    public String getStlType() {
        return (String) getAttributeInternal(STLTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlType.
     * @param value value to set the  StlType
     */
    public void setStlType(String value) {
        setAttributeInternal(STLTYPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlPartOrg.
     * @return the StlPartOrg
     */
    public String getStlPartOrg() {
        return (String) getAttributeInternal(STLPARTORG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlPartOrg.
     * @param value value to set the  StlPartOrg
     */
    public void setStlPartOrg(String value) {
        setAttributeInternal(STLPARTORG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlPartclass.
     * @return the StlPartclass
     */
    public String getStlPartclass() {
        return (String) getAttributeInternal(STLPARTCLASS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlPartclass.
     * @param value value to set the  StlPartclass
     */
    public void setStlPartclass(String value) {
        setAttributeInternal(STLPARTCLASS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute StlListid.
     * @return the StlListid
     */
    public Integer getStlListid() {
        return (Integer) getAttributeInternal(STLLISTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute StlListid.
     * @param value value to set the  StlListid
     */
    public void setStlListid(Integer value) {
        setAttributeInternal(STLLISTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Displayed.
     * @return the Displayed
     */
    public String getDisplayed() {
        return (String) getAttributeInternal(DISPLAYED);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Displayed.
     * @param value value to set the  Displayed
     */
    public void setDisplayed(String value) {
        setAttributeInternal(DISPLAYED, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Include.
     * @return the Include
     */
    public String getInclude() {
        return (String) getAttributeInternal(INCLUDE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Include.
     * @param value value to set the  Include
     */
    public void setInclude(String value) {
        setAttributeInternal(INCLUDE, value);
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
