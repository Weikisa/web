package com.wangyang.web.Dao;

import com.wangyang.web.Model.Speak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpeakDao extends JpaRepository<Speak,Long> {

    @Query("select s from Speak s where function('date_format',s.createTime,'%Y') = ?1")
    Speak findFirstByCreateTime(String year);
}
