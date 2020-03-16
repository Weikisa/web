package com.wangyang.web.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangyang.web.Model.Speak;


public interface TouristDao extends JpaRepository<Speak,Long>{

	
}
