package its.mnr.mp5.model.client;

import its.mnr.mp5.model.common.PORecvDataControl;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jan 20 15:05:31 PST 2014
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PORecvDataControlClient extends ApplicationModuleImpl implements PORecvDataControl {
    /**
     * This is the default constructor (do not remove).
     */
    public PORecvDataControlClient() {
    }


    public void clearFullyRecd() {
        Object _ret = this.riInvokeExportedMethod(this, "clearFullyRecd", null, null);
        return;
    }

    public void clearFullyRetn() {
        Object _ret = this.riInvokeExportedMethod(this, "clearFullyRetn", null, null);
        return;
    }

    public void initCompanyRcpts() {
        Object _ret = this.riInvokeExportedMethod(this, "initCompanyRcpts", null, null);
        return;
    }

    public boolean isPartInactive(String sPart) {
        Object _ret = this.riInvokeExportedMethod(this, "isPartInactive", new String[] { "java.lang.String" }, new Object[] {
                                                  sPart });
        return ((Boolean) _ret).booleanValue();
    }

    public void printRecd() {
        Object _ret = this.riInvokeExportedMethod(this, "printRecd", null, null);
        return;
    }
}
