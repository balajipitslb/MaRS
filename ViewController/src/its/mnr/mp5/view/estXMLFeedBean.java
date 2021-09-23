package its.mnr.mp5.view;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.UploadedFile;
import oracle.jbo.domain.Number;

public class estXMLFeedBean {
    private RichPopup pendingChangePop;
    private RichPanelCollection pcol;
    private RichTable resTable;

    public estXMLFeedBean() {
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

    public void selectAllAction(ValueChangeEvent vce) {
        // Add event code here...
        Boolean newVal = (Boolean) vce.getNewValue();
        System.out.println("newVal: " + newVal.booleanValue());

        if (newVal) {
            onSelectAll();
        } else {
            onDeselectAll();
        }

    }

    public void onSelectAll() {
        BindingContainer bindings = getBindings();
        OperationBinding oper = bindings.getOperationBinding("selectAllEsts");
        oper.execute();

        if (!oper.getErrors().isEmpty()) {
            System.out.println("onSelectAll errors: " + oper.getErrors());
            return;
        }

        //selUnproAll.setDisabled(true);
    }
    /*
    public void onSelectAllUnprocessed() {
        BindingContainer bindings = getBindings();
        OperationBinding oper = bindings.getOperationBinding("selectAllUnprocessedEsts");
        oper.execute();

        if (!oper.getErrors().isEmpty()) {
            System.out.println("onSelectAll errors: " + oper.getErrors());
            return;
        }
        selAll.setDisabled(true);
    }
    */
    public void onDeselectAll() {
        // Add event code here...
        BindingContainer bindings = getBindings();
        OperationBinding oper = bindings.getOperationBinding("deselectAllEsts");
        oper.execute();

        if (!oper.getErrors().isEmpty()) {
            System.out.println("onSelectAll errors: " + oper.getErrors());
            return;
        }
        //  selAll.setDisabled(false);
        //  selUnproAll.setDisabled(false);
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

    public void add_action(ActionEvent ae) {
        if (isDirty()) {
            System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            OperationBinding oper = getOperationBinding("CreateInsert");
            oper.execute();
            if (!oper.getErrors().isEmpty()) {
                System.out.println("Inside add_action errors: " + oper.getErrors());
                return;
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

    public void commit_action() {
        OperationBinding operationBinding = getOperationBinding("Commit");
        Object result = operationBinding.execute();

        if (!operationBinding.getErrors().isEmpty()) {
            return;
        }
    }


    public void processEstimates(ActionEvent ae) {
        // Add event code here...
        String ret = "";

        BindingContainer bindings = getBindings();
      ////  System.out.println("Inside processEstimates");

        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("MrltEstimatexmlfeedViewIterator");
        //Get viewObject from Iterator
        //iter.executeQuery();
        ViewObject vo = iter.getViewObject();
        Row[] selr = vo.getFilteredRows("tSel", "Y");

        for (Row r : selr) {

         ////   System.out.println("Inside processEstimates Estid: " + r.getAttribute("Estid"));
         ////   System.out.println("Inside processEstimates Isprocessed: " + r.getAttribute("Isprocessed"));
            if (r.getAttributeIndexOf("Estid") != -1) {
                oracle.jbo.domain.Number estid = (oracle.jbo.domain.Number) r.getAttribute("Estid");
                String ver = (String)r.getAttribute("Ver");
                ret = estimate_action(estid,ver);
                if (ret != null) {
                    if (ret.equals("Yes"))
                        r.setAttribute("Isprocessed", "Y");
                    else
                        r.setAttribute("Isprocessed", "N");
                } else {
                    r.setAttribute("Isprocessed", "N");
                }
            }
        }
        commit_action();

        }


    public Boolean writeEstimateXMLTool(String sestid, String sver) {
        Boolean result=false;
        try{
        ////    System.out.println("Inside estXMLFeedBean writeEstimateXMLTool sestid: "+sestid+" sver: "+sver);
        OperationBinding operationBinding = getOperationBinding("writeEstimateXMLTool");
        operationBinding.getParamsMap().put("sestid", sestid);
            operationBinding.getParamsMap().put("sVer", sver);
        //invoke method
         result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        }
        catch(StackOverflowError soe){
            System.out.println("Inside writeEstimateXML catch error soe: "+soe.getMessage()+" cause: "+soe.getCause());
            return result;
        }
        return result;
    }

    public String estimate_action(oracle.jbo.domain.Number estid, String ver) {
      /////  System.out.println("Inside estimate_action estid: "+estid+" ver: "+ver);
        String ret = "No";

        Boolean writeEstimateChk = false;
        Boolean woToEstChk = false;
        Boolean woToCChk = false;
        Boolean updateEstimateChk = false;
        Boolean actionChk = false;
        Boolean chkReady = false;
        Boolean commitChk = false;
        List list = new ArrayList();

        oracle.jbo.domain.Number nEstid = estid;
      ////  System.out.println("Inside estimate_action nEstid: " + nEstid);
        if (nEstid != null) {
            String strEstid = nEstid.toString();
            //     chkReady = checkSelected();
       ////     System.out.println("Inside estimate_action strEstid: "+strEstid);
            // if (chkReady) {
            try {
                
                DCBindingContainer bindings =
                    (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding dc = bindings.findIteratorBinding("WOEventsIterator");
                ViewObjectImpl vo = (ViewObjectImpl) dc.getViewObject();
                if (vo != null) {
               ////     System.out.println("Inside estimate_action vo.getEstimatedRowCount()1: " +
               ////                        vo.getEstimatedRowCount());
                    vo.executeQuery();
                    /*
                    ViewCriteriaManager vcm = vo.getViewCriteriaManager();
                    ViewCriteria vc = vcm.getViewCriteria("WObyEvtCode");
                    vo.resetCriteria(vc);
                    */
                    vo.applyViewCriteria(null);
                    vo.executeQuery();

                  ////  System.out.println("Inside estimate_action vo.getEstimatedRowCount()2: " +
                  ////                     vo.getEstimatedRowCount());


                    vo.setApplyViewCriteriaName("WObyEstId");
                    vo.setNamedWhereClauseParam("WOEstidBind", strEstid);
                    vo.executeQuery();

                  ////  System.out.println("Inside estimate_action vo.getEstimatedRowCount()2: " +
                  ////                     vo.getEstimatedRowCount());
                    
                    Row currentRow = vo.getRowSet().first();
                    
                   // Row currentRow = vo.getCurrentRow();
               ////     System.out.println("Inside estimate_action currentRow: " + currentRow);
                    if (currentRow != null) {
                        String sEstid = (String) currentRow.getAttribute("ItsEstid");
                   ////     System.out.println("Inside estimate_action currentRow sEstid: " + sEstid);
                        String nWONum = (String) currentRow.getAttribute("EvtCode");
                        list.add(nEstid);
                   ////     System.out.println("Inside estimate_action nEstid: "+nEstid+" nWONum: "+nWONum);

                        // Generate Invoice XML output
                        if(sEstid!=null){
                      ////      System.out.println("Inside estimate_action calling writeEstimateXMLTool sEstid: "+sEstid);
                            writeEstimateChk = writeEstimateXMLTool(sEstid,ver);
                        }
                        ret = "Yes";
                    }

                }
            } catch (Exception e) {
                System.out.println("Inside estimate_action exception e: " + e.getMessage());

            }
        }
        return ret;
    }

    public void removeProcessedAction(ActionEvent ae) {
        BindingContainer bindings = getBindings();
        OperationBinding oper = bindings.getOperationBinding("removeProcessed");
        oper.execute();

        if (!oper.getErrors().isEmpty()) {
            System.out.println("removeProcessedAction errors: " + oper.getErrors());
            return;
        }
    }

    public void cvsFileUpload(ValueChangeEvent vce) {
        // Add event code here...
        UploadedFile file = (UploadedFile) vce.getNewValue();
        try {
            AdfFacesContext.getCurrentInstance().addPartialTarget(pcol);
            parseFile(file.getInputStream());

        } catch (IOException e) {
            // TODO add more
            System.out.println("Inside cvsFileUpload: file upload failed!");
        }
    }

    public void parseFile(java.io.InputStream file) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        String strLine = "";
        StringTokenizer st = null;
        int lineNumber = 0, tokenNumber = 0;
        Row rw = null;

        CollectionModel _tableModel = (CollectionModel) resTable.getValue();
        //the ADF object that implements the CollectionModel is JUCtrlHierBinding. It
        //is wrapped by the CollectionModel API
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        //Acess the ADF iterator binding that is used with ADF table binding
        DCIteratorBinding it = _adfTableBinding.getDCIteratorBinding();

        //read comma separated file line by line
        try {
            while ((strLine = reader.readLine()) != null) {
                lineNumber++;
                // create a new row skip the header  (header has linenumber 1)
                if (lineNumber > 1) {
                    rw = it.getNavigatableRowIterator().createRow();
                    rw.setNewRowState(Row.STATUS_INITIALIZED);
                    it.getNavigatableRowIterator().insertRow(rw);
                }

                //break comma separated line using ","
                st = new StringTokenizer(strLine, ",");
                while (st.hasMoreTokens()) {
                    //display csv values
                     tokenNumber++;

                    String theToken = st.nextToken();
                 ////   System.out.println("Line# " + lineNumber + ", Token # " + tokenNumber + ", Token : " + theToken);

                    if (lineNumber > 1) {
                        switch (tokenNumber) {
                        case 1: rw.setAttribute("Estid", theToken);
                                System.out.println("Inside parseFile Estid: "+theToken);
                        case 2: rw.setAttribute("Wonum", theToken);
                                System.out.println("Inside parseFile Wonum: "+theToken);
                        case 3: rw.setAttribute("Ver", theToken);
                                System.out.println("Inside parseFile Ver: "+theToken);
                        }
                    }

                }
                tokenNumber = 0;
            }
            //reset token number
            

        } catch (IOException e) {
            // TODO add more
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(resTable.getClientId(fctx),
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Content Error in Uploaded file",
                                             e.getMessage()));
        } catch (Exception e) {
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data Error in Uploaded file",
                                             e.getMessage()));

        }
    }

    public void setPendingChangePop(RichPopup pendingChangePop) {
        this.pendingChangePop = pendingChangePop;
    }

    public RichPopup getPendingChangePop() {
        return pendingChangePop;
    }

    public void deleteSelectedEsts(ActionEvent ae) {
        String ret = "";

        BindingContainer bindings = getBindings();
        System.out.println("Inside deleteSelectedEsts");
                                                       
        DCIteratorBinding iter = (DCIteratorBinding) bindings.get("MrltEstimatexmlfeedViewIterator");
        //Get viewObject from Iterator
        //iter.executeQuery();
        ViewObject vo = iter.getViewObject();
        Row[] selr = vo.getFilteredRows("tSel", "Y");

        for (Row r : selr) {

       ////     System.out.println("Inside deleteSelectedEsts Estid: " + r.getAttribute("Estid"));
       ////     System.out.println("Inside deleteSelectedEsts Isprocessed: " + r.getAttribute("Isprocessed"));
            if (r.getAttributeIndexOf("Estid") != -1) {
                Number estid = (Number) r.getAttribute("Estid");
          ////      System.out.println("Inside deleteSelectedEsts selected Estid: " + estid);
               r.remove();
            }
        }

        }

    public void setPcol(RichPanelCollection pcol) {
        this.pcol = pcol;
    }

    public RichPanelCollection getPcol() {
        return pcol;
    }

    public void setResTable(RichTable resTable) {
        this.resTable = resTable;
    }

    public RichTable getResTable() {
        return resTable;
    }
}
