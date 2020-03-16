package com.wangyang.web.Controller.admin;

import com.wangyang.web.Model.User;
import com.wangyang.web.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user!=null&&user.getType().equals("1")){
            user.setPassword(null); //  不将密码传入Session，保证安全性
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            //redirect重定向返还的内容信息，只能通过RedirectAttributes对像转发，使用ModelAndView无效果
            attributes.addFlashAttribute("errorMessage","用户名或密码错误！");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

}
