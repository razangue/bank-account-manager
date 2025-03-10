package application.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import domain.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientRequest {
    private String lastName;
    private String firstName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String gender;
    private String nationality;

    public Client toClient() {
        return Client.builder()
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(birthDate)
                .gender(gender)
                .nationality(nationality)
                .build();
    }
}
