package refactoring.nms.device.enums.mib_microsoft;

public enum Microsoft_MIB {
    ENTERPRISE(new int[]{1,3,6,1,4,1,311}),
    ;
    Microsoft_MIB(int [] oid){
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
