package monitor.job;

import java.util.List;
import java.util.Vector;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import monitor.entity.InfoEntity;
import monitor.mapper.InfoMapper;
import monitor.lib.SshCommandExecutor;
import monitor.lib.AesCoder;
import monitor.lib.DataTypeConverter;

@Component
public class InfoBase {
    private static String password_key;

    private static List<HashMap<String, Object>> server_connection_info_list = new Vector<HashMap<String, Object>>();;
    private static boolean isValid = false;
    private static InfoMapper infoMapper;

    public InfoBase(@Autowired InfoMapper infoMapper, @Value("${host.info.password_key}")String password_key) {
        InfoBase.infoMapper = infoMapper;
        InfoBase.password_key = password_key;
    }

    public static int getElementId(int index) throws Exception {
        setServerInfoWithLock();
        return Integer.parseInt(server_connection_info_list.get(index).get("id").toString());
    }

    public static SshCommandExecutor getElementSession(int index) throws Exception {
        setServerInfoWithLock();
        return (SshCommandExecutor) server_connection_info_list.get(index).get("ssh_command_executor");
    }

    public static boolean getElementOnline(int index) throws Exception {
        setServerInfoWithLock();
        return Boolean.parseBoolean(server_connection_info_list.get(index).get("online").toString());
    }

    public static boolean getElementEnableTemp(int index) throws Exception {
        setServerInfoWithLock();
        return Boolean.parseBoolean(server_connection_info_list.get(index).get("enable_temp").toString());
    }

    public static int getServerNumber() throws Exception {
        setServerInfoWithLock();
        return server_connection_info_list.size();
    }

    public static void setInfoValid(boolean valid) {
        isValid = valid;
    }

    private static void setServerInfoWithLock() throws Exception {
        if (isValid == false) {
            setServerInfo();
            isValid = true;
        }
    }

    /* For getting server id and server connection. */
    private static void setServerInfo() throws Exception {
        server_connection_info_list.clear();
        List<InfoEntity> server_opt_info_list = infoMapper.getByOnline(true);
        int server_num = server_opt_info_list.size();
        for (int index = 0; index < server_num; index++) {
            HashMap<String, Object> server_connection_info = new HashMap<String, Object>();
            int server_id = server_opt_info_list.get(index).getId();
            String username = server_opt_info_list.get(index).getUsername();
            String ip_address = server_opt_info_list.get(index).getIpAddress();
            int port = server_opt_info_list.get(index).getPort();
            String password_encryped = server_opt_info_list.get(index).getPassword();
            String password = new String(AesCoder.decrypt(DataTypeConverter.hex2ByteArray(password_encryped),
                    DataTypeConverter.hex2ByteArray(password_key)));
            boolean online = server_opt_info_list.get(index).getOnline();
            boolean enable_temp = server_opt_info_list.get(index).getEnableTemp();
            SshCommandExecutor ssh_command_executor = new SshCommandExecutor(username, ip_address, port, password);
            server_connection_info.put("id", server_id);
            server_connection_info.put("online", online);
            server_connection_info.put("enable_temp", enable_temp);
            server_connection_info.put("ssh_command_executor", ssh_command_executor);
            server_connection_info_list.add(server_connection_info);
        }
    }
}
