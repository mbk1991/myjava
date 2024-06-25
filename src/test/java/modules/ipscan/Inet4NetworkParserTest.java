package modules.ipscan;

import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Inet4NetworkParserTest {
    @Test
    void networkIpList_테스트() {
        long s = System.currentTimeMillis();
        Inet4NetworkParser.getNetworkIpList("192.168.10.0", 24).forEach(System.out::println);
        System.out.println(System.currentTimeMillis() - s + "(ms)");
    }

    @Test
    void networkIpRangeList_테스트() {
        long s = System.currentTimeMillis();
        Inet4NetworkParser.getNetworkIpListRange("192.168.0.109", "192.168.0.90", 24).forEach(System.out::println);
        System.out.println(System.currentTimeMillis() - s + "(ms)");
    }

    @Test
    void binary_테스트() {
        int googleDNS = 0b00000100_00000100_00000100_00000100;
        System.out.println("googleDNS = " + googleDNS);
    }

    @Test
    void ipToBytes_테스트() throws UnknownHostException {
        String ipv4Str = "192.168.0.100";
        byte[] bytes = InetAddress.getByName(ipv4Str).getAddress();

        System.out.println("bytes.length = " + bytes.length);
        ;
        System.out.println(bytes[0]);  //192
        System.out.println(bytes[1]);  //168
        System.out.println(bytes[2]);  //0
        System.out.println(bytes[3]);  //100
    }

    @Test
    void shift_테스트() throws Exception {

        int a = 0b00000001_00000000_00000000_00000000;
        int intA = a;
        long longA = (long) a << 56;

        System.out.println(Integer.toBinaryString(intA));
        System.out.println(Long.toBinaryString(longA));

        InetAddress byName = InetAddress.getByName("192.168.10.123");
        System.out.println(byName.getHostName());


    }
}