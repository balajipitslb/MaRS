package its.mnr.mp5.vo.asset.client;

import its.mnr.mp5.vo.asset.common.AssetReading;

import oracle.jbo.client.remote.ViewUsageImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Aug 30 10:55:14 PDT 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AssetReadingClient extends ViewUsageImpl implements AssetReading {
    /**
     * This is the default constructor (do not remove).
     */
    public AssetReadingClient() {
    }

    public String getAssetStatusFnc(String PASSET, String PORG) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"getAssetStatusFnc",new String [] {"java.lang.String","java.lang.String"},new Object[] {PASSET, PORG});
        return (String)_ret;
    }
}
