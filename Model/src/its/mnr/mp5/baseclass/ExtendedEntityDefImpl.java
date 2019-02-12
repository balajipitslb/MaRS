package its.mnr.mp5.baseclass;

import oracle.jbo.server.EntityDefImpl;

public class ExtendedEntityDefImpl extends EntityDefImpl {
    public ExtendedEntityDefImpl(int i, String string, String string1) {
        super(i, string, string1);
    }

    public ExtendedEntityDefImpl(String string) {
        super(string);
    }

    public ExtendedEntityDefImpl(int i, String string) {
        super(i, string);
    }

    public ExtendedEntityDefImpl() {
        super();
    }
}
