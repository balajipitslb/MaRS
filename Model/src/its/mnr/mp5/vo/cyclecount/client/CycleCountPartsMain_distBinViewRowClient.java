package its.mnr.mp5.vo.cyclecount.client;

import its.mnr.mp5.vo.cyclecount.common.CycleCountPartsMain_distBinViewRow;

import oracle.jbo.client.remote.RowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Dec 21 10:46:37 PST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CycleCountPartsMain_distBinViewRowClient
  extends RowImpl
  implements CycleCountPartsMain_distBinViewRow
{
  /**
   * This is the default constructor (do not remove).
   */
  public CycleCountPartsMain_distBinViewRowClient()
  {
  }


  public String writeCCStoreList(String pList, String pType, String pBin)
  {
    Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"writeCCStoreList",new String [] {"java.lang.String","java.lang.String","java.lang.String"},new Object[] {pList, pType, pBin});
    return (String) _ret;
  }

  public String getStlBin()
  {
    return (String) getAttribute("StlBin");
  }

  public Integer getStlListid()
  {
    return (Integer) getAttribute("StlListid");
  }

  public void setStlBin(String value)
  {
    setAttribute("StlBin", value);
  }

  public void setStlListid(Integer value)
  {
    setAttribute("StlListid", value);
  }
}
