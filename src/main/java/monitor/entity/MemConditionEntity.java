package monitor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class MemConditionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Timestamp curTime;
    private int id;
    private long memSum;
    private long memUsed;
    private long swapSum;
    private long swapUsed;

    public MemConditionEntity() {
        super();
    }

    public MemConditionEntity(Timestamp curTime, int id, long memUsed, long memSum, long swapUsed, long swapSum) {
        super();
        this.curTime = curTime;
        this.id = id;
        this.memUsed = memUsed;
        this.memSum = memSum;
        this.swapUsed = swapUsed;
        this.swapSum = swapSum;
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

    public long getMemSum() {
        return memSum;
    }

    public void setMemSum(long memSum) {
        this.memSum = memSum;
    }

    public long getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(long memUsed) {
        this.memUsed  = memUsed;
    }

    public long getSwapSum() {
        return swapSum;
    }

    public void setSwapSum(long swapSum) {
        this.swapSum = swapSum;
    }

    public long getSwapUsed() {
        return swapUsed;
    }

    public void setSwapUsed(long swapUsed) {
        this.swapUsed = swapUsed;
    }
}