package monitor.job;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import monitor.entity.TempConditionEntity;
import monitor.lib.SshCommandExecutor;
import monitor.mapper.TempConditionMapper;
import monitor.job.InfoBase;

@Component
public class TempConditionJob {
    private final static long interval = 5 * 1000;

    @Autowired
    TempConditionMapper tempConditionMapper;

    @Scheduled(fixedRate = interval)
    public void tempConditionJob() throws Exception {
        System.out.println("The job of TempCondition is started...");
        int server_num = InfoBase.getServerNumber();
        for (int index = 0; index < server_num; index++) {
            double temp = 0.0f;
            /* 从数据库配置矩阵中读取该主机是否需要采集温度 */
            if (InfoBase.getElementEnableTemp(index)) {
                int server_id = InfoBase.getElementId(index);
                Timestamp cur_time = new Timestamp(System.currentTimeMillis());
                SshCommandExecutor ssh_command_executor = InfoBase.getElementSession(index);
                try{
                    temp = getTemp(ssh_command_executor);
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
                tempConditionMapper.insert(new TempConditionEntity(cur_time, server_id, temp));
            }
        }
    }

    private static double getTemp(SshCommandExecutor ssh_command_executor) {
        /* 由于不同主机温度采集的方法不同，目前只有树莓派支持温度采集 */
        double host_temp = 0f;
        String cat_result = ssh_command_executor.executeCmd("cat /sys/class/thermal/thermal_zone0/temp");
        host_temp = Double.parseDouble(cat_result) / 1000.0f;
        return host_temp;
    }
}
