package monitor.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshCommandExecutor {
    private Session session = null;

    public SshCommandExecutor(String username, String ip_address, int port, String password) throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, ip_address, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        try {
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            session.disconnect();
            throw e;
        }
    }

    public String executeCmd(String cmd) {
        Channel channel = null;
        try {
            channel = session.openChannel("exec");
        } catch (JSchException e) {
            e.printStackTrace();
            session.disconnect();
            return "[Error] JSch opening channel is failed!";
        }

        ((ChannelExec) channel).setCommand(cmd);

        channel.setInputStream(null);

        ((ChannelExec) channel).setErrStream(System.err);

        BufferedReader input = null;
        try {
            InputStream in = channel.getInputStream();
            input = new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            e.printStackTrace();
            session.disconnect();
            return "[Error] Getting input stream is failed!";
        }

        try {
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
            channel.disconnect();
            session.disconnect();
            return "[Error] JSch connecting channel is failed!";
        }

        String result = "";
        String line = "";
        try {
            while ((line = input.readLine()) != null) {
                result += line;
                result += "\n";
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            channel.disconnect();
            session.disconnect();
            return "[Error] Reading result or colsing input channel is failed!";
        }

        channel.disconnect();
        return result;
    }

    public void disconnectSession() {
        session.disconnect();
    }

    protected void finalize() {
        disconnectSession();
    }
}
