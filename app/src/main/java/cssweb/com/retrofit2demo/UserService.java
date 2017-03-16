package cssweb.com.retrofit2demo;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenh on 2017/3/16.
 */

public interface UserService {
    @GET("/users/{user}")
    Flowable<User> getUser(@Path("user") String user);
}
