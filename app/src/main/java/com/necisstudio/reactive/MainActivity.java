package com.necisstudio.reactive;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.necisstudio.reactive.items.ProfileGSON;
import com.necisstudio.reactive.items.ProfileJackson;
import com.necisstudio.reactive.network.RestClient;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Subscription subscribe;

    TextView txtResponse;
    Button btnGet;
    RadioButton radioResponseBody, radioGson, radioJackson;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioResponseBody:
                        getDataResponseBody();
                        break;
                    case R.id.radioGson:
                        getGSON();
                        break;
                    case R.id.radioJackson:
                        getJackson();
                        break;
                }

            }
        });
    }

    private void initView() {
        txtResponse = (TextView) findViewById(R.id.txtResponse);
        btnGet = (Button) findViewById(R.id.btnGet);
        radioGson = (RadioButton) findViewById(R.id.radioGson);
        radioJackson = (RadioButton) findViewById(R.id.radioJackson);
        radioResponseBody = (RadioButton) findViewById(R.id.radioResponseBody);
        radioGroup = (RadioGroup) findViewById(R.id.radioGrub);

    }

    private void getJackson(){
        final ProgressDialog progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setTitle("Response Body");
        progressBar.setMessage("Please Wait");
        progressBar.show();

        RestClient.ApiInterface service = RestClient.getClient();
        Observable<ProfileJackson> call = service.getJackSon();
        subscribe = call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProfileJackson>() {
                    @Override
                    public void onCompleted() {
                        progressBar.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.dismiss();
                        handleError(e);
                    }

                    @Override
                    public void onNext(ProfileJackson profileJackson) {
                        txtResponse.setText(profileJackson.getName());
                    }
                });
    }
    private void getGSON() {
        final ProgressDialog progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setTitle("Response Body");
        progressBar.setMessage("Please Wait");
        progressBar.show();

        RestClient.ApiInterface service = RestClient.getClient();
        Observable<ProfileGSON> call = service.getGSON();
        subscribe = call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProfileGSON>() {
                    @Override
                    public void onCompleted() {
                        progressBar.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.dismiss();
                        Log.e("data", handleError(e));
                    }

                    @Override
                    public void onNext(ProfileGSON profile) {
                        txtResponse.setText(profile.getName());
                    }
                });
    }

    private void getDataResponseBody() {
        final ProgressDialog progressBar = new ProgressDialog(MainActivity.this);
        progressBar.setTitle("Response Body");
        progressBar.setMessage("Please Wait");
        progressBar.show();
        RestClient.ApiInterface service = RestClient.getClient();
        Observable<retrofit2.Response<ResponseBody>> call = service.getResponsebody();
        subscribe = call.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<retrofit2.Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("data", "complated");
                        progressBar.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("data", handleError(e));
                        progressBar.dismiss();
                    }

                    @Override
                    public void onNext(retrofit2.Response<ResponseBody> responseBodyResponse) {
                        try {
                            String restData = responseBodyResponse.body().string();
                            txtResponse.setText(restData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private String handleError(Throwable t) {
        String data = null;
        if (t instanceof SocketTimeoutException) {
            data = "Connection Timeout";
        } else {
            data = "No Connection";
        }

        return data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();
    }
}
