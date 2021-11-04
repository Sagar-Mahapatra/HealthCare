package in.nareshit.raghu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserName(String username);

	@Modifying
	@Query("UPDATE User SET password=:encPwd WHERE id=:userId")
	void updateUserPwd(String encPwd, Integer userId);

}
