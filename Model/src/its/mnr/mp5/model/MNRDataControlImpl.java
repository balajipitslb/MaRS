package its.mnr.mp5.model;

import its.mnr.mp5.baseclass.ExtendedApplicationModuleImpl;


import its.mnr.mp5.model.common.MNRDataControl;

import its.mnr.mp5.vo.admin.MrlrefMp5profileViewImpl;

import its.mnr.mp5.vo.workorder.WOEventsRowImpl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Hashtable;

import oracle.adf.share.ADFContext;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.Session;
import oracle.jbo.ViewCriteria;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Mar 06 08:34:55 PST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MNRDataControlImpl extends ExtendedApplicationModuleImpl implements MNRDataControl {
    public void setSessionUserData(String key, Object val) {
        Hashtable userData = getDBTransaction().getSession().getUserData();
        if (userData == null) {
            userData = new Hashtable();
        }
        userData.put(key, val);
    }

    public void throwException() {
        throw new JboException("Data Processing Failed");
    }

    public String getSessionUserData(String key) {
        //return (String)this.getSession().getUserData().get(key);
        Hashtable userData = getDBTransaction().getSession().getUserData();
        if (userData == null) {
            userData = new Hashtable();
        }
        return (String) userData.get(key);
    }

    public String getCurrentUser() {
        return ADFContext.getCurrent().getSecurityContext().getUserName();
    }

    public String getUserGroup(String user) {
        return (String) callStoredFunction(VARCHAR2, "mnrpkg.getUserGroup(?)", new Object[] { user });
    }

    public void setUserGroupSessionData() {
        // set authenicated user's group from db
        String user = getCurrentUser();
        setSessionUserData("UserGroup", getUserGroup(user));
        System.out.println("group:" + getSessionUserData("UserGroup"));
    }

    public String[] getCurrentUserRoles() {
        return ADFContext.getCurrent().getSecurityContext().getUserRoles();
    }

    public void printRoles() {
        //System.out.println(getCurrentUser());

        for (String role : getCurrentUserRoles()) {
            //System.out.println("role " + role);

        }
    }

    public String getMP5Profile(String sProfile) {
        // System.out.println("Function getWOJobType");
        String ret = (String) callStoredFunction(VARCHAR2, "mnrpkg.getMP5Profile(?)", new Object[] { sProfile });
        //System.out.println("Function getWOJobType = " + ret);
        return ret;
    }

    public String getWOJobType(String WOType, String RFFlg) {
        // System.out.println("Function getWOJobType");
        String ret = (String) callStoredFunction(VARCHAR2, "mnrpkg.getWOJobType(?,?)", new Object[] { WOType, RFFlg });
        //System.out.println("Function getWOJobType = " + ret);
        return ret;
    }

    public String getDefLocCode(String taskid) {
        //System.out.println("inside setDefLocCode function: " + taskid);

        String ret = (String) callStoredFunction(VARCHAR2, "mnrpkg.getTaskLocationDflt(?)", new Object[] { taskid });
        //System.out.println("Function getTaskLocationDflt = " + ret);
        return ret;
    }

    public String getDefDamCode(String taskid) {
        //System.out.println("inside getDefDamCode function: " + taskid);

        String ret = (String) callStoredFunction(VARCHAR2, "mnrpkg.getTaskDamageDflt(?)", new Object[] { taskid });
        //System.out.println("Function getTaskDamageDflt = " + ret);
        return ret;
    }

    public String getDefRepCode(String taskdamid, String idtype) {
        //System.out.println("inside getDefRepCode function: " + taskdamid + ':' + idtype);

        String ret = (String) callStoredFunction(VARCHAR2, "mnrpkg.getTaskRepairDflt(?,?)", new Object[] {
                                                 taskdamid, idtype });
        //System.out.println("Function getTaskRepairDflt = " + ret);
        return ret;
    }

    public String getAssetDetail(String assetNm) {
        //System.out.println("inside getAssetDetail function: " + assetNm);

        String ret = (String) callStoredFunction(VARCHAR2, "mnrpkg.getAssetDetail(?)", new Object[] { assetNm });
        //System.out.println("Function getAssetDetail = " + ret);
        return ret;
    }

    public String checkWOMatlist(String wonum, String actnum) {
        //System.out.println("inside checkWOMatlist function: " + wonum + ", " + actnum);

        String ret = (String) callStoredFunction(VARCHAR2, "mnrpkg.checkWOMatlist(?,?)", new Object[] {
                                                 wonum, actnum });
        //System.out.println("checkWOMatlist Ret = " + ret);
        return ret;
    }

    public String CreateFlagAssoc(String faitem) {
        String UpdBy=getCurrentUser();
        System.out.println("Inside MNRDataControlImpl calling Function CreateFlagAssoc faitem: "+faitem+" UpdBy: "+UpdBy);

        return (String) callStoredFunction(VARCHAR2, "mnrpkglk6.CreateFlagAssoc(?,?)", new Object[] {
                                            faitem,UpdBy
                                        });
    }

    public String CreateNewAccountRateVersion(String CurrAcctNum, String CopyAcctNum, String InEffDt, Number InBase,
                                              Number InM1, Number InM1o, Number InM2, Number InM2o, Number InM3,
                                              Number InM3o, String UsePrevVersion, String UseOtherAccount) {

        if (UsePrevVersion.equals("0") && !UseOtherAccount.equals("0")) {
            CopyAcctNum = CurrAcctNum;
            //System.out.println("check CopyAcctNum: " + CopyAcctNum);
        }
        /*
        System.out.println("CreateNewAccountRateVersion: " + "CurrAcctNum ->" + CurrAcctNum + ":" + "CopyAcctNum ->" +
                           CopyAcctNum + ":" + "InEffDt ->" + InEffDt + ":" + "InBase ->" + InBase + ":" + "InM1 ->" +
                           InM1 + ":" + "UsePrevVersion ->" + UsePrevVersion + ":" + "UseOtherAccount ->" +
                           UseOtherAccount);*/

        String AccountRateRet =
            AccountRateVerCreate(CurrAcctNum, CopyAcctNum, InEffDt, InBase, InM1, InM1o, InM2, InM2o, InM3, InM3o);

        //System.out.println("AccountRateRet: " + AccountRateRet);

        return AccountRateRet;
    }

    public String AccountRateVerCreate(String CurrAcctNum, String CopyAcctNum, String InEffDt, Number InBase,
                                       Number InM1, Number InM1o, Number InM2, Number InM2o, Number InM3,
                                       Number InM3o) {
        //System.out.println("Function AccountRateVerCreate");
        return (String) callStoredFunction(VARCHAR2, "mnrpkg.AccountRateVerCreate(?,?,?,?,?,?,?,?,?,?)", new Object[] {
                                           CurrAcctNum, CopyAcctNum, InEffDt, InBase, InM1, InM1o, InM2, InM2o, InM3,
                                           InM3o
    });
    }


    public String AccountRateVerCreateString(String CurrAcctNum) {
        return (String) callStoredFunction(VARCHAR2, "mnrpkg.AccountRateVerCreateString(?)", new Object[] {
                                           CurrAcctNum });
    }

    protected void prepareSession(Session session) {
        super.prepareSession(session);
        // set authenicated user's group from db
        String user = getCurrentUser();
        setSessionUserData("UserGroup", getUserGroup(user));
        //System.out.println("user:" + user);
        //System.out.println("group:" + getSessionUserData("UserGroup"));
        setSessionUserData("ShowCycleCount", getMP5Profile("ShowCycleCount"));
        //System.out.println("getShowCycleCount(): " +getMP5Profile("ShowCycleCount"));
    }

    // Some constants
    public static int NUMBER = Types.NUMERIC;
    public static int DATE = Types.DATE;
    public static int VARCHAR2 = Types.VARCHAR;

    protected Object callStoredFunction(int sqlReturnType, String stmt, Object[] bindVars) {
        CallableStatement st = null;
        try {
            // 1. Create a JDBC CallabledStatement
            st = getDBTransaction().createCallableStatement("begin ? := " + stmt + ";end;", 0);
            // 2. Register the first bind variable for the return value
            st.registerOutParameter(1, sqlReturnType);
            if (bindVars != null) {
                // 3. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 4. Set the value of user-supplied bind vars in the stmt
                    st.setObject(z + 2, bindVars[z]);
                }
            }
            // 5. Set the value of user-supplied bind vars in the stmt
            st.executeUpdate();
            // 6. Return the value of the first bind variable
            return st.getObject(1);
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 7. Close the statement
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    protected void callStoredProcedure(String stmt, Object[] bindVars) {
        PreparedStatement st = null;
        try {
            // 1. Create a JDBC PreparedStatement for
            st = getDBTransaction().createPreparedStatement("begin " + stmt + ";end;", 0);
            if (bindVars != null) {
                // 2. Loop over values for the bind variables passed in, if any
                for (int z = 0; z < bindVars.length; z++) {
                    // 3. Set the value of each bind variable in the statement
                    st.setObject(z + 1, bindVars[z]);
                }
            }
            // 4. Execute the statement
            st.executeUpdate();
        } catch (SQLException e) {
            throw new JboException(e);
        } finally {
            if (st != null) {
                try {
                    // 5. Close the statement
                    st.close();
                } catch (SQLException e) {
                }
            }
        }
    }
/*
    public void sendMessage(String toaddress, String subject, String body, String host, Integer port,
                            String fromemail) {
        Util.sendMessage(toaddress, subject, body, host, port,fromemail);
    }

*/
/*
    public String getMP5Profile(String sprofile){
            String ret;
            //set and execute View Criteria
            
            RowSet rs = this.getWODataControlNested().getWOEventsRowImpl().getMrlrefMp5profileView();
            MrlrefMp5profileViewImpl impl = (MrlrefMp5profileViewImpl)rs.getViewObject();
            ViewCriteria vc = impl.getViewCriteria("MP5ProfileByProfile");
            vc.resetCriteria();
            impl.setprofileBind(sprofile);
            impl.applyViewCriteria(vc);
            // set range size to 1 since we expect a single record
            impl.setRangeSize(1);
            // execute query to make sure we get latest data!
            impl.executeQuery();
            Row rw = impl.first();
            if (rw != null) {
                ret = (String)rw.getAttribute("Value");
                rs.closeRowSet();
                return ret;
            }

            return null;
        }
*/
    public void sendMessage(String toaddress, String subject, String body, String host, Integer port,
                            String fromemail) {
     
        callStoredFunction(VARCHAR2, "mnrpkg.sendEmail(?,?,?,?,?)", new Object[] {toaddress,  subject,  body,  host,  port,  fromemail});        
      //  Util.sendMessage(toaddress, subject, body, host, port,fromemail);
    }   
    
    /**
     * This is the default constructor (do not remove).
     */
    public MNRDataControlImpl() {
    }

    /**
     * Container's getter for MasterDataControlNested.
     * @return MasterDataControlNested
     */
    public ApplicationModuleImpl getMasterDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("MasterDataControlNested");
    }

    /**
     * Container's getter for LOVDataControlNested.
     * @return LOVDataControlNested
     */
    public ApplicationModuleImpl getLOVDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("LOVDataControlNested");
    }

    /**
     * Container's getter for BillingRatesDataControlNested.
     * @return BillingRatesDataControlNested
     */
    public ApplicationModuleImpl getBillingRatesDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("BillingRatesDataControlNested");
    }

    /**
     * Container's getter for WODataControlNested.
     * @return WODataControlNested
     */
    public ApplicationModuleImpl getWODataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("WODataControlNested");
    }

    /**
     * Container's getter for INVDataControl1.
     * @return INVDataControl1
     */
    public ApplicationModuleImpl getINVDataControl() {
        return (ApplicationModuleImpl) findApplicationModule("INVDataControl");
    }

    /**
     * Container's getter for KPIDataControlNested.
     * @return KPIDataControlNested
     */
    public ApplicationModuleImpl getKPIDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("KPIDataControlNested");
    }

    /**
     * Container's getter for PartsDataControlNested.
     * @return PartsDataControlNested
     */
    public ApplicationModuleImpl getPartsDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("PartsDataControlNested");
    }

    /**
     * Container's getter for PORecvDataControlNested.
     * @return PORecvDataControlNested
     */
    public ApplicationModuleImpl getPORecvDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("PORecvDataControlNested");
    }


    /**
     * Container's getter for AssetDataControlNested.
     * @return AssetDataControlNested
     */
    public ApplicationModuleImpl getAssetDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("AssetDataControlNested");
    }

    /**
     * Container's getter for CCDataControl1.
     * @return CCDataControl1
     */
    public ApplicationModuleImpl getCCDataControlNested() {
        return (ApplicationModuleImpl) findApplicationModule("CCDataControlNested");
    }
}
