package monitor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TempConditionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Timestamp curTime;
    private int id;
    private double temp;

    public TempConditionEntity() {
        super();
    }

    public TempConditionEntity(Timestamp curTime, int id, double temp) {
        super();
        this.curTime = curTime;
        this.id = id;
        this.temp = temp;
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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
