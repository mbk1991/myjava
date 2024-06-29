package refactoring.nms.device;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fs {
    private int fsIndex;
    private int gubun;
    private String fsName;
    private String getDate;
    private long allocationUnits;
    private long storageSize;
    private long storageUsed;
    private long rowTotalSize;
    private long rowUseSize;
    private long totalSize;
    private long useSize;
    private int sizeUtil;
    private int fsStatus;
    private int fsThres;
}
