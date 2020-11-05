package no.jcaworks.controllers.agreement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Request object for create agreement
 */

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CreateAgreementRequest {

    @NotEmpty(message = "Vehicle registration number is required")
    private String vehicleRegistrationNumber;

    @Min(value = 0, message = "Bonus cannot be lower than 0")
    @Max(value = 100, message = "Bonus cannot be higher than 100")
    private Integer bonus;

    @NotEmpty(message = "Social security number must be provided")
    private String socialSecurityNumber;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Pattern(regexp = "^(.+)@(.+)$\"", message = "Email is invalid")
    private String email;



}
