package akm.com.loginexample.login;

import javax.inject.Inject;

import akm.com.loginexample.defaults.base.BasePresenter;
import akm.com.loginexample.entity.Authentication;
import akm.com.loginexample.util.AuthenticationUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by akm on 2/20/18.
 */

public class LoginPresenter extends BasePresenter implements LoginContract.Presenter {

    private final LoginService loginService;
    private final LoginContract.View view;
    private final AuthenticationUtils authenticationUtils;

    @Inject
    public LoginPresenter(LoginContract.View view, LoginService loginService, AuthenticationUtils authenticationUtils) {
        this.view = view;
        this.loginService = loginService;
        this.authenticationUtils = authenticationUtils;
    }
    @Override
    public void login(String username, String password) {
        loginService.authenticate(username, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceSubscriber<Authentication>() {
                    @Override
                    public void onNext(Authentication authentication) {
                        if (authentication != null) {
                            authenticationUtils.registerAuthentication(authentication);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        view.onLoginFailed(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.onLoginSuccess();
                    }
                });
    }

    @Override
    protected void onViewDestroy() {

    }
}
