package its.mnr.mp5.eo.master;

import its.mnr.mp5.baseclass.ExtendedEntityImpl;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.VariableValueManager;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jul 23 17:12:01 PDT 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltTaskdamageImpl extends ExtendedEntityImpl {
    
    
    /**
     * Validation method for MrltTaskdamage.
     */

    public void brOneRepairDefaultOnly() {
        boolean ret = false;
        RowSet rs = this.getOneRepairDefaultOnly();
        rs.setForwardOnly(true);
        rs.executeQuery();
        Row rw = rs.first();
        if(rw == null){
        //Means this is new record
            ret = true;
        }
        if (rw != null) {
            Number cnt = (Number)rw.getAttribute("Count1");
            //System.out.println("cnt:" + cnt);
            ret = (cnt.equals(1) ? true : false);
        }


        if (!ret) {
            String msg = "'" + getTaskdamdescription() + "' must have ONE default Repair Code";
            throw new JboException(msg);
        }
    }

    protected void validateEntity() {
            // invalidate Task whenever a new Damage is
            // added or when the Default value of an existing
            // Damage is changed to enforce the rule that one default Damage code 
            // exists for the Task
            if ((getEntityState() == STATUS_NEW) ||
                (getEntityState() == STATUS_MODIFIED && ((isAttributeChanged(TASKDAMDFLT))))) {
                getMrltTask().invalidateMe();
            }
            super.validateEntity();
        }
    
    public void beforeCommit(TransactionEvent transactionEvent) {
        brOneRepairDefaultOnly();
        super.beforeCommit(transactionEvent);
    }

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Taskdamageid {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getTaskdamageid();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Taskid {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getTaskid();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setTaskid((String)value);
            }
        }
        ,
        Taskdamdescription {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getTaskdamdescription();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setTaskdamdescription((String)value);
            }
        }
        ,
        Taskdamcode {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getTaskdamcode();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setTaskdamcode((String)value);
            }
        }
        ,
        Taskdamdflt {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getTaskdamdflt();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setTaskdamdflt((String)value);
            }
        }
        ,
        TaskDamCodeId {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getTaskDamCodeId();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setTaskDamCodeId((String)value);
            }
        }
        ,
        MrltTask {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getMrltTask();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setMrltTask((MrltTaskImpl)value);
            }
        }
        ,
        MrltTaskrepair {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getMrltTaskrepair();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MrlrefDamage {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getMrlrefDamage();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setMrlrefDamage((ExtendedEntityImpl)value);
            }
        }
        ,
        TaskRepair {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getTaskRepair();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        OneRepairDefaultOnly {
            public Object get(MrltTaskdamageImpl obj) {
                return obj.getOneRepairDefaultOnly();
            }

            public void put(MrltTaskdamageImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(MrltTaskdamageImpl object);

        public abstract void put(MrltTaskdamageImpl object, Object value);

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


    public static final int TASKDAMAGEID = AttributesEnum.Taskdamageid.index();
    public static final int TASKID = AttributesEnum.Taskid.index();
    public static final int TASKDAMDESCRIPTION = AttributesEnum.Taskdamdescription.index();
    public static final int TASKDAMCODE = AttributesEnum.Taskdamcode.index();
    public static final int TASKDAMDFLT = AttributesEnum.Taskdamdflt.index();
    public static final int TASKDAMCODEID = AttributesEnum.TaskDamCodeId.index();
    public static final int MRLTTASK = AttributesEnum.MrltTask.index();
    public static final int MRLTTASKREPAIR = AttributesEnum.MrltTaskrepair.index();
    public static final int MRLREFDAMAGE = AttributesEnum.MrlrefDamage.index();
    public static final int TASKREPAIR = AttributesEnum.TaskRepair.index();
    public static final int ONEREPAIRDEFAULTONLY = AttributesEnum.OneRepairDefaultOnly.index();

    /**
     * This is the default constructor (do not remove).
     */
    public MrltTaskdamageImpl() {
        
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("its.mnr.mp5.eo.master.MrltTaskdamage");
    }

    /**
     * Gets the attribute value for Taskdamageid, using the alias name Taskdamageid.
     * @return the value of Taskdamageid
     */
    public String getTaskdamageid() {
        return (String)getAttributeInternal(TASKDAMAGEID);
    }

    /**
     * Gets the attribute value for Taskid, using the alias name Taskid.
     * @return the value of Taskid
     */
    public String getTaskid() {
        return (String)getAttributeInternal(TASKID);
    }

    /**
     * Sets <code>value</code> as the attribute value for Taskid.
     * @param value value to set the Taskid
     */
    public void setTaskid(String value) {
        setAttributeInternal(TASKID, value);
    }

    /**
     * Gets the attribute value for Taskdamdescription, using the alias name Taskdamdescription.
     * @return the value of Taskdamdescription
     */
    public String getTaskdamdescription() {
        return (String)getAttributeInternal(TASKDAMDESCRIPTION);
    }

    /**
     * Sets <code>value</code> as the attribute value for Taskdamdescription.
     * @param value value to set the Taskdamdescription
     */
    public void setTaskdamdescription(String value) {
        setAttributeInternal(TASKDAMDESCRIPTION, value);
    }

    /**
     * Gets the attribute value for Taskdamcode, using the alias name Taskdamcode.
     * @return the value of Taskdamcode
     */
    public String getTaskdamcode() {
        return (String)getAttributeInternal(TASKDAMCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Taskdamcode.
     * @param value value to set the Taskdamcode
     */
    public void setTaskdamcode(String value) {
        setAttributeInternal(TASKDAMCODE, value);
    }

    /**
     * Gets the attribute value for Taskdamdflt, using the alias name Taskdamdflt.
     * @return the value of Taskdamdflt
     */
    public String getTaskdamdflt() {
        return (String)getAttributeInternal(TASKDAMDFLT);
    }

    /**
     * Sets <code>value</code> as the attribute value for Taskdamdflt.
     * @param value value to set the Taskdamdflt
     */
    public void setTaskdamdflt(String value) {
        setAttributeInternal(TASKDAMDFLT, value);
    }

    /**
     * Gets the attribute value for TaskDamCodeId, using the alias name TaskDamCodeId.
     * @return the value of TaskDamCodeId
     */
    public String getTaskDamCodeId() {
        return (String)getAttributeInternal(TASKDAMCODEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for TaskDamCodeId.
     * @param value value to set the TaskDamCodeId
     */
    public void setTaskDamCodeId(String value) {
        setAttributeInternal(TASKDAMCODEID, value);
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
     * @return the associated entity MrltTaskImpl.
     */
    public MrltTaskImpl getMrltTask() {
        return (MrltTaskImpl)getAttributeInternal(MRLTTASK);
    }

    /**
     * Sets <code>value</code> as the associated entity MrltTaskImpl.
     */
    public void setMrltTask(MrltTaskImpl value) {
        setAttributeInternal(MRLTTASK, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getMrltTaskrepair() {
        return (RowIterator)getAttributeInternal(MRLTTASKREPAIR);
    }

    /**
     * @return the associated entity its.mnr.mp5.baseclass.ExtendedEntityImpl.
     */
    public ExtendedEntityImpl getMrlrefDamage() {
        return (ExtendedEntityImpl)getAttributeInternal(MRLREFDAMAGE);
    }

    /**
     * Sets <code>value</code> as the associated entity its.mnr.mp5.baseclass.ExtendedEntityImpl.
     */
    public void setMrlrefDamage(ExtendedEntityImpl value) {
        setAttributeInternal(MRLREFDAMAGE, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> TaskRepair.
     */
    public RowSet getTaskRepair() {
        return (RowSet)getAttributeInternal(TASKREPAIR);
    }

    /**
     * Gets the view accessor <code>RowSet</code> OneRepairDefaultOnly.
     */
    public RowSet getOneRepairDefaultOnly() {
        return (RowSet)getAttributeInternal(ONEREPAIRDEFAULTONLY);
    }

    /**
     * @param taskdamageid key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(String taskdamageid) {
        return new Key(new Object[]{taskdamageid});
    }


}
