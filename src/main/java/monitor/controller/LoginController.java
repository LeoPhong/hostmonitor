package monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import monitor.lib.GoogleAuthOperator;

@Controller
@RequestMapping("/login")
public class LoginController {
    //private final static String name = "leophong";

    @RequestMapping(method = RequestMethod.GET)
    public String getLoginPage(String error, Model model) {
        if(error != null) {
            model.addAttribute("is_login_failed", true);
        }
        else {
            model.addAttribute("is_login_failed", false);
        }
        return "login";
    }

/*    @RequestMapping(method = RequestMethod.POST)
    public String postLginInfo(@ModelAttribute(value="username") String username, @ModelAttribute(value="password")String password, Model model) {
        if(username.equals(name) && GoogleAuthOperator.authorize(Integer.parseInt(password))) {
            return "redirect:/monitor/overview";
        }
        else {
            model.addAttribute("is_login_failed", true);
            return "login";
        }
    }*/
}