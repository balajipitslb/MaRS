package its.mnr.mp5.view;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import its.mnr.mp5.vo.po.POTranslinesViewImpl;
import its.mnr.mp5.vo.po.POTranslinesViewRowImpl;
import its.mnr.mp5.vo.workorder.WOEventsImpl;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.event.PhaseEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.adf.view.rich.component.rich.data.RichColumn;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailHeader;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.component.rich.output.RichPanelCollection;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ContextInfoEvent;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.QueryDescriptor;
import oracle.adf.view.rich.render.ClientEvent;


import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowNotFoundException;

import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.server.ViewRowImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.bean.util.ValueMap;
import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;


import utils.system;

public class POMaintenanceBean {
    private RichPopup poLinePop;
    private RichDialog poLineDiag;
    private RichPopup pendingChangePop;
    private RichPopup poRecvPendingChangePop;
    private RichPopup xchargesPop;
    private RichDialog xchargesDiag;
    private RichOutputText xchargesTotal;
    private RichOutputText ttotalOrderLine;
    private RichButton editBtn;
    private RichTable partOrder;
    private RichTable serviceOrder;
    private RichPanelCollection orderTypePanel;
    private RichPopup lineTypeRadioPop;
    private RichTable resultsSearchTable;
    private RichPopup addPartToSuppPop;
    private RichTable recvTransTable;
    private RichColumn trlQty;
    private RichInputListOfValues trlLot;
    private RichInputListOfValues trlBin;
    private RichInputText itTrlQty;
    private RichShowDetailItem sdiReceive;
    private RichShowDetailItem sdiReturn;
    private RichColumn trlQtyRetn;
    private RichInputText itTrlQtyRetn;
    private RichOutputText trlBinRetn;
    private RichShowDetailItem sdiRecord;
    private RichCommandMenuItem menuNewPO;
    private RichCommandMenuItem menuNewRecv;
    private RichCommandMenuItem menuNewRetn;
    private RichSelectOneRadio rdoOrlType;
    private RichInputListOfValues lovPart;
    private RichInputText itUnitPrice;
    private RichPopup recvLinePop;
    private RichSelectBooleanCheckbox extChgChk;
    private RichOutputText tordPrice;
    private RichSelectOneChoice percentage;
    private RichInputText echDiscperc;
    private RichColumn perc;
    private RichShowDetailHeader commentssec;
    private RichPanelHeader mainhdr;
    private RichTable poTransTbl;
    private RichDialog deptPpDiag;
    private RichPopup deptPp;
    private RichTable retTransTable;
    private RichPanelFormLayout ordSummaryForm;
    private RichPanelSplitter mainPanelSplitter;
    private RichPanelTabbed poTab;

