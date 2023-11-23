package ua.lviv.javaclub.hibernate6.multitenancy;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository <User, Long> {

    Optional<User> findUserByName(String name);

}
