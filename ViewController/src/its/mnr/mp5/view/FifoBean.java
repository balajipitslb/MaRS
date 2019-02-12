package its.mnr.mp5.view;

import java.sql.Date;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.validator.ValidatorException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowNotFoundException;
import oracle.jbo.ViewObject;

public class FifoBean {
    private RichInputDate txtACcreated;
    private RichButton btnACUpdate;
    private RichButton btnADJUpdate;
    private RichInputDate txtADJcreated;
    private RichInputText email1;
    private RichInputText email2;
    private RichInputText email3;
    private RichInputText email4;
    private RichSelectBooleanCheckbox emailSubChk;
    private RichPopup fifoExceptionPartPP;

    public FifoBean() {
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
        
    public void goToControlFlow (String asaction, String soutcome){
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
    

    public String commit_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Commit");
        //Object result = 
            operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        return null;
    }

    public String rollback_action() {
        BindingContainer bindings = getBindings();

        // perform rollback operation
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        return null;
    }
    public String cancel_action() {
        rollback_action();
        goToControlFlow(null, "Exit");
        return null;
    }
    
    public String createAC_action() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("Create");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        
        if (isDirty()){
            System.out.println("isDirty create");
            commit_action();
        }
        return null;
    }
    
    public String okAC_action() {
       // System.out.println("okAC_action()");
        if (isDirty()){
        //    System.out.println("isDirty");
            commit_action();
        }
        Integer iListid = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        Integer iFxhdid = (Integer)ADFUtil.evaluateEL("#{bindings.Fpxhid.inputValue}");
        String emailStr = "";
        emailStr = emailStrAsCounted();
        //System.out.println("emailStr: " +emailStr);
        //System.out.println("iFxhdid: " +iFxhdid);
        String ret = ccFIFO(iListid, iFxhdid,emailStr);
        //System.out.println("ccFIFO(iListid, iFxhdid,emailStr); ret:" + ret);
        getDBFIFOHdr();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtACcreated); 
        userModificationMsg("INFO", "Success", "As Counted FIFO update successful.");
        this.btnACUpdate.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.btnACUpdate); 
        return ""; //ret;
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
    
    public String emailStrAsCounted() {
            String emailStr = "";
            if(email1!=null){
                if((email1.getValue()!=null) && (email1.getValue()!="")){
                    if((emailStr!=null)&&(emailStr!="")){
                        //System.out.println("inside email1 1");
                        emailStr = emailStr + ","+ email1.getValue().toString();
                    }
                    else if(emailStr == ""){
                        //System.out.println("inside email1 2");
                        emailStr = email1.getValue().toString();
                    }               
                }
                else{
                    //System.out.println("inside email1 3");
                   // emailStr = "";
                }
            }
            else{
                //System.out.println("inside email1 4");
                emailStr = "";
            }
                
            if(email2!=null){
                if((email2.getValue()!=null)&& (email2.getValue()!="")){
                if((emailStr!=null)&&(emailStr!="")){
                    //System.out.println("inside email2 1");
                        emailStr = emailStr + ","+ email2.getValue().toString();
                }
                else if(emailStr == ""){
                    //System.out.println("inside email2 2");
                        emailStr = email2.getValue().toString();
                }
                }
                else{
                    //System.out.println("inside email2 3");
                   // emailStr = "";
                }
            }
            else{
                //System.out.println("inside email2 4");
                emailStr = "";
            }
            if((emailStr == "")){
                emailStr = getMP5Profile("AdminEmailAddress");
                //System.out.println("emailStr 1: "+emailStr);
            }
            //System.out.println("emailStr 2: "+emailStr);
            if(ADFContext.getCurrent().getSessionScope().containsKey("email1"))
                ADFContext.getCurrent().getSessionScope().remove("email1");
            ADFContext.getCurrent().getSessionScope().put("email1",emailStr);  
            return emailStr;
        }  
    
    public String emailStrAdj(){
        String emailStr = "";
        if(email3!=null){
            if((email3.getValue()!=null) && (email3.getValue()!="")){
                if((emailStr!=null)&&(emailStr!="")){
                  //  //System.out.println("inside email3 1");
                    emailStr = emailStr + ","+ email3.getValue().toString();
                }
                else if(emailStr == ""){
                 //   //System.out.println("inside email3 2");
                    emailStr = email3.getValue().toString();
                }               
            }
            else{
              //  //System.out.println("inside email3 3");
               // emailStr = "";
            }
        }
        else{
          //  //System.out.println("inside email3 4");
            emailStr = "";
        }
            
        if(email4!=null){
            if((email4.getValue()!=null)&& (email4.getValue()!="")){
            if((emailStr!=null)&&(emailStr!="")){
               // //System.out.println("inside email4 1");
                    emailStr = emailStr + ","+ email4.getValue().toString();
            }
            else if(emailStr == ""){
               // //System.out.println("inside email4 2");
                    emailStr = email4.getValue().toString();
            }
            }
            else{
          //      //System.out.println("inside email4 3");
               // emailStr = "";
            }
        }
        else{
        //    //System.out.println("inside email4 4");
            emailStr = "";
        }
        if((emailStr == "")){
            emailStr = getMP5Profile("AdminEmailAddress");
          //  //System.out.println("emailStr 1: "+emailStr);
        }
      //  //System.out.println("emailStr 2: "+emailStr);
        if(ADFContext.getCurrent().getSessionScope().containsKey("email1"))
            ADFContext.getCurrent().getSessionScope().remove("email1");        
        ADFContext.getCurrent().getSessionScope().put("email1",(String)emailStr);  
        return emailStr;
    }
    
    public String okADJ_action() {
        //System.out.println("okADJ_action()");
        if (isDirty()){
            //System.out.println("isDirty");
            commit_action();
        }
        Integer iListid = (Integer)ADFUtil.evaluateEL("#{bindings.Listid.inputValue}");
        Integer iFxhdid = (Integer)ADFUtil.evaluateEL("#{bindings.Fpxhid.inputValue}");
        //System.out.println("iListid: " +iListid);
        //System.out.println("iFxhdid: " +iFxhdid);
        String emailStr = "";
        emailStr = emailStrAdj();
        String ret = ccFIFOAdj(iListid, iFxhdid,emailStr);
        //System.out.println("ccFIFOAdj(iListid, iFxhdid,emailStr); ret:" + ret);
        getDBFIFOHdr();
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtADJcreated); 
        userModificationMsg("INFO", "Success", "Adjusted FIFO update successful.");
        this.btnADJUpdate.setDisabled(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(this.btnADJUpdate); 
        return ""; //ret;
    }


    
    public String getDBFIFOHdr() {
        DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCIteratorBinding iter = bindings.findIteratorBinding("MrltFifoheaderViewIterator");
        ViewObject vo = iter.getViewObject();
        vo.setQueryMode(vo.QUERY_MODE_SCAN_DATABASE_TABLES);
        return null;
    }

    public String onExitExcptn() {
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
            goToControlFlow(null, "Exit");
        }
        return null;
    }
    
    public String ccFIFO(Integer iListid, Integer iFxhdid, String emailStr) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ccFIFO");
        operationBinding.getParamsMap().put("iListid", iListid);
        operationBinding.getParamsMap().put("iFxhdid", iFxhdid);
        operationBinding.getParamsMap().put("emailStr", emailStr);
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return result;
        }
        return result;
    }

    public String ccFIFOAdj(Integer iListid, Integer iFxhdid, String emailStr) {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("ccFIFOAdj");
        operationBinding.getParamsMap().put("iListid", iListid);
        operationBinding.getParamsMap().put("iFxhdid", iFxhdid);
        operationBinding.getParamsMap().put("emailStr", emailStr);
        String result = (String)operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return result;
        }
        return result;
    }
    
    public void setTxtACcreated(RichInputDate txtACcreated) {
        this.txtACcreated = txtACcreated;
    }

    public RichInputDate getTxtACcreated() {
        return txtACcreated;
    }

    public void setBtnACUpdate(RichButton btnACUpdate) {
        this.btnACUpdate = btnACUpdate;
    }

    public RichButton getBtnACUpdate() {
        return btnACUpdate;
    }


    public void setBtnADJUpdate(RichButton btnADJUpdate) {
        this.btnADJUpdate = btnADJUpdate;
    }

    public RichButton getBtnADJUpdate() {
        return btnADJUpdate;
    }

    public void setTxtADJcreated(RichInputDate txtADJcreated) {
        this.txtADJcreated = txtADJcreated;
    }

    public RichInputDate getTxtADJcreated() {
        return txtADJcreated;
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

    public void setEmail3(RichInputText email3) {
        this.email3 = email3;
    }

    public RichInputText getEmail3() {
        return email3;
    }

    public void setEmail4(RichInputText email4) {
        this.email4 = email4;
    }

    public RichInputText getEmail4() {
        return email4;
    }

    public void emailSubCheckbox(ValueChangeEvent vce) {
        Boolean newVal = (Boolean)vce.getNewValue();
        //System.out.println("newVal: "+newVal.booleanValue());
        if(ADFContext.getCurrent().getSessionScope().containsKey("emailSubCh")){
            ADFContext.getCurrent().getSessionScope().remove("emailSubCh");
        }
        ADFContext.getCurrent().getSessionScope().put("emailSubCh",newVal);
        
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
        if(object!=null){
            String name=object.toString();
            String expression="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            CharSequence inputStr=name;
            Pattern pattern=Pattern.compile(expression);
            Matcher matcher=pattern.matcher(inputStr);
            String msg="Email is not in Proper Format";
            if(matcher.matches()){
               
            }
            else{
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
            }
        }
    }

    public void addFifoExceptionPart(ActionEvent ae) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();

            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding("CreateInsert1");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return;
            }
            fifoExceptionPartPP.show(hints); 
        
    
    }
     public void closeFifoExceptionPartPP(ActionEvent ae) {
         if (isDirty()) {
             userModificationMsg("INFO", "Info", "Apply or Cancel Changes before closing.");
             return;
         }
         
         fifoExceptionPartPP.hide();    
         rollback_action();
     }
     
    public void setFifoExceptionPartPP(RichPopup fifoExceptionPartPP) {
        this.fifoExceptionPartPP = fifoExceptionPartPP;
    }

    public RichPopup getFifoExceptionPartPP() {
        return fifoExceptionPartPP;
    }

}
