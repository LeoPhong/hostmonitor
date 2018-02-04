package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import monitor.entity.MemConditionEntity;
import monitor.mapper.InfoMapper;
import monitor.mapper.MemConditionMapper;

public class MemService {
    private InfoMapper infoMapper;
    private MemConditionMapper memConditionMapper;

    @Autowired
    public MemService(InfoMapper infoMapper, MemConditionMapper memConditionMapper) {
        this.infoMapper = infoMapper;
        this.memConditionMapper = memConditionMapper;
    }

    public HashMap<Timestamp, Long>getMemUsed(String hostname, long duringMin) {
        HashMap<Timestamp, Long> mem_used = new HashMap<Timestamp, Long>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0) {
            MemConditionEntity memConditionEntity = memConditionMapper.getLastOneById(server_id);
            mem_used.put(memConditionEntity.getCurTime(), memConditionEntity.getMemUsed());
        }
        else{
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<MemConditionEntity> mem_stat_list = memConditionMapper.getByDuringTime(server_id, start_time);
            for(int index = 0; index < mem_stat_list.size(); index ++) {
                Timestamp time_point = mem_stat_list.get(index).getCurTime();
                long mem_value = mem_stat_list.get(index).getMemUsed();
                mem_used.put(time_point, mem_value);
            }
        }
        return mem_used;
    }

    public HashMap<Timestamp, Long>getMemSum(String hostname, long duringMin) {
        HashMap<Timestamp, Long>mem_sum = new HashMap<Timestamp, Long>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0) {
            MemConditionEntity memConditionEntity = memConditionMapper.getLastOneById(server_id);
            mem_sum.put(memConditionEntity.getCurTime(), memConditionEntity.getMemSum());
        }
        else {
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<MemConditionEntity>mem_stat_list = memConditionMapper.getByDuringTime(server_id, start_time);
            for(int index = 0; index < mem_stat_list.size(); index++) {
                Timestamp time_point = mem_stat_list.get(index).getCurTime();
                long mem_value = mem_stat_list.get(index).getMemSum();
                mem_sum.put(time_point, mem_value);
            }
        }
        return mem_sum;
    }

    public HashMap<Timestamp, Long>getSwapUsed(String hostname, long duringMin) {
        HashMap<Timestamp, Long> swap_used = new HashMap<Timestamp, Long>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0) {
            MemConditionEntity memConditionEntity = memConditionMapper.getLastOneById(server_id);
            swap_used.put(memConditionEntity.getCurTime(), memConditionEntity.getSwapUsed());
        }
        else{
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<MemConditionEntity> swap_stat_list = memConditionMapper.getByDuringTime(server_id, start_time);
            for(int index = 0; index < swap_stat_list.size(); index ++) {
                Timestamp time_point = swap_stat_list.get(index).getCurTime();
                long swap_value = swap_stat_list.get(index).getSwapUsed();
                swap_used.put(time_point, swap_value);
            }
        }
        return swap_used;
    }

    public HashMap<Timestamp, Long>getSwapSum(String hostname, long duringMin) {
        HashMap<Timestamp, Long>swap_sum = new HashMap<Timestamp, Long>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0) {
            MemConditionEntity memConditionEntity = memConditionMapper.getLastOneById(server_id);
            swap_sum.put(memConditionEntity.getCurTime(), memConditionEntity.getSwapSum());
        }
        else {
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<MemConditionEntity>swap_stat_list = memConditionMapper.getByDuringTime(server_id, start_time);
            for(int index = 0; index < swap_stat_list.size(); index++) {
                Timestamp time_point = swap_stat_list.get(index).getCurTime();
                long swap_value = swap_stat_list.get(index).getSwapSum();
                swap_sum.put(time_point, swap_value);
            }
        }
        return swap_sum;
    }

    public long getMemUsed(String hostname) {
        HashMap<Timestamp, Long> last_mem_used = getMemUsed(hostname, 0);
        Timestamp time_point = last_mem_used.keySet().iterator().next();
        return last_mem_used.get(time_point);
    }

    public long getMemSum(String hostname) {
        HashMap<Timestamp, Long>last_mem_sum = getMemSum(hostname, 0);
        Timestamp time_point = last_mem_sum.keySet().iterator().next();
        return last_mem_sum.get(time_point);
    }

    public long getSwapUsed(String hostname) {
        HashMap<Timestamp, Long> last_swap_used = getSwapUsed(hostname, 0);
        Timestamp time_point = last_swap_used.keySet().iterator().next();
        return last_swap_used.get(time_point);
    }

    public long getSwapSum(String hostname) {
        HashMap<Timestamp, Long> last_swap_sum = getSwapSum(hostname, 0);
        Timestamp time_point = last_swap_sum.keySet().iterator().next();
        return last_swap_sum.get(time_point);
    }
}