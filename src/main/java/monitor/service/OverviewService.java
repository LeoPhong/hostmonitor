package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import monitor.entity.CpuConditionEntity;
import monitor.entity.DiskConditionEntity;
import monitor.entity.InfoEntity;
import monitor.entity.MemConditionEntity;
import monitor.entity.TempConditionEntity;
import monitor.mapper.CpuConditionMapper;
import monitor.mapper.DiskConditionMapper;
import monitor.mapper.InfoMapper;
import monitor.mapper.MemConditionMapper;
import monitor.mapper.TempConditionMapper;

@Service
public class OverviewService {
    private InfoMapper infoMapper;
    private CpuConditionMapper cpuConditionMapper;
    private MemConditionMapper memConditionMapper;
    private DiskConditionMapper diskConditionMapper;
    private TempConditionMapper tempConditionMapper;

    @Autowired
    public OverviewService(InfoMapper infoMapper, CpuConditionMapper cpuConditionMapper, MemConditionMapper memConditionMapper, DiskConditionMapper diskConditionMapper, TempConditionMapper tempConditionMapper){
        this.infoMapper = infoMapper;
        this.cpuConditionMapper = cpuConditionMapper;
        this.memConditionMapper = memConditionMapper;
        this.diskConditionMapper = diskConditionMapper;
        this.tempConditionMapper = tempConditionMapper;
    }

    public OverviewGenerator get(String server_name) {
        if(server_name.equals("Router")) {
            return new OverviewGenerator(infoMapper, cpuConditionMapper, memConditionMapper, diskConditionMapper, tempConditionMapper, server_name);
        }
        else if(server_name.equals("LinuxServer")){
            return new OverviewGenerator(infoMapper, cpuConditionMapper, memConditionMapper, diskConditionMapper, tempConditionMapper, server_name);
        }
        else {
            return (OverviewGenerator) null;
        }
    }
}


class OverviewGenerator {
    private int id;
    private double cpuload;
    private long memused;
    private long memsum;
    private long swapused;
    private long swapsum;
    private long diskused;
    private long disksum;
    private long netrx;
    private long nettx;
    private double temp;

    private CpuConditionMapper cpuConditionMapper;
    private MemConditionMapper memConditionMapper;
    private DiskConditionMapper diskConditionMapper;
    private TempConditionMapper tempConditionMapper;

    public OverviewGenerator(
            InfoMapper infoMapper,
            CpuConditionMapper cpuConditionMapper,
            MemConditionMapper memConditionMapper,
            DiskConditionMapper diskConditionMapper,
            TempConditionMapper tempConditionMapper,
            String name) {
        this.cpuConditionMapper = cpuConditionMapper;
        this.memConditionMapper = memConditionMapper;
        this.diskConditionMapper = diskConditionMapper;
        this.tempConditionMapper = tempConditionMapper;
        InfoEntity infoEntity = infoMapper.getByName(name);
        this.id = infoEntity.getId();
        generateCpuLoad();
        generateMemInfo();
        generateDiskInfo();
        if(infoEntity.getEnableTemp()) {
            generateTemp();
        }
    }

    public String getCpuLoad() {
        return String.valueOf(this.cpuload);
    }

    public String getMemSum() {
        if(this.memsum < 1000) {
            return String.valueOf(this.memsum)+"KB";
        }
        else if(this.memsum < 1000000) {
            return String.format("%.2f", this.memsum/1024.0f)+"MB";
        }
        else {
            return String.format("%.2f", this.memsum/1024.0f/1024.0f)+"GB";
        }
    }

    public String getMemUsed() {
        if(this.memused < 1000) {
            return String.valueOf(this.memused)+"KB";
        }
        else if(this.memused < 1000000) {
            return String.format("%.2f", this.memused/1024.0f)+"MB";
        }
        else {
            return String.format("%.2f", this.memused/1024.0f/1024.0f)+"GB";
        }
    }

    public String getSwapSum() {
        if(this.swapsum < 1000) {
            return String.valueOf(this.swapsum)+"KB";
        }
        else if(this.swapsum < 1000000) {
            return String.format("%.2f", this.swapsum/1024.0f)+"MB";
        }
        else {
            return String.format("%.2f", this.swapsum/1024.0f/1024.0f)+"GB";
        }
    }

    public String getSwapUsed() {
        if(this.swapused < 1000) {
            return String.valueOf(this.swapused)+"KB";
        }
        else if(this.swapused < 1000000) {
            return String.format("%.2f", this.swapused/1024.0f)+"MB";
        }
        else {
            return String.format("%.2f", this.swapused/1024.0f/1024.0f)+"GB";
        }
    }

    public String getDiskSum() {
        if(this.disksum < 1000) {
            return String.valueOf(this.disksum)+"KB";
        }
        else if(this.disksum < 1000000) {
            return String.format("%.2f", this.disksum/1024.0f)+"MB";
        }
        else {
            return String.format("%.2f", this.disksum/1024.0f/1024.0f)+"GB";
        }
    }

    public String getDiskUsed() {
        if(this.diskused < 1000) {
            return String.valueOf(this.diskused)+"KB";
        }
        else if(this.diskused < 1000000) {
            return String.format("%.2f", this.diskused/1024.0f)+"MB";
        }
        else {
            return String.format("%.2f", this.diskused/1024.0f/1024.0f)+"GB";
        }
    }

    public String getTemp() {
        return String.format("%.2f", this.temp)+"℃";
    }

    private void generateCpuLoad() {
        CpuConditionEntity cpuConditionEntity = cpuConditionMapper.getLastOneById(id);
        this.cpuload = cpuConditionEntity.getLoad();
    }

    private void generateMemInfo() {
        MemConditionEntity memConditionEntity = memConditionMapper.getLastOneById(id);
        this.memsum = memConditionEntity.getMemSum();
        this.memused = memConditionEntity.getMemUsed();
        this.swapsum = memConditionEntity.getSwapSum();
        this.swapused = memConditionEntity.getSwapUsed();
    }

    private void generateDiskInfo() {
        DiskConditionEntity diskConditionEntity = diskConditionMapper.getLastOneById(id);
        this.disksum = diskConditionEntity.getSum();
        this.diskused = diskConditionEntity.getUsed();
    }

    private void generateTemp() {
        TempConditionEntity tempConditionEntity = tempConditionMapper.getLastOneById(id);
        this.temp = tempConditionEntity.getTemp();
    }
}
