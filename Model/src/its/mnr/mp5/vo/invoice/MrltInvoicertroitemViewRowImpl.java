package its.mnr.mp5.vo.invoice;

import its.mnr.mp5.baseclass.ExtendedEntityImpl;
import its.mnr.mp5.baseclass.ExtendedViewRowImpl;

import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 24 14:20:33 PDT 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltInvoicertroitemViewRowImpl extends ExtendedViewRowImpl {


    public static final int ENTITY_MRLTINVOICERTROITEM = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Invoicertroitemid {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getInvoicertroitemid();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setInvoicertroitemid((Number)value);
            }
        }
        ,
        Invoiceheaderid {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getInvoiceheaderid();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setInvoiceheaderid((Number)value);
            }
        }
        ,
        Wo {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getWo();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setWo((String)value);
            }
        }
        ,
        Credit {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getCredit();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setCredit((Double)value);
            }
        }
        ,
        Act {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getAct();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setAct((Number)value);
            }
        }
        ,
        Compcd {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getCompcd();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setCompcd((String)value);
            }
        }
        ,
        Damcd {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getDamcd();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setDamcd((String)value);
            }
        }
        ,
        Damdesc {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getDamdesc();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setDamdesc((String)value);
            }
        }
        ,
        Equipcharge {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getEquipcharge();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setEquipcharge((String)value);
            }
        }
        ,
        Invoice {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getInvoice();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setInvoice((Number)value);
            }
        }
        ,
        Lbrhrs {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getLbrhrs();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setLbrhrs((Double)value);
            }
        }
        ,
        Loccd {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getLoccd();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setLoccd((String)value);
            }
        }
        ,
        Locdesc {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getLocdesc();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setLocdesc((String)value);
            }
        }
        ,
        Newaccountratetaskid {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getNewaccountratetaskid();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setNewaccountratetaskid((String)value);
            }
        }
        ,
        Newrate {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getNewrate();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setNewrate((Double)value);
            }
        }
        ,
        Newratetype {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getNewratetype();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setNewratetype((String)value);
            }
        }
        ,
        Newver {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getNewver();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setNewver((String)value);
            }
        }
        ,
        Oldaccountratetaskid {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getOldaccountratetaskid();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setOldaccountratetaskid((String)value);
            }
        }
        ,
        Oldrate {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getOldrate();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setOldrate((Double)value);
            }
        }
        ,
        Oldratetype {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getOldratetype();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setOldratetype((String)value);
            }
        }
        ,
        Oldver {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getOldver();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setOldver((String)value);
            }
        }
        ,
        Repcd {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getRepcd();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setRepcd((String)value);
            }
        }
        ,
        Reppdesc {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getReppdesc();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setReppdesc((String)value);
            }
        }
        ,
        Shift {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getShift();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setShift((String)value);
            }
        }
        ,
        Task {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getTask();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setTask((String)value);
            }
        }
        ,
        CreditDebit {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getCreditDebit();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setCreditDebit((Double)value);
            }
        }
        ,
        Hdrcode {
            public Object get(MrltInvoicertroitemViewRowImpl obj) {
                return obj.getHdrcode();
            }

            public void put(MrltInvoicertroitemViewRowImpl obj, Object value) {
                obj.setHdrcode((String)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MrltInvoicertroitemViewRowImpl object);

        public abstract void put(MrltInvoicertroitemViewRowImpl object, Object value);

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


    public static final int INVOICERTROITEMID = AttributesEnum.Invoicertroitemid.index();
    public static final int INVOICEHEADERID = AttributesEnum.Invoiceheaderid.index();
    public static final int WO = AttributesEnum.Wo.index();
    public static final int CREDIT = AttributesEnum.Credit.index();
    public static final int ACT = AttributesEnum.Act.index();
    public static final int COMPCD = AttributesEnum.Compcd.index();
    public static final int DAMCD = AttributesEnum.Damcd.index();
    public static final int DAMDESC = AttributesEnum.Damdesc.index();
    public static final int EQUIPCHARGE = AttributesEnum.Equipcharge.index();
    public static final int INVOICE = AttributesEnum.Invoice.index();
    public static final int LBRHRS = AttributesEnum.Lbrhrs.index();
    public static final int LOCCD = AttributesEnum.Loccd.index();
    public static final int LOCDESC = AttributesEnum.Locdesc.index();
    public static final int NEWACCOUNTRATETASKID = AttributesEnum.Newaccountratetaskid.index();
    public static final int NEWRATE = AttributesEnum.Newrate.index();
    public static final int NEWRATETYPE = AttributesEnum.Newratetype.index();
    public static final int NEWVER = AttributesEnum.Newver.index();
    public static final int OLDACCOUNTRATETASKID = AttributesEnum.Oldaccountratetaskid.index();
    public static final int OLDRATE = AttributesEnum.Oldrate.index();
    public static final int OLDRATETYPE = AttributesEnum.Oldratetype.index();
    public static final int OLDVER = AttributesEnum.Oldver.index();
    public static final int REPCD = AttributesEnum.Repcd.index();
    public static final int REPPDESC = AttributesEnum.Reppdesc.index();
    public static final int SHIFT = AttributesEnum.Shift.index();
    public static final int TASK = AttributesEnum.Task.index();
    public static final int CREDITDEBIT = AttributesEnum.CreditDebit.index();
    public static final int HDRCODE = AttributesEnum.Hdrcode.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MrltInvoicertroitemViewRowImpl() {
    }

    /**
     * Gets MrltInvoicertroitem entity object.
     * @return the MrltInvoicertroitem
     */
    public ExtendedEntityImpl getMrltInvoicertroitem() {
        return (ExtendedEntityImpl)getEntity(ENTITY_MRLTINVOICERTROITEM);
    }

    /**
     * Gets the attribute value for INVOICERTROITEMID using the alias name Invoicertroitemid.
     * @return the INVOICERTROITEMID
     */
    public Number getInvoicertroitemid() {
        return (Number) getAttributeInternal(INVOICERTROITEMID);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICERTROITEMID using the alias name Invoicertroitemid.
     * @param value value to set the INVOICERTROITEMID
     */
    public void setInvoicertroitemid(Number value) {
        setAttributeInternal(INVOICERTROITEMID, value);
    }

    /**
     * Gets the attribute value for INVOICEHEADERID using the alias name Invoiceheaderid.
     * @return the INVOICEHEADERID
     */
    public Number getInvoiceheaderid() {
        return (Number) getAttributeInternal(INVOICEHEADERID);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICEHEADERID using the alias name Invoiceheaderid.
     * @param value value to set the INVOICEHEADERID
     */
    public void setInvoiceheaderid(Number value) {
        setAttributeInternal(INVOICEHEADERID, value);
    }

    /**
     * Gets the attribute value for WO using the alias name Wo.
     * @return the WO
     */
    public String getWo() {
        return (String) getAttributeInternal(WO);
    }

    /**
     * Sets <code>value</code> as attribute value for WO using the alias name Wo.
     * @param value value to set the WO
     */
    public void setWo(String value) {
        setAttributeInternal(WO, value);
    }

    /**
     * Gets the attribute value for CREDIT using the alias name Credit.
     * @return the CREDIT
     */
    public Double getCredit() {
        return (Double) getAttributeInternal(CREDIT);
    }

    /**
     * Sets <code>value</code> as attribute value for CREDIT using the alias name Credit.
     * @param value value to set the CREDIT
     */
    public void setCredit(Double value) {
        setAttributeInternal(CREDIT, value);
    }

    /**
     * Gets the attribute value for ACT using the alias name Act.
     * @return the ACT
     */
    public Number getAct() {
        return (Number) getAttributeInternal(ACT);
    }

    /**
     * Sets <code>value</code> as attribute value for ACT using the alias name Act.
     * @param value value to set the ACT
     */
    public void setAct(Number value) {
        setAttributeInternal(ACT, value);
    }

    /**
     * Gets the attribute value for COMPCD using the alias name Compcd.
     * @return the COMPCD
     */
    public String getCompcd() {
        return (String) getAttributeInternal(COMPCD);
    }

    /**
     * Sets <code>value</code> as attribute value for COMPCD using the alias name Compcd.
     * @param value value to set the COMPCD
     */
    public void setCompcd(String value) {
        setAttributeInternal(COMPCD, value);
    }

    /**
     * Gets the attribute value for DAMCD using the alias name Damcd.
     * @return the DAMCD
     */
    public String getDamcd() {
        return (String) getAttributeInternal(DAMCD);
    }

    /**
     * Sets <code>value</code> as attribute value for DAMCD using the alias name Damcd.
     * @param value value to set the DAMCD
     */
    public void setDamcd(String value) {
        setAttributeInternal(DAMCD, value);
    }

    /**
     * Gets the attribute value for DAMDESC using the alias name Damdesc.
     * @return the DAMDESC
     */
    public String getDamdesc() {
        return (String) getAttributeInternal(DAMDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for DAMDESC using the alias name Damdesc.
     * @param value value to set the DAMDESC
     */
    public void setDamdesc(String value) {
        setAttributeInternal(DAMDESC, value);
    }

    /**
     * Gets the attribute value for EQUIPCHARGE using the alias name Equipcharge.
     * @return the EQUIPCHARGE
     */
    public String getEquipcharge() {
        return (String) getAttributeInternal(EQUIPCHARGE);
    }

    /**
     * Sets <code>value</code> as attribute value for EQUIPCHARGE using the alias name Equipcharge.
     * @param value value to set the EQUIPCHARGE
     */
    public void setEquipcharge(String value) {
        setAttributeInternal(EQUIPCHARGE, value);
    }

    /**
     * Gets the attribute value for INVOICE using the alias name Invoice.
     * @return the INVOICE
     */
    public Number getInvoice() {
        return (Number) getAttributeInternal(INVOICE);
    }

    /**
     * Sets <code>value</code> as attribute value for INVOICE using the alias name Invoice.
     * @param value value to set the INVOICE
     */
    public void setInvoice(Number value) {
        setAttributeInternal(INVOICE, value);
    }

    /**
     * Gets the attribute value for LBRHRS using the alias name Lbrhrs.
     * @return the LBRHRS
     */
    public Double getLbrhrs() {
        return (Double) getAttributeInternal(LBRHRS);
    }

    /**
     * Sets <code>value</code> as attribute value for LBRHRS using the alias name Lbrhrs.
     * @param value value to set the LBRHRS
     */
    public void setLbrhrs(Double value) {
        setAttributeInternal(LBRHRS, value);
    }

    /**
     * Gets the attribute value for LOCCD using the alias name Loccd.
     * @return the LOCCD
     */
    public String getLoccd() {
        return (String) getAttributeInternal(LOCCD);
    }

    /**
     * Sets <code>value</code> as attribute value for LOCCD using the alias name Loccd.
     * @param value value to set the LOCCD
     */
    public void setLoccd(String value) {
        setAttributeInternal(LOCCD, value);
    }

    /**
     * Gets the attribute value for LOCDESC using the alias name Locdesc.
     * @return the LOCDESC
     */
    public String getLocdesc() {
        return (String) getAttributeInternal(LOCDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for LOCDESC using the alias name Locdesc.
     * @param value value to set the LOCDESC
     */
    public void setLocdesc(String value) {
        setAttributeInternal(LOCDESC, value);
    }

    /**
     * Gets the attribute value for NEWACCOUNTRATETASKID using the alias name Newaccountratetaskid.
     * @return the NEWACCOUNTRATETASKID
     */
    public String getNewaccountratetaskid() {
        return (String) getAttributeInternal(NEWACCOUNTRATETASKID);
    }

    /**
     * Sets <code>value</code> as attribute value for NEWACCOUNTRATETASKID using the alias name Newaccountratetaskid.
     * @param value value to set the NEWACCOUNTRATETASKID
     */
    public void setNewaccountratetaskid(String value) {
        setAttributeInternal(NEWACCOUNTRATETASKID, value);
    }

    /**
     * Gets the attribute value for NEWRATE using the alias name Newrate.
     * @return the NEWRATE
     */
    public Double getNewrate() {
        return (Double) getAttributeInternal(NEWRATE);
    }

    /**
     * Sets <code>value</code> as attribute value for NEWRATE using the alias name Newrate.
     * @param value value to set the NEWRATE
     */
    public void setNewrate(Double value) {
        setAttributeInternal(NEWRATE, value);
    }

    /**
     * Gets the attribute value for NEWRATETYPE using the alias name Newratetype.
     * @return the NEWRATETYPE
     */
    public String getNewratetype() {
        return (String) getAttributeInternal(NEWRATETYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for NEWRATETYPE using the alias name Newratetype.
     * @param value value to set the NEWRATETYPE
     */
    public void setNewratetype(String value) {
        setAttributeInternal(NEWRATETYPE, value);
    }

    /**
     * Gets the attribute value for NEWVER using the alias name Newver.
     * @return the NEWVER
     */
    public String getNewver() {
        return (String) getAttributeInternal(NEWVER);
    }

    /**
     * Sets <code>value</code> as attribute value for NEWVER using the alias name Newver.
     * @param value value to set the NEWVER
     */
    public void setNewver(String value) {
        setAttributeInternal(NEWVER, value);
    }

    /**
     * Gets the attribute value for OLDACCOUNTRATETASKID using the alias name Oldaccountratetaskid.
     * @return the OLDACCOUNTRATETASKID
     */
    public String getOldaccountratetaskid() {
        return (String) getAttributeInternal(OLDACCOUNTRATETASKID);
    }

    /**
     * Sets <code>value</code> as attribute value for OLDACCOUNTRATETASKID using the alias name Oldaccountratetaskid.
     * @param value value to set the OLDACCOUNTRATETASKID
     */
    public void setOldaccountratetaskid(String value) {
        setAttributeInternal(OLDACCOUNTRATETASKID, value);
    }

    /**
     * Gets the attribute value for OLDRATE using the alias name Oldrate.
     * @return the OLDRATE
     */
    public Double getOldrate() {
        return (Double) getAttributeInternal(OLDRATE);
    }

    /**
     * Sets <code>value</code> as attribute value for OLDRATE using the alias name Oldrate.
     * @param value value to set the OLDRATE
     */
    public void setOldrate(Double value) {
        setAttributeInternal(OLDRATE, value);
    }

    /**
     * Gets the attribute value for OLDRATETYPE using the alias name Oldratetype.
     * @return the OLDRATETYPE
     */
    public String getOldratetype() {
        return (String) getAttributeInternal(OLDRATETYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for OLDRATETYPE using the alias name Oldratetype.
     * @param value value to set the OLDRATETYPE
     */
    public void setOldratetype(String value) {
        setAttributeInternal(OLDRATETYPE, value);
    }

    /**
     * Gets the attribute value for OLDVER using the alias name Oldver.
     * @return the OLDVER
     */
    public String getOldver() {
        return (String) getAttributeInternal(OLDVER);
    }

    /**
     * Sets <code>value</code> as attribute value for OLDVER using the alias name Oldver.
     * @param value value to set the OLDVER
     */
    public void setOldver(String value) {
        setAttributeInternal(OLDVER, value);
    }

    /**
     * Gets the attribute value for REPCD using the alias name Repcd.
     * @return the REPCD
     */
    public String getRepcd() {
        return (String) getAttributeInternal(REPCD);
    }

    /**
     * Sets <code>value</code> as attribute value for REPCD using the alias name Repcd.
     * @param value value to set the REPCD
     */
    public void setRepcd(String value) {
        setAttributeInternal(REPCD, value);
    }

    /**
     * Gets the attribute value for REPPDESC using the alias name Reppdesc.
     * @return the REPPDESC
     */
    public String getReppdesc() {
        return (String) getAttributeInternal(REPPDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for REPPDESC using the alias name Reppdesc.
     * @param value value to set the REPPDESC
     */
    public void setReppdesc(String value) {
        setAttributeInternal(REPPDESC, value);
    }

    /**
     * Gets the attribute value for SHIFT using the alias name Shift.
     * @return the SHIFT
     */
    public String getShift() {
        return (String) getAttributeInternal(SHIFT);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIFT using the alias name Shift.
     * @param value value to set the SHIFT
     */
    public void setShift(String value) {
        setAttributeInternal(SHIFT, value);
    }

    /**
     * Gets the attribute value for TASK using the alias name Task.
     * @return the TASK
     */
    public String getTask() {
        return (String) getAttributeInternal(TASK);
    }

    /**
     * Sets <code>value</code> as attribute value for TASK using the alias name Task.
     * @param value value to set the TASK
     */
    public void setTask(String value) {
        setAttributeInternal(TASK, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CreditDebit.
     * @return the CreditDebit
     */
    public Double getCreditDebit() {
        return (Double) getAttributeInternal(CREDITDEBIT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CreditDebit.
     * @param value value to set the  CreditDebit
     */
    public void setCreditDebit(Double value) {
        setAttributeInternal(CREDITDEBIT, value);
    }


    /**
     * Gets the attribute value for HDRCODE using the alias name Hdrcode.
     * @return the HDRCODE
     */
    public String getHdrcode() {
        return (String) getAttributeInternal(HDRCODE);
    }

    /**
     * Sets <code>value</code> as attribute value for HDRCODE using the alias name Hdrcode.
     * @param value value to set the HDRCODE
     */
    public void setHdrcode(String value) {
        setAttributeInternal(HDRCODE, value);
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
