package its.mnr.mp5.view;

import its.mnr.mp5.vo.parts.PartStockImpl;

import its.mnr.mp5.vo.parts.PartsBinstockViewImpl;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.QueryEvent;

import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class PartsMaintenanceBean {
    private RichTable resultsTable;
    private RichColumn newColumns;
    private RichSelectBooleanCheckbox transferPartChk;
    private RichInputListOfValues newBin;
    private RichInputListOfValues newLot;
    private RichTable resultsSearchTable;
    private RichShowDetailItem tabStock;
    private RichShowDetailItem tabComment;
    private RichShowDetailItem tabRecord;
    private RichShowDetailItem tabSupplier;
    private RichInputText newQty;
    private RichPanelTabbed panelTabbed;
    private RichPanelHeader panelHeader;
    private RichInputText curBin;
    private RichInputText curLot;
    private RichInputText curQty;
    private RichSelectBooleanCheckbox transferLine;
    private RichColumn transferLineCol;
    private RichButton deleteBinstockButton;

    public PartsMaintenanceBean() {
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

    public String commitAction() {
        doBinTransfer();
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }   
        return null;
    }
    
    public void processQuery(QueryEvent queryEvent) {
        QueryDescriptor qdes = queryEvent.getDescriptor();
        //System.out.println("NAME " + qdes.getName());
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
            ADFUtil.invokeMethodExpressionQueryEvent("#{bindings.PartSearchAllQuery.processQuery}", queryEvent);
            
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.resultsSearchTable);
           /* String sFirstWO = getFirstWOResult();        
            if (sFirstWO != null) {
                syncWOMasterDetail(sFirstWO);
            }*/
        }
        //showPartInBinsCols(false);
    }

    public void resultsPartTableSelectionListener(SelectionEvent selectionEvent) {
        String sPartCode = (String)ADFUtil.evaluateEL("#{bindings.ParCode.inputValue}");
        if (isDirty()) {
            if (StringUtils.isBlank(sPartCode)) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            }
        } else {
            ADFUtil.invokeMethodExpressionSelectionEvent("#{bindings.PartsMaintView.collectionModel.makeCurrent}", selectionEvent);
            //System.out.println("WOEvents Selection Listener");
            //syncWOMasterDetailCurrentRow();
        }
        //showPartInBinsCols(false);
    }

    public void tabListener(DisclosureEvent disclosureEvent) {
        if (isDirty() && disclosureEvent.isExpanded()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));

        }
    }
    
    public String onRollback() {
        String sParCode = (String)ADFUtil.evaluateEL("#{bindings.ParCode.inputValue}");
        String sAddCode = (String)ADFUtil.evaluateEL("#{bindings.AddCode.inputValue}");
        BindingContainer bindings = getBindings();
        if (StringUtils.isBlank(sParCode)) {
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {

            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("PartsMaintViewIterator");
            Row rowKey = iter.getCurrentRow();

            DCIteratorBinding iterCmt = (DCIteratorBinding)getDCBindingContainer().get("PartCommentIterator");
            Row rowCmt = iterCmt.getCurrentRow();
            

            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }

            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                try {
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                } catch (RowNotFoundException ex) {
                    //System.out.println("rowKey RowNotFoundException");
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    //iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }

            if (StringUtils.isNotBlank(sAddCode)) {
                if (rowCmt != null) {
                    Key parentKeyCmt = rowCmt.getKey();
                    try {
                        iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                    } catch (RowNotFoundException ex) {
                        //System.out.println(" rowCmt RowNotFoundException");
                        iterCmt.getViewObject().applyViewCriteria(null);
                        iterCmt.executeQuery();
                        //iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                    }
                }
            }
        }

        //refresh view
        showPartInBinsCols(false);
        return null;
    }

    public void applyTransferPartListener(ValueChangeEvent valueChangeEvent) {
        Boolean bOld = (Boolean)valueChangeEvent.getOldValue();
        Boolean bChecked = (Boolean)valueChangeEvent.getNewValue();
        //System.out.println("from " + bOld + " to " + bNew);
        //clearCredits();
        if (isDirty() && bChecked) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));

        } else {
            if (bChecked) {
                showPartInBinsCols(true);
            } else if (!bChecked) {
                showPartInBinsCols(false);
            }
        }
    }

    public String doBinTransfer() {
        String chk = "";
        String ret = "";
        if (transferPartChk.isSelected()) {
            System.out.println("doBinTransfer()");
            Boolean validateOK = validateBin2Bin();

            if (validateOK) {
                BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("PartsBinstockViewIterator");
                //System.out.println("transferPartChk.isSelected()");
                for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                    Row row = dciter.getRowAtRangeIndex(i);
                    Boolean bTransferLine = ((Boolean)row.getAttribute("tTransferLine") == null ? false : true);
                    String pOBin = (String)row.getAttribute("BisBin");
                    String pOLot = (String)row.getAttribute("BisLot");
                    String pNBin = (String)row.getAttribute("tNewBin");
                    String pNLot = (String)row.getAttribute("tNewLot");
                    Number pNQty = (Number)row.getAttribute("tNewQty");
                    String pPart = (String)row.getAttribute("BisPart");
                    String pPartOrg = (String)row.getAttribute("BisPartOrg");
                    System.out.println("pPartOrg: " + pPartOrg);                        
                    String pStore = (String)row.getAttribute("BisStore");
                    //System.out.println(pOBin + ":" + pOLot  + ":" + pNBin  + ":" + pNQty  + ":" + pPart  + ":" + pPartOrg  + ":" + pStore );
                    if (bTransferLine && pOBin != null && pOLot != null && pNBin != null && pNLot != null && pNQty != null &&
                        pPart != null && pPartOrg != null && pStore != null) {
                        chk = BinTransfer(pOBin, pOLot, pNBin, pNLot, pNQty, pPart, pPartOrg, pStore);
                        //System.out.println("chk:" + chk);
                        ret = chk;
                        System.out.println("chk: " + chk);
                    }
                }
                showPartInBinsCols(false);
                refreshBinStock();
            }
            //refreshCurrentPage();
            //getTabStock().setDisclosed(true);
            //getTabRecord().setDisclosed(false);
            //AdfFacesContext.getCurrentInstance().addPartialTarget(getPanelTabbed());
            //AdfFacesContext.getCurrentInstance().addPartialTarget(getPanelHeader());
            //ActionEvent actionEvent = new ActionEvent(getTabStock());
            //actionEvent.queue();
            //nullPartInBinsCols();
        }
        return ret;
    }

    public void switchTabs(ActionEvent actionEvent) {
        getTabStock().setDisclosed(true);
        getTabRecord().setDisclosed(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(getPanelTabbed());
        //AdfFacesContext.getCurrentInstance().addPartialTarget(getPanelHeader());
    }
    
    
    public String BinTransfer(String pOBin, String pOLot, String pNBin, String pNLot, Number pNQty, String pPart, String pPartOrg, String pStore) {
        //System.out.println("BinTransfer");
        OperationBinding operationBinding = getOperationBinding("BinTransfer");
        operationBinding.getParamsMap().put("pOBin", pOBin);
        operationBinding.getParamsMap().put("pOLot", pOLot);
        operationBinding.getParamsMap().put("pNBin", pNBin);
        operationBinding.getParamsMap().put("pNLot", pNLot);
        operationBinding.getParamsMap().put("pNQty", pNQty);
        operationBinding.getParamsMap().put("pPart", pPart);
        operationBinding.getParamsMap().put("pPartOrg", pPartOrg);
        operationBinding.getParamsMap().put("pStore", pStore);
        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
    return result;    
    }
    
    public void showPartInBinsCols(Boolean show) {
        if (!show) {
            /*
            this.newBin.resetValue();
            this.newLot.resetValue();
            this.newQty.resetValue();
            this.curBin.setValue(curBin.getValue());
            this.curLot.setValue(curLot.getValue());
            this.curQty.setValue(curQty.getValue());
            this.newBin.setValue(null);
            this.newLot.setValue(null);
            this.newQty.setValue(null);
            */
            this.newColumns.setVisible(false);
            this.transferLineCol.setVisible(false);
            this.transferLine.resetValue();
            this.deleteBinstockButton.setDisabled(false);
            //this.transferLine.setDisabled(true);
            this.transferPartChk.resetValue();
            //this.transferPartChk.setSelected(false);
        } else if (show) {
            this.newColumns.setVisible(true);
            this.transferLineCol.setVisible(true);
            this.deleteBinstockButton.setDisabled(true);
            //this.transferLine.setDisabled(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(deleteBinstockButton);
        this.resultsTable.resetStampState();
        AdfFacesContext.getCurrentInstance().addPartialTarget(resultsTable);
    }
    
    public void refreshBinStock(){
            //System.out.println("refreshStock()");
            DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.PartsBinstockViewIterator}");
            ViewObject vo = dc.getViewObject();
            PartsBinstockViewImpl impl = (PartsBinstockViewImpl)vo;
            Key skey = impl.getCurrentRow().getKey();
            //impl.executeEmptyRowSet();
            impl.executeQuery();
            //impl.setCurrentRow(impl.findByKey(skey , 1)[0]); 
            impl.setCurrentRow(impl.findByKey(skey , 1)[0]); 
            //vo.reset();

    }

    public String refreshCurrentPage() {
            FacesContext context = FacesContext.getCurrentInstance();
            String currentView = context.getViewRoot().getViewId();
            ViewHandler vh = context.getApplication().getViewHandler();
            UIViewRoot x = vh.createView(context, currentView);
            context.setViewRoot(x);
        return null;
    }

    public boolean validateBin2Bin() {
        //System.out.println(" validateBinTransfer()");
        Boolean ret = true;
        // get binding for Results table
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("PartsBinstockViewIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            Boolean bTransferLine = ((Boolean)row.getAttribute("tTransferLine") == null ? false : true);
            String sCurBin = (String)row.getAttribute("BisBin");
            String sCurLot = (String)row.getAttribute("BisLot");
            Number sCurQty = (Number)row.getAttribute("BisQty");

            String sNewBin = (String)row.getAttribute("tNewBin");
            String sNewLot = (String)row.getAttribute("tNewLot");
            Number sNewQty = (Number)row.getAttribute("tNewQty");
            System.out.println(i + ":" + bTransferLine + ":" 
                               + sCurBin + ":" + sCurLot + ":" + sCurQty + "->" 
                               + sNewBin + ":" + sNewLot + ":" + sNewQty );
            if (bTransferLine) {
                if (sNewBin == null || sNewLot == null || sNewQty == null) {
                    System.out.println(i + ":" + bTransferLine + ":" 
                                       + sCurBin + ":" + sCurLot + ":" + sCurQty + "->" 
                                       + sNewBin + ":" + sNewLot + ":" + sNewQty );
                    userModificationMsg("ERROR", "Error", "New Bin, New Lot and transfer Qty are required.");
                    return false;
                } else if (sCurBin == sNewBin && sCurLot == sNewLot) {
                    System.out.println(i + ":" + bTransferLine + ":" 
                                       + sCurBin + ":" + sCurLot + ":" + sCurQty + "->" 
                                       + sNewBin + ":" + sNewLot + ":" + sNewQty );
                    userModificationMsg("ERROR", "Error", "New Bin and New Lot are same as current location.");
                    return false;
                }
            }
        }
        return ret;
    }

 
    public void nullPartInBinsCols() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("PartsBinstockViewIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            row.setAttribute("tNewBin", null);
            row.setAttribute("tNewLot", null);
            row.setAttribute("tNewQty", null);
        }
        dciter.executeQuery();
    }
    
    public boolean isDirty() {
        ApplicationModule am = getDCBindingContainer().getDataControl().getApplicationModule();
        Boolean transDirty = am.getTransaction().isDirty();

        BindingContext bc = getBindingContext();
        String dataControl = getBindingContext().getCurrentDataControlFrame();
        Boolean bindingsChanged = bc.findDataControlFrame(dataControl).isTransactionDirty();
//        System.out.println("transDirty: " + transDirty);
//        System.out.println("bindingsChanged: " + bindingsChanged);
        if (transDirty || bindingsChanged) {
            return true;
        }
        return false;
    }
        
    public void setResultsTable(RichTable resultsTable) {
        this.resultsTable = resultsTable;
    }

    public RichTable getResultsTable() {
        return resultsTable;
    }

    public void setNewColumns(RichColumn newColumns) {
        this.newColumns = newColumns;
    }

    public RichColumn getNewColumns() {
        return newColumns;
    }

    public void setTransferPartChk(RichSelectBooleanCheckbox transferPartChk) {
        this.transferPartChk = transferPartChk;
    }

    public RichSelectBooleanCheckbox getTransferPartChk() {
        return transferPartChk;
    }

    public void setNewBin(RichInputListOfValues newBin) {
        this.newBin = newBin;
    }

    public RichInputListOfValues getNewBin() {
        return newBin;
    }

    public void setNewLot(RichInputListOfValues newLot) {
        this.newLot = newLot;
    }

    public RichInputListOfValues getNewLot() {
        return newLot;
    }


    public void setResultsSearchTable(RichTable resultsSearchTable) {
        this.resultsSearchTable = resultsSearchTable;
    }

    public RichTable getResultsSearchTable() {
        return resultsSearchTable;
    }

    public void setTabStock(RichShowDetailItem tabStock) {
        this.tabStock = tabStock;
    }

    public RichShowDetailItem getTabStock() {
        return tabStock;
    }

    public void setTabComment(RichShowDetailItem tabComment) {
        this.tabComment = tabComment;
    }

    public RichShowDetailItem getTabComment() {
        return tabComment;
    }

    public void setTabRecord(RichShowDetailItem tabRecord) {
        this.tabRecord = tabRecord;
    }

    public RichShowDetailItem getTabRecord() {
        return tabRecord;
    }

    public void setTabSupplier(RichShowDetailItem tabSupplier) {
        this.tabSupplier = tabSupplier;
    }

    public RichShowDetailItem getTabSupplier() {
        return tabSupplier;
    }


    public void setNewQty(RichInputText newQty) {
        this.newQty = newQty;
    }

    public RichInputText getNewQty() {
        return newQty;
    }

    public void setPanelTabbed(RichPanelTabbed panelTabbed) {
        this.panelTabbed = panelTabbed;
    }

    public RichPanelTabbed getPanelTabbed() {
        return panelTabbed;
    }

    public void setPanelHeader(RichPanelHeader panelHeader) {
        this.panelHeader = panelHeader;
    }

    public RichPanelHeader getPanelHeader() {
        return panelHeader;
    }


    public void setCurBin(RichInputText curBin) {
        this.curBin = curBin;
    }

    public RichInputText getCurBin() {
        return curBin;
    }

    public void setCurLot(RichInputText curLot) {
        this.curLot = curLot;
    }

    public RichInputText getCurLot() {
        return curLot;
    }

    public void setCurQty(RichInputText curQty) {
        this.curQty = curQty;
    }

    public RichInputText getCurQty() {
        return curQty;
    }

    public void setTransferLine(RichSelectBooleanCheckbox transferLine) {
        this.transferLine = transferLine;
    }

    public RichSelectBooleanCheckbox getTransferLine() {
        return transferLine;
    }

    public void setTransferLineCol(RichColumn transferLineCol) {
        this.transferLineCol = transferLineCol;
    }

    public RichColumn getTransferLineCol() {
        return transferLineCol;
    }

    public void setDeleteBinstockButton(RichButton deleteBinstockButton) {
        this.deleteBinstockButton = deleteBinstockButton;
    }

    public RichButton getDeleteBinstockButton() {
        return deleteBinstockButton;
    }
}
