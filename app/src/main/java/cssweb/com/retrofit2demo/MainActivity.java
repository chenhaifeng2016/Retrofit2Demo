package cssweb.com.retrofit2demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView tvResponse;
    private Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 只是例子，没有采用注解方案

        tvResponse = (TextView) findViewById(R.id.tvResponse);
        btnRequest = (Button) findViewById(R.id.btnTest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "test");

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.0.16:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                UserService userService = retrofit.create(UserService.class);
                userService.getUser("chenhaifeng2016")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onSubscribe(Subscription s) {
                                s.request(Long.MAX_VALUE);
                            }

                            @Override
                            public void onNext(User user) {
                                Log.d(TAG, "成功");
                                Log.d(TAG, "response=" + user.getUsername());
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.d(TAG, "出错");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "完成");
                            }
                        });
            }
        });
    }

}
