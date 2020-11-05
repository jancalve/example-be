package no.jcaworks.api.brevtjeneste;

import no.jcaworks.api.brevtjeneste.dto.DispatchLetterRequest;
import no.jcaworks.api.brevtjeneste.dto.DispatchLetterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

public interface BrevTjenesteResource {

    /**
     * Prepare to dispatch the letter
     *
     * @param transactionId of the transaction
     * @param dispatchLetterRequest containing information on who the letter should be sent to
     * @return Whether the letter has been succesfully queued to send or not
     */
    @POST("{transactionId}/dispatch-letter")
    Call<DispatchLetterResponse> prepareDispatchLetter(@Path("transactionId") UUID transactionId,
                                                       @Body DispatchLetterRequest dispatchLetterRequest);

    /**
     * Confirm dispatching the letter
     *
     * @param transactionId of the transaction
     */
    @POST("{transactionId}/dispatch-letter")
    Call<Void> commitDispatchLetter(@Path("transactionId") UUID transactionId);

    /**
     * Request to abort all operations with the given transactionId
     *
     * @param transactionId to abort
     * @return HTTP status indicating if it was succesfully aborted or not
     */
    @DELETE(("/{transactionId}}"))
    Call<Void> abortTransaction(@Path("transactionId") UUID transactionId);

}
