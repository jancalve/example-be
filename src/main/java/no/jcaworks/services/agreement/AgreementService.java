package no.jcaworks.services.agreement;

import no.jcaworks.controllers.agreement.dto.CreateAgreementRequest;
import no.jcaworks.controllers.agreement.dto.CreateAgreementResponse;
import org.springframework.stereotype.Service;

@Service
public class AgreementService implements IAgreementService {

    public CreateAgreementResponse createAgreement(CreateAgreementRequest createAgreementDao) {
        return new CreateAgreementResponse();
    }

}
