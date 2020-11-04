package no.jcaworks.api.fagsystem.mock;

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

    private ClientAndServer mockServer;

    public FagSystem() {
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


    private void configureMock() {

        // Create customer
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/customer")
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
                                .withDelay(TimeUnit.SECONDS,1)
                );

        // Create agreement
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/agreement")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact("{socialSecurityNumber: '123', vehicleRegistrationNumber: 'CV95523', bonus: '30'}")),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ agreementNumber: '555-666-777', status: 'PENDING'}")
                                .withDelay(TimeUnit.SECONDS,1)
                );

        // Activate agreement
        new MockServerClient(SERVER_HOST, SERVER_PORT)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/agreement/555-666-777/activate")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact("{agreementNumber: '555-666-777'}")),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(201)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("{ agreementNumber: '555-666-777', status: 'ACTIVE'}")
                                .withDelay(TimeUnit.SECONDS,1)
                );
    }

}
