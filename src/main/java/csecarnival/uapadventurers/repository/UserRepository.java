package csecarnival.uapadventurers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import csecarnival.uapadventurers.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	User findByPhoneNumber(String phoneNumber);
	User findByToken(String token);
}
