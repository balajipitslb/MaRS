package its.mnr.mp5.baseclass;

import oracle.jbo.server.ViewDefImpl;

public class ExtendedViewDefImpl extends ViewDefImpl {
    public ExtendedViewDefImpl(int i, String string, String string1) {
        super(i, string, string1);
    }

    public ExtendedViewDefImpl(String string) {
        super(string);
    }

    public ExtendedViewDefImpl(int i, String string) {
        super(i, string);
    }

    public ExtendedViewDefImpl() {
        super();
    }
}
