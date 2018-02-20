package akm.com.loginexample.di;

import android.app.Application;

import javax.inject.Singleton;

import akm.com.loginexample.LoginApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by akm on 2/19/18.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ApplicationModule.class,
        ConnectionModule.class,
        ServiceModule.class,
        ActivityBuilder.class
})
public interface AppComponent {
    void inject(LoginApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
