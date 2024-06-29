package refactoring.nms.device.enter_hp;

import modules.snmp.SnmpUtil;
import modules.snmp.data.SnmpValue;
import org.snmp4j.Snmp;
import refactoring.nms.device.Device;
import refactoring.nms.device.Fs;
import refactoring.nms.device.enums.ManagementMIB;
import refactoring.nms.device.enums.mib_hp.HP_MIB;

import java.util.List;

public class HP  extends Device {

    public HP(String sysOID) {
        super(sysOID);
    }

    @Override
    public void setFsResource() {
        System.out.println("HP: fs 정보를 세팅합니다.");

        List<SnmpValue> snmpValues = SnmpUtil.snmpWalk(super.getIfIp(), super.getVersion(), super.getCommunity(), HP_MIB.FILESYSTEM_ID1.oid(), 2, 1000);

        if(snmpValues.isEmpty()){

        }else{

            for(SnmpValue v:snmpValues){
                Fs fs = new Fs();
                fs.setFsIndex(Integer.parseInt(v.getValue()));
                fs.setGubun(Integer.parseInt(
                        v.getOid().replaceFirst(
                                new StringBuffer()
                                        .append(".")
                                        .append(HP_MIB.FILESYSTEM_ID1)
                                        .append(".")
                                        .append(fs.getFsIndex())
                                        .append(".")
                                        .toString()

                                ,"")
                ));

                String dirOid =
                        new StringBuffer().append(HP_MIB.FILESYSTEM_DIR).append(".").append(fs.getFsIndex()).append(".").append(fs.getGubun()).toString();

                fs.setFsName(SnmpUtil.get(super.getIfIp(),super.getVersion(),super.getCommunity(),dirOid,2,1000));

                fs.setAllocationUnits(0);

                String sizeOid =
                        new StringBuffer().append(HP_MIB.FILESYSTEM_BLOCK).append(".").append(fs.getFsIndex()).append(".").append(fs.getGubun()).toString();

                String storageSize = SnmpUtil.get(super.getIfIp(),super.getVersion(),super.getCommunity(),sizeOid,1000,2);

                if(storageSize == null || storageSize.startsWith("noSuch")){
                    fs.setStorageSize(0);
                } else{
                    fs.setStorageSize(Long.parseLong(storageSize));
                }

                String freeOid =
                        new StringBuffer().append(HP_MIB.FILESYSTEM_B_FREE).append(".").append(fs.getFsIndex()).append(".").append(fs.getGubun()).toString();
                String freeSize = SnmpUtil.get(super.getIfIp(), super.getVersion(), super.getCommunity(),freeOid,2,1000);

                long fSize = (freeSize == null || freeSize.startsWith("noSuch")?
                        0L: Long.parseLong(freeSize));

                fs.setStorageUsed( fs.getStorageSize() - fSize );
                fs.setRowTotalSize(0);
                fs.setRowUseSize(0);
                fs.setTotalSize( fs.getStorageSize() / 1024 );
                fs.setUseSize( fs.getStorageUsed() / 1024 );
                fs.setSizeUtil(
                        (int) ((double) fs.getUseSize() / fs.getTotalSize() * 100)
                );


                super.getFsList().add(fs);

            }
        }

    }
}
