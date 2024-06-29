package refactoring.nms.device;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Port {
    private int DKEY;
    private int PKEY;
    private int IFINDEX;
    private String IFIP;
    private String IFPHYSADDR;
    private String IFNETADDR;
    private String IFSUBNET;
    private String SPEEDCD;
    private String IFNAME;
    private String IFALIAS;
    private String IFDESCR;
    private String REGDATE;
    private int DELFLAG;
    private String DELDATE;
    private int DUPLEX;
    private int SERVICE;
    private int IFADMIN;
    private int IFOPER;
    private String MANDESCR;

    private String CHG_IFPHYSADDR = "1";
    private String CHG_IFNETADDR = "2";
    private String CHG_IFSUBNET = "3";
    private String CHG_SPEEDCD = "4";
    private String CHG_IFNAME = "5";
    private String CHG_IFALIAS = "6";
    private String CHG_IFDESCR = "7";
    private String CHG_DUPLEX = "8";
    private String CHG_IFIP = "9";
}
