package no.jcaworks.api.fagsystem.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCustomerResponse {
    private String customerNumber;
}
