package its.mnr.mp5.view;

import its.mnr.mp5.vo.cyclecount.MrltCyclecountpartsViewRowImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountpartstoresViewRowImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.rich.component.rich.RichMenu;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectManyShuttle;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
//import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichOutputLabel;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.event.QueryOperationEvent;
import oracle.adf.view.rich.model.AttributeCriterion;
import oracle.adf.view.rich.model.ConjunctionCriterion;
import oracle.adf.view.rich.model.Criterion;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Array;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;


public class CycleCountBean {
    private RichTable ccpartsResults;
    private RichSelectManyShuttle ccpartsResults1;
    private RichSelectManyShuttle storeShuttle;
    private RichPanelFormLayout assignBinsToSRForm;
    private RichButton storeProcessBtn;
    private RichButton storeClearBtn;
    private RichSelectBooleanCheckbox storeCreateMoreStoreBtn;
    private RichButton storePartsProcessBtn;
    private RichButton modifyStoresDelBtn;
    private RichButton modifyStoresResetBtn;
    private RichTable modifyStoresStoreTable;
    private RichInputText ccTimestampTxt;
    private RichInputText ccPhyqtyTxt;
    private RichButton setupRangeForwardBtn;
    private RichButton setupRangeReturnBtn;
    private RichButton setupRangeCancelBtn;
    private RichTable ccListTable;
    private RichSelectManyShuttle partShuttle;
    private RichPopup popCCListConfirm;
  //  private RichInputText ccListConfTxt;

    String filterBin = "";
    String showCycleCount;
    private RichSelectBooleanCheckbox includeChk;
    List selectedParts;
    private RichTable ccpartsSelectedResults;
    private RichOutputLabel selectedCntTxt;
    private RichButton processStorePartsBtn;
    private RichMenu selectContextMenu;
    private RichInputText tusedCntTxt;
    private RichCommandMenuItem checkSelected;
    private RichButton chkSelBtn;
    private RichButton unchkSelBtn;
    private RichPopup popFifoDel;
    private RichTable ccInputTbl;
    private RichShowDetailItem countTab;
    private RichSelectBooleanCheckbox tselectedChbox;
    private RichInputText email1;
    private RichInputText email2;
    private RichPopup ppEmailSub;
    private RichSelectBooleanCheckbox emailSubChk;
    private RichPopup ppJobRunning;
    private RichPanelHeader mainHdr;

    public CycleCountBean() {
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

    public String getMP5Profile(String sprofile) {
        OperationBinding operationBinding = getOperationBinding("getMP5Profile");
        operationBinding.getParamsMap().put("sprofile", sprofile);
        //invoke method
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public void toggleCCPhyQty() {
        String sVal = getMP5Profile("ShowCycleCount");
        showCycleCount = sVal;
        //System.out.println("showCycleCount: " + showCycleCount);
        //System.out.println(getShowCycleCount());
        //this.ccPhyqtyTxt.setReadOnly(!showCycleCount);
    }

    public void tabListener(DisclosureEvent disclosureEvent) {
        if (isDirty() && disclosureEvent.isExpanded()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));

        }
        //            if (getCountTab().isDisclosed()) {
        //               //Check to see if CycleCount should be available
        //               toggleCCPhyQty();
        //            }
    }

