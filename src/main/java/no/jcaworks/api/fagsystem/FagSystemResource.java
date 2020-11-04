package no.jcaworks.api.fagsystem;

import no.jcaworks.api.fagsystem.dto.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface FagSystemResource {

    @POST("/customer")
    Call<CreateCustomerResponse> createCustomer(@Body CreateCustomerRequest createCustomerRequest);

    @POST("/agreement")
    Call<CreateAgreementResponse> createAgreement(@Body CreateAgreementRequest createAgreementRequest);

    @PUT(("/agreement/{agreementId}"))
    Call<UpdateAgreementResponse> updateAgreement(@Path("agreementId") String agreementId);

}
