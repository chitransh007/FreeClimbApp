package com.example.freeClimb.MessagingModule.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.vailsys.freeclimb.api.FreeClimbClient;
import com.vailsys.freeclimb.api.FreeClimbException;
import com.vailsys.freeclimb.api.message.Status;
import com.vailsys.freeclimb.percl.Sms;
import com.vailsys.freeclimb.webhooks.StatusCallback;
import com.vailsys.freeclimb.webhooks.application.MessageDelivery;
import com.vailsys.freeclimb.webhooks.message.*;

@Controller
public class receiveMessageControl {

	private final String fromNumber = System.getenv("FREECLIMB_PHONE_NUMBER");
	private String toNumber = System.getenv("TO_PHONE_NUMBER");
	private final String accountId = System.getenv("ACCOUNT_ID");
	private final String authToken = System.getenv("AUTH_TOKEN");
	private String message = "";

	@RequestMapping(value = { "/texting" }, method = RequestMethod.GET)
	public String TextPage() {
		System.out.println(
				"Account ID currently:" + accountId + " auth_token : " + authToken + " phone NUmber :" + fromNumber);
		return "Text";

	}

	@RequestMapping(value = { "/text" }, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String sendText(@RequestParam(value = "toNum") String toNum, @RequestParam(value = "text") String text)
			throws FreeClimbException, UnsupportedEncodingException {

		System.out.println("value of toNum:" + toNum + "    value of text ==" + text);
		message = text;

		System.out.println("URL without encoding");

//		toNumber = UriUtils.encodePath(toNum, "UTF-8");
//
//		message = UriUtils.encodePath(message, "UTF-8");
		return "redirect:/sendText";

	}

	@GetMapping(value = { "/sendText" })
	public String sendText() throws FreeClimbException {

		System.out.println("message printed =" + message + " to number === " + toNumber);

		FreeClimbClient client = new FreeClimbClient(accountId, authToken);

		client.messages.create(fromNumber, toNumber, message);

		System.out.println("FreeClimb Url ======" + client.messages.getFreeClimbUrl());
		return "Text";
	}

	@RequestMapping(value = { "/SendSms" }, method = RequestMethod.GET)
	public void sendSms() throws FreeClimbException {

		System.out.println("entered in the sendSms method");

		FreeClimbClient client1 = new FreeClimbClient(accountId, authToken);

		System.out.println("FreeCLimb Url" + client1.messages.getFreeClimbUrl());
		client1.messages.create(fromNumber, toNumber, "Hello Farhan!:p");

	}

	@RequestMapping(value = { "/InboundSms1" }, method = RequestMethod.POST)
	public void inboundSms(@RequestBody String body) {
		FreeClimbClient client;
		MessageDelivery msgDeliver;
		StatusCallback statusCallback;

		try {
			statusCallback = StatusCallback.fromJson(body);
			msgDeliver = MessageDelivery.createFromJson(body);
			System.out.println("message received ==== " + msgDeliver.getText() + " message from Number"
					+ msgDeliver.getFrom() + "message delivery time" + System.currentTimeMillis());

			if (msgDeliver.getStatus() == Status.RECEIVED) {
				Sms sms = new Sms(toNumber, fromNumber, "Hello Farhan!:P");
			}
			client = new FreeClimbClient(accountId, authToken);
			System.out.println("");
			System.out.println("message created to number" + toNumber + "form Number" + fromNumber);

			client.messages.create(fromNumber, toNumber, "msg for reply");

		} catch (FreeClimbException e) {
			// Handle Error
			e.printStackTrace();
		}
	}
}
