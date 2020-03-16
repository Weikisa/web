package com.wangyang.web.Dao;

import com.wangyang.web.Model.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {

    //查找所有指定blog_id的且prarent_comment_id为null的所有Comment
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

}
