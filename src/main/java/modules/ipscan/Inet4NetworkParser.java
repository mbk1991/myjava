package modules.ipscan;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Inet4NetworkParser {

    private static final int IPV4_LENTH = 32;
    private static final String CIDR_SEPARATOR = "/";
    private static final String OCTET_SEPARATOR = ".";

    private enum OCTET {
        A(0b11111111_00000000_00000000_00000000L),
        B(0b00000000_11111111_00000000_00000000L),
        C(0b00000000_00000000_11111111_00000000L),
        D(0b00000000_00000000_00000000_11111111L);

        OCTET(long mask) {
            this.mask = mask;
        }

        private final long mask;
    }

    public static List<String> getNetworkIpList(String ip, int cidr) {
        long net = ipToLong(ip) & parseCidr(cidr);

        List<String> result = new ArrayList<>();
        for (int i = 0; i < setHostNum(cidr); i++) {
            result.add(longToInetString(net + i));
        }

        return result;
    }

    private static long ipToLong(String ip) {
        try {
            InetAddress inet = InetAddress.getByName(ip);
            byte[] bytes = inet.getAddress();

            long result = 0L;
            for (int i = 0; i < bytes.length; i++) {
                result += (long) (bytes[i] & 0xFF) << (i + 1) * IPV4_LENTH / 4;
            }
            return result;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static long parseCidr(int cidr) {
        return 0xFF_FF_FF_FFL << (IPV4_LENTH - cidr);
    }

    private static int setHostNum(int cidr) {
        return (int) Math.pow(2, IPV4_LENTH - cidr);
    }

    private static String longToInetString(long ip) {
        return new StringBuilder()
                .append((byte) ((OCTET.A.mask & ip) >> 24) & 0xFF)
                .append(OCTET_SEPARATOR)
                .append((byte) ((OCTET.B.mask & ip) >> 16) & 0xFF)
                .append(OCTET_SEPARATOR)
                .append((byte) ((OCTET.C.mask & ip) >> 8) & 0xFF)
                .append(OCTET_SEPARATOR)
                .append((byte) (OCTET.D.mask & ip) & 0xFF)
                .toString();
    }
}
