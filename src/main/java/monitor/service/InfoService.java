package monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import monitor.mapper.InfoMapper;

@Service
public class InfoService{
    private InfoMapper infoMapper;

    @Autowired
    public InfoService(InfoMapper infoMapper) {
        this.infoMapper = infoMapper;
    }

    public int getServerId(String hostname) {
        return infoMapper.getByName(hostname).getId();
    }

    public boolean getTempEnable(String hostname) {
        return infoMapper.getByName(hostname).getEnableTemp();
    }
}