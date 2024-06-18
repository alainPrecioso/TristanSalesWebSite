package com.aprec.tristan.user.registration.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailReader {
	
	

    String readFileToString(String path);

}
