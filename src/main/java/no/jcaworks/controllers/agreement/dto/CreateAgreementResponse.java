package no.jcaworks.controllers.agreement.dto;

import lombok.*;

/**
 * Response object for create agreement
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class CreateAgreementResponse {
    private String agreementId;
    private String agreementStatus;
}
