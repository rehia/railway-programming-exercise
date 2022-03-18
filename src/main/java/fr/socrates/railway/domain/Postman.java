package fr.socrates.railway.domain;

import org.springframework.stereotype.Service;

@Service
public interface Postman {
    void sendWelcomeMessage(String email, String firstName, String lastName);
}
