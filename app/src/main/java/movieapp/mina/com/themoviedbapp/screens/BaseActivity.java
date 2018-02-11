package movieapp.mina.com.themoviedbapp.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import movieapp.mina.com.themoviedbapp.R;

/**
 * This is a base class for all the screens to handle common functions
 *
 * - Using butterknife to bind views
 * - binding common view (optional) like the progress bar, error message, and the reload button
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Optional common views
     */
    @Nullable
    @BindView(R.id.error_text_view)
    AppCompatTextView mErrorTextView;

    @Nullable
    @BindView(R.id.reload_button)
    AppCompatButton mReloadButton;

    @Nullable
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    public abstract int getLayout();

    public void showError(String error) {
        hideProgressBar();
        mErrorTextView.setText(error);
        mErrorTextView.setVisibility(View.VISIBLE);
        mReloadButton.setVisibility(View.VISIBLE);
    }

    public void hideError() {
        mErrorTextView.setVisibility(View.GONE);
        mReloadButton.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Onclick listener to reload button, displayed on error
     */
    @Optional
    @OnClick(R.id.reload_button)
    public abstract void onReloadButtonClick();
}
