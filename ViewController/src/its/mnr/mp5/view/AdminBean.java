package its.mnr.mp5.view;

import its.mnr.mp5.model.Util;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogListener;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;

import oracle.binding.AttributeBinding;

import oracle.jbo.Key;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.domain.Number;

import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class AdminBean {
    private RichInputText txtPath;
    private RichInputText txtFilename;
    private RichInputText txtText;
    private RichButton delFlagBttn;
    private RichPopup flagAssignedPP;
    private RichInputText selectedFactive;
    private RichPopup ppUpdPOBCd;
    private RichPopup pUpjobDetails;
    private RichPopup pupjobRunning;
    private RichInputText poNum;

    public AdminBean() {
    }

    public String Create_action() {
        writeTest();
        return null;
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

    public Boolean writeTest() {
        ////System.out.println("inside writeInvoiceCrXML()");
        Boolean ret = false;
        //java.util.Date date= new java.util.Date();
        //Timestamp time = new Timestamp(date.getTime());

        File testfile = new java.io.File((String)this.txtPath.getValue());
        String pPath = (String)this.txtPath.getValue();
        String filename = (String)this.txtFilename.getValue();
        String filenamelong = pPath + filename;

        Boolean bRet = testfile.canWrite();
        if (!bRet) {
            throw new JboException("System can not print: " + filenamelong);
        } else {
            try {
                Util.writeTextFile(filenamelong, (String)this.txtText.getValue());

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Created file: " + filenamelong, null));

                ret = true;
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                e.printStackTrace();
            }
        }

        /*Uncomment to print to screen
            Node n = vo.writeXML(-1,attrMap);
            PrintWriter pw = new PrintWriter(System.out);
            try {
                ((XMLNode)n).print(pw);
            } catch (IOException e) {
            }*/

        return ret;
    }

    public void setTxtPath(RichInputText txtPath) {
        this.txtPath = txtPath;
    }

    public RichInputText getTxtPath() {
        return txtPath;
    }

    public void setTxtFilename(RichInputText txtFilename) {
        this.txtFilename = txtFilename;
    }

    public RichInputText getTxtFilename() {
        return txtFilename;
    }

    public void setTxtText(RichInputText txtText) {
        this.txtText = txtText;
    }

    public RichInputText getTxtText() {
        return txtText;
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

    public void delFlag(ActionEvent actionEvent) {
        if (isDirty()) {
            BindingContainer bindings = getBindings();
            OperationBinding op = bindings.getOperationBinding("Execute");
            Object result = op.execute();
            if (!op.getErrors().isEmpty()) {
                return;
            }
            /*
            OperationBinding OperationBinding = bindings.getOperationBinding("Rollback");
            Object res = OperationBinding.execute();
            if (!OperationBinding.getErrors().isEmpty()) {
                return;
            }
        */
        } else {
            DCBindingContainer dcbindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding flagIter = (DCIteratorBinding)dcbindings.get("ActiveMrltFlagIterator");

            // Get an object representing the table and what may be selected within it
            ViewObject flagVo = flagIter.getViewObject();

            // Get selected row
            Row selRow = flagVo.getCurrentRow();
            //System.out.println("rowSelected: " + selRow.getAttribute("Fid"));
            Integer fid = new Integer(selRow.getAttribute("Fid").toString());
            //System.out.println("selRow.getAttribute(Fid): " + selRow.getAttribute("Fid") + " selRow.getAttribute(Fname): " + selRow.getAttribute("Fname"));
            OperationBinding op = getOperationBinding("isFlagUsedInWO");

            op.getParamsMap().put("Fidval", fid);
            Object result = op.execute();
            //System.out.println("result: " + result);

            if (!op.getErrors().isEmpty()) {
                return;
            }

            if (result != null) {
                if (result.equals("assigned")) {
                    //this.delFlagBttn.setDisabled(true);
                    // RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    //  flagAssignedPP.show(hints);
                    userModificationMsg("INFO", "Info", "Cannot delete flag as it is associated with a Work Order.");
                }

                else {
                    //this.delFlagBttn.setDisabled(false);
                    OperationBinding opDel = getOperationBinding("Delete");
                    Object resDel = opDel.execute();
                    //System.out.println("after del result: " + resDel);
                    if (!opDel.getErrors().isEmpty()) {
                        return;
                    }
                }
            }

        }
    }

    public void selectFlagRow(SelectionEvent selectionEvent) {
        ADFUtil.invokeEL("#{bindings.ActiveMrltFlag.collectionModel.makeCurrent}",
                         new Class[] { SelectionEvent.class }, new Object[] { selectionEvent });
        // get the selected row , by this you can get any attribute of that row
        Row selRow = (Row)ADFUtil.evaluateEL("#{bindings.ActiveMrltFlagIterator.currentRow}");
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

    public void Close_action(ActionEvent ae) {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
        } else {
            flagAssignedPP.hide();
        }

    }
    
    public void Close_actionUpdBCd(ActionEvent ae) {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
        } else {
            ppUpdPOBCd.hide();
        }

    }
    
    public void Close_actionPUpjobDetails(ActionEvent ae) {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
        } else {
            pUpjobDetails.hide();
        }    
    }
    
    public void Close_actionPUpjobRunning(ActionEvent actionEvent) {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
        } else {
            pupjobRunning.hide();
        }  
    }    
    
    public void setDelFlagBttn(RichButton delFlagBttn) {
        this.delFlagBttn = delFlagBttn;
    }

    public RichButton getDelFlagBttn() {
        return delFlagBttn;
    }

    public void setFlagAssignedPP(RichPopup flagAssignedPP) {
        this.flagAssignedPP = flagAssignedPP;
    }

    public RichPopup getFlagAssignedPP() {
        return flagAssignedPP;
    }

    public void setSelectedFactive(RichInputText selectedFactive) {
        this.selectedFactive = selectedFactive;
    }

    public RichInputText getSelectedFactive() {
        return selectedFactive;
    }


    public void setPpUpdPOBCd(RichPopup ppUpdPOBCd) {
        this.ppUpdPOBCd = ppUpdPOBCd;
    }

    public RichPopup getPpUpdPOBCd() {
        return ppUpdPOBCd;
    }

    public void setPUpjobDetails(RichPopup pUpjobDetails) {
        this.pUpjobDetails = pUpjobDetails;
    }

    public RichPopup getPUpjobDetails() {
        return pUpjobDetails;
    }

    public void setPupjobRunning(RichPopup pupjobRunning) {
        this.pupjobRunning = pupjobRunning;
    }

    public RichPopup getPupjobRunning() {
        return pupjobRunning;
    }
    public void invoice_action(DialogEvent dl) {
        //System.out.println("Inside invoice_action");
        DialogEvent.Outcome result = dl.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            Boolean writeInvoiceChk = false;
            Boolean woToINVChk = false;
            Boolean woToCChk = false;
            Boolean updateInvoiceChk = false;
            Boolean actionChk = false;
            Boolean chkReady = false;
            Boolean commitChk = false;
            List list = new ArrayList();

            if (poNum != null) {
                String nInv = (String)this.poNum.getValue();
                //System.out.println("Inside invoice_action nInv: "+nInv);
                if (nInv != null) {

                    //     chkReady = checkSelected();

                    // if (chkReady) {

                    DCBindingContainer bindings =
                        (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
                    DCIteratorBinding dc = bindings.findIteratorBinding("MrltInvoiceheaderViewIterator");
                    ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
                    if(vo!=null){
                    //System.out.println("Inside invoice_action vo.getEstimatedRowCount()1: "+vo.getEstimatedRowCount());
                    
                        vo.setApplyViewCriteriaName("getInvoiceByInvNum");

                        // set bind variable for ViewObject and execute
                        //System.out.println("Bind Variable Passed: " + nInv);
                        //        vo.ensureVariableManager().setVariableValue("WOEvtCodeBind", sWONum);
                        vo.setNamedWhereClauseParam("InvNumBind", nInv);
                        vo.executeQuery();
                    
                    //System.out.println("Inside invoice_action vo.getEstimatedRowCount()2: "+vo.getEstimatedRowCount());

                    Row currentRow = vo.getRowAtRangeIndex(0);
                        //System.out.println("Inside invoice_action currentRow: "+currentRow);
                    if (currentRow != null) {
                        Number Invnum = (Number)currentRow.getAttribute("Invnum");
                        //System.out.println("Inside invoice_action currentRow Invnum: "+Invnum);
                        String sType = (String)currentRow.getAttribute("Type");
                        Number nHeaderid = (Number)currentRow.getAttribute("Invoiceheaderid");
                        list.add(nInv);
                        //System.out.println("Inside invoice_action nInv: "+nInv+" nHeaderid: "+nHeaderid+" sType: "+sType);
                        //Issue Invoices
                        //   if ("ISSUE".equals(sAction)) {
                        // Generate Invoice XML output
                        if ("STD".equals(sType)) {
                            writeInvoiceChk = writeInvoiceStdXML(Invnum);
                        } else if ("CR".equals(sType)) {
                            writeInvoiceChk = writeInvoiceCrXML(Invnum);
                        } else if ("RTRO".equals(sType)) {
                            writeInvoiceChk = writeInvoiceRtroXML(Invnum);
                        } else if ("PEND".equals(sType)) {
                            writeInvoiceChk = writeInvoiceEstXML(Invnum);
                        }
                        /*
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
                                        ////System.out.println("WorkOrderRFIUpd failed");
                                        deleteInvoiceXMLFile(nInv, sType);
                                        WorkOrderRFIUpd(nHeaderid, "RFI");
                                    }
                                } else { // Update Invoice status okay.
                                    actionChk = true;
                                }
                            } else { //Update Invoice status failed. Delete XML file.
                                deleteInvoiceXMLFile(nInv, sType);
                            }
                        }*/
                        // }
                        /*
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
                                        ////System.out.println("WorkOrderRFIUpd failed");
                                    }
                                } else {
                                    actionChk = true;
                                }
                            }
                        }
                        */


                        //System.out.println("writeInvoiceXML chk:" + writeInvoiceChk);
                        //System.out.println("updateWORFI to INV chk:" + woToINVChk);
                        //System.out.println("updateWORFI to C chk:" + woToCChk);
                        //System.out.println("updateInvoiceStatus chk:" + updateInvoiceChk);

                        //TODO need to 1) undo status changes, 2) delete invoice(xml) created
                        //deleteInvoiceXMLFile(nInv, sType);
                    }
                    /*
                        if (actionChk) {
                            commitChk = commit();
                            //Display confirmation message
                            if (!list.isEmpty() && commitChk) {
                                String msg =
                                    "The following Invoice(s) have been " + ("ISSUE".equals(sAction) ? "Issued" : "Cancelled") + ":" +
                                    '\n';
                                for (int i = 0; i < list.size(); i++) {
                                    msg = msg + " #" + list.get(i) + '\n';
                                }
                
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
                            }
                        } else if (!actionChk || !commitChk) {
                            rollback();
                            String msg =
                                "The following Invoice(s)failed to " + ("ISSUE".equals(sAction) ? "Issue" : "Cancel") + ":" + '\n';
                            for (int i = 0; i < list.size(); i++) {
                                msg = msg + " #" + list.get(i) + '\n';
                            }
                
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
                        }
                        */
                                    /*
                          } else {
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null,
                                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Invoice(s) must be in READY status to perform " +
                                                                sAction + " action!", null));
                        }
               */
                    }
                }
            }
        }
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

    public void setPoNum(RichInputText poNum) {
        this.poNum = poNum;
    }

    public RichInputText getPoNum() {
        return poNum;
    }


}

