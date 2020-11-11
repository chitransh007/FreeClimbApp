package com.example.freeClimb.MessagingModule.service;

import org.springframework.stereotype.Service;

import com.example.freeClimb.MessagingModule.config.SmsSender;
import com.example.freeClimb.MessagingModule.model.SmsRequest;
import com.vailsys.freeclimb.api.FreeClimbClient;
import com.vailsys.freeclimb.api.FreeClimbException;

@Service
public class FreeClimbSmsService implements SmsSender {

	private final String fromNumber = System.getenv("FREECLIMB_PHONE_NUMBER");
	private String toNumber = System.getenv("TO_PHONE_NUMBER");
	private final String accountId = System.getenv("ACCOUNT_ID");
	private final String authToken = System.getenv("AUTH_TOKEN");
	private final String TFNnumber = System.getenv("Pharma_TFN");
	

	@Override
	public void sendSms(SmsRequest smsReq) throws FreeClimbException {
		// TODO Auto-generated method stub

		System.out.println("entered in the sendSms method of FCMService to number"+smsReq.getPhoneNumber());
		if (isPhoneNumberValid(smsReq.getPhoneNumber())) {
			FreeClimbClient client = new FreeClimbClient(accountId, authToken);

			client.messages.create(fromNumber, smsReq.getPhoneNumber(), smsReq.getMessage());

			System.out.println("from number ====="+TFNnumber);
		} else {
			throw new IllegalArgumentException("Phone number [" + smsReq.getPhoneNumber() + "] is not valid");
		}

	}

	private boolean isPhoneNumberValid(String phoneNumber) {
		// TODO Auto-generated method stub

		return true;
	}

}
