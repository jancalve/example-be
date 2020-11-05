package no.jcaworks.api.brevtjeneste;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class BrevTjenesteApi implements InitializingBean {

    @Value("${brevtjeneste.host}")
    private String host;
    @Value("${brevtjeneste.port}")
    private String port;

    OkHttpClient.Builder httpClient;
    Retrofit retrofit;

    @Bean
    public BrevTjenesteResource createBrevTjenesteResource() {
        return retrofit.create(BrevTjenesteResource.class);
    }

    @Override
    public void afterPropertiesSet() {
        httpClient = new OkHttpClient.Builder();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://" + host + ":" + port)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
