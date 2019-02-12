package its.mnr.mp5.model;

import java.util.ListResourceBundle;

public class DBOraMessageBundle extends ListResourceBundle {
    private static final Object[][] sMessageStrings = new String[][] {
    { "MP5PRD.MRLT_TASKREPAIR_FK1", "Can not Delete. Damage has associated Repair dependencies." }                                                       
                                                          
                                                          };

    /**Return String Identifiers and corresponding Messages in a two-dimensional array.
     */
    protected Object[][] getContents() {
        return sMessageStrings;
    }
}
