package no.jcaworks.api.fagsystem;

import no.jcaworks.api.fagsystem.dto.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface FagSystemResource {

    @POST("/customer")
    Call<CreateCustomerResponse> createCustomer(@Body CreateCustomerRequest createCustomerRequest);

    @POST("/agreement")
    Call<CreateAgreementResponse> createAgreement(@Body CreateAgreementRequest createAgreementRequest);

    @PUT(("/agreement/{agreementId}"))
    Call<UpdateAgreementResponse> updateAgreement(@Query("agreementId") String agreementId);

}
