package its.mnr.mp5.eo.local;

import its.mnr.mp5.baseclass.ExtendedEntityImpl;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Sep 16 11:03:24 PDT 2021
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltEstimatexmlfeedImpl extends ExtendedEntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        Estid {
            public Object get(MrltEstimatexmlfeedImpl obj) {
                return obj.getEstid();
            }

            public void put(MrltEstimatexmlfeedImpl obj, Object value) {
                obj.setEstid((Number) value);
            }
        }
        ,
        Wonum {
            public Object get(MrltEstimatexmlfeedImpl obj) {
                return obj.getWonum();
            }

            public void put(MrltEstimatexmlfeedImpl obj, Object value) {
                obj.setWonum((String) value);
            }
        }
        ,
        Ver {
            public Object get(MrltEstimatexmlfeedImpl obj) {
                return obj.getVer();
            }

            public void put(MrltEstimatexmlfeedImpl obj, Object value) {
                obj.setVer((String) value);
            }
        }
        ,
        Isprocessed {
            public Object get(MrltEstimatexmlfeedImpl obj) {
                return obj.getIsprocessed();
            }

            public void put(MrltEstimatexmlfeedImpl obj, Object value) {
                obj.setIsprocessed((String) value);
            }
        }
        ,
        Processeddate {
            public Object get(MrltEstimatexmlfeedImpl obj) {
                return obj.getProcesseddate();
            }

            public void put(MrltEstimatexmlfeedImpl obj, Object value) {
                obj.setProcesseddate((Timestamp) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public abstract Object get(MrltEstimatexmlfeedImpl object);

        public abstract void put(MrltEstimatexmlfeedImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }

    public static final int ESTID = AttributesEnum.Estid.index();
    public static final int WONUM = AttributesEnum.Wonum.index();
    public static final int VER = AttributesEnum.Ver.index();
    public static final int ISPROCESSED = AttributesEnum.Isprocessed.index();
    public static final int PROCESSEDDATE = AttributesEnum.Processeddate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MrltEstimatexmlfeedImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("its.mnr.mp5.eo.local.MrltEstimatexmlfeed");
    }

    /**
     * Gets the attribute value for Estid, using the alias name Estid.
     * @return the value of Estid
     */
    public Number getEstid() {
        return (Number) getAttributeInternal(ESTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Estid.
     * @param value value to set the Estid
     */
    public void setEstid(Number value) {
        setAttributeInternal(ESTID, value);
    }

    /**
     * Gets the attribute value for Wonum, using the alias name Wonum.
     * @return the value of Wonum
     */
    public String getWonum() {
        return (String) getAttributeInternal(WONUM);
    }

    /**
     * Sets <code>value</code> as the attribute value for Wonum.
     * @param value value to set the Wonum
     */
    public void setWonum(String value) {
        setAttributeInternal(WONUM, value);
    }

    /**
     * Gets the attribute value for Ver, using the alias name Ver.
     * @return the value of Ver
     */
    public String getVer() {
        return (String) getAttributeInternal(VER);
    }

    /**
     * Sets <code>value</code> as the attribute value for Ver.
     * @param value value to set the Ver
     */
    public void setVer(String value) {
        setAttributeInternal(VER, value);
    }

    /**
     * Gets the attribute value for Isprocessed, using the alias name Isprocessed.
     * @return the value of Isprocessed
     */
    public String getIsprocessed() {
        return (String) getAttributeInternal(ISPROCESSED);
    }

    /**
     * Sets <code>value</code> as the attribute value for Isprocessed.
     * @param value value to set the Isprocessed
     */
    public void setIsprocessed(String value) {
        setAttributeInternal(ISPROCESSED, value);
    }

    /**
     * Gets the attribute value for Processeddate, using the alias name Processeddate.
     * @return the value of Processeddate
     */
    public Timestamp getProcesseddate() {
        return (Timestamp) getAttributeInternal(PROCESSEDDATE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Processeddate.
     * @param value value to set the Processeddate
     */
    public void setProcesseddate(Timestamp value) {
        setAttributeInternal(PROCESSEDDATE, value);
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
     * @param estid key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Number estid) {
        return new Key(new Object[] { estid });
    }

    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
    }

    /**
     * Add entity remove logic in this method.
     */
    public void remove() {
        super.remove();
    }

    /**
     * Add locking logic here.
     */
    public void lock() {
        try {
            super.lock();
        } ////catch (RowInconsistentException e) {
        catch (Exception e) {
            System.out.println("Inside lock() exception: " + e.getMessage());
            checkInconsistentAttrs();
            refresh(REFRESH_WITH_DB_ONLY_IF_UNCHANGED | REFRESH_CONTAINEES);
            //checkInconsistentAttrs();
            //throw (e);
          ////  e.printStackTrace();
            super.lock();
        }
    }

    private void checkInconsistentAttrs() {
        int count = getAttributeCount();
        // Store the current values of the attributes; we're going to munge them during the check
        Object[] currValues = new Object[count];
        boolean attributeChanges[] = new boolean[count];
        for (int i=0; i<count; i++) {
            currValues[i] = getAttribute(i);
            attributeChanges[i] = isAttributeChanged(i);
        }
        // Change all attribute values to match current DB values (good thing we saved them first!)
        refresh(REFRESH_WITH_DB_FORGET_CHANGES);
        // For each attribute...
        for (int i=0; i<count; i++) {
            // compare the current value (from the DB) with the original value queried from DB.
            Object origValue = getPostedAttribute(i);
            Object currDbValue = getAttribute(i);
            // Watch your nulls! Nobody likes NPEs.
            if ((origValue == null && currDbValue != null) ||
                (origValue != null &&
                 (currDbValue == null ||
                  ! origValue.equals(currDbValue))))
              {
                 // If they don't match, tell us about it.
                 System.out.println("Inconsistent attribute " + getAttributeNames()[i]);
                 System.out.println("  Queried from DB = " + origValue);
                 System.out.println("  Now in DB = " + currDbValue);
             }
             // Set this EO instance back to the way it was
             if (attributeChanges[i]) {
                 populateAttributeAsChanged(i, currValues[i]);
             } else {
                 populateAttribute(i, currValues[i]);
             }
        }
    }

    /**
     * Custom DML update/insert/delete logic here.
     * @param operation the operation type
     * @param e the transaction event
     */
    protected void doDML(int operation, TransactionEvent e) {
        
        if (operation == DML_UPDATE ) {
            System.out.println("Inside MrltEstimatexmlfeedImpl get: "+getIsprocessed());
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                String str_date = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Date());
                Date date;
                date = formatter.parse(str_date);
                java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
                System.out.println("Inside MrltEstimatexmlfeedImpl DML_UPDATE: timeStampDate: "+timeStampDate);
                 setProcesseddate(timeStampDate);
            } catch (ParseException f) {
                System.out.println("Inside MrltEstimatexmlfeedImpl DML_UPDATE catch f: "+f.getMessage());
            }
            

            
        }
        
        super.doDML(operation, e);
    }
}
