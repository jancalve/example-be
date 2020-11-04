package no.jcaworks.api.brevtjeneste.mock;


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

    public BrevTjeneste() {
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

    private void configureMock() {

        // Create customer
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/agreement")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact("{firstName: 'Jan', lastName: 'Alvestad, email: 'jancalve@gmail.com', " +
                                        "socialSecurityNumber: '111'}")),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ customerNumber: '555-666-777' }")
                                .withDelay(TimeUnit.SECONDS, 1)
                );
    }

}
