package refactoring.nms.device.enums;

public enum ManagementMIB {
    //Management OID
    SYS_OBJECT_ID(new int[]{1,3,6,1,2,1,1,2,0}),
    SYS_UP_TIME(new int[]{1,3,6,1,2,1,1,3,0}),
    SYS_NAME(new int[]{1,3,6,1,2,1,1,5,0}),
    SYS_DESCR(new int[]{1,3,6,1,2,1,1,1,0}),
    IF_NUMBER(new int[]{1,3,6,1,2,1,2,1,0}),

    //tree (CPU/ MEM/ FS)
    HR_PROCESSOR_LOAD(new int[]{1,3,6,1,2,1,25,3,3,1,2}),
    HR_STORAGE_INDEX(new int[]{1,3,6,1,2,1,25,2,3,1,1}),
    HR_STORAGE_TYPE(new int[]{1,3,6,1,2,1,25,2,3,1,2}),
    HR_STORAGE_DESCR(new int[]{1,3,6,1,2,1,25,2,3,1,3}),
    HR_STORAGE_ALLOCATION_UNITS(new int[]{1,3,6,1,2,1,25,2,3,1,4}),
    HR_STORAGE_SIZE(new int[]{1,3,6,1,2,1,25,2,3,1,5}),
    HR_STORAGE_USED(new int[]{1,3,6,1,2,1,25,2,3,1,6}),
    HR_STORAGE_FIXED_DISK(new int[]{1,3,6,1,2,1,25,2,1,4}),
    HR_STORAGE_TYPES(new int[]{1,3,6,1,2,1,25,2,1,5}),
    HR_STORAGE_RAM(new int[]{1,3,6,1,2,1,25,2,1,2}),

    //tree(PORT)
    IF_INDEX(new int[]{1,3,6,1,2,1,2,2,1,1}),
    IF_DESCR(new int[]{1,3,6,1,2,1,2,2,1,2}),
    IF_TYPE(new int[]{1,3,6,1,2,1,2,2,1,3}),
    IF_SPEED(new int[]{1,3,6,1,2,1,2,2,1,5}),
    IF_PHYS_ADDRESS(new int[]{1,3,6,1,2,1,2,2,1,6}),
    IF_ADMIN_STATUS(new int[]{1,3,6,1,2,1,2,2,1,7}),
    IF_OPER_STATUS(new int[]{1,3,6,1,2,1,2,2,1,8}),
    IF_NAME(new int[]{1,3,6,1,2,1,31,1,1,1,1}),
    IF_HIGH_SPEED(new int[]{1,3,6,1,2,1,31,1,1,1,15}),
    IF_ALIAS(new int[]{1,3,6,1,2,1,31,1,1,1,18}),
    DOT3_STATS_DUPLEX_STATUS(new int[]{1,3,6,1,2,1,10,7,2,1,19}),
    IP_AD_ENT_ADDR(new int[]{1,3,6,1,2,1,4,20,1,1}),
    IP_AD_ENT_IF_INDEX(new int[]{1,3,6,1,2,1,4,20,1,2}),
    IP_AD_ENT_NETMASK(new int[]{1,3,6,1,2,1,4,20,1,3});

    ManagementMIB(int [] oid){
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
