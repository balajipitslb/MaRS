package its.mnr.mp5.view;

import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
//import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

public class invoiceCreditBean {
    private RichInputText invoiceNumber;
    private RichSelectBooleanCheckbox internalOnlyChk;
    private RichSelectBooleanCheckbox applyInvoiceCreditChk;
    private RichInputText creditAmt;
    private RichTable resultsTable;
    private RichInputText laborCr;
    private RichInputText partCr;    
    private RichInputText taxCr;
    private RichCommandMenuItem createNewMenu;
    private RichButton searchBtn;
    private RichButton resetBtn;
    private RichPopup popInvConf;
    private Number headerId;
    private RichButton applyBtn;
    private RichInputListOfValues refInvoiceId;


    public invoiceCreditBean() {
    }

    //help methods
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
        return getDCBindingContainer().getOperationBinding(operationName);
    }    
    public void refInvValChangeLstnr(ValueChangeEvent vce) {
      //  Double oldVal =  (Double)vce.getOldValue();
      //  Double newVal = (Double)vce.getNewValue();
        System.out.println("Inside refInvValChangeLstnr oldVal: "+vce.getOldValue()+" newVal: "+vce.getNewValue());
    }
    public Object resolveExpression(String expression) {
        System.out.println("Inside resolveExpression");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        System.out.println("Inside resolveExpression exp: "+expression+" valueExp: "+valueExp+" valueExp.getValue(elContext): "+valueExp.getValue(elContext));
        return valueExp.getValue(elContext);
    }  
    
    public String search_action() {
        System.out.println("Inside search_action refInvoiceId invoiceCreditBean: "+this.refInvoiceId.getValue());
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("executeInvoiceCreditSearch");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        Double dInvcr = (Double)resolveExpression("#{bindings.Invcr.inputValue}");
        if(dInvcr!=null){
            System.out.println("System.out.println Inside search_action dInvcr: "+dInvcr);
            if (new Double(0).compareTo(dInvcr)> 0){
                System.out.println("System.out.println Inside search_action INV");
                    enableCrLocation("INV");
            }else if(hasWOCredit()){
                System.out.println("System.out.println Inside search_action WO");
                enableCrLocation("WO");
            }else {            
                System.out.println("System.out.println Inside search_action BOTH");
                enableCrLocation("BOTH");
            }
        }
        return null;
    }

    public String reset_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("initInvoiceCredit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        this.applyInvoiceCreditChk.setSelected(false);
        this.applyInvoiceCreditChk.setReadOnly(true);
        return null;
    }
    public String cancel_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        reset_action();
        return null;
    }
    public String apply_action() {
        Boolean chk = false;
        chk = createInvoiceRec();
        if (chk) {
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

    public void applyInvoiceCreditListener(ValueChangeEvent valueChangeEvent) {
        Boolean bOld = (Boolean)valueChangeEvent.getOldValue();
        Boolean bNew = (Boolean)valueChangeEvent.getNewValue();
        //System.out.println("from " + bOld + " to " + bNew);
        clearCredits();
        if (bNew) {
            //enableCrLocation("INV");
            this.creditAmt.setDisabled(false);
            this.laborCr.setReadOnly(true);
            this.partCr.setReadOnly(true);
            this.taxCr.setReadOnly(true);
        } else if (!bNew) {
            //enableCrLocation("WO");
            this.creditAmt.setDisabled(true);
            this.laborCr.setReadOnly(false);
            this.partCr.setReadOnly(false);
            this.taxCr.setReadOnly(false);
        }
        this.resultsTable.resetStampState();
        AdfFacesContext.getCurrentInstance().addPartialTarget(resultsTable);
    }

    /**
     * @return
     */
    public Boolean createInvoiceRec() {
        Boolean chk = false;
        Boolean ret = false;
        Number nHeaderId = createInvoiceHeader();
        headerId = nHeaderId;
        if (nHeaderId != null && applyInvoiceCreditChk.isSelected()) {
            ret = true;
        } else if (nHeaderId != null && !applyInvoiceCreditChk.isSelected()) {
            // get binding for Results table
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("InvoiceCreditResultsIterator");

            for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                Row row = dciter.getRowAtRangeIndex(i);
                Double dLaborCr = (Double)row.getAttribute("LaborCr");
                Double dPartCr = (Double)row.getAttribute("PartCr");
                Double dTaxCr = (Double)row.getAttribute("TaxCr");
                //System.out.println(dLaborCr + ":" + dPartCr);
                if (dLaborCr != null || dPartCr != null || dTaxCr != null) {
                    // create Invoice Item record for every selected Work Order
                    String sWO = (String)row.getAttribute("Wo");
                    Double dLabor = (Double)row.getAttribute("Labor");
                    Double dPart = (Double)row.getAttribute("Part");
                    Double dTax = (Double)row.getAttribute("Tax");
                    Double dTaxPer = null;
                    Double dTotal = (Double)row.getAttribute("CurrentCr");
                    String sHrdCode = (String)row.getAttribute("Hdrcode");
                    chk = createInvoiceCrItem(nHeaderId, sWO, dLabor, dPart, dTax, dTaxPer, dLaborCr, dPartCr, dTaxCr, dTotal,sHrdCode);
                    //System.out.println("chk:" + chk);
                    ret = chk;
                }
            }
        }
        return ret;
    }
    public Number createInvoiceHeader() {
        OperationBinding operationBinding = getOperationBinding("createInvoiceHeader");
        Number result = (Number)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }  
    public Boolean createInvoiceCrItem(Number pHeaderId,String pWO,Double pLabor, Double pPart, Double pTax, 
                                       Double pTaxPer, Double pLaborCr, Double pPartCr, Double pTaxCr, Double pTotal, String pHdrCode) {
        OperationBinding operationBinding = getOperationBinding("createInvoiceCrItem");
        operationBinding.getParamsMap().put("pHeaderId", pHeaderId);
        operationBinding.getParamsMap().put("pWO", pWO);
        operationBinding.getParamsMap().put("pLabor", pLabor);
        operationBinding.getParamsMap().put("pPart", pPart);
        operationBinding.getParamsMap().put("pTax", pTax);
        operationBinding.getParamsMap().put("pTaxPer", pTaxPer);
        operationBinding.getParamsMap().put("pLaborCr", pLaborCr);
        operationBinding.getParamsMap().put("pPartCr", pPartCr);
        operationBinding.getParamsMap().put("pTaxCr", pTaxCr);
        operationBinding.getParamsMap().put("pTotal", pTotal); 
        operationBinding.getParamsMap().put("pHdrCode", pHdrCode);
        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public void showInvoiceNumber(Number invoicenum){
        //System.out.println("showInvoiceNumber()");
        Number nInvNum = getInvoiceNum(invoicenum);
        this.invoiceNumber.setDisabled(false);
        this.invoiceNumber.setValue(nInvNum.toString());
        this.invoiceNumber.setReadOnly(true);
        }
    public Number getInvoiceNum(Number pHeaderId) {
        OperationBinding operationBinding = getOperationBinding("getInvoiceNum");
        operationBinding.getParamsMap().put("pHeaderId", pHeaderId);
        //invoke method
        Number result = (Number)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public void clearCredits() {
        // get the binding container
        //System.out.println("Clearing current Credit(s)");
        BindingContainer dcbindings = getDCBindingContainer();
        DCIteratorBinding dciter = (DCIteratorBinding)dcbindings.get("InvoiceCreditResultsIterator");
        RowSetIterator rit = dciter.getRowSetIterator();
        rit.reset();

        if (rit.first() != null) {
            Row r = rit.first();
            //System.out.println(r.getAttribute("Wo") + ":" + r.getAttribute("LaborCr")+ ":" + r.getAttribute("PartCr"));
            r.setAttribute("InvoiceCr", null);
            r.setAttribute("LaborCr", null);
            r.setAttribute("PartCr", null);
            r.setAttribute("TaxCr", null);
            //System.out.println(r.getAttribute("Wo") + ":" + r.getAttribute("LaborCr")+ ":" + r.getAttribute("PartCr"));
        }
        while (rit.hasNext()) {
            Row r = rit.next();
            if (r != null) {
                //System.out.println(r.getAttribute("Wo") + ":" + r.getAttribute("LaborCr")+ ":" + r.getAttribute("PartCr"));
                r.setAttribute("InvoiceCr", null);
                r.setAttribute("LaborCr", null);
                r.setAttribute("PartCr", null);
                r.setAttribute("TaxCr", null);
                //System.out.println(r.getAttribute("Wo") + ":" + r.getAttribute("LaborCr")+ ":" + r.getAttribute("PartCr"));
            }
        }

        //AdfFacesContext.getCurrentInstance().addPartialTarget(panelCollection);
    }
    public String createNewInvoice() {
        this.invoiceNumber.setValue("");
        this.applyInvoiceCreditChk.setSelected(false);
        this.applyInvoiceCreditChk.setReadOnly(true);
        UIfieldsDisable(false);  
        reset_action();
        refreshCurrentPage();
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
    public void UIfieldsDisable(Boolean bvalue) {
        this.internalOnlyChk.setDisabled(bvalue);
        this.creditAmt.setDisabled(bvalue);
        this.laborCr.setDisabled(bvalue);
        this.partCr.setDisabled(bvalue);
        this.taxCr.setDisabled(bvalue);
        this.searchBtn.setDisabled(bvalue);
        this.resetBtn.setDisabled(bvalue);
        this.createNewMenu.setDisabled(!bvalue);
        this.applyBtn.setDisabled(bvalue);
    }
    public void enableCrLocation(String sloc) {
        if ("INV".equals(sloc)) {
            this.applyInvoiceCreditChk.setSelected(true);
            this.applyInvoiceCreditChk.setReadOnly(true);
            this.creditAmt.setDisabled(false);
            this.laborCr.setReadOnly(true);
            this.partCr.setReadOnly(true);
            this.taxCr.setReadOnly(true);
            //System.out.println("Enable Invoice Credit(s)"  );
        } else if ("WO".equals(sloc)) {
            this.applyInvoiceCreditChk.setSelected(false);
            this.applyInvoiceCreditChk.setReadOnly(true);
            this.creditAmt.setDisabled(true);
            this.laborCr.setReadOnly(false);
            this.partCr.setReadOnly(false);
            this.taxCr.setReadOnly(false);
            //System.out.println("Enable Work Order Credit(s)" );
        }else if ("BOTH".equals(sloc)) {
            this.applyInvoiceCreditChk.setSelected(false);
            this.applyInvoiceCreditChk.setReadOnly(false);
            this.creditAmt.setDisabled(true);
            this.laborCr.setReadOnly(false);
            this.partCr.setReadOnly(false);
            this.taxCr.setReadOnly(false);
            //System.out.println("No Previous Credit(s)" );
        }
        resultsTable.resetStampState();
        AdfFacesContext.getCurrentInstance().addPartialTarget(resultsTable);
    }
    public Boolean hasWOCredit() {
        Boolean ret = false;
            // get binding for Results table
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("InvoiceCreditResultsIterator");

            for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                Row row = dciter.getRowAtRangeIndex(i);
                Double dWoCr = (Double)row.getAttribute("Wototalcr");
                if (dWoCr != null && new Double (0).compareTo(dWoCr)> 0) {
                    //System.out.println("hasWOCredit():" + dWoCr);
                    return true;
                }
            }
        return ret;
    }
    
    
    public void setInvoiceNumber(RichInputText invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public RichInputText getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInternalOnlyChk(RichSelectBooleanCheckbox internalOnlyChk) {
        this.internalOnlyChk = internalOnlyChk;
    }

    public RichSelectBooleanCheckbox getInternalOnlyChk() {
        return internalOnlyChk;
    }

    public void setApplyInvoiceCreditChk(RichSelectBooleanCheckbox applyInvoiceCreditChk) {
        this.applyInvoiceCreditChk = applyInvoiceCreditChk;
    }

    public RichSelectBooleanCheckbox getApplyInvoiceCreditChk() {
        return applyInvoiceCreditChk;
    }

    public void setCreditAmt(RichInputText creditAmt) {
        this.creditAmt = creditAmt;
    }

    public RichInputText getCreditAmt() {
        return creditAmt;
    }

    public void setResultsTable(RichTable resultsTable) {
        this.resultsTable = resultsTable;
    }

    public RichTable getResultsTable() {
        return resultsTable;
    }

    public void setLaborCr(RichInputText laborCr) {
        this.laborCr = laborCr;
    }

    public RichInputText getLaborCr() {
        return laborCr;
    }

    public void setPartCr(RichInputText partCr) {
        this.partCr = partCr;
    }

    public RichInputText getPartCr() {
        return partCr;
    }

    public void setCreateNewMenu(RichCommandMenuItem createNewMenu) {
        this.createNewMenu = createNewMenu;
    }

    public RichCommandMenuItem getCreateNewMenu() {
        return createNewMenu;
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

    public void setPopInvConf(RichPopup popInvConf) {
        this.popInvConf = popInvConf;
    }

    public RichPopup getPopInvConf() {
        return popInvConf;
    }

    public void setApplyBtn(RichButton applyBtn) {
        this.applyBtn = applyBtn;
    }

    public RichButton getApplyBtn() {
        return applyBtn;
    }

    public void setTaxCr(RichInputText taxCr) {
        this.taxCr = taxCr;
    }

    public RichInputText getTaxCr() {
        return taxCr;
    }

    public void setRefInvoiceId(RichInputListOfValues refInvoiceId) {
        this.refInvoiceId = refInvoiceId;
    }

    public RichInputListOfValues getRefInvoiceId() {
        return refInvoiceId;
    }

}
