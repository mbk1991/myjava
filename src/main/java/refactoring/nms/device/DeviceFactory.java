package refactoring.nms.device;

import refactoring.nms.device.enter_allu.AlcataleLucent;
import refactoring.nms.device.enter_arista.Arista;
import refactoring.nms.device.enter_cisco.Cisco;
import refactoring.nms.device.enter_f5system.F5System;
import refactoring.nms.device.enter_hp.HP;
import refactoring.nms.device.enter_microsoft.Microsoft;
import refactoring.nms.device.enter_window.WindowServer;
import refactoring.nms.device.enums.mib_allu.AlcataleLucent_MIB;
import refactoring.nms.device.enums.mib_arista.Arista_MIB;
import refactoring.nms.device.enums.mib_cisco.Cisco_MIB;
import refactoring.nms.device.enums.mib_f5system.F5System_MIB;
import refactoring.nms.device.enums.mib_hp.HP_MIB;
import refactoring.nms.device.enums.mib_microsoft.Microsoft_MIB;
import refactoring.nms.device.enums.mib_window.WindowServer_MIB;

public interface DeviceFactory {
    static Device getDevice(String sysOid) {

        if (sysOid.equals(AlcataleLucent_MIB.ENTERPRISE.toString())) {
            return new AlcataleLucent(sysOid);

        } else if (sysOid.equals(Arista_MIB.ENTERPRISE.toString())) {
            return new Arista(sysOid);

        } else if (sysOid.equals(Cisco_MIB.ENTERPRISE.toString())) {
            return new Cisco(sysOid);

        } else if (sysOid.equals(F5System_MIB.ENTERPRISE.toString())) {
            return new F5System(sysOid);

        } else if (sysOid.equals(HP_MIB.ENTERPRISE.toString())) {
            return new HP(sysOid);

        } else if (sysOid.equals(Microsoft_MIB.ENTERPRISE.toString())) {
            return new Microsoft(sysOid);

        } else if (sysOid.equals(WindowServer_MIB.ENTERPRISE.toString())) {
            return new WindowServer(sysOid);

        } else {
            return new Device(sysOid);

        }
    }
}
