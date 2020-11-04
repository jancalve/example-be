package no.jcaworks.api.brevtjeneste.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DispatchLetterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String agreementId;

}
