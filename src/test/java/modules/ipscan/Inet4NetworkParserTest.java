package modules.ipscan;

import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Inet4NetworkParserTest {
    @Test
    void networkIpList_테스트(){
        long s = System.currentTimeMillis();
        Inet4NetworkParser.getNetworkIpList("::1", 24).forEach(System.out::println);
        System.out.println(System.currentTimeMillis()-s + "(ms)");
    }

    @Test
    void networkIpRangeList_테스트(){
        long s = System.currentTimeMillis();
        Inet4NetworkParser.getNetworkIpListRange("192.168.0.100", "192.168.10.200",21).forEach(System.out::println);
        System.out.println(System.currentTimeMillis()-s + "(ms)");
    }
    
    @Test
    void binary_테스트(){
        int googleDNS = 0b00000100_00000100_00000100_00000100;
        System.out.println("googleDNS = " + googleDNS);
    }

    @Test
    void ipToBytes_테스트() throws UnknownHostException {
        String ipv4Str = "192.168.0.100";

        String ipv6Str = "::1";

        InetAddress addr = InetAddress.getByName(ipv6Str);
    }
}