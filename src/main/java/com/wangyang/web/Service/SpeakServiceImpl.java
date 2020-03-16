package com.wangyang.web.Service;

import com.wangyang.web.Dao.SpeakDao;
import com.wangyang.web.Model.Speak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpeakServiceImpl implements SpeakService{
    @Autowired
    private SpeakDao speakDao;

    @Override
    public Speak getOneSpeak(Long id) {

        return speakDao.getOne(id);
    }

	@Override
	public Page<Speak> listSpeak(Pageable pageable) {
		
		return speakDao.findAll(pageable);
	}

	@Override
	public void deleteSpeak(Long id) {
		
		speakDao.deleteById(id);
	}
}
