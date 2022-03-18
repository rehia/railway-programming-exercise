package fr.socrates.railway.infra.http;

import fr.socrates.railway.usecases.UserRegistrationService;
import fr.socrates.railway.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRegistrationController {

    private final UserRegistrationService registrationService;

    public UserRegistrationController(UserRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody UserRegistration registration) {
        final var user = registrationService.register(
            registration.firstName(),
            registration.lastName(),
            registration.email(),
            registration.password(),
            registration.passwordConfirmation()
        );

        return ResponseEntity.ok(user);
    }

    record UserRegistration(
        String firstName,
        String lastName,
        String email,
        String password,
        String passwordConfirmation
    ) {}
}
