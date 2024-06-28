package modules.ipscan;

import modules.snmp.SnmpUtil;
import modules.snmp.enums.MIB_MGMT;
import modules.snmp.enums.SnmpVersion;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

public class HostChecker {
    public static boolean pingCheck(String host, int timeout) {
        try {
            return InetAddress.getByName(host).isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean portCheck(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            return socket.isConnected();
        } catch (IOException e) {
            return false;
        }
    }

    public static Optional<String> snmpCheck(String host, String[] communitys) {
        for (String community : communitys) {
            try{
                String s = SnmpUtil.get(host, SnmpVersion.SNMP_VERSION_2C.value(), community, MIB_MGMT.SYS_UP_TIME.oid(), 2, 500);
                if(s != null && !s.equals("")){
                    return Optional.of(community);
                }
            }catch(Exception e){
                continue;
            }
        }
        return Optional.empty();
    }

}
