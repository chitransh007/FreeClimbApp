package com.example.freeClimb.MessagingModule.config;

import com.example.freeClimb.MessagingModule.model.SmsRequest;
import com.vailsys.freeclimb.api.FreeClimbException;

public interface SmsSender {

	
	void sendSms(SmsRequest smsReq) throws FreeClimbException;
}
