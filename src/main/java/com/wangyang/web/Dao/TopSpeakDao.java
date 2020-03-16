package com.wangyang.web.Dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wangyang.web.Model.Blog;
import com.wangyang.web.Model.Tag;
import com.wangyang.web.Model.TopSpeak;

public interface TopSpeakDao extends JpaRepository<TopSpeak,Long>, JpaSpecificationExecutor<Blog>{

	//update需要@Transactional事务注解
//    @Transactional
//    @Modifying
//    @Query("update TopSpeak tt set tt.speak_id = ?1 tt.user_id= ?2 where tt.id = ?3")
//    int updateTopSpeak(Long speak_id,Long user_id,Long id);
    
    
}
