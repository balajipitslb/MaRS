package its.mnr.mp5.view;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.commons.lang.StringUtils;

public class FlagBean {
    private RichTable flagAssocTable;
    private RichButton addFlagAssocWOBttn;
    private RichButton apply;
    private RichPanelHeader flagHdr;

    public FlagBean() {
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

    public void CreateNewWOFlagAssociation_action(ActionEvent actionEvent) {
        String sEvtCode = "";
        //// String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().containsKey("psfEvtcode")) {
            sEvtCode = (String) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfEvtcode");
        }
        if (sEvtCode != "") {
            BindingContainer bindings = getBindings();

            DCIteratorBinding iter = (DCIteratorBinding) bindings.get("ActiveMrltFlagIterator");
            //Get viewObject from Iterator
            iter.executeQuery();
            ViewObject activeVo = iter.getViewObject();

            RowSetIterator rsi = activeVo.createRowSetIterator(null);
            while (rsi.hasNext()) {
                //Get viewObject next row from RowSetIterator
                Row nextRow = rsi.next();
               
                String Fid = nextRow.getAttribute("Fid").toString();
                System.out.println("Inside CreateNewWOFlagAssociation_action Fid: " + Fid);
                /*
                OperationBinding oper = bindings.getOperationBinding("CreateFlagAssoc");
                oper.getParamsMap().put("faitem", sEvtCode);
                */
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
            ////  refreshAll_action();
        }
        return;
    }

    public void syncWOMasterDetail(String sWONum) {
        System.out.println("Inside Flagbean syncWOMasterDetail");
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
        System.out.println("After executeQuery sEvtCode: " + vo.getCurrentRow().getAttribute("EvtCode") + ":" + sEvtCode);
        
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


    public void setFlagAssocTable(RichTable flagAssocTable) {
        this.flagAssocTable = flagAssocTable;
    }

    public RichTable getFlagAssocTable() {
        return flagAssocTable;
    }

    public void setAddFlagAssocWOBttn(RichButton addFlagAssocWOBttn) {
        this.addFlagAssocWOBttn = addFlagAssocWOBttn;
    }

    public RichButton getAddFlagAssocWOBttn() {
        return addFlagAssocWOBttn;
    }
 
    public void setApply(RichButton apply) {
        this.apply = apply;
    }

    public RichButton getApply() {
        return apply;
    }   

    public void goToControlFlow(String asaction, String soutcome) {
        //navigate to controlcase_to_follow"
        FacesContext context = FacesContext.getCurrentInstance();
        NavigationHandler nh = context.getApplication().getNavigationHandler();
        nh.handleNavigation(context, asaction, soutcome);
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

    public void flagPPClose_action(ActionEvent ae) {
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        if (isDirty()) {

            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
            return;
        } else
            goToControlFlow(null, "Exit");
    }
    
    public void flagAssocTblRollback_action(ActionEvent ae) {

        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        String sEvtCode1 = "";

        if(AdfFacesContext.getCurrentInstance().getPageFlowScope().containsKey("psfEvtcode")){
            sEvtCode1 = (String) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfEvtcode");
        }
        System.out.println("Inside FlagBean flagAssocTblRollback_action sEvtCode: "+sEvtCode+" sEvtCode1: "+sEvtCode1);
        
        if(StringUtils.isNotBlank(sEvtCode1))
            syncWOMasterDetail(sEvtCode1);
        
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
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.flagHdr);
        }
    }

    public void setFlagHdr(RichPanelHeader flagHdr) {
        this.flagHdr = flagHdr;
    }

    public RichPanelHeader getFlagHdr() {
        return flagHdr;
    }
}
