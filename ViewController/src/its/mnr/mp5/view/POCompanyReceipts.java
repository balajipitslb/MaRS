package its.mnr.mp5.view;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;


import java.util.List;

import javax.faces.application.ViewHandler;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
//import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.QueryEvent;

import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;

import oracle.jbo.RowNotFoundException;

import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class POCompanyReceipts {
    private RichInputComboboxListOfValues inTraStatus;
    private RichTable resultsSearchTable;
    private RichInputText itTransDesc;
    private RichPopup popTransDescPrompt;
    private RichDocument pvTransDesc;
    private RichInputText inTransDescription;
    private RichInputListOfValues lovSupplier;
    private RichSelectBooleanCheckbox isFetchedChk;
    private RichOutputText otBasePrice;
    private RichInputText itBasePrice;
    private RichPopup addPartToStockPop;
    private RichInputText itLineQty;
    private RichInputText itLineOrg;
    private RichInputText itLineStore;
    private RichPopup addPartPop;
    private RichButton editBtn;
    private RichInputListOfValues itPart;
    private RichInputText itPrice;
    private RichInputListOfValues txtBin;
    private RichInputListOfValues txtLot;

    public POCompanyReceipts() {
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

    public void goToControlFlow (String asaction, String soutcome){
            //navigate to controlcase_to_follow"
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler nh = context.getApplication().getNavigationHandler();
            nh.handleNavigation(context, asaction, soutcome);
        }

    public void queueAction (String id){
            //Code to call ActionEvent 
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot root = facesContext.getViewRoot();
            /**Pass cb1(buttonId) if page is not in taskflow, else if page is inside region then pass rgionid:buttonId*/
            RichButton button = (RichButton)root.findComponent(id);
            ActionEvent actionEvent = new ActionEvent(button);
            actionEvent.queue();
        }
    public String addTransline_action() {
//        System.out.println("calling addTransline_action()");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }
    
    public String addPartLinePop() {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));

        } else {
            addPartPop.show(hints);
            addTransline_action();
            //this.itPart.resetValue();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(this.itPart);
            //queueAction ("cmi2");
        }
        return null;
    }
    public String addPartLine() {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));

        } else {
            addTransline_action();
            //this.itPart.resetValue();
            //AdfFacesContext.getCurrentInstance().addPartialTarget(this.itPart);
            //queueAction ("cmi2");
        }
        return null;
    }
    public String editPartLine() {
//        if (isDirty()) {
//            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
//            return null;
//
//        } else {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            addPartPop.show(hints);
//        }
        return null;
    }

    public void handleLineDoubleClick(ClientEvent clientEvent) {
        if (!this.editBtn.isDisabled()) {
            RichPopup popup = this.getAddPartPop();
            //no hints means that popup is launched in the
            //center of the page
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            popup.show(ph);
        }
    }

    public String closePartLinePop() {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
            return null;
        }
        RichPopup rp = this.getAddPartPop();
        rp.hide();
        return null;
    }
    
    public void exit_action() {
        String straCode = (String)ADFUtil.evaluateEL("#{bindings.TraCode.inputValue}");
        if (isDirty() && !StringUtils.isBlank(straCode)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
            on_rollback();
            goToControlFlow(null, "Exit");
        }
    }

    public void processQuery(QueryEvent queryEvent) {
        QueryDescriptor qdes = queryEvent.getDescriptor();
//        System.out.println("NAME " + qdes.getName());
        String straCode = (String)ADFUtil.evaluateEL("#{bindings.TraCode.inputValue}");
        String straDesc = (String)ADFUtil.evaluateEL("#{bindings.TraDesc.inputValue}");
        String straFromcode = (String)ADFUtil.evaluateEL("#{bindings.TraFromcode.inputValue}");
        //String straDate = (String)ADFUtil.evaluateEL("#{bindings.TraDate.inputValue}");
//        System.out.println("Inside processQuery straCode: "+straCode+" straDesc: "+straDesc+" straFromcode: "+straFromcode+" straDate: "+ADFUtil.evaluateEL("#{bindings.TraDate.inputValue}")+" isDirty(): "+isDirty());
        //()
        if (isDirty() && !StringUtils.isBlank(straCode)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
//            System.out.println("Inside processQuery else section");
            on_rollback();
            ADFUtil.invokeMethodExpressionQueryEvent("#{bindings.POTransactionsCompRcptsCriteriaQuery.processQuery}",
                                                     queryEvent);

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.resultsSearchTable);
            //refreshTransline();
        }
    }
    
    public boolean checkDirtyDataAction() {
        ControllerContext controllerContext=ControllerContext.getInstance();
        ViewPortContext currentRootViewPort = controllerContext.getCurrentRootViewPort();
        boolean isDataDirty = currentRootViewPort.isDataDirty();
        System.out.println("Inside checkDirtyDataAction() isDataDirty: "+isDataDirty);
        if (true == isDataDirty) {
            return true;
        }
        return false;
    }    
   
    public boolean isDirty() {
        ApplicationModule am = getDCBindingContainer().getDataControl().getApplicationModule();
        Boolean transDirty = am.getTransaction().isDirty();
        System.out.println("Inside isDirty() transDirty: "+transDirty);
        
        BindingContext bc = getBindingContext();
        String dataControl = getBindingContext().getCurrentDataControlFrame();
        Boolean bindingsChanged = bc.findDataControlFrame(dataControl).isTransactionDirty();
        System.out.println("Inside isDirty() dataControl: "+dataControl+" bindingsChanged: "+bindingsChanged);

        if (transDirty || bindingsChanged) {
            return true;
        }
        return false;
    }
    
    public Boolean checkRequired() {
        //System.out.println("checkSelected()");
        Boolean ret = true;
            // get binding for Results table
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POTranslinesCompRcptsIterator");

            for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                Row row = dciter.getRowAtRangeIndex(i);
                //System.out.println("HC");
                String sTrlPart = (String)row.getAttribute("TrlPart");
                Double dTrlQty = (Double)row.getAttribute("TrlQty");
                String sTrlBin = (String)row.getAttribute("TrlBin");
                String sTrlLot = (String)row.getAttribute("TrlLot");
                Double dTrlPrice = (Double)row.getAttribute("TrlPrice"); 
                
                if (!StringUtils.isBlank(sTrlPart) && 
                    (StringUtils.isBlank(sTrlBin) || StringUtils.isBlank(sTrlLot) || 
                     dTrlQty == null || dTrlPrice == null)) {
                    ret = false;
                }
            }
        return ret;
    }
    
    public String commit_action() {
        Boolean chk1 = true;   
        Boolean chk2 = true;
        chk1 = checkRequired();
        
        if (!chk1) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Part#, Bin, Lot, Qty and Price are required!", null));
            return null;
        } 
        
        chk2 = verifyPartsInStock();
        
        if (!chk2) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding Part to Stock!", null));
            return null;
        } 
       System.out.println("committing");
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        System.out.println("after committing");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
//        refreshCurrentPage();
//        setRowTra();
        return null;
    }
    
    public String commitConly_action() {
       
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String refreshCurrentPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String currentView = context.getViewRoot().getViewId();
        ViewHandler vh = context.getApplication().getViewHandler();
        UIViewRoot x = vh.createView(context, currentView);
        context.setViewRoot(x);
        return null;
    }
    
    public void setRowTra (){
            String straCode = (String)ADFUtil.evaluateEL("#{bindings.TraCode.inputValue}");

            BindingContainer bindings = getBindings();
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POTransactionsCompRcptsIterator");
            Row rowKey = iter.getCurrentRow();
 
            if (rowKey != null && straCode != null) {
                Key parentKey = rowKey.getKey();
                try {
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                } catch (RowNotFoundException ex) {
                    //System.out.println("rowKey RowNotFoundException");
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
        }
        
    public String on_rollback() {
//        System.out.println("Inside on_rollback()");
        String straCode = (String)ADFUtil.evaluateEL("#{bindings.TraCode.inputValue}");
//        System.out.println("Inside on_rollback() straCode: "+straCode);
        BindingContainer bindings = getBindings();
        if (StringUtils.isBlank(straCode)) {
            // perform rollback operation
//            System.out.println("Inside on_rollback() perform rollback operation");
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            //Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POTransactionsCompRcptsIterator");
            Row rowKey = iter.getCurrentRow();
//            System.out.println("Inside on_rollback() inside else section perform rollback operation");
            // perform rollback operation
            try{
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
           // Object result = operationBinding.execute();
           operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            }
            catch(Exception e){
                System.out.println("Row not found: "+e);
            }
            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                try {
                   System.out.println("Parent: setCurrentRowWithKey");
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                } catch (RowNotFoundException ex) {
                    System.out.println("Parent RowKey RowNotFoundException");
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
            DCIteratorBinding iterLine = (DCIteratorBinding)getDCBindingContainer().get("POTranslinesCompRcptsIterator");
            Row rowLine = iterLine.getCurrentRow();
            if (rowLine != null) {
                Key childKeyLine = rowLine.getKey();
                try {
                   System.out.println("Child: setCurrentRowWithKey");
                    iterLine.setCurrentRowWithKey(childKeyLine.toStringFormat(true));
                } catch (RowNotFoundException ex) {
                    System.out.println("Child RowKey RowNotFoundException");
                    iterLine.getViewObject().applyViewCriteria(null);
                    iterLine.executeQuery();
                    iterLine.setCurrentRowWithKey(childKeyLine.toStringFormat(true));
                }
            }
        }
        return null;
    }

    public String newRecv_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.inTraStatus.resetValue();
        return null;
    }
    
    public void partChangeLsnr(ValueChangeEvent valueChangeEvent) {
//        System.out.println("partChangeLsnr");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
//        String sPart = (String)this.itPart.getValue();
//        String sPartOrg = (String)this.itLineOrg.getValue();
//        System.out.println("sPart: " + sPart);

        getDefaultBinLot();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtBin);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtLot);
        
    }


    public String getDefaultBinLot() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getDefaultBinLot");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }
    
    public Boolean addPartToStock(String pPart, String pPartOrg, String pStore, Double pQty) {
//        System.out.println("Inside addPartToStock()");
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        OperationBinding operationBinding = getOperationBinding("addPartToStock");
        operationBinding.getParamsMap().put("pPart", pPart);
        operationBinding.getParamsMap().put("pPartOrg", pPartOrg);
        operationBinding.getParamsMap().put("pStore", pStore);
        operationBinding.getParamsMap().put("pQty", pQty);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
//        System.out.println(result);
        return result;    
    }

    public Boolean isPartInCat(String pPart, String pPartOrg) {
//        System.out.println("Inside isPartInCat()");
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        OperationBinding operationBinding = getOperationBinding("isPartInCat");
        operationBinding.getParamsMap().put("pPart", pPart);
        operationBinding.getParamsMap().put("pPartOrg", pPartOrg);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
//        System.out.println(result);
        return result;
    }

    public void partStockDiagLsnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        String sPart = (String)this.itPart.getValue();
        String sPartOrg = (String)this.itLineOrg.getValue();
        String sStore = (String)this.itLineStore.getValue();
        Double dQty = (Double)this.itLineQty.getValue();
        if (result == DialogEvent.Outcome.yes) {
            addPartToStock(sPart,sPartOrg,sStore,dQty);
        }
    }
    
    public void transDescDiagLsnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            //System.out.println("DialogEvent.Outcome.ok: " + this.itTransDesc.getValue());
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfdescription", this.itTransDesc.getValue());
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfcalledfromstock", true);
                goToControlFlow(null, "goInit");
            } 
    }
    
    public void onDialogCancel(ClientEvent clientEvent) {
        //System.out.println("transDescDiagLsnr: Cancel ");
        //queueAction("returntoStock");
        goToControlFlow(null, "Exit");
    }

    public void setFetched(ClientEvent clientEvent) {
//        System.out.println("setFetched");
        String iEntity = (String)ADFUtil.evaluateEL("#{bindings.tEntityState.inputValue}");
//        System.out.println("iEntity:" + iEntity);
        if (!iEntity.equals("0") ){
            return;
            }
            
//        Boolean psfcalledfromstock =
//            (Boolean)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfcalledfromstock");
//        psfcalledfromstock = psfcalledfromstock == null ? false : psfcalledfromstock;

        //System.out.println("psfcalledfromstock:" + psfcalledfromstock);
        String psfdescription = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfdescription");

        String pfsSupplierNo = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("pfsSupplierNo");
        //System.out.println("psfdescription: " + psfdescription);
        if (this.isFetchedChk.isSelected()) {
            //System.out.println("setting defaults");
            if (!StringUtils.isBlank(psfdescription)) {
                ADFUtil.setEL("#{bindings.TraDesc.inputValue}", psfdescription);
                //this.inTransDescription.setValue(psfdescription);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.inTransDescription);
            }
            if (!StringUtils.isBlank(pfsSupplierNo)) {
                ADFUtil.setEL("#{bindings.TraFromcode.inputValue}", pfsSupplierNo);
                //this.lovSupplier.setValue(pfsSupplierNo);
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.lovSupplier);
            }
//            String ret = commitConly_action();
//            if (StringUtils.isBlank(ret)) {
//                setFetchedPart();
//                //syncPrice();
//                String sPart = (String)this.itPart.getValue();
//                System.out.println("sPart: " + sPart);
//          }
        }

        //#{pageFlowScope.pfsPartNo} : #{pageFlowScope.pfsSupplierNo}
    }
    public String callPartinStock() {
        verifyPartsInStock();
        return null;
    }

    public boolean verifyPartsInStock() {
        boolean ret = true;
        // get binding for Results table
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POTranslinesCompRcptsIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            
            String sTrlPart = (String)row.getAttribute("TrlPart");
            String sTrlOrg = (String)row.getAttribute("TrlPartOrg");
            String sTrlStore = (String)row.getAttribute("TrlStore");
            Double dTrlQty = (Double)row.getAttribute("TrlQty");
            if (row != null) {
                if (!isPartInCat(sTrlPart, sTrlOrg)) {
//                    System.out.println("Adding Part to Stock");
                    ret = addPartToStock(sTrlPart,sTrlOrg,sTrlStore,dTrlQty);
                }

            }
        }

        return ret;
    }

    public void setFetchedPart() {
//        System.out.println("calling setFetchedPart()");
        String pfsPartNo = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("pfsPartNo");
        //ADFUtil.setEL("#{row.bindings.TrlPart.inputValue}", pfsPartNo);
//        System.out.println("pfsPartNo: " + pfsPartNo);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

        addPartPop.show(hints);
        addTransline_action();

        //this.itLinePart2.setValue(pfsPartNo);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.itLinePart2);
    }

    public void syncPrice() {
//        System.out.println("syncPrice()" );
        Double pfsBasePrice = (Double)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("pfsBasePrice");
        //Double pfsBasePrice = Double.parseDouble(pfsBasePrice);
        //System.out.println("pfsBasePrice):" + pfsBasePrice);
        this.itPrice.setValue(pfsBasePrice);
        //System.out.println("itPrice.getValue():" + this.itPrice.getValue());
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.itPrice);
        //#{pageFlowScope.pfsBasePrice}
    }

    public void setInTraStatus(RichInputComboboxListOfValues inTraStatus) {
        this.inTraStatus = inTraStatus;
    }

    public RichInputComboboxListOfValues getInTraStatus() {
        return inTraStatus;
    }

    public void setResultsSearchTable(RichTable resultsSearchTable) {
        this.resultsSearchTable = resultsSearchTable;
    }

    public RichTable getResultsSearchTable() {
        return resultsSearchTable;
    }

    public void setItTransDesc(RichInputText itTransDesc) {
        this.itTransDesc = itTransDesc;
    }

    public RichInputText getItTransDesc() {
        return itTransDesc;
    }

    public void setPopTransDescPrompt(RichPopup popTransDescPrompt) {
        this.popTransDescPrompt = popTransDescPrompt;
    }

    public RichPopup getPopTransDescPrompt() {
        return popTransDescPrompt;
    }

    public void setPvTransDesc(RichDocument pvTransDesc) {
        this.pvTransDesc = pvTransDesc;
    }

    public RichDocument getPvTransDesc() {
        return pvTransDesc;
    }


    public void setInTransDescription(RichInputText inTransDesc) {
        this.inTransDescription = inTransDesc;
    }

    public RichInputText getInTransDescription() {
        return inTransDescription;
    }

    public void setLovSupplier(RichInputListOfValues lovSupplier) {
        this.lovSupplier = lovSupplier;
    }

    public RichInputListOfValues getLovSupplier() {
        return lovSupplier;
    }

    public void setIsFetchedChk(RichSelectBooleanCheckbox isFetchedChk) {
        this.isFetchedChk = isFetchedChk;
    }

    public RichSelectBooleanCheckbox getIsFetchedChk() {
        return isFetchedChk;
    }

    public void setOtBasePrice(RichOutputText otBasePrice) {
        this.otBasePrice = otBasePrice;
    }

    public RichOutputText getOtBasePrice() {
        return otBasePrice;
    }

    public void setItBasePrice(RichInputText itBasePrice) {
        this.itBasePrice = itBasePrice;
    }

    public RichInputText getItBasePrice() {
        return itBasePrice;
    }

    public void setAddPartToStockPop(RichPopup addPartToStockPop) {
        this.addPartToStockPop = addPartToStockPop;
    }

    public RichPopup getAddPartToStockPop() {
        return addPartToStockPop;
    }


    public void setItLineQty(RichInputText itLineQty) {
        this.itLineQty = itLineQty;
    }

    public RichInputText getItLineQty() {
        return itLineQty;
    }

    public void setItLineOrg(RichInputText itLineOrg) {
        this.itLineOrg = itLineOrg;
    }

    public RichInputText getItLineOrg() {
        return itLineOrg;
    }

    public void setItLineStore(RichInputText itLineStore) {
        this.itLineStore = itLineStore;
    }

    public RichInputText getItLineStore() {
        return itLineStore;
    }

    public void setAddPartPop(RichPopup addPartPop) {
        this.addPartPop = addPartPop;
    }

    public RichPopup getAddPartPop() {
        return addPartPop;
    }

    public void setEditBtn(RichButton editBtn) {
        this.editBtn = editBtn;
    }

    public RichButton getEditBtn() {
        return editBtn;
    }

    public void setItPart(RichInputListOfValues itPart) {
        this.itPart = itPart;
    }

    public RichInputListOfValues getItPart() {
        return itPart;
    }

    public void setItPrice(RichInputText itPrice) {
        this.itPrice = itPrice;
    }

    public RichInputText getItPrice() {
        return itPrice;
    }


    public void setTxtBin(RichInputListOfValues txtBin) {
        this.txtBin = txtBin;
    }

    public RichInputListOfValues getTxtBin() {
        return txtBin;
    }

    public void setTxtLot(RichInputListOfValues txtLot) {
        this.txtLot = txtLot;
    }

    public RichInputListOfValues getTxtLot() {
        return txtLot;
    }
}
