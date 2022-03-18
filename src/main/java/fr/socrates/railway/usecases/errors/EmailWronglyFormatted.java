package fr.socrates.railway.usecases.errors;

public class EmailWronglyFormatted extends RuntimeException {
    private final String email;

    public EmailWronglyFormatted(String email) {
        super();
        this.email = email;
    }
}
