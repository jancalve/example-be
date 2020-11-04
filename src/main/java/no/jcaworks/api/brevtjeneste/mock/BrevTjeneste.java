package no.jcaworks.api.brevtjeneste.mock;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.jcaworks.api.brevtjeneste.dto.DispatchLetterRequest;
import no.jcaworks.api.brevtjeneste.dto.DispatchLetterResponse;
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
 * Mock server for "Brevtjeneste"
 */
@Component
public class BrevTjeneste {

    private static final Logger log = LoggerFactory.getLogger(BrevTjeneste.class);
    private static final int SERVER_PORT = 1088;
    private static final String SERVER_HOST = "127.0.0.1";


    private ClientAndServer mockServer;

    public BrevTjeneste() throws JsonProcessingException {
        log.info("Starting mock server for {} ",  getClass().getSimpleName());
        mockServer = startClientAndServer(SERVER_PORT);
        log.info("Mock server for {} started",  getClass().getSimpleName());

        configureMock();
    }

    @PreDestroy
    public void stopServer() {
        log.info("Shutting down mock server for {} ", getClass().getSimpleName());
        mockServer.stop();
        log.info("Mock server for {} shut down", getClass().getSimpleName());
    }

    private void configureMock() throws JsonProcessingException {

        DispatchLetterRequest dispatchLetterRequest = DispatchLetterRequest.builder()
            .firstName("Jan Christian")
            .lastName("Alvestad")
            .email("test@gmail.com")
            .agreementId("555")
            .build();

        DispatchLetterResponse dispatchLetterResponse = DispatchLetterResponse.builder()
                .dispatchStatus("SENT")
                .build();

        // Dispatch letter
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/dispatch-letter")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact(new ObjectMapper().writeValueAsString(dispatchLetterRequest))),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(exact(new ObjectMapper().writeValueAsString(dispatchLetterResponse)))
                                .withDelay(TimeUnit.SECONDS, 1)
                );
    }

}
