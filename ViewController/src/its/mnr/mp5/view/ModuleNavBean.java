package its.mnr.mp5.view;

import its.mnr.mp5.model.MNRDataControlImpl;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import java.io.Serializable;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.TaskFlowContext;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.ItemEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;

import oracle.jbo.domain.Number;

import org.apache.commons.lang.StringUtils;

public class ModuleNavBean  implements Serializable{

    @SuppressWarnings("compatibility:834200296238744337")
    private static final long serialVersionUID = 1L;
    private String showEstimate;

    public ModuleNavBean() {
    }

    public void setShowEstimate(String showEstimate) {
        this.showEstimate = showEstimate;
    }

    public String getShowEstimate() {
        return returnShowEstimate();
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

    public boolean isDirty() {
        Boolean transDirty = false;
    
        /*
         //BindingContext bindingContext = getBindingContext();
         DataControlFrame dc = getBindingContext().findDataControlFrame("MNRDataControl");
         DCDataControl dcntrl =dc.findDataControl("MNRDataControl");
         MNRDataControlImpl am = (MNRDataControlImpl)dcntrl.getApplicationModule();
         transDirty = am.getTransaction().isDirty();
                 
         //BindingContext bc = getBindingContext();
         String dataControl = getBindingContext().getCurrentDataControlFrame();
         Boolean bindingsChanged = getBindingContext().findDataControlFrame(dataControl).isTransactionDirty();
         */        
        //BindingContext bindingContext = getBindingContext();
        /**
        DCBindingContainer dbc = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCDataControl dc = dbc.findDataControl("MNRDataControlDataControl");
        MNRDataControlImpl am = (MNRDataControlImpl)dc.getDataProvider();
        **/
        DCDataControl dc = getBindingContext().findDataControl("MNRDataControlDataControl");
        MNRDataControlImpl am = (MNRDataControlImpl)dc.getDataProvider();
        transDirty = am.getTransaction().isDirty();

        //BindingContext bc = getBindingContext();
        String dataControl = getBindingContext().getCurrentDataControlFrame();
        Boolean bindingsChanged = getBindingContext().findDataControlFrame(dataControl).isTransactionDirty();

        if (transDirty || bindingsChanged) {
           // System.out.println("transDirty:" + transDirty + "       bindingsChanged:" + bindingsChanged);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            return true;
        }
        ////System.out.println("Inside ModuleNavBean showEstimate: "+am.getMP5Profile("ShowEstimate"));
        return false;
    }
    public void goToControlFlow (String asaction, String soutcome){
            //navigate to controlcase_to_follow"
            FacesContext context = FacesContext.getCurrentInstance();
            NavigationHandler nh = context.getApplication().getNavigationHandler();
            nh.handleNavigation(context, asaction, soutcome);
        }
    
    public void goHomeModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goHome");  
        } 
    }
    public void goWOModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goWorkOrder");  
        }
    }
    public void goEstimatesModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goEstimates");  
        }
    }
    public void goInvoiceModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goInvoice");  
        }
    }
    public void goPORecvModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goPORecv");   
        } 
    }
    public void goStockModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goStock");  
        } 
    }
    public void goAssetModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goAsset");  
        } 
    }
    public void goReportModule(ActionEvent actionEvent) {
        if (isDirty() == false) {
            // go to WOTask
            goToControlFlow(null,"goReport");   
        }
    }
    public void goAdminModule(ActionEvent actionEvent) {
        if (isDirty() == false)  {
            // go to WOTask
            goToControlFlow(null,"goAdmin");
        }
    }
    public void goExit(ActionEvent actionEvent) {
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            
        } else {
            // go to WOTask
            goToControlFlow(null,"Exit");
        }
    }

    public String returnShowEstimate(){
        Boolean transDirty = false;
        String s="";
        DCBindingContainer dbc = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        DCDataControl dc = dbc.findDataControl("MNRDataControlDataControl");
        MNRDataControlImpl am = (MNRDataControlImpl)dc.getDataProvider();
      ////  transDirty = am.getTransaction().isDirty();

        //BindingContext bc = getBindingContext();
     ////   String dataControl = getBindingContext().getCurrentDataControlFrame();
        ////Boolean bindingsChanged = getBindingContext().findDataControlFrame(dataControl).isTransactionDirty();
/*
        if (transDirty || bindingsChanged) {
           // System.out.println("transDirty:" + transDirty + "       bindingsChanged:" + bindingsChanged);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            return "";
        }
*/
        s = am.getMP5Profile("ShowEstimate");
       // showEstimate=s;
        //setShowEstimate(am.getMP5Profile("ShowEstimate"));
       //// System.out.println("Inside ModuleNavBean showEstimate: "+am.getMP5Profile("ShowEstimate"));
    return s;
    }

}
