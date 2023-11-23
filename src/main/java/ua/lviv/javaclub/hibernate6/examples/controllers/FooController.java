package ua.lviv.javaclub.hibernate6.examples.controllers;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.lviv.javaclub.hibernate6.examples.models.Bar;
import ua.lviv.javaclub.hibernate6.examples.models.Foo;
import ua.lviv.javaclub.hibernate6.examples.repositories.FooRepository;

import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/foo")
public class FooController {

    @Autowired
    private FooRepository fooRepository;

    @GetMapping("/generate")
    public ResponseEntity<Void> generateFooData() {
        var faker = new Faker();
        Stream.generate(() -> fooRepository.save(genFoo(faker)))
                .limit(100)
                .count();
        return ResponseEntity.ok().build();
    }

    private Foo genFoo(Faker faker) {
        return Foo.builder()
                .bar(Bar.builder()
                        .id(UUID.randomUUID())
                        .login(faker.name().username())
                        .password(faker.lorem().word())
                        .build()
                ).build();
    }

    @GetMapping("/{id}")
    public Foo getFoo(@PathVariable("id") int id) {
        return fooRepository.findById(id).orElse(null);
    }
}

