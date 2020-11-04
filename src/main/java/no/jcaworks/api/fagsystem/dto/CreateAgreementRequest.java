package no.jcaworks.api.fagsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class CreateAgreementRequest {

    private String vehicleRegistrationNumber;
    private Integer bonus;
    private String customerNumber;
}
