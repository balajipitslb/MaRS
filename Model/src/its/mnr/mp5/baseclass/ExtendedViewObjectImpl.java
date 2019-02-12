package its.mnr.mp5.baseclass;

import oracle.jbo.server.ViewDefImpl;
import oracle.jbo.server.ViewObjectImpl;

public class ExtendedViewObjectImpl extends ViewObjectImpl {
    public ExtendedViewObjectImpl(String string, ViewDefImpl viewDefImpl) {
        super(string, viewDefImpl);
    }

    public ExtendedViewObjectImpl() {
        super();
    }
}
