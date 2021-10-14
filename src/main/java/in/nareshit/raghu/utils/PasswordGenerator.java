package in.nareshit.raghu.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

	public String genPwd() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
	}
}