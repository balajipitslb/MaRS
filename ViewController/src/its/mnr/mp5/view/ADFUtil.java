package its.mnr.mp5.view;

import java.text.DecimalFormat;

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.adf.model.BindingContainer;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.event.QueryEvent;

import org.apache.myfaces.trinidad.event.SelectionEvent;

public class ADFUtil {
    public ADFUtil() {
        super();
    }
   
   /**
   * Programmatic invocation of a method that an EL evaluates to.
   *
   * @param el EL of the method to invoke
   * @param paramTypes Array of Class defining the types of the parameters
   * @param params Array of Object defining the values of the parametrs
   * @return Object that the method returns
   */
   public static Object invokeEL(String el, Class[] paramTypes, Object[] params) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ELContext elContext = facesContext.getELContext();
    ExpressionFactory expressionFactory =
    facesContext.getApplication().getExpressionFactory();
    MethodExpression exp =
    expressionFactory.createMethodExpression(elContext, el,
    Object.class, paramTypes);
   
    return exp.invoke(elContext, params);
    }
  
   /**
   * Programmatic invocation of a method that an EL evaluates to.
   * The method must not take any parameters.
   *
   * @param el EL of the method to invoke
   * @return Object that the method returns
   */
   public static Object invokeEL(String el) {
   return invokeEL(el, new Class[0], new Object[0]);
   }
  
   /**
   * Sets a value into an EL object. Provides similar functionality to
   * the <af:setActionListener> tag, except the from is
   * not an EL. You can get similar behavior by using the following...
  
   * setEL(to, evaluateEL(from))
   *
   * @param el EL object to assign a value
   * @param val Value to assign
   */
   public static void setEL(String el, Object val) {
   FacesContext facesContext = FacesContext.getCurrentInstance();
   ELContext elContext = facesContext.getELContext();
   ExpressionFactory expressionFactory =
   facesContext.getApplication().getExpressionFactory();
   ValueExpression exp =
   expressionFactory.createValueExpression(elContext, el,
   Object.class);
  
   exp.setValue(elContext, val);
   }

/**
  * Programmatic evaluation of EL.
  *
  * @param el EL to evaluate
  * @return Result of the evaluation
  */
  public static Object evaluateEL(String el) {
  FacesContext facesContext = FacesContext.getCurrentInstance();
  ELContext elContext = facesContext.getELContext();
  ExpressionFactory expressionFactory =
  facesContext.getApplication().getExpressionFactory();
  ValueExpression exp =
  expressionFactory.createValueExpression(elContext, el,
  Object.class);
  
  return exp.getValue(elContext);
  }

    public static void invokeMethodExpressionQueryEvent(String expr, QueryEvent queryEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elContext = fctx.getELContext();
        ExpressionFactory eFactory = fctx.getApplication().getExpressionFactory();
        MethodExpression mexpr =
            eFactory.createMethodExpression(elContext, expr, Object.class, new Class[] { QueryEvent.class });
        mexpr.invoke(elContext, new Object[] { queryEvent });
    }
    
    public static void invokeMethodExpressionSelectionEvent(String expr, SelectionEvent selectionEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elContext = fctx.getELContext();
        ExpressionFactory eFactory = fctx.getApplication().getExpressionFactory();
        MethodExpression mexpr =
            eFactory.createMethodExpression(elContext, expr, Object.class, new Class[] { SelectionEvent.class });
        mexpr.invoke(elContext, new Object[] { selectionEvent });
    }
    public static Double roundToHundredth(Double dValue){
            DecimalFormat df = new DecimalFormat("###.##");
            return Double.valueOf(df.format(dValue));        
        }
}