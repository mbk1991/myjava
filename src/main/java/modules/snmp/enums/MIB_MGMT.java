package modules.snmp.enums;

public enum MIB_MGMT {

    //oid
    SYS_OBJECT_ID(new int[]{1,3,6,1,2,1,1,2,0}),
    SYS_UP_TIME(new int[]{1,3,6,1,2,1,1,3,0}),
    SYS_NAME(new int[]{1,3,6,1,2,1,1,5,0}),
    SYS_DESCR(new int[]{1,3,6,1,2,1,1,1,0}),
    IF_NUMBER(new int[]{1,3,6,1,2,1,2,1,0}),

    //tree
    HR_PROCESSOR_LOAD(new int[]{1,3,6,1,2,1,25,3,3,1,2}),
    HR_STORAGE_INDEX(new int[]{1,3,6,1,2,1,25,2,3,1,1}),
    HR_STORAGE_TYPE(new int[]{1,3,6,1,2,1,25,2,3,1,2}),
    HR_STORAGE_DESCR(new int[]{1,3,6,1,2,1,25,2,3,1,3}),
    HR_STORAGE_ALLOCATION_UNITS(new int[]{1,3,6,1,2,1,25,2,3,1,4}),
    HR_STORAGE_USED(new int[]{1,3,6,1,2,1,25,2,3,1,5}),
    HR_STORAGE_SIZE(new int[]{1,3,6,1,2,1,25,2,3,6,1}),


    ;
    MIB_MGMT(int [] oid){
        this.oid = oid;
    }
    int[] oid;

    public int[] oid(){
        return this.oid;
    }
}
