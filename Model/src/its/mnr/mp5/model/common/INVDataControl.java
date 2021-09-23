package its.mnr.mp5.model.common;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 03 14:12:46 PDT 2012
// ---------------------------------------------------------------------
public interface INVDataControl extends ApplicationModule {
    void executeInvoiceAssemblySearch();

    void initInvoiceAssembly();

    void executeInvoiceCreditSearch();

    void initInvoiceCredit();

    void executeInvoiceRetroSearch();

    void initInvoiceRetro();


    void deselectAllInvs();

    void selectAllInvs();

    void selectAllUnprocessedInvs();

    String removeProcessed();
}
