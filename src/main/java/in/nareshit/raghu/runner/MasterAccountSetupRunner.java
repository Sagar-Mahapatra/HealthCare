package in.nareshit.raghu.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.service.IUserService;

@Component
public class MasterAccountSetupRunner implements CommandLineRunner {
	@Value("${master.user.name}")
	private String displayName;
	@Value("${master.user.email}")
	private String userName;
	@Autowired
	private IUserService service;

	@Override
	public void run(String... args) throws Exception {
		if (!service.findByUsername(userName).isPresent()) {
			service.saveUser(new User(null, userName, displayName, "MASTER"));
		}
	}

}
