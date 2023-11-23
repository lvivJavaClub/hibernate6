package ua.lviv.javaclub.hibernate6.multitenancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/multi")
public class TenantController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{name}")
    public User findUser(@PathVariable String name) {
        return userRepository.findUserByName(name).orElse(null);
    }
}

