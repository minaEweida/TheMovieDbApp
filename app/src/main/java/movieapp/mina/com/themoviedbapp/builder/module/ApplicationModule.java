package movieapp.mina.com.themoviedbapp.builder.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mina on 11/30/17.
 */
@Module
public class ApplicationModule {
    Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

}
