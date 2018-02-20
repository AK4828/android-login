package akm.com.loginexample.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akm on 2/20/18.
 */

@Module
public class LoginModule {

    @Provides
    LoginContract.View provideLoginActivity(LoginActivity loginActivity) {
        return loginActivity;
    }

    @Provides
    LoginContract.Presenter provideLoginPresenter(LoginPresenter loginPresenter) {
        return loginPresenter;
    }
}
