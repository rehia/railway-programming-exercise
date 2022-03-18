package fr.socrates.railway.usecases;

import fr.socrates.railway.domain.Postman;
import fr.socrates.railway.domain.User;
import fr.socrates.railway.domain.UserDirectory;
import fr.socrates.railway.usecases.errors.EmailWronglyFormatted;
import fr.socrates.railway.usecases.errors.PasswordsDoNotMatch;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

@Service
public class UserRegistrationService {
    private final UserDirectory userDirectory;
    private final Postman postman;

    public UserRegistrationService(UserDirectory userDirectory, Postman postman) {
        this.userDirectory = userDirectory;
        this.postman = postman;
    }

    public User register(String firstName, String lastName, String email, String password, String passwordConfirmation) {
        checkEmailFormat(email);
        checkPassword(password, passwordConfirmation);

        final var user = new User(UUID.randomUUID().toString(), firstName, lastName, email);

        userDirectory.save(user);
        postman.sendWelcomeMessage(email, firstName, lastName);

        return user;
    }

    private void checkPassword(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw new PasswordsDoNotMatch();
        }
    }

    private void checkEmailFormat(String email) {
        if (!emailIsWellFormatted(email)) {
            throw new EmailWronglyFormatted(email);
        }
    }

    private boolean emailIsWellFormatted(String email) {
        Pattern pattern = compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", CASE_INSENSITIVE);
        return pattern.asPredicate().test(email);
    }
}
