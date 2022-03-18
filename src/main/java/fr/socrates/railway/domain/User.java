package fr.socrates.railway.domain;

public record User(
    String id,
    String firstName,
    String lastName,
    String email
) {
}
