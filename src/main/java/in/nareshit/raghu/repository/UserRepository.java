package in.nareshit.raghu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.raghu.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
