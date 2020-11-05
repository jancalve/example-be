package no.jcaworks.api.fagsystem.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.jcaworks.api.fagsystem.dto.*;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;


/**
 * Mock server for "Fagsystem"
 */
@Component
public class FagSystem {

    private static final Logger log = LoggerFactory.getLogger(FagSystem.class);
    private static final int SERVER_PORT = 1087;
    private static final String SERVER_HOST = "127.0.0.1";

    private final ClientAndServer mockServer;

    public FagSystem() throws JsonProcessingException {
        log.info("Starting mock server for {} ",  getClass().getSimpleName());
        mockServer = startClientAndServer(SERVER_PORT);
        configureMock();
        log.info("Mock server for {} started",  getClass().getSimpleName());
    }

    @PreDestroy
    public void stopServer() {
        log.info("Shutting down mock server for {} ", getClass().getSimpleName());
        mockServer.stop();
        log.info("Mock server for {} shut down", getClass().getSimpleName());
    }


    private void configureMock() throws JsonProcessingException {

        CreateCustomerRequest createCustomerRequest = CreateCustomerRequest.builder()
                .email("test@gmail.com")
                .firstName("Jan Christian")
                .lastName("Alvestad")
                .socialSecurityNumber("123")
                .build();

        CreateCustomerResponse createCustomerResponse = CreateCustomerResponse.builder()
                .customerNumber("456")
                .build();

        // Create customer
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath(".*/customer")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact(new ObjectMapper().writeValueAsString(createCustomerRequest))),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(new ObjectMapper().writeValueAsString(createCustomerResponse))
                                .withDelay(TimeUnit.SECONDS,1)
                );

        CreateAgreementRequest createAgreementRequest = CreateAgreementRequest.builder()
                .vehicleRegistrationNumber("CV95432")
                .bonus(30)
                .customerNumber("456")
                .build();

        CreateAgreementResponse createAgreementResponse = CreateAgreementResponse.builder()
                .agreementId("555")
                .build();

        // Create agreement
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath(".*/agreement")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact(new ObjectMapper().writeValueAsString(createAgreementRequest))),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(new ObjectMapper().writeValueAsString(createAgreementResponse))
                                .withDelay(TimeUnit.SECONDS,1)
                );

        UpdateAgreementResponse updateAgreementResponse = UpdateAgreementResponse.builder()
                .agreementStatus("ACTIVE")
                .build();

        // Activate agreement
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("PUT")
                                .withPath(".*/agreement/555")
                                .withHeader("\"Content-type\", \"application/json\""),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(201)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(new ObjectMapper().writeValueAsString(updateAgreementResponse))
                                .withDelay(TimeUnit.SECONDS,1)
                );
    }

}
