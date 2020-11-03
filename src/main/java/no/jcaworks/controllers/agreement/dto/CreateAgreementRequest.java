package no.jcaworks.controllers.agreement.dto;

import lombok.AllArgsConstructor;

/**
 * Request object for create agreement
 */

@AllArgsConstructor
public class CreateAgreementRequest {

    private String vehicleRegistrationNumber;
    private Integer bonus;
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private String email;



}
