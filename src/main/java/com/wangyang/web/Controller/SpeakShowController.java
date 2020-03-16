package com.wangyang.web.Controller;

import com.wangyang.web.Model.Speak;
import com.wangyang.web.Service.SpeakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SpeakShowController {
    @Autowired
    private SpeakService speakService;

    @GetMapping("/speak")
    public String findFirstByCreateTime(Model model){
        Speak speak = speakService.getOneSpeak(new Long(1));
        model.addAttribute("newspeak",speak);
        return "index";
    }

}
