package its.mnr.mp5.lov;

import its.mnr.mp5.baseclass.ExtendedViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jan 16 16:38:04 PST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class TranslinePartTotalImpl extends ExtendedViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public TranslinePartTotalImpl() {
    }

    /**
     * Returns the bind variable value for bPart.
     * @return bind variable value for bPart
     */
    public String getbPart() {
        return (String)getNamedWhereClauseParam("bPart");
    }

    /**
     * Sets <code>value</code> for bind variable bPart.
     * @param value value to bind as bPart
     */
    public void setbPart(String value) {
        setNamedWhereClauseParam("bPart", value);
    }

    /**
     * Returns the bind variable value for bPartOrg.
     * @return bind variable value for bPartOrg
     */
    public String getbPartOrg() {
        return (String)getNamedWhereClauseParam("bPartOrg");
    }

    /**
     * Sets <code>value</code> for bind variable bPartOrg.
     * @param value value to bind as bPartOrg
     */
    public void setbPartOrg(String value) {
        setNamedWhereClauseParam("bPartOrg", value);
    }

    /**
     * Returns the bind variable value for bOrder.
     * @return bind variable value for bOrder
     */
    public String getbOrder() {
        return (String)getNamedWhereClauseParam("bOrder");
    }

    /**
     * Sets <code>value</code> for bind variable bOrder.
     * @param value value to bind as bOrder
     */
    public void setbOrder(String value) {
        setNamedWhereClauseParam("bOrder", value);
    }

    /**
     * Returns the bind variable value for bType.
     * @return bind variable value for bType
     */
    public String getbType() {
        return (String)getNamedWhereClauseParam("bType");
    }

    /**
     * Sets <code>value</code> for bind variable bType.
     * @param value value to bind as bType
     */
    public void setbType(String value) {
        setNamedWhereClauseParam("bType", value);
    }
}
