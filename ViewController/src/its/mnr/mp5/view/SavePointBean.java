package its.mnr.mp5.view;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCDataControl;

import org.apache.myfaces.trinidad.context.RequestContext;

public class SavePointBean {
    public SavePointBean() {
        super();
    }
    RequestContext rctx = RequestContext.getCurrentInstance();
    
    public void restore_action() {
        //System.out.println("Restore Action");
    String sph = (String) rctx.getPageFlowScope().get("AdfmSavePoint");
    BindingContext bctx = BindingContext.getCurrent();
    DCDataControl dcDataControl = bctx.getDefaultDataControl();
    dcDataControl.restoreSavepoint(sph);        
    }
    
    public void createAdfmSavePoint(){
        //System.out.println("Creating Save Point");
    BindingContext bctx = BindingContext.getCurrent();
    DCDataControl dcDataControl = bctx.getDefaultDataControl();
    String sph = (String) dcDataControl.createSavepoint();
    rctx.getPageFlowScope().put("AdfmSavePoint",sph);
    }
}
