package com.wangyang.web.Service;

import com.wangyang.web.Model.TopSpeak;

public interface TopSpeakService {
	
	TopSpeak getTopSpeak();
	
	int updateSpeakToTop(Long speak_id,Long user_id,Long id);
}
