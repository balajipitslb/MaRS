package its.mnr.mp5.model;

import java.util.ListResourceBundle;

public class ErrMessageBundle extends ListResourceBundle {
    private static final Object[][] sMessageStrings = new String[][] {
    { "MRLREF_COMPONENT_FK1", "Can not Delete. Service Group has associated Component dependencies."},
    { "MRLREF_LOCATION_FK1", "Can not Delete. Component has associated Location dependencies."},
    { "MRLREF_REPAIR_FK1", "Can not Delete. Damage has associated Repair dependencies."},
    { "MRLT_TASKLOCATION_FK1", "Can not Delete. Task has associated Location dependencies."},
    { "MRLT_TASKDAMAGE_FK1", "Can not Delete. Task has associated Damage dependencies."},
    { "MRLT_TASKREPAIR_FK1", "Can not Delete. Damage has associated Repair dependencies."},
    { "MRLT_ACCOUNTMASTER_PK", "Account is a duplicate."},
    { "MRLT_ACCOUNTMASTER_FK1", "Account is not valid - does not have a CostCode."},
    { "MRLT_ACCOUNTRATETASK_FK1", "Account has associated Task Records"},
    
    { "R5NN01_EVT", "Equipment is required."},
    { "R5NN04_EVT", "Work Order Status is required."},   
    { "ITSNN12_EVT", "Work Order Type is required."},
    { "ITSNN13_EVT", "Account Code is required."},
    
    { "ITSNN11_ACT", "Task Location is required."},
    { "ITSNN10_ACT", "Task is required."},
    { "ITSNN12_ACT", "Task Damage is required."},
    { "ITSNN13_ACT", "Task Repair is required."},
    { "ITSNN14_ACT", "Task Location is required."},
    { "ITSNN15_ACT", "Mechanic Id is required."},
    { "ITSNN16_ACT", "Shift is required."},
    { "ITSNN17_ACT", "Labor Hours is required."},
    { "ITSNN18_ACT", "Labor Hours is required."},
    { "ITSNN19_ACT", "Rate Type  is required."},
    { "ITSNN20_ACT", "Shift is required."},
    { "ITSNN21_ACT", "Account Code is required."},
    { "ITSNN22_ACT", "Unit of Measure is required."},
    
    { "R5PRIK_PAR", "Part # already exists."},
    
    { "R5CHK2_STO", "Mininum Level must be less than or equal to Order Level."},
    
    { "MRLT_ASSETDOCUMENTS_FK2", "Can not delete. Assets associated with this document."},
    
    { "MRLT_ASSET_CK1", "Withdraw Date must be greater than Commission Date."},
    
    { "R5EXTCHARGES_FK","Extra charges exist for Order line record."}
                                     
    };
    /**Return String Identifiers and corresponding Messages in a two-dimensional array.
     */
    protected Object[][] getContents() {
        return sMessageStrings;
    }
}
