package com.wangyang.web.Controller.admin;

import com.wangyang.web.Model.Tag;
import com.wangyang.web.Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/findAlltags")
    public String tags(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String  saveTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //已存在该名称(校验)
        Tag tag1 = tagService.getTagByname(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){//非空校验（后端）
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/findAlltags";
    }

    @PostMapping("/tags/{id}")
    public String  editTag(@PathVariable Long id,@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        //已存在该名称(校验)
        Tag tag1 = tagService.getTagByname(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){//非空校验（后端）
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/findAlltags";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/findAlltags";
    }

}
