package com.wangyang.web.Dao;

import com.wangyang.web.Model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagDao extends JpaRepository<Tag,Long> {
    Tag findByname(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
