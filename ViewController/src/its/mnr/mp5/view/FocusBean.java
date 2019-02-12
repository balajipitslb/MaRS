package its.mnr.mp5.view;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class FocusBean {
    public FocusBean() {
    }

    public String onSetFocus(ActionEvent event) {
        RichButton rcb = (RichButton)event.getSource();
        String focusOn = (String)rcb.getAttributes().get("focusField");

        FacesContext fctx = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = fctx.getViewRoot();
        
        //search can be improved to include naming containers
        RichInputText rit = (RichInputText)viewRoot.findComponent(focusOn);

        if (rit != null) {
            String clientId = rit.getClientId(fctx);
            
            StringBuilder script = new StringBuilder();
            //use client id to ensure component is found if located in
            //naming container            
            script.append("var textInput = ");
            script.append("AdfPage.PAGE.findComponentByAbsoluteId");
            script.append ("('"+clientId+"');");

            script.append("if(textInput != null){");            
            script.append("textInput.focus();");
            script.append("}");
            writeJavaScriptToClient(script.toString());
        }
        return null;
    }


    public static void setNextFocus (RichInputText focusOn) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        //UIViewRoot viewRoot = fctx.getViewRoot();
        
        if (focusOn != null) {
            String clientId = focusOn.getClientId(fctx);
            
            StringBuilder script = new StringBuilder();
            //use client id to ensure component is found if located in
            //naming container            
            script.append("var textInput = ");
            script.append("AdfPage.PAGE.findComponentByAbsoluteId");
            script.append ("('"+clientId+"');");

            script.append("if(textInput != null){");            
            script.append("textInput.focus();");
            script.append("}");
            writeJavaScriptToClient(script.toString());
        }
    }
    
    public static void setNextFocusLOV (RichInputListOfValues focusOn) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        //UIViewRoot viewRoot = fctx.getViewRoot();
        
        if (focusOn != null) {
            String clientId = focusOn.getClientId(fctx);
            
            StringBuilder script = new StringBuilder();
            //use client id to ensure component is found if located in
            //naming container            
            script.append("var textInput = ");
            script.append("AdfPage.PAGE.findComponentByAbsoluteId");
            script.append ("('"+clientId+"');");

            script.append("if(textInput != null){");            
            script.append("textInput.focus();");
            script.append("}");
            writeJavaScriptToClient(script.toString());
        }
    }
    private static void writeJavaScriptToClient(String script) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExtendedRenderKitService erks = null;
        erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
        erks.addScript(fctx, script);
    }

}