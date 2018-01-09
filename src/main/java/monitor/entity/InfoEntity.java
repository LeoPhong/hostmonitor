package monitor.entity;

import java.io.Serializable;

public class InfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String username;
    private String password;
    private String ipAddress;
    private int port;
    private boolean online;
    private boolean enableTemp;

    public InfoEntity() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean getEnableTemp() {
        return enableTemp;
    }

    public void setEnableTemp(boolean enableTemp) {
        this.enableTemp = enableTemp;
    }
}