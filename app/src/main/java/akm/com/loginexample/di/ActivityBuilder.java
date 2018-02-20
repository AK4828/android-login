package akm.com.loginexample.di;

import akm.com.loginexample.login.LoginActivity;
import akm.com.loginexample.login.LoginModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by akm on 2/19/18.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();
}
