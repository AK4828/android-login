package akm.com.loginexample.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import akm.com.loginexample.R;
import akm.com.loginexample.defaults.base.BaseActivity;
import akm.com.loginexample.defaults.base.BasePresenter;
import akm.com.loginexample.main.MainActivity;
import akm.com.loginexample.util.AuthenticationUtils;
import butterknife.BindView;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

/**
 * Created by akm on 2/19/18.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {
    @BindView(R.id.username_input)
    EditText usernameInput;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.login_button)
    AppCompatButton loginButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.link_signup)
    TextView linkSignup;

    @Inject
    AuthenticationUtils authenticationUtils;
    @Inject
    LoginPresenter loginPresenter;

    @Override
    public void setup() {
        AndroidInjection.inject(this);
        if (authenticationUtils.getCurrentAuthentication() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public BasePresenter attachPresenter() {
        return loginPresenter;
    }

    @OnClick(R.id.login_button)
    public void onLoginClicked() {
        loginButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        String username = usernameInput.getText().toString();
        String password = inputPassword.getText().toString();

        loginPresenter.login(username, password);
    }

    @Override
    public void onLoginSuccess() {
        loginButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onLoginFailed(String message) {
        loginButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        Toast.makeText(LoginActivity.this, "FAILED", Toast.LENGTH_SHORT).show();
    }
}
