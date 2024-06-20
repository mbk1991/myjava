package modules.ipscan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IpscanTest{
    Ipscan ipscan = new Ipscan();

    @Test
    void test_테스트(){
        ipscan.test();
    }
}