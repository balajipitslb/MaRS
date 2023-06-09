package its.mnr.mp5.vo.invoice;

import its.mnr.mp5.baseclass.ExtendedViewObjectImpl;
import oracle.jbo.domain.Number;
import oracle.jbo.RowIterator;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 24 14:20:33 PDT 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltInvoicertroitemViewImpl extends ExtendedViewObjectImpl {
    public boolean createInvoiceRtroItem(Number pItemId, Number pHeaderId, Number pInvoice, String pWO, Number pAct,String pOldVer,
                                         String pNewVer, String pOldARTaskId, String pNewARTaskId, Double pOldRate,
                                         Double pNewRate, String pShift, Double pHrs, String pOldRateType,
                                         String pNewRateType, String pEquip, String pTask, String pCompCd,
                                         String pDamCd, String pDamDesc, String pLocCd,
                                         String pLocDesc, String pRepCd, String pRepDesc, String pHdrCode) {
        //System.out.println("inside MrltInvoiceitemViewImpl:createInvoiceItem()");
        Boolean ret = true;
        //DBTransaction trans = getDBTransaction();
        //Number ntrlacd = getValueFromSequence("S5ACCOUNTDETAIL", trans);

        RowIterator iter = this.getViewObject();
        MrltInvoicertroitemViewRowImpl rowImpl = (MrltInvoicertroitemViewRowImpl)iter.createRow();
        iter.insertRow(rowImpl);

        rowImpl.setInvoicertroitemid(pItemId);
        rowImpl.setInvoiceheaderid(pHeaderId);
        rowImpl.setWo(pWO);
        rowImpl.setAct(pAct);
        rowImpl.setOldver(pOldVer);
        rowImpl.setNewver(pNewVer);
        rowImpl.setInvoice(pInvoice);
        rowImpl.setOldaccountratetaskid(pOldARTaskId);
        rowImpl.setNewaccountratetaskid(pNewARTaskId);
        rowImpl.setOldrate(pOldRate);
        rowImpl.setNewrate(pNewRate);
        rowImpl.setShift(pShift);
        rowImpl.setLbrhrs(pHrs);
        rowImpl.setOldratetype(pOldRateType);
        rowImpl.setNewratetype(pNewRateType);
        rowImpl.setEquipcharge(pEquip);
        rowImpl.setTask(pTask);
        rowImpl.setCompcd(pCompCd);
        rowImpl.setDamcd(pDamCd);
        rowImpl.setDamdesc(pDamDesc);
        rowImpl.setLoccd(pLocCd);
        rowImpl.setLocdesc(pLocDesc);
        rowImpl.setRepcd(pRepCd);
        rowImpl.setReppdesc(pRepDesc);
        rowImpl.setHdrcode(pHdrCode);

        return ret;
    }
    /**
     * This is the default constructor (do not remove).
     */
    public MrltInvoicertroitemViewImpl() {
    }
}
