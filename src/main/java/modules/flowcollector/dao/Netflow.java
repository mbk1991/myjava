package modules.flowcollector.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Netflow {
    private long collect_time;
    private String ts;
    private String te;
    private String td;
    private String sa;
    private String da;
    private int sp;
    private int dp;
    private String pr;
    private String flg;
    private int fwd;
    private String ipVer;
    private int sas;
    private int das;
    private int inif;  // in interface
    private int outif; // out interface
    private long ipkt;
    private long opkt;
    private long ibyt;
    private long obyt;
    private int tcpFlag;
    private int tos;
    private int smk;
    private int dmk;
    private int dir;
    private String mpls1;
    private String mpls2;
    private String mpls3;
    private String mpls4;
    private String mpls5;
    private String mpls6;
    private String mpls7;
    private String mpls8;
    private String mpls9;
    private String mpls10;
    private int icmpType;
    private int icmpCode;
    private String nh;     //next hop
    private String nhb;    //next hop BGP
    private int svln;
    private int dvln;
    private String ismc;
    private String idmc;
    private String osmc;
    private String odmc;
    private int stos;
    private int dtos;
    private String cl;
    private String sl;
    private String al;
    private String ra;
    private String eng;
    private String exid;
    private String tr;
}
