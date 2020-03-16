package com.wangyang.web.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangyang.web.Dao.TouristDao;
import com.wangyang.web.Model.Speak;

@Service
public class TouristServiceImpl implements TouristService{
	@Autowired
	private TouristDao touristdao;
	
	@Override
	public Speak savespeak(Speak speak) {
		speak.setId(null);
		speak.setAgreenum(0);
		speak.setAginstnum(0);
		speak.setCreateTime(new Date());
		return touristdao.save(speak);
	}

}
