package its.mnr.mp5.vo.cyclecount.common;

import oracle.jbo.Row;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Dec 21 10:46:37 PST 2016
// ---------------------------------------------------------------------
public interface CycleCountPartsMain_distBinViewRow
  extends Row
{
  String getStlBin();

  void setStlBin(String value);

  Integer getStlListid();

  void setStlListid(Integer value);

  String writeCCStoreList(String pList, String pType, String pBin);
}
