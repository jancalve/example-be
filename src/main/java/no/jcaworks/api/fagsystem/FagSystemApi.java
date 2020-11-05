package no.jcaworks.api.fagsystem;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class FagSystemApi implements InitializingBean {

    @Value("${fagsystem.host}")
    private String host;
    @Value("${fagsystem.port}")
    private String port;

    OkHttpClient.Builder httpClient;
    Retrofit retrofit;

    @Bean
    public FagSystemResource createFagsystemResource() {
        return retrofit.create(FagSystemResource.class);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://" + host + ":" + port)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
