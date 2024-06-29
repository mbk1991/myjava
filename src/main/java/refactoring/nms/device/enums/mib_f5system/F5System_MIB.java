package refactoring.nms.device.enums.mib_f5system;

public enum F5System_MIB {
    ENTERPRISE(new int[]{1,3,6,1,4,1,3375}),
    ;
    F5System_MIB(int [] oid){
        this.oid = oid;
    }
    int[] oid;

    public int[] oid(){
        return this.oid;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        for(int i=0; i<oid.length; i++){
            if(0 < i && i < oid.length){
                buffer.append(".");
            }
            buffer.append(oid[i]);
        }
        return buffer.toString();
    }
}
