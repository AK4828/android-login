package akm.com.loginexample.di;

import javax.inject.Singleton;

import akm.com.loginexample.login.LoginService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by akm on 2/19/18.
 */

@Module
public class ServiceModule {

    @Provides
    @Singleton
    public LoginService provideLoginService(Retrofit retrofit) {
        return retrofit.create(LoginService.class);
    }
}
