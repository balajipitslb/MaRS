package its.mnr.mp5.model;

import its.mnr.mp5.baseclass.ExtendedApplicationModuleImpl;
import its.mnr.mp5.baseclass.ExtendedViewObjectImpl;
import its.mnr.mp5.model.common.INVDataControl;
import its.mnr.mp5.vo.invoice.InvoiceAssemblyCriteriaImpl;

import its.mnr.mp5.vo.invoice.InvoiceAssemblyCriteriaRowImpl;
import its.mnr.mp5.vo.invoice.InvoiceAssemblyResultsImpl;

import its.mnr.mp5.vo.invoice.InvoiceCreditCriteriaImpl;
import its.mnr.mp5.vo.invoice.InvoiceCreditCriteriaRowImpl;
import its.mnr.mp5.vo.invoice.InvoiceCreditResultsImpl;
import its.mnr.mp5.vo.invoice.InvoiceCreditResultsRowImpl;
import its.mnr.mp5.vo.invoice.InvoiceRetroCriteriaImpl;
import its.mnr.mp5.vo.invoice.InvoiceRetroCriteriaRowImpl;
import its.mnr.mp5.vo.invoice.InvoiceRetroResultsImpl;
import its.mnr.mp5.vo.invoice.MrltInvoicecritemViewImpl;
import its.mnr.mp5.vo.invoice.MrltInvoiceheaderViewImpl;
import its.mnr.mp5.vo.invoice.MrltInvoiceitemViewImpl;