    public String commit_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        //Object result = operationBinding.execute();
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //System.out.println("Commit errors: " + operationBinding.getErrors());
            return null;
        }
        //System.out.println("Commit successful");
        return null;
    }

    public String rollback_action() {
        Integer iListId = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        BindingContainer bindings = getBindings();
        if (iListId == null) {
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {
            DCIteratorBinding iter =
                (DCIteratorBinding)getDCBindingContainer().get("MrltCyclecountheaderViewIterator");
            Row rowKey = iter.getCurrentRow();
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
                    ////System.out.println("rowKey RowNotFoundException");
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
        }
        return null;
    }

    //    public void initStores(ClientEvent clientEvent) {
    //        initCycleCountParts();
    //        execCycleCountParts();
    //    }

    public String initCycleCountParts() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("initCycleCountParts");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public Integer execCycleCountParts(Integer iListid) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("executeCycleCountParts");
        operationBinding.getParamsMap().put("sListid", iListid);
        Integer result = (Integer)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return result;
    }


    //    public String printSelected_action() {
    //        RowKeySet selectedEmps = getCcpartsResults().getSelectedRowKeys();
    //        Iterator selectedEmpIter = selectedEmps.iterator();
    //
    //        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
    //        DCIteratorBinding empIter = bindings.findIteratorBinding("MrltCyclecountpartsMainStoreIterator");
    //        RowSetIterator empRSIter = empIter.getRowSetIterator();
    //        ViewObject vo = empRSIter.getRowSet().getViewObject();
    //
    //
    //        RowSet rs = vo.createRowSet("CCMainParts");
    //        rs.executeQuery();
    //
    //        while (selectedEmpIter.hasNext()) {
    //            Key key = (Key)((List)selectedEmpIter.next()).get(0);
    //            Row currentRow = rs.getRow(key);  //empRSIter.getRow(key);
    //            ////System.out.println(currentRow.getAttribute("StlPart") + " - " + currentRow.getAttribute("Include"));
    //            currentRow.setAttribute("Include", "Y");
    //            this.includeChk.setSelected(true);
    //            this.includeChk.setValue(true);
    //            //System.out.println(currentRow.getAttribute("StlPart") + " - " + this.includeChk.isSelected() + ":" + currentRow.getAttribute("Include"));
    //            //empRSIter.setCurrentRow(currentRow);
    ////            deleteStorePart();
    //        }
    //        rs.closeRowSet();
    ////        ViewObject vo = empIter.getViewObject();
    ////        ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("MrltCyclecountpartsMainStoreDisplayed");
    ////
    ////        vo.applyViewCriteria(vc);
    ////        vo.setQueryMode(vo.QUERY_MODE_SCAN_VIEW_ROWS);
    ////        vo.executeQuery();
    //        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);
    //        //empIter.executeQuery();
    //        return null;
    //    }

    public void setSelectedParts(List selectedItems) {
        this.selectedParts = selectedItems;
    }

    public List getSelectedParts() {
        if (selectedParts == null) {
            selectedParts = attributeListForIterator("CycleCountPartsMainIterator", "StlPart");
        }
        return selectedParts;
    }

    public static List attributeListForIterator(String iteratorName, String valueAttrName) {
        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer binding = (DCBindingContainer)bc.getCurrentBindingsEntry();
        DCIteratorBinding iter = binding.findIteratorBinding(iteratorName);
        List attributeList = new ArrayList();
        for (Row r : iter.getAllRowsInRange()) {
            attributeList.add(r.getAttribute(valueAttrName));
        }
        return attributeList;
    }

    public String processShuttle() {

        if (this.getSelectedParts().size() > 0) {
            for (int i = 0; i < selectedParts.size(); i++) {
                System.out.println(getSelectedParts().get(i));
            }
        }

        //        BindingContext bctx = BindingContext.getCurrent();
        //        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        //        JUCtrlListBinding listBinding = (JUCtrlListBinding) bindings.get("CycleCountPartsMainIterator");
        //        Object[] selectedValues = listBinding.getSelectedValues();
        //
        //        /**
        //         * in a productive application, this is where you would read the
        //         * selected values to update the business model. For this sample,
        //         * we just print the selected values
        //         */
        //        for (int i = 0; i < selectedValues.length; i++) {
        //            //System.out.println("Part# : = "+selectedValues[i]);
        //        }
        return null;
    }

    //    public String printSelected_action() {
    //        RowKeySet selectedEmps = getCcpartsResults().getSelectedRowKeys();
    //        Iterator selectedEmpIter = selectedEmps.iterator();
    //
    //        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
    //        DCIteratorBinding empIter = bindings.findIteratorBinding("CycleCountPartsMainIterator");
    //        RowSetIterator empRSIter = empIter.getRowSetIterator();
    //        ViewObject vo = empRSIter.getRowSet().getViewObject();
    //
    //        while (selectedEmpIter.hasNext()) {
    //            Key key = (Key)((List)selectedEmpIter.next()).get(0);
    //            Row currentRow = empRSIter.getRow(key);
    //            ////System.out.println(currentRow.getAttribute("StlPart") + " - " + currentRow.getAttribute("Include"));
    //            currentRow.setAttribute("Selected", true);
    //            this.includeChk.setSelected(true);
    //            this.includeChk.setValue(true);
    //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.includeChk);
    //            //System.out.println(currentRow.getAttribute("StlPart") + " - " + this.includeChk.isSelected() + ":" + currentRow.getAttribute("Selected"));
    //        }
    //
    //        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);
    //        return null;
    //    }

    public void mark() {
        /*
    // markSelected(true);

    //Get Binding Continer of Page
    BindingContainer bc = this.getBindings();
    //Get shuttle binding from pagedef
    JUCtrlListBinding listBindings = (JUCtrlListBinding) bc.get("CycleCountPartsMain_distBinView1");
    //Get Selected Values
    Object obj[] = listBindings.getSelectedValues();
    String[] str =new String[listBindings.getSelectedValues().length];
    for (int i=0;i<obj.length;i++) {
          str[i]=obj[i].toString();
        //System.out.println("str[i]: "+str[i]);
  }
      //System.out.println("Inside mark str: "+str+" str.length: "+str.length);
      AdfFacesContext.getCurrentInstance().getPageFlowScope().put("str", str);
      */
        //  //System.out.println("listBindings.getSelectedValues() inside mark: "+listBindings.getSelectedValues()+ " listBindings.getSelectedValues() LENGTH: "+listBindings.getSelectedValues().length);
        //Iterate over selected values
        List<String> storeList = getCheckedStorerooms();
        String[] storeArr = new String[storeList.size()];
        storeArr = storeList.toArray(storeArr);


        commit_action();
        refreshSelectedBtn();
        this.processStorePartsBtn.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.assignBinsToSRForm);
    }

    public void unmark() {
        //  markSelected(false);

        BindingContainer bc = this.getBindings();
        //Get shuttle binding from pagedef
        JUCtrlListBinding listBindings = (JUCtrlListBinding)bc.get("CycleCountPartsMain_distBinView1");
        //clear Selected Values
        listBindings.clearSelectedIndices();

        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().containsKey("str"))
            AdfFacesContext.getCurrentInstance().getPageFlowScope().remove("str");
        this.processStorePartsBtn.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.assignBinsToSRForm);
    }


    public String selectedStoreroomProcess() {
        /*-----*/
        //Get Binding Continer of Page
        BindingContainer bc = this.getBindings();
        //Get shuttle binding from pagedef
        JUCtrlListBinding listBindings = (JUCtrlListBinding)bc.get("CycleCountPartsMain_distBinView1");
        //Get Selected Values
        Object obj[] = listBindings.getSelectedValues();
        String[] str = new String[listBindings.getSelectedValues().length];
        for (int i = 0; i < obj.length; i++) {
            str[i] = obj[i].toString();
            //System.out.println("str[i]: " + str[i]);
        }
        //    //System.out.println("Inside mark str: "+str+" str.length: "+str.length);
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("str", str);


        /*-----*/


        String writeCCStoreList = "";
        Integer iListId = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfListId", iListId);

        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().containsKey("str")) {
            //  String[] str = (String[]) AdfFacesContext.getCurrentInstance().getPageFlowScope().get("str");
            // //System.out.println("Inside selectedStoreroomProcess str: "+str);
            java.util.Date date = new java.util.Date();
            // //System.out.println("Start: " + new Timestamp(date.getTime()));

            ArrayList<String> stringList = getCheckedStorerooms();
            // //System.out.println("stringList inside selectedStoreroomProcess: "+stringList.size());
            for (String storeItem : stringList) {
                if (str != null) {
                    //  //System.out.println("Inside selectedStoreroomProcess str2: "+str+" str class: "+str.getClass()+ " str length: "+str.length);
                    //  //System.out.println("str inside selectedStoreroomProcess before for: "+str.toString());
                    for (String bin : str) {
                        //    //System.out.println("Inside selectedStoreroomProcess bin: "+bin);
                        writeCCStoreList = writeCCStoreList(iListId.toString(), storeItem, bin);
                        //   //System.out.println("writeCCStoreList inside selectedStoreroomProcess: " + writeCCStoreList);
                        //String msg = "Storeroom " + storeItem + " parts created successfully";
                    }
                }
            }
            //        RichPopup.PopupHints hints = new RichPopup.PopupHints();
            //        popCCListConfirm.show(hints);

            date = new java.util.Date();
            //System.out.println("End: " + new Timestamp(date.getTime()));
            String ret = commit_action();
            //System.out.println("ret: " + ret);
            //"TRUE".equals(writeCCStoreList) &&
            if (ret == null) {
                //System.out.println("writeCCStoreList 2 inside selectedStoreroomProcess: " + writeCCStoreList);
                //resetStoresTbl();
                // resetSelectedPartsTbl();

                //  this.ccpartsResults.resetStampState();

                unmark();
                refreshStorerooms(iListId);
                //  AdfFacesContext.getCurrentInstance().getPageFlowScope().remove("str");
                AdfFacesContext.getCurrentInstance().addPartialTarget(this.assignBinsToSRForm);

                //refreshCurrentPage();
            }
        }

        //goToControlFlow(null, "done");

        return null;
    }

    public String writeCCStoreList(String pList, String pType, String pBin) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("writeCCStoreList");
        operationBinding.getParamsMap().put("pList", pList);
        operationBinding.getParamsMap().put("pType", pType);
        operationBinding.getParamsMap().put("pBin", pBin);

        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //System.out.println("writeCCStoreList INSIDE CYCLECOUNTBEAN: " + result);
        return result.toString();
    }

    /*
        public void markSelected(Boolean baction) {
        RowKeySet selectedEmps = getCcpartsResults().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();

        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding empIter = bindings.findIteratorBinding("CycleCountPartsMainIterator");
        RowSetIterator empRSIter = empIter.getRowSetIterator();
        //ViewObject vo = empRSIter.getRowSet().getViewObject();

        while (selectedEmpIter.hasNext()) {
            Key key = (Key)((List)selectedEmpIter.next()).get(0);
            Row currentRow = empRSIter.getRow(key);
            currentRow.setAttribute("Selected", baction);
            this.includeChk.setSelected(baction);
            this.includeChk.setValue(baction);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.includeChk);
            ////System.out.println(currentRow.getAttribute("StlPart") + " - " + this.includeChk.isSelected() + ":" + currentRow.getAttribute("Selected"));
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);
        //return null;
    }
    */

    public void chkSelectedLsnr(ValueChangeEvent valueChangeEvent) {
        RowKeySet selectedEmps = getCcpartsResults().getSelectedRowKeys();
        Iterator selectedEmpIter = selectedEmps.iterator();

        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding empIter = bindings.findIteratorBinding("CycleCountPartsMainIterator");
        RowSetIterator empRSIter = empIter.getRowSetIterator();

        if (valueChangeEvent.getNewValue() != null) {
            Boolean selectedValue = Boolean.parseBoolean(valueChangeEvent.getNewValue().toString());
            ////System.out.println("selectAll:" + selectAll);
            while (selectedEmpIter.hasNext()) {
                Key key = (Key)((List)selectedEmpIter.next()).get(0);
                Row currentRow = empRSIter.getRow(key);
                ////System.out.println(currentRow.getAttribute("StlPart") + " - " + currentRow.getAttribute("Include"));
                currentRow.setAttribute("Selected", selectedValue);
                this.includeChk.setSelected(selectedValue);
                //System.out.println(currentRow.getAttribute("StlPart") + " - " + this.includeChk.isSelected() + ":" + currentRow.getAttribute("Selected"));
                //empRSIter.setCurrentRow(currentRow);
                //            deleteStorePart();
            }
        }
        //        ViewObject vo = empIter.getViewObject();
        //        ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("MrltCyclecountpartsMainStoreDisplayed");
        //
        //        vo.applyViewCriteria(vc);
        //        vo.setQueryMode(vo.QUERY_MODE_SCAN_VIEW_ROWS);
        //        vo.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);
    }

    public String deleteStorePart() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public void resetViewCriteria() {
        //System.out.println("resetViewCriteria()");
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("MrltCyclecountpartsMainStoreIterator");
        ViewObject vo = iter.getViewObject();
        //ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("CycleCountPartsDisplayed");
        ////System.out.println("1");
        vo.applyViewCriteria(null);
        ////System.out.println("1.5");
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
        ////System.out.println("2");
        vo.executeQuery();
        ////System.out.println("3");
        //vo.executeEmptyRowSet();
        //            initCycleCountParts();
        //            execCycleCountParts();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);
    }
