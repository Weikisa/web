package com.wangyang.web.Controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wangyang.web.Model.Speak;
import com.wangyang.web.Service.SpeakService;
import com.wangyang.web.Service.TopSpeakService;

@Controller
@RequestMapping("/admin")
public class SpeakController {
	@Autowired
	private SpeakService speakService;
	@Autowired
    private TopSpeakService topSpeakService;
	
	
	@GetMapping("/findAllSpeaks")
    public String Speaks(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        model.addAttribute("page",speakService.listSpeak(pageable));
      //获取到设置为置顶的留言信息
        model.addAttribute("topspeak",speakService.getOneSpeak(topSpeakService.getTopSpeak().getSpeak_id()));
        System.out.println(speakService.listSpeak(pageable));
        return "admin/speaks";
    }
	
	@GetMapping("/speaks/{id}/updateTop")
	public String updateSpeakToTop(@PathVariable Long id) {
		Speak speak = speakService.getOneSpeak(id);
		Long speak_id = speak.getId();
		Long user_id = speak.getUser().getId();
		int flag =  topSpeakService.updateSpeakToTop(speak_id,user_id,new Long(1));
		
		
		return "redirect:/admin/findAllSpeaks";
	}
	
	@GetMapping("/speaks/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
		speakService.deleteSpeak(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/findAllSpeaks";
    }
}
