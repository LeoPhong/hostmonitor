package monitor.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import monitor.entity.MemConditionEntity;
import monitor.lib.SshCommandExecutor;
import monitor.lib.StringOperator;
import monitor.mapper.MemConditionMapper;

@Component
public class MemConditionJob {
    private final static long interval = 5 * 1000;

    @Autowired
    MemConditionMapper memConditionMapper;

    @Scheduled(fixedRate = interval)
    public void memConditionJob() throws Exception {
        System.out.println("The job of MemCondition is started...");
        int server_num = InfoBase.getServerNumber();
        for (int index = 0; index < server_num; index++) {
            long[] mem_condition = new long[4];
            Timestamp cur_time = new Timestamp(System.currentTimeMillis());
            SshCommandExecutor ssh_command_executor = InfoBase.getElementSession(index);
            int server_id = InfoBase.getElementId(index);
            try {
                mem_condition = getMemCondition(ssh_command_executor);
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
            memConditionMapper.insert(new MemConditionEntity(cur_time, server_id, mem_condition[0], mem_condition[1],
                    mem_condition[2], mem_condition[3]));

        }
    }

    private static long[] getMemCondition(SshCommandExecutor ssh_command_executor) {
        long[] mem_condition = new long[] { 0L, 0L, 0L, 0L };
        String[] free_result = ssh_command_executor.executeCmd("free -b").split("\n");
        mem_condition[0] = Long.parseLong(StringOperator.removeSpace(free_result[1]).split(" ")[2]);
        mem_condition[1] = Long.parseLong(StringOperator.removeSpace(free_result[1]).split(" ")[1]);
        mem_condition[2] = Long.parseLong(StringOperator.removeSpace(free_result[2]).split(" ")[2]);
        mem_condition[3] = Long.parseLong(StringOperator.removeSpace(free_result[2]).split(" ")[1]);
        return mem_condition;
    }
}
