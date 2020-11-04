package no.jcaworks.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.jcaworks.controllers.agreement.dto.CreateAgreementRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AgreementServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void registrationWorksThroughAllLayers() throws Exception {
        CreateAgreementRequest request = CreateAgreementRequest.builder()
                .bonus(30)
                .email("test@gmail.com")
                .firstName("Jan Christian")
                .lastName("Alvestad")
                .socialSecurityNumber("123")
                .vehicleRegistrationNumber("CV95432")
                .build();



        mockMvc.perform(post("/agreement", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

}
