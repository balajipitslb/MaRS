package its.mnr.mp5.model;

import its.mnr.mp5.baseclass.ExtendedApplicationModuleImpl;
import its.mnr.mp5.baseclass.ExtendedViewObjectImpl;
import its.mnr.mp5.lov.EstimateExistenceImpl;
import its.mnr.mp5.lov.UcodesImpl;
import its.mnr.mp5.model.common.WODataControl;
import its.mnr.mp5.vo.workorder.CurrentAccountRateVersionImpl;
import its.mnr.mp5.vo.workorder.EstimateImpl;
import its.mnr.mp5.vo.workorder.LaborCostImpl;
import its.mnr.mp5.vo.workorder.MrltFlagassociation_VOImpl;
import its.mnr.mp5.vo.workorder.TransactionsImpl;
import its.mnr.mp5.vo.workorder.WOEventsImpl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import oracle.adf.share.ADFContext;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Feb 07 11:27:20 PST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class WODataControlImpl extends ExtendedApplicationModuleImpl implements WODataControl {
    /**
     * This is the default constructor (do not remove).
     */
    public WODataControlImpl() {
    }

    public void createFlagAssociation(Integer fid, String faitem, String faactive) {
        //System.out.println("Inside createFlagAssociation fid: " + fid + " faitem: " + faitem + " faactive: " + faactive);
        java.sql.Timestamp datetime = new java.sql.Timestamp(System.currentTimeMillis());

        Boolean ret = false;
        ViewObjectImpl vo = this.getFlagAssoc_VVO1();
        {
            // RowSetIterator rSet = getMrltFlagassociation_VO1().createRowSetIterator(null);
            //  MrltFlagassociation_VORowImpl curr = new MrltFlagassociation_VORowImpl();
            try {
                ViewCriteria vc = vo.createViewCriteria();
                ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
                /**Set the values for ViewCriteriaRow*/
                vcRow.setAttribute("Fid", fid);
                vcRow.setAttribute("Faitem", faitem);
                /**Add row to ViewCriteria*/
                vc.addRow(vcRow);
                /**Apply Criteria on ViewObject*/
                vo.applyViewCriteria(vc);
                /**Execute ViewObject*/
                vo.executeQuery();

                Row curr = vo.getCurrentRow();

                if ((fid.intValue() != 0) && (null != faitem) && (null != faactive)) {

                    if (curr != null) {
                        //System.out.println("curr.getAttribute(Faactive).toString(): " +
                                          // curr.getAttribute("Faactive").toString() +
                                          // " curr.getAttribute(Faactive).toString().equals(faactive): " +
                                         //  curr.getAttribute("Faactive").toString().equals(faactive));
                        if (((curr.getAttribute("Fid").toString()).equals(fid.toString())) &&
                            (curr.getAttribute("Faitem").equals(faitem))) {

                            ret = true;
                            curr.setAttribute("Faupdby", ADFContext.getCurrent().getSecurityContext().getUserName().toString());
                            curr.setAttribute("Faupddt", datetime);
                            curr.setAttribute("Faactive", faactive);
                            //System.out.println("Faupdby: "+ADFContext.getCurrent().getSecurityContext().getUserName().toString()+" datetime: " + datetime);
                        } else
                            return;
                    }
                    if (!ret) {
                        //System.out.println("good3");
                        Row r = vo.createRow();
                        r.setAttribute("Fid", fid);
                        r.setAttribute("Faitem", faitem);
                        r.setAttribute("Faupdby", ADFContext.getCurrent().getSecurityContext().getUserName());
                        r.setAttribute("Faupddt", datetime);
                        r.setAttribute("Faactive", faactive);
                        //System.out.println("Inside createFlagAssociation methodCall: " + r.getAttribute("Fid"));
                        vo.insertRow(r);

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    /*
    public String CopyWOTask(String ACTEVENT, Integer ACTACT, String ACTMATLIST, String TRAAUTH) {
        //System.out.println("actEvent: "+ACTEVENT+" actact: "+ACTACT+"actMatList: "+ACTMATLIST+"TRAAUTH: "+TRAAUTH);
        return (String)callStoredFunction(VARCHAR2, "mnrpkg.copyWOTask(?,?,?,?)",
                                          new Object[] { ACTEVENT, ACTACT, ACTMATLIST,TRAAUTH});
    }
    */
    public String CopyWOTask(String ACTEVENT, Integer ACTACT, String ACTMATLIST, String pUser, String pOrg) {
        
        //System.out.println("actEvent: "+ACTEVENT+" actact: "+ACTACT+" actMatList: "+ACTMATLIST+" pUser: "+pUser+" pOrg: "+pOrg);
        return (String)callStoredFunction(VARCHAR2, "mnrpkg.copyWOTask(?,?,?,?,?)",
                                          new Object[] { ACTEVENT, ACTACT, ACTMATLIST,pUser,pOrg});
    }

    public String isFlagUsedInWO(Integer Fidval) {
        //System.out.println("Fidval: "+Fidval);
        return (String)callStoredFunction(VARCHAR2, "mnrpkg.isFlagUsedInWO(?)",
                                          new Object[] {Fidval});
    }
    
    public void doQueryResultReset() {
        //Reset Query
        WOEventsImpl vo = this.getWOEvents();
        ViewCriteria vc = vo.getViewCriteria();
        vo.executeEmptyRowSet();
        vo.applyViewCriteria(vc);
        // Create new blank Work Order
        Row row = vo.createRow();
        vo.insertRow(row);
        vo.setCurrentRow(row);
    }

    public String UpdateWOStatus(String evntCd, String pStatus, String pUser) {
        
        System.out.println("Inside WODataControlImpl UpdateWOStatus evntCd: "+evntCd+" pStatus: "+pStatus+" pUser: "+pUser);
        return (String)callStoredFunction(VARCHAR2, "mnrpkg.ITS_WorkOrderUpd(?,?,?)",
                                          new Object[] { evntCd, pStatus, pUser});
    }    
    
    public String RejWOPartsReturn(String pEvtCode) {
             //System.out.println("inside brRejWOPartsReturn function: " + pEvtCode);
             
             String ret = (String)callStoredFunction(VARCHAR2, "mnrpkg.RejWOPartsReturn(?)",
                                               new Object[] {pEvtCode});
             //System.out.println("brRejWOPartsReturn Ret = " + ret);
             return ret;
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

    /**
     * Container's getter for AccountRateTask.
     * @return AccountRateTask
     */
    public LaborCostImpl getAccountRateTask() {
        return (LaborCostImpl)findViewObject("AccountRateTask");
    }

    /**
     * Container's getter for CurrentAccountRateVersion.
     * @return CurrentAccountRateVersion
     */
    public CurrentAccountRateVersionImpl getCurrentAccountRateVersion() {
        return (CurrentAccountRateVersionImpl)findViewObject("CurrentAccountRateVersion");
    }

    /**
     * Container's getter for LaborCost.
     * @return LaborCost
     */
    public LaborCostImpl getLaborCost() {
        return (LaborCostImpl)findViewObject("LaborCost");
    }

    /**
     * Container's getter for EstimateExistence.
     * @return EstimateExistence
     */
    public EstimateExistenceImpl getEstimateExistence() {
        return (EstimateExistenceImpl)findViewObject("EstimateExistence");
    }

    /**
     * Container's getter for Ucodes.
     * @return Ucodes
     */
    public UcodesImpl getUcodes() {
        return (UcodesImpl)findViewObject("Ucodes");
    }

    /**
     * Container's getter for WOTaskListByAccountWOType.
     * @return WOTaskListByAccountWOType
     */
    public ExtendedViewObjectImpl getWOTaskListByAccountWOType() {
        return (ExtendedViewObjectImpl)findViewObject("WOTaskListByAccountWOType");
    }

    /**
     * Container's getter for WOEvents.
     * @return WOEvents
     */
    public WOEventsImpl getWOEvents() {
        return (WOEventsImpl)findViewObject("WOEvents");
    }

    /**
     * Container's getter for Assets.
     * @return Assets
     */
    public ExtendedViewObjectImpl getAssets() {
        return (ExtendedViewObjectImpl)findViewObject("Assets");
    }

    /**
     * Container's getter for WOActivities.
     * @return WOActivities
     */
    public ExtendedViewObjectImpl getWOActivities() {
        return (ExtendedViewObjectImpl)findViewObject("WOActivities");
    }

    /**
     * Container's getter for Matlists.
     * @return Matlists
     */
    public ExtendedViewObjectImpl getMatlists() {
        return (ExtendedViewObjectImpl)findViewObject("Matlists");
    }

    /**
     * Container's getter for Matlparts.
     * @return Matlparts
     */
    public ExtendedViewObjectImpl getMatlparts() {
        return (ExtendedViewObjectImpl)findViewObject("Matlparts");
    }


    /**
     * Container's getter for MeterReadings.
     * @return MeterReadings
     */
    public ExtendedViewObjectImpl getMeterReadings() {
        return (ExtendedViewObjectImpl)findViewObject("MeterReadings");
    }

    /**
     * Container's getter for MeterReadings1.
     * @return MeterReadings1
     */
    public ExtendedViewObjectImpl getMeterReadings1() {
        return (ExtendedViewObjectImpl)findViewObject("MeterReadings1");
    }

    /**
     * Container's getter for WOServiceGroup.
     * @return WOServiceGroup
     */
    public ExtendedViewObjectImpl getWOServiceGroup() {
        return (ExtendedViewObjectImpl)findViewObject("WOServiceGroup");
    }

    /**
     * Container's getter for Transactions.
     * @return Transactions
     */
    public TransactionsImpl getTransactions() {
        return (TransactionsImpl)findViewObject("Transactions");
    }

    /**
     * Container's getter for PartsView.
     * @return PartsView
     */
    public ExtendedViewObjectImpl getPartsView() {
        return (ExtendedViewObjectImpl)findViewObject("PartsView");
    }

    /**
     * Container's getter for Estimate.
     * @return Estimate
     */
    public EstimateImpl getEstimate() {
        return (EstimateImpl)findViewObject("Estimate");
    }

    /**
     * Container's getter for EstimateTask.
     * @return EstimateTask
     */
    public ExtendedViewObjectImpl getEstimateTask() {
        return (ExtendedViewObjectImpl)findViewObject("EstimateTask");
    }

    /**
     * Container's getter for EstimatePart.
     * @return EstimatePart
     */
    public ExtendedViewObjectImpl getEstimatePart() {
        return (ExtendedViewObjectImpl)findViewObject("EstimatePart");
    }

    /**
     * Container's getter for WOEstimate.
     * @return WOEstimate
     */
    public EstimateImpl getWOEstimate() {
        return (EstimateImpl)findViewObject("WOEstimate");
    }

    /**
     * Container's getter for WOEvents1.
     * @return WOEvents1
     */
    public WOEventsImpl getWOEvents1() {
        return (WOEventsImpl)findViewObject("WOEvents1");
    }

    /**
     * Container's getter for Estimate1.
     * @return Estimate1
     */
    public EstimateImpl getEstimate1() {
        return (EstimateImpl)findViewObject("Estimate1");
    }

    /**
     * Container's getter for WOEventsQuery.
     * @return WOEventsQuery
     */
    public WOEventsImpl getWOEventsQuery() {
        return (WOEventsImpl)findViewObject("WOEventsQuery");
    }

    /**
     * Container's getter for ActiveMrltFlag.
     * @return ActiveMrltFlag
     */
    public ExtendedViewObjectImpl getActiveMrltFlag() {
        return (ExtendedViewObjectImpl)findViewObject("ActiveMrltFlag");
    }

    /**
     * Container's getter for MrltFlagassociation_VO1.
     * @return MrltFlagassociation_VO1
     */
    public MrltFlagassociation_VOImpl getMrltFlagassociation_VO1() {
        return (MrltFlagassociation_VOImpl)findViewObject("MrltFlagassociation_VO1");
    }

    /**
     * Container's getter for MrltFlag_VO1.
     * @return MrltFlag_VO1
     */
    public ExtendedViewObjectImpl getMrltFlag_VO1() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFlag_VO1");
    }


    /**
     * Container's getter for CurrentARVersionLaborCostLink1.
     * @return CurrentARVersionLaborCostLink1
     */
    public ViewLinkImpl getCurrentARVersionLaborCostLink1() {
        return (ViewLinkImpl)findViewLink("CurrentARVersionLaborCostLink1");
    }

    /**
     * Container's getter for WOAssetLink2.
     * @return WOAssetLink2
     */
    public ViewLinkImpl getWOAssetLink2() {
        return (ViewLinkImpl)findViewLink("WOAssetLink2");
    }

    /**
     * Container's getter for EventActivityLink1.
     * @return EventActivityLink1
     */
    public ViewLinkImpl getEventActivityLink1() {
        return (ViewLinkImpl)findViewLink("EventActivityLink1");
    }

    /**
     * Container's getter for WOActivitiesMatlists1.
     * @return WOActivitiesMatlists1
     */
    public ViewLinkImpl getWOActivitiesMatlists1() {
        return (ViewLinkImpl)findViewLink("WOActivitiesMatlists1");
    }

    /**
     * Container's getter for MatlistsMatlpartsLink1.
     * @return MatlistsMatlpartsLink1
     */
    public ViewLinkImpl getMatlistsMatlpartsLink1() {
        return (ViewLinkImpl)findViewLink("MatlistsMatlpartsLink1");
    }


    /**
     * Container's getter for WOEventsMeterReadingsLink1.
     * @return WOEventsMeterReadingsLink1
     */
    public ViewLinkImpl getWOEventsMeterReadingsLink1() {
        return (ViewLinkImpl)findViewLink("WOEventsMeterReadingsLink1");
    }

    /**
     * Container's getter for AssetReadingLink1.
     * @return AssetReadingLink1
     */
    public ViewLinkImpl getAssetReadingLink1() {
        return (ViewLinkImpl)findViewLink("AssetReadingLink1");
    }

    /**
     * Container's getter for MrltEstimatetaskdetailFk1Link2.
     * @return MrltEstimatetaskdetailFk1Link2
     */
    public ViewLinkImpl getMrltEstimatetaskdetailFk1Link2() {
        return (ViewLinkImpl)findViewLink("MrltEstimatetaskdetailFk1Link2");
    }

    /**
     * Container's getter for MrltEstimatepartdetailFk1Link1.
     * @return MrltEstimatepartdetailFk1Link1
     */
    public ViewLinkImpl getMrltEstimatepartdetailFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltEstimatepartdetailFk1Link1");
    }

    /**
     * Container's getter for WOEventsEstimateLink1.
     * @return WOEventsEstimateLink1
     */
    public ViewLinkImpl getWOEventsEstimateLink1() {
        return (ViewLinkImpl)findViewLink("WOEventsEstimateLink1");
    }

    /**
     * Container's getter for WOEventsEstimateLink2.
     * @return WOEventsEstimateLink2
     */
    public ViewLinkImpl getWOEventsEstimateLink2() {
        return (ViewLinkImpl)findViewLink("WOEventsEstimateLink2");
    }

    /**
     * Container's getter for WOEventFlagAssociationLink1.
     * @return WOEventFlagAssociationLink1
     */
    public ViewLinkImpl getWOEventFlagAssociationLink1() {
        return (ViewLinkImpl)findViewLink("WOEventFlagAssociationLink1");
    }

    /**
     * Container's getter for MrltFlagassociationFk1Link1.
     * @return MrltFlagassociationFk1Link1
     */
    public ViewLinkImpl getMrltFlagassociationFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltFlagassociationFk1Link1");
    }

    /**
     * Container's getter for FlagAssoc_VVO1.
     * @return FlagAssoc_VVO1
     */
    public ExtendedViewObjectImpl getFlagAssoc_VVO1() {
        return (ExtendedViewObjectImpl)findViewObject("FlagAssoc_VVO1");
    }

    /**
     * Container's getter for FlagAssocChkboxLink1.
     * @return FlagAssocChkboxLink1
     */
    public ViewLinkImpl getFlagAssocChkboxLink1() {
        return (ViewLinkImpl)findViewLink("FlagAssocChkboxLink1");
    }


    /**
     * Container's getter for MrltFlag_VO2.
     * @return MrltFlag_VO2
     */
    public ExtendedViewObjectImpl getMrltFlag_VO2() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFlag_VO2");
    }


    /**
     * Container's getter for AvailableQtyExistence1.
     * @return AvailableQtyExistence1
     */
    public ExtendedViewObjectImpl getAvailableQtyExistence1() {
        return (ExtendedViewObjectImpl)findViewObject("AvailableQtyExistence1");
    }

    /**
     * Container's getter for AvailableQtyExistenceLink1.
     * @return AvailableQtyExistenceLink1
     */
    public ViewLinkImpl getAvailableQtyExistenceLink1() {
        return (ViewLinkImpl)findViewLink("AvailableQtyExistenceLink1");
    }

    /**
     * Container's getter for WOEvents2.
     * @return WOEvents2
     */
    public WOEventsImpl getWOEvents2() {
        return (WOEventsImpl) findViewObject("WOEvents2");
    }


    /**
     * Container's getter for EstimateVerLov1.
     * @return EstimateVerLov1
     */
    public ExtendedViewObjectImpl getEstimateVerLov() {
        return (ExtendedViewObjectImpl) findViewObject("EstimateVerLov");
    }

    /**
     * Container's getter for estimate_estimateverlovLink1.
     * @return estimate_estimateverlovLink1
     */
    public ViewLinkImpl getestimate_estimateverlovLink1() {
        return (ViewLinkImpl) findViewLink("estimate_estimateverlovLink1");
    }


    /**
     * Container's getter for Estimate2.
     * @return Estimate2
     */
    public EstimateImpl getEstimateWO() {
        return (EstimateImpl) findViewObject("EstimateWO");
    }

    /**
     * Container's getter for R5EventsEstimatesWOLink1.
     * @return R5EventsEstimatesWOLink1
     */
    public ViewLinkImpl getR5EventsEstimatesWOLink1() {
        return (ViewLinkImpl) findViewLink("R5EventsEstimatesWOLink1");
    }

    /**
     * Container's getter for EstimateTask1.
     * @return EstimateTask1
     */
    public ExtendedViewObjectImpl getEstimateTaskWO() {
        return (ExtendedViewObjectImpl) findViewObject("EstimateTaskWO");
    }

    /**
     * Container's getter for MrltEstimatetaskdetailFk1Link1.
     * @return MrltEstimatetaskdetailFk1Link1
     */
    public ViewLinkImpl getMrltEstimatetaskdetailFk1Link1() {
        return (ViewLinkImpl) findViewLink("MrltEstimatetaskdetailFk1Link1");
    }

    /**
     * Container's getter for EstimatePart1.
     * @return EstimatePart1
     */
    public ExtendedViewObjectImpl getEstimatePartWO() {
        return (ExtendedViewObjectImpl) findViewObject("EstimatePartWO");
    }

    /**
     * Container's getter for MrltEstimatepartdetailFk1Link2.
     * @return MrltEstimatepartdetailFk1Link2
     */
    public ViewLinkImpl getMrltEstimatepartdetailFk1Link2() {
        return (ViewLinkImpl) findViewLink("MrltEstimatepartdetailFk1Link2");
    }


    /**
     * Container's getter for CustomFields.
     * @return CustomFields
     */
    public ExtendedViewObjectImpl getCustomFields() {
        return (ExtendedViewObjectImpl) findViewObject("CustomFields");
    }

    /**
     * Container's getter for WOEventsCustomFieldsLink1.
     * @return WOEventsCustomFieldsLink1
     */
    public ViewLinkImpl getWOEventsCustomFieldsLink1() {
        return (ViewLinkImpl) findViewLink("WOEventsCustomFieldsLink1");
    }


    /**
     * Container's getter for EstimateQueryVO1.
     * @return EstimateQueryVO1
     */
    public ExtendedViewObjectImpl getEstimateQuery() {
        return (ExtendedViewObjectImpl) findViewObject("EstimateQuery");
    }

    /**
     * Container's getter for Estimate2.
     * @return Estimate2
     */
    public EstimateImpl getEstimate2() {
        return (EstimateImpl) findViewObject("Estimate2");
    }

    /**
     * Container's getter for estimateQuery_estimateLink1.
     * @return estimateQuery_estimateLink1
     */
    public ViewLinkImpl getestimateQuery_estimateLink1() {
        return (ViewLinkImpl) findViewLink("estimateQuery_estimateLink1");
    }

    /**
     * Container's getter for EstimateTask1.
     * @return EstimateTask1
     */
    public ExtendedViewObjectImpl getEstimateQueryTask() {
        return (ExtendedViewObjectImpl) findViewObject("EstimateQueryTask");
    }

    /**
     * Container's getter for MrltEstimatetaskdetailFk1Link3.
     * @return MrltEstimatetaskdetailFk1Link3
     */
    public ViewLinkImpl getMrltEstimatetaskdetailFk1Link3() {
        return (ViewLinkImpl) findViewLink("MrltEstimatetaskdetailFk1Link3");
    }

    /**
     * Container's getter for EstimatePart1.
     * @return EstimatePart1
     */
    public ExtendedViewObjectImpl getEstimateQueryPart() {
        return (ExtendedViewObjectImpl) findViewObject("EstimateQueryPart");
    }

    /**
     * Container's getter for MrltEstimatepartdetailFk1Link3.
     * @return MrltEstimatepartdetailFk1Link3
     */
    public ViewLinkImpl getMrltEstimatepartdetailFk1Link3() {
        return (ViewLinkImpl) findViewLink("MrltEstimatepartdetailFk1Link3");
    }
}
