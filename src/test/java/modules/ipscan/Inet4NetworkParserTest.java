package modules.ipscan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Inet4NetworkParserTest {

    @Test
    void networkIpList_테스트(){
        long s = System.currentTimeMillis();
        Inet4NetworkParser.getNetworkIpList("255.255.255.255", 24).forEach(System.out::println);
        System.out.println(System.currentTimeMillis()-s + "(ms)");
    }

    @Test
    void networkIpRangeList_테스트(){
        long s = System.currentTimeMillis();
        Inet4NetworkParser.getNetworkIpListRange("192.168.10.11", "192.168.10.200",24).forEach(System.out::println);
        System.out.println(System.currentTimeMillis()-s + "(ms)");
    }
}