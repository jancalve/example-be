package no.jcaworks.api.fagsystem;

import no.jcaworks.api.fagsystem.dto.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.UUID;

public interface FagSystemResource {

    /**
     * Prepare to create a new customer
     *
     * @param transactionId of the transaction
     * @param CreateCustomerRequest containing information on the new customer
     * @return CreateCustomerResponse containing information on the created customer
     */
    @POST("/{transactionId}/customer")
    Call<CreateCustomerResponse> prepareCreateCustomer(@Path("transactionId") UUID transactionId,
                                                       @Body CreateCustomerRequest createCustomerRequest);

    /**
     * Commit to create the new customer
     *
     * @param transactionId of the transaction
     */
    @POST("/{transactionId}/customer")
    Call<Void> commitCreateCustomer(@Path("transactionId") UUID transactionId);


    /**
     * Prepare to create a new agreement
     *
     * @param transactionId of the transaction
     * @param CreateAgreementRequest containing information on the new agreement
     * @return CreateAgreementResponse containing information on the created agreement
     */
    @POST("/{transactionId}/agreement")
    Call<CreateAgreementResponse> prepareCreateAgreement(@Path("transactionId") UUID transactionId,
                                                         @Body CreateAgreementRequest createAgreementRequest);

    /**
     * Commit to create the new agreement
     *
     * @param transactionId of the transaction
     */
    @POST("/{transactionId}/agreement")
    Call<Void> commitCreateAgreement(@Path("transactionId") UUID transactionId);


    /**
     * Prepare to update an agreement
     *
     * @param transactionId of the transaction
     * @param agreementId of the agreement
     * @return UpdateAgreementResponse containing information on the updated agreement
     */
    @PUT(("/{transactionId}/agreement/{agreementId}"))
    Call<UpdateAgreementResponse> prepareUpdateAgreement(@Path("transactionId") UUID transactionId,
                                                         @Path("agreementId") String agreementId);

    /**
     * Commit to update an new agreement
     *
     * @param transactionId of the transaction
     */
    @PUT(("/{transactionId}/agreement/{agreementId}"))
    Call<Void> commitUpdateAgreement(@Path("transactionId") UUID transactionId);


    /**
     * Request to abort all operations with the given transactionId
     *
     * @param transactionId of the transaction
     */
    @DELETE(("/{transactionId}}"))
    Call<Void> abortTransaction(@Path("transactionId") UUID transactionId);


}
