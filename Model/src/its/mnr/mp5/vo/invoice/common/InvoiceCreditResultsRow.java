package its.mnr.mp5.vo.invoice.common;

import oracle.jbo.Row;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Aug 16 17:25:10 PDT 2012
// ---------------------------------------------------------------------
public interface InvoiceCreditResultsRow extends Row {

    oracle.jbo.domain.Number createInvoiceHeader();

    oracle.jbo.domain.Number getInvoiceNum(oracle.jbo.domain.Number pHeaderId);

    boolean createInvoiceCrItem(oracle.jbo.domain.Number pHeaderId, String pWO, Double pLabor, Double pPart,
                                Double pTax, Double pTaxPer, Double pLaborCr, Double pPartCr, Double pTaxCr,
                                Double pTotal, String pHdrCode);
}
