package monitor.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import monitor.lib.UnitConverter;
import monitor.service.CpuService;
import monitor.service.DiskService;
import monitor.service.InfoService;
import monitor.service.MemService;
import monitor.service.NetworkService;
import monitor.service.TempService;

@Controller
@RequestMapping(value = "/monitor")
public class MonController {
    @Autowired
    InfoService infoservice;
    @Autowired
    CpuService cpuService;
    @Autowired
    DiskService diskService;
    @Autowired
    MemService memService;
    @Autowired
    NetworkService networkService;
    @Autowired
    TempService tempService;

    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    public String overview(Model model) {
        model.addAttribute("linux_server_overview", getHostOverview("LinuxServer"));
        return "monoverview";
    }

    private HashMap<String, String> getHostOverview(String hostname) {
        HashMap<String, String> overview = new HashMap<String, String>();
        double cpu_load = cpuService.getLastCpuLoad(hostname);
        overview.put("cpuLoad", String.valueOf(cpu_load));

        long disk_used = diskService.getLastDiskUsed(hostname);
        overview.put("diskUsed", UnitConverter.convertUnit(disk_used));
        long disk_sum = diskService.getLastDiskSum(hostname);
        overview.put("diskSum", UnitConverter.convertUnit(disk_sum));
        
        long mem_used = memService.getMemUsed(hostname);
        overview.put("memUsed", UnitConverter.convertUnit(mem_used));
        long mem_sum = memService.getMemSum(hostname);
        overview.put("memSum", UnitConverter.convertUnit(mem_sum));
        long swap_used = memService.getSwapUsed(hostname);
        overview.put("swapUsed",UnitConverter.convertUnit(swap_used));
        long swap_sum = memService.getSwapSum(hostname);
        overview.put("swapSum", UnitConverter.convertUnit(swap_sum));

        if(hostname.equals("Router")) {
            double ppp0_rx_speed = networkService.getLastRxSpeed(hostname, "ppp0");
            double ppp0_tx_speed = networkService.getLastTxSpeed(hostname, "ppp0");
            double wlan0_rx_speed = networkService.getLastRxSpeed(hostname, "wlan0");
            double wlan0_tx_speed = networkService.getLastTxSpeed(hostname, "wlan0");
            overview.put("netSpeed", 
                "ppp0:"+UnitConverter.convertUnit(ppp0_rx_speed)+"/"+UnitConverter.convertUnit(ppp0_tx_speed)
                +" | wlan0:"+UnitConverter.convertUnit(wlan0_rx_speed)+"/"+UnitConverter.convertUnit(wlan0_tx_speed));
        }
        else {
            double ens9f1_rx_speed = networkService.getLastRxSpeed(hostname, "ens9f1");
            double ens9f1_tx_speed = networkService.getLastTxSpeed(hostname, "ens9f1");
            overview.put("netSpeed", UnitConverter.convertUnit(ens9f1_rx_speed)+"/"+UnitConverter.convertUnit(ens9f1_tx_speed));
        }

        if(infoservice.getTempEnable(hostname)) {
            overview.put("temp", UnitConverter.convertTempUnit(tempService.getLastTemp(hostname)));
        }
        return overview;
    }
}