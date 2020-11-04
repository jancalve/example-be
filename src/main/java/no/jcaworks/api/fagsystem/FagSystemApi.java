package no.jcaworks.api.fagsystem;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class FagSystemApi {

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://127.0.0.1:1087")
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient.build())
            .build();

    @Bean
    public FagSystemResource createFagsystemResource() {
        return retrofit.create(FagSystemResource.class);
    }
}
