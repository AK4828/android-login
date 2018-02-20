package akm.com.loginexample.login;

/**
 * Created by akm on 2/20/18.
 */

public interface LoginContract {

    interface View {
        void onLoginSuccess();

        void onLoginFailed(String message);
    }

    interface Presenter {
        void login(String username, String password);
    }
}
