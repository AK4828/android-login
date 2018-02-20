package akm.com.loginexample.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.inject.Singleton;

import akm.com.loginexample.LoginApplication;
import akm.com.loginexample.entity.Authentication;


/**
 * Created by AKM on 12/27/17.
 */

@Singleton
public class AuthenticationUtils {

    private static final String AUTHENTICATION = "AUTHENTICATION";

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public void registerAuthentication(Authentication authentication) {
        registerPreference(AUTHENTICATION, authentication);
    }

    public Authentication getCurrentAuthentication(){
        return getObjectFromPreference(AUTHENTICATION, Authentication.class);
    }

    private void registerPreference(String key, Object o) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginApplication.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.putString(key, objectMapper.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            Log.e(AuthenticationUtils.class.getSimpleName(), e.getMessage(), e);
        }

        editor.apply();
    }

    private <T> T getObjectFromPreference(String key, Class<T> clazz) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginApplication.getInstance());
        String jsonAuth = preferences.getString(key, "");

        if (!jsonAuth.equals("")) {
            try {
                return objectMapper.readValue(jsonAuth, clazz);
            } catch (IOException e) {
                Log.e(AuthenticationUtils.class.getSimpleName(), e.getMessage(), e);
            }
        }

        return null;
    }

    public void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginApplication.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(AUTHENTICATION);
        editor.apply();
    }
}
