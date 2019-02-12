package its.mnr.mp5.view;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichButton;


import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;

public class invoiceRetroBean {
    private RichInputText invoiceNumber;
    private RichSelectOneChoice accountNumCrit;
    private RichInputDate startDtCrit;
    private RichInputDate endDtCrit;
    private RichButton searchBtn;
    private RichButton resetBtn;
    private RichCommandMenuItem createNewInvoiceMenu;
    private RichPopup popInvConf;
    private Number headerId;
    private RichTable resultsTable;

    public invoiceRetroBean() {
    }

    /* Helper methods */

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
    

    public String apply_action() {
        Boolean chk1 = false;
        
        chk1 = createInvoiceRec();
        if (!chk1) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "System failed to create Invoice!", null));
        }

        if (chk1) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            showInvoiceNumber(headerId);
            UIfieldsDisable(true);  
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popInvConf.show(hints);
        }
        return null;
    }
    public String cancel_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        reset();
        return null;
    }
    public String createNewInvoice() {
        this.invoiceNumber.setValue("");
        UIfieldsDisable(false);  
        reset();
        refreshCurrentPage();
        return null;
    }

    public Boolean createInvoiceRec() {
        System.out.println("createInvoiceRec()");
        Boolean chk = false;
        Boolean ret = false;
        Number nHeaderId = createInvoiceHeader();
        headerId = nHeaderId;
        if (nHeaderId != null) {
            // get binding for Results table
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("InvoiceRetroResultsIterator");

            for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                Row row = dciter.getRowAtRangeIndex(i);
                // create Invoice Item record for every selected Work Order
                String sWO = (String)row.getAttribute("Wo");
                Number nAct = (Number)row.getAttribute("Act");
                Number nInvoice = (Number)row.getAttribute("Invnum");
                String sOldVer = (String)row.getAttribute("Activever");
                String sRtroVer = (String)row.getAttribute("Rtrover");
                String sNewVer = (String)row.getAttribute("Contractver");
                String pOldARTaskId = (String)row.getAttribute("Oldartaskid");
                String pNewARTaskId = (String)row.getAttribute("Newartaskid");
                Double pOldRate = (Double)row.getAttribute("Oldrate");
                Double pRtroRate = (Double)row.getAttribute("Rtrorate");
                Double pNewRate = (Double)row.getAttribute("Newrate");
                String pShift = (String)row.getAttribute("Shift");
                Double pHrs = (Double)row.getAttribute("Hrs");
                String pOldRateType = (String)row.getAttribute("Oldratetype");
                String pRtroRateType = (String)row.getAttribute("Rtroratetype");
                String pNewRateType = (String)row.getAttribute("Newratetype");
                String pEquip = (String)row.getAttribute("Equipcharge");
                String pTask = (String)row.getAttribute("ItsTaskdesc");
                String pCompCd = (String)row.getAttribute("ItsCompcode");
                String pDamCd = (String)row.getAttribute("ItsDamcode");
                String pDamDesc = (String)row.getAttribute("ItsDamdesc");
                String pLocCd = (String)row.getAttribute("ItsLoccode");
                String pLocDesc = (String)row.getAttribute("ItsLocdesc");
                String pRepCd = (String)row.getAttribute("ItsRepcode");
                String pRepDesc = (String)row.getAttribute("ItsRepdesc");
                String sHrdCode = (String)row.getAttribute("Hdrcode");
//                System.out.println(headerId + ":" + nInvoice + ":" +sWO + ":"+ nAct + ":" + sRtroVer + ":" + sOldVer + ":" + sNewVer  + ":" +pOldARTaskId);
//                System.out.println(pNewARTaskId + ":" + pRtroRate  + ":" + pOldRate  + ":" + pNewRate + ":" +  pShift + ":" +  pHrs);
//                System.out.println(pRtroRateType  + ":" +  pOldRateType  + ":" + pNewRateType  + ":" + pEquip  + ":" + pTask + ":" +  pCompCd);
//                System.out.println(pDamCd + ":" +  pDamDesc + ":" +  pLocCd  + ":" + pLocDesc + ":" +  pRepCd  + ":" + pRepDesc  + ":" + sHrdCode);
                chk =
    createInvoiceRtroItem(headerId, nInvoice, sWO, nAct, (sRtroVer != null ? sRtroVer : sOldVer), sNewVer, pOldARTaskId,
                      pNewARTaskId, (pRtroRate != null ? pRtroRate : pOldRate), pNewRate, pShift, pHrs,
                      (pRtroRateType != null ? pRtroRateType : pOldRateType), pNewRateType, pEquip, pTask, pCompCd,
                      pDamCd, pDamDesc, pLocCd, pLocDesc, pRepCd, pRepDesc, sHrdCode);
                if (chk != null) {
                    ret = chk;
                }
            }
        }
        return ret;
    }
    public Number createInvoiceHeader() {
        OperationBinding operationBinding = getOperationBinding("createInvoiceHeader");
        //AttributeBinding assetNm = (AttributeBinding)getBindings().get("EvtDesc");
        //System.out.println("getAssetDetail() Equipment #: " + assetNm);
        //optional
        //operationBinding.getParamsMap().put("assetNm", assetNm);
        //invoke method
        Number result = (Number)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }  
    public Boolean createInvoiceRtroItem(Number pHeaderId, Number pInvoice, String pWO, Number pAct, String pOldVer,
                                         String pNewVer, String pOldARTaskId, String pNewARTaskId, Double pOldRate,
                                         Double pNewRate, String pShift, Double pHrs, String pOldRateType,
                                         String pNewRateType, String pEquip, String pTask, String pCompCd,
                                         String pDamCd, String pDamDesc, String pLocCd,
                                         String pLocDesc, String pRepCd, String pRepDesc, String pHdrCode) {
        OperationBinding operationBinding = getOperationBinding("createInvoiceRtroItem");
        //AttributeBinding assetNm = (AttributeBinding)getBindings().get("EvtDesc");
        //System.out.println("getAssetDetail() Equipment #: " + assetNm);
        //optional
        operationBinding.getParamsMap().put("pHeaderId", pHeaderId);
        operationBinding.getParamsMap().put("pInvoice", pInvoice);
        operationBinding.getParamsMap().put("pWO", pWO);
        operationBinding.getParamsMap().put("pAct", pAct);
        operationBinding.getParamsMap().put("pOldVer", pOldVer);
        operationBinding.getParamsMap().put("pNewVer", pNewVer);
        operationBinding.getParamsMap().put("pOldARTaskId", pOldARTaskId);
        operationBinding.getParamsMap().put("pNewARTaskId", pNewARTaskId);
        operationBinding.getParamsMap().put("pOldRate", pOldRate);
        operationBinding.getParamsMap().put("pNewRate", pNewRate);
        operationBinding.getParamsMap().put("pShift", pShift);
        operationBinding.getParamsMap().put("pHrs", pHrs);
        operationBinding.getParamsMap().put("pOldRateType", pOldRateType);
        operationBinding.getParamsMap().put("pNewRateType", pNewRateType);
        operationBinding.getParamsMap().put("pEquip", pEquip);
        operationBinding.getParamsMap().put("pTask", pTask);
        operationBinding.getParamsMap().put("pCompCd", pCompCd);       
        operationBinding.getParamsMap().put("pDamCd", pDamCd);
        operationBinding.getParamsMap().put("pDamDesc", pDamDesc);
        operationBinding.getParamsMap().put("pLocCd", pLocCd);
        operationBinding.getParamsMap().put("pLocDesc", pLocDesc);
        operationBinding.getParamsMap().put("pRepCd", pRepCd);
        operationBinding.getParamsMap().put("pRepDesc", pRepDesc);   
        operationBinding.getParamsMap().put("pHdrCode", pHdrCode);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public Boolean createInvoiceRtroItemDetail(Number pRtroItemId, Number pOldARTaskId, Number pNewARTaskId, 
                                               Double pOldRate, Double pNewRate, String pShift, Double pLbrHrs, String pRateType,
                                               String pEquip, String pTask) {
        OperationBinding operationBinding = getOperationBinding("createInvoiceRtroItemDetail");
        //AttributeBinding assetNm = (AttributeBinding)getBindings().get("EvtDesc");
        //System.out.println("getAssetDetail() Equipment #: " + assetNm);
        //optional
        operationBinding.getParamsMap().put("pRtroItemId", pRtroItemId);
        operationBinding.getParamsMap().put("pOldARTaskId", pOldARTaskId);
        operationBinding.getParamsMap().put("pNewARTaskId", pNewARTaskId);
        operationBinding.getParamsMap().put("pOldRate", pOldRate);
        operationBinding.getParamsMap().put("pNewRate", pNewRate);
        operationBinding.getParamsMap().put("pShift", pShift);
        operationBinding.getParamsMap().put("pLbrHrs", pLbrHrs);
        operationBinding.getParamsMap().put("pRateType", pRateType);
        operationBinding.getParamsMap().put("pEquip", pEquip);
        operationBinding.getParamsMap().put("pTask", pTask);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public void showInvoiceNumber(Number invoicenum){

        Number nInvNum = getInvoiceNum(invoicenum);
        this.invoiceNumber.setValue(nInvNum.toString());
           
        //System.out.println("showInvoiceNumber():" + nInvNum.toString());
        }
    public Number getInvoiceNum(Number pHeaderId) {
        OperationBinding operationBinding = getOperationBinding("getInvoiceNum");
        //System.out.println("getAssetDetail() Equipment #: " + assetNm);
        //optional
        operationBinding.getParamsMap().put("pHeaderId", pHeaderId);
        //invoke method
        Number result = (Number)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public void UIfieldsDisable(Boolean bvalue) {
        //System.out.println("UIfieldsDisable()");
        this.accountNumCrit.setDisabled(bvalue);
        this.startDtCrit.setDisabled(bvalue);
        this.endDtCrit.setDisabled(bvalue);
        this.searchBtn.setDisabled(bvalue);
        this.resetBtn.setDisabled(bvalue);
        this.createNewInvoiceMenu.setDisabled(!bvalue);
    }
    public void reset() {
        OperationBinding operationBinding = getOperationBinding("initInvoiceRetro");
        //invoke method
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }    
        this.accountNumCrit.resetValue();
        this.startDtCrit.resetValue();
        this.endDtCrit.resetValue();
    }
    public String refreshCurrentPage() {
            FacesContext context = FacesContext.getCurrentInstance();
            String currentView = context.getViewRoot().getViewId();
            ViewHandler vh = context.getApplication().getViewHandler();
            UIViewRoot x = vh.createView(context, currentView);
            context.setViewRoot(x);
        return null;
    }

    public void rowDisclosureEvent(RowDisclosureEvent rowDisclosureEvent) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultsTable());
    }
    public void setInvoiceNumber(RichInputText invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public RichInputText getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setAccountNumCrit(RichSelectOneChoice accountNumCrit) {
        this.accountNumCrit = accountNumCrit;
    }

    public RichSelectOneChoice getAccountNumCrit() {
        return accountNumCrit;
    }

    public void setStartDtCrit(RichInputDate startDtCrit) {
        this.startDtCrit = startDtCrit;
    }

    public RichInputDate getStartDtCrit() {
        return startDtCrit;
    }

    public void setEndDtCrit(RichInputDate endDtCrit) {
        this.endDtCrit = endDtCrit;
    }

    public RichInputDate getEndDtCrit() {
        return endDtCrit;
    }

    public void setSearchBtn(RichButton searchBtn) {
        this.searchBtn = searchBtn;
    }

    public RichButton getSearchBtn() {
        return searchBtn;
    }

    public void setResetBtn(RichButton resetBtn) {
        this.resetBtn = resetBtn;
    }

    public RichButton getResetBtn() {
        return resetBtn;
    }

    public void setCreateNewInvoiceMenu(RichCommandMenuItem createNewInvoiceMenu) {
        this.createNewInvoiceMenu = createNewInvoiceMenu;
    }

    public RichCommandMenuItem getCreateNewInvoiceMenu() {
        return createNewInvoiceMenu;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void setPopInvConf(RichPopup popInvConf) {
        this.popInvConf = popInvConf;
    }

    public RichPopup getPopInvConf() {
        return popInvConf;
    }

    public void setResultsTable(RichTable resultsTable) {
        this.resultsTable = resultsTable;
    }

    public RichTable getResultsTable() {
        return resultsTable;
    }

}
