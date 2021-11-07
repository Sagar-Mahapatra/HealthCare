package in.nareshit.raghu.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.utils.MyMailUtil;
import in.nareshit.raghu.utils.UserUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	private Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService service;

	@Autowired
	private UserUtil util;

	@Autowired
	private MyMailUtil mailUtil;

	@GetMapping("/login")
	public String showLogin() {
		return "UserLogin";
	}

	@GetMapping("/profile")
	public String showProfile() {
		return "UserProfile";
	}

	@GetMapping("/setup")
	public String setup(HttpSession session, Principal p) {

		// read current username
		String username = p.getName();

		// load user object
		User user = service.findByUsername(username).get();

		// store in HttpSession
		session.setAttribute("userOb", user);

		// session.setMaxInactiveInterval(10*60);

		return "UserHome";
	}

	@GetMapping("/showPwdUpdate")
	public String showPwdUpdate() {
		return "UserPwdUpdate";
	}

	@PostMapping("/pwdUpdate")
	public String updatePwd(@RequestParam String password, HttpSession session, Model model) {
		// read current user from session
		User user = (User) session.getAttribute("userOb");
		// read userId
		Integer userId = user.getUId();

		// make service call
		service.updateUserPwd(password, userId);
		// TODO : EMAIL TASK
		model.addAttribute("message", "Password Updated!");
		return "UserPwdUpdate";
		// return "redirect:logout"
	}

	@GetMapping("/showForgot")
	public String showForgot() {
		return "UserNewPwdGen";
	}

	@PostMapping("/genNewPwd")
	public String genNewPwd(@RequestParam String email, Model model) {
		Optional<User> opt = service.findByUsername(email);
		if (opt.isPresent()) {
			// read user object
			User user = opt.get();

			// Generate new Password
			String pwd = util.genPwd();

			// encode and update in DB
			service.updateUserPwd(pwd, user.getUId());

			// send message to UI
			model.addAttribute("message", "PASSWORD UPDATED! CHECK YOUR INBOX!!");

			log.info("UPDATED PASSWORD IS:: " + pwd);
			// send email to user
			/*
			 * if (user.getUId() != null) new Thread(new Runnable() { public void run() {
			 * String text = "YOUR USERNAME IS: " + user.getUserName() +
			 * ", AND NEW PASSWORD IS " + pwd; try { mailUtil.send(user.getUserName(),
			 * "PASWORD UPDATED!", text); } catch (MessagingException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } } }).start();
			 * 
			 * } else { // if user not present model.addAttribute("message",
			 * "User Not Found!"); }
			 */

		} else {
			model.addAttribute("message", "USER NOT FOUND!");
		}
		return "UserNewPwdGen";
	}
}