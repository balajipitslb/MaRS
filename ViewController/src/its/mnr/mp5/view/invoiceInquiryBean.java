package its.mnr.mp5.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.adf.view.rich.event.ContextInfoEvent;

import oracle.jbo.domain.Number;
import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class invoiceInquiryBean {
    private RichTable invoiceTable;
    private RichPopup pdtlCr;
    private RichPopup pdtlStd;
    private RichPopup pdtlRtro;

    public invoiceInquiryBean() {
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
    public Object resolveExpression(String expression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        return valueExp.getValue(elContext);
    }
    
    public void invoice_action(ActionEvent actionEvent) {
        Boolean writeInvoiceChk = false;
        Boolean woToINVChk = false;
        Boolean woToCChk = false;
        Boolean updateInvoiceChk = false;
        Boolean actionChk = false;
        Boolean chkReady = false;
        Boolean commitChk = false;
        List list = new ArrayList();
        String sAction = (String)actionEvent.getComponent().getAttributes().get("invAction");

        chkReady = checkSelected();

        if (chkReady) {
            RowKeySet selectedEmps = getInvoiceTable().getSelectedRowKeys();
            Iterator selectedEmpIter = selectedEmps.iterator();
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding empIter = bindings.findIteratorBinding("MrltInvoiceheaderViewIterator");
            RowSetIterator empRSIter = empIter.getRowSetIterator();
            while (selectedEmpIter.hasNext()) {
                Key key = (Key)((List)selectedEmpIter.next()).get(0);
                Row currentRow = empRSIter.getRow(key);
                String sType = (String)currentRow.getAttribute("Type");
                Number nInv = (Number)currentRow.getAttribute("Invnum");
                Number nHeaderid = (Number)currentRow.getAttribute("Invoiceheaderid");
                list.add(nInv);
                //System.out.println(nInv);
                //Issue Invoices
                if ("ISSUE".equals(sAction)) {
                    // Generate Invoice XML output
                    if ("STD".equals(sType)) {
                        writeInvoiceChk = writeInvoiceStdXML(nInv);
                    } else if ("CR".equals(sType)) {
                        writeInvoiceChk = writeInvoiceCrXML(nInv);
                    } else if ("RTRO".equals(sType)) {
                        writeInvoiceChk = writeInvoiceRtroXML(nInv);
                    } else if ("EST".equals(sType)) {
                        writeInvoiceChk = writeInvoiceEstXML(nInv);
                    }

                    // Update Invoices from READY status to ISSUED
                    if (writeInvoiceChk) {
                        updateInvoiceChk = updateInvoiceStatus(nInv, "ISSUED");
                        if (updateInvoiceChk) {
                            // Update Workorders from RFI status to INVOICED
                            if ("STD".equals(sType)) {
                                woToINVChk = WorkOrderRFIUpd(nHeaderid, "INV");
                                if (woToINVChk) {
                                    actionChk = true;
                                } else { //WO Update failed. Undo Status and delete XML file.
                                    //System.out.println("WorkOrderRFIUpd failed");
                                    deleteInvoiceXMLFile(nInv, sType);
                                    WorkOrderRFIUpd(nHeaderid, "RFI");
                                }
                            } else { // Update Invoice status okay.
                                actionChk = true;
                            }
                        } else { //Update Invoice status failed. Delete XML file.
                            deleteInvoiceXMLFile(nInv, sType);
                        }
                    }
                }
                //Cancel Invoices
                else if ("CANCEL".equals(sAction)) {
                    // Update Invoices from READY status to CANC
                    updateInvoiceChk = updateInvoiceStatus(nInv, "CANC");
                    if (updateInvoiceChk) {
                        // Update Workorders from RFI status back to COMPLETED
                        if ("STD".equals(sType)) {
                            woToCChk = WorkOrderRFIUpd(nHeaderid, "C");
                            if (woToCChk) {
                                actionChk = true;
                            } else {
                                //System.out.println("WorkOrderRFIUpd failed");
                            }
                        } else {
                            actionChk = true;
                        }
                    }
                }
                

                //System.out.println("writeInvoiceXML chk:" + writeInvoiceChk);
                //System.out.println("updateWORFI to INV chk:" + woToINVChk);
                //System.out.println("updateWORFI to C chk:" + woToCChk);
                //System.out.println("updateInvoiceStatus chk:" + updateInvoiceChk);
                
                //TODO need to 1) undo status changes, 2) delete invoice(xml) created
                //deleteInvoiceXMLFile(nInv, sType);
            }
            if (actionChk) {
                commitChk = commit();
                //Display confirmation message
                if (!list.isEmpty() && commitChk) {
                    String msg =
                        "The following Invoice(s) have been " + ("ISSUE".equals(sAction) ? "Issued" : "Cancelled") +
                        ":" + '\n';
                    for (int i = 0; i < list.size(); i++) {
                        msg = msg + " #" + list.get(i) + '\n';
                    }

                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
                }
            }else if (!actionChk || !commitChk){                                                               
                    rollback();                                              
                    String msg =
                        "The following Invoice(s)failed to " + ("ISSUE".equals(sAction) ? "Issue" : "Cancel") +
                        ":" + '\n';
                    for (int i = 0; i < list.size(); i++) {
                        msg = msg + " #" + list.get(i) + '\n';
                    }

                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
                }
        }else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Invoice(s) must be in READY status to perform " + sAction + " action!", null));
        }
    }
    public Boolean deleteInvoiceXMLFile(Number pInvNum, String pType) {
        Boolean result = false;
        OperationBinding operationBinding = getOperationBinding("deleteInvoiceXMLFile");
        operationBinding.getParamsMap().put("pInvNum", pInvNum);
        operationBinding.getParamsMap().put("pType", pType);
        //invoke method
        result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public Boolean writeInvoiceStdXML(Number pInvNum) {
        Boolean result = false;
        OperationBinding operationBinding = getOperationBinding("writeInvoiceStdXML");
        operationBinding.getParamsMap().put("pInvNum", pInvNum);
        //invoke method
        result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public Boolean writeInvoiceCrXML(Number pInvNum) {
        Boolean result = false;
        OperationBinding operationBinding = getOperationBinding("writeInvoiceCrXML");
        operationBinding.getParamsMap().put("pInvNum", pInvNum);
        //invoke method
        result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public Boolean writeInvoiceRtroXML(Number pInvNum) {
        Boolean result = false;
        OperationBinding operationBinding = getOperationBinding("writeInvoiceRtroXML");
        operationBinding.getParamsMap().put("pInvNum", pInvNum);
        //invoke method
        result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public Boolean writeInvoiceEstXML(Number pInvNum) {
        Boolean result = false;
        OperationBinding operationBinding = getOperationBinding("writeInvoiceEstXML");
        operationBinding.getParamsMap().put("pInvNum", pInvNum);
        //invoke method
        result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    public Boolean WorkOrderRFIUpd(Number pInvHdrId, String pNewStatus) {
        Boolean ret = false;
        OperationBinding operationBinding = getOperationBinding("WorkOrderRFIUpd");
        operationBinding.getParamsMap().put("pInvHdrId", pInvHdrId);
        operationBinding.getParamsMap().put("pNewStatus", pNewStatus);
        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        ret = ("TRUE".equals(result)? true :false);
    return ret;    
    }
    public Boolean updateInvoiceStatus(Number pInvNum, String newStatus) {
        Boolean result = false;
        OperationBinding operationBinding = getOperationBinding("updateInvoiceStatus");
        operationBinding.getParamsMap().put("pInvNum", pInvNum);
        operationBinding.getParamsMap().put("newStatus", newStatus);
        //invoke method
        result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }
    public Boolean checkSelected() {
        //System.out.println("checkSelected()");
        Boolean ret = false;
        RowKeySet selectedEmps = getInvoiceTable().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding empIter = bindings.findIteratorBinding("MrltInvoiceheaderViewIterator");
        RowSetIterator empRSIter = empIter.getRowSetIterator();
        while (selectedEmpIter.hasNext()) {
            Key key = (Key)((List)selectedEmpIter.next()).get(0);
            Row currentRow = empRSIter.getRow(key);
            String sStatus = (String)currentRow.getAttribute("Status");
            //System.out.println(sStatus);
            if ("READY".equals(sStatus)) {
                ret = true;
            } else {
                return false;
            }
        }
        return ret;
    }
    public Boolean commit() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return false;
        }        
        return true;
    }
    public Boolean rollback() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return false;
        }        
        return true;
    }
    public void contextInfoListener(ContextInfoEvent contextInfoEvent) {
        String sType = (String)resolveExpression("#{row.Type}");
        //System.out.println("sType:" + sType);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        if ("CR".equals(sType)) {
            pdtlCr.show(hints);
        } else if ("STD".equals(sType) || "EST".equals(sType)) {
            pdtlStd.show(hints);
        } else if ("RTRO".equals(sType)) {
            pdtlRtro.show(hints);
        }
    }
    
    public void setInvoiceTable(RichTable invoiceTable) {
        this.invoiceTable = invoiceTable;
    }

    public RichTable getInvoiceTable() {
        return invoiceTable;
    }

    public void setPdtlCr(RichPopup pdtlCr) {
        this.pdtlCr = pdtlCr;
    }

    public RichPopup getPdtlCr() {
        return pdtlCr;
    }

    public void setPdtlStd(RichPopup pdtlStd) {
        this.pdtlStd = pdtlStd;
    }

    public RichPopup getPdtlStd() {
        return pdtlStd;
    }

    public void setPdtlRtro(RichPopup pdtlRtro) {
        this.pdtlRtro = pdtlRtro;
    }

    public RichPopup getPdtlRtro() {
        return pdtlRtro;
    }
}
