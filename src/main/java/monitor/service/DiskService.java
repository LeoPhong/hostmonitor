package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import monitor.entity.DiskConditionEntity;
import monitor.mapper.DiskConditionMapper;
import monitor.mapper.InfoMapper;

@Service
public class DiskService {
    private InfoMapper infoMapper;
    private DiskConditionMapper diskConditionMapper;

    @Autowired
    public DiskService(InfoMapper infoMapper, DiskConditionMapper diskConditionMapper) {
        this.infoMapper = infoMapper;
        this.diskConditionMapper = diskConditionMapper;
    }

    public HashMap<Timestamp, Long>getDiskUsed(String hostname, long duringMin) {
        HashMap<Timestamp, Long> disk_used = new HashMap<Timestamp, Long>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0) {
            DiskConditionEntity diskConditionEntity = diskConditionMapper.getLastOneById(server_id);
            disk_used.put(diskConditionEntity.getCurTime(), diskConditionEntity.getUsed());
        }
        else{
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<DiskConditionEntity> disk_stat_list = diskConditionMapper.getDuringTime(server_id, start_time);
            for(int index = 0; index < disk_stat_list.size(); index ++) {
                Timestamp time_point = disk_stat_list.get(index).getCurTime();
                long disk_value = disk_stat_list.get(index).getUsed();
                disk_used.put(time_point, disk_value);
            }
        }
        return disk_used;
    }

    public HashMap<Timestamp, Long>getDiskSum(String hostname, long duringMin) {
        HashMap<Timestamp, Long>disk_sum = new HashMap<Timestamp, Long>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0) {
            DiskConditionEntity diskConditionEntity = diskConditionMapper.getLastOneById(server_id);
            disk_sum.put(diskConditionEntity.getCurTime(), diskConditionEntity.getSum());
        }
        else {
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<DiskConditionEntity>disk_stat_list = diskConditionMapper.getDuringTime(server_id, start_time);
            for(int index = 0; index < disk_stat_list.size(); index++) {
                Timestamp time_point = disk_stat_list.get(index).getCurTime();
                long disk_value = disk_stat_list.get(index).getSum();
                disk_sum.put(time_point, disk_value);
            }
        }
        return disk_sum;
    }

    public long getLastDiskUsed(String hostname) {
        HashMap<Timestamp, Long> last_disk_used = getDiskUsed(hostname, 0);
        Timestamp time_point = last_disk_used.keySet().iterator().next();
        return last_disk_used.get(time_point);
    }

    public long getLastDiskSum(String hostname) {
        HashMap<Timestamp, Long> last_disk_sum = getDiskSum(hostname, 0);
        Timestamp time_point = last_disk_sum.keySet().iterator().next();
        return last_disk_sum.get(time_point);
    }
}