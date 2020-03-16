package com.wangyang.web.Controller.tourist;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wangyang.web.Model.Speak;
import com.wangyang.web.Model.User;
import com.wangyang.web.Service.TouristService;
import com.wangyang.web.Service.UserService;

@Controller
@RequestMapping("/tourist")
public class TouristLoginController {
	@Autowired
	private UserService userservice;
	@Autowired
	private TouristService touristservice;
	
	@GetMapping
	public String toLogin() {//跳至游客登录页面
		return "tourist/login";
	}
	
	@GetMapping("/register")
	public String tourisRegister() {//跳至游客注册页面
		return "tourist/register";
	}
	
	@GetMapping("/speak")
	public String toSpeak() {//跳至游客留言页面
		return "tourist/speak-input";
	}
	
	//发布新留言
	@PostMapping("/saveSpeak")
	public String saveSpeak(@RequestParam String content,HttpSession session,
			RedirectAttributes attributes,Model model) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			attributes.addFlashAttribute("errorMessage", "请先登录");
			return "redirect:/tourist";
		}else {
			Speak speak = new Speak();
			speak.setUser(user);
			speak.setContent(content);
			Speak speak0 = touristservice.savespeak(speak);
			if(speak0!=null) {//新增成功
				model.addAttribute("message", "新增成功");
				return "/tourist/speak-input";
			}else {//新增失败
				model.addAttribute("message", "新增失败");
				return "/tourist/speak-input";
			}
		}
	}
	
	//游客用户注册
	@PostMapping("/saveTourist")
	public String saveTourist(@RequestParam String username, @RequestParam String password,
			User user,RedirectAttributes attributes,Model model) {
		User userflag = userservice.checkUser(username,password);
		if(userflag==null) {//如果用户名未被注册
			User u = userservice.saveUser(user);
			if(u!=null) {//注册成功
				user.setPassword(null);
				attributes.addFlashAttribute("successMessage","注册成功！");
				return "redirect:/tourist";
			}else {//注册失败
				model.addAttribute("errorMessage","注册失败~");
				return "tourist/register";
			}
		}else {//用户名已被注册
			model.addAttribute("errorMessage","该用户名已被注册");
			return "tourist/register";
		}
		
	}
	
	@PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        User user = userservice.checkUser(username,password);
        if(user!=null){
            user.setPassword(null); //  不将密码传入Session，保证安全性
            session.setAttribute("user",user);
            return "redirect:/";
        }else{
            //redirect重定向返还的内容信息，只能通过RedirectAttributes对像转发，使用ModelAndView无效果
            attributes.addFlashAttribute("errorMessage","用户名或密码错误！");
            return "redirect:/tourist";
        }
    }

}
