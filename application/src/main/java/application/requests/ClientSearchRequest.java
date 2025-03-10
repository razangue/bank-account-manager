package application.requests;

import java.time.LocalDate;
public record ClientSearchRequest(String lastName, String firstName, LocalDate birthDate) {
    
}
