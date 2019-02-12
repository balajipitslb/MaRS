package its.mnr.mp5.view;

import oracle.adf.model.BindingContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowNotFoundException;

import org.apache.commons.lang.StringUtils;

public class SupplierBean {
    private RichInputText txtAddressInvoiceCd;
    private RichInputText txtAddressInvoiceCountry;
    private RichInputText txtAddressMailCountry;
    private RichInputText txtAddressMailCd;

    public SupplierBean() {
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
    
    public String crAddressMail() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertAddressMail");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        setAddressDflts("M");
        
        return null;
    }
    
    public String crAddressInvoice() {
        BindingContainer bindings = getBindings();
        OperationBinding operationBinding = bindings.getOperationBinding("CreateInsertAddressInvoice");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }
        setAddressDflts("I");
        
        return null;
        
        
    }

    public void setAddressDflts(String addressType) {
        String sOrg = "1";
        String sCd = "";
        String sNewCd = "";
        String sCountry = "USA";

        sOrg = (String)ADFUtil.evaluateEL("#{bindings.ComOrg.inputValue}");
        sCd = (String)ADFUtil.evaluateEL("#{bindings.ComCode.inputValue}");
        sNewCd = sCd + '#' + sOrg;

        if ("I".equals(addressType)) {
            ADFUtil.setEL("#{bindings.AdrCode.inputValue}", sNewCd);
            ADFUtil.setEL("#{bindings.AdrRtype.inputValue}", addressType);
            ADFUtil.setEL("#{bindings.AdrCountry.inputValue}", sCountry);

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtAddressInvoiceCountry);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtAddressInvoiceCd);
        } else if ("M".equals(addressType)) {
            ADFUtil.setEL("#{bindings.AdrCode1.inputValue}", sNewCd);
            ADFUtil.setEL("#{bindings.AdrRtype1.inputValue}", addressType);
            ADFUtil.setEL("#{bindings.AdrCountry1.inputValue}", sCountry);

            AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtAddressMailCountry);
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.txtAddressMailCd);

        }


    }

    public String doRollback() {
        System.out.println("rolling back");
        String sComp = (String)ADFUtil.evaluateEL("#{bindings.ComCode.inputValue}");
        BindingContainer bindings = getBindings();

        if (StringUtils.isBlank(sComp)) {
            // perform rollback operation on new record
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
        } else {
            // get current rows
            DCIteratorBinding iter = (DCIteratorBinding)getDCBindingContainer().get("CompaniesIterator");
            Row rowKey = iter.getCurrentRow();
            // perform rollback operation
            OperationBinding operationBinding = bindings.getOperationBinding("Rollback");
            Object result = operationBinding.execute();
            if (!operationBinding.getErrors().isEmpty()) {
                return null;
            }
            //set current rows
            if (StringUtils.isNotBlank(sComp)) {
                if (rowKey != null) {
                    Key parentKey = rowKey.getKey();
                    try {
                        iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                        //System.out.println("Setting Orders");
                    } catch (RowNotFoundException ex) {
                        iter.getViewObject().applyViewCriteria(null);
                        iter.executeQuery();
                        //iter.setCurrentRowWithKey(parentKey.toStringFormat(true));
                    }
                }
            }

        }
        return "";
        }
    
    public void setTxtAddressInvoiceCd(RichInputText txtAddressInvoiceCd) {
        this.txtAddressInvoiceCd = txtAddressInvoiceCd;
    }

    public RichInputText getTxtAddressInvoiceCd() {
        return txtAddressInvoiceCd;
    }

    public void setTxtAddressInvoiceCountry(RichInputText txtAddressInvoiceCountry) {
        this.txtAddressInvoiceCountry = txtAddressInvoiceCountry;
    }

    public RichInputText getTxtAddressInvoiceCountry() {
        return txtAddressInvoiceCountry;
    }


    public void setTxtAddressMailCountry(RichInputText txtAddressMailCountry) {
        this.txtAddressMailCountry = txtAddressMailCountry;
    }

    public RichInputText getTxtAddressMailCountry() {
        return txtAddressMailCountry;
    }

    public void setTxtAddressMailCd(RichInputText txtAddressMailCd) {
        this.txtAddressMailCd = txtAddressMailCd;
    }

    public RichInputText getTxtAddressMailCd() {
        return txtAddressMailCd;
    }


}
