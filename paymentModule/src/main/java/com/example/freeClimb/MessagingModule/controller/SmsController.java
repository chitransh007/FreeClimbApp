package com.example.freeClimb.MessagingModule.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.freeClimb.MessagingModule.config.SmsSender;
import com.example.freeClimb.MessagingModule.service.SmsService;
import com.example.freeClimb.MessagingModule.model.ChatMessage;
import com.example.freeClimb.MessagingModule.model.SmsRequest;
import com.example.freeClimb.MessagingModule.repository.ChatMessageRepository;
import com.vailsys.freeclimb.api.FreeClimbClient;
import com.vailsys.freeclimb.api.FreeClimbException;
import com.vailsys.freeclimb.api.message.Status;
import com.vailsys.freeclimb.percl.Sms;
import com.vailsys.freeclimb.webhooks.StatusCallback;
import com.vailsys.freeclimb.webhooks.application.MessageDelivery;

@Controller
public class SmsController {
	@Autowired
	private SmsService service;

	@Autowired
	private ChatMessageRepository chatMessageRepo;

	private final String fromNumber = System.getenv("FREECLIMB_PHONE_NUMBER");
	private String toNumber = System.getenv("TO_PHONE_NUMBER");
	private final String accountId = System.getenv("ACCOUNT_ID");
	private final String authToken = System.getenv("AUTH_TOKEN");
	private ChatMessage	chatMessage;
	@RequestMapping(value = { "/sendMessage" }, method = RequestMethod.GET)
	public String TextPage(@ModelAttribute("smsRequest") SmsRequest smsReq, Model model) {
		model.addAttribute("smsRequest", new SmsRequest());
		System.out.println(
				"Account ID currently:" + accountId + " auth_token : " + authToken + " phone NUmber :" + fromNumber);

		return "Text";

	}

	
//	@PostMapping(value = "/sendSms")
//	public String sendSms(@ModelAttribute SmsRequest smsReq, Model model)
//			throws FreeClimbException {
//
//		
//		System.out.println("entered sendSms"+smsReq.getPhoneNumber());
////		model.addAttribute("header", smsReq);
//		service.sendSms(smsReq);
//		return "redirect:/sendMessage";
//	}

	@PostMapping(value = "/sendSms")
	public String sendSms(@RequestParam(value = "PhoneNumber") String toNum,@RequestParam(value = "message") String message, Model model)
			throws FreeClimbException {

		SmsRequest smsReq = new SmsRequest();
		smsReq.setMessage(message);
		smsReq.setPhoneNumber(toNum);
		model.addAttribute("PhoneNumber", toNum);
			
//		model.addAttribute("PhoneNumber", smsReq.getPhoneNumber());
		model.addAttribute("message", message);
		System.out.println("entered via submit" +message+ "send to Number" + toNum);
		
		service.sendSms(smsReq);
		return "redirect:/sendMessage";
	}

	@RequestMapping(value = "/InboundSms", method = RequestMethod.POST)
	public String InboundSms(@RequestBody String body) {
		
		Model model = null;
		MessageDelivery msgDeliver;
		try {
			StatusCallback.fromJson(body);
			msgDeliver = MessageDelivery.createFromJson(body);
			System.out.println("message received ==== " + msgDeliver.getText() + " message from Number"
					+ msgDeliver.getFrom() + "message delivery time" + System.currentTimeMillis());

			if (msgDeliver.getStatus() == Status.RECEIVED) {

			
			chatMessage = new ChatMessage(msgDeliver.getText(), new Date(), msgDeliver.getFrom());
				chatMessageRepo.save(chatMessage);
//				model.addAttribute("sender",msgDeliver.getFrom());
//				model.addAttribute("content", msgDeliver.getText());
//				model.addAttribute("chatMessage", chatMessage);
				return "redirect:receivedSms";
//				Sms sms = new Sms(toNumber, fromNumber, "Hello Farhan!:P");
			}

		} catch (FreeClimbException e) {
			// Handle Error
			e.printStackTrace();
		}

		return "Text";
	}
	@GetMapping(value = "/receivedSms")
	public String messageReceived(Model model) {
		model.addAttribute("chatMessage", chatMessage.getContent());
		
		return "Text";
	}

}
