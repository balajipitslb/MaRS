package its.mnr.mp5.view;

import javax.faces.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import javax.faces.event.PhaseEvent;

import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.TaskFlowId;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelGridLayout;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.component.UIXGroup;
import org.apache.myfaces.trinidad.event.SelectionEvent;

import oracle.jbo.domain.Number;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.QueryEvent;

import oracle.jbo.Key;
import oracle.jbo.NameValuePairs;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.RowSetIterator;
import oracle.jbo.server.RowFinder;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class estimatesBean {
    private RichSelectOneChoice verChoice;
    private String taskFlowId = "/WEB-INF/Taskflows/Estimates/Estimates-EstimatesEntry.xml#Estimates-EstimatesEntry";

    private RichTable resultTable;
    private RichPanelGridLayout panelGroupLayoutDetail;
    private RichSelectOneChoice stChoice;
    private UIXGroup detailGrp;

    public estimatesBean() {
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
        return (DCBindingContainer) getBindingContainer();
    }

    public static OperationBinding getOperationBinding(String operationName) {
        return getBindingContainer().getOperationBinding(operationName);
    }


    public void setVerChoice(RichSelectOneChoice verChoice) {
        this.verChoice = verChoice;
    }

    public RichSelectOneChoice getVerChoice() {
        return verChoice;
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

    public void setCurrentEstimates2KeyVal(Number pEstid) {
        System.out.println("Inside setCurrentEstimates2KeyVal pEstid: " + pEstid);
        if (pEstid != null) {
            BindingContainer bindings = getBindings();
            /*
            OperationBinding op = bindings.getOperationBinding("setCurrentRowWithKeyValue");
            op.getParamsMap().put("rowKey", pEstid.toString());
            op.execute();
            */


            ////dc.setCurrentRowWithKey(pEstid.toString());
        }
    }

    public void eventsTableSelectionListener(SelectionEvent selectionEvent) {
        Number sEstid = (Number) ADFUtil.evaluateEL("#{bindings.Estid.inputValue}");
        BindingContainer bindings = getBindings();
        if (isDirty()) {
            if (sEstid != null) {

                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!",
                                                    null));
            }
        } else {
            ADFUtil.invokeMethodExpressionSelectionEvent("#{bindings.Estimate.collectionModel.makeCurrent}",
                                                         selectionEvent);
            Number sEstid1 = (Number) ADFUtil.evaluateEL("#{bindings.Estid1.inputValue}");
            System.out.println("Estimates Selection Listener sEstid: " + sEstid + " sEstid1: " + sEstid1);
            ////  syncWOMasterDetailCurrentRow();
            DCIteratorBinding iterQ = (DCIteratorBinding) bindings.get("EstimateQueryIterator");
            DCIteratorBinding iter = (DCIteratorBinding) bindings.get("EstimateIterator");
            Row rowKey = iterQ.getCurrentRow();

            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                if (parentKey != null)
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));

            }

            //// setCurrentEstimates2KeyVal(sEstid1);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.detailGrp);
        }
    }

    public void syncWOMasterDetailCurrentRow() {
        System.out.println("syncWOMasterDetailCurrentRow");
        Row selectedRow = (Row) ADFUtil.evaluateEL("#{bindings.EstimateQueryIterator.currentRow}");
        // Number sEstid = (Number)selectedRow.getAttribute("Estid");
        String swo = (String) selectedRow.getAttribute("Wonum");
        // System.out.println("sEstid: " + sEstid);
        System.out.println("swo: " + swo);
        //syncWOMasterDetail(sEstid.toString());
        syncWOMasterDetail(swo);
    }

    public void syncEstQuery(String sEstid) {
        System.out.println("syncEstQuery");
        BindingContainer bindings = getBindings();
        DCIteratorBinding dc = (DCIteratorBinding) bindings.get("EstimateQueryIterator");
        dc.setCurrentRowWithKey(sEstid);
    }

    public void syncWOMasterDetail(String sWONum) {
        //System.out.println("syncWOMasterDetail");
        BindingContainer bindings = getBindings();
        DCIteratorBinding dc = (DCIteratorBinding) bindings.get("EstimateIterator");
        ViewObjectImpl vo = (ViewObjectImpl) dc.getViewObject();

        //System.out.println("Previous: " + vo.getCurrentRow().getAttribute("Estid"));
        ////  System.out.println("Previous: " + vo.getCurrentRow().getAttribute("Wonum"));

        ////  vo.setApplyViewCriteriaName("EstimateCriteria");
        vo.setApplyViewCriteriaName("EstimateCriteriaByWO");
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
        vo.setNamedWhereClauseParam("EIdBind", sWONum);
        //// vo.setNamedWhereClauseParam("WOBind", sWONum);
        vo.executeQuery();

        System.out.println("Estimated Row Count: " + vo.getEstimatedRowCount());
        ////   Number sEstid = (Number) ADFUtil.evaluateEL("#{bindings.Estid.inputValue}");
        //// System.out.println("After executeQuery: " + vo.getCurrentRow().getAttribute("Estid") + ":" + sEstid);
        //// System.out.println("");
        ////  AdfFacesContext.getCurrentInstance().addPartialTarget(this.panelGroupLayoutDetail);
    }


    public void processQuery(QueryEvent queryEvent) {
        //QueryDescriptor qdes = queryEvent.getDescriptor();
        System.out.println("Inside estimatesBean processQuery");
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
            ADFUtil.invokeMethodExpressionQueryEvent("#{bindings.EstimateSearchQuery.processQuery}", queryEvent);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultTable());
            syncWOMasterDetailCurrentRow();
        }
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }


    public void doRollback() {
        // System.out.println("rolling back stChoice.isDisabled(): "+stChoice.isDisabled());
        // System.out.println("rolling back stChoice.getEditable(): "+stChoice.getEditable());
        //stChoice.setEditable("onAccess");
        // stChoice.setDisabled(true);
        Number sEstid = (Number) ADFUtil.evaluateEL("#{bindings.Estid.inputValue}");
        String sWO = (String) ADFUtil.evaluateEL("#{bindings.Wonum.inputValue}");
        BindingContainer bindings = getBindings();

        if (sEstid == null) {

            // perform rollback operation on new record
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
        } else {
            System.out.println("rolling back sEstid: " + sEstid + " sWO: " + sWO);
            // get current rows
            refreshIterator();
        }
        // stChoice.setEditable("inherit");
        //stChoice.setDisabled(false);
        return;
    }

    public void refreshEstIterator() {
        
        String wo="";
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterWO = (DCIteratorBinding) getDCBindingContainer().get("WOEventsIterator");
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("EstimateIterator");
        
        Row rowKeyWO = iter.getCurrentRow();
        Row rowKey = iter.getCurrentRow();
        
        if (ADFContext.getCurrent().getPageFlowScope().containsKey("psfEstid")) {
            if (ADFContext.getCurrent().getPageFlowScope().get("psfEstid") != null) {
                wo = (String) ADFContext.getCurrent().getPageFlowScope().get("psfEstid");
                System.out.println("Inside refreshEstIterator wo: " + wo);
            }
        }
        
        
        // perform rollback operation
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }

        if (rowKeyWO != null) {
            Key parentKey = rowKeyWO.getKey();
            try {
                //iterWO.setCurrentRowWithKey(parentKey.toStringFormat(true));
                if((wo!="")&&(wo!=null)){
                    iterWO.executeQuery();
                    System.out.println("Inside refreshEstIterator wo 2: " + wo);
                    iterWO.setCurrentRowWithKeyValue(wo);
                }
            } catch (RowNotFoundException ex) {
                ////System.out.println("rowKey RowNotFoundException");
                iterWO.getViewObject().applyViewCriteria(null);
                iterWO.executeQuery();
                iterWO.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
        }
        if (rowKey != null) {
            Key parentKey = rowKey.getKey();
            try {
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            } catch (RowNotFoundException ex) {
                ////System.out.println("rowKey RowNotFoundException");
                iter.getViewObject().applyViewCriteria(null);
                iter.executeQuery();
                iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
        }

    }

    public void refreshIterator() {
        BindingContainer bindings = getBindings();
        DCIteratorBinding iterQ = (DCIteratorBinding) getDCBindingContainer().get("EstimateQueryIterator");
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("EstimateIterator");
        
        Row rowKeyQ = iter.getCurrentRow();
        Row rowKey = iter.getCurrentRow();
        // perform rollback operation
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }

        if (rowKeyQ != null) {
            Key parentKey = rowKeyQ.getKey();
            try {
                iterQ.setCurrentRowWithKey(parentKey.toStringFormat(true));
            } catch (RowNotFoundException ex) {
                ////System.out.println("rowKey RowNotFoundException");
                iterQ.getViewObject().applyViewCriteria(null);
                iterQ.executeQuery();
                iterQ.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
        }
        if (rowKey != null) {
            Key parentKey = rowKey.getKey();
            try {
               
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            } catch (RowNotFoundException ex) {
                ////System.out.println("rowKey RowNotFoundException");
                iter.getViewObject().applyViewCriteria(null);
                iter.executeQuery();
                iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
            }
        }

    }

    public void setResultTable(RichTable resultTable) {
        this.resultTable = resultTable;
    }

    public RichTable getResultTable() {
        return resultTable;
    }

    public void setPanelGroupLayoutDetail(RichPanelGridLayout panelGroupLayoutDetail) {
        this.panelGroupLayoutDetail = panelGroupLayoutDetail;
    }

    public RichPanelGridLayout getPanelGroupLayoutDetail() {
        return panelGroupLayoutDetail;
    }

    public void goToControlFlow(String asaction, String soutcome) {
        //navigate to controlcase_to_follow"
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler nh = context.getApplication().getNavigationHandler();
        nh.handleNavigation(context, asaction, soutcome);
    }

    ////   public void onPageLoad(ClientEvent clientEvent) {
    public void onPageLoad(ActionEvent ae) {
        try {

            DCIteratorBinding deptIter = (DCIteratorBinding) getBindingContainer().get("EstimateQueryIterator");

            if (deptIter != null) {
                Row currentRow = deptIter.getCurrentRow();
                //List to store table RowKey
                List rowKeyList = new ArrayList();
                if (currentRow != null) {

                    //Add current row primary key to RowKeyList to form Old Key List
                    Key jboKey = new Key(new Object[] { currentRow.getAttribute("Estid") });
                    rowKeyList.add(jboKey);

                    RowKeySet newRks = new RowKeySetImpl();
                    newRks.add(rowKeyList);
                    //Create SelectionEvent Object and put necessary values
                    SelectionEvent selectionEvent =
                        new SelectionEvent(resultTable.getSelectedRowKeys(), newRks, resultTable);
                    eventsTableSelectionListener(selectionEvent);
                    //Queue Selection Event
                    ////selectionEvent.queue();
                    /*
        if (isDirty() == false) {
            goToControlFlow(null, "goEstimatesModule");
        }
        */
                    /////     ModuleNavBean.goEstimatesModule(ae);

                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }


    }

    public void commit_action_new(ActionEvent ae) {
        System.out.println("Inside commit_action");

        BindingContainer bindings = getBindings();
        updateEstimate();

        /*
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
*/
        refreshIterator();
        // stChoice.setEditable("inherit");
        //     stChoice.setDisabled(false);
    }

    public void commit_action(ActionEvent ae) {
        ///             System.out.println("commit action stChoice.isDisabled(): "+stChoice.isDisabled());
        // System.out.println("Inside commit_action stChoice.getEditable(): "+stChoice.getEditable());
        // stChoice.setEditable("onAccess");
        BindingContainer bindings = getBindings();
        if (ADFContext.getCurrent().getPageFlowScope().containsKey("updateStatus")) {
            if (ADFContext.getCurrent().getPageFlowScope().get("updateStatus") != null) {
                String newStatus = (String) ADFContext.getCurrent().getPageFlowScope().get("updateStatus");
                System.out.println("Inside commit_action calling updateEstimateStatus newStatus: " + newStatus);
                updateEstimateStatus(newStatus);
                ADFContext.getCurrent().getPageFlowScope().remove("updateStatus");
            }
        } else {
            System.out.println("Inside commit_action calling updateEstimate");
            updateEstimate();
            /*
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            */
        }
        /*
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }*/
        refreshIterator();
        // stChoice.setEditable("inherit");
        //     stChoice.setDisabled(false);
    }

    public void commit_actionWO(ActionEvent ae) {
        ///             System.out.println("commit action stChoice.isDisabled(): "+stChoice.isDisabled());
        // System.out.println("Inside commit_action stChoice.getEditable(): "+stChoice.getEditable());
        // stChoice.setEditable("onAccess");
        BindingContainer bindings = getBindings();
        if (ADFContext.getCurrent().getPageFlowScope().containsKey("updateStatus")) {
            if (ADFContext.getCurrent().getPageFlowScope().get("updateStatus") != null) {
                String newStatus = (String) ADFContext.getCurrent().getPageFlowScope().get("updateStatus");
                System.out.println("Inside commit_actionWO calling updateEstimateStatus newStatus: " + newStatus);
                updateEstimateStatus(newStatus);
                ADFContext.getCurrent().getPageFlowScope().remove("updateStatus");
            }
        } else {
            System.out.println("Inside commit_actionWO calling updateEstimate");
            updateEstimate();
            /*
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            */
        }
        
        refreshEstIterator();

        
        // stChoice.setEditable("inherit");
        //     stChoice.setDisabled(false);
    }


    public String RejWOPartsReturn(String pEvtCode) {
        System.out.println("Inside estimatesBean RejWOPartsReturn evntCd: " + pEvtCode);
        OperationBinding operationBinding = getOperationBinding("UpdateWOStatus");
        operationBinding.getParamsMap().put("evntCd", pEvtCode);
        //invoke method
        String result = (String) operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public String UpdateWOStatus(String evntCd, String pStatus, String pUser) {
        System.out.println("Inside estimatesBean UpdateWOStatus evntCd: " + evntCd + " pStatus: " + pStatus +
                           " pUser: " + pUser);
        OperationBinding operationBinding = getOperationBinding("UpdateWOStatus");
        operationBinding.getParamsMap().put("evntCd", evntCd);
        operationBinding.getParamsMap().put("pStatus", pStatus);
        //operationBinding.getParamsMap().put("pUser", pUser);
        operationBinding.getParamsMap().put("pUser", "WEB");
        //invoke method
        String result = (String) operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public String updateEstimateStatus(String pStatus) {
        System.out.println("Inside updateEstimateStatus pStatus: " + pStatus);
        String sWONum = (String) ADFUtil.evaluateEL("#{bindings.Wonum.inputValue}");
        String sUser = ADFContext.getCurrent().getSecurityContext().getUserName();
        String sAppnum = (String) ADFUtil.evaluateEL("#{bindings.Appnum.inputValue}");
        String sReason = (String) ADFUtil.evaluateEL("#{bindings.Reason.inputValue}");
        Number sEstid = (Number) ADFUtil.evaluateEL("#{bindings.Estid.inputValue}");

        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();

        String sIp = (String) request.getRemoteAddr();
        System.out.println("Inside updateEstimateStatus sUser: " + sUser + " sEstid: " + sEstid + " pStatus: " +
                           pStatus + " sWONum: " + sWONum + " sAppnum: " + sAppnum + " sReason: " + sReason + " sIp: " +
                           sIp);

        if (sEstid != null) {
            String pEstid = sEstid.toString();

            System.out.println("Inside updateEstimateStatus sUser: " + sUser + " pEstid: " + pEstid + " pStatus: " +
                               pStatus);

            OperationBinding operationBinding = getOperationBinding("ITS_EstimateUpd");
            operationBinding.getParamsMap().put("pEstId", pEstid);
            operationBinding.getParamsMap().put("pStatus", pStatus);
            operationBinding.getParamsMap().put("pUpdBy", sUser);
            operationBinding.getParamsMap().put("pAppNum", sAppnum);
            operationBinding.getParamsMap().put("pReason", sReason);
            operationBinding.getParamsMap().put("pRspType", "WEB");
            operationBinding.getParamsMap().put("pIp", sIp);
            //invoke method
            String result = (String) operationBinding.execute();

            if (!operationBinding.getErrors().isEmpty()) {
                //check errors
                List errors = operationBinding.getErrors();
            }
            if (result.equals("TRUE")) {
                if (pStatus.equals("UNA")) {
                    System.out.println("Inside updateEstimateStatus calling UpdateWOStatus for UNA");
                    result = UpdateWOStatus(sWONum, "UNA", sUser);
                } else if (pStatus.equals("APP")) {
                    System.out.println("Inside updateEstimateStatus calling UpdateWOStatus for APP");
                    result = UpdateWOStatus(sWONum, "APP", sUser);

                } else if (pStatus.equals("REJ")) {
                    System.out.println("Inside updateEstimateStatus calling UpdateWOStatus for REJ");
                    result = UpdateWOStatus(sWONum, "REJ", sUser);
                    if (result.equals("TRUE")) {
                        System.out.println("Inside updateEstimateStatus calling RejWOPartsReturn for REJ");
                        result = RejWOPartsReturn(sWONum);
                    }
                }
            }

            return result;
        } else
            return null;
    }

    public String updateEstimate() {
        System.out.println("Inside updateEstimate");
        String sStatus = (String) ADFUtil.evaluateEL("#{bindings.Status.inputValue}");
        String sWONum = (String) ADFUtil.evaluateEL("#{bindings.Wonum.inputValue}");
        String sUser = ADFContext.getCurrent().getSecurityContext().getUserName();
        String sAppnum = (String) ADFUtil.evaluateEL("#{bindings.Appnum.inputValue}");
        String sReason = (String) ADFUtil.evaluateEL("#{bindings.Reason.inputValue}");
        Number sEstid = (Number) ADFUtil.evaluateEL("#{bindings.Estid.inputValue}");

        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();

        String sIp = (String) request.getRemoteAddr();
        System.out.println("Inside updateEstimate sUser: " + sUser + " sEstid: " + sEstid + " sStatus: " + sStatus +
                           " sWONum: " + sWONum + " sAppnum: " + sAppnum + " sReason: " + sReason + " sIp: " + sIp);

        if (sEstid != null) {
            String pEstid = sEstid.toString();

            System.out.println("Inside updateEstimate sUser: " + sUser + " pEstid: " + pEstid + " sStatus: " + sStatus);

            OperationBinding operationBinding = getOperationBinding("ITS_EstimateUpd");
            operationBinding.getParamsMap().put("pEstId", pEstid);
            operationBinding.getParamsMap().put("pStatus", sStatus);
            operationBinding.getParamsMap().put("pUpdBy", sUser);
            operationBinding.getParamsMap().put("pAppNum", sAppnum);
            operationBinding.getParamsMap().put("pReason", sReason);
            operationBinding.getParamsMap().put("pRspType", "WEB");
            operationBinding.getParamsMap().put("pIp", sIp);
            //invoke method
            String result = (String) operationBinding.execute();

            if (!operationBinding.getErrors().isEmpty()) {
                //check errors
                List errors = operationBinding.getErrors();
            }
            /*
            if (result.equals("TRUE")) {
                if (sStatus.equals("UNA")) {
                    System.out.println("Inside updateEstimateStatus calling UpdateWOStatus for UNA");
                    result = UpdateWOStatus(sWONum, "UNA", sUser);
                } else if (sStatus.equals("APP")) {
                    System.out.println("Inside updateEstimateStatus calling UpdateWOStatus for APP");
                    result = UpdateWOStatus(sWONum, "APP", sUser);

                } else if (sStatus.equals("REJ")) {
                    System.out.println("Inside updateEstimateStatus calling UpdateWOStatus for REJ");
                    result = UpdateWOStatus(sWONum, "REJ", sUser);
                    if(result.equals("TRUE")){
                        System.out.println("Inside updateEstimateStatus calling RejWOPartsReturn for REJ");
                        result =RejWOPartsReturn(sWONum);
                    }
                }
            }
          */
            return result;
        } else
            return null;
    }


    public void updEstStatusLstnr(ValueChangeEvent vce) {
        String newVal = "";
        if (vce != null) {
            if (vce.getNewValue() != null) {
                newVal = vce.getNewValue().toString();
                System.out.println("Inside updEstStatusLstnr new value newVal: " + newVal);
            } else {
                newVal = vce.getOldValue().toString();
                System.out.println("Inside updEstStatusLstnr old value newVal: " + newVal);
            }
            if (newVal != null) {
                System.out.println("Inside updEstStatusLstnr newVal: " + newVal);
                if (ADFContext.getCurrent().getPageFlowScope().containsKey("updateStatus"))
                    ADFContext.getCurrent().getPageFlowScope().remove("updateStatus");

                ADFContext.getCurrent().getPageFlowScope().put("updateStatus", newVal);
                ////updateEstimateStatus(newVal);
            }
            //            System.out.println("Inside updEstStatusLstnr stChoice.getEditable(): "+stChoice.getEditable());
            // stChoice.setDisabled(false);
            //System.out.println("Inside updEstStatusLstnr set stChoice.getEditable(): "+stChoice.getEditable());
        }

    }

    public void setStChoice(RichSelectOneChoice stChoice) {
        this.stChoice = stChoice;
    }

    public RichSelectOneChoice getStChoice() {
        return stChoice;
    }

    public void setDetailGrp(UIXGroup detailGrp) {
        this.detailGrp = detailGrp;
    }

    public UIXGroup getDetailGrp() {
        return detailGrp;
    }

    public void verRefreshEstWOIterLstnr(ValueChangeEvent vce) {
        //This method is used to refresh the iterators in the EstimateWO page such that only the latest ver is 
        //editable and other esimate versions are disabled.
       //// System.out.println("Inside verRefreshEstWOIterLstnr"); 
        refreshEstIterator();
    }
    
    public void verRefreshEstEntryIterLstnr(ValueChangeEvent vce) {
        //This method is used to refresh the iterators in the EstimatesEntry page such that only the latest ver is 
        //editable and other esimate versions are disabled.
       //// System.out.println("Inside verRefreshEstEntryIterLstnr"); 
        refreshIterator();
    }
}
