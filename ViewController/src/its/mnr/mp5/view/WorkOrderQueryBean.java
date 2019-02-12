package its.mnr.mp5.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.view.rich.component.rich.RichQuery;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;

import oracle.adf.view.rich.model.QueryDescriptor;

import oracle.adf.view.rich.model.QueryModel;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class WorkOrderQueryBean {
    private RichTable resultsTable;
    private RichQuery woRichQuery;

    public WorkOrderQueryBean() {
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

    public void processQuery(QueryEvent queryEvent) {
        //QueryDescriptor qdes = queryEvent.getDescriptor();
        //System.out.println("NAME " + qdes.getName());
        if (isDirty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                               new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
        } else {
            ADFUtil.invokeMethodExpressionQueryEvent("#{bindings.WorkOrderListQuery.processQuery}", queryEvent);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getResultsTable());
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

    public void eventsTableSelectionListener(SelectionEvent selectionEvent) {
        String sEvtCode = (String)ADFUtil.evaluateEL("#{bindings.EvtCode.inputValue}");
        if (isDirty()) {
            if (StringUtils.isBlank(sEvtCode)) {
                BindingContainer bindings = getBindings();
                OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
                operationBinding.execute();

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null,
                                   new FacesMessage(FacesMessage.SEVERITY_INFO, "Apply or Cancel Changes First!", null));
            }
        } else {
            ADFUtil.invokeMethodExpressionSelectionEvent("#{bindings.WOEvents.collectionModel.makeCurrent}", selectionEvent);
        }
    }

    public void setResultsTable(RichTable resultsTable) {
        this.resultsTable = resultsTable;
    }

    public RichTable getResultsTable() {
        return resultsTable;
    }

    public void setWoRichQuery(RichQuery woRichQuery) {
        this.woRichQuery = woRichQuery;
    }

    public RichQuery getWoRichQuery() {
        return woRichQuery;
    }
}
