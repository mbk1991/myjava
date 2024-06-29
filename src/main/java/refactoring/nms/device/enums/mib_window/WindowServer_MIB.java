package refactoring.nms.device.enums.mib_window;

public enum WindowServer_MIB {
    ENTERPRISE(new int[]{1, 3, 6, 1, 4, 1, 311, 1, 1, 3, 1, 2}),
    ;

    WindowServer_MIB(int[] oid) {
        this.oid = oid;
    }

    int[] oid;

    public int[] oid() {
        return this.oid;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < oid.length; i++) {
            if (i < oid.length - 1) {
                buffer.append(".");
            }
            buffer.append(i);
        }
        return buffer.toString();
    }

}
