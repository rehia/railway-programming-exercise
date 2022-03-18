package fr.socrates.railway.infra;

import fr.socrates.railway.domain.User;
import fr.socrates.railway.domain.UserDirectory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryUserDirectory implements UserDirectory {
    @Override
    public void save(User user) {

    }
}
