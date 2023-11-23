package ua.lviv.javaclub.hibernate6.examples.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.lviv.javaclub.hibernate6.examples.models.Foo;
import ua.lviv.javaclub.hibernate6.multitenancy.User;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface FooRepository extends CrudRepository <Foo, Integer> {

    Optional<Foo> findById(Integer id);

}
