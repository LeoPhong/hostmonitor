package monitor.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import monitor.entity.CpuConditionEntity;
import monitor.mapper.CpuConditionMapper;
import monitor.mapper.InfoMapper;

@Service
public class CpuService {
    private InfoMapper infoMapper;
    private CpuConditionMapper cpuConditionMapper;

    @Autowired
    public CpuService(InfoMapper infoMapper, CpuConditionMapper cpuConditionMapper) {
        this.infoMapper = infoMapper;
        this.cpuConditionMapper = cpuConditionMapper;
    }

    public HashMap<Timestamp, Double> getCpuLoad(String hostname, long duringMin) {
        HashMap<Timestamp, Double>cpu_load = new HashMap<Timestamp, Double>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0){
            CpuConditionEntity cpuConditionEntity = cpuConditionMapper.getLastOneById(server_id);
            cpu_load.put(cpuConditionEntity.getCurTime(), cpuConditionEntity.getLoad());
        }
        else {
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<CpuConditionEntity> cpu_stat_list = cpuConditionMapper.getByDuringTime(server_id, start_time);
            for(int index = 0; index < cpu_stat_list.size(); index++) {
                Timestamp time_point = cpu_stat_list.get(index).getCurTime();
                double load_value = cpu_stat_list.get(index).getLoad();
                cpu_load.put(time_point, load_value);
            }
        }
        return cpu_load;
    }
}