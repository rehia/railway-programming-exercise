package fr.socrates.railway.infra;

import fr.socrates.railway.domain.Postman;
import org.springframework.stereotype.Service;

@Service
public class FakePostman implements Postman {
    @Override
    public void sendWelcomeMessage(String email, String firstName, String lastName) {

    }
}
