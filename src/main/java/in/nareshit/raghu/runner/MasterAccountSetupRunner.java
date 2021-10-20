package in.nareshit.raghu.runner;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.utils.MyMailUtil;
import in.nareshit.raghu.utils.PasswordGenerator;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {

	@Value("${master.user.name}")
	private String displayName;

	@Value("${master.user.email}")
	private String username;

	@Autowired
	private IUserService userService;

	@Autowired
	private PasswordGenerator passwordGenerator;

	@Autowired
	private MyMailUtil mailUtil;

	public void run(String... args) throws Exception {
		if (!userService.findByUsername(username).isPresent()) {
			String pwd = passwordGenerator.genPwd();
			User user = new User();
			user.setDisplayName(displayName);
			user.setUserName(username);
			user.setPassword(pwd);
			user.setRole(UserRoles.ADMIN.name());
			Integer genId = userService.saveUser(user);
			if (genId != null)
				new Thread(new Runnable() {
					public void run() {
						String text = "Your uname is " + username + ", password is " + pwd;
						try {
							mailUtil.send(username, "ADMIN ADDED", text);
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
		}
	}

}
