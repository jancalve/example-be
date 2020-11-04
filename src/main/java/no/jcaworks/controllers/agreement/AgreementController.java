package no.jcaworks.controllers.agreement;

import no.jcaworks.controllers.agreement.dto.CreateAgreementRequest;
import no.jcaworks.controllers.agreement.dto.CreateAgreementResponse;
import no.jcaworks.services.agreement.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@ResponseBody
@RequestMapping(value = "/agreement")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @RequestMapping(method = RequestMethod.POST)
    public CreateAgreementResponse create(@RequestBody CreateAgreementRequest createAgreementDao) throws IOException {
        return agreementService.createAgreement(createAgreementDao);

    }

}
