package modules.ipscan;

import modules.ipscan.data.HostStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HostCheckerTest {
    private static final int[] CHK_PORTS = {22, 80, 443, 161, 1521, 3306, 8080};
    private static final String[] COMMINITYS = {"public", "ncnms", "Office", "infra"};

    @Test
    void interface_테스트() {
        List<String> networkIpList = Inet4NetworkParser.getNetworkIpList("192.168.10.0", 24);
        for (String host : networkIpList) {
            System.out.printf("IP: %s, isRechable: %b \n", host, HostChecker.pingCheck(host, 500));
        }
    }

    @Test
    void port_테스트() {
        String host = "192.168.10.225";
        for (int port : CHK_PORTS) {
            System.out.printf("%s port %d status: %b \n", host, port, HostChecker.portCheck(host, port));
        }
    }

    @Test
    void snmp_테스트() {
        String host = "192.168.10.225";
        Optional<String> result = HostChecker.snmpCheck(host, COMMINITYS);
        if (result.isPresent()) {
            String string = result.toString();
            System.out.println("community = " + result.get());
        }
    }

    @Test
    void 프로비저닝_테스트() {

        long s = System.currentTimeMillis();
        List<String> networkIpList = Inet4NetworkParser.getNetworkIpList("192.168.0.0", 24);
//        List<String> networkIpList = Inet4NetworkParser.getNetworkIpListRange("192.168.10.200", "192.168.10.230", 24);
        List<HostStatus> hostStatusList = new ArrayList<>();

        for (String host : networkIpList) {
            HostStatus hostStatus = new HostStatus();
            hostStatus.setIp(host);
            hostStatus.setReachable(HostChecker.pingCheck(host, 500));
            Optional<String> settingedCommunity = HostChecker.snmpCheck(host, COMMINITYS);
            if (settingedCommunity.isPresent()) {
                hostStatus.setSuccessSnmp(true);
                hostStatus.setSnmpCommunity(settingedCommunity.get());
            }
            hostStatusList.add(hostStatus);
        }
        System.out.println("===========================================");
        System.out.println("|\tIP |\tPing |\tSNMP |\tCommunity |");
        hostStatusList.forEach(
                host->{
                    System.out.printf("|\t%s |\t%b |\t%b |\t%s |\n", host.getIp(), host.isReachable(), host.isSuccessSnmp(), host.getSnmpCommunity());
                }
        );
        System.out.println("===========================================");

        System.out.println("(System.currentTimeMillis() - s = " + (System.currentTimeMillis() - s));
    }
}