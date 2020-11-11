package com.example.freeClimb.MessagingModule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.freeClimb.MessagingModule.model.SmsRequest;
import com.example.freeClimb.MessagingModule.service.FreeClimbSmsService;
import com.vailsys.freeclimb.api.FreeClimbException;
@Service
public class SmsService {
	@Autowired
	private FreeClimbSmsService FCSmsService;
	public void sendSms(SmsRequest smsReq) throws FreeClimbException {
		
		FCSmsService.sendSms(smsReq);
	}
	
	

}
