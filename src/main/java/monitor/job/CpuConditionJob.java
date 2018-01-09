package monitor.job;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import monitor.lib.SshCommandExecutor;
import monitor.mapper.CpuConditionMapper;
import monitor.entity.CpuConditionEntity;
import monitor.job.InfoBase;

@Component
public class CpuConditionJob {
    private final static long interval = 5*1000;

    @Autowired
    private CpuConditionMapper cpuConditionMapper;


    @Scheduled(fixedRate = interval)
    public void cpuConditionJob() throws Exception {
        System.out.println("The job of CpuConditioin is started...");
        int server_num = InfoBase.getServerNumber();
        for(int i = 0; i < server_num; i++) {
            double load  = 0.0f;
            int server_id = InfoBase.getElementId(i);
            SshCommandExecutor ssh_command_executor = InfoBase.getElementSession(i);
            try {
                load = getCpuCondition(ssh_command_executor);
            }
            catch (NumberFormatException e) {
                System.out.println("Maybe the ssh session is not valid...");
                InfoBase.setInfoValid(false);
                throw e;
            }
            catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Maybe the ssh session is not valid...");
                InfoBase.setInfoValid(false);
                throw e;
            }
            Timestamp curTime = new Timestamp(System.currentTimeMillis());
            cpuConditionMapper.insert(new CpuConditionEntity(curTime, server_id, load));
        }
    }

    private static double getCpuCondition(SshCommandExecutor ssh_command_executor) {
        double load_val = Double.parseDouble(
            ssh_command_executor.executeCmd("uptime").split("load average: ")[1].split(", ")[0]);
        double processers_num = Double.parseDouble(
            ssh_command_executor.executeCmd("cat /proc/cpuinfo | grep processor | wc -l"));
        double cpu_load = load_val / processers_num;
        return cpu_load;
    }
}
