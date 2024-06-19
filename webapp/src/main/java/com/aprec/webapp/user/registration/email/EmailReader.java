package com.aprec.webapp.user.registration.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailReader {
	
	

    String readFileToString(String path);

}
