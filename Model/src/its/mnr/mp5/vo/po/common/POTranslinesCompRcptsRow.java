package its.mnr.mp5.vo.po.common;

import oracle.jbo.Row;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Aug 04 14:11:34 PDT 2014
// ---------------------------------------------------------------------
public interface POTranslinesCompRcptsRow extends Row {
    boolean addPartToStock(String pPart, String pPartOrg, String pStore, Double pQty);

    boolean isPartInCat(String pPart, String pPartOrg);

    String getDefaultBinLot();
}