/*//ADF 11g R2
    public String resetTableFilter() {
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("MrltCyclecountpartsMainStoreIterator");
        ViewObject vo = iter.getViewObject();
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor)getCcpartsResults().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
            queryDescriptor.getFilterCriteria().clear();
            getCcpartsResults().queueEvent(new QueryEvent(getCcpartsResults(), queryDescriptor));
        } else {
            String message = "Have some problem in Clearing Filters. please try later";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
        }
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
        return null;
    }
    */
    /**In ADF 12c getFilterCriteria() is depricated instead use getFilterConjunctionCriterion
     * method to reset filter attributes on an af:table
     * @param actionEvent event which triggers the method
     */
    public String resetTableFilter() {
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("MrltCyclecountpartsMainStoreIterator");
        ViewObject vo = iter.getViewObject();
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);        
        FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor) getCcpartsResults().getFilterModel();
        if (queryDescriptor != null && queryDescriptor.getFilterConjunctionCriterion() != null) {
            ConjunctionCriterion cc = queryDescriptor.getFilterConjunctionCriterion();
            List<Criterion> lc = cc.getCriterionList();
            for (Criterion c : lc) {
                if (c instanceof AttributeCriterion) {
                    AttributeCriterion ac = (AttributeCriterion) c;
                    ac.setValue(null);
                }
            }
            getCcpartsResults().queueEvent(new QueryEvent(getCcpartsResults(), queryDescriptor));
        }
        else {
                    String message = "Have some problem in Clearing Filters. please try later";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
                }
                vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
                return null;
    }

    public void updTimestamp(ClientEvent clientEvent) {
        ////System.out.println("updTimestamp");
        if (this.ccPhyqtyTxt.getValue() != null) {
            this.ccTimestampTxt.setValue(new Timestamp(new java.sql.Timestamp(System.currentTimeMillis())));
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccTimestampTxt);
        }

    }
    /*
      public String RangeNext_action()
      {
        Integer resInt = 0;
        Integer iListid = (Integer) ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        String sRange = (String) ADFUtil.evaluateEL("#{bindings.RangeType.inputValue}");
        String pBIN = (String) ADFUtil.evaluateEL("#{bindings.Bin.inputValue}");
        String pBINSTART = (String) ADFUtil.evaluateEL("#{bindings.Binstart.inputValue}");
        String pBINEND = (String) ADFUtil.evaluateEL("#{bindings.Binend.inputValue}");
        String pPARTCLASS = (String) ADFUtil.evaluateEL("#{bindings.Partclass.inputValue}");
        String pPARTSTART = (String) ADFUtil.evaluateEL("#{bindings.Partstart.inputValue}");
        String pPARTEND = (String) ADFUtil.evaluateEL("#{bindings.Partend.inputValue}");
        String pP1 = (String) ADFUtil.evaluateEL("#{bindings.P1.inputValue}");
        String pP2 = (String) ADFUtil.evaluateEL("#{bindings.P2.inputValue}");
        String pP3 = (String) ADFUtil.evaluateEL("#{bindings.P3.inputValue}");
        String pP4 = (String) ADFUtil.evaluateEL("#{bindings.P4.inputValue}");
        String pP5 = (String) ADFUtil.evaluateEL("#{bindings.P5.inputValue}");
        String pP6 = (String) ADFUtil.evaluateEL("#{bindings.P6.inputValue}");

        pBIN = pBIN != null? pBIN.toUpperCase(): null;
        pBINSTART = pBINSTART != null? pBINSTART.toUpperCase(): null;
        pBINEND = pBINEND != null? pBINEND.toUpperCase(): null;
        pPARTCLASS = pPARTCLASS != null? pPARTCLASS.toUpperCase(): null;
        pPARTSTART = pPARTSTART != null? pPARTSTART.toUpperCase(): null;
        pPARTEND = pPARTEND != null? pPARTEND.toUpperCase(): null;
        pP1 = pP1 != null? pP1.toUpperCase(): null;
        pP2 = pP2 != null? pP2.toUpperCase(): null;
        pP3 = pP3 != null? pP3.toUpperCase(): null;
        pP4 = pP4 != null? pP4.toUpperCase(): null;
        pP5 = pP5 != null? pP5.toUpperCase(): null;
        pP6 = pP6 != null? pP6.toUpperCase(): null;

        ////System.out.println("pP3: " + pP3);
        //System.out.println("pBIN: " + pBIN + " pBINSTART: "+pBINSTART+" pBINEND: "+pBINEND);
        Boolean multiCountsExist =
          chkPartsOtherCount(pBIN,pBINSTART, pBINEND, pPARTCLASS, pPARTSTART, pPARTEND, pP1, pP2, pP3, pP4, pP5, pP6);

        Boolean multiBinsExist =
          PartsMultiBinsExist(pBIN,pBINSTART, pBINEND, pPARTCLASS, pPARTSTART, pPARTEND, pP1, pP2, pP3, pP4, pP5, pP6);

        if (!multiBinsExist && !multiCountsExist)
        {
          //System.out.println("No multiple Bins");
          //Save Cycle Count Header
          commit_action();
          //System.out.println((String) ADFUtil.evaluateEL("#{bindings.Ccstatus.inputValue}"));
          //System.out.println("Commit Header");
          //Create Cycle Count Parts
          initCycleCountParts();
          resInt = execCycleCountParts(iListid);
          //System.out.println("resInt: " + resInt);
          ////System.out.println("CCList initialized");

          if (resInt == null || resInt == 0)
          {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "No parts meet criteria: 1) active status, " +
                                                "  2) not in multiple bins, and 3) not in another active Cycle Count.",
                                                null));
            //Show cancel and restart buttons, disable move forward.
            showCancReturnAfterMultiBins();
            return null;
          }

          Integer iListId = (Integer) ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
          //String sTrans = (String)ADFUtil.evaluateEL("#{bindings.Sttknum.inputValue}");
          AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfListId", iListId);

          java.util.Date date = new java.util.Date();
          //System.out.println("Start: " + new Timestamp(date.getTime()));

          //CreateCCList(iListId, sTrans, "MAIN");
          String retwriteCCListXML = writeCCListXML(iListId.toString(), "MAIN");

          //if ("TRUE".equals(retwriteCCListXML)) {
          //String msg ="Cycle Count List # " + iListId.toString() + " created successfully";

          //FacesContext context = FacesContext.getCurrentInstance();
          //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));

          //this.ccListConfTxt.setValue(msg);
          ////System.out.println("ccListConfTxt: " + this.ccListConfTxt.getValue());
          ////System.out.println(msg);
          ////System.out.println("Showing pop up");
          //AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccListConfTxt);
          RichPopup.PopupHints hints = new RichPopup.PopupHints();
          popCCListConfirm.show(hints);
          //System.out.println((String) ADFUtil.evaluateEL("#{bindings.Ccstatus.inputValue}"));
          //}

          date = new java.util.Date();
          //System.out.println("End: " + new Timestamp(date.getTime()));
          //System.out.println("CCList created");
          //goToControlFlow(null, "done");

        }
        return null;
      }
    */

    public String emailSet() {
        String emailStr = "";
        if (email1 != null) {
            if ((email1.getValue() != null) && (email1.getValue() != "")) {
                if ((emailStr != null) && (emailStr != "")) {
                    //System.out.println("inside email1 1");
                    emailStr = emailStr + "," + email1.getValue().toString();
                } else if (emailStr == "") {
                    //System.out.println("inside email1 2");
                    emailStr = email1.getValue().toString();
                }
            } else {
                System.out.println("inside email1 3");
                // emailStr = "";
            }
        } else {
            System.out.println("inside email1 4");
            emailStr = "";
        }

        if (email2 != null) {
            if ((email2.getValue() != null) && (email2.getValue() != "")) {
                if ((emailStr != null) && (emailStr != "")) {
                    //System.out.println("inside email2 1");
                    emailStr = emailStr + "," + email2.getValue().toString();
                } else if (emailStr == "") {
                    //System.out.println("inside email2 2");
                    emailStr = email2.getValue().toString();
                }
            } else {
                System.out.println("inside email2 3");
                // emailStr = "";
            }
        } else {
            //System.out.println("inside email2 4");
            emailStr = "";
        }
        if ((emailStr == "")) {
            emailStr = getMP5Profile("AdminEmailAddress");
            //System.out.println("emailStr 1: " + emailStr);
        }
        //System.out.println("emailStr 2: " + emailStr);
        ADFContext.getCurrent().getSessionScope().put("email1", emailStr);
        System.out.println("Session email1: " + ADFContext.getCurrent().getSessionScope().get("email1"));
        return emailStr;
    }

    public void emailStr() {
        String emailStr = "";
        /*
        if (emailSubChk != null) {
            if (emailSubChk.getValue().equals(true)) {

                System.out.println("inside emailStr() true : " + emailStr);
                emailStr = emailSet();
            } else {
                System.out.println("inside emailStr() 1 : " + emailStr);
                emailStr = getMP5Profile("AdminEmailAddress");
            }
        } else {
            System.out.println("inside emailStr() 2 : " + emailStr);
            emailStr = getMP5Profile("AdminEmailAddress");
        }
    */
        System.out.println("inside emailStr() true : " + emailStr);
        emailStr = emailSet();
        System.out.println("inside emailStr() : " + emailStr);
        createCCListAndSetEmailJobs(emailStr);
    }

    public String RangeNext_action(ActionEvent ae) {
        Integer resInt = 0;
        Integer iListid = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        String sRange = (String)ADFUtil.evaluateEL("#{bindings.RangeType.inputValue}");
        String pBIN = (String)ADFUtil.evaluateEL("#{bindings.Bin.inputValue}");
        String pBINSTART = (String)ADFUtil.evaluateEL("#{bindings.Binstart.inputValue}");
        String pBINEND = (String)ADFUtil.evaluateEL("#{bindings.Binend.inputValue}");
        String pPARTCLASS = (String)ADFUtil.evaluateEL("#{bindings.Partclass.inputValue}");
        String pPARTSTART = (String)ADFUtil.evaluateEL("#{bindings.Partstart.inputValue}");
        String pPARTEND = (String)ADFUtil.evaluateEL("#{bindings.Partend.inputValue}");
        String pP1 = (String)ADFUtil.evaluateEL("#{bindings.P1.inputValue}");
        String pP2 = (String)ADFUtil.evaluateEL("#{bindings.P2.inputValue}");
        String pP3 = (String)ADFUtil.evaluateEL("#{bindings.P3.inputValue}");
        String pP4 = (String)ADFUtil.evaluateEL("#{bindings.P4.inputValue}");
        String pP5 = (String)ADFUtil.evaluateEL("#{bindings.P5.inputValue}");
        String pP6 = (String)ADFUtil.evaluateEL("#{bindings.P6.inputValue}");

        pBIN = pBIN != null ? pBIN.toUpperCase() : null;
        pBINSTART = pBINSTART != null ? pBINSTART.toUpperCase() : null;
        pBINEND = pBINEND != null ? pBINEND.toUpperCase() : null;
        pPARTCLASS = pPARTCLASS != null ? pPARTCLASS.toUpperCase() : null;
        pPARTSTART = pPARTSTART != null ? pPARTSTART.toUpperCase() : null;
        pPARTEND = pPARTEND != null ? pPARTEND.toUpperCase() : null;
        pP1 = pP1 != null ? pP1.toUpperCase() : null;
        pP2 = pP2 != null ? pP2.toUpperCase() : null;
        pP3 = pP3 != null ? pP3.toUpperCase() : null;
        pP4 = pP4 != null ? pP4.toUpperCase() : null;
        pP5 = pP5 != null ? pP5.toUpperCase() : null;
        pP6 = pP6 != null ? pP6.toUpperCase() : null;

        ////System.out.println("pP3: " + pP3);
        //System.out.println("pBIN: " + pBIN + " pBINSTART: " + pBINSTART + " pBINEND: " + pBINEND);
        Boolean multiCountsExist =
            chkPartsOtherCount(pBIN, pBINSTART, pBINEND, pPARTCLASS, pPARTSTART, pPARTEND, pP1, pP2, pP3, pP4, pP5,
                               pP6);

        Boolean multiBinsExist =
            PartsMultiBinsExist(pBIN, pBINSTART, pBINEND, pPARTCLASS, pPARTSTART, pPARTEND, pP1, pP2, pP3, pP4, pP5,
                                pP6);

        if (!multiBinsExist && !multiCountsExist) {
            //System.out.println("No multiple Bins");
            //Save Cycle Count Header
            commit_action();
            //System.out.println((String)ADFUtil.evaluateEL("#{bindings.Ccstatus.inputValue}"));
            //System.out.println("Commit Header");
            //Create Cycle Count Parts
            initCycleCountParts();
            resInt = execCycleCountParts(iListid);
            //System.out.println("resInt: " + resInt);
            ////System.out.println("CCList initialized");

            if (resInt == null || resInt == 0) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "No parts meet criteria: 1) active status, " +
                                                    "  2) not in multiple bins, and 3) not in another active Cycle Count.",
                                                    null));
                //Show cancel and restart buttons, disable move forward.
                showCancReturnAfterMultiBins();
                return null;
            }

            Integer iListId = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
            //String sTrans = (String)ADFUtil.evaluateEL("#{bindings.Sttknum.inputValue}");
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfListId", iListId);

            java.util.Date date = new java.util.Date();
            //System.out.println("Start: " + new Timestamp(date.getTime()));

            /*
      //CreateCCList(iListId, sTrans, "MAIN");
      String retwriteCCListXML = writeCCListXML(iListId.toString(), "MAIN");

      RichPopup.PopupHints hints = new RichPopup.PopupHints();
      popCCListConfirm.show(hints);
      //System.out.println((String) ADFUtil.evaluateEL("#{bindings.Ccstatus.inputValue}"));
      //}

      date = new java.util.Date();
      //System.out.println("End: " + new Timestamp(date.getTime()));
      //System.out.println("CCList created");
      //goToControlFlow(null, "done");
        */
        }
        return null;
    }

    public void createCCListAndSetEmailJobs(String recEmail) {
        if (AdfFacesContext.getCurrentInstance().getPageFlowScope().containsKey("psfListId")) {
            Integer iListId = (Integer)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfListId");
            //String recEmail = "";
            //System.out.println("Inside createCCListAndSetEmailJobs: iListId: " + iListId + " type: MAIN");
            /*
            if(AdfFacesContext.getCurrentInstance().getPageFlowScope().containsKey("email1")){
                //System.out.println("#{email1.inputValue}: "+AdfFacesContext.getCurrentInstance().getPageFlowScope().get("email1").toString());
                recEmail = AdfFacesContext.getCurrentInstance().getPageFlowScope().get("email1").toString();
            }*/
            /*
            String pP1 = (String)ADFUtil.evaluateEL("#{bindings.P1.inputValue}");
            String pP2 = (String)ADFUtil.evaluateEL("#{bindings.P2.inputValue}");
            String pP3 = (String)ADFUtil.evaluateEL("#{bindings.P3.inputValue}");
            String pP4 = (String)ADFUtil.evaluateEL("#{bindings.P4.inputValue}");
            String pP5 = (String)ADFUtil.evaluateEL("#{bindings.P5.inputValue}");
            String pP6 = (String)ADFUtil.evaluateEL("#{bindings.P6.inputValue}");
            */
            Integer resInt = 0;
            initCycleCountParts();
            resInt = execCycleCountParts(iListId);
            //System.out.println("resInt inside createCCListAndSetEmailJobs: " + resInt);
            //commit_action();
            java.util.Date date = new java.util.Date();
            //System.out.println("Start: " + new Timestamp(date.getTime()));
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfListId", iListId);
            //CreateCCList(iListId, sTrans, "MAIN");
            String retwriteCCListXML = writeCCListXML(iListId.toString(), "MAIN", recEmail);
            // String retwriteCCListXML = writeCCListXML(iListId.toString(), "MAIN", "lakshmi.kumar@itslb.com");
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            popCCListConfirm.show(hints);
            //System.out.println((String)ADFUtil.evaluateEL("#{bindings.Ccstatus.inputValue}"));
            //}

            date = new java.util.Date();
            //System.out.println("End: " + new Timestamp(date.getTime()));
            //System.out.println("CCList created");
            //goToControlFlow(null, "done");

        }
    }

    public void diagLsnrCCListConfirm(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            goToControlFlow(null, "done");
        }
    }

    public String CreateCCList(Integer iListId, String sTrans, String sStoreroom) {
        ////System.out.println("CreateCCList bean");
       // BindingContainer bindings = getBindings();
       DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("createCCList");
        operationBinding.getParamsMap().put("iListId", iListId);
        operationBinding.getParamsMap().put("sTrans", sTrans);
        operationBinding.getParamsMap().put("sStoreroom", sStoreroom);

        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String writeCCListXML(String pList, String pType, String recEmail) {
        //System.out.println("Inside writeCCListXML: pList: " + pList + " pType: " + pType + " recEmail: " + recEmail);

       // BindingContainer bindings = getBindings();
       DCBindingContainer bindings = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("writeCCListXML");
        operationBinding.getParamsMap().put("pList", pList);
        operationBinding.getParamsMap().put("pType", pType);
        operationBinding.getParamsMap().put("recEmail", recEmail);

        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //System.out.println("writeCCListXML: " + result);
        return result.toString();
        //return null;
    }

    public String writeCCStoreListXML(String pList, String pType) {

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("writeCCStoreListXML");
        operationBinding.getParamsMap().put("pList", pList);
        operationBinding.getParamsMap().put("pType", pType);

        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //System.out.println("writeCCStoreListXML: " + result);
        return result.toString();
    }

    public boolean PartsMultiBinsExist(String pBIN, String pBINSTART, String pBINEND, String pPARTCLASS,
                                       String pPARTSTART, String pPARTEND, String pP1, String pP2, String pP3,
                                       String pP4, String pP5, String pP6) {
        ////System.out.println("CreateCCList bean");
        boolean ret = false;
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkPartsMultiBins");
        if (pBIN != null) {
            operationBinding.getParamsMap().put("pBIN", pBIN);
        }
        if (pBINSTART != null && pBINEND != null) {
            operationBinding.getParamsMap().put("pBINSTART", pBINSTART);
            operationBinding.getParamsMap().put("pBINEND", pBINEND);
        }
        if (pPARTCLASS != null) {
            operationBinding.getParamsMap().put("pPARTCLASS", pPARTCLASS);
        }
        if (pPARTSTART != null && pPARTEND != null) {
            operationBinding.getParamsMap().put("pPARTSTART", pPARTSTART);
            operationBinding.getParamsMap().put("pPARTEND", pPARTEND);
        }
        if (pP1 != null) {
            operationBinding.getParamsMap().put("pP1", pP1);
        }
        if (pP2 != null) {
            operationBinding.getParamsMap().put("pP2", pP2);
        }
        if (pP3 != null) {
            operationBinding.getParamsMap().put("pP3", pP3);
        }
        if (pP4 != null) {
            operationBinding.getParamsMap().put("pP4", pP4);
        }
        if (pP5 != null) {
            operationBinding.getParamsMap().put("pP5", pP5);
        }
        if (pP6 != null) {
            operationBinding.getParamsMap().put("pP6", pP6);
        }
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //Show cancel and restart buttons, disable move forward.
            showCancReturnAfterMultiBins();
            ret = true;
        }
        return ret;
    }


    public boolean chkPartsOtherCount(String pBIN, String pBINSTART, String pBINEND, String pPARTCLASS,
                                      String pPARTSTART, String pPARTEND, String pP1, String pP2, String pP3,
                                      String pP4, String pP5, String pP6) {
        boolean ret = false;
        //System.out.println("Inside chkPartsOtherCount");
        //System.out.println("pBIN: " + pBIN + " pBINSTART: " + pBINSTART + " pBINEND: " + pBINEND);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("chkPartsOtherCount");
        if (pBIN != null) {
            operationBinding.getParamsMap().put("pBIN", pBIN);
        }
        if (pBINSTART != null && pBINEND != null) {
            operationBinding.getParamsMap().put("pBINSTART", pBINSTART);
            operationBinding.getParamsMap().put("pBINEND", pBINEND);
        }
        if (pPARTCLASS != null) {
            operationBinding.getParamsMap().put("pPARTCLASS", pPARTCLASS);
        }
        if (pPARTSTART != null && pPARTEND != null) {
            operationBinding.getParamsMap().put("pPARTSTART", pPARTSTART);
            operationBinding.getParamsMap().put("pPARTEND", pPARTEND);
        }
        if (pP1 != null) {
            operationBinding.getParamsMap().put("pP1", pP1);
        }
        if (pP2 != null) {
            operationBinding.getParamsMap().put("pP2", pP2);
        }
        if (pP3 != null) {
            operationBinding.getParamsMap().put("pP3", pP3);
        }
        if (pP4 != null) {
            operationBinding.getParamsMap().put("pP4", pP4);
        }
        if (pP5 != null) {
            operationBinding.getParamsMap().put("pP5", pP5);
        }
        if (pP6 != null) {
            operationBinding.getParamsMap().put("pP6", pP6);
        }
        //System.out.println("Inside chkPartsOtherCount before calling execute");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //Show cancel and restart buttons, disable move forward.
            showCancReturnAfterMultiBins();
            ret = true;
        }
        return ret;
    }

    public void showCancReturnAfterMultiBins() {
        //this.setupRangeForwardBtn.setDisabled(true);
        this.setupRangeCancelBtn.setVisible(true);
        this.setupRangeReturnBtn.setVisible(true);

        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.setupRangeForwardBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.setupRangeCancelBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.setupRangeReturnBtn);

    }
    /*
    public void selectStoreRoomPopLsnr(LaunchPopupEvent launchPopupEvent) {
        Integer iListid = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        //System.out.println("iListid: " + iListid);

        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        JUCtrlListBinding lov = (JUCtrlListBinding)bindings.get("tStoreroomId");
        ViewCriteriaManager vcm = lov.getListIterBinding().getViewObject().getViewCriteriaManager();
        //make sure the view criteria is cleared
        vcm.removeViewCriteria(vcm.DFLT_VIEW_CRITERIA_NAME);
        //create a new view criteria
        ViewCriteria vc = new ViewCriteria(lov.getListIterBinding().getViewObject());
        //use the default view criteria name
        //"__DefaultViewCriteria__"
        vc.setName(vcm.DFLT_VIEW_CRITERIA_NAME);
        //create a view criteria row for all queryable attributes
        ViewCriteriaRow vcr = new ViewCriteriaRow(vc);
        //for this sample I set the query filter to DepartmentId 60.
        //You may determine it at runtime by reading it from a managed bean
        //or binding layer
        vcr.setAttribute("BLISTID", iListid);
        //also note that the view criteria row consists of all attributes
        //that belong to the LOV list view object, which means that you can
        //filter on multiple attributes
        vc.addRow(vcr);
        lov.getListIterBinding().getViewObject().applyViewCriteria(vc);
    }
*/

    public void setCcpartsResults(RichTable ccpartsResults) {
        this.ccpartsResults = ccpartsResults;
    }

    public RichTable getCcpartsResults() {
        return ccpartsResults;
    }

    /*
    public void testPopLsnr(LaunchPopupEvent launchPopupEvent) {
        Integer iListid =(Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        //System.out.println("iListid: " + iListid);
        JUCtrlListBinding lov = (JUCtrlListBinding)getBindings().get("ilov1");
        //lov.getListIterBinding().getViewObject().setNamedWhereClauseParam("BLISTID",iListid);
        //System.out.println(lov.getListIterBinding().getViewObject().getName());
    }
*/
    /*
    public void setListId(ClientEvent clientEvent) {
        Integer iListid = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        //System.out.println("iListid: " + iListid);

        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("RemainingCycleCountStoreLOVIterator");
        ViewObject vo = iter.getViewObject();

        ViewCriteriaManager vcm = vo.getViewCriteriaManager();
        //make sure the view criteria is cleared
        vcm.removeViewCriteria(vcm.DFLT_VIEW_CRITERIA_NAME);
        //create a new view criteria
        ViewCriteria vc = new ViewCriteria(vo);
        //use the default view criteria name
        //"__DefaultViewCriteria__"
        vc.setName(vcm.DFLT_VIEW_CRITERIA_NAME);
        //create a view criteria row for all queryable attributes
        ViewCriteriaRow vcr = new ViewCriteriaRow(vc);
        //for this sample I set the query filter to DepartmentId 60.
        //You may determine it at runtime by reading it from a managed bean
        //or binding layer
        vcr.setAttribute("BLISTID", iListid);
        //also note that the view criteria row consists of all attributes
        //that belong to the LOV list view object, which means that you can
        //filter on multiple attributes
        vc.addRow(vcr);
        vo.applyViewCriteria(vc);
    }
*/

    public void resetStorePartsTable(ClientEvent clientEvent) {
        resetViewCriteria();
    }

    public void processSelection(ActionEvent actionEvent) {
        BindingContainer bc = this.getBindings();
        JUCtrlListBinding listBinding = (JUCtrlListBinding)bc.get("MrlrefCyclecountstoreView");
        Integer iListid = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        Object[] str = listBinding.getSelectedValues();
        ArrayList<String> stringList = new ArrayList<String>();
        String ret = null;
        for (int i = 0; i < str.length; i++) {
            ////System.out.println(str[i]);
            stringList.add((String)str[i]);
        }
        //        for(String item: stringList){
        //        //System.out.println("retrieved element: " + item);
        //        }

        ////System.out.println("stringList.size() = " + stringList.size());
        if (stringList.size() > 0) {
            ret = createCCPartStores(iListid, stringList);
        }
        if (ret == null) {
            ////System.out.println("ret is null");
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfStoreroom", true);

            this.storeShuttle.setDisabled(true);
            this.storeProcessBtn.setDisabled(true);
            this.storeClearBtn.setDisabled(true);
            this.storeCreateMoreStoreBtn.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeShuttle);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeProcessBtn);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeClearBtn);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeCreateMoreStoreBtn);
        }
    }

    //    public void addTrainStop (Object actid){
    //            TrainModel trainModel = TrainUtils.findCurrentTrainModel();
    //            MetadataService metadataService =
    //                 MetadataService.getInstance();
    //
    //             //Get activity and its train stop definition
    //             Activity activity =
    //                 metadataService.getActivity((ActivityId)actid);
    //             TrainStopContainer stopContainer =
    //                     (TrainStopContainer)activity.getMetadataObject();
    //             TrainStop ts = stopContainer.getTrainStop();
    //
    //
    //             //Create new train stop model and add it to the train
    //             trainModel.getTrainStops().put(actid, new TrainStopModel(ts, (ActivityId)actid))
    //        }

    public void clearSelection(ActionEvent actionEvent) {
        clearShuttle();
    }

    public void clearShuttle() {
        BindingContainer bc = this.getBindings();
        JUCtrlListBinding listBinding = (JUCtrlListBinding)bc.get("MrlrefCyclecountstoreView");
        listBinding.clearSelectedIndices();
    }

    public String createCCPartStores(Integer iListId, ArrayList<String> sStoreroom) {
        ////System.out.println("createCCPartStores bean");
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("MrltCyclecountpartstoresViewIterator");
        ViewObject vo = iter.getViewObject();
        for (String item : sStoreroom) {
            MrltCyclecountpartstoresViewRowImpl rowImpl = (MrltCyclecountpartstoresViewRowImpl)vo.createRow();
            ////System.out.println("Storeroom " + item);

            rowImpl.setListid(iListId);
            rowImpl.setSrcd(item);

            vo.insertRow(rowImpl);
        }
        String ret = commit_action();
        return ret;
    }

    public void crMoreStoresChkLsnr(ValueChangeEvent valueChangeEvent) {
        Boolean bOld = (Boolean)valueChangeEvent.getOldValue();
        Boolean bChecked = (Boolean)valueChangeEvent.getNewValue();
        if (bChecked) {
            this.storeShuttle.setVisible(true);
            this.storeProcessBtn.setVisible(true);
            this.storeClearBtn.setVisible(true);
            //AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfStoreroom", true);
            clearShuttle();
        } else if (!bChecked) {
            this.storeShuttle.setVisible(false);
            this.storeProcessBtn.setVisible(false);
            this.storeClearBtn.setVisible(false);
            //AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfStoreroom", false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeShuttle);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeProcessBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.storeClearBtn);
    }

    public ArrayList<String> getCheckedStorerooms() {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltCyclecountpartstoresViewIterator");
        ArrayList<String> sList = new ArrayList<String>();
        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            //Boolean sIsUsed = ("Y".equals(row.getAttribute("Used")) ? true : false);
            Boolean sIsUsed = (Boolean)row.getAttribute("tSelected");
            if (sIsUsed != null && sIsUsed) {
                String pStore = (String)row.getAttribute("Srcd");
                //System.out.println(pStore);
                sList.add(pStore);
            }
        }

        for (String item : sList) {
            System.out.println("retrieved element of sList: " + item);
        }

        //System.out.println("stringList.size() = " + sList.size());
        return sList;
    }

    public Integer getCheckedStoreroomCnt() {
        //System.out.println("getCheckedStoreroomCnt()");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltCyclecountpartstoresViewIterator");
        Integer cnt = 0;
        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            //Boolean sIsUsed = ("Y".equals(row.getAttribute("Used")) ? true : false);
            Boolean sIsUsed = (Boolean)row.getAttribute("tSelected");
            if (sIsUsed != null && sIsUsed) {
                cnt++;
            }
        }

        return cnt;
    }

    public String processStorePartsBtn() {
        String ret = null;
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltCyclecountpartsMainStoreIterator");
        //DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltCyclecountpartstoresViewIterator");


        DCIteratorBinding iter = bindings.findIteratorBinding("MrltCyclecountpartsViewIterator");
        ViewObject vo = iter.getViewObject();

        ArrayList<String> stringList = getCheckedStorerooms();

        for (String storeItem : stringList) {
            //System.out.println("retrieved element: " + storeItem);
            for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
                Row row = dciter.getRowAtRangeIndex(i);

                Integer iListid = (Integer)row.getAttribute("StlListid");
                String sSttk = (String)row.getAttribute("StlTrans");
                String pBin = (String)row.getAttribute("StlBin");
                String pLot = (String)row.getAttribute("StlLot");
                //Number pExpQty = (Number)row.getAttribute("StlExpqty");
                String pPart = (String)row.getAttribute("StlPart");
                String pPartOrg = (String)row.getAttribute("StlPartOrg");
                String pStore = (String)row.getAttribute("StlStore");
                //System.out.println(iListid + ":" + sSttk + ":" + pBin + ":" + pLot + ":" + /*pExpQty + ":" + */pPart +":" + pPartOrg + ":" + pStore);

                MrltCyclecountpartsViewRowImpl rowImpl = (MrltCyclecountpartsViewRowImpl)vo.createRow();
                ////System.out.println(sSttk);
                rowImpl.setStlTrans(sSttk);
                // //System.out.println(pPart);
                rowImpl.setStlPart(pPart);
                ////System.out.println(pPartOrg);
                rowImpl.setStlPartOrg(pPartOrg);
                ////System.out.println(pBin);
                rowImpl.setStlBin(pBin);
                ////System.out.println(pLot);
                rowImpl.setStlLot(pLot);
                ////System.out.println(pStore);
                rowImpl.setStlStore(pStore);
                // //System.out.println(pExpQty);
                //rowImpl.setStlExpqty(pExpQty);
                ////System.out.println(storeItem);
                //rowImpl.setStlType(sStoreroom);
                rowImpl.setStlType(storeItem);
                ////System.out.println(iListid);
                rowImpl.setStlListid(iListid);
                vo.insertRow(rowImpl);
                //System.out.println("InsertRow");
            }
        }
        //System.out.println("b4 Commit");
        ret = commit_action();
        //System.out.println("after Commit");
        if (ret == null) {
            ////System.out.println("ret is null");
            //            this.storePartsProcessBtn.setDisabled(true);
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.storePartsProcessBtn);
            resetStoresTbl();
            resetTableFilter();
        }
        return ret;
    }

    public String processStoreRoomParts() {
        String retwriteCCListXML = "";
        Integer iListId = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfListId", iListId);

        java.util.Date date = new java.util.Date();
        //System.out.println("Start: " + new Timestamp(date.getTime()));

        ArrayList<String> stringList = getCheckedStorerooms();

        for (String storeItem : stringList) {

            retwriteCCListXML = writeCCStoreListXML(iListId.toString(), storeItem);
            //System.out.println("retwriteCCListXML: " + retwriteCCListXML);
            //String msg = "Storeroom " + storeItem + " parts created successfully";
        }
        //        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //        popCCListConfirm.show(hints);

        date = new java.util.Date();
        //System.out.println("End: " + new Timestamp(date.getTime()));
        String ret = commit_action();

        if ("TRUE".equals(retwriteCCListXML) && ret == null) {
            //            //System.out.println("retwriteCCListXML 2: " + retwriteCCListXML);
            resetStoresTbl();
            resetSelectedPartsTbl();
            //
            this.ccpartsResults.resetStampState();
            //            this.includeChk.setDisabled(true);
            //            this.includeChk.resetValue();
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);

            refreshCurrentPage();
        }
        //goToControlFlow(null, "done");

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

    //Print selected values.

    public String readSelectedValues() {
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        JUCtrlListBinding listBinding = (JUCtrlListBinding)bindings.get("MrltCyclecountpartsMainStore1");
        Object[] selectedValues = listBinding.getSelectedValues();

        /**
         * in a productive application, this is where you would read the
         * selected values to update the business model. For this sample,
         * we just print the selected values
         */
        for (int i = 0; i < selectedValues.length; i++) {
            System.out.println("Employee Id = " + selectedValues[i]);
        }
        return null;
    }

    //Reset the selected shuttle values for a fresh start

    public String resetAction() {
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        JUCtrlListBinding listBinding = (JUCtrlListBinding)bindings.get("MrltCyclecountpartsMainStore1");
        listBinding.clearSelectedIndices();
        return null;
    }

    public String filterListAction() {
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        JUCtrlListBinding listBinding = (JUCtrlListBinding)bindings.get("MrltCyclecountpartsMainStore1");

        HashMap m = new HashMap();
        try {
            m.put("StlBin", new String(filterBin));

            //System.out.println("filterBin: " + filterBin);
        } catch (Exception e) {
            //TODO handle exception gracefully
            e.printStackTrace();
        }
        //listBinding.getDCIteratorBinding().getViewCriteria().clear();
        listBinding.getDCIteratorBinding().getViewCriteria().last().remove();
        //remove selected values before filtering
        //listBinding.clearSelectedIndices();
        listBinding.filterList(m);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.partShuttle);
        return null;
    }

    public void resetStoresTbl() {
        //System.out.println("resetStores()");
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("MrltCyclecountpartstoresViewIterator");
        ViewObject vo = iter.getViewObject();
        //ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("CycleCountPartsDisplayed");
        vo.applyViewCriteria(null);
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.modifyStoresStoreTable);
    }

    public void resetAvailablePartsTbl() {
        //System.out.println("resetStores()");
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("CycleCountPartsMainIterator");
        ViewObject vo = iter.getViewObject();
        //ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("CycleCountPartsDisplayed");
        vo.applyViewCriteria(null);
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);
    }

    public void resetCCInputTbl() {
        //System.out.println("resetCCInputTbl()");
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("MrltCyclecountpartsView");
        ViewObject vo = iter.getViewObject();
        //ViewCriteria vc = vo.getViewCriteriaManager().getViewCriteria("CycleCountPartsDisplayed");
        vo.applyViewCriteria(null);
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
        vo.executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccInputTbl);
    }

    public void resetSelectedPartsTbl() {
        //System.out.println("resetStores()");
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("CycleCountPartsMainSelectedIterator");
        ViewObject vo = iter.getViewObject();
        RowSetIterator rsi = vo.createRowSetIterator(null);
        while (rsi.hasNext()) {
            Row row = rsi.next();
            if (row != null) {
                row.setAttribute("Selected", false);
            }
        }
        rsi.closeRowSetIterator();
        //        vo.executeQuery();
        //this.includeChk.setDisabled(true);
        refreshSelectedBtn();
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsResults);

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.ccpartsSelectedResults);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.selectedCntTxt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.processStorePartsBtn);
    }

    public String refreshStorerooms(int listID) {
        BindingContainer bindings = getBindings();
        OperationBinding opBinding = bindings.getOperationBinding("refreshStores");
        opBinding.getParamsMap().put("bListid", new Integer(listID));
        Object result = opBinding.execute();
        if (!opBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String refreshSelectedBtn() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Execute1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String ccSetupRangeCanc_action() {
        // perform rollback operation
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        goToControlFlow(null, "goCloseSetup");
        return null;
    }

    public void selectStoreroomLsnr(ValueChangeEvent vce) {
        //System.out.println("selectStoreroomLsnr");

        BindingContainer bindings = getBindings();
        getCheckedStorerooms();

        //System.out.println("2");

    }

    public void enableDisableAvailablePartsTbl() {
        Integer chkCnt = getCheckedStoreroomCnt();
        //System.out.println("enableDisableAvailablePartsTbl ()");
        //System.out.println(chkCnt);

        if (chkCnt.compareTo(0) > 0) {
            //System.out.println("Enabling");
            this.includeChk.setDisabled(false);
            this.unchkSelBtn.setDisabled(false);
            this.chkSelBtn.setDisabled(false);
            //this.checkSelected.setDisabled(false);
            //this.unchkSelectedCxt.setDisabled(false);
        } else if (chkCnt.compareTo(0) == 0) {
            //System.out.println("Disabling");
            this.includeChk.setDisabled(true);
            this.unchkSelBtn.setDisabled(true);
            this.chkSelBtn.setDisabled(true);
            //this.checkSelected.setDisabled(true);
            //this.unchkSelectedCxt.setDisabled(true);
        }

        //System.out.println("PartialTargets");
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.includeChk);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.chkSelBtn);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.unchkSelBtn);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.chkSelectedCxt);
        //AdfFacesContext.getCurrentInstance().addPartialTarget(this.checkSelected);
    }

    public String printChkSel() {
        int iCnt = getCheckedStoreroomCnt() == null ? 0 : getCheckedStoreroomCnt();
        //System.out.println(iCnt);
        return null;
    }

    public String launchFIFOAction(ActionEvent ae) {

        Boolean chkFrozen = false;
        chkFrozen = checkSelected();
        String res = "";
        if (chkFrozen) {
            res = checkIfJobRunning(ae);
            if (res.equals("jobExists")) {
             //   //System.out.println("Inside launchFIFOAction res: " + res);
                userModificationMsg("INFO", "Info", "Cycle Count is not Frozen or Completed.");
            } else {
                //System.out.println("Inside jobCreated");
                String ret = "";
                ret = createJob(ae);
                if (ret.equals("jobCreated")){
                    //System.out.println("Inside launchFIFOAction ret.equals(jobCreated)");
                    goToControlFlow(null, "launchFIFO");
                }
                else
                    userModificationMsg("INFO", "Info", "Cycle Count is not Frozen or Completed.");
            }
        } else {
            userModificationMsg("INFO", "Info", "Cycle Count is not Frozen or Completed.");
        }


        return null;
    }

    public void FifoDelLsnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            DelFifo_action();
            commit_action();
            //close popup
            BindingContainer bindings = getBindings();
            RichPopup popup = this.getPopFifoDel();
            popup.hide();
        } else {
            //close popup
            BindingContainer bindings = getBindings();
            RichPopup popup = this.getPopFifoDel();
            popup.hide();
        }
    }

    public void emailSubscriptionLstnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {
            String emailStr = "";
            emailStr = emailSet();
            // commit_action();
            //close popup
            BindingContainer bindings = getBindings();
            RichPopup popup = this.getPpEmailSub();
            popup.hide();
        } else {
            //close popup
            BindingContainer bindings = getBindings();
            RichPopup popup = this.getPpEmailSub();
            popup.hide();
        }
    }


    public void openCompletePopup(ValueChangeEvent vce) {

        String newVal = vce.getNewValue().toString();
        //System.out.println("Inside ppEmailSub newVal: " + newVal);
        if (newVal.equals("COMPLETED")) {

            if (ADFContext.getCurrent().getSessionScope().containsKey("emailSubCh")) {
                ADFContext.getCurrent().getSessionScope().remove("emailSubCh");
            }
            ADFContext.getCurrent().getSessionScope().put("emailSubCh", newVal);

            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            ppEmailSub.show(hints);
        }
    }

    public String DelFifo_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public Boolean checkSelected() {
        ////System.out.println("checkSelected()");
        Boolean ret = false;
        RowKeySet selectedList = getCcListTable().getSelectedRowKeys();
        Iterator selectedListIter = selectedList.iterator();
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding listIter = bindings.findIteratorBinding("MrltCyclecountheaderViewIterator");
        RowSetIterator listRSIter = listIter.getRowSetIterator();
        while (selectedListIter.hasNext()) {
            Key key = (Key)((List)selectedListIter.next()).get(0);
            Row currentRow = listRSIter.getRow(key);
            String sStatus = (String)currentRow.getAttribute("Ccstatus");
            ////System.out.println(sStatus);
            if ("FROZEN".equals(sStatus) || "COMPLETED".equals(sStatus)) {
                ret = true;
            } else {
                return false;
            }
        }
        return ret;
    }

    public String onExit() {
        if (isDirty()) {
            onRollback();
        }
        goToControlFlow(null, "returnCC");
        return null;
    }

    public String onExitStore() {
        if (isDirty()) {
            //System.out.println("Rolling Back onExitStore");
            onRollback();
        }
        goToControlFlow(null, "Exit");
        return null;
    }

    public String onRollback() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public void setStoreShuttle(RichSelectManyShuttle storeShuttle) {
        this.storeShuttle = storeShuttle;
    }

    public RichSelectManyShuttle getStoreShuttle() {
        return storeShuttle;
    }

    public void setStoreProcessBtn(RichButton storeProcessBtn) {
        this.storeProcessBtn = storeProcessBtn;
    }

    public RichButton getStoreProcessBtn() {
        return storeProcessBtn;
    }

    public void setStoreClearBtn(RichButton storeClearBtn) {
        this.storeClearBtn = storeClearBtn;
    }

    public RichButton getStoreClearBtn() {
        return storeClearBtn;
    }

    public void setStoreCreateMoreStoreBtn(RichSelectBooleanCheckbox storeCreateMoreStoreBtn) {
        this.storeCreateMoreStoreBtn = storeCreateMoreStoreBtn;
    }

    public RichSelectBooleanCheckbox getStoreCreateMoreStoreBtn() {
        return storeCreateMoreStoreBtn;
    }


    public void setStorePartsProcessBtn(RichButton storePartsProcessBtn) {
        this.storePartsProcessBtn = storePartsProcessBtn;
    }

    public RichButton getStorePartsProcessBtn() {
        return storePartsProcessBtn;
    }

    public void setModifyStoresDelBtn(RichButton modifyStoresDelBtn) {
        this.modifyStoresDelBtn = modifyStoresDelBtn;
    }

    public RichButton getModifyStoresDelBtn() {
        return modifyStoresDelBtn;
    }

    public void setModifyStoresResetBtn(RichButton modifyStoresResetBtn) {
        this.modifyStoresResetBtn = modifyStoresResetBtn;
    }

    public RichButton getModifyStoresResetBtn() {
        return modifyStoresResetBtn;
    }

    public void setModifyStoresStoreTable(RichTable modifyStoresStoreTable) {
        this.modifyStoresStoreTable = modifyStoresStoreTable;
    }

    public RichTable getModifyStoresStoreTable() {
        return modifyStoresStoreTable;
    }

    public void setCcTimestampTxt(RichInputText ccTimestampTxt) {
        this.ccTimestampTxt = ccTimestampTxt;
    }

    public RichInputText getCcTimestampTxt() {
        return ccTimestampTxt;
    }

    public void setCcPhyqtyTxt(RichInputText ccPhyqtyTxt) {
        this.ccPhyqtyTxt = ccPhyqtyTxt;
    }

    public RichInputText getCcPhyqtyTxt() {
        return ccPhyqtyTxt;
    }

    public void setSetupRangeForwardBtn(RichButton setupRangeForwardBtn) {
        this.setupRangeForwardBtn = setupRangeForwardBtn;
    }

    public RichButton getSetupRangeForwardBtn() {
        return setupRangeForwardBtn;
    }

    public void setSetupRangeReturnBtn(RichButton setupRangeReturnBtn) {
        this.setupRangeReturnBtn = setupRangeReturnBtn;
    }

    public RichButton getSetupRangeReturnBtn() {
        return setupRangeReturnBtn;
    }

    public void setSetupRangeCancelBtn(RichButton setupRangeCancelBtn) {
        this.setupRangeCancelBtn = setupRangeCancelBtn;
    }

    public RichButton getSetupRangeCancelBtn() {
        return setupRangeCancelBtn;
    }

    public void setCcListTable(RichTable ccListTable) {
        this.ccListTable = ccListTable;
    }

    public RichTable getCcListTable() {
        return ccListTable;
    }

    public void setFilterBin(String filterBin) {
        this.filterBin = filterBin;
    }

    public String getFilterBin() {
        return filterBin;
    }

    public void setPartShuttle(RichSelectManyShuttle partShuttle) {
        this.partShuttle = partShuttle;
    }

    public RichSelectManyShuttle getPartShuttle() {
        return partShuttle;
    }

    public void setPopCCListConfirm(RichPopup popCCListConfirm) {
        this.popCCListConfirm = popCCListConfirm;
    }

    public RichPopup getPopCCListConfirm() {
        return popCCListConfirm;
    }
