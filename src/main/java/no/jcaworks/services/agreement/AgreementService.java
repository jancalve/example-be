package no.jcaworks.services.agreement;

import no.jcaworks.api.brevtjeneste.BrevTjenesteResource;
import no.jcaworks.api.brevtjeneste.dto.DispatchLetterRequest;
import no.jcaworks.api.brevtjeneste.dto.DispatchLetterResponse;
import no.jcaworks.api.fagsystem.FagSystemResource;
import no.jcaworks.api.fagsystem.dto.CreateCustomerRequest;
import no.jcaworks.api.fagsystem.dto.CreateCustomerResponse;
import no.jcaworks.controllers.agreement.dto.CreateAgreementRequest;
import no.jcaworks.controllers.agreement.dto.CreateAgreementResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AgreementService implements IAgreementService {

    private static final Logger log = LoggerFactory.getLogger(AgreementService.class);

    @Autowired
    private FagSystemResource fagSystemResource;

    @Autowired
    private BrevTjenesteResource brevTjenesteResource;

    public CreateAgreementResponse createAgreement(CreateAgreementRequest createAgreementRequest) throws IOException {
        log.info("Recieved request {}", createAgreementRequest.toString());

        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email(createAgreementRequest.getEmail())
                .firstName(createAgreementRequest.getFirstName())
                .lastName(createAgreementRequest.getLastName())
                .socialSecurityNumber(createAgreementRequest.getSocialSecurityNumber())
                .build();

        CreateCustomerResponse createCustomerResponse = fagSystemResource.createCustomer(createCustomerRequest).execute().body();
        log.info("Create customer response {}", createCustomerResponse.toString());


        no.jcaworks.api.fagsystem.dto.CreateAgreementRequest request = no.jcaworks.api.fagsystem.dto.CreateAgreementRequest.builder()
                .bonus(createAgreementRequest.getBonus())
                .customerNumber(createCustomerResponse.getCustomerNumber())
                .vehicleRegistrationNumber(createAgreementRequest.getVehicleRegistrationNumber())
                .build();

        no.jcaworks.api.fagsystem.dto.CreateAgreementResponse createAgreementResponse = fagSystemResource.createAgreement(request)
                .execute()
                .body();
        log.info("Create agreement response {}", createAgreementResponse.toString());


        DispatchLetterRequest dispatchLetterRequest = DispatchLetterRequest.builder()
                .agreementId(createAgreementResponse.getAgreementId())
                .email(createAgreementRequest.getEmail())
                .firstName(createAgreementRequest.getFirstName())
                .lastName(createAgreementRequest.getLastName())
                .build();

        DispatchLetterResponse dispatchLetterResponse = brevTjenesteResource.dispatchLetter(dispatchLetterRequest).execute().body();
        log.info("Dispatch letter response {}", dispatchLetterResponse.toString());


        no.jcaworks.api.fagsystem.dto.UpdateAgreementResponse updateAgreementResponse =
                fagSystemResource.updateAgreement(createAgreementResponse.getAgreementId())
                .execute()
                .body();
        log.info("Update agreement response {}", updateAgreementResponse.toString());

        return new CreateAgreementResponse(createAgreementResponse.getAgreementId(), updateAgreementResponse.getAgreementStatus());
    }

}
