package refactoring.nms.device.enums.mib_hp;

import refactoring.nms.device.enums.ManagementMIB;

public enum HP_MIB{
    ENTERPRISE(new int[]{1,3,6,1,4,1,11}),
    FILESYSTEM_ID1(new int[]{1,3,6,1,4,1,11,2,3,1,2,2,1,1}),
    FILESYSTEM_DIR(new int[]{1,3,6,1,4,1,11,2,3,1,2,2,1,10}),
    FILESYSTEM_BLOCK(new int[]{1,3,6,1,4,1,11,2,3,1,2,2,1,4}),
    FILESYSTEM_B_FREE(new int[]{1,3,6,1,4,1,11,2,3,1,2,2,1,5}),
    ;
    HP_MIB(int [] oid){
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
