package modules.ipscan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Inet4NetworkParserTest {

    @Test
    void networkIpList_테스트(){

        long s = System.currentTimeMillis();
        List<String> networkIpList = Inet4NetworkParser.getNetworkIpList("192.168.10.2", 21);
        networkIpList.stream().forEach(System.out::println);

        System.out.println(System.currentTimeMillis()-s + "(ms)");
    }
}