package akm.com.loginexample.di;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Singleton;

import akm.com.loginexample.constant.ConstantUrl;
import akm.com.loginexample.interceptor.AuthInterceptor;
import akm.com.loginexample.util.AuthenticationUtils;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by akm on 2/19/18.
 */

@Module
public class ConnectionModule {

    @Provides
    @Singleton
    ObjectMapper providesObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(Set<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ConstantUrl.SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Set<Interceptor> providesRetrofitInterceptors(Context context, AuthInterceptor authInterceptor) {
        Set<Interceptor> interceptors = new LinkedHashSet<>();
        interceptors.add(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));
        interceptors.add(authInterceptor);
        return interceptors;
    }

    @Provides
    @Singleton
    AuthInterceptor provideAuthInterceptor(AuthenticationUtils authenticationUtils) {
        return new AuthInterceptor(authenticationUtils);
    }

    @Provides
    @Singleton
    AuthenticationUtils provideAuthenticationUtils() {
        return new AuthenticationUtils();
    }
}
