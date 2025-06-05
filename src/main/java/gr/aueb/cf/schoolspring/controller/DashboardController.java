package gr.aueb.cf.schoolspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/school")
public class DashboardController {

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "dashboard";
    }

}
