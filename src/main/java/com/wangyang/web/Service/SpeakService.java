package com.wangyang.web.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangyang.web.Model.Speak;

public interface SpeakService {

    Speak getOneSpeak(Long id);
    
    Page<Speak> listSpeak(Pageable pageable);
    
    void deleteSpeak(Long id);
}