import its.mnr.mp5.vo.invoice.MrltInvoicertroitemViewImpl;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.ViewLinkImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 03 14:05:51 PDT 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class INVDataControlImpl extends ExtendedApplicationModuleImpl implements INVDataControl {


    public void initInvoiceAssembly() {
        //System.out.println("initInvoiceAssembly()");
        InvoiceAssemblyResultsImpl resultVO = this.getInvoiceAssemblyResults();
        resultVO.executeEmptyRowSet();

        InvoiceAssemblyCriteriaImpl criteriaVO = this.getInvoiceAssemblyCriteria();
        /*
        RowSetIterator critieriaIter = criteriaVO.createRowSetIterator("delete");

        while (critieriaIter.hasNext()) {
            critieriaIter.next().remove();
        }
        critieriaIter.closeRowSetIterator();
        */
        criteriaVO.insertRow(criteriaVO.createRow());
    }

    public void executeInvoiceAssemblySearch() {
        //System.out.println("executeInvoiceAssemblySearch()");
        InvoiceAssemblyResultsImpl resultVO = this.getInvoiceAssemblyResults();
        resultVO.setWhereClause(null);
        resultVO.setNestedSelectForFullSql(false);

        StringBuffer whereClause = new StringBuffer();
        InvoiceAssemblyCriteriaImpl criteriaVO = this.getInvoiceAssemblyCriteria();
        Row[] existingRows = criteriaVO.getAllRowsInRange();
        if (existingRows != null || existingRows.length > 0) {
            InvoiceAssemblyCriteriaRowImpl criteriaRow = (InvoiceAssemblyCriteriaRowImpl)existingRows[0];
            whereClause.append("\n EVT.EVT_CODE = ACT.ACT_EVENT")
                       .append("\n AND  EVT.EVT_CODE= PAR.MTL_EVENT(+)")
                       .append("\n AND EVT.ITS_ISADF='Y'")
                       .append("\n AND EVT.EVT_STATUS = 'C'")
                       .append("\n AND EVT.EVT_COSTCODE= T_TAX_PERC.ACCTNUM(+)")
                        .append("\n AND EVT.EVT_CODE = T_TAX_PERC.EVT_CODE(+)");
            
            if (criteriaRow.getAccountNum() != null) {
                whereClause.append("\n AND  EVT.EVT_COSTCODE = '" + criteriaRow.getAccountNum() + "'");
            }
            if (criteriaRow.getWOType() != null) {
                whereClause.append("\n AND  EVT.EVT_JOBTYPE = '" + criteriaRow.getWOType() + "'");
            }
            if (criteriaRow.getHeaderCd() != null) {
                whereClause.append("\n AND  EVT.EVT_CLASS = '" + criteriaRow.getHeaderCd() + "'");
            }
            if (criteriaRow.getWeekEnding() != null) {
                whereClause.append("\n AND  EVT.EVT_COMPLETED < TO_DATE('" + criteriaRow.getWeekEnding() +
                                   "','rrrr/mm/dd')  + 1");
            }
            resultVO.addWhereClause(whereClause.toString());
           //// System.out.println(resultVO.getQuery());
            resultVO.executeQuery();
        }
    }
    
    public void initInvoiceCredit() {
        try{
        //System.out.println("initInvoiceCredit()");
        InvoiceCreditResultsImpl resultVO = this.getInvoiceCreditResults();
        resultVO.executeEmptyRowSet();

        InvoiceCreditCriteriaImpl criteriaVO = this.getInvoiceCreditCriteria();
        RowSetIterator critieriaIter = criteriaVO.createRowSetIterator("delete");
        while (critieriaIter.hasNext()) {
            critieriaIter.next().remove();
        }
        critieriaIter.closeRowSetIterator();
        criteriaVO.insertRow(criteriaVO.createRow());
        
       // System.out.println("initInvoiceCredit() 2: "+criteriaVO.getEstimatedRowCount());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void executeInvoiceCreditSearch() {
        System.out.println("executeInvoiceCreditSearch()");
        try{
        System.out.println("executeInvoiceCreditSearch() 1");
        InvoiceCreditResultsImpl resultVO = this.getInvoiceCreditResults();
       // System.out.println("Inside INVDataCntrlImpl executeInvoiceCreditSearch() resultVO: "+resultVO.getEstimatedRowCount());
         //   System.out.println("Inside INVDataCntrlImpl executeInvoiceCreditSearch() resultVO Invcr: "+resultVO.getCurrentRow().getAttribute("Invnum"));
        resultVO.setWhereClause(null);
        resultVO.setNestedSelectForFullSql(false);
       // System.out.println("executeInvoiceCreditSearch() 2");
        StringBuffer whereClause = new StringBuffer();
        InvoiceCreditCriteriaImpl criteriaVO = this.getInvoiceCreditCriteria();
       // System.out.println("executeInvoiceCreditSearch() row count: "+criteriaVO.getRowCount()+" criteriaVO.getEstimatedRowCount(): "+criteriaVO.getEstimatedRowCount());
        Row[] existingRows = criteriaVO.getAllRowsInRange();
        if (existingRows != null || existingRows.length > 0) {
            InvoiceCreditCriteriaRowImpl criteriaRow = (InvoiceCreditCriteriaRowImpl)existingRows[0];
            whereClause.append("\n H.INVOICEHEADERID=I.INVOICEHEADERID")
                       .append("\n AND H.STATUS ='ISSUED'")
                       .append("\n AND H.TYPE='STD'");
            
            if (criteriaRow.getRefInvoice() != null) {
                whereClause.append("\n AND H.INVNUM =  '" + criteriaRow.getRefInvoice() + "'");
            }
            resultVO.addWhereClause(whereClause.toString());
           // System.out.println(resultVO.getQuery());
            resultVO.executeQuery();
        }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void initInvoiceRetro() {
        //System.out.println("initInvoiceRetro()");
        InvoiceRetroResultsImpl resultVO = this.getInvoiceRetroResults();
        resultVO.executeEmptyRowSet();

        InvoiceRetroCriteriaImpl criteriaVO = this.getInvoiceRetroCriteria();
        RowSetIterator critieriaIter = criteriaVO.createRowSetIterator("delete");

        while (critieriaIter.hasNext()) {
            critieriaIter.next().remove();
        }
        critieriaIter.closeRowSetIterator();
        criteriaVO.insertRow(criteriaVO.createRow());
    }

    public void executeInvoiceRetroSearch() {
        //System.out.println("executeInvoiceCreditSearch()");
        InvoiceRetroResultsImpl resultVO = this.getInvoiceRetroResults();
        resultVO.setWhereClause(null);
        resultVO.setNestedSelectForFullSql(false);

        StringBuffer whereClause = new StringBuffer();
        InvoiceRetroCriteriaImpl criteriaVO = this.getInvoiceRetroCriteria();
        Row[] existingRows = criteriaVO.getAllRowsInRange();
        if (existingRows != null || existingRows.length > 0) {
            InvoiceRetroCriteriaRowImpl criteriaRow = (InvoiceRetroCriteriaRowImpl)existingRows[0];
            whereClause.append("\n H.INVOICEHEADERID=I.INVOICEHEADERID")
                .append("\n AND I.WO = ACT.ACT_EVENT")
                .append("\n AND ART.ACCOUNTRATEID = A.ACCOUNTRATEID ")
                .append("\n AND ART.TASKID = ITS_TASKID")
                .append("\n AND H.ACCTNUM = A.ACCTNUM")
                .append("\n AND A.ACCOUNTRATEID =  (SELECT MAX(TO_NUMBER(B.ACCOUNTRATEID)) FROM MRLT_ACCOUNTRATE B WHERE I.COMPLETEDT >=  B.EFFECTIVEDT AND B.ACCTNUM=A.ACCTNUM)")
                .append("\n AND H.STATUS ='ISSUED'")
                .append("\n AND H.TYPE = 'STD'")
                .append("\n  AND A.VER > NVL((SELECT RTROVER FROM MRLT_INVOICERTROITEMVW R WHERE R.WO =I.WO  AND R.ACT=ACT.ACT_ACT), I.INVVER)");

            if (criteriaRow.getAccountNum() != null) {
                whereClause.append("\n AND H.ACCTNUM =  '" + criteriaRow.getAccountNum() + "'");
            }
            if (criteriaRow.getStartDt() != null && criteriaRow.getEndDt() != null) {
                whereClause.append("\n AND I.COMPLETEDT BETWEEN TO_DATE('" + criteriaRow.getStartDt() + "','rrrr/mm/dd') AND TO_DATE('" +
                                   criteriaRow.getEndDt() + "','rrrr/mm/dd') + 1");
                
                resultVO.addWhereClause(whereClause.toString());
                //System.out.println(resultVO.getQuery());
                resultVO.executeQuery();
            }
        }
    }
    
    /**
     * This is the default constructor (do not remove).
     */
    public INVDataControlImpl() {
    }


    /**
     * Container's getter for MrltInvoiceheaderView.
     * @return MrltInvoiceheaderView
     */
    public ExtendedViewObjectImpl getMrltInvoiceheaderView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltInvoiceheaderView");
    }

    /**
     * Container's getter for MrltInvoiceitemView1.
     * @return MrltInvoiceitemView1
     */
    public ExtendedViewObjectImpl getMrltInvoiceitemView1() {
        return (ExtendedViewObjectImpl)findViewObject("MrltInvoiceitemView1");
    }


    /**
     * Container's getter for MrltInvoicecritemView.
     * @return MrltInvoicecritemView
     */
    public ExtendedViewObjectImpl getMrltInvoicecritemView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltInvoicecritemView");
    }

    /**
     * Container's getter for MrltInvoiceitemView.
     * @return MrltInvoiceitemView
     */
    public ExtendedViewObjectImpl getMrltInvoiceitemView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltInvoiceitemView");
    }

    /**
     * Container's getter for MrltInvoicertroitemView.
     * @return MrltInvoicertroitemView
     */
    public MrltInvoicertroitemViewImpl getMrltInvoicertroitemView() {
        return (MrltInvoicertroitemViewImpl)findViewObject("MrltInvoicertroitemView");
    }


    /**
     * Container's getter for InvoiceAssemblyCriteria.
     * @return InvoiceAssemblyCriteria
     */
    public InvoiceAssemblyCriteriaImpl getInvoiceAssemblyCriteria() {
        return (InvoiceAssemblyCriteriaImpl)findViewObject("InvoiceAssemblyCriteria");
    }

    /**
     * Container's getter for InvoiceAssemblyResults.
     * @return InvoiceAssemblyResults
     */
    public InvoiceAssemblyResultsImpl getInvoiceAssemblyResults() {
        return (InvoiceAssemblyResultsImpl)findViewObject("InvoiceAssemblyResults");
    }

    /**
     * Container's getter for MrltInvoicecritemFk1Link1.
     * @return MrltInvoicecritemFk1Link1
     */
    public ViewLinkImpl getMrltInvoicecritemFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltInvoicecritemFk1Link1");
    }

    /**
     * Container's getter for MrltInvoiceitemFk1Link1.
     * @return MrltInvoiceitemFk1Link1
     */
    public ViewLinkImpl getMrltInvoiceitemFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltInvoiceitemFk1Link1");
    }

    /**
     * Container's getter for MrltInvoicertroitemFk1Link1.
     * @return MrltInvoicertroitemFk1Link1
     */
    public ViewLinkImpl getMrltInvoicertroitemFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltInvoicertroitemFk1Link1");
    }

    /**
     * Container's getter for MrltInvoiceheaderView1.
     * @return MrltInvoiceheaderView1
     */
    public MrltInvoiceheaderViewImpl getMrltInvoiceheaderView1() {
        return (MrltInvoiceheaderViewImpl)findViewObject("MrltInvoiceheaderView1");
    }

    /**
     * Container's getter for InvoiceCreditCriteria.
     * @return InvoiceCreditCriteria
     */
    public InvoiceCreditCriteriaImpl getInvoiceCreditCriteria() {
        return (InvoiceCreditCriteriaImpl)findViewObject("InvoiceCreditCriteria");
    }

    /**
     * Container's getter for InvoiceCreditResults.
     * @return InvoiceCreditResults
     */
    public InvoiceCreditResultsImpl getInvoiceCreditResults() {
        return (InvoiceCreditResultsImpl)findViewObject("InvoiceCreditResults");
    }

    /**
     * Container's getter for InvoiceRetroCriteria.
     * @return InvoiceRetroCriteria
     */
    public InvoiceRetroCriteriaImpl getInvoiceRetroCriteria() {
        return (InvoiceRetroCriteriaImpl)findViewObject("InvoiceRetroCriteria");
    }

    /**
     * Container's getter for InvoiceRetroResults.
     * @return InvoiceRetroResults
     */
    public InvoiceRetroResultsImpl getInvoiceRetroResults() {
        return (InvoiceRetroResultsImpl)findViewObject("InvoiceRetroResults");
    }
}
