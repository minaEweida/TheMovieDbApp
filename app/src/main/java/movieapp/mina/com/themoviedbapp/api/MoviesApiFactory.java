package movieapp.mina.com.themoviedbapp.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by mina on 1/30/18.
 */

public class MoviesApiFactory {

    private static String sBaseUrl;
    private static boolean sDebug;

    public static MoviesApi createRetroFitService(String baseUrl, boolean debug) {
        sBaseUrl = baseUrl;
        sDebug = debug;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .client(makeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(makeGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(MoviesApi.class);
    }

    private static OkHttpClient makeOkHttpClient() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS);

        if (sDebug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message
                    -> Timber.d(message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
            httpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        return httpClientBuilder.build();
    }

    private static Gson makeGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
