package modules.snmp.enums;

public enum SnmpVersion {
    SNMP_VERSION_1(0),
    SNMP_VERSION_2C(1),
    SNMP_VERSION_2(2),
    SNMP_VERSION_3(3);

    SnmpVersion(int version){
        this. version = version;
    }

    int version;
    public int value(){
        return this.version;
    }
}
