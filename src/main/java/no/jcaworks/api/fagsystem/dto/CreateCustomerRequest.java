package no.jcaworks.api.fagsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;
    private String email;
}
