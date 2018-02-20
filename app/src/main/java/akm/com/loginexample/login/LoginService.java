package akm.com.loginexample.login;

import akm.com.loginexample.constant.ConstantUrl;
import akm.com.loginexample.entity.Authentication;
import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by akm on 2/20/18.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST(ConstantUrl.LOGIN_URL)
    Flowable<Authentication> authenticate(@Field("username") String username,
                                          @Field("password") String password);
}
