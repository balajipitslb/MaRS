package its.mnr.mp5.view;

import java.math.BigDecimal;

import java.text.DecimalFormat;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
//import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.*;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

public class invoiceAssemblyBean {
    private RichSelectOneChoice accountNumCrit;
    private RichSelectOneChoice woTypeCrit;
    private RichSelectOneChoice headerCdCrit;
    private RichInputDate weekEndingCrit;
    private RichSelectBooleanCheckbox checkAll;
    private RichSelectBooleanCheckbox isSelectedChk;
    private RichInputText invoiceNumber;
    
    private Number headerId;
    private RichPopup popInvConf;
    private RichCommandMenuItem createNewInvoiceButton;
    private RichButton searchButton;
    private RichButton resetButton;

    public invoiceAssemblyBean() {
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

    public void selectAllChangeListener(ValueChangeEvent valueChangeEvent) {
        // get the binding container
  
        BindingContainer dcbindings = getDCBindingContainer();
        DCIteratorBinding dciter = (DCIteratorBinding)dcbindings.get("InvoiceAssemblyResultsIterator");
        RowSetIterator rit = dciter.getRowSetIterator();
        rit.reset();
        if (valueChangeEvent.getNewValue() != null) {
            Boolean selectAll = Boolean.parseBoolean(valueChangeEvent.getNewValue().toString());
            //System.out.println("selectAll:" + selectAll);
            String selectedValue = (selectAll? "Y":"N");
            if (rit.first() != null) {
                Row r = rit.first();
                r.setAttribute("isSelected", selectedValue);
                this.isSelectedChk.setSelected(selectAll);
                //System.out.println(r.getAttribute("EvtCode") + ":" + this.isSelectedChk.isSelected());
                
            }
            while (rit.hasNext()) {
                Row r = rit.next();
                if (r != null) {
                    r.setAttribute("isSelected", selectedValue);
                    this.isSelectedChk.setSelected(selectAll);
                    //System.out.println(r.getAttribute("EvtCode") + ":" + this.isSelectedChk.isSelected());
                }
            }
        }
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
    public Boolean createInvoiceItem(Number pHeaderId,String pWO, String pEquip, String pVer,
                                     Double pLabor, Double pPart, Double pTax, Double pTotal,Date pCompdt, Date pStartdt, String pHdrCode) {
        OperationBinding operationBinding = getOperationBinding("createInvoiceItem");
        //AttributeBinding assetNm = (AttributeBinding)getBindings().get("EvtDesc");
        //System.out.println("getAssetDetail() Equipment #: " + assetNm);
        //optional
        System.out.println("Inside createInvoiceItem pTax:" + pTax);
        operationBinding.getParamsMap().put("pHeaderId", pHeaderId);
        operationBinding.getParamsMap().put("pWO", pWO);
        operationBinding.getParamsMap().put("pEquip", pEquip);
        operationBinding.getParamsMap().put("pVer", pVer);
        operationBinding.getParamsMap().put("pLabor", pLabor);
        operationBinding.getParamsMap().put("pPart", pPart);
        operationBinding.getParamsMap().put("pTax", pTax);
        operationBinding.getParamsMap().put("pTotal", pTotal);     
        operationBinding.getParamsMap().put("pCompdt", pCompdt);
        operationBinding.getParamsMap().put("pStartdt", pStartdt);
        operationBinding.getParamsMap().put("pHdrCode", pHdrCode);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
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
    public void reset() {
        OperationBinding operationBinding = getOperationBinding("initInvoiceAssembly");
        //invoke method
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }    
    }
    public Boolean createInvoiceRec() {
        Boolean chk = false;
        Boolean ret = false;
        Number nHeaderId = createInvoiceHeader();
        headerId = nHeaderId;
        if (nHeaderId != null) {
            // get binding for Results table
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("InvoiceAssemblyResultsIterator");

            for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                Row row = dciter.getRowAtRangeIndex(i);
                String sIsSelected = (String)row.getAttribute("isSelected");
                //System.out.println("sIsSelected ::" + sIsSelected);
                if ("Y".equals(sIsSelected)) {
                    // create Invoice Item record for every selected Work Order
                    String sWO = (String)row.getAttribute("EvtCode");
                    String sEquip = (String)row.getAttribute("EvtDesc");
                    String sVer = (String)row.getAttribute("Ver");
                    Double sLabor = (Double)row.getAttribute("Laborcost");
                    Double sPart = (Double)row.getAttribute("Parttotal");
                    Double sTax1 = (Double)row.getAttribute("Salestax");
                    DecimalFormat df = new DecimalFormat("#.00");
                    Double sTax =new Double(df.format(sTax1));
                    System.out.println("Inside createInvoiceRec sTax:" + sTax+" sTax1: "+sTax1);
                    Double sTotal = (Double)row.getAttribute("Wototal");
                    Date sCompdt = (Date)row.getAttribute("EvtCompleted");
                    Date sStartdt = (Date)row.getAttribute("EvtStart");
                    String sHrdCode = (String)row.getAttribute("EvtClass");
                    chk = createInvoiceItem(nHeaderId, sWO, sEquip, sVer, sLabor, sPart, sTax, sTotal, sCompdt, sStartdt, sHrdCode);

                    if (chk) {
                        // /* update Work Order status to RFI
                        Boolean RFIresult = updateWORFI(sWO);
                        //System.out.println("RFIresult 2:" + RFIresult);
                        ret = RFIresult;
                        //*/
                        //ret = true;
                    }
                }
            }
        }
        return ret;
    }
    public Boolean updateWORFI(String pevtcode) {
        OperationBinding operationBinding = getOperationBinding("updateWORFI");
        //optional
        operationBinding.getParamsMap().put("pevtcode", pevtcode);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        //System.out.println("Result 3:" + result);
    return result;    
    }
    public void showInvoiceNumber(Number invoicenum){

        Number nInvNum = getInvoiceNum(invoicenum);
        this.invoiceNumber.setValue(nInvNum.toString());
           
        //System.out.println("showInvoiceNumber():" + nInvNum.toString());
        }
    public String refreshCurrentPage() {
            FacesContext context = FacesContext.getCurrentInstance();
            String currentView = context.getViewRoot().getViewId();
            ViewHandler vh = context.getApplication().getViewHandler();
            UIViewRoot x = vh.createView(context, currentView);
            context.setViewRoot(x);
        return null;
    }
    public void UIfieldsDisable(Boolean bvalue) {
        //System.out.println("UIfieldsDisable()");
        this.isSelectedChk.setDisabled(bvalue);
        this.accountNumCrit.setDisabled(bvalue);
        this.woTypeCrit.setDisabled(bvalue);
        this.headerCdCrit.setDisabled(bvalue);
        this.weekEndingCrit.setDisabled(bvalue);
        this.checkAll.setDisabled(bvalue);
        this.searchButton.setDisabled(bvalue);
        this.resetButton.setDisabled(bvalue);
        this.createNewInvoiceButton.setDisabled(!bvalue);
    }
    public String Apply_action() {
        Boolean chk1 = false;
        Boolean chk2 = false;
        
        chk1 = checkSelected();

        if (chk1) {
            chk2 = createInvoiceRec();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Work Order(s) must be selected!", null));
        }

        if (chk1 && chk2){
            System.out.println("Inside Apply_action chk1: "+chk1+" chk2: "+chk2);
        BindingContainer bindings = getBindings();
        try{
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        } 
        }
        catch(Exception e){
            System.out.println("Inside Apply_action commit error: "+e.getMessage());
            e.printStackTrace();
        }
        showInvoiceNumber(headerId);
        UIfieldsDisable(true);  
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popInvConf.show(hints);
        }
        return null;
    }
    public String Cancel_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        reset();
        return null;
    }
    public Boolean checkSelected() {
        //System.out.println("checkSelected()");
        Boolean ret = false;
            // get binding for Results table
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("InvoiceAssemblyResultsIterator");

            for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                Row row = dciter.getRowAtRangeIndex(i);
                //System.out.println("HC");
                String sIsSelected = (String)row.getAttribute("isSelected");
                if ("Y".equals(sIsSelected)) {
                    ret = true;
                }
            }
        return ret;
    }
    public String createNewInvoice() {
        this.invoiceNumber.setValue("");
        UIfieldsDisable(false);  
        reset();
        refreshCurrentPage();
        return null;
    }
    
    public void setAccountNumCrit(RichSelectOneChoice accountNumCrit) {
        this.accountNumCrit = accountNumCrit;
    }
    public RichSelectOneChoice getAccountNumCrit() {
        return accountNumCrit;
    }
    public void setWoTypeCrit(RichSelectOneChoice woTypeCrit) {
        this.woTypeCrit = woTypeCrit;
    }
    public RichSelectOneChoice getWoTypeCrit() {
        return woTypeCrit;
    }
    public void setHeaderCdCrit(RichSelectOneChoice headerCdCrit) {
        this.headerCdCrit = headerCdCrit;
    }
    public RichSelectOneChoice getHeaderCdCrit() {
        return headerCdCrit;
    }
    public void setWeekEndingCrit(RichInputDate weekEndingCrit) {
        this.weekEndingCrit = weekEndingCrit;
    }
    public RichInputDate getWeekEndingCrit() {
        return weekEndingCrit;
    }
    public void setCheckAll(RichSelectBooleanCheckbox checkAll) {
        this.checkAll = checkAll;
    }
    public RichSelectBooleanCheckbox getCheckAll() {
        return checkAll;
    }
    public void setIsSelectedChk(RichSelectBooleanCheckbox isSelectedChk) {
        this.isSelectedChk = isSelectedChk;
    }
    public RichSelectBooleanCheckbox getIsSelectedChk() {
        return isSelectedChk;
    }
    public void setInvoiceNumber(RichInputText invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public RichInputText getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setPopInvConf(RichPopup popInvConf) {
        this.popInvConf = popInvConf;
    }
    public RichPopup getPopInvConf() {
        return popInvConf;
    }
    public void setCreateNewInvoiceButton(RichCommandMenuItem createNewInvoiceButton) {
        this.createNewInvoiceButton = createNewInvoiceButton;
    }
    public RichCommandMenuItem getCreateNewInvoiceButton() {
        return createNewInvoiceButton;
    }

    public void setSearchButton(RichButton searchButton) {
        this.searchButton = searchButton;
    }

    public RichButton getSearchButton() {
        return searchButton;
    }

    public void setResetButton(RichButton resetButton) {
        this.resetButton = resetButton;
    }

    public RichButton getResetButton() {
        return resetButton;
    }
}
