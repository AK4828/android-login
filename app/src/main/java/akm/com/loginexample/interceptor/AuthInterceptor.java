package akm.com.loginexample.interceptor;

import android.util.Base64;

import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import akm.com.loginexample.util.AuthenticationUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private static final String CLIENT_ID = "0000015bb4a150850007bf0700000000";
    private static final String CLIENT_SECRET = "$2a$06$F0YQTRPvG8M9SPzIgk49GOgwOH7jcHaT2elonRrs9mSCftNtEgMmi";
    private String loginAuthorizationHeader;
    private AuthenticationUtils authenticationUtils;

    @Inject
    public AuthInterceptor(AuthenticationUtils authenticationUtils) {
        try {
            this.loginAuthorizationHeader = "Basic " + Base64
                    .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(), Base64.NO_WRAP);
            this.authenticationUtils = authenticationUtils;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        if (original.url().encodedPath().equals("/oauth/token")) {
            return chain.proceed(original.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", loginAuthorizationHeader)
                    .build());
        }  else if (authenticationUtils.getCurrentAuthentication() != null) {
            return chain.proceed(original.newBuilder()
                    .header("Authorization", "Bearer " + authenticationUtils.getCurrentAuthentication().getAccessToken())
                    .build());
        } else {
            return chain.proceed(original);
        }
    }
}