/*
    public void setCcListConfTxt(RichInputText ccListConfTxt) {
        this.ccListConfTxt = ccListConfTxt;
    }

    public RichInputText getCcListConfTxt() {
        return ccListConfTxt;
    }
*/
    public void setIncludeChk(RichSelectBooleanCheckbox includeChk) {
        this.includeChk = includeChk;
    }

    public RichSelectBooleanCheckbox getIncludeChk() {
        return includeChk;
    }


    public void setCcpartsSelectedResults(RichTable ccpartsSelectedResults) {
        this.ccpartsSelectedResults = ccpartsSelectedResults;
    }

    public RichTable getCcpartsSelectedResults() {
        return ccpartsSelectedResults;
    }

    public void setSelectedCntTxt(RichOutputLabel selectedCntTxt) {
        this.selectedCntTxt = selectedCntTxt;
    }

    public RichOutputLabel getSelectedCntTxt() {
        return selectedCntTxt;
    }

    public void setProcessStorePartsBtn(RichButton processStorePartsBtn) {
        this.processStorePartsBtn = processStorePartsBtn;
    }

    public RichButton getProcessStorePartsBtn() {
        return processStorePartsBtn;
    }

    public void setSelectContextMenu(RichMenu selectContextMenu) {
        this.selectContextMenu = selectContextMenu;
    }

    public RichMenu getSelectContextMenu() {
        return selectContextMenu;
    }

    public void setTusedCntTxt(RichInputText tusedCntTxt) {
        this.tusedCntTxt = tusedCntTxt;
    }

    public RichInputText getTusedCntTxt() {
        return tusedCntTxt;
    }


    public void setCheckSelected(RichCommandMenuItem checkSelected) {
        this.checkSelected = checkSelected;
    }

    public RichCommandMenuItem getCheckSelected() {
        return checkSelected;
    }

    public void setChkSelBtn(RichButton chkSelBtn) {
        this.chkSelBtn = chkSelBtn;
    }

    public RichButton getChkSelBtn() {
        return chkSelBtn;
    }

    public void setUnchkSelBtn(RichButton unchkSelBtn) {
        this.unchkSelBtn = unchkSelBtn;
    }

    public RichButton getUnchkSelBtn() {
        return unchkSelBtn;
    }

    public void setPopFifoDel(RichPopup popFifoDel) {
        this.popFifoDel = popFifoDel;
    }

    public RichPopup getPopFifoDel() {
        return popFifoDel;
    }

    public void setCcInputTbl(RichTable ccInputTbl) {
        this.ccInputTbl = ccInputTbl;
    }

    public RichTable getCcInputTbl() {
        return ccInputTbl;
    }

    public void setCountTab(RichShowDetailItem countTab) {
        this.countTab = countTab;
    }

    public RichShowDetailItem getCountTab() {
        return countTab;
    }

    public String getShowCycleCount() {
        return showCycleCount;
    }

    public void setShowCycleCount(String showCycleCount) {
        this.showCycleCount = showCycleCount;
    }

    public void binShuttleValue(ValueChangeEvent vce) {
        if (vce.getOldValue() != null)
            //System.out.println("vce.getOldValue().toString(): " + vce.getOldValue().toString() +  "vce.getOldValue().getClass(): " + vce.getOldValue().getClass());
        if (vce.getNewValue() != null) {
            //System.out.println("vce.getNewValue().toString(): " + vce.getNewValue().toString() + "vce.getNewValue().getClass(): " + vce.getNewValue().getClass());
            mark();
        } else
            unmark();
    }

    public void setCcpartsResults1(RichSelectManyShuttle ccpartsResults1) {
        this.ccpartsResults1 = ccpartsResults1;
    }

    public RichSelectManyShuttle getCcpartsResults1() {
        return ccpartsResults1;
    }

    public void getPartsHeader(ActionEvent ae) {
        //System.out.println("getPartsHeader");
        // //System.out.println("parent comp id: "+ae.getComponent().findComponent(arg0));
        BindingContainer bindings = getBindings();

        AttributeBinding attr = (AttributeBinding)bindings.getControlBinding("Listid");
        if (attr != null) {
            int listID = Integer.parseInt(attr.getInputValue().toString());
            ////System.out.println("listID: " + listID);
            AdfFacesContext adfFacesContext = AdfFacesContext.getCurrentInstance();
            Map pageFlowScope = adfFacesContext.getPageFlowScope();
            pageFlowScope.put("listID", listID);
        }

    }


    public void setAssignBinsToSRForm(RichPanelFormLayout assignBinsToSRForm) {
        this.assignBinsToSRForm = assignBinsToSRForm;
    }

    public RichPanelFormLayout getAssignBinsToSRForm() {
        return assignBinsToSRForm;
    }

    public void unmarkAction(ActionEvent actionEvent) {
        deSelectAllStorerooms();
        unmark();
    }

    public void setTselectedChbox(RichSelectBooleanCheckbox tselectedChbox) {
        this.tselectedChbox = tselectedChbox;
    }

    public RichSelectBooleanCheckbox getTselectedChbox() {
        return tselectedChbox;
    }

    private void deSelectAllStorerooms() {
        //System.out.println("getCheckedStoreroomCnt()");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("MrltCyclecountpartstoresViewIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            //Boolean sIsUsed = ("Y".equals(row.getAttribute("Used")) ? true : false);
            Boolean sIsUsed = (Boolean)row.getAttribute("tSelected");
            if (sIsUsed != null && sIsUsed) {
                row.setAttribute("tSelected", Boolean.FALSE);
            }
        }

    }

    public void setEmail1(RichInputText email1) {
        this.email1 = email1;
    }

    public RichInputText getEmail1() {
        return email1;
    }

    public void setEmail2(RichInputText email2) {
        this.email2 = email2;
    }

    public RichInputText getEmail2() {
        return email2;
    }

    public void setPpEmailSub(RichPopup ppEmailSub) {
        this.ppEmailSub = ppEmailSub;
    }

    public RichPopup getPpEmailSub() {
        return ppEmailSub;
    }

    public void emailSubCheckbox(ValueChangeEvent vce) {
        Boolean newVal = (Boolean)vce.getNewValue();
        System.out.println("newVal: " + newVal.booleanValue());
        if (ADFContext.getCurrent().getSessionScope().containsKey("emailSubCh")) {
            ADFContext.getCurrent().getSessionScope().remove("emailSubCh");
        }
        ADFContext.getCurrent().getSessionScope().put("emailSubCh", newVal);
    }

    public void setEmailSubChk(RichSelectBooleanCheckbox emailSubChk) {
        this.emailSubChk = emailSubChk;
    }

    public RichSelectBooleanCheckbox getEmailSubChk() {
        return emailSubChk;
    }


    public void emailValChngLstnr(ValueChangeEvent vce) {
        String oldVal = "";
        String newVal = "";
             
        if(vce.getOldValue()!=null){
            oldVal =  (String)vce.getOldValue();
            //System.out.println("Inside emailValChngLstnr oldVal");
        }
        if(vce.getNewValue()!=null){
            newVal =  (String)vce.getNewValue();
            //System.out.println("Inside emailValChngLstnr newVal");

            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = newVal;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Email is not in Proper Format";      
            if (matcher.matches()) {

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }        
    }
    
    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (object != null) {
            String name = object.toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            String msg = "Email is not in Proper Format";
            if (matcher.matches()) {

            } else {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
            }
        }
    }
