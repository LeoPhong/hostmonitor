package monitor.job;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import monitor.lib.SshCommandExecutor;
import monitor.lib.StringOperator;
import monitor.job.InfoBase;
import monitor.mapper.DiskConditionMapper;
import monitor.entity.DiskConditionEntity;

@Component
public class DiskConditionJob {
    private final static long interval = 60 * 1000;

    @Autowired
    private DiskConditionMapper diskConditionMapper;

    @Scheduled(fixedRate = interval)
    public void diskConditionJob() throws Exception {
        System.out.println("The job of DiskCondition is started...");
        int server_num = InfoBase.getServerNumber();
        for (int index = 0; index < server_num; index++) {
            long[] disk_info = new long[2];
            int server_id = InfoBase.getElementId(index);
            SshCommandExecutor ssh_command_executor = InfoBase.getElementSession(index);
            try {
                disk_info = getDiskCondition(ssh_command_executor);
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
            Timestamp cur_time = new Timestamp(System.currentTimeMillis());
            diskConditionMapper.insert(new DiskConditionEntity(cur_time, server_id, disk_info[0], disk_info[1]));
        }
    }

    private static long[] getDiskCondition(SshCommandExecutor ssh_command_executor) {
        long[] disk_info = new long[2];
        long sum_KB = 0;
        long used_KB = 0;
        String[] df_result = ssh_command_executor.executeCmd("df").split("\n");
        for (int index = 0; index < df_result.length; index++) {
            String line = df_result[index];
            if (line.endsWith("/")) {
                used_KB = Long.parseLong(StringOperator.removeSpace(line).split(" ")[2]);
                sum_KB = Long.parseLong(StringOperator.removeSpace(line).split(" ")[1]);
            }
        }
        disk_info[0] = used_KB;
        disk_info[1] = sum_KB;
        return disk_info;
    }
}
