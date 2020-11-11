package com.example.login.paymentModuleApp.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.login.paymentModuleApp.model.User;
import com.example.login.paymentModuleApp.service.EmailService;
import com.example.login.paymentModuleApp.service.UserServiceImpl;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

@Controller
public class RegisterController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private EmailService emailService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {

		ModelAndView model = new ModelAndView();
		model.setViewName("user/login");
		return model;
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView model = new ModelAndView();
		User user = new User();
		model.addObject("user", user);
		model.setViewName("user/signup");
		return model;
	}

	public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder, UserServiceImpl userService,
			EmailService emailService) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userService = userService;
		this.emailService = emailService;
	}

	// Return registration form template
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView processRegistrationPage(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register");

		return modelAndView;
	}

	// Process form Input Data
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user,
			BindingResult bindingResult, HttpServletRequest request) {

		// Lookup user in database by e-mail
		User userExists = userService.findUserByEmail(user.getEmail());

		System.out.println(userExists);

		if (userExists != null) {
			modelAndView.addObject("alreadyRegisteredMessage", "Oops! This is a user registered.");
			modelAndView.setViewName("register");
			bindingResult.reject("email");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("register");
		} else {
			// new user so user object created and confirmation email is sent
			user.setEnabled(false);

			// Random String 36-character string token for confirmation link
			user.setConfirmationToken(UUID.randomUUID().toString());

			userService.saveUser(user);

			String appUrl = request.getScheme() + "://" + request.getServerName();

			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(user.getEmail());
			registrationEmail.setSubject("Registration Email");
			registrationEmail.setText(("To confirm your e-mail address, please click the link below:\n" + appUrl
					+ "/confirm?token=" + user.getConfirmationToken()));
			registrationEmail.setFrom("no-reply@gmail.com");

			emailService.sendEmail(registrationEmail);

			modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
			modelAndView.setViewName("register");
		}

		return modelAndView;
	}
	@RequestMapping(value = {"/home/home"}, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		model.addObject("username",user.getFirstName() +" "+user.getLastName());
		model.setViewName("home/home");
	return model;
	}
	@RequestMapping(value = {"/access_denied"}, method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errors/access_denied");
	return model;
	}
	

	// Process Confirmation Link
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView confirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {
		User user = userService.findByConfirmationToken(token);

		if (user == null) { // No Token Found in DB
			modelAndView.addObject("invalidToken", "Ooops! This is an Invalid confirmation link");
		} else { // Token Found
			modelAndView.addObject("confrimationToken", user.getConfirmationToken());
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	// Process confirmation link
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ModelAndView confirmRegistration(ModelAndView modelAndView, BindingResult bindingResult,
			@RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
		modelAndView.setViewName("confirm");

		Zxcvbn passwordCheck = new Zxcvbn();

		Strength strength = passwordCheck.measure(requestParams.get("password"));

		if (strength.getScore() < 3) {
			bindingResult.reject("password");

			redir.addFlashAttribute("errorMessage", "Your password is too weak. Choose a stronger one");

			modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));

			System.out.println(requestParams.get("token"));

			return modelAndView;
		}

		// Find user associated with reset token
		User user = userService.findByConfirmationToken(requestParams.get("token"));

		// Set new Password:error
		user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

		// Set User to enabled
		user.setEnabled(true);

		// Save User
		userService.saveUser(user);

		modelAndView.addObject("successMessage", "Your password has been reset");

		return modelAndView;
	}
}