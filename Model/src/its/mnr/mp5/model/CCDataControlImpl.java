package its.mnr.mp5.model;

import its.mnr.mp5.baseclass.ExtendedApplicationModuleImpl;
import its.mnr.mp5.baseclass.ExtendedViewObjectImpl;
import its.mnr.mp5.eo.local.R5activitiesImpl;
import its.mnr.mp5.model.common.CCDataControl;
import its.mnr.mp5.vo.cyclecount.CycleCountPartsImpl;
import its.mnr.mp5.vo.cyclecount.CycleCountPartsMainImpl;
import its.mnr.mp5.vo.cyclecount.CycleCountPartsMain_distBinViewImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountheaderViewImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountheaderViewRowImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountpartsMainStoreImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountpartsViewImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountpartsViewRowImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountpartstoresViewImpl;
import its.mnr.mp5.vo.cyclecount.MrltCyclecountpartstoresViewRowImpl;
import its.mnr.mp5.vo.cyclecount.MrltFifoheaderViewImpl;
import its.mnr.mp5.vo.invoice.InvoiceAssemblyCriteriaImpl;
import its.mnr.mp5.vo.invoice.InvoiceAssemblyCriteriaRowImpl;
import its.mnr.mp5.vo.invoice.InvoiceAssemblyResultsImpl;

import its.mnr.mp5.vo.invoice.MrltInvoiceheaderViewRowImpl;

import its.mnr.mp5.vo.parts.PartsMaintViewRowImpl;

