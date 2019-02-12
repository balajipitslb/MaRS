package its.mnr.mp5.vo.cyclecount;

import its.mnr.mp5.baseclass.ExtendedViewObjectImpl;

import java.sql.ResultSet;

import oracle.jbo.Row;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Feb 28 16:45:23 PST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltCyclecountpartstoresViewImpl extends ExtendedViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public MrltCyclecountpartstoresViewImpl() {
    }

    /**
     * Returns the variable value for bListid.
     * @return variable value for bListid
     */
    public Integer getbListid() {
        return (Integer)ensureVariableManager().getVariableValue("bListid");
    }

    /**
     * Sets <code>value</code> for variable bListid.
     * @param value value to bind as bListid
     */
    public void setbListid(Integer value) {
        ensureVariableManager().setVariableValue("bListid", value);
    }

    /**
     * executeQueryForCollection - overridden for custom java data source support.
     */
    @Override
    protected void executeQueryForCollection(Object qc, Object[] params, int noUserParams) {
        super.executeQueryForCollection(qc, params, noUserParams);
    }

    /**
     * hasNextForCollection - overridden for custom java data source support.
     */
    @Override
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - overridden for custom java data source support.
     */
    @Override
    protected ViewRowImpl createRowFromResultSet(Object qc, ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - overridden for custom java data source support.
     */
    @Override
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }

    /**
     * getCappedQueryHitCount - overridden for custom java data source support.
     */
    @Override
    public long getCappedQueryHitCount(ViewRowSetImpl viewRowSet, Row[] masterRows, long oldCap, long cap) {
        long value = super.getCappedQueryHitCount(viewRowSet, masterRows, oldCap, cap);
        return value;
    }
}
