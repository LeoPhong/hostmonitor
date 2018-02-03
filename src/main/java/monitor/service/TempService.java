package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import monitor.entity.TempConditionEntity;
import monitor.mapper.InfoMapper;
import monitor.mapper.TempConditionMapper;

@Service
public class TempService {
    private InfoMapper infoMapper;
    private TempConditionMapper tempConditionMapper;

    @Autowired
    public TempService(InfoMapper infoMapper, TempConditionMapper tempConditionMapper) {
        this.infoMapper = infoMapper;
        this.tempConditionMapper = tempConditionMapper;
    }

    public HashMap<Timestamp, Double> getTemp(String hostname, long duringMin) {
        HashMap<Timestamp, Double> temp_status = new HashMap<Timestamp, Double>();
        int server_id = infoMapper.getByName(hostname).getId();
        if(duringMin == 0) {
            TempConditionEntity tempConditionEntity = tempConditionMapper.getLastOneById(server_id);
            temp_status.put(tempConditionEntity.getCurTime(), tempConditionEntity.getTemp());
            System.out.println(tempConditionEntity.getCurTime());
            System.out.println(tempConditionEntity.getTemp());
        }
        else {
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<TempConditionEntity> temp_entity_list = tempConditionMapper.getDuringTime(server_id, start_time);
            for(int index = 0; index < temp_entity_list.size(); index ++) {
                Timestamp time_point = temp_entity_list.get(index).getCurTime();
                double temp = temp_entity_list.get(index).getTemp();
                System.out.println(time_point);
                System.out.println(temp);
                temp_status.put(time_point, temp);
            }
        }
        return temp_status;
    }
}