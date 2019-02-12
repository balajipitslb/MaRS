package its.mnr.mp5.view;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.share.ADFContext;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.event.ReturnEvent;


public class AccountBillingMasterBean {

    public AccountBillingMasterBean() {
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }
    public Boolean getViewScopeValue(String name) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map viewScope = adfCtx.getViewScope();
        Boolean val = (Boolean)viewScope.get(name);

        if (val == null)
            return false;
        else
            return true;
    }
        
    public String onRollback() {
        BindingContainer bindings = getBindings();
        //get rowKey for parent and child
        DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("AccountMasterViewIterator");
        DCIteratorBinding childIter = (DCIteratorBinding)bindings.get("AccountRateViewIterator");
        DCIteratorBinding grandchildIter = (DCIteratorBinding)bindings.get("AccountRateTaskViewIterator");   
        Key parentKey = parentIter.getCurrentRow().getKey();        
        Key childKey = childIter.getCurrentRow().getKey();
        Key grandchildKey = grandchildIter.getCurrentRow().getKey();
        
        // perform rollback operation
        OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        //user row key back to preserve row selection
        parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        childIter.setCurrentRowWithKey(childKey.toStringFormat(true));
        grandchildIter.setCurrentRowWithKey(grandchildKey.toStringFormat(true));

        //System.out.println("isVerRateDelAction():" + isVerRateDelAction());
        ///*
        if (isVerRateDelAction()) {
            //System.out.println("calling refresh");
            refreshNewVersion();
            parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
        }
        //*/
        return null;
    }

    public void refreshNewVersion() {
        BindingContext bc = BindingContext.getCurrent();
        DCBindingContainer dcb = (DCBindingContainer)bc.getCurrentBindingsEntry();
        DCIteratorBinding iter = dcb.findIteratorBinding("AccountMasterViewIterator");
        DCIteratorBinding iter2 = dcb.findIteratorBinding("AccountRateVersionDetailIterator"); 
        iter.executeQuery();
        iter2.executeQuery();
        //RequestContext.getCurrentInstance().addPartialTarget(returnEvent.getComponent().getParent().getParent());
    }
    
    public Boolean isVerRateDelAction() {
        Boolean ret = false;
        ret = getViewScopeValue("psfVerDelAction");

        //System.out.println("ret: " + ret);
        return ret;
    }

}
