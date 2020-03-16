package com.wangyang.web.Controller;

import com.wangyang.web.Model.Comment;
import com.wangyang.web.Model.User;
import com.wangyang.web.Service.BlogService;
import com.wangyang.web.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    //根据blog的id获取当前blog下的所有评论
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));

        return "blog :: commentList";
    }

    //点击发布提交评论信息至，指定id的blog (comment.getBlog().getId())
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {

        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if(user!=null){
            comment.setAvatar(user.getAvatar());//获取头像
            comment.setAdminComment(true);//设置为管理员
//            comment.setNickname(user.getNickname());
        }else{
            comment.setAvatar(avatar);//设置默认头像(写死)
        }
        commentService.save(comment);
        return "redirect:/comments/"+blogId;
    }

}
