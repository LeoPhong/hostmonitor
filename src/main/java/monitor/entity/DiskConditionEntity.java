package monitor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class DiskConditionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Timestamp curTime;
    private int id;
    private long used;
    private long sum;

    public DiskConditionEntity() {
        super();
    }

    public DiskConditionEntity(Timestamp curTime, int id, long used, long sum) {
        super();
        this.curTime = curTime;
        this.id = id;
        this.used = used;
        this.sum = sum;
    }

    public Timestamp getCurTime() {
        return curTime;
    }

    public void setCurTime(Timestamp curTime) {
        this.curTime = curTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUsed() {
        return used;
    }

    public void setUsed(long used) {
        this.used = used;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }
}
