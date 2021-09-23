package its.mnr.mp5.view;

import com.bea.wls.ejbgen.Bean;


import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectManyCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.LaunchPopupEvent;


import its.mnr.mp5.vo.workorder.WOEventsImpl;
import its.mnr.mp5.vo.workorder.WOEventsRowImpl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.model.QueryModel;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.domain.Timestamp;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.uicli.binding.JUCtrlAttrsBinding;

import oracle.jbo.uicli.binding.JUCtrlListBinding;
import oracle.jbo.uicli.binding.JUIteratorBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import org.apache.tools.ant.taskdefs.Available;

import utils.system;

public class WorkOrderBean {
    private RichInputComboboxListOfValues jobTypeLOV;
    private RichInputComboboxListOfValues costCodeLOV;
    private RichSelectBooleanCheckbox isAssetChkBox;
    private RichInputText actMatList;
    private RichPopup estReqPop;
    private RichPopup pendingChangePop;
    private RichSelectOneChoice loccode;
    private RichSelectOneChoice damcode;
    private RichSelectOneChoice repcode;
    private RichSelectOneChoice evtStatus;
    private RichPopup meterReadingPop;
    private RichPopup customFieldsPop;
    private RichPanelFormLayout partsForm;
    private RichPopup woTaskPop;
    private RichDialog woTaskDialog;
    private RichInputListOfValues partlov;
    private RichInputText partQty;
    private RichInputText partDesc;
    private RichInputText actHrs;
    private RichInputText equipDesc;
    private RichInputListOfValues taskLOV;
    private RichButton editRecordBtn;
    private RichPanelSplitter lhsSplitter;
    private RichSelectOneChoice taskSOC;
    private RichInputText actMechIc;
    private RichTable resultsTable;
    private RichPanelGroupLayout panelGroupLayoutDetail;
    private String AssetUom;
    private RichPopup flagAssoc;
    private RichSelectManyCheckbox flagsVals;
    private RichInputComboboxListOfValues evtClass;
    private RichButton commbttn;
    private RichButton addFlagAssocWOBttn;
    private RichButton copyWOTaskBttn;
    private RichButton addNewWOTaskBttn;
    private RichButton applyWOTaskBttn;
    private RichButton cancelWOTaskBttn;
    private RichSelectOneChoice actActChoice;
    private RichTable woActivitiesTable;
    private RichTable flagAssocTable;
    
    private String showEst;
    private RichQuery woQuery;

    public WorkOrderBean() {
    }

    public void setShowEst(String showEst) {
        this.showEst = showEst;
    }

    public String getShowEst() {
        return getMP5Profile("ShowEstimate");
    }
    /* Helper methods */

    public oracle.binding.BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public static BindingContext getBindingContext() {
        return BindingContext.getCurrent();
    }

    public static BindingContainer getBindingContainer() {
        return getBindingContext().getCurrentBindingsEntry();
    }

    public static DCBindingContainer getDCBindingContainer() {
        return (DCBindingContainer)getBindingContainer();
    }

    public static OperationBinding getOperationBinding(String operationName) {
        return getBindingContainer().getOperationBinding(operationName);
    }

    private String parseString(String str, String separator, String delimiter) {
        if (separator == null) {
            return "";
        }
        int separatorPos = str.indexOf(separator);
        if (separatorPos == -1) {
            return "";
        }

        //return str.substring(pos + separator.length()); 1152,EQTYP:CH,RFFLG:
        String detail = str.substring(separatorPos + separator.length());
        int delimiterPos = detail.indexOf(delimiter);

        if (delimiterPos == -1) {
            return "";
        }
        return detail.substring(0, delimiterPos);
    }

    public String userModificationMsg(String scope, String summary, String detail) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        //FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, text);

        FacesMessage fm = new FacesMessage();
        if (scope == "INFO") {
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        if (scope == "WARN") {
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        if (scope == "ERROR") {
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        if (scope == "FATAL") {
            fm.setSeverity(FacesMessage.SEVERITY_FATAL);
        }
        //fm.setSeverity(FacesMessage.SEVERITY_INFO);
        fm.setSummary(summary);
        fm.setDetail(detail);
        ctx.addMessage(null, fm);
        return null;
    }

    /* Web Service methods */

    public String cbWS_action() {
        String eqOwner = "";
        String eqTyp = "";
        String eqJobType = "";
        String eqRFFlg = "";
        String assetinfo;
        String currJobType = (String)ADFUtil.evaluateEL("#{bindings.EvtJobtype.inputValue}");

        //Check if equipment is ITS Asset and get Owner and Jobtype
        assetinfo = getAssetDetail();
        //System.out.println("assetinfo:" + assetinfo);


        // if (!assetinfo.equals("NOTFOUND")) {
        if (!StringUtils.equals(assetinfo, "NOTFOUND")) {
            eqOwner = parseString(assetinfo, "OWNER:", ",");
            //System.out.println("asset eqOwner: " + eqOwner);
            eqJobType = parseString(assetinfo, "JOBTYP:", ",");
            //System.out.println("asset JobTyp: " + eqJobType);
        } else {

            //System.out.println("Not Asset, calling WS");
            OperationBinding operationBinding = getOperationBinding("Get_EquipInfo");
            Object result = operationBinding.execute();

            String returnStr = (String)ADFUtil.evaluateEL("#{bindings.WSReturn.inputValue}");

            //System.out.println("returnStr: " + returnStr);

            if (StringUtils.isNotBlank(returnStr)) {
                eqOwner = parseString(returnStr, "OWNER:", ",");
                //System.out.println("eqOwner: " + eqOwner);
                eqTyp = parseString(returnStr, "EQTYP:", ",");
                //System.out.println("eqTyp: " + eqTyp);
                eqRFFlg = parseString(returnStr, "RFFLG:", ",");
                //System.out.println("eqRFFlg: " + eqRFFlg);
                eqJobType = getWOJobType(eqTyp, eqRFFlg);
                //System.out.println("eqJobType: " + eqJobType);
            } else {
                userModificationMsg("ERROR", "Error", "Search Interface is down. Please contact the Administrator");
            }

            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        }

        //Update Account # if Account is currently active
        //System.out.println("eqOwner:" + eqOwner);
        //System.out.println("isAccountActive(eqOwner):" + isAccountActive(eqOwner) );
        if (isAccountActive(eqOwner)) {
            //System.out.println("auto-populating UI field" );
            ADFUtil.setEL("#{bindings.EvtCostcode2.inputValue}", eqOwner);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.costCodeLOV);
        }

        //Update Job Type only if current Job Type is empty
        //if (currJobType.equals("") || StringUtils.isBlank(currJobType)) {
        if (StringUtils.isBlank(currJobType)) {
            //System.out.println("Updating Job Type");
            ADFUtil.setEL("#{bindings.EvtJobtype.inputValue}", eqJobType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.jobTypeLOV);
        }

        //Check Asset CheckBox
        // if (!assetinfo.equals("NOTFOUND")) {
        if (!StringUtils.equals(assetinfo, "NOTFOUND")) {
            ADFUtil.setEL("#{bindings.ItsIsasset.inputValue}", true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.isAssetChkBox);
        }
        /*
        if (!eqOwner.equals("") && !eqTyp.equals("") && !eqRFFlg.equals("") && assetinfo.equals("NOTFOUND")) {
            userModificationMsg("INFO", "Success", "Account Owner and Work Order Type found for Equipment");
        } else if (!eqOwner.equals("") && !eqJobType.equals("") && !assetinfo.equals("NOTFOUND")) {
            userModificationMsg("INFO", "Success", "Account Owner and Work Order Type found for Asset");
        } else if (eqOwner.equals("")) {
            userModificationMsg("WARN", "Success", "Account Owner not found.");
        } else if (eqTyp.equals("") && assetinfo.equals("NOTFOUND")) {
            userModificationMsg("WARN", "Success", "WO Type not found for Equipment.");
        } else if (eqJobType.equals("") && !assetinfo.equals("NOTFOUND")) {
            userModificationMsg("WARN", "Success", "WO Type not found for Asset.");
        }
*/
        if (StringUtils.isNotBlank(eqOwner) && StringUtils.isNotBlank(eqTyp) && StringUtils.isNotBlank(eqRFFlg) &&
            StringUtils.equals(assetinfo, "NOTFOUND")) {
            userModificationMsg("INFO", "Success", "Account Owner and Work Order Type found for Equipment");
        } else if (StringUtils.isNotBlank(eqOwner) && StringUtils.isNotBlank(eqJobType) &&
                   !StringUtils.equals(assetinfo, "NOTFOUND")) {
            userModificationMsg("INFO", "Success", "Account Owner and Work Order Type found for Asset");
        } else if (StringUtils.isBlank(eqOwner)) {
            userModificationMsg("WARN", "Success", "Account Owner not found.");
        } else if (StringUtils.isBlank(eqTyp) && StringUtils.equals(assetinfo, "NOTFOUND")) {
            userModificationMsg("WARN", "Success", "WO Type not found for Equipment.");
        } else if (StringUtils.isBlank(eqJobType) && !StringUtils.equals(assetinfo, "NOTFOUND")) {
            userModificationMsg("WARN", "Success", "WO Type not found for Asset.");
        }

        return null;
    }

    public String getAssetDetail() {
        OperationBinding operationBinding = getOperationBinding("getAssetDetail");
        AttributeBinding assetNm = (AttributeBinding)getBindings().get("EvtDesc");
            //System.out.println("getAssetDetail() Equipment #: " + assetNm);
            //optional
            operationBinding.getParamsMap().put("assetNm", assetNm);
            //invoke method
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //check errors
                List errors = operationBinding.getErrors();
            }
            return (String)result;
    }

    public String getWOJobType(String sWOType, String sRFFlg) {
        OperationBinding operationBinding = getOperationBinding("getWOJobType");
        //optional
        operationBinding.getParamsMap().put("WOType", sWOType);
        operationBinding.getParamsMap().put("RFFlg", sRFFlg);
        //invoke method
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return (String)result;
    }

