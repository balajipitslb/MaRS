package its.mnr.mp5.vo.workorder;

import its.mnr.mp5.baseclass.ExtendedViewObjectImpl;

import oracle.jbo.domain.Timestamp;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 05 09:37:01 PDT 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CurrentAccountRateVersionImpl extends ExtendedViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CurrentAccountRateVersionImpl() {
    }

    public String getCurrentAccountRateId(String acctnum/*, Timestamp tWOStartDt*/) {
        //System.out.println("inside getCurrentAccountRateId");
        //System.out.println("tWOStartDt: " + tWOStartDt);
        String sacctrateid = null;
        //get Activity Account Code

        if (acctnum != null /*&& tWOStartDt != null*/) {
            //System.out.println("acctnum not null");
            //set and execute View Criteria
            ViewCriteria vc = this.getViewCriteria("CurrentAccountRateVersionByAccount");
            vc.resetCriteria();
            setAccountNumBind(acctnum);
            //setWOStartDt(tWOStartDt);
            applyViewCriteria(vc);
            // set range size to 1 since we expect a single record
            setRangeSize(1);
            // execute query to make sure we get latest data!
            executeQuery();
            Row rw = first();
            if (rw != null) {
                sacctrateid = (String)rw.getAttribute("Accountrateid");
                //System.out.println("sacctrateid: " + sacctrateid);
                return sacctrateid;
            }
        }
        return sacctrateid;
    }

    /**
     * Returns the variable value for AccountNumBind.
     * @return variable value for AccountNumBind
     */
    public String getAccountNumBind() {
        return (String)ensureVariableManager().getVariableValue("AccountNumBind");
    }

    /**
     * Sets <code>value</code> for variable AccountNumBind.
     * @param value value to bind as AccountNumBind
     */
    public void setAccountNumBind(String value) {
        ensureVariableManager().setVariableValue("AccountNumBind", value);
    }

    /**
     * Returns the bind variable value for WOStartDt.
     * @return bind variable value for WOStartDt
     */
    public Timestamp getWOStartDt() {
        return (Timestamp)getNamedWhereClauseParam("WOStartDt");
    }

    /**
     * Sets <code>value</code> for bind variable WOStartDt.
     * @param value value to bind as WOStartDt
     */
    public void setWOStartDt(oracle.jbo.domain.Timestamp value) {
        setNamedWhereClauseParam("WOStartDt", value);
    }
}