/*
    public void emailValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        
        //if(uIComponent.getId().equals("email1")){
            //System.out.println("Inside emailValidator uIComponent.getId(): "+uIComponent.getId());
        if (object != null) {
            String name = object.toString();
            String expression = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr = name;
            Pattern pattern = Pattern.compile(expression);
            if(inputStr!=""){
                //System.out.println("Inside emailValidator inputStr:"+inputStr);
                Matcher matcher = pattern.matcher(inputStr);
                String msg = "Email is not in Proper Format";
                if(uIComponent.getId().equals("email1")){
                    if (matcher.matches()) {
                        //System.out.println("Inside emailValidator email1 matched");
                    } else {
                        throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                    }
                }
                if(uIComponent.getId().equals("email2")){
                    if(name!=""){
                        if (matcher.matches()) {
                            //System.out.println("Inside emailValidator email2 matched");
                        } else {
                            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                        }
                    }
                    else{
                        //System.out.println("Inside emailValidator email2 empty");
                    }
                }
            }
        }
   // }
    }
*/
    public void setPpJobRunning(RichPopup ppJobRunning) {
        this.ppJobRunning = ppJobRunning;
    }

    public RichPopup getPpJobRunning() {
        return ppJobRunning;
    }

    public String checkIfJobRunning(ActionEvent ae) {
        //System.out.println("Inside checkIfJobRunning");
        String ID = ae.getComponent().getId().toString();
       // //System.out.println("component IDs: " + ID);

      //  if ((ID.equals("CCSetup")) || (ID.equals("createAsCounted")) || (ID.equals("createAdjusted"))) {
            BindingContainer bindings = getBindings();

            OperationBinding operationBinding = bindings.getOperationBinding("ExecuteCCHeader");
            Object result = operationBinding.execute();

            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            String Jobid = "";

            OperationBinding oper = bindings.getOperationBinding("ExecuteJobRunning");
            Object res = oper.execute();

            if (!oper.getErrors().isEmpty()) {
                return null;
            }
            AttributeBinding jobIdBinding = (AttributeBinding)getBindings().getControlBinding("Jobid");
            Jobid = (String)jobIdBinding.getInputValue();


            if (Jobid != null) {

              //  //System.out.println("Inside checkIfJobRunning Jobid: " + Jobid);
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                ppJobRunning.show(hints);
                return "jobExists";
            }
            else{
                if(ID.equals("CCSetup")){
                   // //System.out.println("CCSetup IDs: " + ID);
                    String ret = "";
                    ret = createJob(ae);
                    if (ret.equals("jobCreated")){
                        ////System.out.println("Inside launchFIFOAction ret.equals(jobCreated)");
                        goToControlFlow(null, "launchCCSetup");
                    }
                }                  
                if(ID.equals("CCStoreSetup")){
                         //   //System.out.println("CCStoreSetupcomponent IDs: " + ID);
                            getPartsHeader(ae);
                        }
                if(ID.equals("delFifo")){
                  //  //System.out.println("delFifo IDs: " + ID);
                    RichPopup.PopupHints hints = new RichPopup.PopupHints();
                    popFifoDel.show(hints);
                }
            }
       // }

        return "jobDoesNotExist";
    }

    public String createJob(ActionEvent ae) {
        String ID = "";
        ID = ae.getComponent().getId().toString();
      //  //System.out.println("Inside createJob ID: " + ID);

        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("MrltCyclecountheaderViewIterator");
        ViewObject vo = iter.getViewObject();
        Row r = vo.getCurrentRow();
        Integer iListid = (Integer)r.getAttribute("Listid");

        if (ID.equals("CCSetup")) {
           // //System.out.println("Inside createJob ID: " + ID + " set Jobid: CC" + iListid + "CreateList");
            r.setAttribute("Jobid", "CC" + iListid + "CreateList");
            //commit_action();
        } else if (ID.equals("createAsCounted")) {
          //  //System.out.println("Inside createJob ID: " + ID + " set Jobid: CC" + iListid +
                           //    "Create_As_Counted_Fifo");
            r.setAttribute("Jobid", "CC" + iListid + "Create_As_Counted_Fifo");
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfFIFO", "AC");
          //  commit_action();
        } else if (ID.equals("createAdjusted")) {
          //  //System.out.println("Inside createJob ID: " + ID + " set Jobid: CC" + iListid +
                            //   "Create_Adjusted_Fifo");
            r.setAttribute("Jobid", "CC" + iListid + "Create_Adjusted_Fifo");
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put("psfFIFO", "ADJ");
          //  commit_action();
        }
        
        return "jobCreated";
    }

    /**Method to invoke EL Expression
     * @param el
     * @param paramTypes
     * @param params
     * @return
     */
    public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        MethodExpression exp = expressionFactory.createMethodExpression(elContext, el, Object.class, paramTypes);

        return exp.invoke(elContext, params);
    }

    public void partQueryProcess(QueryEvent queryEvent) {
        String partNo="";
        String binNo="";
        String sr="";
        String discrepant="";
        //System.out.println("Inside partQueryProcess");
        /**Get Iterator of Table*/
        DCIteratorBinding iter = (DCIteratorBinding)getBindings().get("MrltCyclecountpartsViewIterator");
                                                                        
        /**Get ViewObject from Iterator*/
       // ViewObjectImpl vo = (ViewObjectImpl)iter.getViewObject();

        /**Get Bind Variable's Value*/
        if (iter.getViewObject().getNamedWhereClauseParam("StlPart") != null) {
            partNo= (String)iter.getViewObject().getNamedWhereClauseParam("StlPart");
        }
        if (iter.getViewObject().getNamedWhereClauseParam("StlBin") != null) {
            binNo= (String)iter.getViewObject().getNamedWhereClauseParam("StlBin");
        }
        if (iter.getViewObject().getNamedWhereClauseParam("StlType") != null) {
            sr= (String)iter.getViewObject().getNamedWhereClauseParam("StlType");
        }
        if (iter.getViewObject().getNamedWhereClauseParam("tDiscrepant") != null) {
            discrepant= (String)iter.getViewObject().getNamedWhereClauseParam("tDiscrepant");
        }

   ////System.out.println("Inside partQueryProcess partNo: "+partNo+" binNo: "+binNo+" sr: "+sr+" discrepant: "+discrepant);

            invokeEL("#{bindings.DepartmentsViewCriteriaQuery.processQuery}", new Class[] { QueryEvent.class },
                     new Object[] { queryEvent });
      
        
    }

    public void setMainHdr(RichPanelHeader mainHdr) {
        this.mainHdr = mainHdr;
}

    public RichPanelHeader getMainHdr() {
        return mainHdr;
    }
}
