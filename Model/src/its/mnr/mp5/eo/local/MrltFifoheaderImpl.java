package its.mnr.mp5.eo.local;

import its.mnr.mp5.baseclass.ExtendedEntityImpl;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu May 08 14:41:28 PDT 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltFifoheaderImpl extends ExtendedEntityImpl {

    /**
     * BR: When FIFO Header is created, add create timestamp
     */
    public void brSetCreateDt() {
        final Timestamp cCreateDt = new Timestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        if (getAdjtodate() == null) {
            setAccreated(cCreateDt);
        } else if (getAdjtodate() != null) {
            setAdjcreated(cCreateDt);
        }
        ;
    }

    protected void doDML(int i, TransactionEvent transactionEvent) {
        /**
          * When status = FROZEN, update Part qty & timestamp, header frozen timestamp
          */
        if (i == DML_INSERT || i == DML_UPDATE) {
            //System.out.println("calling brChangeActAccountCode()");
            brSetCreateDt();
        }
        super.doDML(i, transactionEvent);
    }

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Fhid {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getFhid();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setFhid((Integer)value);
            }
        }
        ,
        Listid {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getListid();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setListid((Integer)value);
            }
        }
        ,
        Accreated {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getAccreated();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setAccreated((Timestamp)value);
            }
        }
        ,
        Adjcreated {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getAdjcreated();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setAdjcreated((Timestamp)value);
            }
        }
        ,
        Adjtodate {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getAdjtodate();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setAdjtodate((Timestamp)value);
            }
        }
        ,
        Fpxhid {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getFpxhid();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setFpxhid((Integer)value);
            }
        }
        ,
        MrltFifoadjitem {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getMrltFifoadjitem();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MrltFifoitem {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getMrltFifoitem();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MrltCyclecountheader {
            public Object get(MrltFifoheaderImpl obj) {
                return obj.getMrltCyclecountheader();
            }

            public void put(MrltFifoheaderImpl obj, Object value) {
                obj.setMrltCyclecountheader((MrltCyclecountheaderImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MrltFifoheaderImpl object);

        public abstract void put(MrltFifoheaderImpl object, Object value);

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
    public static final int FHID = AttributesEnum.Fhid.index();
    public static final int LISTID = AttributesEnum.Listid.index();
    public static final int ACCREATED = AttributesEnum.Accreated.index();
    public static final int ADJCREATED = AttributesEnum.Adjcreated.index();
    public static final int ADJTODATE = AttributesEnum.Adjtodate.index();
    public static final int FPXHID = AttributesEnum.Fpxhid.index();
    public static final int MRLTFIFOADJITEM = AttributesEnum.MrltFifoadjitem.index();
    public static final int MRLTFIFOITEM = AttributesEnum.MrltFifoitem.index();
    public static final int MRLTCYCLECOUNTHEADER = AttributesEnum.MrltCyclecountheader.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MrltFifoheaderImpl() {
    }

    /**
     * Gets the attribute value for Fhid, using the alias name Fhid.
     * @return the value of Fhid
     */
    public Integer getFhid() {
        return (Integer)getAttributeInternal(FHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Fhid.
     * @param value value to set the Fhid
     */
    public void setFhid(Integer value) {
        setAttributeInternal(FHID, value);
    }

    /**
     * Gets the attribute value for Listid, using the alias name Listid.
     * @return the value of Listid
     */
    public Integer getListid() {
        return (Integer)getAttributeInternal(LISTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Listid.
     * @param value value to set the Listid
     */
    public void setListid(Integer value) {
        setAttributeInternal(LISTID, value);
    }

    /**
     * Gets the attribute value for Accreated, using the alias name Accreated.
     * @return the value of Accreated
     */
    public Timestamp getAccreated() {
        return (Timestamp)getAttributeInternal(ACCREATED);
    }

    /**
     * Sets <code>value</code> as the attribute value for Accreated.
     * @param value value to set the Accreated
     */
    public void setAccreated(Timestamp value) {
        setAttributeInternal(ACCREATED, value);
    }

    /**
     * Gets the attribute value for Adjcreated, using the alias name Adjcreated.
     * @return the value of Adjcreated
     */
    public Timestamp getAdjcreated() {
        return (Timestamp)getAttributeInternal(ADJCREATED);
    }

    /**
     * Sets <code>value</code> as the attribute value for Adjcreated.
     * @param value value to set the Adjcreated
     */
    public void setAdjcreated(Timestamp value) {
        setAttributeInternal(ADJCREATED, value);
    }

    /**
     * Gets the attribute value for Adjtodate, using the alias name Adjtodate.
     * @return the value of Adjtodate
     */
    public Timestamp getAdjtodate() {
        return (Timestamp)getAttributeInternal(ADJTODATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Adjtodate.
     * @param value value to set the Adjtodate
     */
    public void setAdjtodate(Timestamp value) {
        setAttributeInternal(ADJTODATE, value);
    }

    /**
     * Gets the attribute value for Fpxhid, using the alias name Fpxhid.
     * @return the value of Fpxhid
     */
    public Integer getFpxhid() {
        return (Integer)getAttributeInternal(FPXHID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Fpxhid.
     * @param value value to set the Fpxhid
     */
    public void setFpxhid(Integer value) {
        setAttributeInternal(FPXHID, value);
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

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMrltFifoadjitem() {
        return (RowIterator)getAttributeInternal(MRLTFIFOADJITEM);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMrltFifoitem() {
        return (RowIterator)getAttributeInternal(MRLTFIFOITEM);
    }

    /**
     * @return the associated entity MrltCyclecountheaderImpl.
     */
    public MrltCyclecountheaderImpl getMrltCyclecountheader() {
        return (MrltCyclecountheaderImpl)getAttributeInternal(MRLTCYCLECOUNTHEADER);
    }

    /**
     * Sets <code>value</code> as the associated entity MrltCyclecountheaderImpl.
     */
    public void setMrltCyclecountheader(MrltCyclecountheaderImpl value) {
        setAttributeInternal(MRLTCYCLECOUNTHEADER, value);
    }

    /**
     * @param fhid key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Integer fhid) {
        return new Key(new Object[]{fhid});
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("its.mnr.mp5.eo.local.MrltFifoheader");
    }
}
