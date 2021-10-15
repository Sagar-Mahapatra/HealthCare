
package in.nareshit.raghu.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.repository.UserRepository;
import in.nareshit.raghu.service.IUserService;

@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public Integer saveUser(User user) {
		String encodedPsw = encoder.encode(user.getPassword());
		user.setPassword(encodedPsw);
		return repo.save(user).getUId();
	}

	public Optional<User> findByUsername(String username) {
		return repo.findByUserName(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = findByUsername(username);
		if (optional.isPresent()) {
			User user = optional.get();
			UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(),
					user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
			System.out.println(userDetails);
			return userDetails;
		} else {
			throw new UsernameNotFoundException("NO USER FOUND WITH THIS USERNAME");
		}

	}

}
