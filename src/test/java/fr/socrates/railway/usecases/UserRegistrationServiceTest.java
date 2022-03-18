package fr.socrates.railway.usecases;

import fr.socrates.railway.domain.Postman;
import fr.socrates.railway.domain.User;
import fr.socrates.railway.domain.UserDirectory;
import fr.socrates.railway.usecases.errors.CouldNotSaveUser;
import fr.socrates.railway.usecases.errors.CouldNotSendWelcomeEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class UserRegistrationServiceTest {

    private final String firstName = "George";
    private final String lastName = "Abitbol";
    private final String password = "M0nde2M3rde!";
    private final String email = "george.abitbol@pom-pom-galli.fr";

    UserRegistrationService registrationService;
    UserDirectory userDirectory;
    Postman postman;

    @BeforeEach
    void setUp() {
        userDirectory = mock(UserDirectory.class);
        postman = mock(Postman.class);
        registrationService = new UserRegistrationService(userDirectory, postman);
    }

    @Test
    void should_register_a_user() {
        User registeredUser = registrationService.register(firstName, lastName, email, password, password);

        assertThat(registeredUser.id()).isNotBlank();
        assertThat(registeredUser.firstName()).isEqualTo(firstName);
        assertThat(registeredUser.lastName()).isEqualTo(lastName);
        assertThat(registeredUser.email()).isEqualTo(email);
    }

    @Test
    void should_fail_if_email_is_not_a_proper_one() {
        final var email = "george.abitbol@";

        assertThatThrownBy(() -> registrationService.register(firstName, lastName, email, password, password));
    }

    @Test
    void should_fail_if_password_password_confirmation_does_not_match() {
        final var passwordConfirmation = "wrong-password";

        assertThatThrownBy(() -> registrationService.register(firstName, lastName, email, password, passwordConfirmation));
    }

    @Test
    void should_fail_if_could_not_save_the_user() {
        doThrow(new CouldNotSaveUser())
            .when(userDirectory)
            .save(any(User.class));

        assertThatThrownBy(() -> registrationService.register(firstName, lastName, email, password, password));
    }

    @Test
    void should_fail_if_could_not_send_the_welcome_email() {
        doThrow(new CouldNotSendWelcomeEmail())
            .when(postman)
            .sendWelcomeMessage(anyString(), anyString(), anyString());

        assertThatThrownBy(() -> registrationService.register(firstName, lastName, email, password, password));
    }
}