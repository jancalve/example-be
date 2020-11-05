package no.jcaworks.controllers.agreement;

import no.jcaworks.controllers.agreement.dto.CreateAgreementRequest;
import no.jcaworks.controllers.agreement.dto.CreateAgreementResponse;
import no.jcaworks.exceptions.TransactionException;
import no.jcaworks.services.agreement.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping(value = "/agreement")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @RequestMapping(method = RequestMethod.POST)
    public CreateAgreementResponse create(@RequestBody CreateAgreementRequest createAgreementDao) {
        return agreementService.createAgreement(createAgreementDao);
    }

    @ExceptionHandler(TransactionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, UUID> handleTransactionException(TransactionException te) {
        // Provide transaction ID for FE client in case of errors to aid support/traceability
        return new HashMap<String, UUID>() {{
            put("transactionId", te.getTransactionId());
        }};
    }

}
