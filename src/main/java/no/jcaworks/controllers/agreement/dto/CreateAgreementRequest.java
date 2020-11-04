package no.jcaworks.controllers.agreement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object for create agreement
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CreateAgreementRequest {

    private String vehicleRegistrationNumber;
    private Integer bonus;
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private String email;



}
