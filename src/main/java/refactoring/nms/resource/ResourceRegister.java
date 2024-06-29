package refactoring.nms.resource;

import refactoring.nms.device.Device;
import refactoring.nms.device.DeviceFactory;

public class ResourceRegister implements ResourceProcessor {
    private String ip;
    private int version;
    private String community;
    private String netCD;
    private String stateCD;
    private String devGubun;
    private String loginUser;

    public ResourceRegister(String[] args) {
        /*
            0:ip,
            1:version,
            2:community,
            3:netCD
            4:stateCD
            5:device_gubun
            5:loginUser
        */
        setIp(args[0]);
        setVersion(args[1]);
        setCommunity(args[2]);
        setNetCD(args[3]);
        setStateCD(args[4]);
        setDevGubun(args[5]);
        setLoginUser(args[6]);
    }

    @Override
    public void process() {

        String sysOID = Device.getSysOID(ip, version, community);

        if(sysOID == null || sysOID.equals("") || sysOID.equals("NULL") || sysOID.equals("null")){
            System.exit(-1);
        }

        Device device = DeviceFactory.getDevice(sysOID);

        device.setDeviceBasicInfo(ip, version, community, netCD, stateCD, devGubun, loginUser);

        device.setDeviceSnmpCollectData();

        device.setCpuResource();

        device.setMemResource();

        device.setFsResource();

        device.setPortResource();

        device.setIcmpResource();

    }


    private void setIp(String arg) {
        this.ip = arg.trim();
    }

    private void setVersion(String arg) {

        this.version = Integer.parseInt(arg.trim());
    }

    private void setCommunity(String arg) {
        this.community = arg.trim();
    }

    private void setNetCD(String arg) {
        this.netCD = arg.trim();

    }

    private void setStateCD(String arg) {
        this.stateCD = arg.trim();
    }

    private void setDevGubun(String arg) {
        this.devGubun = arg.trim();
    }

    private void setLoginUser(String arg) {
        this.loginUser = arg.trim();
    }
}
