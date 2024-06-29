package refactoring.nms.device;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cpu {
    private int DKEY;
    private int CINDEX;
    private String GETDATE;
    private int CPUUTIL;
    private int CPUSTATUS;
    private int CPUTHR;
    private int CPULOWTHR;
}
