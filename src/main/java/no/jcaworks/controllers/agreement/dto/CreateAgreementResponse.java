package no.jcaworks.controllers.agreement.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Response object for create agreement
 */

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateAgreementResponse {
    private String agreementNumber;
    private String agreementStatus;
}
