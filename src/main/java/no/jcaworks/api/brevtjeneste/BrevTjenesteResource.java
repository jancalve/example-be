package no.jcaworks.api.brevtjeneste;

import no.jcaworks.api.brevtjeneste.dto.DispatchLetterRequest;
import no.jcaworks.api.brevtjeneste.dto.DispatchLetterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BrevTjenesteResource {

    @POST("/dispatch-letter")
    Call<DispatchLetterResponse> dispatchLetter(@Body DispatchLetterRequest dispatchLetterRequest);

}
