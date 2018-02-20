package akm.com.loginexample;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import akm.com.loginexample.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by akm on 2/19/18.
 */

public class LoginApplication extends Application implements HasActivityInjector {

    private static LoginApplication instance;

    public LoginApplication() {
        instance = this;
    }

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    public static LoginApplication getInstance() {
        return instance;
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
