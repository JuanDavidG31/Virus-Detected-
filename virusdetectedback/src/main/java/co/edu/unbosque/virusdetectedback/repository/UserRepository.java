package co.edu.unbosque.virusdetectedback.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.virusdetectedback.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	List<User> findByUser(String user);

	Optional<User> findByGmail(String gmail);

	void deleteByUser(String user);
}
