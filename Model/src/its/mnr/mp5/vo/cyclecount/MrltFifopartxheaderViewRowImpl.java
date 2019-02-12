package its.mnr.mp5.vo.cyclecount;

import its.mnr.mp5.baseclass.ExtendedEntityImpl;
import its.mnr.mp5.baseclass.ExtendedViewRowImpl;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jul 29 09:35:04 PDT 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltFifopartxheaderViewRowImpl extends ExtendedViewRowImpl {


    public static final int ENTITY_MRLTFIFOPARTXHEADER = 0;

    public void setNewRowState(byte b) {
        if (b != Row.STATUS_INITIALIZED || getNewRowState() != Row.STATUS_NEW) {
            super.setNewRowState(b);
        }
    }
    
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Fpxhid {
            public Object get(MrltFifopartxheaderViewRowImpl obj) {
                return obj.getFpxhid();
            }

            public void put(MrltFifopartxheaderViewRowImpl obj, Object value) {
                obj.setFpxhid((Integer) value);
            }
        }
        ,
        Created {
            public Object get(MrltFifopartxheaderViewRowImpl obj) {
                return obj.getCreated();
            }

            public void put(MrltFifopartxheaderViewRowImpl obj, Object value) {
                obj.setCreated((Timestamp) value);
            }
        }
        ,
        MrltFifopartxView {
            public Object get(MrltFifopartxheaderViewRowImpl obj) {
                return obj.getMrltFifopartxView();
            }

            public void put(MrltFifopartxheaderViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MrltFifoheaderView {
            public Object get(MrltFifopartxheaderViewRowImpl obj) {
                return obj.getMrltFifoheaderView();
            }

            public void put(MrltFifopartxheaderViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MrltFifoPartx_partView {
            public Object get(MrltFifopartxheaderViewRowImpl obj) {
                return obj.getMrltFifoPartx_partView();
            }

            public void put(MrltFifopartxheaderViewRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        static AttributesEnum[] vals = null;
        ;
        private static int firstIndex = 0;

        public abstract Object get(MrltFifopartxheaderViewRowImpl object);

        public abstract void put(MrltFifopartxheaderViewRowImpl object, Object value);

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

    public static final int FPXHID = AttributesEnum.Fpxhid.index();
    public static final int CREATED = AttributesEnum.Created.index();
    public static final int MRLTFIFOPARTXVIEW = AttributesEnum.MrltFifopartxView.index();
    public static final int MRLTFIFOHEADERVIEW = AttributesEnum.MrltFifoheaderView.index();
    public static final int MRLTFIFOPARTX_PARTVIEW = AttributesEnum.MrltFifoPartx_partView.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MrltFifopartxheaderViewRowImpl() {
    }

    /**
     * Gets MrltFifopartxheader entity object.
     * @return the MrltFifopartxheader
     */
    public ExtendedEntityImpl getMrltFifopartxheader() {
        return (ExtendedEntityImpl)getEntity(ENTITY_MRLTFIFOPARTXHEADER);
    }

    /**
     * Gets the attribute value for FPXHID using the alias name Fpxhid.
     * @return the FPXHID
     */
    public Integer getFpxhid() {
        return (Integer) getAttributeInternal(FPXHID);
    }

    /**
     * Sets <code>value</code> as attribute value for FPXHID using the alias name Fpxhid.
     * @param value value to set the FPXHID
     */
    public void setFpxhid(Integer value) {
        setAttributeInternal(FPXHID, value);
    }

    /**
     * Gets the attribute value for CREATED using the alias name Created.
     * @return the CREATED
     */
    public Timestamp getCreated() {
        return (Timestamp) getAttributeInternal(CREATED);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED using the alias name Created.
     * @param value value to set the CREATED
     */
    public void setCreated(Timestamp value) {
        setAttributeInternal(CREATED, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MrltFifopartxView.
     */
    public RowIterator getMrltFifopartxView() {
        return (RowIterator)getAttributeInternal(MRLTFIFOPARTXVIEW);
    }

    /**
     * Gets the associated <code>Row</code> using master-detail link MrltFifoheaderView.
     */
    public Row getMrltFifoheaderView() {
        return (Row)getAttributeInternal(MRLTFIFOHEADERVIEW);
    }

    /**
     * Sets the master-detail link MrltFifoheaderView between this object and <code>value</code>.
     */
    public void setMrltFifoheaderView(Row value) {
        setAttributeInternal(MRLTFIFOHEADERVIEW, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MrltFifoPartx_partView.
     */
    public RowIterator getMrltFifoPartx_partView() {
        return (RowIterator) getAttributeInternal(MRLTFIFOPARTX_PARTVIEW);
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
