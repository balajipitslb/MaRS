package its.mnr.mp5.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import its.mnr.mp5.model.Util;

public class ErrorHandler {
    public ErrorHandler() {
        super();
    }

    public void controllerExceptionHandler() {
        ControllerContext context = ControllerContext.getInstance();
        ViewPortContext currentRootViewPort = context.getCurrentRootViewPort();
        Exception exceptionData = currentRootViewPort.getExceptionData();
        
        if (currentRootViewPort.isExceptionPresent()) {
            exceptionData.printStackTrace();
            currentRootViewPort.clearException();
          
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, exceptionData.getMessage(), null));
            
            Util.sendMessage("henry.chan@itslb.com", "Exception Handler", exceptionData.getMessage(), "itsdc16", 25,
                                               "henry.chan@itslb.com");
        }
    }
}