    public POMaintenanceBean() {
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
/*_orig()*/
    public String apply_action() {
        //System.out.println("apply_action");
        //sopTransaction();
        boolean chkRecv = true;
        boolean chkRet = true;
        if (tabDisclosed() == "RETURN") {
            chkRet = chkRetMandatoryFields();
        }
        if (tabDisclosed() == "RECEIVE") {
            chkRecv = chkRecvMandatoryFields();
        }
        if (chkRet && chkRecv) {
            //System.out.println(chkRet + ":" + chkRecv);
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            //System.out.println(result);
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        }

        if (tabDisclosed() == "RETURN") {
            resetTranslinesRETN();
        }

        return null;
    }


 /*
    public String apply_action()_new {
        //System.out.println("apply_action");
        //sopTransaction();
        boolean chkRecv = true;
        boolean chkRet = true;
        if (tabDisclosed() == "RETURN"){
               chkRet = chkRetMandatoryFields();
            }
        if (tabDisclosed() == "RECEIVE"){
               chkRecv = chkRecvMandatoryFields();
            }
        if (chkRet && chkRecv) {

            String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
            String sAddCode = (String)ADFUtil.evaluateEL("#{bindings.AddCode.inputValue}");
            Integer sOLineCode = (Integer)ADFUtil.evaluateEL("#{bindings.OrlOrdline.inputValue}");
            String sEchInvOrdCd = (String)ADFUtil.evaluateEL("#{bindings.EchInvordCode.inputValue}");


            //System.out.println(chkRet + ":" + chkRecv);
            BindingContainer bindings = getBindings();

            // get current rows
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POrdersViewIterator");
            Row rowKey = iter.getCurrentRow();
            // iter.getViewObject().executeQuery();

            DCIteratorBinding iterCmt = (DCIteratorBinding)getDCBindingContainer().get("POrdersCommentIterator");
            Row rowCmt = iterCmt.getCurrentRow();
            // iterCmt.getViewObject().executeQuery();

            DCIteratorBinding iterLine = (DCIteratorBinding)getDCBindingContainer().get("POrderlinesViewIterator");
            Row rowLine = iterLine.getCurrentRow();
            // iterLine.getViewObject().executeQuery();

            DCIteratorBinding iterEch = (DCIteratorBinding)getDCBindingContainer().get("POExchargesViewIterator");
            Row rowEch = iterEch.getCurrentRow();
            //iterEch.getViewObject().executeQuery();

            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            //System.out.println(result);
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }

            //Need this line to reset the POOrdersView Iterator to avaoid the Another user chnaged....error.
            iter.executeQuery();


            //set current rows
            if (StringUtils.isNotBlank(sOrdCode)) {
                if (rowKey != null) {
                    Key parentKey = rowKey.getKey();
                    try {
                        iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        //System.out.println("Setting Orders");
                    } catch (RowNotFoundException ex) {
                        iter.getViewObject().applyViewCriteria(null);
                        iter.executeQuery();
                        iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }

            if (StringUtils.isNotBlank(sAddCode)) {
                if (rowCmt != null) {
                    Key parentKeyCmt = rowCmt.getKey();
                    try {
                        iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                        //System.out.println("Setting Comments");
                    } catch (RowNotFoundException ex) {
                        iterCmt.getViewObject().applyViewCriteria(null);
                        iterCmt.executeQuery();
                        iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                    }
                }
            }


            if (sOLineCode != null) {
                //System.out.println("Setting Orderlines sOLineCode: "+sOLineCode);
                if (rowLine != null) {
                    Key parentKeyLine = rowLine.getKey();
                    try {
                        iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));

                    } catch (RowNotFoundException ex) {
                        //   System.out.println("Setting Orderlines in the catch block");
                        iterLine.getViewObject().applyViewCriteria(null);
                        iterLine.executeQuery();
                        iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));
                    }
                }
            }


            if (StringUtils.isNotBlank(sEchInvOrdCd)) {
                if (rowEch != null) {
                    Key parentKeyEch = rowEch.getKey();
                    try {
                        iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                        //System.out.println("Setting Extra Charges");
                    } catch (RowNotFoundException ex) {
                        iterEch.getViewObject().applyViewCriteria(null);
                        iterEch.executeQuery();
                        iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));

                    }
                }
            }
        }
    
        AdfFacesContext.getCurrentInstance().addPartialTarget(mainPanelSplitter);
        
        if (tabDisclosed() == "RECEIVE"){
                refreshIterators();
                    DCIteratorBinding iter =
                        (DCIteratorBinding)getDCBindingContainer().get("POTransactionsRecvViewIterator");
                    iter.getViewObject().executeQuery();
                    DCIteratorBinding iter1 =
                        (DCIteratorBinding)getDCBindingContainer().get("POTranslinesRecvViewIterator");
                    iter1.getViewObject().executeQuery();

                    // AdfFacesContext.getCurrentInstance().addPartialTarget(mainhdr);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(poTransTbl);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(recvTransTable);

            }
        if (tabDisclosed() == "RETURN"){
               resetTranslinesRETN();
            }

        return null;
    }


    public String apply_action_old() {
        //System.out.println("apply_action");
        //sopTransaction();
        boolean chkRecv = true;
        boolean chkRet = true;

        BindingContainer bindings = getBindings();

        if (tabDisclosed() == "RETURN") {
            chkRet = chkRetMandatoryFields();
        }
        if (tabDisclosed() == "RECEIVE") {
            chkRecv = chkRecvMandatoryFields();
        }
        if (chkRet && chkRecv) {
            //System.out.println(chkRet + ":" + chkRecv);


            OperationBinding operationBinding = bindings.getOperationBinding("Commit");

            operationBinding.execute();

            refreshIterators();
                DCIteratorBinding iter =
                    (DCIteratorBinding)getDCBindingContainer().get("POTransactionsRecvViewIterator");
                iter.getViewObject().executeQuery();
                DCIteratorBinding iter1 =
                    (DCIteratorBinding)getDCBindingContainer().get("POTranslinesRecvViewIterator");
                iter1.getViewObject().executeQuery();
        
                // AdfFacesContext.getCurrentInstance().addPartialTarget(mainhdr);
                AdfFacesContext.getCurrentInstance().addPartialTarget(poTransTbl);
                AdfFacesContext.getCurrentInstance().addPartialTarget(recvTransTable);
            if (!operationBinding.getErrors().isEmpty()) {

                return null;
            }

        }

        if (tabDisclosed() == "RETURN") {
            resetTranslinesRETN();
        }

        return null;
    }
*/
    public String recvPopupRollback() {
        
        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
        String sAddCode = (String)ADFUtil.evaluateEL("#{bindings.AddCode.inputValue}");
        Integer sOLineCode = (Integer)ADFUtil.evaluateEL("#{bindings.OrlOrdline.inputValue}");
        String sEchInvOrdCd = (String)ADFUtil.evaluateEL("#{bindings.EchInvordCode.inputValue}");


        //System.out.println(chkRet + ":" + chkRecv);
        BindingContainer bindings = getBindings();

        // get current rows
        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POrdersViewIterator");
        Row rowKey = iter.getCurrentRow();
        // iter.getViewObject().executeQuery();

        DCIteratorBinding iterCmt = (DCIteratorBinding)getDCBindingContainer().get("POrdersCommentIterator");
        Row rowCmt = iterCmt.getCurrentRow();
        // iterCmt.getViewObject().executeQuery();

        DCIteratorBinding iterLine = (DCIteratorBinding)getDCBindingContainer().get("POrderlinesViewIterator");
        Row rowLine = iterLine.getCurrentRow();
        // iterLine.getViewObject().executeQuery();

        DCIteratorBinding iterEch = (DCIteratorBinding)getDCBindingContainer().get("POExchargesViewIterator");
        Row rowEch = iterEch.getCurrentRow();
        //iterEch.getViewObject().executeQuery();

        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();

        //Need this line to reset the POOrdersView Iterator to avaoid the Another user chnaged....error.
        iter.executeQuery();


        //set current rows
        if (StringUtils.isNotBlank(sOrdCode)) {
            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                try {
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    //System.out.println("Setting Orders");
                } catch (RowNotFoundException ex) {
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
        }

        if (StringUtils.isNotBlank(sAddCode)) {
            if (rowCmt != null) {
                Key parentKeyCmt = rowCmt.getKey();
                try {
                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                    //System.out.println("Setting Comments");
                } catch (RowNotFoundException ex) {
                    iterCmt.getViewObject().applyViewCriteria(null);
                    iterCmt.executeQuery();
                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                }
            }
        }


        if (sOLineCode != null) {
            //System.out.println("Setting Orderlines sOLineCode: "+sOLineCode);
            if (rowLine != null) {
                Key parentKeyLine = rowLine.getKey();
                try {
                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));

                } catch (RowNotFoundException ex) {
                    //   System.out.println("Setting Orderlines in the catch block");
                    iterLine.getViewObject().applyViewCriteria(null);
                    iterLine.executeQuery();
                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));
                }
            }
        }


        if (StringUtils.isNotBlank(sEchInvOrdCd)) {
            if (rowEch != null) {
                Key parentKeyEch = rowEch.getKey();
                try {
                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                    //System.out.println("Setting Extra Charges");
                } catch (RowNotFoundException ex) {
                    iterEch.getViewObject().applyViewCriteria(null);
                    iterEch.executeQuery();
                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));

                }
            }
        }
              
        
        /*
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        operationBinding.execute();
        refreshIterators();
            */
        DCIteratorBinding iter1 = (DCIteratorBinding)getDCBindingContainer().get("POTransactionsRecvViewIterator");
        iter1.getViewObject().executeQuery();
        DCIteratorBinding iter2 = (DCIteratorBinding)getDCBindingContainer().get("POTranslinesRecvViewIterator");
        iter2.getViewObject().executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.mainhdr);
        if (!operationBinding.getErrors().isEmpty()) {

            return null;
        }
        return null;
    }

    public void poRecvPendingChangeDiaLstnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.yes) {
            //  System.out.println("Inside pendingChangeDiagListener DialogEvent.Outcome.yes");
            refreshIterators();
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POTransactionsRecvViewIterator");
            iter.getViewObject().executeQuery();
            DCIteratorBinding iter1 = (DCIteratorBinding)getDCBindingContainer().get("POTranslinesRecvViewIterator");
            iter1.getViewObject().executeQuery();
            apply_action();
        }
    }
    

    public void refreshIterators() {

        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
        String sAddCode = (String)ADFUtil.evaluateEL("#{bindings.AddCode.inputValue}");
        Integer sOLineCode = (Integer)ADFUtil.evaluateEL("#{bindings.OrlOrdline.inputValue}");
        String sEchInvOrdCd = (String)ADFUtil.evaluateEL("#{bindings.EchInvordCode.inputValue}");

        // get current rows
        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POrdersViewIterator");
        Row rowKey = iter.getCurrentRow();

        DCIteratorBinding iterCmt = (DCIteratorBinding)getDCBindingContainer().get("POrdersCommentIterator");
        Row rowCmt = iterCmt.getCurrentRow();

        DCIteratorBinding iterLine = (DCIteratorBinding)getDCBindingContainer().get("POrderlinesViewIterator");
        Row rowLine = iterLine.getCurrentRow();

        DCIteratorBinding iterEch = (DCIteratorBinding)getDCBindingContainer().get("POExchargesViewIterator");
        Row rowEch = iterEch.getCurrentRow();

        //set current rows
        if (StringUtils.isNotBlank(sOrdCode)) {
            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                try {
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    //  System.out.println("Inside refreshIterators Setting Orders sOrdCode: " + sOrdCode);
                } catch (RowNotFoundException ex) {
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
        }

        if (StringUtils.isNotBlank(sAddCode)) {
            if (rowCmt != null) {
                Key parentKeyCmt = rowCmt.getKey();
                try {
                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                    //  System.out.println("Inside refreshIterators Setting Comments sAddCode: " + sAddCode);
                } catch (RowNotFoundException ex) {
                    iterCmt.getViewObject().applyViewCriteria(null);
                    iterCmt.executeQuery();
                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                }
            }
        }


        if (sOLineCode != null) {
            //System.out.println("Inside refreshIterators Setting Orderlines sOLineCode: " + sOLineCode);
            if (rowLine != null) {
                Key parentKeyLine = rowLine.getKey();
                try {
                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));

                } catch (RowNotFoundException ex) {
                    // System.out.println("Inside refreshIterators Setting Orderlines in the catch block");
                    iterLine.getViewObject().applyViewCriteria(null);
                    iterLine.executeQuery();
                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));
                }
            }
        }


        if (StringUtils.isNotBlank(sEchInvOrdCd)) {
            if (rowEch != null) {
                Key parentKeyEch = rowEch.getKey();
                try {
                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                    // System.out.println("Inside refreshIterators Setting Extra Charges sEchInvOrdCd: " + sEchInvOrdCd);
                } catch (RowNotFoundException ex) {
                    iterEch.getViewObject().applyViewCriteria(null);
                    iterEch.executeQuery();
                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                }
            }
        }
    }

    public String applyOLine_action() {
        //update Order Price and Discount
        //        updOrderPriceDiscount();

        //Double price = 0.0;
        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
        String sAddCode = (String)ADFUtil.evaluateEL("#{bindings.AddCode.inputValue}");
        Integer sOLineCode = (Integer)ADFUtil.evaluateEL("#{bindings.OrlOrdline.inputValue}");
        String sEchInvOrdCd = (String)ADFUtil.evaluateEL("#{bindings.EchInvordCode.inputValue}");


        BindingContainer bindings = getBindings();
        if (tabDisclosed() == "RECORD"){

        //update Order Price and Discount
        updOrderPriceDiscount();
        }

        // get current rows
        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POrdersViewIterator");
        Row rowKey = iter.getCurrentRow();
        // iter.getViewObject().executeQuery();

        DCIteratorBinding iterCmt = (DCIteratorBinding)getDCBindingContainer().get("POrdersCommentIterator");
        Row rowCmt = iterCmt.getCurrentRow();
        // iterCmt.getViewObject().executeQuery();

        DCIteratorBinding iterLine = (DCIteratorBinding)getDCBindingContainer().get("POrderlinesViewIterator");
        Row rowLine = iterLine.getCurrentRow();
        // iterLine.getViewObject().executeQuery();

        DCIteratorBinding iterEch = (DCIteratorBinding)getDCBindingContainer().get("POExchargesViewIterator");
        Row rowEch = iterEch.getCurrentRow();
        //iterEch.getViewObject().executeQuery();


        // perform rollback operation
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        //  AdfFacesContext.getCurrentInstance().addPartialTarget(ordSummaryForm);

        //Need this line to reset the POOrdersView Iterator to avaoid the Another user chnaged....error.
        iter.executeQuery();


        //set current rows
        if (StringUtils.isNotBlank(sOrdCode)) {
            if (rowKey != null) {
                Key parentKey = rowKey.getKey();
                try {
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    //System.out.println("Setting Orders");
                } catch (RowNotFoundException ex) {
                    iter.getViewObject().applyViewCriteria(null);
                    iter.executeQuery();
                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                }
            }
        }

        if (StringUtils.isNotBlank(sAddCode)) {
            if (rowCmt != null) {
                Key parentKeyCmt = rowCmt.getKey();
                try {
                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                    //System.out.println("Setting Comments");
                } catch (RowNotFoundException ex) {
                    iterCmt.getViewObject().applyViewCriteria(null);
                    iterCmt.executeQuery();
                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                }
            }
        }


        if (sOLineCode != null) {
            //System.out.println("Setting Orderlines sOLineCode: "+sOLineCode);
            if (rowLine != null) {
                Key parentKeyLine = rowLine.getKey();
                try {
                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));

                } catch (RowNotFoundException ex) {
                    //   System.out.println("Setting Orderlines in the catch block");
                    iterLine.getViewObject().applyViewCriteria(null);
                    iterLine.executeQuery();
                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));
                }
            }
        }


        if (StringUtils.isNotBlank(sEchInvOrdCd)) {
            if (rowEch != null) {
                Key parentKeyEch = rowEch.getKey();
                try {
                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                    //System.out.println("Setting Extra Charges");
                } catch (RowNotFoundException ex) {
                    iterEch.getViewObject().applyViewCriteria(null);
                    iterEch.executeQuery();
                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                }
            }
        }
        //refresh view
        // resetFieldsRollback();

        //  on_rollback();
        //  refreshIterators();

        return null;
    }

    public String tabDisclosed() {
        String sTab = "";
        if (getSdiReceive().isDisclosed()) {
            sTab = "RECEIVE";
        } else if (getSdiRecord().isDisclosed()) {
            sTab = "RECORD";
        } else if (getSdiReturn().isDisclosed()) {
            sTab = "RETURN";
        }
        //System.out.println(sTab);
        return sTab;
    }

    public String updOrderPriceDiscount() {

        //  System.out.println("Inside updOrderPriceDiscount");
        Double stOrderlineSum = (Double)ADFUtil.evaluateEL("#{bindings.tOrderlineSum.inputValue}");
        Double stPriceExtended1 = (Double)ADFUtil.evaluateEL("#{bindings.tPriceExtended1.inputValue}");
        //  System.out.println("Inside updOrderPriceDiscount stOrderlineSum: " + stOrderlineSum + " stPriceExtended1: " +
        //            stPriceExtended1);


        ADFUtil.setEL("#{bindings.OrdPrice.inputValue}", (stOrderlineSum != null ? stOrderlineSum : 0));
        ADFUtil.setEL("#{bindings.OrdDiscount.inputValue}", (stPriceExtended1 != null ? stPriceExtended1 : 0));


        AdfFacesContext.getCurrentInstance().addPartialTarget(this.tordPrice);
        return null;

    }

    public void sopTransaction() {

        // get binding for Results table
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POTransactionsRecvViewIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);
            // create Invoice Item record for every selected Work Order
            //                    System.out.println(row.getAttribute("TraAcd"));
            //            System.out.println(row.getAttribute("TraAuth"));
            //            System.out.println(row.getAttribute("TraCode"));
            //            System.out.println(row.getAttribute("TraDate"));
            //            System.out.println(row.getAttribute("TraDesc"));
            //            System.out.println(row.getAttribute("TraFromcode"));
            //            System.out.println(row.getAttribute("TraFromcodeOrg"));
            //            System.out.println(row.getAttribute("TraFromentity"));
            //            System.out.println(row.getAttribute("TraFromrentity"));
            //            System.out.println(row.getAttribute("TraFromrtype"));
            //            System.out.println(row.getAttribute("TraFromtype"));
            //            System.out.println(row.getAttribute("TraOrder"));
            //            System.out.println(row.getAttribute("TraOrderOrg"));
            //            System.out.println(row.getAttribute("TraOrg"));
            //            System.out.println(row.getAttribute("TraRstatus"));
            //            System.out.println(row.getAttribute("TraRtype"));
            //            System.out.println(row.getAttribute("TraStatus"));
            //            System.out.println(row.getAttribute("TraTocode"));
            //            System.out.println(row.getAttribute("TraTocodeOrg"));
            //            System.out.println(row.getAttribute("TraToentity"));
            //            System.out.println(row.getAttribute("TraTorentity"));
            //            System.out.println(row.getAttribute("TraTortype"));
            //            System.out.println(row.getAttribute("TraTotype"));
            //            System.out.println(row.getAttribute("TraType"));
            //            System.out.println(row.getAttribute("TraPrinted"));

        }
    }

    public boolean chkRetMandatoryFields() {
        boolean chk = true;
        // get binding for Results table
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POTranslinesRetViewIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);

            if (row.getAttribute("TrlQty") == null) {
                chk = false;
                break;
            }
        }
        if (!chk) {
            userModificationMsg("WARN", "Warn", "Quantity is required.");
        }
        return chk;
    }

    public boolean chkRecvMandatoryFields() {
        System.out.println("Checking RECV mandatory fields");
        boolean chk = true;
        // get binding for Results table
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POTranslinesRecvViewIterator");

        for (int i = 0; i < dciter.getViewObject().getEstimatedRowCount(); i++) {
            Row row = dciter.getRowAtRangeIndex(i);

            if (row.getAttribute("TrlQty") == null || row.getAttribute("TrlBin") == null ||
                row.getAttribute("TrlLot") == null) {
                //System.out.println("Part: " + row.getAttribute("TrlPart") + " fields missing");
                chk = false;
                break;
            }
            //System.out.println("Part: " + row.getAttribute("TrlPart") + " okay");
        }
        if (!chk) {
            userModificationMsg("WARN", "Warn", "Quantity, Bin, and Lot are required.");
        }
        return chk;
    }

    public void recvQtyValidations(FacesContext facesContext, UIComponent uIComponent, Object object) {
        //System.out.println("recvQtyGtZero validation");
        Double dTrlQty = (Double)object;
        //System.out.println("dTrlQty: " + dTrlQty);

        if (dTrlQty <= 0) {
            String error = "Quantity must be greater than 0";
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
        }

        if (tabDisclosed() == "RECEIVE") {
            Double dOutQty = (Double)ADFUtil.evaluateEL("#{bindings.tOutstanding.inputValue}");
            if (dTrlQty > dOutQty) {
                String error = "Can not receive more than Outstanding order qty ( " + dOutQty + " )";
                //  AdfFacesContext.getCurrentInstance().addPartialTarget(recvTransTable);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
        }

        if (tabDisclosed() == "RETURN") {
            Double dOrlRecvqty = (Double)ADFUtil.evaluateEL("#{bindings.tOrlRecvqty.inputValue}");
            if (dTrlQty > dOrlRecvqty) {
                String error = "Return can not exceed " + dOrlRecvqty;
                // AdfFacesContext.getCurrentInstance().addPartialTarget(retTransTable);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }
            Double dOnHandQty = (Double)ADFUtil.evaluateEL("#{bindings.tTrlOnHandQty.inputValue}");
            if (dOnHandQty - dTrlQty < 0) {
                String error = "Number of part returned will result in negative on-hand quantity";
                //  AdfFacesContext.getCurrentInstance().addPartialTarget(retTransTable);
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, error, null));
            }

        }
    }

    public String newRecv_action() {
        String sOrdStatus = (String)ADFUtil.evaluateEL("#{bindings.OrdStatus1.inputValue}");

        if ("A".equals(sOrdStatus)) {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert3");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {
            userModificationMsg("WARN", "Warn", "Order must be Approved/Completed.");
        }
        return null;
    }

    public String newRETN_action() {
        String sOrdStatus = (String)ADFUtil.evaluateEL("#{bindings.OrdStatus1.inputValue}");

        //        if ("RC".equals(sOrdStatus)) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert4");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        } else {
        //            userModificationMsg("WARN", "Warn", "Order must be Recieved/Closed.");
        //        }
        return null;
    }

    public String on_rollback() {
        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
        String sAddCode = (String)ADFUtil.evaluateEL("#{bindings.AddCode.inputValue}");
        Integer sOLineCode = (Integer)ADFUtil.evaluateEL("#{bindings.OrlOrdline.inputValue}");
        String sEchInvOrdCd = (String)ADFUtil.evaluateEL("#{bindings.EchInvordCode.inputValue}");
        BindingContainer bindings = getBindings();
        if (StringUtils.isBlank(sOrdCode)) {
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {

            // get current rows
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POrdersViewIterator");
            Row rowKey = iter.getCurrentRow();

            DCIteratorBinding iterCmt = (DCIteratorBinding)getDCBindingContainer().get("POrdersCommentIterator");
            Row rowCmt = iterCmt.getCurrentRow();

            DCIteratorBinding iterLine = (DCIteratorBinding)getDCBindingContainer().get("POrderlinesViewIterator");
            Row rowLine = iterLine.getCurrentRow();

            DCIteratorBinding iterEch = (DCIteratorBinding)getDCBindingContainer().get("POExchargesViewIterator");
            Row rowEch = iterEch.getCurrentRow();

            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            //set current rows
            if (StringUtils.isNotBlank(sOrdCode)) {
                if (rowKey != null) {
                    Key parentKey = rowKey.getKey();
                    try {
                        iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        //System.out.println("Setting Orders");
                    } catch (RowNotFoundException ex) {
                        iter.getViewObject().applyViewCriteria(null);
                        iter.executeQuery();
                        //iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }

            if (StringUtils.isNotBlank(sAddCode)) {
                if (rowCmt != null) {
                    Key parentKeyCmt = rowCmt.getKey();
                    try {
                        iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                        //System.out.println("Setting Comments");
                    } catch (RowNotFoundException ex) {
                        iterCmt.getViewObject().applyViewCriteria(null);
                        iterCmt.executeQuery();
                        //iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                    }
                }
            }

            if (sOLineCode != null) {
                if (rowLine != null) {
                    Key parentKeyLine = rowLine.getKey();
                    try {
                        iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));
                        //System.out.println("Setting Orderlines");
                    } catch (RowNotFoundException ex) {
                        iterLine.getViewObject().applyViewCriteria(null);
                        iterLine.executeQuery();
                        //iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));
                    }
                }
            }
            if (StringUtils.isNotBlank(sEchInvOrdCd)) {
                if (rowEch != null) {
                    Key parentKeyEch = rowEch.getKey();
                    try {
                        iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                        //System.out.println("Setting Extra Charges");
                    } catch (RowNotFoundException ex) {
                        iterEch.getViewObject().applyViewCriteria(null);
                        iterEch.executeQuery();
                        //iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                    }
                }
            }
        }
        return null;

    }

    public void resetFieldsRollback() {
        //        System.out.println("Receive Tab Disclosed: " + this.sdiReceive.isDisclosed());
        //        System.out.println("Return Tab Disclosed: " + this.sdiReturn.isDisclosed());
        if (this.sdiReceive.isDisclosed()) {
            this.trlQty.setVisible(true);
            //            this.ttrlQty.setVisible(false);
            this.trlBin.setReadOnly(true);
            this.trlLot.setReadOnly(true);
        } else if (this.sdiReturn.isDisclosed()) {
            //            this.trlQtyRetn.setVisible(true);
            //            this.ttrlQtyRetn.setVisible(false);
            //            this.trlBin.setReadOnly(true);
            //            this.trlLot.setReadOnly(true);
        }
    }

    public void deptPpLaunch(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

        if (isDirty()) {
            pendingChangePop.show(hints);

        } else {
            deptPp.show(hints);
        }
    }

    public void addOLineAction(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            pendingChangePop.show(hints);

        } else {
            OrderLineCrIns_action();
            //onLastRowCreate("POrderlinesViewIterator");
            //call popup
            //poLinePop.show(hints);
            lineTypeRadioPop.show(hints);
        }
    }

    public String delOrl_action() {
        //update Order Price and Discounts
        updOrderPriceDiscount();

        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Delete1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String CrInsOLineAction() {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            pendingChangePop.show(hints);
        } else {

            String cLinePartType = "PS";
            String cLineServiceType = "ST";

            String sLineType = (String)ADFUtil.evaluateEL("#{bindings.ItsOrltype.inputValue}");
            //System.out.println("CrInsOLineAction()- sLineType: " + sLineType);

            OrderLineCrIns_action();
            //onLastRowCreate("POrderlinesViewIterator");
            ADFUtil.setEL("#{bindings.ItsOrltype.inputValue}", sLineType);

            if (sLineType.equals("PART")) {
                ADFUtil.setEL("#{bindings.OrlType.inputValue}", cLinePartType);
                ADFUtil.setEL("#{bindings.OrlRtype.inputValue}", cLinePartType);
            } else if (sLineType.equals("SERVICE")) {
                ADFUtil.setEL("#{bindings.OrlType.inputValue}", cLineServiceType);
                ADFUtil.setEL("#{bindings.OrlRtype.inputValue}", cLineServiceType);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.poLineDiag);
        }
        return null;
    }

    public String OrderLineCrIns_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String onLastRowCreate(String sIter) {
        //https://blogs.oracle.com/jdevotnharvest/entry/how_to_add_new_adf
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        //access the name of the iterator the table is bound to. Its "allDepartmentsIterator"
        //in this sample
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get(sIter);
        //access the underlying RowSetIterator
        RowSetIterator rsi = dciter.getRowSetIterator();
        //get handle to the last row
        Row lastRow = rsi.last();
        //obtain the index of the last row
        int lastRowIndex = rsi.getRangeIndexOf(lastRow);
        //create a new row
        Row newRow = rsi.createRow();
        //initialize the row
        newRow.setNewRowState(Row.STATUS_INITIALIZED);
        //add row to last index + 1 so it becomes last in the range set
        rsi.insertRowAtRangeIndex(lastRowIndex + 1, newRow);
        //make row the current row so it is displayed correctly
        rsi.setCurrentRow(newRow);
        return null;
    }

    public void editOLineAction(ActionEvent actionEvent) {
        if (isDirty()) {
            System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            poLinePop.show(hints);
        }
    }

    public void closePOLinePop(ActionEvent actionEvent) {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
            return;
        }

        DCIteratorBinding iterLine = (DCIteratorBinding)getDCBindingContainer().get("POrderlinesViewIterator");
        iterLine.getViewObject().executeQuery();

        RichPopup rp = (RichPopup)poLineDiag.getParent();
        rp.hide();
    }

    public void closeDeptPp(ActionEvent actionEvent) {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
            return;
        }

        RichPopup rp = (RichPopup)deptPpDiag.getParent();
        rp.hide();
    }

    public void handlePOrderlineDoubleClick(ClientEvent clientEvent) {
        if (!this.editBtn.isDisabled()) {
            RichPopup popup = this.getPoLinePop();
            //no hints means that popup is launched in the
            //center of the page
            RichPopup.PopupHints ph = new RichPopup.PopupHints();
            popup.show(ph);
        }
    }
    /*
    public void makeASelection(PhaseEvent phaseEvent) {
      String parTax="";
      RichSelectOneChoice soc;
        System.out.println("Inside makeASelection: ");
        soc = (RichSelectOneChoice)findComponentInRoot("soc15");

       System.out.println("Inside makeASelection 2");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding) bindings.get("POPartsIterator");
        Row r = dciter.getCurrentRow();
        if(r!=null){
            parTax = (String)r.getAttribute("ParTax");
            System.out.println("Inside makeASelection parTax: "+parTax);
        }
        DCIteratorBinding dc = (DCIteratorBinding) bindings.get("POExchargesViewIterator");
        ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
            ViewCriteria vc = vo.getViewCriteria("POTranslinesViewClearFullRecv");
            //System.out.println("ViewCriteria:" + vc.getName());
            vo.applyViewCriteria(vc);

            //vc.resetCriteria();
            vo.executeQuery();
    }


    public static UIComponent findComponentInRoot(String id) {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        return component;
    }
    */

    public static UIComponent findComponent(UIComponent base, String id) {
        if (id.equals(base.getId()))
            return base;

        UIComponent children = null;
        UIComponent result = null;
        Iterator childrens = base.getFacetsAndChildren();
        while (childrens.hasNext() && (result == null)) {
            children = (UIComponent)childrens.next();
            if (id.equals(children.getId())) {
                result = children;
                break;
            }
            result = findComponent(children, id);
            if (result != null) {
                break;
            }
        }
        return result;
    }

    public void xchargesListener(ActionEvent actionEvent) {
        String parTax = "";
        String type = "";
        if (isDirty()) {
            //System.out.println("Dirty");
            //call popup
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            pendingChangePop.show(hints);

        } else {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POPartsIterator");
            Row r = dciter.getCurrentRow();
            if (r != null) {
                parTax = (String)r.getAttribute("ParTax");
                // System.out.println("Inside xchargesListener parTax: " + parTax);
            }
            // if(parTax != ""){
            /*
            DCIteratorBinding dc = (DCIteratorBinding)bindings.get("POExchargesViewIterator");
            ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
            Row rex = vo.getCurrentRow();
            if (rex != null) {
                type = (String)rex.getAttribute("tTypeDesc");
                System.out.println("Inside xchargesListener type: " + type);
*/
            /*
                    if(type.equals("Tax code")){
                        rex.setAttribute("EchDiscperc", newVal);
                    }
*/
            //   }
            //   }

            xchargesPop.show(hints);
        }

    }

    public void pendingChangeDiagListener(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.yes) {
            //  System.out.println("Inside pendingChangeDiagListener DialogEvent.Outcome.yes");
            //apply_action();
            applyOLine_action();
        }
    }


    public void xChargesDiagListener(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.ok) {

            Double dLineTtl = 0.0;
            Double dOrlExt = 0.0;
            String dparTax = "";
            Double dtax = 0.0;
            String dpart = "";
            String dType = "";
            //String sOrlExt;
            Double dFixed = 0.0;
            Double dPercent = 0.0;
            String dEchType = "";
            String sInc;
            Integer echcode;
            String echpart = "";
            String sOrdType = (String)ADFUtil.evaluateEL("#{bindings.ItsOrltype.inputValue}");

            BindingContainer bindings = getBindings();
            DCIteratorBinding dc = (DCIteratorBinding)bindings.get("POExchargesViewIterator");
            ViewObject vo = dc.getViewObject();
            RowSetIterator rit = vo.createRowSetIterator(null);
            rit.reset();
            while (rit.hasNext()) {
                Row rw = rit.next();
                sInc = (String)rw.getAttribute("EchParprice");
                echcode = (Integer)rw.getAttribute("EchCode");
                echpart = (String)rw.getAttribute("EchInvordCode");

                //   System.out.println((String)rw.getAttribute("EchType") + "Inside xChargesDiagListener echpart: "+echpart+" echcode: "+echcode+" sInc: " + sInc );
                if ("+".equals(sInc)) {
                    //  System.out.println("Inside xChargesDiagListener dLineTtl: " + dLineTtl);
                    if ("PART".equals(sOrdType)) {
                        dOrlExt = (Double)ADFUtil.evaluateEL("#{bindings.tOrlPriceExtended.inputValue}");
                        dparTax = (String)ADFUtil.evaluateEL("#{bindings.tTypeDesc.inputValue}");
                        dpart = (String)ADFUtil.evaluateEL("#{bindings.OrlPart1.inputValue}");
                        dtax = (Double)ADFUtil.evaluateEL("#{bindings.ParTax.inputValue}");
                        dType = (String)ADFUtil.evaluateEL("#{row.tTypeDesc}");
                        dEchType = (String)ADFUtil.evaluateEL("#{row.EchType}");
                        //System.out.println("Inside xChargesDiagListener dparTax: "+dparTax+" dtax: "+dtax+" dpart: "+dpart+" dType: "+dType+" dEchType: "+dEchType);
                    } else if ("SERVICE".equals(sOrdType)) {
                        dOrlExt = (Double)ADFUtil.evaluateEL("#{bindings.tItsTaskPriceExtended.inputValue}");
                    }
                    //Double.parseDouble(sOrlExt);
                    dFixed = (Double)rw.getAttribute("EchDiscount");
                    dPercent = (Double)rw.getAttribute("EchDiscperc");
                    //  System.out.println("Inside xChargesDiagListener dPercent 1: "+dPercent);
                    //(EchDiscount != null ? EchDiscount : (EchDiscperc != null ? POrderlinesView.tOrlPriceExtended * (EchDiscperc/100): null))
                    if ((dparTax != "") && (dparTax.equals("Tax code"))) {
                        dPercent = dtax;
                        //  ADFUtil.setEL("#{bindings.EchParprice.inputValue}", "+");
                    }
                    //    System.out.println("Inside xChargesDiagListener 2 dPercent: "+dPercent);
                    //dLineTtl = dLineTtl + (Double)rw.getAttribute("tTotalLine");
                    dLineTtl =
                            dLineTtl + (dFixed != null ? dFixed : (dPercent != null ? dOrlExt * (dPercent / 100) : 0.0));
                    //    System.out.println("Inside xChargesDiagListener dLineTtl: "+dLineTtl+" dPercent: "+dPercent);
                }

            }

            rit.closeRowSetIterator();

            ADFUtil.setEL("#{bindings.OrlTotextra.inputValue}", dLineTtl);
            /*
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.echDiscperc);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.xchargesTotal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.ttotalOrderLine);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.extChgChk);
*/

        }
    }

    public void contextInfoListener(ContextInfoEvent contextInfoEvent) {
        //System.out.println("sType:" + sType);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        xchargesPop.show(hints);
    }


    /*
    public void orderTypeListener(ValueChangeEvent valueChangeEvent) {
        //get current instance of change
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String orderType = (String)ADFUtil.evaluateEL("#{bindings.ItsOrdtype.inputValue}");
        System.out.println(orderType);

        if (orderType.equals("PART")) {
            this.partOrder.setRendered(true);
            this.serviceOrder.setRendered(false);
        } else if (orderType.equals("SERVICE")) {
            this.partOrder.setRendered(false);
            this.serviceOrder.setRendered(true);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.orderTypePanel);
    }
 */

    public void lineTypeRadioDiagListener(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        String cLinePartType = "PS";
        String cLineServiceType = "ST";
        if (result == DialogEvent.Outcome.ok) {
            String sLineType = (String)ADFUtil.evaluateEL("#{bindings.ItsOrltype.inputValue}");
            //System.out.println("DialogEvent.Outcome.ok: " + sLineType);
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            if (sLineType.equals("PART")) {
                poLinePop.show(hints);
                ADFUtil.setEL("#{bindings.OrlType.inputValue}", cLinePartType);
                ADFUtil.setEL("#{bindings.OrlRtype.inputValue}", cLinePartType);
            } else if (sLineType.equals("SERVICE")) {
                ADFUtil.setEL("#{bindings.OrlType.inputValue}", cLineServiceType);
                ADFUtil.setEL("#{bindings.OrlRtype.inputValue}", cLineServiceType);
                poLinePop.show(hints);
            }
        }
    }

    public void linePartChangeListener(ValueChangeEvent valueChangeEvent) {
        //get current instance of change
        //System.out.println("LOV ID: " + valueChangeEvent.getComponent().getId());
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        String sPart = (String)ADFUtil.evaluateEL("#{bindings.OrlPart.inputValue}");

        if (!isPartInCat() && StringUtils.isNotBlank(sPart)) {
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            addPartToSuppPop.show(hints);
        }

    }

    public void addPartToSuppDiagLsnr(DialogEvent dialogEvent) {
        DialogEvent.Outcome result = dialogEvent.getOutcome();
        if (result == DialogEvent.Outcome.yes) {
            addPartToSupplier();
        }
    }

    public void resultsPOSearchSelectionListener(SelectionEvent selectionEvent) {
        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
        if (isDirty()) {
            if (StringUtils.isBlank(sOrdCode)) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            }
        } else {
            ADFUtil.invokeMethodExpressionSelectionEvent("#{bindings.POrdersView.collectionModel.makeCurrent}",
                                                         selectionEvent);
            //refreshTransline();
        }
    }

    public void transactionSelLnsr(SelectionEvent selectionEvent) {
        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
        if (isDirty()) {
            if (StringUtils.isBlank(sOrdCode)) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            }
        } //else {
        ADFUtil.invokeMethodExpressionSelectionEvent("#{bindings.POTransactionsRecvView.collectionModel.makeCurrent}",
                                                     selectionEvent);
        //refreshTransline();
        //  }
    }

    public void transactionSelLnsrRetn(SelectionEvent selectionEvent) {
        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
        if (isDirty()) {
            if (StringUtils.isBlank(sOrdCode)) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            }
        } else {
            ADFUtil.invokeMethodExpressionSelectionEvent("#{bindings.POTransactionsRetView.collectionModel.makeCurrent}",
                                                         selectionEvent);
            //refreshTransline();
        }
    }

    public void processQuery(QueryEvent queryEvent) {
        QueryDescriptor qdes = queryEvent.getDescriptor();
        //System.out.println("NAME " + qdes.getName());
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
            ADFUtil.invokeMethodExpressionQueryEvent("#{bindings.POMARSQuery.processQuery}", queryEvent);

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.resultsSearchTable);
            //refreshTransline();
        }
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

    public Boolean addPartToSupplier() {
        //System.out.println("Inside addPartToSupplier()");
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        OperationBinding operationBinding = getOperationBinding("addPartToSupplier");

        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public Boolean isPartInCat() {
        //System.out.println("Inside isPartInCat()");
        //String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        OperationBinding operationBinding = getOperationBinding("isPartInCat");

        //invoke method
        Boolean result = (Boolean)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            //check errors
            List errors = operationBinding.getErrors();
        }
        return result;
    }

    public String fetch_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("fetchOrderlines");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        //AMclearFullyRecvd();
        //        this.trlQty.setVisible(false);
        //        this.ttrlQty.setVisible(true);
        //        this.trlBin.setReadOnly(false);
        //        this.trlLot.setReadOnly(false);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(this.recvTransTable);
        return null;
    }

    public String fetchRETN_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("fetchOrderlines1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        //AMclearFullyRetn();
        //        this.trlQtyRetn.setVisible(false);
        //        this.ttrlQtyRetn.setVisible(true);
        return null;
    }

    public String AMclearFullyRecvd() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("clearFullyRecd");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String AMclearFullyRetn() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("clearFullyRetn");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public void clearFullyRecd() {
        //System.out.println("clearFullyRecd()");
        DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.POTranslinesRecvViewIterator}");
        ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
        //System.out.println(vo.getViewCriteriaManager().getViewCriteria().getName());
        //if ( vo.getViewCriteriaManager().getViewCriteria().getName() != null) {
        ViewCriteria vc = vo.getViewCriteria("POTranslinesViewClearFullRecv");
        //System.out.println("ViewCriteria:" + vc.getName());
        vo.applyViewCriteria(vc);

        //vc.resetCriteria();
        vo.executeQuery();
        // }
        //vo.applyViewCriteria(vc);
    }

    public String printRecvList() {
        BindingContainer bindings = getBindings();
        DCIteratorBinding dc = (DCIteratorBinding)bindings.get("POTranslinesRecvViewIterator");
        ViewObject vo = dc.getViewObject();
        RowSetIterator rit = vo.createRowSetIterator(null);
        rit.reset();
        while (rit.hasNext()) {
            // System.out.println("2");
            Row rw = rit.next();
            System.out.println("Part: " + rw.getAttribute("TrlPart"));

        }

        rit.closeRowSetIterator();
        return "";
    }

    public void refreshTransline() {
        System.out.println("Refreshing transline");
        DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.POTranslinesRecvViewIterator}");
        ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
        //System.out.println(vo.getViewCriteriaManager().getViewCriteria().getName());
        //if ( vo.getViewCriteriaManager().getViewCriteria().getName() != null) {
        ViewCriteria vc = vo.getViewCriteria("POTranslinesViewClearFullRecv");
        //System.out.println("ViewCriteria:" + vc.getName());
        vo.removeViewCriteria("POTranslinesViewClearFullRecv");

        //vc.resetCriteria();
        vo.executeQuery();
        // }
        //vo.applyViewCriteria(vc);

    }

    public void resetTransline() {
        //System.out.println("Refreshing transline");
        DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.POTranslinesRecvViewIterator}");
        ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
        vo.executeQuery();

        //        this.trlQty.setVisible(true);
        //        this.ttrlQty.setVisible(false);
        //        this.trlBin.setReadOnly(true);
        //        this.trlLot.setReadOnly(true);
    }

    public void resetTranslinesRETN() {
        //System.out.println("Refreshing transline");
        DCIteratorBinding dc = (DCIteratorBinding)ADFUtil.evaluateEL("#{bindings.POTranslinesRetViewIterator}");
        ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
        vo.executeQuery();

        //        this.trlQtyRetn.setVisible(true);
        //        this.ttrlQtyRetn.setVisible(false);
        //        this.trlBin.setReadOnly(true);
        //        this.trlLot.setReadOnly(true);
    }

    public void removeFullyRecdPartsVC() {
        //System.out.println("syncWOMasterDetail");
        BindingContainer bindings = getBindings();
        DCIteratorBinding dc = (DCIteratorBinding)bindings.get("POTranslinesRecvViewIterator");
        ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();

        //System.out.println("Line: " + vo.getCurrentRow().getAttribute("TrlOrdline"));

        vo.setApplyViewCriteriaName("POTranslinesViewClearFullRecv");
        vo.executeQuery();

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.recvTransTable);
    }

    public void tTrlQtyChangeLsnr(ValueChangeEvent valueChangeEvent) {
        //        Double dQty = (Double)valueChangeEvent.getNewValue();
        //        this.itTrlQty.setValue(dQty);
        //        AdfFacesContext.getCurrentInstance().addPartialTarget(this.itTrlQty);
    }

    public void tabDisclosed(DisclosureEvent disclosureEvent) {
        String tabDisclosed = "";
        if (disclosureEvent.isExpanded() == true) {
             tabDisclosed = (String)disclosureEvent.getComponent().getAttributes().get("disclosedTab");
           // setwhichTab((String)disclosureEvent.getComponent().getAttributes().get("disclosedTab"));
          //  System.out.println("tabDisclosed: "+tabDisclosed);
            setAvailbleMenu((String)disclosureEvent.getComponent().getAttributes().get("disclosedTab"));
        }

    }


    public void tabLineDisclosed(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded() == true) {
            //ADFUtil.setEL("#{bindings.ItsOrltype.inputValue}",(String)disclosureEvent.getComponent().getAttributes().get("disclosedTabLine"));
            String sTab = (String)disclosureEvent.getComponent().getAttributes().get("disclosedTabLine");
            //System.out.println("sTab: " + sTab);
            if (sTab != null && getRdoOrlType() != null) {
                this.rdoOrlType.setValue(sTab);
            }
            //AdfFacesContext.getCurrentInstance().addPartialTarget(this.rdoOrlType);
        }
    }

    public void setAvailbleMenu(String menu) {
        String sIsAdf = (String)ADFUtil.evaluateEL("#{bindings.ItsIsadf.inputValue}");
        String sOrdSts = (String)ADFUtil.evaluateEL("#{bindings.OrdStatus1.inputValue}");
        //System.out.println("sIsAdf: " +sIsAdf);
        Boolean bIsMP5PO = "N".equals(sIsAdf) ? true : false;
        //System.out.println(sIsAdf);
        //System.out.println("bIsMP5PO: " + bIsMP5PO);
        if ("RECORD".equals(menu)) {
            this.menuNewPO.setDisabled(false);
            this.menuNewRecv.setDisabled(true);
            this.menuNewRetn.setDisabled(true);
        } else if ("RECEIVE".equals(menu)) {
            this.menuNewPO.setDisabled(true);
            this.menuNewRecv.setDisabled(bIsMP5PO);
            this.menuNewRetn.setDisabled(true);
        } else if ("RETURN".equals(menu)) {
            this.menuNewPO.setDisabled(true);
            this.menuNewRecv.setDisabled(true);
            this.menuNewRetn.setDisabled(bIsMP5PO);
        }
        //        //based on Order Status
        //        if ("RC".equals(sOrdSts)){
        //            this.menuNewRecv.setDisabled(true);
        //            this.menuNewRetn.setDisabled(true);
        //        }
    }


    public void setPoLinePop(RichPopup poLinePop) {
        this.poLinePop = poLinePop;
    }

    public RichPopup getPoLinePop() {
        return poLinePop;
    }


    public void setPoLineDiag(RichDialog poLineDiag) {
        this.poLineDiag = poLineDiag;
    }

    public RichDialog getPoLineDiag() {
        return poLineDiag;
    }

    public void setPendingChangePop(RichPopup pendingChangePop) {
        this.pendingChangePop = pendingChangePop;
    }

    public RichPopup getPendingChangePop() {
        return pendingChangePop;
    }


    public void setXchargesPop(RichPopup xchargesPop) {
        this.xchargesPop = xchargesPop;
    }

    public RichPopup getXchargesPop() {
        return xchargesPop;
    }

    public void setXchargesDiag(RichDialog xchargesDiag) {
        this.xchargesDiag = xchargesDiag;
    }

    public RichDialog getXchargesDiag() {
        return xchargesDiag;
    }

    public void setXchargesTotal(RichOutputText xchargesTotal) {
        this.xchargesTotal = xchargesTotal;
    }

    public RichOutputText getXchargesTotal() {
        return xchargesTotal;
    }

    public void setTtotalOrderLine(RichOutputText ttotalOrderLine) {
        this.ttotalOrderLine = ttotalOrderLine;
    }

    public RichOutputText getTtotalOrderLine() {
        return ttotalOrderLine;
    }


    public void setEditBtn(RichButton editBtn) {
        this.editBtn = editBtn;
    }

    public RichButton getEditBtn() {
        return editBtn;
    }

    public void setPartOrder(RichTable partOrder) {
        this.partOrder = partOrder;
    }

    public RichTable getPartOrder() {
        return partOrder;
    }

    public void setServiceOrder(RichTable serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public RichTable getserviceOrder() {
        return serviceOrder;
    }

    public void setOrderTypePanel(RichPanelCollection orderTypePanel) {
        this.orderTypePanel = orderTypePanel;
    }

    public RichPanelCollection getOrderTypePanel() {
        return orderTypePanel;
    }


    public void setLineTypeRadioPop(RichPopup lineTypeRadioPop) {
        this.lineTypeRadioPop = lineTypeRadioPop;
    }

    public RichPopup getLineTypeRadioPop() {
        return lineTypeRadioPop;
    }


    public void setResultsSearchTable(RichTable resultsSearchTable) {
        this.resultsSearchTable = resultsSearchTable;
    }

    public RichTable getResultsSearchTable() {
        return resultsSearchTable;
    }


    public void setAddPartToSuppPop(RichPopup addPartToSuppPop) {
        this.addPartToSuppPop = addPartToSuppPop;
    }

    public RichPopup getAddPartToSuppPop() {
        return addPartToSuppPop;
    }

    public void setRecvTransTable(RichTable recvTransTable) {
        this.recvTransTable = recvTransTable;
    }

    public RichTable getRecvTransTable() {
        return recvTransTable;
    }

    public void setTrlQty(RichColumn trlQty) {
        this.trlQty = trlQty;
    }

    public RichColumn getTrlQty() {
        return trlQty;
    }

    public void setTrlLot(RichInputListOfValues trlLot) {
        this.trlLot = trlLot;
    }

    public RichInputListOfValues getTrlLot() {
        return trlLot;
    }

    public void setTrlBin(RichInputListOfValues trlBin) {
        this.trlBin = trlBin;
    }

    public RichInputListOfValues getTrlBin() {
        return trlBin;
    }

    public void setItTrlQty(RichInputText itTrlQty) {
        this.itTrlQty = itTrlQty;
    }

    public RichInputText getItTrlQty() {
        return itTrlQty;
    }


    public void setSdiReceive(RichShowDetailItem sdiReceive) {
        this.sdiReceive = sdiReceive;
    }

    public RichShowDetailItem getSdiReceive() {
        return sdiReceive;
    }

    public void setSdiReturn(RichShowDetailItem sdiReturn) {
        this.sdiReturn = sdiReturn;
    }

    public RichShowDetailItem getSdiReturn() {
        return sdiReturn;
    }

    public void setTrlQtyRetn(RichColumn trlQtyRetn) {
        this.trlQtyRetn = trlQtyRetn;
    }

    public RichColumn getTrlQtyRetn() {
        return trlQtyRetn;
    }

    public void setItTrlQtyRetn(RichInputText itTrlQtyRetn) {
        this.itTrlQtyRetn = itTrlQtyRetn;
    }

    public RichInputText getItTrlQtyRetn() {
        return itTrlQtyRetn;
    }

    public void setTrlBinRetn(RichOutputText trlBinRetn) {
        this.trlBinRetn = trlBinRetn;
    }

    public RichOutputText getTrlBinRetn() {
        return trlBinRetn;
    }

    public void setSdiRecord(RichShowDetailItem sdiRecord) {
        this.sdiRecord = sdiRecord;
    }

    public RichShowDetailItem getSdiRecord() {
        return sdiRecord;
    }

    public void setMenuNewPO(RichCommandMenuItem menuNewPO) {
        this.menuNewPO = menuNewPO;
    }

    public RichCommandMenuItem getMenuNewPO() {
        return menuNewPO;
    }

    public void setMenuNewRecv(RichCommandMenuItem menuNewRecv) {
        this.menuNewRecv = menuNewRecv;
    }

    public RichCommandMenuItem getMenuNewRecv() {
        return menuNewRecv;
    }

    public void setMenuNewRetn(RichCommandMenuItem menuNewRetn) {
        this.menuNewRetn = menuNewRetn;
    }

    public RichCommandMenuItem getMenuNewRetn() {
        return menuNewRetn;
    }

    public void setRdoOrlType(RichSelectOneRadio rdoOrlType) {
        this.rdoOrlType = rdoOrlType;
    }

    public RichSelectOneRadio getRdoOrlType() {
        return rdoOrlType;
    }

    public void setLovPart(RichInputListOfValues lovPart) {
        this.lovPart = lovPart;
    }

    public RichInputListOfValues getLovPart() {
        return lovPart;
    }

    public void setItUnitPrice(RichInputText itUnitPrice) {
        this.itUnitPrice = itUnitPrice;
    }

    public RichInputText getItUnitPrice() {
        return itUnitPrice;
    }

    public String editRecvLine() {
        /*
                if (isDirty()) {
                   // userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
                              RichPopup.PopupHints hints = new RichPopup.PopupHints();
                             poRecvPendingChangePop.show(hints);
                    return null;

                } else {*/
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        recvLinePop.show(hints);
        //   }
        return null;
    }

    public static UIComponent findComponentInRoot(String id) {
        UIComponent component = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            UIComponent root = facesContext.getViewRoot();
            component = findComponent(root, id);
        }
        return component;
    }

    private void resetInputText(String id) {
        RichInputText input = (RichInputText)findComponentInRoot(id);
        input.setSubmittedValue(null);
        input.resetValue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(input);
    }

    public String closeRecvPartLinePop() {
        if (isDirty()) {
            userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
            return null;
        }
        RichPopup rp = this.getRecvLinePop();
        resetInputText("trlQty");
        rp.hide();
        
        BindingContainer bindings = getBindings();
        /*
        refreshIterators();
        */
        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POTransactionsRecvViewIterator");
        iter.getViewObject().executeQuery();
        DCIteratorBinding iter1 = (DCIteratorBinding)getDCBindingContainer().get("POTranslinesRecvViewIterator");
        iter1.getViewObject().executeQuery();        
       
        AdfFacesContext.getCurrentInstance().addPartialTarget(poTransTbl);
        AdfFacesContext.getCurrentInstance().addPartialTarget(recvTransTable);
        return null;
    }

    public void trlQtyLstnr(ValueChangeEvent vce) {
        Double oldVal;
        Double newVal;

        if (vce.getOldValue() != null) {
            oldVal = (Double)vce.getOldValue();
            System.out.println("Inside trlQtyLstnr oldVal: " + oldVal);
        }
        if (vce.getNewValue() != null) {
            newVal = (Double)vce.getNewValue();
            System.out.println("Inside trlQtyLstnr newVal: " + newVal);
            //ADFUtil.setEL("#{bindings.TrlQty.inputValue}",newVal);

            RichInputText input = (RichInputText)findComponentInRoot("trlQty");
            input.setSubmittedValue(newVal);
            AdfFacesContext.getCurrentInstance().addPartialTarget(input);
            AdfFacesContext.getCurrentInstance().addPartialTarget(poTransTbl);
            AdfFacesContext.getCurrentInstance().addPartialTarget(recvTransTable);
        }

    }

    public void setRecvLinePop(RichPopup recvLinePop) {
        this.recvLinePop = recvLinePop;
    }

    public RichPopup getRecvLinePop() {
        return recvLinePop;
    }


    public String oLineDtlFirst_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("First1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        }
        return null;
    }

    public String oLineDtlPrev_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Previous1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        }
        return null;
    }

    public String oLineDtlNext_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Next1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        }
        return null;
    }

    public String oLineDtlLast_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Last1");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
            //            }
        }
        return null;
    }

    public String recvFirst_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("First2");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        }
        return null;
    }

    public String recvPrev_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Previous2");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        }
        return null;
    }

    public String recvNext_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Next2");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        }
        return null;
    }

    public String recvLast_action() {
        //        if (isDirty()) {
        //            //System.out.println("Dirty");
        //            //call popup
        //            RichPopup.PopupHints hints = new RichPopup.PopupHints();
        //            pendingChangePop.show(hints);
        //
        //        } else {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Last2");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //        }
        return null;
    }


    public void setExtChgChk(RichSelectBooleanCheckbox extChgChk) {
        this.extChgChk = extChgChk;
    }

    public RichSelectBooleanCheckbox getExtChgChk() {
        return extChgChk;
    }


    public void setTordPrice(RichOutputText tordPrice) {
        this.tordPrice = tordPrice;
    }

    public RichOutputText getTordPrice() {
        return tordPrice;
    }


    public void setPercentage(RichSelectOneChoice percentage) {
        this.percentage = percentage;
    }

    public RichSelectOneChoice getPercentage() {
        return percentage;
    }
    /*
    public void EchDiscpercLstnr(ValueChangeEvent vce) {
        Double newVal = 0.0;
        Double oldVal = 0.0;
        String parTax = "";
        String type = "";

        newVal = (Double)vce.getNewValue();
        oldVal = (Double)vce.getOldValue();
        // System.out.println("Inside EchDiscpercLstnr newVal: "+newVal+" oldVal: "+oldVal);

        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POPartsIterator");
        Row r = dciter.getCurrentRow();
        if (r != null) {
            parTax = (String)r.getAttribute("ParTax");
            //   System.out.println("Inside EchDiscpercLstnr parTax: "+parTax);
        }
        if (parTax != "") {
            DCIteratorBinding dc = (DCIteratorBinding)bindings.get("POExchargesViewIterator");
            ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
            Row rex = vo.getCurrentRow();
            if (rex != null) {
                type = (String)rex.getAttribute("tTypeDesc");
                //   System.out.println("Inside EchDiscpercLstnr type: "+type);
                if (type.equals("Tax code")) {
                    rex.setAttribute("EchDiscperc", newVal);
                }
            }

        }

    }
*/
    /*
    public void EchDiscpercLstnr(ValueChangeEvent vce) {
        Double newVal = 0.0;
        Double oldVal = 0.0;
        String parTax = "";
        int orOrdline = "";
        String psf = "";

        newVal = (Double)vce.getNewValue();
        oldVal = (Double)vce.getOldValue();
        // System.out.println("Inside EchDiscpercLstnr newVal: "+newVal+" oldVal: "+oldVal);



        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dcorliter = (DCIteratorBinding)bindings.get("POrderlinesViewIterator");
        Row orlr = dcorliter.getCurrentRow();
        if (orlr != null) {
            orOrdline = (Integer)orlr.getAttribute("orOrdline");
               System.out.println("Inside EchDiscpercLstnr orOrdline: "+orOrdline);
        }
        psf = "pfsEchDisperVal#"+orOrdline;
        System.out.println("Inside EchDiscpercLstnr newVal: "+newVal+" oldVal: "+oldVal);
        if (ADFContext.getCurrent().getPageFlowScope().containsKey(psf)){
            AdfFacesContext.getCurrentInstance().getPageFlowScope().put(psf, newVal);
    }
    }
    */

    /**Method to set value in Expression (EL)
     * @param el
     * @param val
     */
    public void setvalueToExpression(String el, Object val) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);
        exp.setValue(elContext, val);
    }

    /**Method to get value from Expression (EL)
     * @param data
     * @return
     */
    public String getValueFrmExpression(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, data, Object.class);
        String Message = null;
        Object obj = valueExp.getValue(elContext);
        if (obj != null) {
            Message = obj.toString();
        }
        return Message;
    }

    public void EchTypeLstnr(ValueChangeEvent vce) {
        Integer newVal;
        Integer oldVal;
        int sel = 0;
        String parTax = "";
        String type = "";
        Double taxval = 0.0;
        Double origTaxval = 0.0;

        if (vce.getNewValue() != null) {
        newVal = (Integer)vce.getNewValue();
        sel = ((Integer)vce.getNewValue()).intValue();
        }
        if (vce.getOldValue() != null) {
            oldVal = (Integer)vce.getOldValue();
            // System.out.println("Inside EchTypeLstnr newVal: "+newVal+" oldVal: "+oldVal+" sel: "+sel);
        }
        //System.out.println("New Value is-" + vce.getNewValue());
        if (vce.getNewValue() != null) {
            this.setvalueToExpression("#{row.bindings.EchType.inputValue}", vce.getNewValue()); //Updating Model Values

            String data =
                "#{row.bindings.EchType.selectedValue.attributeValues[" + ((Integer)vce.getNewValue()).intValue() +
                "]}";
            type = getValueFrmExpression(data);
            //  System.out.println("*******Display Value in List ****   data" + getValueFrmExpression(data)+"type: "+type);
        }

        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("POPartsIterator");
        Row r = dciter.getCurrentRow();
        if (r != null) {
            parTax = (String)r.getAttribute("ParTax");
            // System.out.println("Inside EchTypeLstnr parTax: "+parTax);
        }
        if (parTax != "") {
            DCIteratorBinding dctax = (DCIteratorBinding)bindings.get("TaxRateValuesViewIterator");
            ViewObjectImpl votax = (ViewObjectImpl)dctax.getViewObject();
            Row rdctax = votax.getCurrentRow();
            if (rdctax != null) {
                taxval = (Double)rdctax.getAttribute("TxvPercent");
                // System.out.println("Inside EchTypeLstnr taxval: "+taxval);
            }
            DCIteratorBinding dc = (DCIteratorBinding)bindings.get("POExchargesViewIterator");
            ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
            Row rex = vo.getCurrentRow();
            if (rex != null) {
                if (rex.getAttributeIndexOf("EchDiscperc") != -1)
                    origTaxval = (Double)rex.getAttribute("EchDiscperc");
                //System.out.println("Inside EchTypeLstnr type: "+type+" origTaxval: "+origTaxval);
                //   rex.setAttribute("EchParprice", '+');
                if (type.equals("Tax Charge")) {
                    //  System.out.println("Inside EchTypeLstnr inside type");
                    if (origTaxval == null) {
                        //    System.out.println("Inside EchTypeLstnr inside type taxval: "+taxval);
                        rex.setAttribute("EchDiscperc", taxval);


                        // AdfFacesContext.getCurrentInstance().addPartialTarget(this.perc);

                        String sOrdCode = (String)ADFUtil.evaluateEL("#{bindings.OrdCode.inputValue}");
                        String sAddCode = (String)ADFUtil.evaluateEL("#{bindings.AddCode.inputValue}");
                        Integer sOLineCode = (Integer)ADFUtil.evaluateEL("#{bindings.OrlOrdline.inputValue}");
                        String sEchInvOrdCd = (String)ADFUtil.evaluateEL("#{bindings.EchInvordCode.inputValue}");


                        // get current rows
                        DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("POrdersViewIterator");
                        Row rowKey = iter.getCurrentRow();
                        // iter.getViewObject().executeQuery();

                        DCIteratorBinding iterCmt =
                            (DCIteratorBinding)getDCBindingContainer().get("POrdersCommentIterator");
                        Row rowCmt = iterCmt.getCurrentRow();
                        // iterCmt.getViewObject().executeQuery();

                        DCIteratorBinding iterLine =
                            (DCIteratorBinding)getDCBindingContainer().get("POrderlinesViewIterator");
                        Row rowLine = iterLine.getCurrentRow();
                        // iterLine.getViewObject().executeQuery();

                        DCIteratorBinding iterEch =
                            (DCIteratorBinding)getDCBindingContainer().get("POExchargesViewIterator");
                        Row rowEch = iterEch.getCurrentRow();
                        //iterEch.getViewObject().executeQuery();

                        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                        Object result = operationBinding.execute();
                        //System.out.println(result);
                        if (!operationBinding.getErrors().isEmpty()) {
                            return;
                        }

                        //Need this line to reset the POOrdersView Iterator to avaoid the Another user chnaged....error.
                        iter.executeQuery();


                        //set current rows
                        if (StringUtils.isNotBlank(sOrdCode)) {
                            if (rowKey != null) {
                                Key parentKey = rowKey.getKey();
                                try {
                                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                                    //System.out.println("Setting Orders");
                                } catch (RowNotFoundException ex) {
                                    iter.getViewObject().applyViewCriteria(null);
                                    iter.executeQuery();
                                    iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                                }
                            }
                        }

                        if (StringUtils.isNotBlank(sAddCode)) {
                            if (rowCmt != null) {
                                Key parentKeyCmt = rowCmt.getKey();
                                try {
                                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                                    //System.out.println("Setting Comments");
                                } catch (RowNotFoundException ex) {
                                    iterCmt.getViewObject().applyViewCriteria(null);
                                    iterCmt.executeQuery();
                                    iterCmt.setCurrentRowWithKey(parentKeyCmt.toStringFormat(true));
                                }
                            }
                        }


                        if (sOLineCode != null) {
                            //System.out.println("Setting Orderlines sOLineCode: "+sOLineCode);
                            if (rowLine != null) {
                                Key parentKeyLine = rowLine.getKey();
                                try {
                                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));

                                } catch (RowNotFoundException ex) {
                                    //   System.out.println("Setting Orderlines in the catch block");
                                    iterLine.getViewObject().applyViewCriteria(null);
                                    iterLine.executeQuery();
                                    iterLine.setCurrentRowWithKey(parentKeyLine.toStringFormat(true));
                                }
                            }
                        }


                        if (StringUtils.isNotBlank(sEchInvOrdCd)) {
                            if (rowEch != null) {
                                Key parentKeyEch = rowEch.getKey();
                                try {
                                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                                    //System.out.println("Setting Extra Charges");
                                } catch (RowNotFoundException ex) {
                                    iterEch.getViewObject().applyViewCriteria(null);
                                    iterEch.executeQuery();
                                    iterEch.setCurrentRowWithKey(parentKeyEch.toStringFormat(true));
                                }
                            }
                        }


                    }
                }
            }

        } //
    }

    public void setEchDiscperc(RichInputText echDiscperc) {
        this.echDiscperc = echDiscperc;
    }

    public RichInputText getEchDiscperc() {
        return echDiscperc;
    }


    public void setPerc(RichColumn perc) {
        this.perc = perc;
    }

    public RichColumn getPerc() {
        return perc;
    }
    /*
    public void echParPriceLstnr(ValueChangeEvent vce) {
        // Add event code here...
        Boolean oldVal;
        Boolean newVal;
        String include = "";
        Integer echCode;

        if (vce.getOldValue() != null) {
            oldVal = (Boolean)vce.getOldValue();
        }

        if (vce.getNewValue() != null) {
            newVal = (Boolean)vce.getNewValue();
            //  System.out.println("Inside echParPriceLstnr newVal: " + newVal);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dc = (DCIteratorBinding)bindings.get("POExchargesViewIterator");
            ViewObjectImpl vo = (ViewObjectImpl)dc.getViewObject();
            Row rex = vo.getCurrentRow();
            if (rex != null) {
                echCode = (Integer)rex.getAttribute("EchCode");

                if (rex.getAttributeIndexOf("EchParprice") != -1) {
                    include = (String)rex.getAttribute("EchParprice");
                    //       System.out.println("Inside echParPriceLstnr echCode: " + echCode + " include: " + include);
                    if (newVal == true) {
                        // System.out.println("Inside echParPriceLstnr newVal is true");
                        rex.setAttribute("EchParprice", "+");
                    }
                    if (newVal == false) {
                        // System.out.println("Inside echParPriceLstnr newVal is false");
                        rex.setAttribute("EchParprice", "-");
                    }

                    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
                    Object result = operationBinding.execute();
                    //System.out.println(result);
                    if (!operationBinding.getErrors().isEmpty()) {
                        return;
                    }
                    include = (String)rex.getAttribute("EchParprice");
                    //System.out.println("Inside echParPriceLstnr after commit echCode: " + echCode + " include: " + include);
                }
            }

        }
        // AdfFacesContext.getCurrentInstance().addPartialTarget(this.perc);
    }
*/

    public void setCommentssec(RichShowDetailHeader commentssec) {
        this.commentssec = commentssec;
    }

    public RichShowDetailHeader getCommentssec() {
        return commentssec;
    }

    public void setMainhdr(RichPanelHeader mainhdr) {
        this.mainhdr = mainhdr;
    }

    public RichPanelHeader getMainhdr() {
        return mainhdr;
    }

    public void setPoTransTbl(RichTable poTransTbl) {
        this.poTransTbl = poTransTbl;
    }

    public RichTable getPoTransTbl() {
        return poTransTbl;
    }

    public void setPoRecvPendingChangePop(RichPopup poRecvPendingChangePop) {
        this.poRecvPendingChangePop = poRecvPendingChangePop;
    }

    public RichPopup getPoRecvPendingChangePop() {
        return poRecvPendingChangePop;
    }

    public void setDeptPpDiag(RichDialog deptPpDiag) {
        this.deptPpDiag = deptPpDiag;
    }

    public RichDialog getDeptPpDiag() {
        return deptPpDiag;
    }

    public void setDeptPp(RichPopup deptPp) {
        this.deptPp = deptPp;
    }

    public RichPopup getDeptPp() {
        return deptPp;
    }

    public void setRetTransTable(RichTable retTransTable) {
        this.retTransTable = retTransTable;
    }

    public RichTable getRetTransTable() {
        return retTransTable;
    }

    public void setOrdSummaryForm(RichPanelFormLayout ordSummaryForm) {
        this.ordSummaryForm = ordSummaryForm;
    }

    public RichPanelFormLayout getOrdSummaryForm() {
        return ordSummaryForm;
    }

    public void setMainPanelSplitter(RichPanelSplitter mainPanelSplitter) {
        this.mainPanelSplitter = mainPanelSplitter;
    }

    public RichPanelSplitter getMainPanelSplitter() {
        return mainPanelSplitter;
    }

    public void setPoTab(RichPanelTabbed poTab) {
        this.poTab = poTab;
    }

    public RichPanelTabbed getPoTab() {
        return poTab;
    }
}
