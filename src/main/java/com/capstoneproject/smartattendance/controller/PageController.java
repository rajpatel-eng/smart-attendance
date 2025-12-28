package com.capstoneproject.smartattendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    @GetMapping({"/","/login"})
    public String loginPage() {
        return "login"; 
    }
    @GetMapping({"/register"})
    public String registerPage() {
        return "register"; 
    }
    @GetMapping({"/admin/dashboard"})
    public String adminDashboardPage() {
        return "admin-dashboard"; 
    }
    @GetMapping({"/forgotpassword"})
    public String forgotPasswordPage() {
        return "forgot-password"; 
    }
}


