package com.example.freeClimb.Call.moduleApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vailsys.freeclimb.api.FreeClimbClient;
import com.vailsys.freeclimb.api.FreeClimbException;
import com.vailsys.freeclimb.api.call.Call;
import com.vailsys.freeclimb.percl.PerCLScript;
import com.vailsys.freeclimb.percl.Say;


@RestController
public class Make_outbound_CallController {
	static Logger LOGGER = LoggerFactory.getLogger(Make_outbound_CallController.class);

	private static  FreeClimbClient client;
	
	public static void run() {
		
		LOGGER.info("entered in the run method ");
	    String accountId = System.getenv("ACCOUNT_ID");
	    String authToken = System.getenv("AUTH_TOKEN");
	    String applicationId = System.getenv("APPLICATION_ID");
	    String toNumber = System.getenv("TO_PHONE_NUMBER");
	    String fromNumber = System.getenv("FREECLIMB_PHONE_NUMBER");
	    try {
	      client = new FreeClimbClient(accountId, authToken);
	    } catch (FreeClimbException e) {
	      // handle exception
	    }

	    LOGGER.info("outDial invoked{}"+fromNumber+"{}"+toNumber);
	    OutDial(fromNumber, toNumber, applicationId);
	  }

	private static void OutDial(String fromNumber, String toNumber, String applicationId) {
		// TODO Auto-generated method stub
		 try {
			 LOGGER.info("entered in the OutDial method");
		      // Create FreeClimbClient object
		      Call call = client.calls.create(toNumber, fromNumber, applicationId);
		    } catch (FreeClimbException ex) {
		      // Exception throw upon failure
		      System.out.print(ex);
		    }
	}
	
	 // set url in call connect field in FreeClimb dashboard
	  @RequestMapping("/InboundCall")
	  public String respond() {
//		 Make_outbound_CallController.run();
		 
		  
	    PerCLScript script = new PerCLScript();
	    Say say = new Say("You just made a call with the FreeClimb API!");
	    // Add PerCL play script to PerCL container
	    script.add(say);
	    return script.toJson();
	  }
}
