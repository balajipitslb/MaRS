package its.mnr.mp5.baseclass;

import oracle.jbo.server.ApplicationModuleDefImpl;

public class ExtendedApplicationModuleDefImpl extends ApplicationModuleDefImpl {
    public ExtendedApplicationModuleDefImpl(String string) {
        super(string);
    }

    public ExtendedApplicationModuleDefImpl(int i, String string) {
        super(i, string);
    }

    public ExtendedApplicationModuleDefImpl() {
        super();
    }
}
