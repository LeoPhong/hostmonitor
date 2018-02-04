package monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import monitor.service.OverviewService;

@Controller
@RequestMapping(value = "/monitor")
public class MonController {

    @Autowired
    OverviewService overview;

    @RequestMapping(value = "/overview", method = RequestMethod.GET)
    public String overview(Model model) {
        model.addAttribute("router_overview", overview.get("Router"));
        model.addAttribute("linux_server_overview", overview.get("LinuxServer"));
        return "monoverview";
    }
}