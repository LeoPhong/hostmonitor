package monitor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class NetConditionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Timestamp curTime;
    private int id;
    private String device;
    private long rxBytes;
    private long txBytes;

    public NetConditionEntity() {
        super();
    }

    public NetConditionEntity(Timestamp curTime, int id, String device, long rxBytes, long txBytes) {
        super();
        this.curTime = curTime;
        this.id = id;
        this.device = device;
        this.rxBytes = rxBytes;
        this.txBytes = txBytes;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public long getRxBytes() {
        return rxBytes;
    }

    public void setRxBytes(long rxBytes) {
        this.rxBytes = rxBytes;
    }

    public long getTxBytes() {
        return txBytes;
    }

    public void setTxBytes(long txBytes) {
        this.txBytes = txBytes;
    }
}