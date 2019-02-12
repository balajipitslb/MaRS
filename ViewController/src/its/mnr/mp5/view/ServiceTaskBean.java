package its.mnr.mp5.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;

import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;

public class ServiceTaskBean {
    public ServiceTaskBean() {
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

    public void actionListener(ActionEvent actionEvent) {
        String sAction = (String)actionEvent.getComponent().getAttributes().get("adfAction");
        if (isDirty()) {
            //System.out.println("Dirty");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));

        } else {
            BindingContainer bindings = getBindings();
            OperationBinding operationBinding = bindings.getOperationBinding(sAction);
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
            }
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
}
