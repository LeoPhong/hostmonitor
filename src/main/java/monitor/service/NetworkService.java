package monitor.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import monitor.entity.NetConditionEntity;
import monitor.mapper.NetConditionMapper;
import monitor.mapper.InfoMapper;
import monitor.entity.InfoEntity;

@Service
public class NetworkService {
    private InfoMapper infoMapper;
    private NetConditionMapper netconditionMapper;

    @Autowired
    public NetworkService(InfoMapper infoMapper, NetConditionMapper netConditionMapper) {
        this.infoMapper = infoMapper;
        this.netconditionMapper = netConditionMapper;
    }

    public HashMap<Timestamp,Double> getRxSpeed(String hostname, String devicename, long duringMin) {
        HashMap<Timestamp,Double> rx_speed_cluster = new HashMap<Timestamp,Double>();
        InfoEntity infoEntity= this.infoMapper.getByName(hostname);
        int server_id = infoEntity.getId();
        if(duringMin == 0) {
            List<NetConditionEntity>net_stat_last_two = netconditionMapper.getLastTwoById(server_id, devicename);
            Timestamp time1 = net_stat_last_two.get(0).getCurTime();
            Timestamp time2 = net_stat_last_two.get(1).getCurTime();
            long sum1 = net_stat_last_two.get(0).getRxBytes();
            long sum2 = net_stat_last_two.get(1).getRxBytes();
            double rx_speed = calSpeed(time1, sum1, time2, sum2);
            rx_speed_cluster.put(time2, rx_speed);
        }
        else {
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<NetConditionEntity>net_stat_list =  netconditionMapper.getByDuringTime(start_time, server_id, devicename);
            for(int index = 1; index < net_stat_list.size(); index++) {
                Timestamp time1 = net_stat_list.get(index-1).getCurTime();
                Timestamp time2 = net_stat_list.get(index).getCurTime();
                long sum1 = net_stat_list.get(index-1).getRxBytes();
                long sum2 = net_stat_list.get(index).getRxBytes();
                double rx_speed = calSpeed(time1, sum1, time2, sum2);
                rx_speed_cluster.put(time2, rx_speed);
            }
        }
        return rx_speed_cluster;
    }

    public HashMap<Timestamp, Double> getTxSpeed(String hostname, String devicename, long duringMin) {
        HashMap<Timestamp, Double>tx_speed_cluster = new HashMap<Timestamp, Double>();
        InfoEntity infoEntity = this.infoMapper.getByName(hostname);
        int server_id = infoEntity.getId();
        if(duringMin == 0) {
            List<NetConditionEntity>net_stat_last_two = netconditionMapper.getLastTwoById(server_id, devicename);
            Timestamp time1 = net_stat_last_two.get(0).getCurTime();
            Timestamp time2 = net_stat_last_two.get(1).getCurTime();
            long sum1 = net_stat_last_two.get(0).getTxBytes();
            long sum2 = net_stat_last_two.get(1).getTxBytes();
            double tx_speed = calSpeed(time1, sum1, time2, sum2);
            tx_speed_cluster.put(time2, tx_speed);
        }
        else {
            Timestamp start_time = new Timestamp(System.currentTimeMillis() - duringMin*60*1000);
            List<NetConditionEntity>net_stat_list =  netconditionMapper.getByDuringTime(start_time, server_id, devicename);
            for(int index = 1; index < net_stat_list.size(); index++) {
                Timestamp time1 = net_stat_list.get(index-1).getCurTime();
                Timestamp time2 = net_stat_list.get(index).getCurTime();
                long sum1 = net_stat_list.get(index-1).getTxBytes();
                long sum2 = net_stat_list.get(index).getTxBytes();
                double tx_speed = calSpeed(time1, sum1, time2, sum2);
                tx_speed_cluster.put(time2, tx_speed);
            }
        }
        return tx_speed_cluster;
    }

    private double calSpeed(Timestamp time1,long sum1, Timestamp time2, long sum2) {
        if(sum2<sum1) {
            return 0.0f;
        }
        else{
            return (sum2-sum1)*1000/(time2.getTime()-time1.getTime());
        }
    }
}