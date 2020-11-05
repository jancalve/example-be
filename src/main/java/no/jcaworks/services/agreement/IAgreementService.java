package no.jcaworks.services.agreement;

import no.jcaworks.controllers.agreement.dto.CreateAgreementRequest;
import no.jcaworks.controllers.agreement.dto.CreateAgreementResponse;
import no.jcaworks.exceptions.TransactionException;

public interface IAgreementService {

    /**
     * Create an agreement
     *
     * @param createAgreement containing parameters for creating agreement
     * @return response containing information about the created agreement
     */
    CreateAgreementResponse createAgreement(CreateAgreementRequest createAgreement) throws TransactionException;

}
