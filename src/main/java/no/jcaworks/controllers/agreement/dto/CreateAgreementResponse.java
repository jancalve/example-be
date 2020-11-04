package no.jcaworks.controllers.agreement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Response object for create agreement
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CreateAgreementResponse {
    private String agreementId;
    private String agreementStatus;
}
