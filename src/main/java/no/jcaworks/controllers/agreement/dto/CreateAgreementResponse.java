package no.jcaworks.controllers.agreement.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Response object for create agreement
 */


@AllArgsConstructor
@ToString
public class CreateAgreementResponse {
    private String agreementId;
    private String agreementStatus;
}
