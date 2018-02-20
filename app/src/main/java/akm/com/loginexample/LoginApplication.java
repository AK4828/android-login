package akm.com.loginexample;

import android.app.Application;

/**
 * Created by akm on 2/19/18.
 */

public class LoginApplication extends Application {

    private static LoginApplication instance;

    public LoginApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static LoginApplication getInstance() {
        return instance;
    }
}
