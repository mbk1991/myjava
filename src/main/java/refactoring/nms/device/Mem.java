package refactoring.nms.device;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mem {
    private int DKEY;
    private int MINDEX;
    private String GETDATE;
    private int MEMUTIL;
    private long USEDMEM;
    private long TOTALMEM;
    private int MEMSTATUS;
    private int MEMTHR;
    private int MEMLOWTHR;
}
