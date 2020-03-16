package com.wangyang.web.Service;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangyang.web.NotFoundException;
import com.wangyang.web.Dao.TopSpeakDao;
import com.wangyang.web.Model.TopSpeak;

@Service
public class TopSpeakServiceImpl implements TopSpeakService{
	@Autowired
	private TopSpeakDao topSpeakDao;

	
	@Override
	public TopSpeak getTopSpeak() {
		return topSpeakDao.getOne(new Long(1));
	}


	@Override
	public int updateSpeakToTop(Long speak_id, Long user_id,Long id) {
		TopSpeak topspeak = topSpeakDao.getOne(id);
		if(topspeak==null){
            throw new NotFoundException("置顶出现异常");
        }
		topspeak.setSpeak_id(speak_id);
		topspeak.setUser_id(user_id);
		
		TopSpeak top = topSpeakDao.save(topspeak);
		System.out.println(top);
		if(top!=null) {
			return 1;
		}
		
		return 0;
	}



}
