package movieapp.mina.com.themoviedbapp;

import android.support.annotation.StringRes;

/**
 * Created by mina on 11/30/17.
 */

public interface BaseContract {
    interface BasePresenter<T extends BaseView> {
        void attachView(T view);
        void detachView();
    }

    interface BaseView {
        void showErrorMessage(@StringRes int error);
    }
}