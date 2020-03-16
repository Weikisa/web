package com.wangyang.web.Service;

import com.wangyang.web.Model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);

    Comment save(Comment comment);
}
