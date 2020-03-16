package com.wangyang.web.Dao;

import com.wangyang.web.Model.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeDao extends JpaRepository<Type,Long> {

    Type findByname(String name);

    //根据该type类型的blog数量多少，来从大到小排序，取前6个作为展示的type置于前端右边菜单栏展示
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);


}
