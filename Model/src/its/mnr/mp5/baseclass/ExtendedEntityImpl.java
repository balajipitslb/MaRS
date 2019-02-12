package its.mnr.mp5.baseclass;

import oracle.adf.share.ADFContext;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.share.security.binding.BindingPermissionDef;

import oracle.jbo.DataSecurityProvider;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.DBTransactionImpl;
import oracle.jbo.server.EntityCache;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.security.DataSecurityProviderManager;
import oracle.jbo.server.security.PermissionHelper;

import oracle.security.jps.ResourcePermission;

public class ExtendedEntityImpl extends EntityImpl {
    public ExtendedEntityImpl() {
        super();
    }

    @Override
    public boolean isAttributeUpdateable(int i) {

        DBTransactionImpl dbtransaction = (DBTransactionImpl)this.getDBTransaction();
        DataSecurityProvider provider;
        provider = new DataSecurityProviderManager(dbtransaction).getDataSecurityProvider();

        EntityCache ec = getEntityCache();
        AttributeDefImpl attrDef = (AttributeDefImpl)ec.getAttributeDef(i);
        BindingPermissionDef permDef = attrDef.getPermissionDef();

        String privToCheck = permDef == null ? null : permDef.findPrivilege(PermissionHelper.UPDATE_ACTION);

        //if one of the following is true, then no security has been enabled
        //on the entity attribute. Security is enabled by chosing the Edit
        //Security option on the attribute context menu in the Structure Window
        if (provider == null || permDef == null || privToCheck == null) {
            return true;
        }

        //check if attribute is new (insert case)
        if (getPostState() == STATUS_NEW || getPostState() == STATUS_INITIALIZED) {
            //build ResourcePermission
            //type = InsertEntityAttribute, Target = InsertEntityAttribute, Action = updateWhenNew
            String type = "InsertEntityAttribute";
            String entityName = this.getEntityDef().getName();
            String action = "updateWhenNew";


            SecurityContext securityCtx = ADFContext.getCurrent().getSecurityContext();

            ResourcePermission resourcePermission = new ResourcePermission(type, entityName, action);
            boolean userHasPermission = securityCtx.hasPermission(resourcePermission);
            if (userHasPermission) {
                return true;
            }
            return false;
        }

        //its an update of an existing attribute vaulue. So lets have the default implementation
        //handling this
        return super.isAttributeUpdateable(i);
    }
    public void invalidateMe(){
        setInvalid();
        }

}
