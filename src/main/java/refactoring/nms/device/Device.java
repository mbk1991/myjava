package refactoring.nms.device;


import lombok.Getter;
import lombok.Setter;
import modules.snmp.SnmpUtil;
import modules.snmp.data.SnmpValue;
import refactoring.nms.device.enums.ManagementMIB;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Device {

    //device 테이블의 ID
    private int dKey;

    /*입력받거나 device 테이블에서 가지고 오는 값*/
    private String ifIp;
    private int version;
    private String community;
    private String netCD;
    private String netNm;
    private String stateCD;
    private String stateNm;
    private String regDate;
    private int dev_gubun;
    private String loginUser;

    /*SNMP로 수집하는 값*/
    private String sysOID;
    private String sysName;
    private String hostName;
    private String devAlias;
    private String sysUpTime;
    private int ifCount;
    private String sysDescr;
    private String osVer;
    private List<Cpu> cpuList = new ArrayList<>();
    private List<Mem> memList = new ArrayList<>();
    private List<Fs> fsList = new ArrayList<>();
    private List<Port> portList = new ArrayList<>();
    private List<Icmp> icmpList = new ArrayList<>();


    public Device(String sysOID) {
        setSysOID(sysOID);
    }

    public int getNumOfCpu() {
        return cpuList.size();
    }

    public int getNumOfMem() {
        return memList.size();
    }

    public int getNumOfFs() {
        return fsList.size();
    }

    public int getNumOfPort() {
        return portList.size();
    }

    public int getNumOfIcmp() {
        return icmpList.size();
    }


    public static String getSysOID(String ip, int version, String community) {
        return parseSysOID(SnmpUtil.get(ip, version, community, ManagementMIB.SYS_OBJECT_ID.oid(), 2, 1000));
    }


    //Resource
    public void setDeviceBasicInfo(String ip, int version, String community, String netCD, String stateCD, String devGubun, String loginUser) {
        System.out.println("장비 기본 정보를 세팅합니다.");
        setIfIp(ip);
        setVersion(version);
        setCommunity(community);
    }


    public void setDeviceSnmpCollectData() {
        System.out.println("장비 SNMP 기본 정보를 세팅합니다.");
        setSysUpTime(parseSysUpTime(SnmpUtil.get(ifIp, version, community, ManagementMIB.SYS_UP_TIME.oid(), 2, 1000)));
        setSysName(parseSysName(SnmpUtil.get(ifIp, version, community, ManagementMIB.SYS_NAME.oid(), 2, 1000)));
        setSysDescr(parseSysDescr(SnmpUtil.get(ifIp, version, community, ManagementMIB.SYS_DESCR.oid(), 2, 1000)));
        setIfCount(parseIfCount(SnmpUtil.get(ifIp, version, community, ManagementMIB.IF_NUMBER.oid(), 2, 1000)));
        setOsVer(parseOsVer(getSysDescr()));
    }


    public void setCpuResource() {
        System.out.println("cpu 정보를 세팅합니다.");
        Cpu cpu = new Cpu();
        cpu.setCINDEX(1);
        cpu.setCPUSTATUS(0);
        cpu.setCPUTHR(80);
        cpu.setCPULOWTHR(60);
        cpuList.add(cpu);
    }


    public void setMemResource() {
        System.out.println("mem 정보를 세팅합니다.");
        Mem mem = new Mem();
        mem.setMINDEX(1);
        mem.setMEMSTATUS(0);
        mem.setMEMTHR(80);
        mem.setMEMLOWTHR(60);
        memList.add(mem);
    }


    public void setFsResource() {
        System.out.println("fs 정보를 세팅합니다.");

        List<SnmpValue> snmpValues = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.HR_STORAGE_INDEX.oid(), 2, 1000);

        if (snmpValues.isEmpty()) {
            Fs fs = new Fs();
            fs.setFsIndex(1);
            fs.setFsName("FILE SYSTEM");
            fs.setAllocationUnits(0);
            fs.setStorageSize(0);
            fs.setStorageUsed(0);
            fs.setRowTotalSize(0);
            fs.setRowUseSize(0);
            fs.setTotalSize(0);
            fs.setUseSize(0);
            fs.setSizeUtil(0);

            fsList.add(fs);

        } else {

            for (SnmpValue v : snmpValues) {

                int fsIndex = (!v.getOid().startsWith(".")) ?
                        Integer.parseInt(v.getOid().replaceFirst(ManagementMIB.HR_STORAGE_INDEX + ".", "")) : Integer.parseInt(v.getOid());


                String fsType = SnmpUtil.get(ifIp, version, community, ManagementMIB.HR_STORAGE_TYPE.toString() + fsIndex, 2, 1000);


                if (fsType.equals(ManagementMIB.HR_STORAGE_FIXED_DISK.toString())
                        || fsType.equals(ManagementMIB.HR_STORAGE_TYPES.toString())) {


                    String fsName = SnmpUtil.get(ifIp, version, community, ManagementMIB.HR_STORAGE_DESCR.toString() + fsIndex, 2, 1000);
                    try {
                        fsName = new String(fsName.getBytes(StandardCharsets.ISO_8859_1), "euc-kr");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }


                    String allocationUnits = SnmpUtil.get(ifIp, version, community, ManagementMIB.HR_STORAGE_ALLOCATION_UNITS.toString() + fsIndex, 2, 1000);
                    long aUnits = (allocationUnits == null || allocationUnits.startsWith("noSuch")) ?
                            0L : Long.parseLong(allocationUnits);


                    String storageSize = SnmpUtil.get(ifIp, version, community, ManagementMIB.HR_STORAGE_SIZE.toString() + fsIndex, 2, 1000);
                    long sSize = (storageSize == null || storageSize.startsWith("noSuch")) ?
                            0L : Long.parseLong(storageSize);


                    String storageUsed = SnmpUtil.get(ifIp, version, community, ManagementMIB.HR_STORAGE_USED.toString() + fsIndex, 2, 1000);
                    long sUsed = (storageUsed == null || storageUsed.startsWith("noSuch")) ?
                            0L : Long.parseLong(storageUsed);


                    Fs fs = new Fs();
                    fs.setFsIndex(fsIndex);
                    fs.setFsName(fsName);
                    fs.setAllocationUnits(aUnits);
                    fs.setStorageUsed(sUsed);
                    fs.setRowTotalSize(aUnits * sSize);
                    fs.setRowUseSize(aUnits * sUsed);
                    fs.setTotalSize(fs.getRowTotalSize() / 1024);
                    fs.setUseSize(fs.getRowUseSize() / 1024);
                    fs.setSizeUtil(
                            (int) ((double) fs.getUseSize() / fs.getTotalSize() * 100)
                    );


                    this.fsList.add(fs);


                } else if (fsType.equals(ManagementMIB.HR_STORAGE_RAM.toString())) {


                }

            }

        }


    }

    public void setPortResource() {
        System.out.println("port 정보를 세팅합니다.");

        List<SnmpValue> ifIndex = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_INDEX.oid(), 2, 1000);
        List<SnmpValue> ifDescr = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_DESCR.oid(), 2, 1000);
        List<SnmpValue> ifType = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_TYPE.oid(), 2, 1000);
        List<SnmpValue> ifSpeed = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_SPEED.oid(), 2, 1000);
        List<SnmpValue> ifPhysAddress = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_PHYS_ADDRESS.oid(), 2, 1000);
        List<SnmpValue> ifAdminStatus = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_ADMIN_STATUS.oid(), 2, 1000);
        List<SnmpValue> ifOperStatus = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_OPER_STATUS.oid(), 2, 1000);
        List<SnmpValue> ifName = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_NAME.oid(), 2, 1000);
        List<SnmpValue> ifHighSpeed = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_HIGH_SPEED.oid(), 2, 1000);
        List<SnmpValue> ifAlias = SnmpUtil.snmpWalk(ifIp, version, community, ManagementMIB.IF_ALIAS.oid(), 2, 1000);







        if(ifIndex.isEmpty()){

        }else{



        }



    }

    public void setIcmpResource() {
        System.out.println("icmp 정보를 세팅합니다.");
    }


    //Performance
    public void setCpuPerformance() {
    }

    public void setMemPerformance() {
    }

    public void setFsPerformance() {
    }

    public void setPortPerformance() {
    }

    public void setIcmpPerformance() {
    }

    //Etc Performance
    public void setEtcPerformance() {
    }


    private static String parseSysOID(String value) {
        if (value == null) {
            return "";
        } else {
            if (value.equals("") || value.equals("Null") || value.equals("NULL") || value.contains("noSuch")) {
                return "";
            } else {
                return "." + value;
            }
        }
    }

    private String parseOsVer(String sysDescr) {
        if (sysDescr == null) return "";

        if (sysDescr.indexOf("Ver") != -1) {
            String Temp1 = sysDescr.substring(sysDescr.indexOf("Ver"));
            String Temp2 = Temp1.substring(Temp1.indexOf(" "));
            String value = "";
            if (Temp2.indexOf("(") != -1) {
                value = Temp2.substring(0, Temp2.indexOf("("));
            } else if (Temp2.indexOf(":") != -1) {
                value = Temp2.substring(0, Temp2.indexOf(":"));
            } else {
                value = "NO";
            }
            if (value.indexOf(",") != -1) {
                String[] value_ary = value.split(",");
                value = value_ary[0];
            }
            if (value.length() >= 30) {
                String[] value_ary = value.split(" ");
                value = value_ary[0];
            }
            return value.trim();
        } else {
            return "";
        }
    }

    private int parseIfCount(String value) {
        if (value == null || value.equals("Null") || value.equals("NULL")) {
            /*20200610 SOURCE ADD(NULL)*/
            if (value == null) {
                return 0;
            } else {
                //if value = null occur error
                if (value.equals("") || value.equals("Null") || value.equals("NULL") || value.contains("noSuch")) {
                    return 0;
                } else {
                    return Integer.valueOf(value);
                }
            }
        }
        return 0;
    }

    private String parseSysDescr(String value) {
        return value;
    }

    private String parseSysName(String value) {
        try {
            if (value == null) {
                return "";
            } else {
                if (value.equals("") || value.equals("Null") || value.equals("NULL") || value.contains("noSuch")) {
                    return ifIp;
                } else {
                    return new String(value.getBytes("iso-8859-1"), "euc-kr");
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String parseSysUpTime(String value) {
        return value;
    }

}
