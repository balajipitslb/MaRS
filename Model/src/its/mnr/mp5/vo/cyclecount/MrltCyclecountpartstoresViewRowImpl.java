package its.mnr.mp5.vo.cyclecount;

import its.mnr.mp5.baseclass.ExtendedEntityImpl;
import its.mnr.mp5.baseclass.ExtendedViewRowImpl;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Char;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Feb 28 16:46:05 PST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class MrltCyclecountpartstoresViewRowImpl extends ExtendedViewRowImpl
{


  public static final int ENTITY_MRLTCYCLECOUNTPARTSTORES = 0;

  /**
   * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
   */
    public enum AttributesEnum {
    Listid
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.getListid();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.setListid((Integer)value);
      }
    }
    ,
    Srcd
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.getSrcd();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.setSrcd((String)value);
      }
    }
    ,
    Used
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.getUsed();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.setUsed((String)value);
      }
    }
    ,
    Ccpsid
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.getCcpsid();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.setCcpsid((Number)value);
      }
    }
    ,
    tUsedCnt
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.gettUsedCnt();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.settUsedCnt((String)value);
      }
    }
    ,
    tSelected
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.gettSelected();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.settSelected((Boolean)value);
      }
    }
    ,
    MrltCyclecountheaderView
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.getMrltCyclecountheaderView();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.setAttributeInternal(index(), value);
      }
    }
    ,
    MrltCyclecountpartsView
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.getMrltCyclecountpartsView();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.setAttributeInternal(index(), value);
      }
    }
    ,
    MrlrefCyclecountstoreView
    {
      public Object get(MrltCyclecountpartstoresViewRowImpl obj)
      {
        return obj.getMrlrefCyclecountstoreView();
      }

      public void put(MrltCyclecountpartstoresViewRowImpl obj, Object value)
      {
        obj.setAttributeInternal(index(), value);
      }
    }
    ;
    private static AttributesEnum[] vals = null;
    private static int firstIndex = 0;

        public abstract Object get(MrltCyclecountpartstoresViewRowImpl object);

        public abstract void put(MrltCyclecountpartstoresViewRowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


  public static final int LISTID = AttributesEnum.Listid.index();
  public static final int SRCD = AttributesEnum.Srcd.index();
  public static final int USED = AttributesEnum.Used.index();
  public static final int CCPSID = AttributesEnum.Ccpsid.index();
  public static final int TUSEDCNT = AttributesEnum.tUsedCnt.index();
  public static final int TSELECTED = AttributesEnum.tSelected.index();
  public static final int MRLTCYCLECOUNTHEADERVIEW = AttributesEnum.MrltCyclecountheaderView.index();
  public static final int MRLTCYCLECOUNTPARTSVIEW = AttributesEnum.MrltCyclecountpartsView.index();
  public static final int MRLREFCYCLECOUNTSTOREVIEW = AttributesEnum.MrlrefCyclecountstoreView.index();

  /**
   * This is the default constructor (do not remove).
   */
    public MrltCyclecountpartstoresViewRowImpl() {
    }

    /**
     * Gets MrltCyclecountpartstores entity object.
     * @return the MrltCyclecountpartstores
     */
    public ExtendedEntityImpl getMrltCyclecountpartstores() {
        return (ExtendedEntityImpl)getEntity(ENTITY_MRLTCYCLECOUNTPARTSTORES);
    }

    /**
     * Gets the attribute value for LISTID using the alias name Listid.
     * @return the LISTID
     */
    public Integer getListid() {
        return (Integer) getAttributeInternal(LISTID);
    }

    /**
     * Sets <code>value</code> as attribute value for LISTID using the alias name Listid.
     * @param value value to set the LISTID
     */
    public void setListid(Integer value) {
        setAttributeInternal(LISTID, value);
    }

    /**
     * Gets the attribute value for SRCD using the alias name Srcd.
     * @return the SRCD
     */
    public String getSrcd() {
        return (String) getAttributeInternal(SRCD);
    }

    /**
     * Sets <code>value</code> as attribute value for SRCD using the alias name Srcd.
     * @param value value to set the SRCD
     */
    public void setSrcd(String value) {
        setAttributeInternal(SRCD, value);
    }

    /**
     * Gets the attribute value for USED using the alias name Used.
     * @return the USED
     */
    public String getUsed() {
        return (String)getAttributeInternal(USED);
    }

    /**
     * Sets <code>value</code> as attribute value for USED using the alias name Used.
     * @param value value to set the USED
     */
    public void setUsed(String value) {
        setAttributeInternal(USED, value);
    }

    /**
     * Gets the attribute value for CCPSID using the alias name Ccpsid.
     * @return the CCPSID
     */
    public oracle.jbo.domain.Number getCcpsid() {
        return (oracle.jbo.domain.Number) getAttributeInternal(CCPSID);
    }

    /**
     * Sets <code>value</code> as attribute value for CCPSID using the alias name Ccpsid.
     * @param value value to set the CCPSID
     */
    public void setCcpsid(oracle.jbo.domain.Number value) {
        setAttributeInternal(CCPSID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute tUsedCnt.
     * @return the tUsedCnt
     */
    public String gettUsedCnt() {
        return (String) getAttributeInternal(TUSEDCNT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute tUsedCnt.
     * @param value value to set the  tUsedCnt
     */
    public void settUsedCnt(String value) {
        setAttributeInternal(TUSEDCNT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute tSelected.
     * @return the tSelected
     */
    public Boolean gettSelected() {
        return (Boolean) getAttributeInternal(TSELECTED);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute tSelected.
     * @param value value to set the  tSelected
     */
    public void settSelected(Boolean value) {
        //System.out.println("Setting Used value:" + value);
        String c = value == true ? "Y" : "N";
        setAttributeInternal(USED, c);
     
        setAttributeInternal(TSELECTED, value);
    }

    /**
     * Gets the associated <code>Row</code> using master-detail link MrltCyclecountheaderView.
     */
    public Row getMrltCyclecountheaderView() {
        return (Row)getAttributeInternal(MRLTCYCLECOUNTHEADERVIEW);
    }

    /**
     * Sets the master-detail link MrltCyclecountheaderView between this object and <code>value</code>.
     */
    public void setMrltCyclecountheaderView(Row value) {
        setAttributeInternal(MRLTCYCLECOUNTHEADERVIEW, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link MrltCyclecountpartsView.
     */
    public RowIterator getMrltCyclecountpartsView() {
        return (RowIterator)getAttributeInternal(MRLTCYCLECOUNTPARTSVIEW);
    }


  /**
   * Gets the view accessor <code>RowSet</code> MrlrefCyclecountstoreView.
   */
    public RowSet getMrlrefCyclecountstoreView() {
        return (RowSet)getAttributeInternal(MRLREFCYCLECOUNTSTOREVIEW);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
    if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count()))
    {
      return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
    }
    return super.getAttrInvokeAccessor(index, attrDef);
  }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
    if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count()))
    {
      AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
      return;
    }
    super.setAttrInvokeAccessor(index, value, attrDef);
  }
}
