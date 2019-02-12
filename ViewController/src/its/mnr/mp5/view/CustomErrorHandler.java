package its.mnr.mp5.view;

import java.sql.SQLIntegrityConstraintViolationException;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCErrorHandlerImpl;

import oracle.jbo.DMLConstraintException;
import oracle.jbo.SQLStmtException;

public class CustomErrorHandler extends DCErrorHandlerImpl {
    public CustomErrorHandler(boolean b) {
        super(b);
    }
    public CustomErrorHandler() {
        super(false);
    }
    /**
     * If an exception is a RowValException or a TxnValException
     * and they have nested exceptions, then do not display
     * it.
     */
    @Override
    protected boolean skipException(Exception ex) {

        if (ex instanceof DMLConstraintException) {
            return false;
        } else if (ex instanceof SQLIntegrityConstraintViolationException) {
            return true;
        }
        return super.skipException(ex);
    }
    
    @Override
    public void reportException(DCBindingContainer dCBindingContainer, Exception exception){
        if(exception instanceof SQLStmtException){
            if(Integer.parseInt(((SQLStmtException)exception).getErrorCode()) == 27122){
                System.out.println("LOV in Find Mode Exception");
            }
        }
        else{
            super.reportException(dCBindingContainer, exception);
        }
    }
}
