
package in.nareshit.raghu.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.repository.UserRepository;
import in.nareshit.raghu.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository repo;

	public Integer saveUser(User user) {
		return repo.save(user).getUId();
	}

	public Optional<User> findByUsername(String username) {
		return repo.findByUserName(username);
	}
}