import java.util.ArrayList;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Feb 20 10:23:41 PST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CCDataControlImpl extends ExtendedApplicationModuleImpl implements CCDataControl {
    public void initCycleCountParts() {
        //System.out.println("initCycleCountParts()");
        CycleCountPartsImpl resultVO = (CycleCountPartsImpl)this.getCycleCountParts();
        resultVO.executeEmptyRowSet();
    }

    public Integer executeCycleCountParts(Integer sListid) {
        Integer retInt = 0;
       // System.out.println("executeCycleCountParts");
        CycleCountPartsImpl resultVO = (CycleCountPartsImpl)this.getCycleCountParts();
        resultVO.setWhereClause(null);
        resultVO.setNestedSelectForFullSql(false);

        StringBuffer whereClause = new StringBuffer();
       // MrltCyclecountheaderViewImpl criteriaVO = (MrltCyclecountheaderViewImpl)this.getMrltCyclecountheaderView();
        ViewObject vo = this.getMrltCyclecountheaderView();
        
        Key voKey = new Key(new Object[] { sListid });
        Row[] hdrFound = vo.findByKey(voKey, 1);
        
            if (hdrFound != null && hdrFound.length > 0) {
                //System.out.println("Criteria Found");
                Row rw = hdrFound[0];
                
            String sRangeType = (String)rw.getAttribute("RangeType");
            String sBin = (String)rw.getAttribute("Bin");
            String sBinStart = (String)rw.getAttribute("Binstart");
            String sBinEnd = (String)rw.getAttribute("Binend");
            String sPartClass = (String)rw.getAttribute("Partclass");
            String sPartStart = (String)rw.getAttribute("Partstart");
            String sPartEnd = (String)rw.getAttribute("Partend");
            String sP1 = (String)rw.getAttribute("P1");
            String sP2 = (String)rw.getAttribute("P2");
            String sP3 = (String)rw.getAttribute("P3");
            String sP4 = (String)rw.getAttribute("P4");
            String sP5 = (String)rw.getAttribute("P5");
            String sP6 = (String)rw.getAttribute("P6");
                                                               
//            System.out.println("getListid(): " + rw.getAttribute("Listid"));
//            
//            System.out.println("executeCycleCountParts sRangeType: " + sRangeType);
//            System.out.println("getBinstart(): " + sBinStart);
//            System.out.println("getBinend(): " + sBinEnd);
    
            whereClause.append("\n PAR_CODE = BIS_PART AND PAR_ORG = BIS_PART_ORG AND PAR_NOTUSED='-'");
            if ("Bin #".equals(sRangeType) && sBin != null) {
                whereClause.append("\n  AND BIS_BIN = '" + sBin + "'");
            }            
            if ("Bin Range".equals(sRangeType) && sBinStart != null && sBinEnd != null) {
                whereClause.append("\n  AND (BIS_BIN >= '" + sBinStart +  "'  AND BIS_BIN <= '"  + sBinEnd + "')");
            }
            if ("Part Class".equals(sRangeType) && sPartClass != null) {
                whereClause.append("\n AND  PAR_CLASS = '" + sPartClass + "'");
            }
            if ("Part Range".equals(sRangeType) && sPartStart != null && sPartEnd != null) {
                whereClause.append("\n AND  (PAR_CODE >= '" + sPartStart +  "'  AND PAR_CODE <= '"  + sPartEnd + "')");
            }
            if ("Part #".equals(sRangeType) 
                && (sP1 != null || sP2 != null || sP3 != null
                || sP4 != null || sP5 != null || sP6 != null)) {
                whereClause.append("\n AND  PAR_CODE IN ('" + sP1 + "','" 
                                                            + sP2 + "','"
                                                            + sP3 + "','"
                                                            + sP4 + "','"
                                                            + sP5 + "','"
                                                            + sP6 + "')");
            }
            
            resultVO.addWhereClause(whereClause.toString());
            //System.out.println(resultVO.getQuery());
            resultVO.executeQuery();
            Long res = resultVO.getEstimatedRowCount();
            retInt = res.intValue();
            //System.out.println("resultVO.getEstimatedRowCount():" + resultVO.getEstimatedRowCount());
        }
        return retInt;
    }
    
    
    public void executeCycleCountPartsOld(String sRangeType) {
        //System.out.println("executeCycleCountParts");
        CycleCountPartsImpl resultVO = (CycleCountPartsImpl)this.getCycleCountParts();
        resultVO.setWhereClause(null);
        resultVO.setNestedSelectForFullSql(false);

        StringBuffer whereClause = new StringBuffer();
        MrltCyclecountheaderViewImpl criteriaVO = (MrltCyclecountheaderViewImpl)this.getMrltCyclecountheaderView();
        Row[] existingRows = criteriaVO.getAllRowsInRange();
        
        if (existingRows != null || existingRows.length > 0) {
            MrltCyclecountheaderViewRowImpl criteriaRow = (MrltCyclecountheaderViewRowImpl)existingRows[0];
            //String sRangeType = criteriaRow.getRangeType();
            //System.out.println("criteriaRow.getListid(): " + criteriaRow.getListid());
            
           // System.out.println("executeCycleCountParts sRangeType: " + sRangeType);
           // System.out.println("criteriaRow.getBinstart(): " + criteriaRow.getBinstart());
           // System.out.println("criteriaRow.getBinend(): " + criteriaRow.getBinend());
    
            whereClause.append("\n PAR_CODE = BIS_PART AND PAR_ORG = BIS_PART_ORG AND PAR_NOTUSED='-'");
            if ("Bin #".equals(sRangeType) &&  criteriaRow.getBin()!=null) {
                whereClause.append("\n  AND BIS_BIN = '" + criteriaRow.getBin() + "'");
            }             
            if ("Bin Range".equals(sRangeType) && criteriaRow.getBinstart() != null && criteriaRow.getBinend() != null) {
                whereClause.append("\n  AND (BIS_BIN > '" + criteriaRow.getBinstart() +  "'  AND BIS_BIN < '"  + criteriaRow.getBinend() + "')");
            }
            if ("Part Class".equals(sRangeType) && criteriaRow.getPartclass() != null) {
                whereClause.append("\n AND  PAR_CLASS = '" + criteriaRow.getPartclass() + "'");
            }
            if ("Part Range".equals(sRangeType) && criteriaRow.getPartstart() != null && criteriaRow.getPartend() != null) {
                whereClause.append("\n AND  (PAR_CODE >= '" + criteriaRow.getPartstart() +  "'  AND PAR_CODE <= '"  + criteriaRow.getPartend() + "')");
            }
            if ("Part #".equals(sRangeType) 
                /*&& (criteriaRow.getP1() != null || criteriaRow.getP2() != null || criteriaRow.getP3() != null
                || criteriaRow.getP5() != null || criteriaRow.getP5() != null || criteriaRow.getP6() != null)*/) {
                whereClause.append("\n AND  PAR_CODE IN ('" + criteriaRow.getP1() + "','" 
                                                            + criteriaRow.getP2() + "','"
                                                            + criteriaRow.getP3() + "','"
                                                            + criteriaRow.getP4() + "','"
                                                            + criteriaRow.getP5() + "','"
                                                            + criteriaRow.getP6() + "')");
            }
            //if ("Part #".equals(sRangeType) ){ System.out.println("equals Part #");}
            resultVO.addWhereClause(whereClause.toString());
            //System.out.println(resultVO.getQuery());
            resultVO.executeQuery();
        }
    }
    public boolean createCCList(Integer iListId, String sTrans, String sStoreroom){
        //System.out.println("createCCList :" + iListId + " " + sTrans + " " + sStoreroom);

        Boolean ret = true;
        //Integer cnt = 0;
        RowSetIterator iterCCP = this.getCycleCountParts();
        //iterCCP.first();
        ViewObjectImpl vo = this.getMrltCyclecountpartsView();
        // execute query to make sure we get latest data!
        while (iterCCP.hasNext()) {
            //System.out.println("iterCCP.hasNext()");
            Row row = iterCCP.next();
            //System.out.println(cnt++);
            MrltCyclecountpartsViewRowImpl rowImpl = (MrltCyclecountpartsViewRowImpl)vo.createRow();
            //System.out.println("Create List for Part: " + (String)row.getAttribute("ParCode"));
            
            rowImpl.setStlListid(iListId); 
            rowImpl.setStlTrans(sTrans); 
            rowImpl.setStlPart((String)row.getAttribute("ParCode"));
            rowImpl.setStlPartOrg((String)row.getAttribute("ParOrg"));
            rowImpl.setStlBin((String)row.getAttribute("BisBin"));
            rowImpl.setStlLot((String)row.getAttribute("BisLot"));
            rowImpl.setStlStore((String)row.getAttribute("BisStore"));
            rowImpl.setStlExpqty((Number)row.getAttribute("BisQty"));
            rowImpl.setStlType(sStoreroom);
            
            vo.insertRow(rowImpl);
            //update Parts.its_cyclent flag
            PartsMaintViewRowImpl rowImplParts = (PartsMaintViewRowImpl)rowImpl.getPartsMaintView();
            rowImplParts.setItsCyclecnt("Y");
            //System.out.println("getItsCyclecnt()" + rowImplParts.getParCode() + " : " + (String)rowImplParts.getItsCyclecnt());
        }
        this.getDBTransaction().commit();
        return ret;
        }
    
    public boolean createCCPartStores(Integer iListId, ArrayList<String> sStoreroom){
        //System.out.println("createCCPartStores dataControl");

        Boolean ret = true;
        // execute query to make sure we get latest data!
        ViewObjectImpl vo = this.getMrltCyclecountpartstoresView();
        for (String item : sStoreroom) {
            //MrltCyclecountpartstoresViewRowImpl rowImpl = (MrltCyclecountpartstoresViewRowImpl)vo.createRow();
            //System.out.println("Storeroom" + item);
            
//            rowImpl.setListid(iListId); 
//            rowImpl.setSrcd(item);
//            
//            vo.insertRow(rowImpl);
        }
        this.getDBTransaction().commit();
        return ret;
        }
    
    /**
     * This is the default constructor (do not remove).
     */
    public CCDataControlImpl() {
    }

    /**
     * Container's getter for MrltCyclecountheaderView.
     * @return MrltCyclecountheaderView
     */
    public MrltCyclecountheaderViewImpl getMrltCyclecountheaderView() {
        return (MrltCyclecountheaderViewImpl)findViewObject("MrltCyclecountheaderView");
    }

    /**
     * Container's getter for MrltCyclecountView.
     * @return MrltCyclecountView
     */
    public ExtendedViewObjectImpl getMrltCyclecountView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltCyclecountView");
    }


    /**
     * Container's getter for CycleCountParts.
     * @return CycleCountParts
     */
    public CycleCountPartsImpl getCycleCountParts() {
        return (CycleCountPartsImpl)findViewObject("CycleCountParts");
    }


    /**
     * Container's getter for RemainingCycleCountStoreLOV.
     * @return RemainingCycleCountStoreLOV
     */
    public ExtendedViewObjectImpl getRemainingCycleCountStoreLOV() {
        return (ExtendedViewObjectImpl)findViewObject("RemainingCycleCountStoreLOV");
    }

    /**
     * Container's getter for MrlrefCyclecountstoreView.
     * @return MrlrefCyclecountstoreView
     */
    public ExtendedViewObjectImpl getMrlrefCyclecountstoreView() {
        return (ExtendedViewObjectImpl)findViewObject("MrlrefCyclecountstoreView");
    }


    /**
     * Container's getter for MrltCyclecountpartsMainStore.
     * @return MrltCyclecountpartsMainStore
     */
    public ExtendedViewObjectImpl getMrltCyclecountpartsMainStore() {
        return (ExtendedViewObjectImpl)findViewObject("MrltCyclecountpartsMainStore");
    }

    /**
     * Container's getter for CCHdrCCPartsMainLink1.
     * @return CCHdrCCPartsMainLink1
     */
    public ViewLinkImpl getCCHdrCCPartsMainLink1() {
        return (ViewLinkImpl)findViewLink("CCHdrCCPartsMainLink1");
    }


    /**
     * Container's getter for MrltCyclecountpartsView.
     * @return MrltCyclecountpartsView
     */
    public MrltCyclecountpartsViewImpl getMrltCyclecountpartsView() {
        return (MrltCyclecountpartsViewImpl) findViewObject("MrltCyclecountpartsView");
    }

    /**
     * Container's getter for CCHeaderCCPartsLink2.
     * @return CCHeaderCCPartsLink2
     */
    public ViewLinkImpl getCCHeaderCCPartsLink2() {
        return (ViewLinkImpl)findViewLink("CCHeaderCCPartsLink2");
    }

    /**
     * Container's getter for MrltCyclecountpartstoresView.
     * @return MrltCyclecountpartstoresView
     */
    public MrltCyclecountpartstoresViewImpl getMrltCyclecountpartstoresView() {
        return (MrltCyclecountpartstoresViewImpl)findViewObject("MrltCyclecountpartstoresView");
    }

    /**
     * Container's getter for CCHrdCCPartStoresLink1.
     * @return CCHrdCCPartStoresLink1
     */
    public ViewLinkImpl getCCHrdCCPartStoresLink1() {
        return (ViewLinkImpl)findViewLink("CCHrdCCPartStoresLink1");
    }


    /**
     * Container's getter for MrltFifopartxheaderView.
     * @return MrltFifopartxheaderView
     */
    public ExtendedViewObjectImpl getMrltFifopartxheaderView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFifopartxheaderView");
    }

    /**
     * Container's getter for MrltFifopartxView.
     * @return MrltFifopartxView
     */
    public ExtendedViewObjectImpl getMrltFifopartxView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFifopartxView");
    }

    /**
     * Container's getter for MrltFifopartxFk1Link1.
     * @return MrltFifopartxFk1Link1
     */
    public ViewLinkImpl getMrltFifopartxFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltFifopartxFk1Link1");
    }

    /**
     * Container's getter for MrltFifoheaderView1.
     * @return MrltFifoheaderView1
     */
    public ExtendedViewObjectImpl getMrltFifoheaderView1() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFifoheaderView1");
    }

    /**
     * Container's getter for MrltFifoheaderFk2Link1.
     * @return MrltFifoheaderFk2Link1
     */
    public ViewLinkImpl getMrltFifoheaderFk2Link1() {
        return (ViewLinkImpl)findViewLink("MrltFifoheaderFk2Link1");
    }

    /**
     * Container's getter for MrltFifoheaderView.
     * @return MrltFifoheaderView
     */
    public ExtendedViewObjectImpl getMrltFifoheaderView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFifoheaderView");
    }

    /**
     * Container's getter for CCHeaderFifoHeaderLink1.
     * @return CCHeaderFifoHeaderLink1
     */
    public ViewLinkImpl getCCHeaderFifoHeaderLink1() {
        return (ViewLinkImpl)findViewLink("CCHeaderFifoHeaderLink1");
    }

    /**
     * Container's getter for MrltFifoitemView.
     * @return MrltFifoitemView
     */
    public ExtendedViewObjectImpl getMrltFifoitemView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFifoitemView");
    }

    /**
     * Container's getter for MrltFifoitemFk1Link1.
     * @return MrltFifoitemFk1Link1
     */
    public ViewLinkImpl getMrltFifoitemFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltFifoitemFk1Link1");
    }

    /**
     * Container's getter for MrltFifoadjitemView.
     * @return MrltFifoadjitemView
     */
    public ExtendedViewObjectImpl getMrltFifoadjitemView() {
        return (ExtendedViewObjectImpl)findViewObject("MrltFifoadjitemView");
    }

    /**
     * Container's getter for MrltFifoadjitemFk1Link1.
     * @return MrltFifoadjitemFk1Link1
     */
    public ViewLinkImpl getMrltFifoadjitemFk1Link1() {
        return (ViewLinkImpl)findViewLink("MrltFifoadjitemFk1Link1");
    }


    /**
     * Container's getter for CycleCountPartsMain.
     * @return CycleCountPartsMain
     */
    public ExtendedViewObjectImpl getCycleCountPartsMain() {
        return (ExtendedViewObjectImpl)findViewObject("CycleCountPartsMain");
    }

    /**
     * Container's getter for CCHdrCCPartsMainLink2.
     * @return CCHdrCCPartsMainLink2
     */
    public ViewLinkImpl getCCHdrCCPartsMainLink2() {
        return (ViewLinkImpl)findViewLink("CCHdrCCPartsMainLink2");
    }

    /**
     * Container's getter for CycleCountPartsMainSelected.
     * @return CycleCountPartsMainSelected
     */
    public ExtendedViewObjectImpl getCycleCountPartsMainSelected() {
        return (ExtendedViewObjectImpl)findViewObject("CycleCountPartsMainSelected");
    }

    /**
     * Container's getter for CCHdrCCPartsMainLink2B.
     * @return CCHdrCCPartsMainLink2B
     */
    public ViewLinkImpl getCCHdrCCPartsMainLink2B() {
        return (ViewLinkImpl)findViewLink("CCHdrCCPartsMainLink2B");
    }


  /**
   * Container's getter for CycleCountPartsMain_distBinView2.
   * @return CycleCountPartsMain_distBinView2
   */
  public CycleCountPartsMain_distBinViewImpl getCycleCountPartsMain_distBinView1()
  {
    return (CycleCountPartsMain_distBinViewImpl) findViewObject("CycleCountPartsMain_distBinView1");
  }

  /**
   * Container's getter for CCHdrCCPartsMain_distBinLink2_1.
   * @return CCHdrCCPartsMain_distBinLink2_1
   */
  public ViewLinkImpl getCCHdrCCPartsMain_distBinLink2_1()
  {
    return (ViewLinkImpl) findViewLink("CCHdrCCPartsMain_distBinLink2_1");
  }


    /**
     * Container's getter for CCPartVO1.
     * @return CCPartVO1
     */
    public ExtendedViewObjectImpl getCCPartVO() {
        return (ExtendedViewObjectImpl) findViewObject("CCPartVO");
    }

    /**
     * Container's getter for CCPartVL1.
     * @return CCPartVL1
     */
    public ViewLinkImpl getCCPartVL() {
        return (ViewLinkImpl) findViewLink("CCPartVL");
    }

    /**
     * Container's getter for MrltFifoPartx_partView1.
     * @return MrltFifoPartx_partView1
     */
    public ExtendedViewObjectImpl getMrltFifoPartx_partView1() {
        return (ExtendedViewObjectImpl) findViewObject("MrltFifoPartx_partView1");
    }

    /**
     * Container's getter for MrltFifoHeaderx_partxVL1.
     * @return MrltFifoHeaderx_partxVL1
     */
    public ViewLinkImpl getMrltFifoHeaderx_partxVL1() {
        return (ViewLinkImpl) findViewLink("MrltFifoHeaderx_partxVL1");
    }
}
