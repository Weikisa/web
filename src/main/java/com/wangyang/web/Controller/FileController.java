package com.wangyang.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
public class FileController {
	
	
	@GetMapping("/toUpload")
	public String toUploadFile() {
		return "upload";
	}

}
