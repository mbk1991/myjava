package refactoring.nms.device.enter_f5system;

import refactoring.nms.device.Device;
import refactoring.nms.device.Fs;

public class F5System  extends Device {
    public F5System(String sysOID) {
        super(sysOID);
    }

    @Override
    public void setFsResource() {
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

        super.getFsList().add(fs);
    }
}
