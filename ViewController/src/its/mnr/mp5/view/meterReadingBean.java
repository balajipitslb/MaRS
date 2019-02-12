package its.mnr.mp5.view;

import its.mnr.mp5.vo.asset.AssetReadingImpl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowSetImpl;
import oracle.jbo.uicli.binding.JUCtrlListBinding;

import org.apache.commons.lang.StringUtils;

public class meterReadingBean {
    private RichInputText txtAssetCd;
    private RichInputText txtAssetOrg;
    private RichInputDate txtDate;
    private RichInputText txtDifference;
    private RichInputText txtUom;
    private RichInputText txtLastReading;
    private RichInputText txtLastReadingDate;
    private RichInputText txtNewReading;
    private RichSelectOneChoice socMeterType;

    public meterReadingBean() {
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
    
    public void setTxtAssetCd(RichInputText txtAssetCd) {
        this.txtAssetCd = txtAssetCd;
    }

    public RichInputText getTxtAssetCd() {
        return txtAssetCd;
    }

    public void setTxtAssetOrg(RichInputText txtAssetOrg) {
        this.txtAssetOrg = txtAssetOrg;
    }

    public RichInputText getTxtAssetOrg() {
        return txtAssetOrg;
    }

    public void setTxtDate(RichInputDate txtDate) {
        this.txtDate = txtDate;
    }

    public RichInputDate getTxtDate() {
        return txtDate;
    }

    public void setTxtDifference(RichInputText txtDifference) {
        this.txtDifference = txtDifference;
    }

    public RichInputText getTxtDifference() {
        return txtDifference;
    }

    public void setTxtUom(RichInputText txtUom) {
        this.txtUom = txtUom;
    }

    public RichInputText getTxtUom() {
        return txtUom;
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
    
    public  oracle.jbo.domain.Date getCurrentOracleDate()
     {
       return new oracle.jbo.domain.Date(oracle.jbo.domain.Date.getCurrentDate());
     }
    
    public String createInsert_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public void getLastReadByType(String sAsset, String sOrg, Integer iType) {
        // get last meter reading
//        System.out.println("getLastReadByType");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("LastReadingTypeIterator");
//        System.out.println("LastReadingTypeIterator Row Count: " + dciter.getEstimatedRowCount());

        ViewObjectImpl vo = (ViewObjectImpl)dciter.getViewObject();
        ViewRowSetImpl rowSet = (ViewRowSetImpl)vo.createRowSet(null); //secondary rowSet
                    
        vo.ensureVariableManager().setVariableValue("BIND_ASSETCD", sAsset); 
        vo.ensureVariableManager().setVariableValue("BIND_ASSETORG", sOrg); 
        vo.ensureVariableManager().setVariableValue("BIND_TYPE", iType); 
        
        vo.executeQuery(); 
        RowIterator rit = rowSet.getRowSetIterator();
        rit.reset();
        Row row=null;  
        
     
        Integer dLastReading = 0;
        Date tLastDate = null;
       
        while (rit.hasNext()){
                row = rit.next();
//                if(row.getAttribute("Readingdate")!=null){      
//                        System.out.println("Readingdate :"+row.getAttribute("Readingdate").toString());               
//                }
            dLastReading = (Integer)row.getAttribute("Readingvalue");
//            System.out.println(dLastReading);
            tLastDate = (Date)row.getAttribute("Readingdate");
//            System.out.println(tLastDate);
        }  
        // auto-fill values


        ADFUtil.setEL("#{bindings.tLastReadingValue.inputValue}", dLastReading);
        ADFUtil.setEL("#{bindings.tLastReadingDate.inputValue}", tLastDate);

        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtLastReading);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtLastReadingDate);
        
        rowSet.closeRowSet();
    }

    public void meterTypeLsnr(ValueChangeEvent valueChangeEvent) {
//       System.out.println("meterTypeLsnr");
        //get current instance of change
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Integer sReadingType = (Integer)ADFUtil.evaluateEL("#{bindings.Readinglabel.inputValue}");
        //String sReadingType = (String)valueChangeEvent.getNewValue();
//        System.out.println("Reading Type Selected: " + sReadingType);
        
        // 
        String sAsset = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfAssetCd");
        String sAssetOrg = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfAssetOrg");
        
        getLastReadByType(sAsset,sAssetOrg,sReadingType);
    }
    
