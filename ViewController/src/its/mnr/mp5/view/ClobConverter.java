package its.mnr.mp5.view;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.application.FacesMessage;
import javax.faces.convert.ConverterException;
import oracle.jbo.domain.ClobDomain;
import oracle.jbo.domain.DataCreationException;

public class ClobConverter implements Converter {
     public Object getAsObject(FacesContext facesContext,
                               UIComponent uIComponent,
                               String string) {
       try {
         return string != null ? new ClobDomain(string) : null;
       } catch (DataCreationException dce) {
         dce.setAppendCodes(false);
         FacesMessage fm =
           new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Invalid Clob Value",
                            dce.getMessage());
         throw new ConverterException(fm);
       }
     }
     public String getAsString(FacesContext facesContext,
                               UIComponent uIComponent,
                               Object object) {
       return object != null ?
              object.toString() :
              null;
     }
}
