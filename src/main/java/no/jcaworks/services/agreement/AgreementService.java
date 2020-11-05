package no.jcaworks.services.agreement;

import no.jcaworks.api.brevtjeneste.BrevTjenesteResource;
import no.jcaworks.api.brevtjeneste.dto.DispatchLetterRequest;
import no.jcaworks.api.fagsystem.FagSystemResource;
import no.jcaworks.api.fagsystem.dto.CreateCustomerRequest;
import no.jcaworks.api.fagsystem.dto.CreateCustomerResponse;
import no.jcaworks.controllers.agreement.dto.CreateAgreementRequest;
import no.jcaworks.controllers.agreement.dto.CreateAgreementResponse;
import no.jcaworks.exceptions.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * Service to create customer agreements.
 *
 */
@Service
public class AgreementService implements IAgreementService {

    private static final Logger log = LoggerFactory.getLogger(AgreementService.class);

    @Autowired
    private FagSystemResource fagSystemResource;

    @Autowired
    private BrevTjenesteResource brevTjenesteResource;

    public CreateAgreementResponse createAgreement(CreateAgreementRequest createAgreementRequest) {
        final UUID transactionId = UUID.randomUUID();
        log.info("Received request {} with transaction ID {}", createAgreementRequest.toString(), transactionId);

        final CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email(createAgreementRequest.getEmail())
                .firstName(createAgreementRequest.getFirstName())
                .lastName(createAgreementRequest.getLastName())
                .socialSecurityNumber(createAgreementRequest.getSocialSecurityNumber())
                .build();

        CreateCustomerResponse createCustomerResponse;
        try {
            createCustomerResponse = fagSystemResource.prepareCreateCustomer(transactionId, createCustomerRequest).execute().body();
        } catch (IOException e) {
            // Nothing to roll back
            throw new TransactionException(transactionId, "Failed to create customer " + createCustomerRequest.toString() + " in transaction " + transactionId, e);
        }

        final no.jcaworks.api.fagsystem.dto.CreateAgreementRequest request = no.jcaworks.api.fagsystem.dto.CreateAgreementRequest.builder()
                .bonus(createAgreementRequest.getBonus())
                .customerNumber(createCustomerResponse.getCustomerNumber())
                .vehicleRegistrationNumber(createAgreementRequest.getVehicleRegistrationNumber())
                .build();

        no.jcaworks.api.fagsystem.dto.CreateAgreementResponse createAgreementResponse;
        try {
            createAgreementResponse = fagSystemResource.prepareCreateAgreement(transactionId, request).execute().body();
        } catch (IOException e) {
            fagSystemResource.abortTransaction(transactionId);
            throw new TransactionException(transactionId,
                    "Failed to create agreement " + request.toString() + " in transaction " + transactionId, e);
        }

        final DispatchLetterRequest dispatchLetterRequest = DispatchLetterRequest.builder()
                .agreementId(createAgreementResponse.getAgreementId())
                .email(createAgreementRequest.getEmail())
                .firstName(createAgreementRequest.getFirstName())
                .lastName(createAgreementRequest.getLastName())
                .build();

        try {
            brevTjenesteResource.prepareDispatchLetter(transactionId, dispatchLetterRequest).execute().body();
        } catch (IOException e) {
            fagSystemResource.abortTransaction(transactionId);
            throw new TransactionException(transactionId, "Failed to dispatch letter " + dispatchLetterRequest.toString() + " in transaction " + transactionId, e);
        }

        no.jcaworks.api.fagsystem.dto.UpdateAgreementResponse updateAgreementResponse;
        try {
            updateAgreementResponse = fagSystemResource.prepareUpdateAgreement(transactionId, createAgreementResponse.getAgreementId())
            .execute()
            .body();
        } catch (IOException e) {
            fagSystemResource.abortTransaction(transactionId);
            brevTjenesteResource.abortTransaction(transactionId);
           throw new TransactionException(transactionId, "Failed to update agreement " + createAgreementResponse.getAgreementId() + " in transaction " + transactionId, e);
        }

        fagSystemResource.commitCreateCustomer(transactionId);
        fagSystemResource.commitCreateAgreement(transactionId);
        brevTjenesteResource.commitDispatchLetter(transactionId);
        fagSystemResource.commitUpdateAgreement(transactionId);

        log.info("Completed request {} with transaction ID {}", createAgreementRequest.toString(), transactionId);
        return new CreateAgreementResponse(createAgreementResponse.getAgreementId(), updateAgreementResponse.getAgreementStatus());
    }

}