    public String crInsReading() {
        //System.out.println("Getting last reading");
        // get last meter reading
        String sAssetStatusRtn ="Can not create Reading";
        sAssetStatusRtn = getAssetStatusFnc();
        //System.out.println("sAssetStatusRtn: " +sAssetStatusRtn);
       
        if ( "IS".equals(sAssetStatusRtn) == false) {
            userModificationMsg("INFO", "Info", sAssetStatusRtn);
            return null;
        }
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding dciter = (DCIteratorBinding)bindings.get("AssetReadingIterator");
        //System.out.println("AssetReadingIterator Row Count: " + dciter.getEstimatedRowCount());
        
        Integer dLastReading = 0;
        Date tLastDate = null;

        if (dciter.getEstimatedRowCount() > 0) {
            for (int i = 0; i < 1; i++) {
                Row row = dciter.getRowAtRangeIndex(i);
                // create Invoice Item record for every selected Work Order
                dLastReading = (Integer)row.getAttribute("Readingvalue");
                tLastDate = (Date)row.getAttribute("Readingdate");
                            //System.out.println("Last reading: " + dLastReading);
                          //  System.out.println("Last reading date: " + tLastDate);
            }
        }

        // Create new record
        createInsert_action();

        // auto-fill values
        String sAsset = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfAssetCd");
        String sAssetOrg = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfAssetOrg");
        String sAssetUom = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfAssetUom");
        //System.out.println("sAsset: "+sAsset+" sAssetOrg: "+sAssetOrg+" sAssetUom: " + sAssetUom);

        ADFUtil.setEL("#{bindings.Readingitem.inputValue}", sAsset);
        ADFUtil.setEL("#{bindings.Org.inputValue}", sAssetOrg);
        ADFUtil.setEL("#{bindings.Uom.inputValue}", sAssetUom);
        //System.out.println("Inside crInsReading getCurrentOracleDate(): "+getCurrentOracleDate()+" oracle.jbo.domain.Date.getCurrentDate(): "+oracle.jbo.domain.Date.getCurrentDate()+" new java.util.Date(): "+new java.util.Date());
       // ADFUtil.setEL("#{bindings.Readingdate.inputValue}", getCurrentOracleDate());
        ADFUtil.setEL("#{bindings.tLastReadingValue.inputValue}", dLastReading);
        ADFUtil.setEL("#{bindings.tLastReadingDate.inputValue}", tLastDate);        
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtAssetCd);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtAssetOrg);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtUom);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtLastReading);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtLastReadingDate);

        return null;
    }

    
     public void setTxtLastReading(RichInputText txtLastReading) {
        this.txtLastReading = txtLastReading;
    }

    public RichInputText getTxtLastReading() {
        return txtLastReading;
    }

    public void setTxtLastReadingDate(RichInputText txtLastReadingDate) {
        this.txtLastReadingDate = txtLastReadingDate;
    }

    public RichInputText getTxtLastReadingDate() {
        return txtLastReadingDate;
    }

    public void setTxtNewReading(RichInputText txtNewReading) {
        this.txtNewReading = txtNewReading;
    }

    public RichInputText getTxtNewReading() {
        return txtNewReading;
    }
    
    public Boolean chkRequiredFields() {
        Boolean ret = true;
        
//        System.out.println("socMeterType: " +this.socMeterType.getValue());
        if (this.txtNewReading.getValue() == null || this.txtDate.getValue() == null || this.socMeterType.getValue() == "") {
            String msg = "Reading Value, Type, and Date are required";
            userModificationMsg("INFO", "Info", msg);
            ret = false;

        }
        return ret;

    }
    
    public String getAssetStatusFnc() {
        String sAsset = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfAssetCd");
        String sAssetOrg = (String)AdfFacesContext.getCurrentInstance().getPageFlowScope().get("psfAssetOrg");
//        System.out.println("sAsset: " +sAsset);
//        System.out.println("sAssetOrg: " + sAssetOrg);
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("getAssetStatusFnc");
        operationBinding.getParamsMap().put("PASSET", sAsset);
        operationBinding.getParamsMap().put("PORG", sAssetOrg);
        String result = (String)operationBinding.execute();
        if(result!=null){
        //    System.out.println("Inside getAssetStatusFnc result: "+result);
        }
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return result;
    }
    
    public String saveReading() {
        Boolean reqFields = false;
        reqFields = chkRequiredFields();

        if (!reqFields) {
            return "";
        }
        
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String rollback_action() {
     //   System.out.println("rolling back");
        String sAsset = (String)ADFUtil.evaluateEL("#{bindings.Readingitem.inputValue}");
       // System.out.println(sAsset);
        BindingContainer bindings = getBindings();

        if (StringUtils.isBlank(sAsset)) {
            // perform rollback operation on new record
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {
            // get current rows
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("AssetReadingIterator");
            Row rowKey = iter.getCurrentRow();
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            //set current rows
            if (StringUtils.isNotBlank(sAsset)) {
                if (rowKey != null) {
                    System.out.println("Parent key not blank");
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

        }
        return "";
    }

    public void setSocMeterType(RichSelectOneChoice socMeterType) {
        this.socMeterType = socMeterType;
    }

    public RichSelectOneChoice getSocMeterType() {
        return socMeterType;
    }

}
