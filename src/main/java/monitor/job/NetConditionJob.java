package monitor.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import monitor.entity.NetConditionEntity;
import monitor.lib.SshCommandExecutor;
import monitor.mapper.NetConditionMapper;
import monitor.job.InfoBase;

@Component
public class NetConditionJob {
    private final static long interval = 3 * 1000;

    @Autowired
    NetConditionMapper netConditionMapper;

    @Scheduled(fixedRate = interval)
    public void netConditionJob() throws Exception {
        System.out.println("The job of NetCondition is started...");
        int server_num = InfoBase.getServerNumber();
        for (int index = 0; index < server_num; index++) {
            List<HashMap<String, Object>> net_device_info_list = new ArrayList<HashMap<String, Object>>();
            int server_id = InfoBase.getElementId(index);
            SshCommandExecutor ssh_command_executor = InfoBase.getElementSession(index);
            try{
                net_device_info_list = getNetCondition(ssh_command_executor);
            }
            catch(NumberFormatException e) {
                System.out.println("Maybe the ssh session is not valid...");
                InfoBase.setInfoValid(false);
                throw e;
            }
            catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Maybe the ssh session is not valid...");
                InfoBase.setInfoValid(false);
                throw e;
            }
            for (int i = 0; i < net_device_info_list.size(); i++) {
                //System.out.println(net_device_info_list.get(i).get("rx_bytes"));
                Timestamp cur_time = Timestamp.valueOf(String.valueOf(net_device_info_list.get(i).get("cur_time")));
                String device = String.valueOf(net_device_info_list.get(i).get("device"));
                long rx_bytes = Long.parseLong(String.valueOf(net_device_info_list.get(i).get("rx_bytes")));
                long tx_bytes = Long.parseLong(String.valueOf(net_device_info_list.get(i).get("tx_bytes")));
                netConditionMapper.insert(new NetConditionEntity(cur_time, server_id, device, rx_bytes, tx_bytes));
            }
        }
    }

    private static List<HashMap<String, Object>> getNetCondition(SshCommandExecutor ssh_command_executor)
            throws Exception {
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        String[] net_devices = ssh_command_executor.executeCmd("ls /sys/class/net/").split("\n");
        for (int index = 0; index < net_devices.length; index++) {
            HashMap<String, Object> net_device_info = new HashMap<String, Object>();
            Timestamp cur_time = new Timestamp(System.currentTimeMillis());
            long rx_bytes = Long.parseLong(ssh_command_executor
                    .executeCmd("cat /sys/class/net/" + net_devices[index] + "/statistics/rx_bytes").split("\n")[0]);
            long tx_bytes = Long.parseLong(ssh_command_executor
                    .executeCmd("cat /sys/class/net/" + net_devices[index] + "/statistics/tx_bytes").split("\n")[0]);
            if (rx_bytes != 0L && tx_bytes != 0L) {
                net_device_info.put("device", net_devices[index]);
                net_device_info.put("rx_bytes", rx_bytes);
                net_device_info.put("tx_bytes", tx_bytes);
                net_device_info.put("cur_time", cur_time);
                result.add(net_device_info);
            }
        }
        return result;
    }
}
