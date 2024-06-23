package modules.ipscan;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Inet4NetworkParser {
    private static final int IPV4_LENTH = 32;
    private static final int OCTET_LENGTH = 8;
    private static final String CIDR_SEPARATOR = "/";
    private static final String OCTET_SEPARATOR = ".";
    private static final int[] OCTET_MASK = {
            0xFF_00_00_00,
            0x00_FF_00_00,
            0x00_00_FF_00,
            0x00_00_00_FF
    };

    public static List<String> getNetworkIpList(String ip, int cidr) {
        if (!(16 <= cidr && cidr <= 32)) throw new RuntimeException("CIDR Range Exception");
        int net = ipToInt(ip) & netMask(cidr);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < hostNum(cidr); i++) {
            result.add(intToInet4String(net + i));
        }
        return result;
    }

    public static List<String> getNetworkIpListRange(String startIp, String endIp, int cidr) {
        if (!(16 <= cidr && cidr <= 32)) throw new RuntimeException("CIDR Range Exception");
        if ((ipToInt(startIp) & netMask(cidr)) != (ipToInt(endIp) & netMask(cidr))) {
            throw new RuntimeException("Different Network Exception");
        }
        int s = Math.min(ipToInt(startIp), ipToInt(endIp));
        int e = Math.max(ipToInt(startIp), ipToInt(endIp));
        List<String> result = new ArrayList<>();
        for (int ip = s; ip <= e; ip++) {
            result.add(intToInet4String(ip));
        }
        return result;
    }

    private static int ipToInt(String ip) {
        try {
            InetAddress inet = InetAddress.getByName(ip);
            byte[] bytes = inet.getAddress();
            int result = 0;
            for (int i = 0; i < bytes.length; i++) {
                result |= bytes[i] << ((3 - i) * OCTET_LENGTH) & OCTET_MASK[i];
            }
            return result;
        } catch (UnknownHostException e) {
            throw new RuntimeException("Wrong IP address");
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException("Wrong IP address: IPv6 Not Supported");
        }
    }

    private static int netMask(int cidr) {
        return 0xFF_FF_FF_FF << (IPV4_LENTH - cidr);
    }

    private static int hostMask(int cidr) {
        return ~netMask(cidr);
    }

    private static int hostNum(int cidr) {
        return (int) Math.pow(2, IPV4_LENTH - cidr);
    }

    private static String intToInet4String(int ip) {
        StringBuilder ipString = new StringBuilder();
        int cnt = 3;
        for (byte octet : intToInetBytes(ip)) {
            ipString.append(octet & 0xFF);
            if (cnt > 0) {
                ipString.append(OCTET_SEPARATOR);
                cnt--;
            }
        }
        return ipString.toString();
    }

    private static byte[] intToInetBytes(int ip) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((ip & OCTET_MASK[i]) >>> (OCTET_LENGTH * (3 - i)));
        }
        return bytes;
    }
}
