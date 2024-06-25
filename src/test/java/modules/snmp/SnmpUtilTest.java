package modules.snmp;

import modules.snmp.data.SnmpValue;
import modules.snmp.enums.MIB_MGMT;
import modules.snmp.enums.SnmpVersion;
import org.junit.jupiter.api.Test;

import java.util.List;

class SnmpUtilTest {
    @Test
    void get_테스트() {
        String result = SnmpUtil.get("192.168.10.225", SnmpVersion.SNMP_VERSION_2C.value(), "ncnms", MIB_MGMT.SYS_UP_TIME.oid(), 2, 1000);
        System.out.println("result = " + result);
    }

    @Test
    void get_next_walk_테스트() {
        List<SnmpValue> valueList = SnmpUtil.snmpWalk("192.168.10.225", SnmpVersion.SNMP_VERSION_2C.value(), "ncnms", MIB_MGMT.HR_STORAGE_INDEX.oid(), 2, 1000);
        valueList.forEach(
                v->{
                    System.out.println(v.getOid());
                    System.out.println(v.getSyntax());
                    System.out.println(v.getValue());
                }
        );
    }

}