    public boolean isAccountActive(String sacctnum) {
        OperationBinding operationBinding = getOperationBinding("isAccountActive");
        //optional
        operationBinding.getParamsMap().put("sacctnum", sacctnum);
        //invoke method
        boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    /* Left-hand-side Query Panel */

    public void processQuery(QueryEvent queryEvent) {
        QueryDescriptor qdes = queryEvent.getDescriptor();
        System.out.println("NAME " + qdes.getName());
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
            ADFUtil.invokeMethodExpressionQueryEvent("#{bindings.WorkOrderListQuery.processQuery}", queryEvent);

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.resultsTable);
            String sFirstWO = getFirstWOResult();
            if (sFirstWO != null) {
                syncWOMasterDetail(sFirstWO);
            }
        }
    }

    public void eventsTableSelectionListener(SelectionEvent selectionEvent) {
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        if (isDirty()) {
            if (StringUtils.isBlank(sEvtCode)) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            }
        } else {
            ADFUtil.invokeMethodExpressionSelectionEvent("#{bindings.WOEvents.collectionModel.makeCurrent}",
                                                         selectionEvent);
            //System.out.println("WOEvents Selection Listener");
            syncWOMasterDetailCurrentRow();
        }
    }

    public void syncWOMasterDetailCurrentRow() {
        //System.out.println("syncWOMasterDetailCurrentRow");
        Row selectedRow = (Row)ADFUtil.evaluateEL("#{bindings.WOEventsQueryIterator.currentRow}");
        String sEvtCode = (String)selectedRow.getAttribute("EvtCode");
        //System.out.println("sEvtCode: " + sEvtCode);
        syncWOMasterDetail(sEvtCode);
    }

    public String getFirstWOResult() {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dc = (DCIteratorBinding)bindings.get("WOEventsQueryIterator");
        System.out.println("EstimatedRowCount: " + dc.getViewObject().getEstimatedRowCount());

        if (dc.getViewObject().getEstimatedRowCount() > 0) {
            int i = 0;
            Row row = dc.getRowAtRangeIndex(i);
            String sEvtCode = (String)row.getAttribute("EvtCode");
            //System.out.println("ActAct ::" + row.getAttribute("ActAct"));
            //System.out.println("ItsTaskid ::" + sTaskid);
            return sEvtCode;
        }
        return null;
    }

    public void syncWOMasterDetail(String sWONum) {
        //System.out.println("syncWOMasterDetail");
        BindingContainer bindings = getBindings();
        DCIteratorBinding dc = (DCIteratorBinding)bindings.get("WOEventsIterator");
        ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();

        System.out.println("Previous: " + vo.getCurrentRow().getAttribute("EvtCode"));

        vo.setApplyViewCriteriaName("WObyEvtCode");
        //        ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("WObyEvtCode");
        //        vo.removeViewCriteria("WObyEvtCode");
        //        //execute empty RowSet to clear it
        //        vo.executeEmptyRowSet();
        //
        //        vc.resetCriteria();
        //        vo.applyViewCriteria(vc);

        // set bind variable for ViewObject and execute
        System.out.println("Bind Variable Passed: " + sWONum);
        //        vo.ensureVariableManager().setVariableValue("WOEvtCodeBind", sWONum);
        vo.setNamedWhereClauseParam("WOEvtCodeBind", sWONum);
        vo.executeQuery();

        System.out.println("Estimated Row Count: " + vo.getEstimatedRowCount());
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        System.out.println("After executeQuery: " + vo.getCurrentRow().getAttribute("EvtCode") + ":" + sEvtCode);
        System.out.println("");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelGroupLayoutDetail);
    }

    public String previousBtnAction() {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Previous");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            syncWOMasterDetailCurrentRow();
        }

        return null;
    }

    public String nextBtnAction() {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Next");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            syncWOMasterDetailCurrentRow();
        }

        return null;
    }

    public void setResultsTable(RichTable resultsTable) {
        this.resultsTable = resultsTable;
    }

    public RichTable getResultsTable() {
        return resultsTable;
    }

    public void setPanelGroupLayoutDetail(RichPanelGroupLayout panelGroupLayoutDetail) {
        this.panelGroupLayoutDetail = panelGroupLayoutDetail;
    }

    public RichPanelGroupLayout getPanelGroupLayoutDetail() {
        return panelGroupLayoutDetail;
    }

    /* Material Parts methods */

    public String addPart_action() {
        //System.out.println("inside addPart_action()") ;
        Boolean chk = chkMatList();
        if (chk) {
            OperationBinding operationBinding = getOperationBinding("CreateInsert5");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        }
        //Material List check failure
        else if (!chk) {
            //System.out.println("Material List check failure");
            userModificationMsg("ERROR", "Fail", "System is unable to add Parts. Please contact Administrator");
            return null;

        }
        return null;
    }

    public Boolean chkMatList() {
        //Function checks if a material list exists for the Work Order Activity, if not, then create it
        //System.out.println("inside chkMatList()");

        String matlistexist = (String)ADFUtil.evaluateEL("#{bindings.MtlCode.inputValue}");

        //System.out.println("inside chkMatList():" + matlistexist);

        if (StringUtils.isBlank(matlistexist)) {

            //System.out.println("Creating MatList");
            OperationBinding operationBinding = getOperationBinding("CreateWithParams");
            Object result = operationBinding.execute();

            String matlistexist2 = (String)ADFUtil.evaluateEL("#{bindings.MtlCode.inputValue}");

            ADFUtil.setEL("#{bindings.ActMatlist.inputValue}", matlistexist2);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.actMatList);

            if (!operationBinding.getErrors().isEmpty()) {
                return false;
            }
        }


        return true;
    }

    public String delPart_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete3");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //Refresh the form
        //refreshCurrentPage();
        partlov.resetValue();
        partQty.resetValue();
        //woTaskRollback_action();
        return null;
    }
    /* Estimate Logic */

    public Boolean enterEstimateLogic(String sEvtCodeIn, String sEvtStsIn) {
        Boolean statusChk = false;
        Boolean processEstimateLogicChk = false;

        //Check if WO Status is Completed By Maintenance
        //String sEvtSts = (String)ADFUtil.evaluateEL("#{bindings.EvtStatus.inputValue}");
        //System.out.println("enterEstimateLogic()" + sEvtStsIn);
        if (sEvtStsIn.equals("CBM")) {
            statusChk = true;
        } else {
            return true;
        }

        if (statusChk) {
            //Check if Estimate EDI is enabled
            if (SendEstimateThruEDIFlg()) {
                //EDI Estimate is enabled
                //Is Estimate Exceed Alert?
                if (SendEstimateExceedAlert() && isEstimateRequired()) {
                    //if (SendEstimateExceedAlert()) {
                    //Yes. Call Pop up
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    estReqPop.show(hints);
                    return false;
                } else {
                    //No. Estimate Exceed Alert is disabled
                    //System.out.println("calling processEstimateLogic()");
                    processEstimateLogicChk = processEstimateLogic(sEvtCodeIn);
                }

            } else {
                //Estimate EDI is disabled. Estimate will not be generated.
                //set WO Status = COMPLETED
                Boolean ret = updateWOStatus("C");
                //Notify user if error occurs in update
                if (!ret) {
                    String adminemail = getMP5Profile("AdminEmailAddress");
                    //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");

                    userModificationMsg("ERROR", "ERROR",
                                        "Error encountered while setting status to Completed. The Administrator has been notified");
                    sendEstimateFailure(adminemail, "Pending WO XML Error #1",
                                        "Failed to status to COMPLETED for " + sEvtCodeIn);
                }
                return ret;
            }
        }
        return processEstimateLogicChk;
    }

    public Boolean processEstimateLogic(String sEvtCodeIn) {
        //System.out.println("Inside processEstimateLogic()");
        Boolean getEstChk = false;
        Boolean createEstChk = false;
        Boolean writeEstChk = false;
        String adminemail = getMP5Profile("AdminEmailAddress");
        String sEstId = null;
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        String sAcctNum = (String)ADFUtil.evaluateEL("#{bindings.EvtCostcode2.inputValue}");

        //printStatus("processEstimateLogic(): before checkEstimateCriteria");

        //Check Estimate Criteria
        Boolean retCrit = (Boolean)checkEstimateCriteria();
        //printStatus("processEstimateLogic(): after checkEstimateCriteria");
        //Meet Criteria?
        if (retCrit) {
            createEstChk = true;
        } else {
            //Estimate required, but WO does not meet Criteria(Estimate not required)
            //set WO Status = COMPLETED
            //printStatus("processEstimateLogic(): before updateWOStatus");
            Boolean ret = updateWOStatus("C");
            //printStatus("processEstimateLogic(): after updateWOStatus");
            //Notify user if error occurs in update
            if (!ret) {
                userModificationMsg("ERROR", "ERROR",
                                    "Error encountered while setting status to Completed. The Administrator has been notified");
                sendEstimateFailure(adminemail, "Pending WO XML Error #2",
                                    "Failed to status to COMPLETED for " + sEvtCodeIn);
            }
            return ret;
        }

        //Create Estimate Record Logic
        if (createEstChk) {
            String retEst;
            //Create Estimate record
            retEst = ITS_EstimateIns(sEvtCodeIn, sAcctNum);
            if (retEst.contains("ESTID")) {
                //Success. Updated WO Estimate Id
                sEstId = parseString(retEst, "ESTID:", ",");
                updateWOEstId(sEstId);
                writeEstChk = true;
            } else if (retEst.contains("CHK")) {
                //Fail. Send email notification
                String sEstChk = parseString(retEst, "CHK:", ",");
                sendEstimateFailure(adminemail, "Pending WO Record Error #3-Chk" + sEstChk,
                                    "Failed to create Pending WO record for " + sEvtCodeIn + "PWO#" + sEstId);
                //Set WO Status = EXCEPTION
                Boolean ret = updateWOStatus("EXC");
                //Notify user
                if (ret) {
                    userModificationMsg("ERROR", "ERROR", "Error encountered. The Administrator has been notified");
                } else {
                    sendEstimateFailure(adminemail, "Pending WO Record Error #3",
                                        "Failed to update to EXCEPTION for " + sEvtCodeIn);
                }
                return ret;
            }
        }

        //Write Estimate to XML file Logic
        if (writeEstChk && StringUtils.isNotBlank(sEstId)) {
            Boolean retWrXML = writeEstimateXML(sEstId);
            if (retWrXML) {
                //Success
                //Set WO Status = ESTIMATE
                Boolean ret = updateWOStatus("PEND");
                //Notify user if error occurs in update
                if (!ret) {
                    userModificationMsg("ERROR", "ERROR",
                                        "Error encountered while setting status to Pending. The Administrator has been notified");
                    sendEstimateFailure(adminemail, "Pending WO XML Error #4",
                                        "Failed to status to PENDING for " + sEvtCodeIn);
                }
                return ret;
            } else {
                //Fail. Send email notification
                sendEstimateFailure(adminemail, "Pending WO XML Error",
                                    "Failed to create Pending WO XML for Pending WO #" + sEstId);
                //Set WO Status = EXCEPTION
                Boolean ret = updateWOStatus("EXC");
                //Notify user if error occurs in update
                if (!ret) {
                    userModificationMsg("ERROR", "ERROR",
                                        "Error encountered while setting status to EXCEPTION. The Administrator has been notified");
                    sendEstimateFailure(adminemail, "Pending WO XML Error #5",
                                        "Failed to status to EXCEPTION for " + sEvtCodeIn);
                }
                return ret;
            }
        }
        return false;
    }

    public Boolean writeEstimateXML(String sestid) {
        OperationBinding operationBinding = getOperationBinding("writeEstimateXML");
        operationBinding.getParamsMap().put("sestid", sestid);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public Boolean checkEstimateCriteria() {
        //printStatus("checkEstimateCriteria()");
        //Check Estimate Criteria
        String retCrit = getEstimateCriteria();
        //parse results "EST: "OVER:" "UOM:"
        String sEST = parseString(retCrit, "PEND:", ",");
        String sOVER = parseString(retCrit, "OVER:", ",");
        String sUOM = parseString(retCrit, "UOM:", ",");
        Boolean result = false;
        //Meet Criteria?
        if (sEST.equals("Y") && StringUtils.isNotBlank(sOVER) && StringUtils.isNotBlank(sUOM)) {
            Double dOVER = Double.parseDouble(sOVER);
            if (sEST.equals("Y")) {
                //Account requires Estimate
                if (sUOM.equals("D")) {
                    //By Dollar
                    //Get Total Parts and Labor
                    //String sTotalPartsLabor = (String)ADFUtil.evaluateEL("#{bindings.TotalPartsLabor.inputValue}");
                    //Double dTotalPartsLabor = Double.parseDouble(sTotalPartsLabor);
                    Double dTotalPartsLabor = (Double)ADFUtil.evaluateEL("#{bindings.TotalPartsLabor.inputValue}");

                    if (dTotalPartsLabor != null && dTotalPartsLabor.compareTo(dOVER) >= 0) {
                        //Parts & Labor amount exceed criteria
                        //set font color
                        //this.totalPartslaborText.setContentStyle("color: Red;");
                        return true;
                    }
                } else if (sUOM.equals("H")) {
                    //By Hour
                    //Get Total Hours
                    String sTotalHrs = (String)ADFUtil.evaluateEL("#{bindings.TotalHours.inputValue}");
                    Double dTotalHrs = Double.parseDouble(sTotalHrs);
                    if (dTotalHrs != null && dTotalHrs.compareTo(dOVER) >= 0) {
                        //Hour amount exceed criteria
                        //set font color
                        //this.totalHoursText.setContentStyle("color: Red;");
                        return true;
                    }
                }
            } else {
                //Estimate required, but WO does not meet Criteria(Estimate not required)
                return false;
            }
        }
        return result;
    }

    public Boolean isEstimateRequired() {
        Boolean sTotalHoursOver = (Boolean)ADFUtil.evaluateEL("#{bindings.isTotalHourOver.inputValue}");
        Boolean sTotalPartsLaborOver = (Boolean)ADFUtil.evaluateEL("#{bindings.isTotalPartsLaborOver.inputValue}");
        if (sTotalHoursOver || sTotalPartsLaborOver) {
            return true;
        }
        return false;
    }

    public Boolean SendEstimateThruEDIFlg() {
        OperationBinding operationBinding = getOperationBinding("SendEstimateThruEDIFlg");
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public Boolean SendEstimateExceedAlert() {
        OperationBinding operationBinding = getOperationBinding("SendEstimateExceedAlert");
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public String getEstimateCriteria() {
        OperationBinding operationBinding = getOperationBinding("getEstimateCriteria");
        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public String getMP5Profile(String sprofile) {
        OperationBinding operationBinding = getOperationBinding("getMP5Profile");
        operationBinding.getParamsMap().put("sprofile", sprofile);
        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        ////System.out.println("Inside MNRDatacontrol method getMP5Profile sprofile: "+sprofile+" result: "+result);
        return result;
    }

    public Boolean sendEstimateFailure(String toaddress, String subject, String body) {
  //// public void sendEstimateFailure(String toaddress, String subject, String body) {
       System.out.println("Inside sendEstimateFailure toaddress: "+toaddress+" subject: "+subject+" body: "+body);
        OperationBinding operationBinding = getOperationBinding("sendEstimateFailure");
        operationBinding.getParamsMap().put("toaddress", toaddress);
        operationBinding.getParamsMap().put("subject", subject);
        operationBinding.getParamsMap().put("body", body);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
      //// operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public Boolean deleteEstimate(Number id) {
        OperationBinding operationBinding = getOperationBinding("deleteEstimate");
        operationBinding.getParamsMap().put("id", id);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public String ITS_EstimateIns(String WOEventCode, String WOAcctNum) {
        OperationBinding operationBinding = getOperationBinding("ITS_EstimateIns");
        operationBinding.getParamsMap().put("WOEventCode", WOEventCode);
        operationBinding.getParamsMap().put("WOAcctNum", WOAcctNum);
        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }
    /*
    public Boolean updateWOStatus(String newSts) {
        OperationBinding operationBinding = getOperationBinding("updateWOStatus");
        operationBinding.getParamsMap().put("newSts", newSts);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }
    */
    ///*

    public Boolean updateWOStatus(String newSts) {
        DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.WOEventsIterator}");
        ViewObject uv = dc.getViewObject();
        //printStatus("setAttribute:");
        uv.getCurrentRow().setAttribute("EvtStatus", newSts);
        //uv.getCurrentRow().setAttribute("EvtRstatus", newSts);
        //uv.executeQuery();
        //Key skey = uv.getCurrentRow().getKey();
        //uv.executeQuery();
        //impl.setCurrentRow(impl.findByKey(skey , 1)[0]);
        //uv.setCurrentRow(uv.findByKey(skey , 1)[0]);

        //refreshVO();
        //printStatus("after setAttribute");
        return true;
    }
    //*/

    public Boolean updateWOEstId(String sestid) {
  ////      if(sestid!=null){
            OperationBinding operationBinding = getOperationBinding("setEstId");
            operationBinding.getParamsMap().put("sestid", sestid);
            //invoke method
            Boolean result = (Boolean)operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                //check errors
                List errors = operationBinding.getErrors();
            }
            return result;
        }
  ////      return false;
  ////  }

    public String updateEstimateStatus(String pStatus) {

        String sUser = ADFContext.getCurrent().getSecurityContext().getUserName();
        String sEstid = (String)ADFUtil.evaluateEL("#{bindings.ItsEstid.inputValue}");
        OperationBinding operationBinding = getOperationBinding("ITS_EstimateUpd");
        operationBinding.getParamsMap().put("pEstId", sEstid);
        operationBinding.getParamsMap().put("pStatus", pStatus);
        operationBinding.getParamsMap().put("pUpdBy", sUser);
        operationBinding.getParamsMap().put("pAppNum", null);
        operationBinding.getParamsMap().put("pReason", null);
        operationBinding.getParamsMap().put("pRspType", "SYSTEM");
        operationBinding.getParamsMap().put("pIp", "127.0.0.1");
        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    /* BIT/PI Methods */

    public Boolean processBITPI() {
        Boolean processChk = false;
        String CHASSISJOBTYPE = "1";
        String sSendBITFIThruEDI = getMP5Profile("SendBITFHWAThruEDIFlg");
        System.out.println("Inside processBITPI() sSendBITFIThruEDI: "+sSendBITFIThruEDI);
        String sEvtJobtype = (String)ADFUtil.evaluateEL("#{bindings.EvtJobtype.inputValue}");
        Timestamp tEvtStart = (Timestamp)ADFUtil.evaluateEL("#{bindings.EvtStart.inputValue}");

        String sEvtStart = tEvtStart.stringValue();
        String sEvtDesc = (String)ADFUtil.evaluateEL("#{bindings.EvtDesc.inputValue}");

        //exit if Work Order Type is not Chassis, ChassisNum is blank, or Start Dt is blank
        if (!sEvtJobtype.equals(CHASSISJOBTYPE) || StringUtils.isBlank(sEvtDesc) || StringUtils.isBlank(sEvtStart))
            return true;

        //exit if SendBITFHWAThruEDIFlg is not Y
        if((sSendBITFIThruEDI!=null)&&(!sSendBITFIThruEDI.equals(null))){
            if (!sSendBITFIThruEDI.equals("Y")) {
                System.out.println("SendBITFHWAThruEDIFlg = " + sSendBITFIThruEDI);
                return true;
            }
        }
        String sBIT90TaskId = getMP5Profile("BIT90TaskId");
        String sPIFHWATaskId = getMP5Profile("PIFHWATaskId");
        String adminemail = getMP5Profile("AdminEmailAddress");
        System.out.println("Inside processBITPI adminemail: "+adminemail+" sBIT90TaskId: "+sBIT90TaskId+" sPIFHWATaskId: "+sPIFHWATaskId);
        /*
        System.out.println("Inside processBITPI adminemail: "+adminemail+" sEvtCode: 1026584");
        sendEstimateFailure("lakshmi.kumar@itslb.com", "Chassis BIT/FHWA I/F Error",
                            "Failed to update BIT/FHWA for WO#: 1026584");
        */
        DCIteratorBinding dciter = (DCIteratorBinding)getDCBindingContainer().get("WOActivitiesIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            String sTaskid = (String)row.getAttribute("ItsTaskid");
            System.out.println("ActAct ::" + row.getAttribute("ActAct"));
            System.out.println("ItsTaskid ::" + sTaskid);

            //call Web Service
            System.out.println("Before calling the webservices");
            if (sTaskid.equals(sBIT90TaskId)) {
                System.out.println("sBIT90TaskId");
                processChk = callBITWS(sEvtDesc, sEvtStart, "");
            } else if (sTaskid.equals(sPIFHWATaskId)) {
                System.out.println("sPIFHWATaskId");
                processChk = callBITWS(sEvtDesc, "", sEvtStart);
            }
            if (!processChk) {
                //Update failed. Email admin
                adminemail = getMP5Profile("AdminEmailAddress");
                String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
                System.out.println("Inside processBITPI adminemail: "+adminemail+" sEvtCode: "+sEvtCode);
                sendEstimateFailure(adminemail, "Chassis BIT/FHWA I/F Error",
                                    "Failed to update BIT/FHWA for WO#" + sEvtCode);
            }
        }

        return processChk;
    }

    public Boolean callBITWS(String pChassisNo, String pBit, String pFhwa) {
        //System.out.println("Inside callBITWS()");
        OperationBinding operationBinding = getOperationBinding("Update_BIT");
        operationBinding.getParamsMap().put("ChassisNo", pChassisNo);
        operationBinding.getParamsMap().put("Bit", pBit);
        operationBinding.getParamsMap().put("Fhwa", pFhwa);

        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    /* Activity Methods */

    public void addAct_action(ActionEvent actionEvent) {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            ADFContext.getCurrent().getPageFlowScope().put("disabled", true);
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            // go to WOTask
            //goToControlFlow("CreateInsert3",null,"launchWOTask");
            /*
            FacesContext context = FacesContext.getCurrentInstance();
            UIComponent source = (UIComponent) actionEvent.getSource();
            String alignId = source.getClientId(context);
            */

            //ADFContext.getCurrent().getPageFlowScope().put("actCount","displayCopyBttn");

            ADFContext.getCurrent().getPageFlowScope().put("disabled", true);
            OperationBinding operationBinding = getOperationBinding("CreateInsert3");
            operationBinding.execute();
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            /*
            hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID, source)
                .add(RichPopup.PopupHints.HintTypes.HINT_LAUNCH_ID, source)
                .add(RichPopup.PopupHints.HintTypes.HINT_ALIGN,
                     RichPopup.PopupHints.AlignTypes.ALIGN_AFTER_END);
            */
            woTaskPop.show(hints);
        }
    }

    public void editRecord_action(ActionEvent actionEvent) {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            ADFContext.getCurrent().getPageFlowScope().put("disabled", true);
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            // go to WOTask
            //goToControlFlow(null,null,"launchWOTask");
            chkSplitter();
            //call popup
            ADFContext.getCurrent().getPageFlowScope().put("disabled", false);
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            woTaskPop.show(hints);

        }

    }

    public void taskDescChangeListener(ValueChangeEvent valueChangeEvent) {
        //get current instance of change
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String task = (String)ADFUtil.evaluateEL("#{bindings.ItsTaskid1.inputValue}");
        //System.out.println("Inside taskDescChangeListener()");
        //System.out.println("taskid:" + task);
        //set default CEDEX Values
        setDefLocationCode(task);
        this.loccode.setReadOnly(isLocationCodeEditable());
        setDefDamageCode(task);
        this.damcode.setReadOnly(isDamageCodeEditable());
        setDefRepairCode(task, "TASK");
        this.repcode.setReadOnly(isRepairCodeEditable());
        FocusBean.setNextFocus(this.actHrs);
    }

    public void damCodeChangeListener(ValueChangeEvent valueChangeEvent) {
        //get current instance of change
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String taskdam = (String)ADFUtil.evaluateEL("#{bindings.ItsTaskdamid.inputValue}");
        //set default CEDEX Values
        setDefRepairCode(taskdam, "TASKDAMAGE");
        this.repcode.setReadOnly(isRepairCodeEditable());
    }

    public void setDefLocationCode(String taskid) {
        OperationBinding operationBinding = getOperationBinding("getDefLocCode");
        operationBinding.getParamsMap().put("taskid", taskid);
        //invoke method
        Object result = operationBinding.execute();

        ADFUtil.setEL("#{bindings.ItsLoccode.inputValue}", result);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.loccode);

        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        //System.out.println("Inside setDefLocationCode()");
    }

    public void setDefDamageCode(String taskid) {
        OperationBinding operationBinding = getOperationBinding("getDefDamCode");
        operationBinding.getParamsMap().put("taskid", taskid);
        //invoke method
        Object result = operationBinding.execute();

        ADFUtil.setEL("#{bindings.ItsDamcode.inputValue}", result);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.damcode);

        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        //System.out.println("Inside setDefDamageCode()");
    }

    public void setDefRepairCode(String taskdamid, String idtype) {
        OperationBinding operationBinding = getOperationBinding("getDefRepCode");
        operationBinding.getParamsMap().put("taskdamid", taskdamid);
        operationBinding.getParamsMap().put("idtype", idtype);
        //invoke method
        Object result = operationBinding.execute();

        ADFUtil.setEL("#{bindings.ItsRepcode.inputValue}", result);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.repcode);

        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        //System.out.println("Inside setDefRepairCode()");
    }

    public boolean isLocationCodeEditable() {
        OperationBinding operationBinding = getOperationBinding("isSingleDefaultLoc");
        //invoke method
        boolean result = (Boolean)operationBinding.execute();
        return result;
    }

    public boolean isDamageCodeEditable() {
        OperationBinding operationBinding = getOperationBinding("isSingleDefaultDam");
        //invoke method
        boolean result = (Boolean)operationBinding.execute();
        return result;
    }

    public boolean isRepairCodeEditable() {
        OperationBinding operationBinding = getOperationBinding("isSingleDefaultRep");
        //invoke method
        boolean result = (Boolean)operationBinding.execute();
        return result;
    }

    /* Misc Methods */

    public String CreateNewWO_action() {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            chkSplitter();
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            FocusBean.setNextFocus(this.equipDesc);
        }
        return null;
    }

    public void flagAssocTblRollback_action(ActionEvent ae) {

        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        BindingContainer bindings = getBindings();
        if (StringUtils.isBlank(sEvtCode)) {
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
        } else {

            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("WOEventsIterator");
            Row rowKey = iter.getCurrentRow();
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }

            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                //iter.executeQuery();
                try {
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                } catch (RowNotFoundException ex) {
                    System.out.println("RowNotFoundException");
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.flagAssoc);
        }
    }

    public String CreateNewTrackWO_action() {
        /*
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {*/
        chkSplitter();
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert2");
        Object result = operationBinding.execute();
        ADFUtil.setEL("#{bindings.EvtCostcode.inputValue}", "1197");
        ADFUtil.setEL("#{bindings.EvtJobtype1.inputValue}", "5");
        ADFUtil.setEL("#{bindings.EvtDesc.inputValue}", "Track Time");
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;

        /*
        OperationBinding oper = bindings.getOperationBinding("setNewTrackWOValues");
        Object res = oper.execute();
        if (!oper.getErrors().isEmpty()) {
            return null;
        }
        refreshCurrentPage();
        FocusBean.setNextFocus(this.equipDesc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.commbttn);
        */

    }

    public void ShowWOFlagAssociation_action(ActionEvent actionEvent) {
        String evtcode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        System.out.println("Inside WorkOrderBean ShowWOFlagAssociation_action evtcode: "+evtcode);
        if (isDirty()) {
            System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            chkSplitter();
            
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().containsKey("psfEvtcode")){
                AdfFacesContext.getCurrentInstance().getPageFlowScope().remove("psfEvtcode");
            }
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfEvtcode",evtcode);
            
            refreshFlagAssocVVO(evtcode);
            goToControlFlow(null,"goFlag");
        }
    }

    public void ShowWOFlagAssociation_action_orig(ActionEvent actionEvent) {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            chkSplitter();

            // FocusBean.setNextFocus(this.equipDesc);
            String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
            System.out.println("Inside ShowWOFlagAssociation_action sEvtCode: " + sEvtCode);

            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            flagAssoc.show(hints);
            refreshFlagAssocVVO(sEvtCode);
            BindingContainer bindings = getBindings();
            DCIteratorBinding fAIter = (DCIteratorBinding)bindings.get("FlagAssoc_VVO1Iterator");
            //Get viewObject from Iterator
            ViewObject fAVo = fAIter.getViewObject();
            RowSetIterator rsi = fAVo.createRowSetIterator(null);
            int rc = (int)fAVo.getEstimatedRowCount();
            System.out.println("Inside ShowWOFlagAssociation_action rc: " + rc);
            if (rc == 0) {

                OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertFlagAssocWO");
                Object result = operationBinding.execute();

                if (!operationBinding.getErrors().isEmpty()) {
                    return;
                }

            }
            rsi.closeRowSetIterator();
        }
        return;
    }

    public void refreshFlagAssocVVO(String sEvtCode) {
        DCIteratorBinding vvoIter = (DCIteratorBinding)getDCBindingContainer().get("FlagAssoc_VVO1Iterator");
        ViewObject vo = vvoIter.getViewObject();
        ViewCriteria vc = vo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        /**Set the values for ViewCriteriaRow*/
        vcRow.setAttribute("Faitem", sEvtCode);
        /**Add row to ViewCriteria*/
        vc.addRow(vcRow);
        /**Apply Criteria on ViewObject*/
        vo.applyViewCriteria(vc);
        /**Execute ViewObject*/
        vo.executeQuery();

        //   AdfFacesContext.getCurrentInstance().addPartialTarget(this.flagAssocTable);
    }

    public void CreateNewWOFlagAssociation_action(ActionEvent actionEvent) {

        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        BindingContainer bindings = getBindings();

        DCIteratorBinding iter = (DCIteratorBinding)bindings.get("ActiveMrltFlagIterator");
        //Get viewObject from Iterator
        iter.executeQuery();
        ViewObject activeVo = iter.getViewObject();

        RowSetIterator rsi = activeVo.createRowSetIterator(null);
        while (rsi.hasNext()) {
            //Get viewObject next row from RowSetIterator
            Row nextRow = rsi.next();
            String Fid = nextRow.getAttribute("Fid").toString();
            System.out.println("Inside CreateNewWOFlagAssociation_action Fid: " + Fid);
            OperationBinding oper = bindings.getOperationBinding("createFlagAssociation");
            oper.getParamsMap().put("fid", new Integer(Fid));
            oper.getParamsMap().put("faitem", sEvtCode);
            oper.getParamsMap().put("faactive", "N");
            oper.execute();

            if (!oper.getErrors().isEmpty()) {
                System.out.println("CreateNewWOFlagAssociation_action errors: " + oper.getErrors());
                return;
            }
        }
        rsi.closeRowSetIterator();
        //commit_action();
        OperationBinding opcommit = bindings.getOperationBinding("Commit");
        Object result = opcommit.execute();
        if (!opcommit.getErrors().isEmpty()) {
            return;
        }

        refreshFlagAssocVVO(sEvtCode);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.addFlagAssocWOBttn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.flagAssocTable);
        return;
    }


    public void SetWOFlagAssociation_action(ActionEvent ae) {
        BindingContainer bindings = getBindings();

        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("FlagAssoc_VVO1Iterator");

        Row rowKey = iter.getCurrentRow();
        if (rowKey != null) {
            OperationBinding oper = bindings.getOperationBinding("createFlagAssociation");
            oper.getParamsMap().put("fid", new Integer(rowKey.getAttribute("Fid").toString()));
            oper.getParamsMap().put("faitem", rowKey.getAttribute("Faitem").toString());
            oper.getParamsMap().put("faactive", rowKey.getAttribute("Faactive").toString());
            oper.execute();
        }

        OperationBinding opcommit = bindings.getOperationBinding("Commit");
        Object result = opcommit.execute();
        if (!opcommit.getErrors().isEmpty()) {
            return;
        }

        ViewObject vo = iter.getViewObject();
        ViewCriteria vc = vo.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        /**Set the values for ViewCriteriaRow*/
        vcRow.setAttribute("Faitem", rowKey.getAttribute("Faitem").toString());
        /**Add row to ViewCriteria*/
        vc.addRow(vcRow);
        /**Apply Criteria on ViewObject*/
        vo.applyViewCriteria(vc);
        /**Execute ViewObject*/
        vo.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.flagAssocTable);
    }


    /*
    public void SetWOFlagAssociation_action(ActionEvent ae) {

        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        HashMap<Integer, String> flagMap = new HashMap<Integer, String>();
        if (ADFContext.getCurrent().getPageFlowScope().containsKey("flagMap"))
            flagMap = (HashMap<Integer, String>)ADFContext.getCurrent().getPageFlowScope().get("flagMap");

        BindingContainer bindings = getBindings();
        OperationBinding OperationBinding = bindings.getOperationBinding("Execute");
        Object res = OperationBinding.execute();
        if (!OperationBinding.getErrors().isEmpty()) {
            return;
        }
        OperationBinding op = bindings.getOperationBinding("Execute1");
        Object resu = op.execute();
        if (!op.getErrors().isEmpty()) {
            return;
        }

        JUCtrlListBinding listBindings = (JUCtrlListBinding)getBindings().get("FlagAssoc_VVO1");
        Object str[] = listBindings.getSelectedValues();
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
            OperationBinding oper = bindings.getOperationBinding("createFlagAssociation");
            oper.getParamsMap().put("fid", new Integer(str[i].toString()));
            oper.getParamsMap().put("faitem", sEvtCode);
            oper.getParamsMap().put("faactive", "Y");
            oper.execute();
            flagMap.remove(Integer.parseInt(str[i].toString()));
            if (!oper.getErrors().isEmpty()) {
                System.out.println("createFlagAssociation errors: " + oper.getErrors());
                return;
            }
        }

        Iterator it = flagMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            // System.out.println(pair.getKey() + " = " + pair.getValue());
            OperationBinding oper = bindings.getOperationBinding("createFlagAssociation");
            oper.getParamsMap().put("fid", new Integer(pair.getKey().toString()));
            oper.getParamsMap().put("faitem", sEvtCode);
            oper.getParamsMap().put("faactive", "N");
            oper.execute();
        }
       // commit_action();
        ADFContext.getCurrent().getPageFlowScope().remove("flagMap");
        flagMap.clear();

    }
*/

    public void EstReqDiagListener(DialogEvent dialogEvent) {
        DialogEvent.Outcome response = dialogEvent.getOutcome();
        if (response == DialogEvent.Outcome.yes) {
            String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
            if(sEvtCode!=null){
                Boolean chk = processEstimateLogic(sEvtCode);
                //Everything is okay, Commit
                if (chk) {
                    OperationBinding operationBinding = getOperationBinding("Commit");
                    Object result = operationBinding.execute();
                    //refresh Iterator so WO Status can get new status flows
                    //refreshEventIter();
                }
            }
        }
    }

    public void pendingChangeDiagListener(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.yes) {
            //System.out.println("DialogEvent.Outcome.yes");
            commit_action();
        }
    }

    public void evtStatusChangeListener(ValueChangeEvent valueChangeEvent) {
        //if (isDirty()) {
        //System.out.println("Dirty");
        //call popup
        // String oldStatus = (String)valueChangeEvent.getOldValue();
        //this.evtStatus.setValue(oldStatus);
        //ADFUtil.setEL("#{bindings.EvtStatus.inputValue}", oldStatus);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.evtStatus);
        //RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //pendingChangePop.show(hints);
        FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        //}
    }

    public void goToControlFlow(String asaction, String soutcome) {
        //navigate to controlcase_to_follow"
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler nh = context.getApplication().getNavigationHandler();
        nh.handleNavigation(context, asaction, soutcome);
    }

    public void goToControlFlow(String sbinding, String asaction, String soutcome) {
        if (StringUtils.isNotBlank(sbinding)) {
            OperationBinding operationBinding = getOperationBinding(sbinding);
            operationBinding.execute();
        }
        //navigate to controlcase_to_follow"
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler nh = context.getApplication().getNavigationHandler();
        nh.handleNavigation(context, asaction, soutcome);
    }

    public void refreshEventIter() {
        //System.out.println("refreshEventIter()");
        // refresh the iterator
        Boolean exists=true;
       //     DCIteratorBinding iterQ = (DCIteratorBinding)getDCBindingContainer().get("WOEventsQueryIterator");
        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("WOEventsIterator");
        DCIteratorBinding iterAct = (DCIteratorBinding)getDCBindingContainer().get("WOActivitiesIterator");
        DCIteratorBinding iterMatlist = (DCIteratorBinding)getDCBindingContainer().get("MatlistsIterator");
        DCIteratorBinding iterMatlparts = (DCIteratorBinding)getDCBindingContainer().get("MatlpartsIterator");
        DCIteratorBinding iterCFields = (DCIteratorBinding)getDCBindingContainer().get("CustomFieldsIterator");
        DCIteratorBinding iterMeter = (DCIteratorBinding)getDCBindingContainer().get("MeterReadings1Iterator");
        DCIteratorBinding iterAssets = (DCIteratorBinding)getDCBindingContainer().get("AssetsIterator");
        DCIteratorBinding iterFlags = (DCIteratorBinding)getDCBindingContainer().get("ActiveMrltFlagIterator");
        DCIteratorBinding iterFlagAssoc =
            (DCIteratorBinding)getDCBindingContainer().get("MrltFlagassociation_VO1Iterator");
        DCIteratorBinding iterFlagpopup = (DCIteratorBinding)getDCBindingContainer().get("FlagAssoc_VVO1Iterator");
        //DCIteratorBinding iterEstimate = (DCIteratorBinding)getDCBindingContainer().get("EstimateIterator");
        Row rowKey = iter.getCurrentRow();
        if (rowKey != null) {
            Key parentKey = rowKey.getKey();
            if(parentKey!=null)
                try{
                    iter.getViewObject().applyViewCriteria(null);
                iter.executeQuery();
                iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
                catch (RowNotFoundException ex) {
                    System.out.println("Inside refreshEventIter");

                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }

            Row rowAct = iterAct.getCurrentRow();
            if (rowAct != null) {
                Key parentKeyAct = rowAct.getKey();
                iterAct.executeQuery();
                iterAct.setCurrentRowWithKey(parentKeyAct.toStringFormat(true));
            }
            // Key parentKeyMatlist = iterMatlist.getCurrentRow().getKey();
            Row rowMatlist = iterMatlist.getCurrentRow();
            if (rowMatlist != null) {
                Key parentKeyMatlist = rowMatlist.getKey();
                iterMatlist.executeQuery();
                iterMatlist.setCurrentRowWithKey(parentKeyMatlist.toStringFormat(true));
            }
    
            Row rowMatlparts = iterMatlparts.getCurrentRow();
            if (rowMatlparts != null) {
                Key parentKeyMatlparts = rowMatlparts.getKey();
                iterMatlparts.executeQuery();
                iterMatlparts.setCurrentRowWithKey(parentKeyMatlparts.toStringFormat(true));
            }
    
            Row rowCFields = iterCFields.getCurrentRow();
            if (rowCFields != null) {
                Key parentKeyCFields = rowCFields.getKey();
                iterCFields.executeQuery();
                iterCFields.setCurrentRowWithKey(parentKeyCFields.toStringFormat(true));
            }
    
            Row rowMeter = iterMeter.getCurrentRow();
            if (rowMeter != null) {
                Key parentKeyMeter = rowMeter.getKey();
                iterMeter.executeQuery();
                iterMeter.setCurrentRowWithKey(parentKeyMeter.toStringFormat(true));
            }
    
            Row rowAssets = iterAssets.getCurrentRow();
            if (rowAssets != null) {
                Key parentKeyAssets = rowAssets.getKey();
                iterAssets.executeQuery();
                iterAssets.setCurrentRowWithKey(parentKeyAssets.toStringFormat(true));
            }
    
            Row rowFlags = iterFlags.getCurrentRow();
            if (rowFlags != null) {
                Key parentKeyFlags = rowFlags.getKey();
                iterFlags.executeQuery();
                iterFlags.setCurrentRowWithKey(parentKeyFlags.toStringFormat(true));
            }
    
            Row rowFlagAssoc = iterFlagAssoc.getCurrentRow();
            if (rowFlagAssoc != null) {
                Key parentKeyFlagAssoc = rowFlagAssoc.getKey();
                iterFlagAssoc.executeQuery();
                iterFlagAssoc.setCurrentRowWithKey(parentKeyFlagAssoc.toStringFormat(true));
            }
    
            Row rowFlagpopup = iterFlagpopup.getCurrentRow();
            if (rowFlagpopup != null) {
                Key parentKeyFlagpopup = rowFlagpopup.getKey();
                iterFlagpopup.executeQuery();
                iterFlagpopup.setCurrentRowWithKey(parentKeyFlagpopup.toStringFormat(true));
            }
        /*
        Row rowEstimate = iterEstimate.getCurrentRow();
        if (rowEstimate != null) {
            Key parentKeyEstimate = rowEstimate.getKey();
            iterEstimate.executeQuery();
            iterEstimate.setCurrentRowWithKey(parentKeyEstimate.toStringFormat(true));
        }*/
        ////    }

        }

    public void refreshVO() {
        DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.WOEventsIterator}");
        ViewObject vo = dc.getViewObject();
        WOEventsImpl impl = (WOEventsImpl)vo;
        Key skey = impl.getCurrentRow().getKey();
        impl.executeQuery();
        //impl.setCurrentRow(impl.findByKey(skey , 1)[0]);
        impl.setCurrentRow(impl.findByKey(skey, 1)[0]);
        //vo.reset();

    }

    public String refreshWOStatusLOV() {
        //System.out.println("refreshVO()");

        DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.WOEventsIterator}");
        ViewObject vo = dc.getViewObject();
        Row row = vo.getCurrentRow();
        if (row != null) {
//            row.refresh(row.REFRESH_UNDO_CHANGES | row.REFRESH_WITH_DB_FORGET_CHANGES);
            dc.getDataControl().commitTransaction();
        }
        //RowSet rs = row.findOrCreateViewAccessorRS("WOStatus");
        //rs.getViewObject().clearCache();
        return null;
    }

    public void refreshQueryKeepingCurrentRow() {
        OperationBinding operationBinding = getOperationBinding("refreshQueryKeepingCurrentRow");
        //invoke method
        operationBinding.execute();
    }

    public void clearCache() {
        ApplicationModule am = getDCBindingContainer().getDataControl().getApplicationModule();
        am.getTransaction().clearEntityCache("R5events");
    }

    public String refreshCurrentPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String currentView = context.getViewRoot().getViewId();
        ViewHandler vh = context.getApplication().getViewHandler();
        UIViewRoot x = vh.createView(context, currentView);
        context.setViewRoot(x);
        return null;
    }

    public String refreshAll_action() {
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        BindingContainer bindings = getBindings();
        if (StringUtils.isNotBlank(sEvtCode)) {
            refreshEventIter();
        }

        return null;
    }

    public String refreshScreen_action() {
        refreshCurrentPage();
        return null;
    }

    public String commit_action() {
        Boolean processBITPIChk = true;
        Boolean updateEstimateChk = true;
        Boolean RejWOPartsReturnChk = true;
        Boolean enterEstimateChk = true;
        Boolean createEstimateInvoiceChk = true;
        Boolean writeEstCompXMLChk = true;
        Boolean flagAssocChk = true;

        String sAcctNum = (String)ADFUtil.evaluateEL("#{bindings.EvtCostcode2.inputValue}");
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        //Business rules by Work Order Status
        String sEvtSts = (String)ADFUtil.evaluateEL("#{bindings.EvtStatus1.inputValue}");
        String sEvtDesc = (String)ADFUtil.evaluateEL("#{bindings.EvtDesc.inputValue}");
        // Run BIT/FHWA logic
        if (sEvtSts.equals("CBM") || sEvtSts.equals("C")) {
            processBITPIChk = processBITPI();
        }
        //Update Estimate status to same
        if (sEvtSts.equals("UNA") || sEvtSts.equals("APP") || sEvtSts.equals("REJ")) {
            updateEstimateChk = updateEstimateStatus(sEvtSts).equals("TRUE") ? true : false;
        }
        //Rejected Work Orders should return all parts back to Stock.
        if (updateEstimateChk && sEvtSts.equals("REJ")) {
            RejWOPartsReturnChk = RejWOPartsReturn(sEvtCode);
            if (RejWOPartsReturnChk) {
                createEstimateInvoiceChk = createEstimateInvoice(sEvtCode);
            } else {
                //reset Estimate Status to OPEN. Notify user of error.
                updateEstimateStatus("OPEN");
                userModificationMsg("ERROR", "ERROR",
                                    "Error encountered while returning Part(s) to Stock. Please notify the Administrator.");
            }
        }

        if (!updateEstimateChk) {
            //reset Estimate Status to OPEN. Notify user of error.
            userModificationMsg("ERROR", "ERROR",
                                "Error encountered setting Pending WO status. Please notify the Administrator.");
        }

        // Run Estimate Completion logic
        if (sEvtSts.equals("C")) {
            writeEstCompXMLChk = writeEstimateCompletionXML(sAcctNum, sEvtCode);
            if (!writeEstCompXMLChk) {
                userModificationMsg("ERROR", "ERROR",
                                    "Error encountered setting Pending WO Completion status. Please notify the Administrator.");
            }
        }

        //Check to enter Estimate Logic
        if (RejWOPartsReturnChk && enterEstimateChk && createEstimateInvoiceChk) {
            enterEstimateChk = enterEstimateLogic(sEvtCode, sEvtSts);
        }

        if (updateEstimateChk && RejWOPartsReturnChk && enterEstimateChk && createEstimateInvoiceChk && flagAssocChk) {
            OperationBinding operationBinding = getOperationBinding("Commit");
            Object result = operationBinding.execute();

            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            //System.out.println("refreshing"); refreshEventIter();
            refreshAll_action();
            refreshCurrentPage();
        }
        return null;
    }

    /*
    public void printStatus(String sid){
            String sEvtSts2 = (String)ADFUtil.evaluateEL("#{bindings.EvtStatus.inputValue}");
            String sEvtSts3 = (String)ADFUtil.evaluateEL("#{bindings.EvtStatus.attributeValue}");
            String sEvtRSts2 = (String)ADFUtil.evaluateEL("#{bindings.EvtRstatus.inputValue}");
            String sEvtRSts3 = (String)ADFUtil.evaluateEL("#{bindings.EvtRstatus.attributeValue}");
            //System.out.println(sid + "Status:" + sEvtSts2+ ":" + sEvtSts3);
            //System.out.println(sid + "RStatus:" + sEvtRSts2+ ":" + sEvtRSts3);
        }
    */

    public String onRollback() {
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        BindingContainer bindings = getBindings();
        if (StringUtils.isBlank(sEvtCode)) {
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {

            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("WOEventsIterator");
            // DCIteratorBinding iterAct = (DCIteratorBinding)getDCBindingContainer().get("WOActivitiesIterator");
            // DCIteratorBinding iterMatlist = (DCIteratorBinding)getDCBindingContainer().get("MatlistsIterator");
            // DCIteratorBinding iterMatlparts = (DCIteratorBinding)getDCBindingContainer().get("MatlpartsIterator");
            //DCIteratorBinding iterCFields = (DCIteratorBinding)getDCBindingContainer().get("CustomFieldsIterator");
            //DCIteratorBinding iterMeter = (DCIteratorBinding)getDCBindingContainer().get("MeterReadings1Iterator");
            //DCIteratorBinding iterAssets = (DCIteratorBinding)getDCBindingContainer().get("AssetsIterator");

            Row rowKey = iter.getCurrentRow();
            // Row rowAct = iterAct.getCurrentRow();
            // Row rowMatlist = iterMatlist.getCurrentRow();
            // Row rowMatlparts = iterMatlparts.getCurrentRow();
            // Row rowCFields = iterCFields.getCurrentRow();
            // Row rowMeter = iterMeter.getCurrentRow();
            // Row rowAssets = iterAssets.getCurrentRow();
            // Row rowEstimate = iterEstimate.getCurrentRow();

            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }

            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                //iter.executeQuery();
                try {
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                } catch (RowNotFoundException ex) {
                    System.out.println("RowNotFoundException");
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
            /*
            if (rowAct != null) {
                Key parentKeyAct = rowAct.getKey();
                iterAct.executeQuery();
                iterAct.setCurrentRowWithKey(parentKeyAct.toStringFormat(true));
            }

            if (rowMatlist != null) {
                Key parentKeyMatlist = rowMatlist.getKey();
                iterMatlist.executeQuery();
                iterMatlist.setCurrentRowWithKey(parentKeyMatlist.toStringFormat(true));
            }

            if (rowMatlparts != null) {
                Key parentKeyMatlparts = rowMatlparts.getKey();
                iterMatlparts.executeQuery();
                iterMatlparts.setCurrentRowWithKey(parentKeyMatlparts.toStringFormat(true));
            }

            if (rowCFields != null) {
                Key parentKeyCFields = rowCFields.getKey();
                iterCFields.executeQuery();
                iterCFields.setCurrentRowWithKey(parentKeyCFields.toStringFormat(true));
            }

            if (rowMeter != null) {
                Key parentKeyMeter = rowMeter.getKey();
                iterMeter.executeQuery();
                iterMeter.setCurrentRowWithKey(parentKeyMeter.toStringFormat(true));
            }

            if (rowAssets != null) {
                Key parentKeyAssets = rowAssets.getKey();
                iterAssets.executeQuery();
                iterAssets.setCurrentRowWithKey(parentKeyAssets.toStringFormat(true));
            }

            if (rowEstimate != null) {
                Key parentKeyEstimate = rowEstimate.getKey();
                iterEstimate.executeQuery();
                iterEstimate.setCurrentRowWithKey(parentKeyEstimate.toStringFormat(true));
            }
*/
        }


        //refresh view
        refreshCurrentPage();
        return null;
    }

    public boolean isDirty() {
        ApplicationModule am = getDCBindingContainer().getDataControl().getApplicationModule();
        Boolean transDirty = am.getTransaction().isDirty();

        BindingContext bc = getBindingContext();
        String dataControl = getBindingContext().getCurrentDataControlFrame();
        Boolean bindingsChanged = bc.findDataControlFrame(dataControl).isTransactionDirty();

        if (transDirty || bindingsChanged) {
            return true;
        }
        return false;
    }

    public Boolean RejWOPartsReturn(String pEvtCode) {
        //System.out.println("Inside callBITWS()");
        Boolean ret = false;
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        OperationBinding operationBinding = getOperationBinding("RejWOPartsReturn");
        operationBinding.getParamsMap().put("pEvtCode", pEvtCode);

        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        } else if (result.equals("PASS")) {
            ret = true;
        }
        return ret;
    }

    public void createEstimateWO_action(ActionEvent ae){
        if (isDirty()) {
            System.out.println("Inside createEstimateWO_action isDirty");
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            if (ADFContext.getCurrent().getPageFlowScope().containsKey("psfEstid"))
                System.out.println("Inside createEstimateWO_action psfEstid: "+ADFContext.getCurrent().getPageFlowScope().get("psfEstid"));
           // ADFContext.getCurrent().getPageFlowScope().remove("disabled");
            
            if (ADFContext.getCurrent().getPageFlowScope().containsKey("pfsCalledFromWO"))
                System.out.println("Inside createEstimateWO_action pfsCalledFromWO: "+ADFContext.getCurrent().getPageFlowScope().get("pfsCalledFromWO"));
               // ADFContext.getCurrent().getPageFlowScope().remove("disabled");
           // ADFContext.getCurrent().getPageFlowScope().remove("disabled");
            
            DCIteratorBinding iterEstimate = (DCIteratorBinding)getDCBindingContainer().get("EstimateIterator");
            if(iterEstimate!=null){
                iterEstimate.executeQuery();
            }
            
            
            goToControlFlow(null,"callWOEstimates");
        }
        
    }

    public Boolean createEstimateInvoice(String pEvtCode) {
        //System.out.println("Inside callBITWS()");
        Boolean ret = false;
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        OperationBinding operationBinding = getOperationBinding("createEstimateInvoice");
        operationBinding.getParamsMap().put("pEvtCode", pEvtCode);

        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        } else if (result.equals("PASS")) {
            ret = true;
        }
        return ret;
    }

    public Boolean writeEstimateCompletionXML(String sacctnum, String sevtcode) {
        //System.out.println("Inside writeEstimateCompletionXML()");
        Boolean ret = false;
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        OperationBinding operationBinding = getOperationBinding("writeEstimateCompletionXML");
        operationBinding.getParamsMap().put("sacctnum", sacctnum);
        operationBinding.getParamsMap().put("sevtcode", sevtcode);

        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        } else
            ret = true;
        return ret;
    }

    public void customFieldsActionListener(ActionEvent actionEvent) {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            customFieldsPop.show(hints);
        }
    }

    public void meterReadingActionListener(ActionEvent actionEvent) {
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            meterReadingPop.show(hints);
        }
    }

    /* Custom Validators */

    public void partQty_validator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        FacesContext fc = FacesContext.getCurrentInstance();
        DCIteratorBinding iterBind = (DCIteratorBinding)getDCBindingContainer().get("MatlpartsIterator");
        Number nAvail = (Number)iterBind.getCurrentRow().getAttribute("Available");
        Number nPostedQty = (Number)iterBind.getCurrentRow().getAttribute("PostedMlpQty");
        Number qty = (Number)object;
        Number difQty = new oracle.jbo.domain.Number(0);
        Boolean isNew = false;
        Boolean throwError = false;
        String error = "";

        if (nPostedQty == null) {
            nPostedQty = new oracle.jbo.domain.Number(0);
            isNew = true;
        }
        if (qty.compareTo(0) <= 0) {
            error = "Quantity requested must be greater than 0";
            throwError = true;
        } else {
            difQty = qty.subtract(nPostedQty);
        }
        //System.out.println("difQty:" + difQty);

        if (!throwError && qty.compareTo(nPostedQty) > 0) { //More qty is requested
            if (difQty.compareTo(nAvail) <= 0) {
                //Additional qty requested is less than available stock
            } else {
                error = (!isNew ? "Additional " : "") + difQty + " requested is greater than available stock on hand";
                throwError = true;
            }
        }
        if (throwError) {
            //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, error));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, error);
            fc.addMessage(uIComponent.getClientId(fc), message);
        }
    }


    /* Control Bindings */

    public void setJobTypeLOV(RichInputComboboxListOfValues jobTypeLOV) {
        this.jobTypeLOV = jobTypeLOV;
    }

    public RichInputComboboxListOfValues getJobTypeLOV() {
        return jobTypeLOV;
    }

    public void setCostCodeLOV(RichInputComboboxListOfValues costCodeLOV) {
        this.costCodeLOV = costCodeLOV;
    }

    public RichInputComboboxListOfValues getCostCodeLOV() {
        return costCodeLOV;
    }

    public void setIsAssetChkBox(RichSelectBooleanCheckbox isAssetChkBox) {
        this.isAssetChkBox = isAssetChkBox;
    }

    public RichSelectBooleanCheckbox getIsAssetChkBox() {
        return isAssetChkBox;
    }

    public void setActMatList(RichInputText actMatList) {
        this.actMatList = actMatList;
    }

    public RichInputText getActMatList() {
        return actMatList;
    }

    public void setEstReqPop(RichPopup estReqPop) {
        this.estReqPop = estReqPop;
    }

    public RichPopup getEstReqPop() {
        return estReqPop;
    }

    public void setPendingChangePop(RichPopup pendingChangePop) {
        this.pendingChangePop = pendingChangePop;
    }

    public RichPopup getPendingChangePop() {
        return pendingChangePop;
    }

    public void setLoccode(RichSelectOneChoice loccode) {
        this.loccode = loccode;
    }

    public RichSelectOneChoice getLoccode() {
        return loccode;
    }

    public void setDamcode(RichSelectOneChoice damcode) {
        this.damcode = damcode;
    }

    public RichSelectOneChoice getDamcode() {
        return damcode;
    }

    public void setRepcode(RichSelectOneChoice repcode) {
        this.repcode = repcode;
    }

    public RichSelectOneChoice getRepcode() {
        return repcode;
    }

    public void setEvtStatus(RichSelectOneChoice evtStatus) {
        this.evtStatus = evtStatus;
    }

    public RichSelectOneChoice getEvtStatus() {
        return evtStatus;
    }

    public void setMeterReadingPop(RichPopup meterReadingPop) {
        this.meterReadingPop = meterReadingPop;
    }

    public RichPopup getMeterReadingPop() {
        return meterReadingPop;
    }

    public void setCustomFieldsPop(RichPopup customFieldsPop) {
        this.customFieldsPop = customFieldsPop;
    }

    public RichPopup getCustomFieldsPop() {
        return customFieldsPop;
    }

    public void setPartsForm(RichPanelFormLayout partsForm) {
        this.partsForm = partsForm;
    }

    public RichPanelFormLayout getPartsForm() {
        return partsForm;
    }

    public void setWoTaskPop(RichPopup woTaskPop) {
        this.woTaskPop = woTaskPop;
    }

    public RichPopup getWoTaskPop() {
        return woTaskPop;
    }

    public String woTaskRollback_action() {
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        // System.out.println(sEvtCode);
        BindingContainer bindings = getBindings();
        if (StringUtils.isBlank(sEvtCode)) {
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {

            DCIteratorBinding iter = (DCIteratorBinding)bindings.get("WOEventsIterator");
            DCIteratorBinding iterAct = (DCIteratorBinding)bindings.get("WOActivitiesIterator");
            // DCIteratorBinding iterMatlist = (DCIteratorBinding)getDCBindingContainer().get("MatlistsIterator");
            //DCIteratorBinding iterMatlparts = (DCIteratorBinding)getDCBindingContainer().get("MatlpartsIterator");

            Key rowKey = iter.getCurrentRow().getKey();
            Key rowAct = iterAct.getCurrentRow().getKey();
            // Row rowMatlist = iterMatlist.getCurrentRow();
            // Row rowMatlparts = iterMatlparts.getCurrentRow();

            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            //use row key back to preserve row selection
            if (rowKey != null) {
                try {
                    iter.setCurrentRowWithKey(rowKey.toStringFormat(true));
                } catch (RowNotFoundException ex) {
                    System.out.println("Event RowNotFoundException");
                }

                if (rowAct != null) {
                    try {
                        iterAct.executeQuery();
                        if(iterAct.containsKey(rowAct.toStringFormat(true))){
                            iterAct.setCurrentRowWithKey(rowAct.toStringFormat(true));
                        }
                    } catch (RowNotFoundException ex) {
                        System.out.println("Act RowNotFoundException");
                    }
                }
            }
        }
        return null;
    }

    public static void closePopup(String popupId) {
        FacesContext context = FacesContext.getCurrentInstance();

        ExtendedRenderKitService extRenderKitSrvc =
            Service.getRenderKitService(context, ExtendedRenderKitService.class);
        extRenderKitSrvc.addScript(context, "AdfPage.PAGE.findComponent('" + popupId + "').hide();");
    }

    public void woTaskClose_action() {
        // Add event code here...
        //closePopup("pWOTask");
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
            return;
        }
        RichPopup rp = (RichPopup)woTaskDialog.getParent();
        rp.hide();
    }
    /*
    public void flagPPRollbackClose_action() {
        System.out.println("Inside flagPPRollbackClose_action");
        if (isDirty()) {
            BindingContainer bindings = getBindings();
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
        }
        flagAssoc.hide();
    }

    public void flagPPClose_action() {
        if (isDirty()) {
            String sEvtSts = (String)ADFUtil.evaluateEL("#{bindings.EvtStatus1.inputValue}");
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
            return;
        }
        flagAssoc.hide();
    }
*/

    public void flagPPClose_action() {
        
        if (isDirty()) {

            String sEvtSts = (String)ADFUtil.evaluateEL("#{bindings.EvtStatus1.inputValue}");
            BindingContainer bindings = getBindings();
            DCIteratorBinding iter = (DCIteratorBinding)bindings.get("FlagAssoc_VVO1Iterator");
            RowSetIterator rsi = iter.getRowSetIterator();
            int rc = 0;
            rc = (int)rsi.getFetchedRowCount();
            System.out.println("Inside ShowWOFlagAssociation_action rc: " + rc);
            
            if (((ADFContext.getCurrent().getSecurityContext().isUserInRole("appAdmin")) ||
                (ADFContext.getCurrent().getSecurityContext().isUserInRole("appIAMForeman")) ||
                (ADFContext.getCurrent().getSecurityContext().isUserInRole("appIAMMechanic")) ||
                (ADFContext.getCurrent().getSecurityContext().isUserInRole("appIAMPart")) ||
                (ADFContext.getCurrent().getSecurityContext().isUserInRole("appR5"))) 
                &&
                (sEvtSts.equals("OPEN") || sEvtSts.equals("RBF") || sEvtSts.equals("CRV"))
                &&
                (rc > 0)) {
                System.out.println("Inside flagPPClose_action 1");
                userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
                return;
            } else {
                System.out.println("Inside flagPPClose_action 2");
                
                // perform rollback operation
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                Object result = operationBinding.execute();
                if (!operationBinding.getErrors().isEmpty()) {
                    return;
                }
            }

        }
        System.out.println("Inside flagPPClose_action userRole 1: "+ADFContext.getCurrent().getSecurityContext().getUserRoles()[0]+" role 2: "+
                           ADFContext.getCurrent().getSecurityContext().getUserRoles()[1]+" role 3: "+ADFContext.getCurrent().getSecurityContext().getUserRoles()[2]);
        flagAssoc.hide();

    }

    public void setWoTaskDialog(RichDialog woTaskDialog) {
        this.woTaskDialog = woTaskDialog;
    }

    public RichDialog getWoTaskDialog() {
        return woTaskDialog;
    }

    public void setPartlov(RichInputListOfValues partlov) {
        this.partlov = partlov;
    }

    public RichInputListOfValues getPartlov() {
        return partlov;
    }

    public void partChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        // finally, update dependent components
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.partDesc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.partQty);
        this.partQty.setValue(1);
        FocusBean.setNextFocus(this.partQty);
    }

    public void setPartQty(RichInputText partQty) {
        this.partQty = partQty;
    }

    public RichInputText getPartQty() {
        return partQty;
    }

    public void setPartDesc(RichInputText partDesc) {
        this.partDesc = partDesc;
    }

    public RichInputText getPartDesc() {
        return partDesc;
    }

    public void setActHrs(RichInputText actHrs) {
        this.actHrs = actHrs;
    }

    public RichInputText getActHrs() {
        return actHrs;
    }

    public void setEquipDesc(RichInputText equipDesc) {
        this.equipDesc = equipDesc;
    }

    public RichInputText getEquipDesc() {
        return equipDesc;
    }

    public void setTaskLOV(RichInputListOfValues taskLOV) {
        this.taskLOV = taskLOV;
    }

    public RichInputListOfValues getTaskLOV() {
        return taskLOV;
    }

    public void handleTaskDoubleClick(ClientEvent clientEvent) {

        if (!this.editRecordBtn.isDisabled()) {
            RichPopup popup = this.getWoTaskPop();
            //no hints means that popup is launched in the
            //center of the page
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            popup.show(ph);
        }

    }

    public void setEditRecordBtn(RichButton editRecordBtn) {
        this.editRecordBtn = editRecordBtn;
    }

    public RichButton getEditRecordBtn() {
        return editRecordBtn;
    }

    public void setLhsSplitter(RichPanelSplitter lhsSplitter) {
        this.lhsSplitter = lhsSplitter;
    }

    public RichPanelSplitter getLhsSplitter() {
        return lhsSplitter;
    }

    public boolean isSplitterClosed() {
        return lhsSplitter.isCollapsed();
    }

    public void chkSplitter() {
        if (!lhsSplitter.isCollapsed()) {
            lhsSplitter.setCollapsed(true);
        }
    }

    public void setTaskSOC(RichSelectOneChoice taskSOC) {
        this.taskSOC = taskSOC;
    }

    public RichSelectOneChoice getTaskSOC() {
        return taskSOC;
    }

    public String WOTaskCommit_action() {
        /*
        boolean error = false;

        if (this.getActHrs().getValue() == null) {
            userModificationMsg("WARN", "Warning",  "Hrs is required.");
        }

        if (this.getActMechIc().getValue() == null) {
            //userModificationMsg("WARN", "Warning",  "Mech is required.");
        }

        if (error) {
            return null;
        }
*/
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        ADFContext.getCurrent().getPageFlowScope().put("disabled", false);
        if (copyWOTaskBttn != null)
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.copyWOTaskBttn);
        return null;
    }

    public void setActMechIc(RichInputText actMechIc) {
        this.actMechIc = actMechIc;
    }

    public RichInputText getActMechIc() {
        return actMechIc;
    }

    public void setAssetUom(String AssetUom) {
        this.AssetUom = AssetUom;
    }

    public String getAssetUom() {
        return AssetUom;
    }

    public void setFlagAssoc(RichPopup flagAssoc) {
        this.flagAssoc = flagAssoc;
    }

    public RichPopup getFlagAssoc() {
        return flagAssoc;
    }

    public void setFlagsVals(RichSelectManyCheckbox flagsVals) {
        this.flagsVals = flagsVals;
    }

    public RichSelectManyCheckbox getFlagsVals() {
        return flagsVals;
    }

    public void setEvtClass(RichInputComboboxListOfValues evtClass) {
        this.evtClass = evtClass;
    }

    public RichInputComboboxListOfValues getEvtClass() {
        return evtClass;
    }

    public void setCommbttn(RichButton commbttn) {
        this.commbttn = commbttn;
    }

    public RichButton getCommbttn() {
        return commbttn;
    }


    public void vcl(ValueChangeEvent v) {
        System.out.println("Inside v");

        Object[] objArr = (Object[])v.getNewValue();
        for (int x = 0; x < objArr.length; x++) {
            Object obj = objArr[x];
            System.out.println(obj.toString());

        }

    }

    public void setCopyWOTaskBttn(RichButton copyWOTaskBttn) {
        this.copyWOTaskBttn = copyWOTaskBttn;
    }

    public RichButton getCopyWOTaskBttn() {
        return copyWOTaskBttn;
    }

    /**Method to set value in Expression (EL)
     * @param el
     * @param val
     */


    public void copyWOTask_action(ActionEvent ae) {
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        int i = ((Number)ADFUtil.evaluateEL("#{bindings.ActAct.attributeValue}")).intValue();
        Integer actAct = new Integer(i);
        System.out.println("actAct: " + actAct.intValue());
        String actMatlist = "";
        actMatlist = (String)ADFUtil.evaluateEL("#{bindings.ActMatlist.inputValue}");
        
        String pOrg = "";
        pOrg = (String)ADFUtil.evaluateEL("#{bindings.EvtOrg.inputValue}");

        System.out.println("last act_act: " + actAct);
        String traauth = "";
        traauth = ADFContext.getCurrent().getSecurityContext().getUserName().toUpperCase();
        System.out.println("traauth: " + traauth);
        String pUser = "";
         pUser = ADFContext.getCurrent().getSecurityContext().getUserName().toString();
       
       /************************************/
        BindingContainer bindings = getBindings();  
        DCIteratorBinding partIter = (DCIteratorBinding)bindings.get("MatlpartsIterator");
        //Get viewObject from Iterator
        ViewObject partVo = partIter.getViewObject();
        RowSetIterator partrsi = partVo.createRowSetIterator(null);
        System.out.println("partrsi.getRowCount(): " + partrsi.getRowCount());
        Row currPartsRow = null;
        while(partrsi.hasNext()) {
            currPartsRow = partrsi.next();
            System.out.println("Parts :" +currPartsRow.getAttribute("MlpPart"));
            System.out.println("MLP Line :" +currPartsRow.getAttribute("MlpLine"));
            System.out.println("Bin :" +currPartsRow.getAttribute("ItsBin"));
            System.out.println("Lot :" +currPartsRow.getAttribute("ItsLot"));       
            
            OperationBinding operationBinding = getOperationBinding("AvailableQtyExecute");
            /*
            operationBinding.getParamsMap().put("part", currPartsRow.getAttribute("MlpPart"));
            operationBinding.getParamsMap().put("bin", currPartsRow.getAttribute("ItsBin"));
            operationBinding.getParamsMap().put("lot", currPartsRow.getAttribute("ItsLot"));
*/
            Object result = operationBinding.execute();
            System.out.println("result: " + result);

            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
          //  AttributeBinding attr = (AttributeBinding)getBindings().getControlBinding("AvailableQty");
            
          DCIteratorBinding availIter = (DCIteratorBinding)bindings.get("AvailableQtyExistence1Iterator");
          //Get viewObject from Iterator
          ViewObject availVo = availIter.getViewObject();
          RowSetIterator availrsi = availVo.createRowSetIterator(null);
          System.out.println("availrsi.getRowCount(): " + availrsi.getRowCount());
            
                System.out.println("Inside if(availrsi.getRowCount() > 1)");
                while (availrsi.hasNext()) {
                    System.out.println("Inside while (availrsi.hasNext())");
                   Row availRow = availrsi.next();
                 
                    if(availRow != null){
                        System.out.println("Inside if(availrsi.getRowCount() == 1)if(availRow != null)");
                        System.out.println("Inside if(availrsi.getRowCount() == 1)if(availRow != null) availRow.getAttribute(\"Availqty\").toString(): "+availRow.getAttribute("Availqty").toString());
                        
                        Object[] values = availRow.getAttributeValues();
                        if (availRow.getAttributeIndexOf("Availqty") != -1) {
                            System.out.println("no Available quantity: "); 
                            int indAvailable= availRow.getAttributeIndexOf("Availqty");
                            String Available = null;
                            if (values[indAvailable] != null) {
                                 Available = availRow.getAttribute("Availqty").toString();
                                System.out.println("Available quantity: "+Available); 
                            }
                        }
                }
            }
                
             availrsi.closeRowSetIterator();
             
                      
        }      
        partrsi.closeRowSetIterator();    
         
        /************************************/
        /* main code to CopyWOTask please uncomment later*/
        OperationBinding operationBinding = getOperationBinding("CopyWOTask");
        operationBinding.getParamsMap().put("ACTEVENT", sEvtCode);
        operationBinding.getParamsMap().put("ACTACT", actAct);
        operationBinding.getParamsMap().put("ACTMATLIST", actMatlist);
        operationBinding.getParamsMap().put("pUser", pUser);
        operationBinding.getParamsMap().put("pOrg", pOrg);
        Object result = operationBinding.execute();
        System.out.println("result: " + result);

        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }

      /*  main code to CopyWOTask please uncomment later*/
        
        
       // refreshEventIter();
      //  BindingContainer bindings = getBindings();
        OperationBinding op = getOperationBinding("Commit");
        Object resop = op.execute();
        if (!op.getErrors().isEmpty()) {
            return;
        }


        DCIteratorBinding actIter = (DCIteratorBinding)bindings.get("WOActivitiesIterator");
        actIter.executeQuery();
        //Get viewObject from Iterator
        ViewObject actVo = actIter.getViewObject();
        RowSetIterator rsi = actVo.createRowSetIterator(null);
        System.out.println("rsi.getRowCount(): " + rsi.getRowCount());
        // int rowCount = (int)actVo.getEstimatedRowCount();
        Row r = rsi.last();
        if (r != null) {
            List rowKeyList = new ArrayList();
            Key jboKey = new Key(new Object[] { r.getAttribute("ActEvent"), r.getAttribute("ActAct") });
            rowKeyList.add(jboKey);

            RowKeySet newRks = new RowKeySetImpl();
            newRks.add(rowKeyList);
            //Create SelectionEvent Object and put necessary values
            SelectionEvent selectionEvent =
                new SelectionEvent(woActivitiesTable.getSelectedRowKeys(), newRks, woActivitiesTable);
            //Queue Selection Event
            selectionEvent.queue();
            DCIteratorBinding listIter = (DCIteratorBinding)bindings.get("MatlistsIterator");
            //Get viewObject from Iterator
            ViewObject listVo = listIter.getViewObject();
            RowSetIterator listrsi = listVo.createRowSetIterator(null);
            System.out.println("listrsi.getRowCount(): " + listrsi.getRowCount());
            Row rlist = listrsi.last();

            DCIteratorBinding MatlPartIter = (DCIteratorBinding)bindings.get("MatlpartsIterator");
            //Get viewObject from Iterator
            ViewObject MatlPartVo = MatlPartIter.getViewObject();
            RowSetIterator MatlPartrsi = MatlPartVo.createRowSetIterator(null);
            System.out.println("MatlPartrsi.getRowCount(): " + MatlPartrsi.getRowCount());
            Row rpart = MatlPartrsi.last();

            System.out.println("actAct: " + ((Number)r.getAttribute("ActAct")).intValue());

            OperationBinding opcommit = getOperationBinding("Commit");
            Object rcommit = opcommit.execute();

            if (!opcommit.getErrors().isEmpty()) {
                return;
            }
            listrsi.closeRowSetIterator();
        }
    }

    public void woTaskPPCancel_action(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
        if (ADFContext.getCurrent().getPageFlowScope().containsKey("disabled"))
            ADFContext.getCurrent().getPageFlowScope().remove("disabled");
    }

    public void setAddNewWOTaskBttn(RichButton addNewWOTaskBttn) {
        this.addNewWOTaskBttn = addNewWOTaskBttn;
    }

    public RichButton getAddNewWOTaskBttn() {
        return addNewWOTaskBttn;
    }

    public void setApplyWOTaskBttn(RichButton applyWOTaskBttn) {
        this.applyWOTaskBttn = applyWOTaskBttn;
    }

    public RichButton getApplyWOTaskBttn() {
        return applyWOTaskBttn;
    }

    public void setCancelWOTaskBttn(RichButton cancelWOTaskBttn) {
        this.cancelWOTaskBttn = cancelWOTaskBttn;
    }

    public RichButton getCancelWOTaskBttn() {
        return cancelWOTaskBttn;
    }

    public void setActActChoice(RichSelectOneChoice actActChoice) {
        this.actActChoice = actActChoice;
    }

    public RichSelectOneChoice getActActChoice() {
        return actActChoice;
    }

    public void setWoActivitiesTable(RichTable woActivitiesTable) {
        this.woActivitiesTable = woActivitiesTable;
    }

    public RichTable getWoActivitiesTable() {
        return woActivitiesTable;
    }

    public void setAddFlagAssocWOBttn(RichButton addFlagAssocWOBttn) {
        this.addFlagAssocWOBttn = addFlagAssocWOBttn;
    }

    public RichButton getAddFlagAssocWOBttn() {
        return addFlagAssocWOBttn;
    }

    public void setFlagAssocTable(RichTable flagAssocTable) {
        this.flagAssocTable = flagAssocTable;
    }

    public RichTable getFlagAssocTable() {
        return flagAssocTable;
    }

    public void estimateReturnLstnr(ReturnEvent re) {
        System.out.println("Inside estimateReturnLstnr");
        //refreshVO();
        // Add event code here...
        refreshAll_action();
        refreshCurrentPage();
    }

    public void setWoQuery(RichQuery woQuery) {
        this.woQuery = woQuery;
    }

    public RichQuery getWoQuery() {
        return woQuery;
    }
}
