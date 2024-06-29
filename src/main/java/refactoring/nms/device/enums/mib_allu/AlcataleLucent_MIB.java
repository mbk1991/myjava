package refactoring.nms.device.enums.mib_allu;

public enum AlcataleLucent_MIB {
    ENTERPRISE(new int[]{}),
    ;

    AlcataleLucent_MIB(int[] oid) {
        this.oid = oid;
    }

    int[] oid;

    public int[] oid() {
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
