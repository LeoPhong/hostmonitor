package monitor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class CpuConditionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Timestamp curTime;
    private int id;
    private double load;

    public CpuConditionEntity() {
        super();
    }

    public CpuConditionEntity(Timestamp curTime, int id, double load) {
        super();
        this.curTime = curTime;
        this.id = id;
        this.load = load;
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

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }
}